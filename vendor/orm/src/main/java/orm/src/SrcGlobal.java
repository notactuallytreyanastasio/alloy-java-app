package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
public final class SrcGlobal {
    private SrcGlobal() {
    }
    public static Changeset changeset(TableDef tableDef__444, Map<String, String> params__445) {
        Map<String, String> t_4872 = Core.mapConstructor(List.of());
        return new ChangesetImpl(tableDef__444, params__445, t_4872, List.of(), true);
    }
    static boolean isIdentStart__305(int c__635) {
        boolean return__230;
        boolean t_2795;
        boolean t_2796;
        if (c__635 >= 97) {
            t_2795 = c__635 <= 122;
        } else {
            t_2795 = false;
        }
        if (t_2795) {
            return__230 = true;
        } else {
            if (c__635 >= 65) {
                t_2796 = c__635 <= 90;
            } else {
                t_2796 = false;
            }
            if (t_2796) {
                return__230 = true;
            } else {
                return__230 = c__635 == 95;
            }
        }
        return return__230;
    }
    static boolean isIdentPart__306(int c__637) {
        boolean return__231;
        if (SrcGlobal.isIdentStart__305(c__637)) {
            return__231 = true;
        } else if (c__637 >= 48) {
            return__231 = c__637 <= 57;
        } else {
            return__231 = false;
        }
        return return__231;
    }
    public static SafeIdentifier safeIdentifier(String name__639) {
        int t_4870;
        if (name__639.isEmpty()) {
            throw Core.bubble();
        }
        int idx__641 = 0;
        if (!SrcGlobal.isIdentStart__305(name__639.codePointAt(idx__641))) {
            throw Core.bubble();
        }
        int t_4867 = Core.stringNext(name__639, idx__641);
        idx__641 = t_4867;
        while (true) {
            if (!Core.stringHasIndex(name__639, idx__641)) {
                break;
            }
            if (!SrcGlobal.isIdentPart__306(name__639.codePointAt(idx__641))) {
                throw Core.bubble();
            }
            t_4870 = Core.stringNext(name__639, idx__641);
            idx__641 = t_4870;
        }
        return new ValidatedIdentifier(name__639);
    }
    public static SqlFragment deleteSql(TableDef tableDef__534, int id__535) {
        SqlBuilder b__537 = new SqlBuilder();
        b__537.appendSafe("DELETE FROM ");
        b__537.appendSafe(tableDef__534.getTableName().getSqlValue());
        b__537.appendSafe(" WHERE id = ");
        b__537.appendInt32(id__535);
        return b__537.getAccumulated();
    }
    public static Query from(SafeIdentifier tableName__586) {
        return new Query(tableName__586, List.of(), List.of(), List.of(), null, null);
    }
}
