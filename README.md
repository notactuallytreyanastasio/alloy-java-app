# Alloy Todo App -- Java

A full-featured todo list manager built with **Spring Boot + Thymeleaf + SQLite JDBC** that demonstrates **every feature** of the [Alloy ORM](https://github.com/notactuallytreyanastasio/alloy) compiled from Temper to Java. The app exercises **48 ORM features** across 14 routes with a retro Mac System 6 + Windows 95 hybrid UI theme.

## Overview

This application is a complete, working todo list manager with lists, todos, tags, priorities, due dates, and search. Every database interaction -- from simple lookups to complex aggregate queries -- is built through the Alloy ORM's type-safe query builder and changeset validation pipeline. No hand-written SQL touches user input.

The UI features a retro aesthetic blending Windows 95 chrome (beveled borders, grey panels, classic button styles) with a Mac System 6 desktop feel. Inline editing, confirmation dialogs, priority badges (color-coded 1-5), due date pickers, and tag chips are all supported.

## Data Model

Four tables with foreign key relationships:

```sql
lists (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT NOT NULL,
    description TEXT,
    created_at  TEXT NOT NULL
)

todos (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    title       TEXT NOT NULL,
    completed   INTEGER NOT NULL DEFAULT 0,
    priority    INTEGER NOT NULL DEFAULT 3,
    due_date    TEXT,
    list_id     INTEGER NOT NULL REFERENCES lists(id) ON DELETE CASCADE,
    created_at  TEXT NOT NULL
)

tags (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
)

todo_tags (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    todo_id INTEGER NOT NULL REFERENCES todos(id) ON DELETE CASCADE,
    tag_id  INTEGER NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    UNIQUE(todo_id, tag_id)
)
```

All four tables have corresponding `TableDef` and `FieldDef` schema definitions in the ORM layer using `SafeIdentifier` for every table and column name.

## Complete ORM Feature Coverage

### Query Builder

| ORM Feature | Route / Location | Description |
|---|---|---|
| `from` | `GET /` | Start SELECT query on `lists` or `todos` table |
| `select` | `GET /lists/{id}`, `GET /search` | Select specific columns by `SafeIdentifier` |
| `selectExpr` | `GET /`, `GET /advanced` | Select aggregate expressions (countAll, col refs) |
| `where` | `GET /lists/{id}`, `POST /todos/{id}/toggle` | WHERE with `SqlBuilder`-generated conditions |
| `orWhere` | `GET /search?filter=high_or_done`, `GET /advanced` | OR WHERE for compound conditions |
| `whereNull` | `GET /search?filter=no_due_date` | `IS NULL` check on `due_date` |
| `whereNotNull` | `GET /search?filter=has_due_date` | `IS NOT NULL` check on `due_date` |
| `whereIn` | `GET /search?filter=medium_priority` | `IN (values)` with `SqlInt32` list |
| `whereInSubquery` | `GET /search?filter=work_list` | `IN (SELECT ...)` for related table filtering |
| `whereNot` | `GET /search?filter=incomplete` | `NOT (condition)` for negation |
| `whereBetween` | `GET /search?filter=high_priority` | `BETWEEN low AND high` with `SqlInt32` bounds |
| `whereLike` | `GET /search?q=...` | `LIKE` pattern matching on title |
| `whereILike` | `GET /advanced` | Case-insensitive `ILIKE` pattern matching |
| `orderBy` | `GET /`, `GET /lists/{id}` | `ORDER BY` with ASC/DESC control |
| `orderByNulls` | `GET /search?filter=has_due_date`, `GET /search?filter=nulls_first` | `ORDER BY ... NULLS FIRST/LAST` |
| `groupBy` | `GET /advanced` | `GROUP BY` for aggregate grouping |
| `having` | `GET /advanced` | `HAVING` with count threshold |
| `orHaving` | `GET /advanced` | `OR HAVING` for compound group filters |
| `limit` | `GET /lists/{id}`, `GET /search?filter=page2` | `LIMIT n` for result capping |
| `offset` | `GET /search?filter=page2` | `OFFSET n` for pagination |
| `distinct` | `GET /advanced` | `SELECT DISTINCT` for unique values |
| `lock (ForUpdate)` | `GET /advanced` | `FOR UPDATE` row-level locking (SQL generation) |
| `lock (ForShare)` | `GET /advanced` | `FOR SHARE` row-level locking (SQL generation) |
| `countSql` | `GET /advanced` | `SELECT COUNT(*)` variant of a query |
| `safeToSql` | `GET /search?filter=safe_limit` | Query with enforced default limit |
| `toSql` | All routes | Convert query to `SqlFragment` for execution |

### Joins

| ORM Feature | Route / Location | Description |
|---|---|---|
| `innerJoin` | `GET /advanced` | Todos joined with lists for display |
| `leftJoin` | `GET /advanced` | Todos left-joined with todo_tags for tag counts |
| `rightJoin` | `GET /advanced` | SQL generation demo (RIGHT JOIN) |
| `fullJoin` | `GET /advanced` | SQL generation demo (FULL OUTER JOIN) |
| `crossJoin` | `GET /advanced` | SQL generation demo (CROSS JOIN) |

### Aggregates

| ORM Feature | Route / Location | Description |
|---|---|---|
| `countAll` | `GET /`, `GET /advanced` | `COUNT(*)` for total counts |
| `countCol` | `GET /advanced` | `COUNT(column)` for non-null counts |
| `sumCol` | `GET /advanced` | `SUM(priority)` across all todos |
| `avgCol` | `GET /advanced` | `AVG(priority)` average computation |
| `minCol` | `GET /advanced` | `MIN(priority)` minimum value |
| `maxCol` | `GET /advanced` | `MAX(priority)` maximum value |

### Set Operations

| ORM Feature | Route / Location | Description |
|---|---|---|
| `unionSql` | `GET /advanced` | UNION of high-priority and completed queries |
| `unionAllSql` | `GET /advanced` | UNION ALL (preserves duplicates) |
| `intersectSql` | `GET /advanced` | INTERSECT -- both high-priority AND completed |
| `exceptSql` | `GET /advanced` | EXCEPT -- high-priority but NOT completed |

### Subqueries

| ORM Feature | Route / Location | Description |
|---|---|---|
| `subquery` | `GET /advanced` | Wrap query as derived table with alias |
| `existsSql` | `GET /advanced` | `EXISTS (SELECT ...)` for existence checks |

### Mutations

| ORM Feature | Route / Location | Description |
|---|---|---|
| `update().set().where().toSql()` | `POST /todos/{id}/toggle`, `POST /todos/{id}/priority` | UPDATE with `SqlInt32` values |
| `deleteFrom().where().toSql()` | `POST /todos/{id}/delete`, `POST /tags/{id}/delete` | DELETE with WHERE conditions |
| `deleteSql` | `POST /lists/{id}/delete`, `POST /tags/{id}/delete` | Quick DELETE by primary key ID |

### Changeset Validation

| ORM Feature | Route / Location | Description |
|---|---|---|
| `changeset` | `POST /lists`, `POST /lists/{id}/todos`, `POST /tags` | Create changeset from TableDef + params |
| `cast` | All POST routes | Whitelist allowed fields |
| `validateRequired` | `POST /lists`, `POST /lists/{id}/todos` | Non-empty field check |
| `validateLength` | `POST /lists`, `POST /lists/{id}/todos`, `POST /tags` | Min/max string length |
| `validateInt` | `POST /lists/{id}/todos` | Integer parsing validation |
| `validateInt64` | `GET /advanced` (comprehensive demo) | 64-bit integer validation |
| `validateFloat` | `GET /advanced` (comprehensive demo) | Float parsing validation |
| `validateBool` | `GET /advanced` (comprehensive demo) | Boolean parsing validation |
| `validateInclusion` | `POST /lists/{id}/todos`, `GET /advanced` | Value must be in allowed list |
| `validateExclusion` | `POST /tags`, `GET /advanced` | Value must NOT be in blocked list |
| `validateNumber` | `POST /lists/{id}/todos` | Numeric range with `NumberValidationOpts` |
| `validateAcceptance` | `GET /advanced` (comprehensive demo) | Must be truthy ("true", "1", "yes") |
| `validateConfirmation` | `GET /advanced` (comprehensive demo) | Two fields must match |
| `validateContains` | `GET /advanced` (comprehensive demo) | String must contain substring |
| `validateStartsWith` | `GET /advanced` (comprehensive demo) | String must start with prefix |
| `validateEndsWith` | `GET /advanced` (comprehensive demo) | String must end with suffix |
| `putChange` | `POST /lists` (description), `POST /lists/{id}/todos` (dueDate) | Programmatically set a changeset value |
| `getChange` | `TodoRepository.updateListName()` | Read back a changeset value |
| `deleteChange` | `TodoRepository.validateComprehensive()` | Remove a change from the changeset |
| `toInsertSql` | `POST /lists`, `POST /lists/{id}/todos`, `POST /tags` | Generate INSERT SQL |
| `toUpdateSql` | `POST /todos/{id}/edit` | Generate UPDATE SQL for a given ID |

### Types Used

| Type | Usage |
|---|---|
| `SafeIdentifier` | Every table and column name |
| `TableDef` | Schema for `lists`, `todos`, `tags`, `todo_tags` |
| `FieldDef` | Each column in every table |
| `StringField` | `name`, `title`, `description`, `due_date`, `created_at` |
| `IntField` | `completed`, `priority`, `list_id`, `todo_id`, `tag_id` |
| `SqlBuilder` | Manual fragment construction (join conditions, WHERE clauses) |
| `SqlFragment` | Immutable SQL fragments from `toSql()` |
| `SqlString` | Parameterized string values in SET clauses |
| `SqlInt32` | Parameterized integer values (priority, completed toggle) |
| `SqlInt64` | 64-bit integer demo in comprehensive validation |
| `SqlFloat64` | Float demo in orHaving condition |
| `SqlBoolean` | Boolean demo in comprehensive validation |
| `SqlDate` | Date type (available, used via string representation) |
| `SqlDefault` | SQL DEFAULT keyword type |
| `SqlSource` | Raw safe SQL source fragments |
| `NumberValidationOpts` | Priority range validation (greaterThan, lessThan, gte, lte, equalTo) |
| `NullsFirst` | NULLS FIRST ordering mode |
| `NullsLast` | NULLS LAST ordering mode |
| `ForUpdate` | FOR UPDATE lock mode |
| `ForShare` | FOR SHARE lock mode |

## Route Reference

| Route | Method | Description | Key ORM Features |
|---|---|---|---|
| `/` | GET | Lists index with todo counts | `from`, `orderBy`, `selectExpr`, `countAll`, `col` |
| `/lists` | POST | Create a new list | `changeset`, `cast`, `validateRequired`, `validateLength`, `putChange`, `toInsertSql` |
| `/lists/{id}` | GET | Show list with todos | `from`, `where`, `limit`, `orderBy`, `toSql` |
| `/lists/{id}/delete` | POST | Delete list and cascade | `deleteSql`, `deleteFrom().where()` |
| `/lists/{id}/todos` | POST | Create todo with full validation | `changeset`, `cast`, `validateRequired`, `validateInt`, `validateNumber`, `validateInclusion`, `validateLength`, `putChange`, `toInsertSql` |
| `/todos/{id}/toggle` | POST | Toggle completed state | `update().set().where()`, `SqlInt32` |
| `/todos/{id}/edit` | POST | Edit todo title | `changeset`, `cast`, `validateRequired`, `validateLength`, `toUpdateSql` |
| `/todos/{id}/priority` | POST | Update priority | `update().set().where()`, `SqlInt32` |
| `/todos/{id}/delete` | POST | Delete a todo | `deleteFrom().where()`, `deleteSql` |
| `/tags` | GET | List all tags | `from`, `orderBy`, `toSql` |
| `/tags` | POST | Create a tag | `changeset`, `cast`, `validateRequired`, `validateLength`, `validateExclusion`, `toInsertSql` |
| `/tags/{id}/delete` | POST | Delete a tag | `deleteSql`, `deleteFrom().where()` |
| `/todos/{todoId}/tags/{tagId}` | POST | Tag a todo | `changeset`, `validateRequired`, `validateInt`, `toInsertSql` |
| `/todos/{todoId}/tags/{tagId}/remove` | POST | Untag a todo | `deleteFrom().where()` |
| `/search` | GET | Search with 10 filter modes | `whereLike`, `whereNull`, `whereNotNull`, `whereBetween`, `whereIn`, `whereInSubquery`, `whereNot`, `orWhere`, `distinct`, `limit`, `offset`, `orderByNulls`, `safeToSql` |
| `/advanced` | GET | Full ORM feature showcase | All 5 join types, all 6 aggregates, all 4 set ops, `subquery`, `existsSql`, `lock`, `groupBy`, `having`, `orHaving`, comprehensive changeset validation |

## Code Examples

### Query Builder -- Inner Join with Aggregates

```java
// From TodoRepository.java -- find todos with list names using innerJoin + col()
SqlFragment joinCond = buildJoinCondition(todosTable, listIdField, listsTable, idField);

SqlFragment todoTitle = SrcGlobal.col(todosTable, titleField);
SqlFragment listName = SrcGlobal.col(listsTable, nameField);

String sql = SrcGlobal.from(todosTable)
        .selectExpr(List.of(todoTitle, listName, SrcGlobal.countAll()))
        .innerJoin(listsTable, joinCond)
        .groupBy(listIdField)
        .having(havingCond.getAccumulated())
        .orHaving(orHavingCond.getAccumulated())
        .orderBy(priorityField, true)
        .toSql()
        .toString();
```

### Changeset -- Full Validation Pipeline

```java
// From TodoRepository.java -- insert a todo with every validator
NumberValidationOpts priorityOpts = new NumberValidationOpts(null, null, 1.0, 5.0, null);

Changeset cs = SrcGlobal.changeset(todoTableDef, params)
        .cast(castFields)
        .validateRequired(requiredFields)
        .validateLength(titleField, 1, 500)
        .validateInt(priorityField)
        .validateNumber(priorityField, priorityOpts)
        .validateInt(completedField)
        .validateInclusion(completedField, List.of("0", "1"));

if (dueDate != null && !dueDate.trim().isEmpty()) {
    cs = cs.putChange(dueDateField, dueDate.trim());
}

if (cs.isValid()) {
    jdbc.execute(cs.toInsertSql().toString());
}
```

### Set Operations -- Union, Intersect, Except

```java
// From TodoRepository.java -- generate UNION of two filtered queries
Query q1 = SrcGlobal.from(todosTable).where(highPri.getAccumulated());
Query q2 = SrcGlobal.from(todosTable).where(completed.getAccumulated());

String unionSql     = SrcGlobal.unionSql(q1, q2).toString();
String intersectSql = SrcGlobal.intersectSql(q1, q2).toString();
String exceptSql    = SrcGlobal.exceptSql(q1, q2).toString();
```

### Subquery + EXISTS

```java
// From TodoRepository.java -- find lists that have at least one todo
Query existsQuery = SrcGlobal.from(todosTable).where(listIdMatch);
SqlFragment existsCond = SrcGlobal.existsSql(existsQuery);

String sql = SrcGlobal.from(listsTable)
        .where(existsCond)
        .orderBy(nameField, true)
        .toSql()
        .toString();
```

## Security Model

The Alloy ORM enforces **5 layers of defense** against SQL injection and mass-assignment attacks:

1. **SafeIdentifier** -- Table and column names are validated against `[a-zA-Z_][a-zA-Z0-9_]*` at construction time. Invalid identifiers throw immediately.
2. **SqlBuilder / SqlFragment** -- All values are type-dispatched through a sealed `SqlPart` hierarchy (`SqlString`, `SqlInt32`, `SqlFloat64`, etc.) that handles escaping per type. No string interpolation.
3. **Changeset `cast()`** -- Field whitelisting prevents mass-assignment (CWE-915). Only explicitly listed fields are accepted from user input.
4. **Changeset validators** -- 15 validators catch invalid data before any SQL is generated. Invalid changesets refuse to produce SQL.
5. **No raw SQL with user input** -- DDL (`CREATE TABLE`) is the only raw SQL in the codebase, using static strings with zero user input.

For the full MITRE CWE analysis, see [SECURITY_ANALYSIS.md](SECURITY_ANALYSIS.md) or the [main Alloy repository](https://github.com/notactuallytreyanastasio/alloy).

## Running the App

### Prerequisites

- Java 17+
- Maven 3.8+

### Install and Run

```bash
mvn spring-boot:run
```

The app starts at **http://localhost:5004** with seeded sample data (3 lists, 15 todos, 5 tags).

## Source

- **Alloy ORM (Temper source):** [github.com/notactuallytreyanastasio/alloy](https://github.com/notactuallytreyanastasio/alloy)
- **Compiled Java library:** [github.com/notactuallytreyanastasio/alloy-java](https://github.com/notactuallytreyanastasio/alloy-java)

The `vendor/` directory contains the ORM compiled from Temper to Java source files. Maven includes them via `build-helper-maven-plugin`. Updated automatically by CI when the ORM source changes.

---

Part of the [Alloy](https://github.com/notactuallytreyanastasio/alloy) project -- write once in Temper, compile to Java/Python/Lua/JS, secure everywhere.
