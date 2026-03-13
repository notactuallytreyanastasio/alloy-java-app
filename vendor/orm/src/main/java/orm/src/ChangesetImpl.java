package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.LinkedHashMap;
final class ChangesetImpl implements Changeset {
    final TableDef _tableDef;
    final Map<String, String> _params;
    final Map<String, String> _changes;
    final List<ChangesetError> _errors;
    final boolean _isValid;
    public TableDef getTableDef() {
        return this._tableDef;
    }
    public Map<String, String> getChanges() {
        return this._changes;
    }
    public List<ChangesetError> getErrors() {
        return this._errors;
    }
    public boolean isValid() {
        return this._isValid;
    }
    public Changeset cast(List<SafeIdentifier> allowedFields__364) {
        Map<String, String> mb__366 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__5014 = f__367 -> {
            String t_5012;
            String t_5009 = f__367.getSqlValue();
            String val__368 = this._params.getOrDefault(t_5009, "");
            if (!val__368.isEmpty()) {
                t_5012 = f__367.getSqlValue();
                mb__366.put(t_5012, val__368);
            }
        };
        allowedFields__364.forEach(fn__5014);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__366), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__370) {
        Changeset return__192;
        List<ChangesetError> t_5007;
        TableDef t_2977;
        Map<String, String> t_2978;
        Map<String, String> t_2979;
        fn__371: {
            if (!this._isValid) {
                return__192 = this;
                break fn__371;
            }
            List<ChangesetError> eb__372 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__373 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__5003 = f__374 -> {
                ChangesetError t_5001;
                String t_4998 = f__374.getSqlValue();
                if (!this._changes.containsKey(t_4998)) {
                    t_5001 = new ChangesetError(f__374.getSqlValue(), "is required");
                    Core.listAdd(eb__372, t_5001);
                    local$1.valid__373 = false;
                }
            };
            fields__370.forEach(fn__5003);
            t_2977 = this._tableDef;
            t_2978 = this._params;
            t_2979 = this._changes;
            t_5007 = List.copyOf(eb__372);
            return__192 = new ChangesetImpl(t_2977, t_2978, t_2979, t_5007, local$1.valid__373);
        }
        return return__192;
    }
    public Changeset validateLength(SafeIdentifier field__376, int min__377, int max__378) {
        Changeset return__193;
        String t_4985;
        List<ChangesetError> t_4996;
        boolean t_2960;
        TableDef t_2966;
        Map<String, String> t_2967;
        Map<String, String> t_2968;
        fn__379: {
            if (!this._isValid) {
                return__193 = this;
                break fn__379;
            }
            t_4985 = field__376.getSqlValue();
            String val__380 = this._changes.getOrDefault(t_4985, "");
            int len__381 = Core.stringCountBetween(val__380, 0, val__380.length());
            if (len__381 < min__377) {
                t_2960 = true;
            } else {
                t_2960 = len__381 > max__378;
            }
            if (t_2960) {
                String msg__382 = "must be between " + Integer.toString(min__377) + " and " + Integer.toString(max__378) + " characters";
                List<ChangesetError> eb__383 = new ArrayList<>(this._errors);
                Core.listAdd(eb__383, new ChangesetError(field__376.getSqlValue(), msg__382));
                t_2966 = this._tableDef;
                t_2967 = this._params;
                t_2968 = this._changes;
                t_4996 = List.copyOf(eb__383);
                return__193 = new ChangesetImpl(t_2966, t_2967, t_2968, t_4996, false);
                break fn__379;
            }
            return__193 = this;
        }
        return return__193;
    }
    public Changeset validateInt(SafeIdentifier field__385) {
        Changeset return__194;
        String t_4976;
        List<ChangesetError> t_4983;
        TableDef t_2951;
        Map<String, String> t_2952;
        Map<String, String> t_2953;
        fn__386: {
            if (!this._isValid) {
                return__194 = this;
                break fn__386;
            }
            t_4976 = field__385.getSqlValue();
            String val__387 = this._changes.getOrDefault(t_4976, "");
            if (val__387.isEmpty()) {
                return__194 = this;
                break fn__386;
            }
            boolean parseOk__388;
            boolean parseOk_5121;
            try {
                Core.stringToInt(val__387);
                parseOk_5121 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_5121 = false;
            }
            parseOk__388 = parseOk_5121;
            if (!parseOk__388) {
                List<ChangesetError> eb__389 = new ArrayList<>(this._errors);
                Core.listAdd(eb__389, new ChangesetError(field__385.getSqlValue(), "must be an integer"));
                t_2951 = this._tableDef;
                t_2952 = this._params;
                t_2953 = this._changes;
                t_4983 = List.copyOf(eb__389);
                return__194 = new ChangesetImpl(t_2951, t_2952, t_2953, t_4983, false);
                break fn__386;
            }
            return__194 = this;
        }
        return return__194;
    }
    public Changeset validateInt64(SafeIdentifier field__391) {
        Changeset return__195;
        String t_4967;
        List<ChangesetError> t_4974;
        TableDef t_2938;
        Map<String, String> t_2939;
        Map<String, String> t_2940;
        fn__392: {
            if (!this._isValid) {
                return__195 = this;
                break fn__392;
            }
            t_4967 = field__391.getSqlValue();
            String val__393 = this._changes.getOrDefault(t_4967, "");
            if (val__393.isEmpty()) {
                return__195 = this;
                break fn__392;
            }
            boolean parseOk__394;
            boolean parseOk_5123;
            try {
                Core.stringToInt64(val__393);
                parseOk_5123 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_5123 = false;
            }
            parseOk__394 = parseOk_5123;
            if (!parseOk__394) {
                List<ChangesetError> eb__395 = new ArrayList<>(this._errors);
                Core.listAdd(eb__395, new ChangesetError(field__391.getSqlValue(), "must be a 64-bit integer"));
                t_2938 = this._tableDef;
                t_2939 = this._params;
                t_2940 = this._changes;
                t_4974 = List.copyOf(eb__395);
                return__195 = new ChangesetImpl(t_2938, t_2939, t_2940, t_4974, false);
                break fn__392;
            }
            return__195 = this;
        }
        return return__195;
    }
    public Changeset validateFloat(SafeIdentifier field__397) {
        Changeset return__196;
        String t_4958;
        List<ChangesetError> t_4965;
        TableDef t_2925;
        Map<String, String> t_2926;
        Map<String, String> t_2927;
        fn__398: {
            if (!this._isValid) {
                return__196 = this;
                break fn__398;
            }
            t_4958 = field__397.getSqlValue();
            String val__399 = this._changes.getOrDefault(t_4958, "");
            if (val__399.isEmpty()) {
                return__196 = this;
                break fn__398;
            }
            boolean parseOk__400;
            boolean parseOk_5125;
            try {
                Core.stringToFloat64(val__399);
                parseOk_5125 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_5125 = false;
            }
            parseOk__400 = parseOk_5125;
            if (!parseOk__400) {
                List<ChangesetError> eb__401 = new ArrayList<>(this._errors);
                Core.listAdd(eb__401, new ChangesetError(field__397.getSqlValue(), "must be a number"));
                t_2925 = this._tableDef;
                t_2926 = this._params;
                t_2927 = this._changes;
                t_4965 = List.copyOf(eb__401);
                return__196 = new ChangesetImpl(t_2925, t_2926, t_2927, t_4965, false);
                break fn__398;
            }
            return__196 = this;
        }
        return return__196;
    }
    public Changeset validateBool(SafeIdentifier field__403) {
        Changeset return__197;
        String t_4949;
        List<ChangesetError> t_4956;
        boolean t_2900;
        boolean t_2901;
        boolean t_2903;
        boolean t_2904;
        boolean t_2906;
        TableDef t_2912;
        Map<String, String> t_2913;
        Map<String, String> t_2914;
        fn__404: {
            if (!this._isValid) {
                return__197 = this;
                break fn__404;
            }
            t_4949 = field__403.getSqlValue();
            String val__405 = this._changes.getOrDefault(t_4949, "");
            if (val__405.isEmpty()) {
                return__197 = this;
                break fn__404;
            }
            boolean isTrue__406;
            if (val__405.equals("true")) {
                isTrue__406 = true;
            } else {
                if (val__405.equals("1")) {
                    t_2901 = true;
                } else {
                    if (val__405.equals("yes")) {
                        t_2900 = true;
                    } else {
                        t_2900 = val__405.equals("on");
                    }
                    t_2901 = t_2900;
                }
                isTrue__406 = t_2901;
            }
            boolean isFalse__407;
            if (val__405.equals("false")) {
                isFalse__407 = true;
            } else {
                if (val__405.equals("0")) {
                    t_2904 = true;
                } else {
                    if (val__405.equals("no")) {
                        t_2903 = true;
                    } else {
                        t_2903 = val__405.equals("off");
                    }
                    t_2904 = t_2903;
                }
                isFalse__407 = t_2904;
            }
            if (!isTrue__406) {
                t_2906 = !isFalse__407;
            } else {
                t_2906 = false;
            }
            if (t_2906) {
                List<ChangesetError> eb__408 = new ArrayList<>(this._errors);
                Core.listAdd(eb__408, new ChangesetError(field__403.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_2912 = this._tableDef;
                t_2913 = this._params;
                t_2914 = this._changes;
                t_4956 = List.copyOf(eb__408);
                return__197 = new ChangesetImpl(t_2912, t_2913, t_2914, t_4956, false);
                break fn__404;
            }
            return__197 = this;
        }
        return return__197;
    }
    SqlBoolean parseBoolSqlPart(String val__410) {
        SqlBoolean return__198;
        boolean t_2889;
        boolean t_2890;
        boolean t_2891;
        boolean t_2893;
        boolean t_2894;
        boolean t_2895;
        fn__411: {
            if (val__410.equals("true")) {
                t_2891 = true;
            } else {
                if (val__410.equals("1")) {
                    t_2890 = true;
                } else {
                    if (val__410.equals("yes")) {
                        t_2889 = true;
                    } else {
                        t_2889 = val__410.equals("on");
                    }
                    t_2890 = t_2889;
                }
                t_2891 = t_2890;
            }
            if (t_2891) {
                return__198 = new SqlBoolean(true);
                break fn__411;
            }
            if (val__410.equals("false")) {
                t_2895 = true;
            } else {
                if (val__410.equals("0")) {
                    t_2894 = true;
                } else {
                    if (val__410.equals("no")) {
                        t_2893 = true;
                    } else {
                        t_2893 = val__410.equals("off");
                    }
                    t_2894 = t_2893;
                }
                t_2895 = t_2894;
            }
            if (t_2895) {
                return__198 = new SqlBoolean(false);
                break fn__411;
            }
            throw Core.bubble();
        }
        return return__198;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__413, String val__414) {
        SqlPart return__199;
        int t_2876;
        long t_2879;
        double t_2882;
        LocalDate t_2887;
        fn__415: {
            FieldType ft__416 = fieldDef__413.getFieldType();
            if (ft__416 instanceof StringField) {
                return__199 = new SqlString(val__414);
                break fn__415;
            }
            if (ft__416 instanceof IntField) {
                t_2876 = Core.stringToInt(val__414);
                return__199 = new SqlInt32(t_2876);
                break fn__415;
            }
            if (ft__416 instanceof Int64Field) {
                t_2879 = Core.stringToInt64(val__414);
                return__199 = new SqlInt64(t_2879);
                break fn__415;
            }
            if (ft__416 instanceof FloatField) {
                t_2882 = Core.stringToFloat64(val__414);
                return__199 = new SqlFloat64(t_2882);
                break fn__415;
            }
            if (ft__416 instanceof BoolField) {
                return__199 = this.parseBoolSqlPart(val__414);
                break fn__415;
            }
            if (ft__416 instanceof DateField) {
                t_2887 = LocalDate.parse(val__414);
                return__199 = new SqlDate(t_2887);
                break fn__415;
            }
            throw Core.bubble();
        }
        return return__199;
    }
    public SqlFragment toInsertSql() {
        int t_4897;
        String t_4902;
        boolean t_4903;
        int t_4908;
        String t_4910;
        String t_4914;
        int t_4929;
        boolean t_2840;
        FieldDef t_2848;
        SqlPart t_2853;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__419 = 0;
        while (true) {
            t_4897 = this._tableDef.getFields().size();
            if (i__419 >= t_4897) {
                break;
            }
            FieldDef f__420 = Core.listGet(this._tableDef.getFields(), i__419);
            if (!f__420.isNullable()) {
                t_4902 = f__420.getName().getSqlValue();
                t_4903 = this._changes.containsKey(t_4902);
                t_2840 = !t_4903;
            } else {
                t_2840 = false;
            }
            if (t_2840) {
                throw Core.bubble();
            }
            i__419 = i__419 + 1;
        }
        List<Entry<String, String>> pairs__421 = Core.mappedToList(this._changes);
        if (pairs__421.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__422 = new ArrayList<>();
        List<SqlPart> valParts__423 = new ArrayList<>();
        int i__424 = 0;
        while (true) {
            t_4908 = pairs__421.size();
            if (i__424 >= t_4908) {
                break;
            }
            Entry<String, String> pair__425 = Core.listGet(pairs__421, i__424);
            t_4910 = pair__425.getKey();
            t_2848 = this._tableDef.field(t_4910);
            FieldDef fd__426 = t_2848;
            Core.listAdd(colNames__422, fd__426.getName().getSqlValue());
            t_4914 = pair__425.getValue();
            t_2853 = this.valueToSqlPart(fd__426, t_4914);
            Core.listAdd(valParts__423, t_2853);
            i__424 = i__424 + 1;
        }
        SqlBuilder b__427 = new SqlBuilder();
        b__427.appendSafe("INSERT INTO ");
        b__427.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__427.appendSafe(" (");
        List<String> t_4922 = List.copyOf(colNames__422);
        Function<String, String> fn__4895 = c__428 -> c__428;
        b__427.appendSafe(Core.listJoinObj(t_4922, ", ", fn__4895));
        b__427.appendSafe(") VALUES (");
        b__427.appendPart(Core.listGet(valParts__423, 0));
        int j__429 = 1;
        while (true) {
            t_4929 = valParts__423.size();
            if (j__429 >= t_4929) {
                break;
            }
            b__427.appendSafe(", ");
            b__427.appendPart(Core.listGet(valParts__423, j__429));
            j__429 = j__429 + 1;
        }
        b__427.appendSafe(")");
        return b__427.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__431) {
        int t_4882;
        String t_4885;
        String t_4890;
        FieldDef t_2821;
        SqlPart t_2827;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__433 = Core.mappedToList(this._changes);
        if (pairs__433.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__434 = new SqlBuilder();
        b__434.appendSafe("UPDATE ");
        b__434.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__434.appendSafe(" SET ");
        int i__435 = 0;
        while (true) {
            t_4882 = pairs__433.size();
            if (i__435 >= t_4882) {
                break;
            }
            if (i__435 > 0) {
                b__434.appendSafe(", ");
            }
            Entry<String, String> pair__436 = Core.listGet(pairs__433, i__435);
            t_4885 = pair__436.getKey();
            t_2821 = this._tableDef.field(t_4885);
            FieldDef fd__437 = t_2821;
            b__434.appendSafe(fd__437.getName().getSqlValue());
            b__434.appendSafe(" = ");
            t_4890 = pair__436.getValue();
            t_2827 = this.valueToSqlPart(fd__437, t_4890);
            b__434.appendPart(t_2827);
            i__435 = i__435 + 1;
        }
        b__434.appendSafe(" WHERE id = ");
        b__434.appendInt32(id__431);
        return b__434.getAccumulated();
    }
    public static final class Builder {
        TableDef _tableDef;
        public Builder _tableDef(TableDef _tableDef) {
            this._tableDef = _tableDef;
            return this;
        }
        Map<String, String> _params;
        public Builder _params(Map<String, String> _params) {
            this._params = _params;
            return this;
        }
        Map<String, String> _changes;
        public Builder _changes(Map<String, String> _changes) {
            this._changes = _changes;
            return this;
        }
        List<ChangesetError> _errors;
        public Builder _errors(List<ChangesetError> _errors) {
            this._errors = _errors;
            return this;
        }
        boolean _isValid;
        boolean _isValid__set;
        public Builder _isValid(boolean _isValid) {
            _isValid__set = true;
            this._isValid = _isValid;
            return this;
        }
        public ChangesetImpl build() {
            if (!_isValid__set || _tableDef == null || _params == null || _changes == null || _errors == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!_isValid__set) {
                    _message.append(" _isValid");
                }
                if (_tableDef == null) {
                    _message.append(" _tableDef");
                }
                if (_params == null) {
                    _message.append(" _params");
                }
                if (_changes == null) {
                    _message.append(" _changes");
                }
                if (_errors == null) {
                    _message.append(" _errors");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new ChangesetImpl(_tableDef, _params, _changes, _errors, _isValid);
        }
    }
    public ChangesetImpl(TableDef _tableDef__439, Map<String, String> _params__440, Map<String, String> _changes__441, List<ChangesetError> _errors__442, boolean _isValid__443) {
        this._tableDef = _tableDef__439;
        this._params = _params__440;
        this._changes = _changes__441;
        this._errors = _errors__442;
        this._isValid = _isValid__443;
    }
}
