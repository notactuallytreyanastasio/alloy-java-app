package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__601, Map<String, String> params__602) {
        Map<String, String> t_9441 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__601, params__602, t_9441, List.of(), true);
    }
    static boolean isIdentStart__462(int c__1121) {
        boolean return__387;
        boolean t_5298;
        boolean t_5299;
        if (c__1121 >= 97) {
            t_5298 = c__1121 <= 122;
        } else {
            t_5298 = false;
        }
        if (t_5298) {
            return__387 = true;
        } else {
            if (c__1121 >= 65) {
                t_5299 = c__1121 <= 90;
            } else {
                t_5299 = false;
            }
            if (t_5299) {
                return__387 = true;
            } else {
                return__387 = c__1121 == 95;
            }
        }
        return return__387;
    }
    static boolean isIdentPart__463(int c__1123) {
        boolean return__388;
        if (SrcGlobal.isIdentStart__462(c__1123)) {
            return__388 = true;
        } else if (c__1123 >= 48) {
            return__388 = c__1123 <= 57;
        } else {
            return__388 = false;
        }
        return return__388;
    }
    public static SafeIdentifier safeIdentifier(String name__1125) {
        int t_9439;
        if (name__1125.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1127 = 0;
        if (!SrcGlobal.isIdentStart__462(name__1125.codePointAt(idx__1127))) {
            throw Core.bubble();
        }
        int t_9436 = Core.stringNext(name__1125, idx__1127);
        idx__1127 = t_9436;
        while (true) {
            if (!Core.stringHasIndex(name__1125, idx__1127)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__463(name__1125.codePointAt(idx__1127))) {
                throw Core.bubble();
            }
            t_9439 = Core.stringNext(name__1125, idx__1127);
            idx__1127 = t_9439;
        }
        return new ValidatedIdentifier(name__1125);
    }
    public static SqlFragment deleteSql(TableDef tableDef__691, int id__692) {
        SqlBuilder b__694 = new SqlBuilder();
        b__694.appendSafe("DELETE FROM ");
        b__694.appendSafe(tableDef__691.getTableName().getSqlValue());
        b__694.appendSafe(" WHERE id = ");
        b__694.appendInt32(id__692);
        return b__694.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__886) {
        return new Query(tableName__886, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of());
    }
    public static SqlFragment col(SafeIdentifier table__888, SafeIdentifier column__889) {
        SqlBuilder b__891 = new SqlBuilder();
        b__891.appendSafe(table__888.getSqlValue());
        b__891.appendSafe(".");
        b__891.appendSafe(column__889.getSqlValue());
        return b__891.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__893 = new SqlBuilder();
        b__893.appendSafe("COUNT(*)");
        return b__893.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__894) {
        SqlBuilder b__896 = new SqlBuilder();
        b__896.appendSafe("COUNT(");
        b__896.appendSafe(field__894.getSqlValue());
        b__896.appendSafe(")");
        return b__896.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__897) {
        SqlBuilder b__899 = new SqlBuilder();
        b__899.appendSafe("SUM(");
        b__899.appendSafe(field__897.getSqlValue());
        b__899.appendSafe(")");
        return b__899.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__900) {
        SqlBuilder b__902 = new SqlBuilder();
        b__902.appendSafe("AVG(");
        b__902.appendSafe(field__900.getSqlValue());
        b__902.appendSafe(")");
        return b__902.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__903) {
        SqlBuilder b__905 = new SqlBuilder();
        b__905.appendSafe("MIN(");
        b__905.appendSafe(field__903.getSqlValue());
        b__905.appendSafe(")");
        return b__905.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__906) {
        SqlBuilder b__908 = new SqlBuilder();
        b__908.appendSafe("MAX(");
        b__908.appendSafe(field__906.getSqlValue());
        b__908.appendSafe(")");
        return b__908.getAccumulated();
    }
    public static SqlFragment unionSql(Query a__909, Query b__910) {
        SqlBuilder sb__912 = new SqlBuilder();
        sb__912.appendSafe("(");
        sb__912.appendFragment(a__909.toSql());
        sb__912.appendSafe(") UNION (");
        sb__912.appendFragment(b__910.toSql());
        sb__912.appendSafe(")");
        return sb__912.getAccumulated();
    }
    public static SqlFragment unionAllSql(Query a__913, Query b__914) {
        SqlBuilder sb__916 = new SqlBuilder();
        sb__916.appendSafe("(");
        sb__916.appendFragment(a__913.toSql());
        sb__916.appendSafe(") UNION ALL (");
        sb__916.appendFragment(b__914.toSql());
        sb__916.appendSafe(")");
        return sb__916.getAccumulated();
    }
    public static SqlFragment intersectSql(Query a__917, Query b__918) {
        SqlBuilder sb__920 = new SqlBuilder();
        sb__920.appendSafe("(");
        sb__920.appendFragment(a__917.toSql());
        sb__920.appendSafe(") INTERSECT (");
        sb__920.appendFragment(b__918.toSql());
        sb__920.appendSafe(")");
        return sb__920.getAccumulated();
    }
    public static SqlFragment exceptSql(Query a__921, Query b__922) {
        SqlBuilder sb__924 = new SqlBuilder();
        sb__924.appendSafe("(");
        sb__924.appendFragment(a__921.toSql());
        sb__924.appendSafe(") EXCEPT (");
        sb__924.appendFragment(b__922.toSql());
        sb__924.appendSafe(")");
        return sb__924.getAccumulated();
    }
    public static SqlFragment subquery(Query q__925, SafeIdentifier alias__926) {
        SqlBuilder b__928 = new SqlBuilder();
        b__928.appendSafe("(");
        b__928.appendFragment(q__925.toSql());
        b__928.appendSafe(") AS ");
        b__928.appendSafe(alias__926.getSqlValue());
        return b__928.getAccumulated();
    }
    public static SqlFragment existsSql(Query q__929) {
        SqlBuilder b__931 = new SqlBuilder();
        b__931.appendSafe("EXISTS (");
        b__931.appendFragment(q__929.toSql());
        b__931.appendSafe(")");
        return b__931.getAccumulated();
    }
}
