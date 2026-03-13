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
    public Changeset cast(List<SafeIdentifier> allowedFields__521) {
        Map<String, String> mb__523 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__9583 = f__524 -> {
            String t_9581;
            String t_9578 = f__524.getSqlValue();
            String val__525 = this._params.getOrDefault(t_9578, "");
            if (!val__525.isEmpty()) {
                t_9581 = f__524.getSqlValue();
                mb__523.put(t_9581, val__525);
            }
        };
        allowedFields__521.forEach(fn__9583);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__523), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__527) {
        Changeset return__284;
        List<ChangesetError> t_9576;
        TableDef t_5480;
        Map<String, String> t_5481;
        Map<String, String> t_5482;
        fn__528: {
            if (!this._isValid) {
                return__284 = this;
                break fn__528;
            }
            List<ChangesetError> eb__529 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__530 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__9572 = f__531 -> {
                ChangesetError t_9570;
                String t_9567 = f__531.getSqlValue();
                if (!this._changes.containsKey(t_9567)) {
                    t_9570 = new ChangesetError(f__531.getSqlValue(), "is required");
                    Core.listAdd(eb__529, t_9570);
                    local$1.valid__530 = false;
                }
            };
            fields__527.forEach(fn__9572);
            t_5480 = this._tableDef;
            t_5481 = this._params;
            t_5482 = this._changes;
            t_9576 = List.copyOf(eb__529);
            return__284 = new ChangesetImpl(t_5480, t_5481, t_5482, t_9576, local$1.valid__530);
        }
        return return__284;
    }
    public Changeset validateLength(SafeIdentifier field__533, int min__534, int max__535) {
        Changeset return__285;
        String t_9554;
        List<ChangesetError> t_9565;
        boolean t_5463;
        TableDef t_5469;
        Map<String, String> t_5470;
        Map<String, String> t_5471;
        fn__536: {
            if (!this._isValid) {
                return__285 = this;
                break fn__536;
            }
            t_9554 = field__533.getSqlValue();
            String val__537 = this._changes.getOrDefault(t_9554, "");
            int len__538 = Core.stringCountBetween(val__537, 0, val__537.length());
            if (len__538 < min__534) {
                t_5463 = true;
            } else {
                t_5463 = len__538 > max__535;
            }
            if (t_5463) {
                String msg__539 = "must be between " + Integer.toString(min__534) + " and " + Integer.toString(max__535) + " characters";
                List<ChangesetError> eb__540 = new ArrayList<>(this._errors);
                Core.listAdd(eb__540, new ChangesetError(field__533.getSqlValue(), msg__539));
                t_5469 = this._tableDef;
                t_5470 = this._params;
                t_5471 = this._changes;
                t_9565 = List.copyOf(eb__540);
                return__285 = new ChangesetImpl(t_5469, t_5470, t_5471, t_9565, false);
                break fn__536;
            }
            return__285 = this;
        }
        return return__285;
    }
    public Changeset validateInt(SafeIdentifier field__542) {
        Changeset return__286;
        String t_9545;
        List<ChangesetError> t_9552;
        TableDef t_5454;
        Map<String, String> t_5455;
        Map<String, String> t_5456;
        fn__543: {
            if (!this._isValid) {
                return__286 = this;
                break fn__543;
            }
            t_9545 = field__542.getSqlValue();
            String val__544 = this._changes.getOrDefault(t_9545, "");
            if (val__544.isEmpty()) {
                return__286 = this;
                break fn__543;
            }
            boolean parseOk__545;
            boolean parseOk_9690;
            try {
                Core.stringToInt(val__544);
                parseOk_9690 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_9690 = false;
            }
            parseOk__545 = parseOk_9690;
            if (!parseOk__545) {
                List<ChangesetError> eb__546 = new ArrayList<>(this._errors);
                Core.listAdd(eb__546, new ChangesetError(field__542.getSqlValue(), "must be an integer"));
                t_5454 = this._tableDef;
                t_5455 = this._params;
                t_5456 = this._changes;
                t_9552 = List.copyOf(eb__546);
                return__286 = new ChangesetImpl(t_5454, t_5455, t_5456, t_9552, false);
                break fn__543;
            }
            return__286 = this;
        }
        return return__286;
    }
    public Changeset validateInt64(SafeIdentifier field__548) {
        Changeset return__287;
        String t_9536;
        List<ChangesetError> t_9543;
        TableDef t_5441;
        Map<String, String> t_5442;
        Map<String, String> t_5443;
        fn__549: {
            if (!this._isValid) {
                return__287 = this;
                break fn__549;
            }
            t_9536 = field__548.getSqlValue();
            String val__550 = this._changes.getOrDefault(t_9536, "");
            if (val__550.isEmpty()) {
                return__287 = this;
                break fn__549;
            }
            boolean parseOk__551;
            boolean parseOk_9692;
            try {
                Core.stringToInt64(val__550);
                parseOk_9692 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_9692 = false;
            }
            parseOk__551 = parseOk_9692;
            if (!parseOk__551) {
                List<ChangesetError> eb__552 = new ArrayList<>(this._errors);
                Core.listAdd(eb__552, new ChangesetError(field__548.getSqlValue(), "must be a 64-bit integer"));
                t_5441 = this._tableDef;
                t_5442 = this._params;
                t_5443 = this._changes;
                t_9543 = List.copyOf(eb__552);
                return__287 = new ChangesetImpl(t_5441, t_5442, t_5443, t_9543, false);
                break fn__549;
            }
            return__287 = this;
        }
        return return__287;
    }
    public Changeset validateFloat(SafeIdentifier field__554) {
        Changeset return__288;
        String t_9527;
        List<ChangesetError> t_9534;
        TableDef t_5428;
        Map<String, String> t_5429;
        Map<String, String> t_5430;
        fn__555: {
            if (!this._isValid) {
                return__288 = this;
                break fn__555;
            }
            t_9527 = field__554.getSqlValue();
            String val__556 = this._changes.getOrDefault(t_9527, "");
            if (val__556.isEmpty()) {
                return__288 = this;
                break fn__555;
            }
            boolean parseOk__557;
            boolean parseOk_9694;
            try {
                Core.stringToFloat64(val__556);
                parseOk_9694 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_9694 = false;
            }
            parseOk__557 = parseOk_9694;
            if (!parseOk__557) {
                List<ChangesetError> eb__558 = new ArrayList<>(this._errors);
                Core.listAdd(eb__558, new ChangesetError(field__554.getSqlValue(), "must be a number"));
                t_5428 = this._tableDef;
                t_5429 = this._params;
                t_5430 = this._changes;
                t_9534 = List.copyOf(eb__558);
                return__288 = new ChangesetImpl(t_5428, t_5429, t_5430, t_9534, false);
                break fn__555;
            }
            return__288 = this;
        }
        return return__288;
    }
    public Changeset validateBool(SafeIdentifier field__560) {
        Changeset return__289;
        String t_9518;
        List<ChangesetError> t_9525;
        boolean t_5403;
        boolean t_5404;
        boolean t_5406;
        boolean t_5407;
        boolean t_5409;
        TableDef t_5415;
        Map<String, String> t_5416;
        Map<String, String> t_5417;
        fn__561: {
            if (!this._isValid) {
                return__289 = this;
                break fn__561;
            }
            t_9518 = field__560.getSqlValue();
            String val__562 = this._changes.getOrDefault(t_9518, "");
            if (val__562.isEmpty()) {
                return__289 = this;
                break fn__561;
            }
            boolean isTrue__563;
            if (val__562.equals("true")) {
                isTrue__563 = true;
            } else {
                if (val__562.equals("1")) {
                    t_5404 = true;
                } else {
                    if (val__562.equals("yes")) {
                        t_5403 = true;
                    } else {
                        t_5403 = val__562.equals("on");
                    }
                    t_5404 = t_5403;
                }
                isTrue__563 = t_5404;
            }
            boolean isFalse__564;
            if (val__562.equals("false")) {
                isFalse__564 = true;
            } else {
                if (val__562.equals("0")) {
                    t_5407 = true;
                } else {
                    if (val__562.equals("no")) {
                        t_5406 = true;
                    } else {
                        t_5406 = val__562.equals("off");
                    }
                    t_5407 = t_5406;
                }
                isFalse__564 = t_5407;
            }
            if (!isTrue__563) {
                t_5409 = !isFalse__564;
            } else {
                t_5409 = false;
            }
            if (t_5409) {
                List<ChangesetError> eb__565 = new ArrayList<>(this._errors);
                Core.listAdd(eb__565, new ChangesetError(field__560.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_5415 = this._tableDef;
                t_5416 = this._params;
                t_5417 = this._changes;
                t_9525 = List.copyOf(eb__565);
                return__289 = new ChangesetImpl(t_5415, t_5416, t_5417, t_9525, false);
                break fn__561;
            }
            return__289 = this;
        }
        return return__289;
    }
    SqlBoolean parseBoolSqlPart(String val__567) {
        SqlBoolean return__290;
        boolean t_5392;
        boolean t_5393;
        boolean t_5394;
        boolean t_5396;
        boolean t_5397;
        boolean t_5398;
        fn__568: {
            if (val__567.equals("true")) {
                t_5394 = true;
            } else {
                if (val__567.equals("1")) {
                    t_5393 = true;
                } else {
                    if (val__567.equals("yes")) {
                        t_5392 = true;
                    } else {
                        t_5392 = val__567.equals("on");
                    }
                    t_5393 = t_5392;
                }
                t_5394 = t_5393;
            }
            if (t_5394) {
                return__290 = new SqlBoolean(true);
                break fn__568;
            }
            if (val__567.equals("false")) {
                t_5398 = true;
            } else {
                if (val__567.equals("0")) {
                    t_5397 = true;
                } else {
                    if (val__567.equals("no")) {
                        t_5396 = true;
                    } else {
                        t_5396 = val__567.equals("off");
                    }
                    t_5397 = t_5396;
                }
                t_5398 = t_5397;
            }
            if (t_5398) {
                return__290 = new SqlBoolean(false);
                break fn__568;
            }
            throw Core.bubble();
        }
        return return__290;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__570, String val__571) {
        SqlPart return__291;
        int t_5379;
        long t_5382;
        double t_5385;
        LocalDate t_5390;
        fn__572: {
            FieldType ft__573 = fieldDef__570.getFieldType();
            if (ft__573 instanceof StringField) {
                return__291 = new SqlString(val__571);
                break fn__572;
            }
            if (ft__573 instanceof IntField) {
                t_5379 = Core.stringToInt(val__571);
                return__291 = new SqlInt32(t_5379);
                break fn__572;
            }
            if (ft__573 instanceof Int64Field) {
                t_5382 = Core.stringToInt64(val__571);
                return__291 = new SqlInt64(t_5382);
                break fn__572;
            }
            if (ft__573 instanceof FloatField) {
                t_5385 = Core.stringToFloat64(val__571);
                return__291 = new SqlFloat64(t_5385);
                break fn__572;
            }
            if (ft__573 instanceof BoolField) {
                return__291 = this.parseBoolSqlPart(val__571);
                break fn__572;
            }
            if (ft__573 instanceof DateField) {
                t_5390 = LocalDate.parse(val__571);
                return__291 = new SqlDate(t_5390);
                break fn__572;
            }
            throw Core.bubble();
        }
        return return__291;
    }
    public SqlFragment toInsertSql() {
        int t_9466;
        String t_9471;
        boolean t_9472;
        int t_9477;
        String t_9479;
        String t_9483;
        int t_9498;
        boolean t_5343;
        FieldDef t_5351;
        SqlPart t_5356;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__576 = 0;
        while (true) {
            t_9466 = this._tableDef.getFields().size();
            if (i__576 >= t_9466) {
                break;
            }
            FieldDef f__577 = Core.listGet(this._tableDef.getFields(), i__576);
            if (!f__577.isNullable()) {
                t_9471 = f__577.getName().getSqlValue();
                t_9472 = this._changes.containsKey(t_9471);
                t_5343 = !t_9472;
            } else {
                t_5343 = false;
            }
            if (t_5343) {
                throw Core.bubble();
            }
            i__576 = i__576 + 1;
        }
        List<Entry<String, String>> pairs__578 = Core.mappedToList(this._changes);
        if (pairs__578.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__579 = new ArrayList<>();
        List<SqlPart> valParts__580 = new ArrayList<>();
        int i__581 = 0;
        while (true) {
            t_9477 = pairs__578.size();
            if (i__581 >= t_9477) {
                break;
            }
            Entry<String, String> pair__582 = Core.listGet(pairs__578, i__581);
            t_9479 = pair__582.getKey();
            t_5351 = this._tableDef.field(t_9479);
            FieldDef fd__583 = t_5351;
            Core.listAdd(colNames__579, fd__583.getName().getSqlValue());
            t_9483 = pair__582.getValue();
            t_5356 = this.valueToSqlPart(fd__583, t_9483);
            Core.listAdd(valParts__580, t_5356);
            i__581 = i__581 + 1;
        }
        SqlBuilder b__584 = new SqlBuilder();
        b__584.appendSafe("INSERT INTO ");
        b__584.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__584.appendSafe(" (");
        List<String> t_9491 = List.copyOf(colNames__579);
        Function<String, String> fn__9464 = c__585 -> c__585;
        b__584.appendSafe(Core.listJoinObj(t_9491, ", ", fn__9464));
        b__584.appendSafe(") VALUES (");
        b__584.appendPart(Core.listGet(valParts__580, 0));
        int j__586 = 1;
        while (true) {
            t_9498 = valParts__580.size();
            if (j__586 >= t_9498) {
                break;
            }
            b__584.appendSafe(", ");
            b__584.appendPart(Core.listGet(valParts__580, j__586));
            j__586 = j__586 + 1;
        }
        b__584.appendSafe(")");
        return b__584.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__588) {
        int t_9451;
        String t_9454;
        String t_9459;
        FieldDef t_5324;
        SqlPart t_5330;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__590 = Core.mappedToList(this._changes);
        if (pairs__590.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__591 = new SqlBuilder();
        b__591.appendSafe("UPDATE ");
        b__591.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__591.appendSafe(" SET ");
        int i__592 = 0;
        while (true) {
            t_9451 = pairs__590.size();
            if (i__592 >= t_9451) {
                break;
            }
            if (i__592 > 0) {
                b__591.appendSafe(", ");
            }
            Entry<String, String> pair__593 = Core.listGet(pairs__590, i__592);
            t_9454 = pair__593.getKey();
            t_5324 = this._tableDef.field(t_9454);
            FieldDef fd__594 = t_5324;
            b__591.appendSafe(fd__594.getName().getSqlValue());
            b__591.appendSafe(" = ");
            t_9459 = pair__593.getValue();
            t_5330 = this.valueToSqlPart(fd__594, t_9459);
            b__591.appendPart(t_5330);
            i__592 = i__592 + 1;
        }
        b__591.appendSafe(" WHERE id = ");
        b__591.appendInt32(id__588);
        return b__591.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__596, Map<String, String> _params__597, Map<String, String> _changes__598, List<ChangesetError> _errors__599, boolean _isValid__600) {
        this._tableDef = _tableDef__596;
        this._params = _params__597;
        this._changes = _changes__598;
        this._errors = _errors__599;
        this._isValid = _isValid__600;
    }
}
