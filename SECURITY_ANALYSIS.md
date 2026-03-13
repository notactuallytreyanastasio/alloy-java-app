# SQL Security Analysis: Java Todo App

SQL injection analysis of the Java todo app built on the Generic Temper ORM. This analysis focuses exclusively on SQL generation and execution — the core value proposition of the ORM.

**Analysis Date:** 2026-03-12
**Framework:** Spring Boot + SQLite JDBC + Thymeleaf
**ORM:** Generic Temper ORM (compiled to Java)

---

## How This App Uses the ORM

All user-facing CRUD operations flow through the Temper ORM's type-safe SQL generation:

| Operation | Method | SQL Source |
|-----------|--------|------------|
| SELECT lists/todos | `SrcGlobal.from(safeIdentifier).where(...).toSql()` | ORM |
| INSERT list/todo | `SrcGlobal.changeset(table, params).cast(fields).validateRequired(fields).toInsertSql()` | ORM |
| UPDATE todo title | `SrcGlobal.changeset(table, params).cast(fields).toUpdateSql(id)` | ORM |
| DELETE list/todo | `SrcGlobal.deleteSql(table, id)` | ORM |
| WHERE clauses | `SqlBuilder.appendSafe()` + `appendInt32()` | ORM |
| Toggle completed | `UPDATE todos SET completed = CASE ... WHERE id = ?` | Raw (parameterized) |
| Cascade delete | `DELETE FROM todos WHERE list_id = ?` | Raw (parameterized) |
| Count queries | `SELECT COUNT(*) FROM todos WHERE list_id = ?` | Raw (parameterized) |
| Seed count | `SELECT COUNT(*) FROM lists` | Raw (parameterized) |
| DDL | `CREATE TABLE IF NOT EXISTS ...` | Raw (static) |

### ORM SQL Generation Path

```
User input (form field)
  → Spring @RequestParam String title
    → Temper Map construction
      → SrcGlobal.changeset(tableDef, paramsMap)   [factory — sealed interface]
        → .cast(allowedFields)                      [SafeIdentifier whitelist]
          → .validateRequired(fields)               [non-null enforcement]
            → .toInsertSql()                        [type-dispatched escaping]
              → .toString()                         [rendered SQL string]
                → jdbc.execute(sql)                 [Spring JdbcTemplate]
```

For queries:
```
Route parameter (@PathVariable Long id)
  → Spring typed binding (guaranteed Long)
    → id.intValue()                                 [truncation — see JV-SQL-1]
      → SqlBuilder.appendInt32(id)                  [bare integer]
        → SrcGlobal.from(safeIdentifier).where(fragment).toSql()
          → jdbc.query(sql, rowMapper)
```

---

## SQL Injection Analysis

### ORM-Generated SQL: Protected

**SafeIdentifier validation** — Java's `SrcGlobal.safeIdentifier()` validates against `[a-zA-Z_][a-zA-Z0-9_]*`. Returns via Temper's bubble mechanism (equivalent to checked exception) on invalid input.

**SqlString escaping** — String values pass through `SqlString` which escapes `'` → `''`.

**Changeset field whitelisting** — `cast()` requires `List<SafeIdentifier>`, preventing mass assignment.

**Spring `@PathVariable Long`** — Route parameters are type-bound to `Long` by Spring's argument resolver. Non-numeric path segments return 400 before reaching the ORM.

### Raw SQL: Also Protected

All raw SQL uses Spring's `JdbcTemplate` with `?` parameterized queries:

```java
// Toggle — parameterized
jdbc.update("UPDATE todos SET completed = CASE WHEN completed = 0 THEN 1 ELSE 0 END WHERE id = ?", id);

// Cascade delete — parameterized
jdbc.update("DELETE FROM todos WHERE list_id = ?", id);

// Count — parameterized
jdbc.queryForObject("SELECT COUNT(*) FROM todos WHERE list_id = ?", Long.class, list.getId());

// Completed count — parameterized
jdbc.queryForObject("SELECT COUNT(*) FROM todos WHERE list_id = ? AND completed = 1", Long.class, list.getId());
```

**100% of raw SQL in this app uses parameterized queries.** No string concatenation of user input into SQL.

### DDL: Static

Schema creation uses hardcoded `CREATE TABLE` statements.

### PRAGMA Note

`PRAGMA foreign_keys = ON` is set per-connection. With HikariCP connection pooling, this should be set via `connection-init-sql` in `application.properties` to ensure it applies to all pooled connections.

