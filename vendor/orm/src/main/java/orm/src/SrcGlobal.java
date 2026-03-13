package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__583, Map<String, String> params__584) {
        Map<String, String> t_8567 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__583, params__584, t_8567, List.of(), true);
    }
    static boolean isIdentStart__444(int c__1037) {
        boolean return__369;
        boolean t_4824;
        boolean t_4825;
        if (c__1037 >= 97) {
            t_4824 = c__1037 <= 122;
        } else {
            t_4824 = false;
        }
        if (t_4824) {
            return__369 = true;
        } else {
            if (c__1037 >= 65) {
                t_4825 = c__1037 <= 90;
            } else {
                t_4825 = false;
            }
            if (t_4825) {
                return__369 = true;
            } else {
                return__369 = c__1037 == 95;
            }
        }
        return return__369;
    }
    static boolean isIdentPart__445(int c__1039) {
        boolean return__370;
        if (SrcGlobal.isIdentStart__444(c__1039)) {
            return__370 = true;
        } else if (c__1039 >= 48) {
            return__370 = c__1039 <= 57;
        } else {
            return__370 = false;
        }
        return return__370;
    }
    public static SafeIdentifier safeIdentifier(String name__1041) {
        int t_8565;
        if (name__1041.isEmpty()) {
            throw Core.bubble();
        }
        int idx__1043 = 0;
        if (!SrcGlobal.isIdentStart__444(name__1041.codePointAt(idx__1043))) {
            throw Core.bubble();
        }
        int t_8562 = Core.stringNext(name__1041, idx__1043);
        idx__1043 = t_8562;
        while (true) {
            if (!Core.stringHasIndex(name__1041, idx__1043)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__445(name__1041.codePointAt(idx__1043))) {
                throw Core.bubble();
            }
            t_8565 = Core.stringNext(name__1041, idx__1043);
            idx__1043 = t_8565;
        }
        return new ValidatedIdentifier(name__1041);
    }
    public static SqlFragment deleteSql(TableDef tableDef__673, int id__674) {
        SqlBuilder b__676 = new SqlBuilder();
        b__676.appendSafe("DELETE FROM ");
        b__676.appendSafe(tableDef__673.getTableName().getSqlValue());
        b__676.appendSafe(" WHERE id = ");
        b__676.appendInt32(id__674);
        return b__676.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__863) {
        return new Query(tableName__863, List.of(), List.of(), List.of(), null, null, List.of(), List.of(), List.of(), false, List.of());
    }
    public static SqlFragment col(SafeIdentifier table__865, SafeIdentifier column__866) {
        SqlBuilder b__868 = new SqlBuilder();
        b__868.appendSafe(table__865.getSqlValue());
        b__868.appendSafe(".");
        b__868.appendSafe(column__866.getSqlValue());
        return b__868.getAccumulated();
    }
    public static SqlFragment countAll() {
        SqlBuilder b__870 = new SqlBuilder();
        b__870.appendSafe("COUNT(*)");
        return b__870.getAccumulated();
    }
    public static SqlFragment countCol(SafeIdentifier field__871) {
        SqlBuilder b__873 = new SqlBuilder();
        b__873.appendSafe("COUNT(");
        b__873.appendSafe(field__871.getSqlValue());
        b__873.appendSafe(")");
        return b__873.getAccumulated();
    }
    public static SqlFragment sumCol(SafeIdentifier field__874) {
        SqlBuilder b__876 = new SqlBuilder();
        b__876.appendSafe("SUM(");
        b__876.appendSafe(field__874.getSqlValue());
        b__876.appendSafe(")");
        return b__876.getAccumulated();
    }
    public static SqlFragment avgCol(SafeIdentifier field__877) {
        SqlBuilder b__879 = new SqlBuilder();
        b__879.appendSafe("AVG(");
        b__879.appendSafe(field__877.getSqlValue());
        b__879.appendSafe(")");
        return b__879.getAccumulated();
    }
    public static SqlFragment minCol(SafeIdentifier field__880) {
        SqlBuilder b__882 = new SqlBuilder();
        b__882.appendSafe("MIN(");
        b__882.appendSafe(field__880.getSqlValue());
        b__882.appendSafe(")");
        return b__882.getAccumulated();
    }
    public static SqlFragment maxCol(SafeIdentifier field__883) {
        SqlBuilder b__885 = new SqlBuilder();
        b__885.appendSafe("MAX(");
        b__885.appendSafe(field__883.getSqlValue());
        b__885.appendSafe(")");
        return b__885.getAccumulated();
    }
}
