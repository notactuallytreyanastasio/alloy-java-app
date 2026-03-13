package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__921, Map<String, String> params__922) {
        Map<String, String> t_13867 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__921, params__922, t_13867, List.of(), true);
    }
    static boolean isIdentStart__639(int c__1695) {
        boolean return__564;
        boolean t_7693;
        boolean t_7694;
        if (c__1695 >= 97) {
            t_7693 = c__1695 <= 122;
        } else {
            t_7693 = false;
        }
        if (t_7693) {
            return__564 = true;
        } else {
            if (c__1695 >= 65) {
                t_7694 = c__1695 <= 90;
            } else {
                t_7694 = false;
            }
            if (t_7694) {
                return__564 = true;
            } else {
                return__564 = c__1695 == 95;
            }
        }
        return return__564;
    }
    static boolean isIdentPart__640(int c__1697) {
        boolean return__565;
        if (SrcGlobal.isIdentStart__639(c__1697)) {
            return__565 = true;
        } else if (c__1697 >= 48) {
            return__565 = c__1697 <= 57;
        } else {
            return__565 = false;
        }
        return return__565;
    }
    public static SafeIdentifier safeIdentifier(String name__1699) {
        int t_13865;
        if (name__1699.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1701 = 0;
        if (!SrcGlobal.isIdentStart__639(name__1699.codePointAt(idx__1701))) {
            throw Core.bubble();
        }
        int t_13862 = Core.stringNext(name__1699, idx__1701);
        idx__1701 = t_13862;
        while (true) {
            if (!Core.stringHasIndex(name__1699, idx__1701)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__640(name__1699.codePointAt(idx__1701))) {
                throw Core.bubble();
            }
            t_13865 = Core.stringNext(name__1699, idx__1701);
            idx__1701 = t_13865;
        }
        return new ValidatedIdentifier(name__1699);
    }
    public static SqlFragment deleteSql(TableDef tableDef__1120, int id__1121) {
        SqlBuilder b__1123 = new SqlBuilder();
        b__1123.appendSafe("DELETE FROM ");
        b__1123.appendSafe(tableDef__1120.getTableName().getSqlValue());
        b__1123.appendSafe(" WHERE id = ");
        b__1123.appendInt32(id__1121);
        return b__1123.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__1355) {
        return new Query(tableName__1355, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of(), null);
    }
    public static SqlFragment col(SafeIdentifier table__1357, SafeIdentifier column__1358) {
        SqlBuilder b__1360 = new SqlBuilder();
        b__1360.appendSafe(table__1357.getSqlValue());
        b__1360.appendSafe(".");
        b__1360.appendSafe(column__1358.getSqlValue());
        return b__1360.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__1362 = new SqlBuilder();
        b__1362.appendSafe("COUNT(*)");
        return b__1362.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__1363) {
        SqlBuilder b__1365 = new SqlBuilder();
        b__1365.appendSafe("COUNT(");
        b__1365.appendSafe(field__1363.getSqlValue());
        b__1365.appendSafe(")");
        return b__1365.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__1366) {
        SqlBuilder b__1368 = new SqlBuilder();
        b__1368.appendSafe("SUM(");
        b__1368.appendSafe(field__1366.getSqlValue());
        b__1368.appendSafe(")");
        return b__1368.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__1369) {
        SqlBuilder b__1371 = new SqlBuilder();
        b__1371.appendSafe("AVG(");
        b__1371.appendSafe(field__1369.getSqlValue());
        b__1371.appendSafe(")");
        return b__1371.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__1372) {
        SqlBuilder b__1374 = new SqlBuilder();
        b__1374.appendSafe("MIN(");
        b__1374.appendSafe(field__1372.getSqlValue());
        b__1374.appendSafe(")");
        return b__1374.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__1375) {
        SqlBuilder b__1377 = new SqlBuilder();
        b__1377.appendSafe("MAX(");
        b__1377.appendSafe(field__1375.getSqlValue());
        b__1377.appendSafe(")");
        return b__1377.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__1378, Query b__1379) {
        SqlBuilder sb__1381 = new SqlBuilder();
        sb__1381.appendSafe("(");
        sb__1381.appendFragment(a__1378.toSql());
        sb__1381.appendSafe(") UNION (");
        sb__1381.appendFragment(b__1379.toSql());
        sb__1381.appendSafe(")");
        return sb__1381.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__1382, Query b__1383) {
        SqlBuilder sb__1385 = new SqlBuilder();
        sb__1385.appendSafe("(");
        sb__1385.appendFragment(a__1382.toSql());
        sb__1385.appendSafe(") UNION ALL (");
        sb__1385.appendFragment(b__1383.toSql());
        sb__1385.appendSafe(")");
        return sb__1385.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__1386, Query b__1387) {
        SqlBuilder sb__1389 = new SqlBuilder();
        sb__1389.appendSafe("(");
        sb__1389.appendFragment(a__1386.toSql());
        sb__1389.appendSafe(") INTERSECT (");
        sb__1389.appendFragment(b__1387.toSql());
        sb__1389.appendSafe(")");
        return sb__1389.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__1390, Query b__1391) {
        SqlBuilder sb__1393 = new SqlBuilder();
        sb__1393.appendSafe("(");
        sb__1393.appendFragment(a__1390.toSql());
        sb__1393.appendSafe(") EXCEPT (");
        sb__1393.appendFragment(b__1391.toSql());
        sb__1393.appendSafe(")");
        return sb__1393.getAccumulated();
    }
    public static SqlFragment subquery(Query q__1394, SafeIdentifier alias__1395) {
        SqlBuilder b__1397 = new SqlBuilder();
        b__1397.appendSafe("(");
        b__1397.appendFragment(q__1394.toSql());
        b__1397.appendSafe(") AS ");
        b__1397.appendSafe(alias__1395.getSqlValue());
        return b__1397.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__1398) {
        SqlBuilder b__1400 = new SqlBuilder();
        b__1400.appendSafe("EXISTS (");
        b__1400.appendFragment(q__1398.toSql());
        b__1400.appendSafe(")");
        return b__1400.getAccumulated();
    }
    public static UpdateQuery update(SafeIdentifier tableName__1460) {
        return new UpdateQuery(tableName__1460, List.of(), List.of(), null);
    }
    public static DeleteQuery deleteFrom(SafeIdentifier tableName__1462) {
        return new DeleteQuery(tableName__1462, List.of(), null);
    }
}
