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
    public Changeset cast(List<SafeIdentifier> allowedFields__503) {
        Map<String, String> mb__505 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__8709 = f__506 -> {
            String t_8707;
            String t_8704 = f__506.getSqlValue();
            String val__507 = this._params.getOrDefault(t_8704, "");
            if (!val__507.isEmpty()) {
                t_8707 = f__506.getSqlValue();
                mb__505.put(t_8707, val__507);
            }
        };
        allowedFields__503.forEach(fn__8709);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__505), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__509) {
        Changeset return__273;
        List<ChangesetError> t_8702;
        TableDef t_5006;
        Map<String, String> t_5007;
        Map<String, String> t_5008;
        fn__510: {
            if (!this._isValid) {
                return__273 = this;
                break fn__510;
            }
            List<ChangesetError> eb__511 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__512 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__8698 = f__513 -> {
                ChangesetError t_8696;
                String t_8693 = f__513.getSqlValue();
                if (!this._changes.containsKey(t_8693)) {
                    t_8696 = new ChangesetError(f__513.getSqlValue(), "is required");
                    Core.listAdd(eb__511, t_8696);
                    local$1.valid__512 = false;
                }
            };
            fields__509.forEach(fn__8698);
            t_5006 = this._tableDef;
            t_5007 = this._params;
            t_5008 = this._changes;
            t_8702 = List.copyOf(eb__511);
            return__273 = new ChangesetImpl(t_5006, t_5007, t_5008, t_8702, local$1.valid__512);
        }
        return return__273;
    }
    public Changeset validateLength(SafeIdentifier field__515, int min__516, int max__517) {
        Changeset return__274;
        String t_8680;
        List<ChangesetError> t_8691;
        boolean t_4989;
        TableDef t_4995;
        Map<String, String> t_4996;
        Map<String, String> t_4997;
        fn__518: {
            if (!this._isValid) {
                return__274 = this;
                break fn__518;
            }
            t_8680 = field__515.getSqlValue();
            String val__519 = this._changes.getOrDefault(t_8680, "");
            int len__520 = Core.stringCountBetween(val__519, 0, val__519.length());
            if (len__520 < min__516) {
                t_4989 = true;
            } else {
                t_4989 = len__520 > max__517;
            }
            if (t_4989) {
                String msg__521 = "must be between " + Integer.toString(min__516) + " and " + Integer.toString(max__517) + " characters";
                List<ChangesetError> eb__522 = new ArrayList<>(this._errors);
                Core.listAdd(eb__522, new ChangesetError(field__515.getSqlValue(), msg__521));
                t_4995 = this._tableDef;
                t_4996 = this._params;
                t_4997 = this._changes;
                t_8691 = List.copyOf(eb__522);
                return__274 = new ChangesetImpl(t_4995, t_4996, t_4997, t_8691, false);
                break fn__518;
            }
            return__274 = this;
        }
        return return__274;
    }
    public Changeset validateInt(SafeIdentifier field__524) {
        Changeset return__275;
        String t_8671;
        List<ChangesetError> t_8678;
        TableDef t_4980;
        Map<String, String> t_4981;
        Map<String, String> t_4982;
        fn__525: {
            if (!this._isValid) {
                return__275 = this;
                break fn__525;
            }
            t_8671 = field__524.getSqlValue();
            String val__526 = this._changes.getOrDefault(t_8671, "");
            if (val__526.isEmpty()) {
                return__275 = this;
                break fn__525;
            }
            boolean parseOk__527;
            boolean parseOk_8816;
            try {
                Core.stringToInt(val__526);
                parseOk_8816 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_8816 = false;
            }
            parseOk__527 = parseOk_8816;
            if (!parseOk__527) {
                List<ChangesetError> eb__528 = new ArrayList<>(this._errors);
                Core.listAdd(eb__528, new ChangesetError(field__524.getSqlValue(), "must be an integer"));
                t_4980 = this._tableDef;
                t_4981 = this._params;
                t_4982 = this._changes;
                t_8678 = List.copyOf(eb__528);
                return__275 = new ChangesetImpl(t_4980, t_4981, t_4982, t_8678, false);
                break fn__525;
            }
            return__275 = this;
        }
        return return__275;
    }
    public Changeset validateInt64(SafeIdentifier field__530) {
        Changeset return__276;
        String t_8662;
        List<ChangesetError> t_8669;
        TableDef t_4967;
        Map<String, String> t_4968;
        Map<String, String> t_4969;
        fn__531: {
            if (!this._isValid) {
                return__276 = this;
                break fn__531;
            }
            t_8662 = field__530.getSqlValue();
            String val__532 = this._changes.getOrDefault(t_8662, "");
            if (val__532.isEmpty()) {
                return__276 = this;
                break fn__531;
            }
            boolean parseOk__533;
            boolean parseOk_8818;
            try {
                Core.stringToInt64(val__532);
                parseOk_8818 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_8818 = false;
            }
            parseOk__533 = parseOk_8818;
            if (!parseOk__533) {
                List<ChangesetError> eb__534 = new ArrayList<>(this._errors);
                Core.listAdd(eb__534, new ChangesetError(field__530.getSqlValue(), "must be a 64-bit integer"));
                t_4967 = this._tableDef;
                t_4968 = this._params;
                t_4969 = this._changes;
                t_8669 = List.copyOf(eb__534);
                return__276 = new ChangesetImpl(t_4967, t_4968, t_4969, t_8669, false);
                break fn__531;
            }
            return__276 = this;
        }
        return return__276;
    }
    public Changeset validateFloat(SafeIdentifier field__536) {
        Changeset return__277;
        String t_8653;
        List<ChangesetError> t_8660;
        TableDef t_4954;
        Map<String, String> t_4955;
        Map<String, String> t_4956;
        fn__537: {
            if (!this._isValid) {
                return__277 = this;
                break fn__537;
            }
            t_8653 = field__536.getSqlValue();
            String val__538 = this._changes.getOrDefault(t_8653, "");
            if (val__538.isEmpty()) {
                return__277 = this;
                break fn__537;
            }
            boolean parseOk__539;
            boolean parseOk_8820;
            try {
                Core.stringToFloat64(val__538);
                parseOk_8820 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_8820 = false;
            }
            parseOk__539 = parseOk_8820;
            if (!parseOk__539) {
                List<ChangesetError> eb__540 = new ArrayList<>(this._errors);
                Core.listAdd(eb__540, new ChangesetError(field__536.getSqlValue(), "must be a number"));
                t_4954 = this._tableDef;
                t_4955 = this._params;
                t_4956 = this._changes;
                t_8660 = List.copyOf(eb__540);
                return__277 = new ChangesetImpl(t_4954, t_4955, t_4956, t_8660, false);
                break fn__537;
            }
            return__277 = this;
        }
        return return__277;
    }
    public Changeset validateBool(SafeIdentifier field__542) {
        Changeset return__278;
        String t_8644;
        List<ChangesetError> t_8651;
        boolean t_4929;
        boolean t_4930;
        boolean t_4932;
        boolean t_4933;
        boolean t_4935;
        TableDef t_4941;
        Map<String, String> t_4942;
        Map<String, String> t_4943;
        fn__543: {
            if (!this._isValid) {
                return__278 = this;
                break fn__543;
            }
            t_8644 = field__542.getSqlValue();
            String val__544 = this._changes.getOrDefault(t_8644, "");
            if (val__544.isEmpty()) {
                return__278 = this;
                break fn__543;
            }
            boolean isTrue__545;
            if (val__544.equals("true")) {
                isTrue__545 = true;
            } else {
                if (val__544.equals("1")) {
                    t_4930 = true;
                } else {
                    if (val__544.equals("yes")) {
                        t_4929 = true;
                    } else {
                        t_4929 = val__544.equals("on");
                    }
                    t_4930 = t_4929;
                }
                isTrue__545 = t_4930;
            }
            boolean isFalse__546;
            if (val__544.equals("false")) {
                isFalse__546 = true;
            } else {
                if (val__544.equals("0")) {
                    t_4933 = true;
                } else {
                    if (val__544.equals("no")) {
                        t_4932 = true;
                    } else {
                        t_4932 = val__544.equals("off");
                    }
                    t_4933 = t_4932;
                }
                isFalse__546 = t_4933;
            }
            if (!isTrue__545) {
                t_4935 = !isFalse__546;
            } else {
                t_4935 = false;
            }
            if (t_4935) {
                List<ChangesetError> eb__547 = new ArrayList<>(this._errors);
                Core.listAdd(eb__547, new ChangesetError(field__542.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_4941 = this._tableDef;
                t_4942 = this._params;
                t_4943 = this._changes;
                t_8651 = List.copyOf(eb__547);
                return__278 = new ChangesetImpl(t_4941, t_4942, t_4943, t_8651, false);
                break fn__543;
            }
            return__278 = this;
        }
        return return__278;
    }
    SqlBoolean parseBoolSqlPart(String val__549) {
        SqlBoolean return__279;
        boolean t_4918;
        boolean t_4919;
        boolean t_4920;
        boolean t_4922;
        boolean t_4923;
        boolean t_4924;
        fn__550: {
            if (val__549.equals("true")) {
                t_4920 = true;
            } else {
                if (val__549.equals("1")) {
                    t_4919 = true;
                } else {
                    if (val__549.equals("yes")) {
                        t_4918 = true;
                    } else {
                        t_4918 = val__549.equals("on");
                    }
                    t_4919 = t_4918;
                }
                t_4920 = t_4919;
            }
            if (t_4920) {
                return__279 = new SqlBoolean(true);
                break fn__550;
            }
            if (val__549.equals("false")) {
                t_4924 = true;
            } else {
                if (val__549.equals("0")) {
                    t_4923 = true;
                } else {
                    if (val__549.equals("no")) {
                        t_4922 = true;
                    } else {
                        t_4922 = val__549.equals("off");
                    }
                    t_4923 = t_4922;
                }
                t_4924 = t_4923;
            }
            if (t_4924) {
                return__279 = new SqlBoolean(false);
                break fn__550;
            }
            throw Core.bubble();
        }
        return return__279;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__552, String val__553) {
        SqlPart return__280;
        int t_4905;
        long t_4908;
        double t_4911;
        LocalDate t_4916;
        fn__554: {
            FieldType ft__555 = fieldDef__552.getFieldType();
            if (ft__555 instanceof StringField) {
                return__280 = new SqlString(val__553);
                break fn__554;
            }
            if (ft__555 instanceof IntField) {
                t_4905 = Core.stringToInt(val__553);
                return__280 = new SqlInt32(t_4905);
                break fn__554;
            }
            if (ft__555 instanceof Int64Field) {
                t_4908 = Core.stringToInt64(val__553);
                return__280 = new SqlInt64(t_4908);
                break fn__554;
            }
            if (ft__555 instanceof FloatField) {
                t_4911 = Core.stringToFloat64(val__553);
                return__280 = new SqlFloat64(t_4911);
                break fn__554;
            }
            if (ft__555 instanceof BoolField) {
                return__280 = this.parseBoolSqlPart(val__553);
                break fn__554;
            }
            if (ft__555 instanceof DateField) {
                t_4916 = LocalDate.parse(val__553);
                return__280 = new SqlDate(t_4916);
                break fn__554;
            }
            throw Core.bubble();
        }
        return return__280;
    }
    public SqlFragment toInsertSql() {
        int t_8592;
        String t_8597;
        boolean t_8598;
        int t_8603;
        String t_8605;
        String t_8609;
        int t_8624;
        boolean t_4869;
        FieldDef t_4877;
        SqlPart t_4882;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__558 = 0;
        while (true) {
            t_8592 = this._tableDef.getFields().size();
            if (i__558 >= t_8592) {
                break;
            }
            FieldDef f__559 = Core.listGet(this._tableDef.getFields(), i__558);
            if (!f__559.isNullable()) {
                t_8597 = f__559.getName().getSqlValue();
                t_8598 = this._changes.containsKey(t_8597);
                t_4869 = !t_8598;
            } else {
                t_4869 = false;
            }
            if (t_4869) {
                throw Core.bubble();
            }
            i__558 = i__558 + 1;
        }
        List<Entry<String, String>> pairs__560 = Core.mappedToList(this._changes);
        if (pairs__560.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__561 = new ArrayList<>();
        List<SqlPart> valParts__562 = new ArrayList<>();
        int i__563 = 0;
        while (true) {
            t_8603 = pairs__560.size();
            if (i__563 >= t_8603) {
                break;
            }
            Entry<String, String> pair__564 = Core.listGet(pairs__560, i__563);
            t_8605 = pair__564.getKey();
            t_4877 = this._tableDef.field(t_8605);
            FieldDef fd__565 = t_4877;
            Core.listAdd(colNames__561, fd__565.getName().getSqlValue());
            t_8609 = pair__564.getValue();
            t_4882 = this.valueToSqlPart(fd__565, t_8609);
            Core.listAdd(valParts__562, t_4882);
            i__563 = i__563 + 1;
        }
        SqlBuilder b__566 = new SqlBuilder();
        b__566.appendSafe("INSERT INTO ");
        b__566.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__566.appendSafe(" (");
        List<String> t_8617 = List.copyOf(colNames__561);
        Function<String, String> fn__8590 = c__567 -> c__567;
        b__566.appendSafe(Core.listJoinObj(t_8617, ", ", fn__8590));
        b__566.appendSafe(") VALUES (");
        b__566.appendPart(Core.listGet(valParts__562, 0));
        int j__568 = 1;
        while (true) {
            t_8624 = valParts__562.size();
            if (j__568 >= t_8624) {
                break;
            }
            b__566.appendSafe(", ");
            b__566.appendPart(Core.listGet(valParts__562, j__568));
            j__568 = j__568 + 1;
        }
        b__566.appendSafe(")");
        return b__566.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__570) {
        int t_8577;
        String t_8580;
        String t_8585;
        FieldDef t_4850;
        SqlPart t_4856;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__572 = Core.mappedToList(this._changes);
        if (pairs__572.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__573 = new SqlBuilder();
        b__573.appendSafe("UPDATE ");
        b__573.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__573.appendSafe(" SET ");
        int i__574 = 0;
        while (true) {
            t_8577 = pairs__572.size();
            if (i__574 >= t_8577) {
                break;
            }
            if (i__574 > 0) {
                b__573.appendSafe(", ");
            }
            Entry<String, String> pair__575 = Core.listGet(pairs__572, i__574);
            t_8580 = pair__575.getKey();
            t_4850 = this._tableDef.field(t_8580);
            FieldDef fd__576 = t_4850;
            b__573.appendSafe(fd__576.getName().getSqlValue());
            b__573.appendSafe(" = ");
            t_8585 = pair__575.getValue();
            t_4856 = this.valueToSqlPart(fd__576, t_8585);
            b__573.appendPart(t_4856);
            i__574 = i__574 + 1;
        }
        b__573.appendSafe(" WHERE id = ");
        b__573.appendInt32(id__570);
        return b__573.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__578, Map<String, String> _params__579, Map<String, String> _changes__580, List<ChangesetError> _errors__581, boolean _isValid__582) {
        this._tableDef = _tableDef__578;
        this._params = _params__579;
        this._changes = _changes__580;
        this._errors = _errors__581;
        this._isValid = _isValid__582;
    }
}