---

## Findings

| # | Severity | CWE | Finding |
|---|----------|-----|---------|
| JV-SQL-1 | LOW | CWE-681 | `Long` path params truncated to `int` via `.intValue()` for ORM calls (`appendInt32`, `deleteSql`, `toUpdateSql`). IDs above `Integer.MAX_VALUE` silently wrap. Not exploitable for injection, but could target wrong record. |
| JV-SQL-2 | LOW | CWE-16 | `PRAGMA foreign_keys = ON` set once at init, but HikariCP may create new connections that don't have the pragma. Should use `spring.datasource.hikari.connection-init-sql`. |
| JV-SQL-3 | INFO | CWE-89 | ORM SQL executed via `jdbc.execute(sql)` as pre-rendered string. Escaping is correct, but parameterized would be strictly safer. |
| JV-SQL-4 | INFO | CWE-400 | SELECT queries use `toSql()` without limits. `safeToSql(defaultLimit)` available but unused. |

### ORM-Level Concerns (shared across all apps)

| # | Severity | CWE | Finding |
|---|----------|-----|---------|
| ORM-1 | MEDIUM | CWE-89 | `toInsertSql`/`toUpdateSql` pass `pair.key` (a `String`) to `appendSafe`. Safe by construction but not type-enforced. |
| ORM-2 | LOW | CWE-89 | `SqlDate.formatTo` wraps `value.toString()` in quotes without escaping. |
| ORM-3 | LOW | CWE-20 | `SqlFloat64.formatTo` can produce `NaN`/`Infinity`. |

---

## Verdict

**No SQL injection vulnerabilities found.** The ORM handles all user-input-to-SQL paths, and this app achieves 100% parameterized raw SQL via Spring's JdbcTemplate. The strongest raw-SQL safety posture of all 6 apps alongside JavaScript.

---

## Evolution: Temper-Level Remediation

**Date:** 2026-03-12
**Commit:** [`1df8c7a`](https://github.com/notactuallytreyanastasio/generic_orm/commit/1df8c7a)

The security analysis above identified 3 ORM-level concerns (ORM-1, ORM-2, ORM-3) shared across all 6 app implementations. Because the ORM is written once in Temper and compiled to all backends, fixing these issues at the Temper source level automatically resolves them in every language — including this Java app.

### What Changed

**ORM-1 (MEDIUM → RESOLVED): Column name type safety in INSERT/UPDATE SQL**

The `toInsertSql()` and `toUpdateSql()` methods previously passed `pair.getKey()` (a raw `String`) to `appendSafe()`. While safe by construction (keys originated from `cast()` via `SafeIdentifier.getSqlValue()`), the type system didn't enforce this. A future refactor could have silently introduced an unvalidated code path.

The fix routes column names through the looked-up `FieldDef.getName().getSqlValue()` — a `SafeIdentifier` — so the column name in the generated SQL always comes from a validated identifier, not a raw map key.

**ORM-2 (LOW → RESOLVED): SqlDate quote escaping**

`SqlDate.formatTo()` previously wrapped `value.toString()` in quotes without escaping. The fix adds character-by-character quote escaping identical to `SqlString.formatTo()`, ensuring defense-in-depth against any future Date format that might contain single quotes.

**ORM-3 (LOW → RESOLVED): SqlFloat64 NaN/Infinity handling**

`SqlFloat64.formatTo()` previously called `value.toString()` directly, which could produce `NaN`, `Infinity`, or `-Infinity` — none valid SQL literals. The fix checks for these values and renders `NULL` instead, which is the safest SQL representation for non-representable floating-point values.

### Why This Matters

This is the core value proposition of a cross-compiled ORM: **one fix in Temper source propagates to all 6 backends simultaneously.** The same commit that fixed the Java compiled output also fixed JavaScript, Python, Rust, Lua, and C#. No per-language patches needed. No risk of inconsistent fixes across implementations.

### Updated Status

| Finding | Original | Current | Resolution |
|---------|----------|---------|------------|
| ORM-1 | MEDIUM | RESOLVED | Column names routed through `SafeIdentifier` |
| ORM-2 | LOW | RESOLVED | `SqlDate.formatTo()` now escapes quotes |
| ORM-3 | LOW | RESOLVED | `SqlFloat64.formatTo()` renders NaN/Infinity as `NULL` |
| ORM-4 | INFO | ACKNOWLEDGED | Design limitation — escaping-based, not parameterized |
