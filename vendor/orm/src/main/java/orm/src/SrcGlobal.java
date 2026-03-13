package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__950, Map<String, String> params__951) {
        Map<String, String> t_15116 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__950, params__951, t_15116, List.of(), true);
    }
    static boolean isIdentStart__663(int c__1768) {
        boolean return__581;
        boolean t_8366;
        boolean t_8367;
        if (c__1768 >= 97) {
            t_8366 = c__1768 <= 122;
        } else {
            t_8366 = false;
        }
        if (t_8366) {
            return__581 = true;
        } else {
            if (c__1768 >= 65) {
                t_8367 = c__1768 <= 90;
            } else {
                t_8367 = false;
            }
            if (t_8367) {
                return__581 = true;
            } else {
                return__581 = c__1768 == 95;
            }
        }
        return return__581;
    }
    static boolean isIdentPart__664(int c__1770) {
        boolean return__582;
        if (SrcGlobal.isIdentStart__663(c__1770)) {
            return__582 = true;
        } else if (c__1770 >= 48) {
            return__582 = c__1770 <= 57;
        } else {
            return__582 = false;
        }
        return return__582;
    }
    public static SafeIdentifier safeIdentifier(String name__1772) {
        int t_15114;
        if (name__1772.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1774 = 0;
        if (!SrcGlobal.isIdentStart__663(name__1772.codePointAt(idx__1774))) {
            throw Core.bubble();
        }
        int t_15111 = Core.stringNext(name__1772, idx__1774);
        idx__1774 = t_15111;
        while (true) {
            if (!Core.stringHasIndex(name__1772, idx__1774)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__664(name__1772.codePointAt(idx__1774))) {
                throw Core.bubble();
            }
            t_15114 = Core.stringNext(name__1772, idx__1774);
            idx__1774 = t_15114;
        }
        return new ValidatedIdentifier(name__1772);
    }
    public static List<FieldDef> timestamps() {
        SafeIdentifier t_7625;
        t_7625 = SrcGlobal.safeIdentifier("inserted_at");
        FieldDef t_14213 = new FieldDef(t_7625, new DateField(), true, new SqlDefault(), false);
        SafeIdentifier t_7629;
        t_7629 = SrcGlobal.safeIdentifier("updated_at");
        return List.of(t_14213, new FieldDef(t_7629, new DateField(), true, new SqlDefault(), false));
    }
    public static SqlFragment deleteSql(TableDef tableDef__1193, int id__1194) {
        SqlBuilder b__1196 = new SqlBuilder();
        b__1196.appendSafe("DELETE FROM ");
        b__1196.appendSafe(tableDef__1193.getTableName().getSqlValue());
        b__1196.appendSafe(" WHERE ");
        b__1196.appendSafe(tableDef__1193.pkName());
        b__1196.appendSafe(" = ");
        b__1196.appendInt32(id__1194);
        return b__1196.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__1428) {
        return new Query(tableName__1428, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of(), null);
    }
    public static SqlFragment col(SafeIdentifier table__1430, SafeIdentifier column__1431) {
        SqlBuilder b__1433 = new SqlBuilder();
        b__1433.appendSafe(table__1430.getSqlValue());
        b__1433.appendSafe(".");
        b__1433.appendSafe(column__1431.getSqlValue());
        return b__1433.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__1435 = new SqlBuilder();
        b__1435.appendSafe("COUNT(*)");
        return b__1435.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__1436) {
        SqlBuilder b__1438 = new SqlBuilder();
        b__1438.appendSafe("COUNT(");
        b__1438.appendSafe(field__1436.getSqlValue());
        b__1438.appendSafe(")");
        return b__1438.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__1439) {
        SqlBuilder b__1441 = new SqlBuilder();
        b__1441.appendSafe("SUM(");
        b__1441.appendSafe(field__1439.getSqlValue());
        b__1441.appendSafe(")");
        return b__1441.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__1442) {
        SqlBuilder b__1444 = new SqlBuilder();
        b__1444.appendSafe("AVG(");
        b__1444.appendSafe(field__1442.getSqlValue());
        b__1444.appendSafe(")");
        return b__1444.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__1445) {
        SqlBuilder b__1447 = new SqlBuilder();
        b__1447.appendSafe("MIN(");
        b__1447.appendSafe(field__1445.getSqlValue());
        b__1447.appendSafe(")");
        return b__1447.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__1448) {
        SqlBuilder b__1450 = new SqlBuilder();
        b__1450.appendSafe("MAX(");
        b__1450.appendSafe(field__1448.getSqlValue());
        b__1450.appendSafe(")");
        return b__1450.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__1451, Query b__1452) {
        SqlBuilder sb__1454 = new SqlBuilder();
        sb__1454.appendSafe("(");
        sb__1454.appendFragment(a__1451.toSql());
        sb__1454.appendSafe(") UNION (");
        sb__1454.appendFragment(b__1452.toSql());
        sb__1454.appendSafe(")");
        return sb__1454.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__1455, Query b__1456) {
        SqlBuilder sb__1458 = new SqlBuilder();
        sb__1458.appendSafe("(");
        sb__1458.appendFragment(a__1455.toSql());
        sb__1458.appendSafe(") UNION ALL (");
        sb__1458.appendFragment(b__1456.toSql());
        sb__1458.appendSafe(")");
        return sb__1458.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__1459, Query b__1460) {
        SqlBuilder sb__1462 = new SqlBuilder();
        sb__1462.appendSafe("(");
        sb__1462.appendFragment(a__1459.toSql());
        sb__1462.appendSafe(") INTERSECT (");
        sb__1462.appendFragment(b__1460.toSql());
        sb__1462.appendSafe(")");
        return sb__1462.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__1463, Query b__1464) {
        SqlBuilder sb__1466 = new SqlBuilder();
        sb__1466.appendSafe("(");
        sb__1466.appendFragment(a__1463.toSql());
        sb__1466.appendSafe(") EXCEPT (");
        sb__1466.appendFragment(b__1464.toSql());
        sb__1466.appendSafe(")");
        return sb__1466.getAccumulated();
    }
    public static SqlFragment subquery(Query q__1467, SafeIdentifier alias__1468) {
        SqlBuilder b__1470 = new SqlBuilder();
        b__1470.appendSafe("(");
        b__1470.appendFragment(q__1467.toSql());
        b__1470.appendSafe(") AS ");
        b__1470.appendSafe(alias__1468.getSqlValue());
        return b__1470.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__1471) {
        SqlBuilder b__1473 = new SqlBuilder();
        b__1473.appendSafe("EXISTS (");
        b__1473.appendFragment(q__1471.toSql());
        b__1473.appendSafe(")");
        return b__1473.getAccumulated();
    }
    public static UpdateQuery update(SafeIdentifier tableName__1533) {
        return new UpdateQuery(tableName__1533, List.of(), List.of(), null);
    }
    public static DeleteQuery deleteFrom(SafeIdentifier tableName__1535) {
        return new DeleteQuery(tableName__1535, List.of(), null);
    }
}
