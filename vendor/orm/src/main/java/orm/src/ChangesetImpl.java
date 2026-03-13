package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.Consumer;
import temper.core.Nullable;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.function.Function;
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
    public Changeset cast(List<SafeIdentifier> allowedFields__750) {
        Map<String, String> mb__752 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__14193 = f__753 -> {
            String t_14191;
            String t_14188 = f__753.getSqlValue();
            String val__754 = this._params.getOrDefault(t_14188, "");
            if (!val__754.isEmpty()) {
                t_14191 = f__753.getSqlValue();
                mb__752.put(t_14191, val__754);
            }
        };
        allowedFields__750.forEach(fn__14193);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__752), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__756) {
        Changeset return__405;
        List<ChangesetError> t_14186;
        TableDef t_8102;
        Map<String, String> t_8103;
        Map<String, String> t_8104;
        fn__757: {
            if (!this._isValid) {
                return__405 = this;
                break fn__757;
            }
            List<ChangesetError> eb__758 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__759 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__14182 = f__760 -> {
                ChangesetError t_14180;
                String t_14177 = f__760.getSqlValue();
                if (!this._changes.containsKey(t_14177)) {
                    t_14180 = new ChangesetError(f__760.getSqlValue(), "is required");
                    Core.listAdd(eb__758, t_14180);
                    local$1.valid__759 = false;
                }
            };
            fields__756.forEach(fn__14182);
            t_8102 = this._tableDef;
            t_8103 = this._params;
            t_8104 = this._changes;
            t_14186 = List.copyOf(eb__758);
            return__405 = new ChangesetImpl(t_8102, t_8103, t_8104, t_14186, local$1.valid__759);
        }
        return return__405;
    }
    public Changeset validateLength(SafeIdentifier field__762, int min__763, int max__764) {
        Changeset return__406;
        String t_14164;
        List<ChangesetError> t_14175;
        boolean t_8085;
        TableDef t_8091;
        Map<String, String> t_8092;
        Map<String, String> t_8093;
        fn__765: {
            if (!this._isValid) {
                return__406 = this;
                break fn__765;
            }
            t_14164 = field__762.getSqlValue();
            String val__766 = this._changes.getOrDefault(t_14164, "");
            int len__767 = Core.stringCountBetween(val__766, 0, val__766.length());
            if (len__767 < min__763) {
                t_8085 = true;
            } else {
                t_8085 = len__767 > max__764;
            }
            if (t_8085) {
                String msg__768 = "must be between " + Integer.toString(min__763) + " and " + Integer.toString(max__764) + " characters";
                List<ChangesetError> eb__769 = new ArrayList<>(this._errors);
                Core.listAdd(eb__769, new ChangesetError(field__762.getSqlValue(), msg__768));
                t_8091 = this._tableDef;
                t_8092 = this._params;
                t_8093 = this._changes;
                t_14175 = List.copyOf(eb__769);
                return__406 = new ChangesetImpl(t_8091, t_8092, t_8093, t_14175, false);
                break fn__765;
            }
            return__406 = this;
        }
        return return__406;
    }
    public Changeset validateInt(SafeIdentifier field__771) {
        Changeset return__407;
        String t_14155;
        List<ChangesetError> t_14162;
        TableDef t_8076;
        Map<String, String> t_8077;
        Map<String, String> t_8078;
        fn__772: {
            if (!this._isValid) {
                return__407 = this;
                break fn__772;
            }
            t_14155 = field__771.getSqlValue();
            String val__773 = this._changes.getOrDefault(t_14155, "");
            if (val__773.isEmpty()) {
                return__407 = this;
                break fn__772;
            }
            boolean parseOk__774;
            boolean parseOk_14300;
            try {
                Core.stringToInt(val__773);
                parseOk_14300 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_14300 = false;
            }
            parseOk__774 = parseOk_14300;
            if (!parseOk__774) {
                List<ChangesetError> eb__775 = new ArrayList<>(this._errors);
                Core.listAdd(eb__775, new ChangesetError(field__771.getSqlValue(), "must be an integer"));
                t_8076 = this._tableDef;
                t_8077 = this._params;
                t_8078 = this._changes;
                t_14162 = List.copyOf(eb__775);
                return__407 = new ChangesetImpl(t_8076, t_8077, t_8078, t_14162, false);
                break fn__772;
            }
            return__407 = this;
        }
        return return__407;
    }
    public Changeset validateInt64(SafeIdentifier field__777) {
        Changeset return__408;
        String t_14146;
        List<ChangesetError> t_14153;
        TableDef t_8063;
        Map<String, String> t_8064;
        Map<String, String> t_8065;
        fn__778: {
            if (!this._isValid) {
                return__408 = this;
                break fn__778;
            }
            t_14146 = field__777.getSqlValue();
            String val__779 = this._changes.getOrDefault(t_14146, "");
            if (val__779.isEmpty()) {
                return__408 = this;
                break fn__778;
            }
            boolean parseOk__780;
            boolean parseOk_14302;
            try {
                Core.stringToInt64(val__779);
                parseOk_14302 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_14302 = false;
            }
            parseOk__780 = parseOk_14302;
            if (!parseOk__780) {
                List<ChangesetError> eb__781 = new ArrayList<>(this._errors);
                Core.listAdd(eb__781, new ChangesetError(field__777.getSqlValue(), "must be a 64-bit integer"));
                t_8063 = this._tableDef;
                t_8064 = this._params;
                t_8065 = this._changes;
                t_14153 = List.copyOf(eb__781);
                return__408 = new ChangesetImpl(t_8063, t_8064, t_8065, t_14153, false);
                break fn__778;
            }
            return__408 = this;
        }
        return return__408;
    }
    public Changeset validateFloat(SafeIdentifier field__783) {
        Changeset return__409;
        String t_14137;
        List<ChangesetError> t_14144;
        TableDef t_8050;
        Map<String, String> t_8051;
        Map<String, String> t_8052;
        fn__784: {
            if (!this._isValid) {
                return__409 = this;
                break fn__784;
            }
            t_14137 = field__783.getSqlValue();
            String val__785 = this._changes.getOrDefault(t_14137, "");
            if (val__785.isEmpty()) {
                return__409 = this;
                break fn__784;
            }
            boolean parseOk__786;
            boolean parseOk_14304;
            try {
                Core.stringToFloat64(val__785);
                parseOk_14304 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_14304 = false;
            }
            parseOk__786 = parseOk_14304;
            if (!parseOk__786) {
                List<ChangesetError> eb__787 = new ArrayList<>(this._errors);
                Core.listAdd(eb__787, new ChangesetError(field__783.getSqlValue(), "must be a number"));
                t_8050 = this._tableDef;
                t_8051 = this._params;
                t_8052 = this._changes;
                t_14144 = List.copyOf(eb__787);
                return__409 = new ChangesetImpl(t_8050, t_8051, t_8052, t_14144, false);
                break fn__784;
            }
            return__409 = this;
        }
        return return__409;
    }
    public Changeset validateBool(SafeIdentifier field__789) {
        Changeset return__410;
        String t_14128;
        List<ChangesetError> t_14135;
        boolean t_8025;
        boolean t_8026;
        boolean t_8028;
        boolean t_8029;
        boolean t_8031;
        TableDef t_8037;
        Map<String, String> t_8038;
        Map<String, String> t_8039;
        fn__790: {
            if (!this._isValid) {
                return__410 = this;
                break fn__790;
            }
            t_14128 = field__789.getSqlValue();
            String val__791 = this._changes.getOrDefault(t_14128, "");
            if (val__791.isEmpty()) {
                return__410 = this;
                break fn__790;
            }
            boolean isTrue__792;
            if (val__791.equals("true")) {
                isTrue__792 = true;
            } else {
                if (val__791.equals("1")) {
                    t_8026 = true;
                } else {
                    if (val__791.equals("yes")) {
                        t_8025 = true;
                    } else {
                        t_8025 = val__791.equals("on");
                    }
                    t_8026 = t_8025;
                }
                isTrue__792 = t_8026;
            }
            boolean isFalse__793;
            if (val__791.equals("false")) {
                isFalse__793 = true;
            } else {
                if (val__791.equals("0")) {
                    t_8029 = true;
                } else {
                    if (val__791.equals("no")) {
                        t_8028 = true;
                    } else {
                        t_8028 = val__791.equals("off");
                    }
                    t_8029 = t_8028;
                }
                isFalse__793 = t_8029;
            }
            if (!isTrue__792) {
                t_8031 = !isFalse__793;
            } else {
                t_8031 = false;
            }
            if (t_8031) {
                List<ChangesetError> eb__794 = new ArrayList<>(this._errors);
                Core.listAdd(eb__794, new ChangesetError(field__789.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_8037 = this._tableDef;
                t_8038 = this._params;
                t_8039 = this._changes;
                t_14135 = List.copyOf(eb__794);
                return__410 = new ChangesetImpl(t_8037, t_8038, t_8039, t_14135, false);
                break fn__790;
            }
            return__410 = this;
        }
        return return__410;
    }
    public Changeset putChange(SafeIdentifier field__796, String value__797) {
        int t_14116;
        Map<String, String> mb__799 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__800 = Core.mappedToList(this._changes);
        int i__801 = 0;
        while (true) {
            t_14116 = pairs__800.size();
            if (i__801 >= t_14116) {
                break;
            }
            mb__799.put(Core.listGet(pairs__800, i__801).getKey(), Core.listGet(pairs__800, i__801).getValue());
            i__801 = i__801 + 1;
        }
        mb__799.put(field__796.getSqlValue(), value__797);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__799), this._errors, this._isValid);
    }
    public String getChange(SafeIdentifier field__803) {
        String t_14110 = field__803.getSqlValue();
        if (!this._changes.containsKey(t_14110)) {
            throw Core.bubble();
        }
        String t_14112 = field__803.getSqlValue();
        return this._changes.getOrDefault(t_14112, "");
    }
    public Changeset deleteChange(SafeIdentifier field__806) {
        int t_14097;
        Map<String, String> mb__808 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__809 = Core.mappedToList(this._changes);
        int i__810 = 0;
        while (true) {
            t_14097 = pairs__809.size();
            if (i__810 >= t_14097) {
                break;
            }
            if (!Core.listGet(pairs__809, i__810).getKey().equals(field__806.getSqlValue())) {
                mb__808.put(Core.listGet(pairs__809, i__810).getKey(), Core.listGet(pairs__809, i__810).getValue());
            }
            i__810 = i__810 + 1;
        }
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__808), this._errors, this._isValid);
    }
    public Changeset validateInclusion(SafeIdentifier field__812, List<String> allowed__813) {
        Changeset return__414;
        String t_14083;
        String t_14085;
        List<ChangesetError> t_14093;
        TableDef t_7987;
        Map<String, String> t_7988;
        Map<String, String> t_7989;
        fn__814: {
            if (!this._isValid) {
                return__414 = this;
                break fn__814;
            }
            t_14083 = field__812.getSqlValue();
            if (!this._changes.containsKey(t_14083)) {
                return__414 = this;
                break fn__814;
            }
            t_14085 = field__812.getSqlValue();
            String val__815 = this._changes.getOrDefault(t_14085, "");
            class Local_2 {
                boolean found__816 = false;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<String> fn__14082 = a__817 -> {
                if (a__817.equals(val__815)) {
                    local$2.found__816 = true;
                }
            };
            allowed__813.forEach(fn__14082);
            if (!local$2.found__816) {
                List<ChangesetError> eb__818 = new ArrayList<>(this._errors);
                Core.listAdd(eb__818, new ChangesetError(field__812.getSqlValue(), "is not included in the list"));
                t_7987 = this._tableDef;
                t_7988 = this._params;
                t_7989 = this._changes;
                t_14093 = List.copyOf(eb__818);
                return__414 = new ChangesetImpl(t_7987, t_7988, t_7989, t_14093, false);
                break fn__814;
            }
            return__414 = this;
        }
        return return__414;
    }
    public Changeset validateExclusion(SafeIdentifier field__820, List<String> disallowed__821) {
        Changeset return__415;
        String t_14070;
        String t_14072;
        List<ChangesetError> t_14080;
        TableDef t_7973;
        Map<String, String> t_7974;
        Map<String, String> t_7975;
        fn__822: {
            if (!this._isValid) {
                return__415 = this;
                break fn__822;
            }
            t_14070 = field__820.getSqlValue();
            if (!this._changes.containsKey(t_14070)) {
                return__415 = this;
                break fn__822;
            }
            t_14072 = field__820.getSqlValue();
            String val__823 = this._changes.getOrDefault(t_14072, "");
            class Local_3 {
                boolean found__824 = false;
            }
            final Local_3 local$3 = new Local_3();
            Consumer<String> fn__14069 = d__825 -> {
                if (d__825.equals(val__823)) {
                    local$3.found__824 = true;
                }
            };
            disallowed__821.forEach(fn__14069);
            if (local$3.found__824) {
                List<ChangesetError> eb__826 = new ArrayList<>(this._errors);
                Core.listAdd(eb__826, new ChangesetError(field__820.getSqlValue(), "is reserved"));
                t_7973 = this._tableDef;
                t_7974 = this._params;
                t_7975 = this._changes;
                t_14080 = List.copyOf(eb__826);
                return__415 = new ChangesetImpl(t_7973, t_7974, t_7975, t_14080, false);
                break fn__822;
            }
            return__415 = this;
        }
        return return__415;
    }
    public Changeset validateNumber(SafeIdentifier field__828, NumberValidationOpts opts__829) {
        Changeset return__416;
        String t_14019;
        String t_14021;
        List<ChangesetError> t_14027;
        List<ChangesetError> t_14035;
        List<ChangesetError> t_14043;
        List<ChangesetError> t_14051;
        List<ChangesetError> t_14059;
        List<ChangesetError> t_14067;
        TableDef t_7906;
        Map<String, String> t_7907;
        Map<String, String> t_7908;
        double t_7910;
        TableDef t_7919;
        Map<String, String> t_7920;
        Map<String, String> t_7921;
        TableDef t_7929;
        Map<String, String> t_7930;
        Map<String, String> t_7931;
        TableDef t_7939;
        Map<String, String> t_7940;
        Map<String, String> t_7941;
        TableDef t_7949;
        Map<String, String> t_7950;
        Map<String, String> t_7951;
        TableDef t_7959;
        Map<String, String> t_7960;
        Map<String, String> t_7961;
        fn__830: {
            if (!this._isValid) {
                return__416 = this;
                break fn__830;
            }
            t_14019 = field__828.getSqlValue();
            if (!this._changes.containsKey(t_14019)) {
                return__416 = this;
                break fn__830;
            }
            t_14021 = field__828.getSqlValue();
            String val__831 = this._changes.getOrDefault(t_14021, "");
            boolean parseOk__832;
            boolean parseOk_14308;
            try {
                Core.stringToFloat64(val__831);
                parseOk_14308 = true;
            } catch (RuntimeException ignored$4) {
                parseOk_14308 = false;
            }
            parseOk__832 = parseOk_14308;
            if (!parseOk__832) {
                List<ChangesetError> eb__833 = new ArrayList<>(this._errors);
                Core.listAdd(eb__833, new ChangesetError(field__828.getSqlValue(), "must be a number"));
                t_7906 = this._tableDef;
                t_7907 = this._params;
                t_7908 = this._changes;
                t_14027 = List.copyOf(eb__833);
                return__416 = new ChangesetImpl(t_7906, t_7907, t_7908, t_14027, false);
                break fn__830;
            }
            double num__834;
            double num_14309;
            try {
                t_7910 = Core.stringToFloat64(val__831);
                num_14309 = t_7910;
            } catch (RuntimeException ignored$5) {
                num_14309 = 0.0D;
            }
            num__834 = num_14309;
            @Nullable Double gt__835 = opts__829.getGreaterThan();
            if (gt__835 != null) {
                double gt_2466 = gt__835;
                if (Double.doubleToLongBits(num__834) <= Double.doubleToLongBits(gt_2466)) {
                    List<ChangesetError> eb__836 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__836, new ChangesetError(field__828.getSqlValue(), "must be greater than " + Core.float64ToString(gt_2466)));
                    t_7919 = this._tableDef;
                    t_7920 = this._params;
                    t_7921 = this._changes;
                    t_14035 = List.copyOf(eb__836);
                    return__416 = new ChangesetImpl(t_7919, t_7920, t_7921, t_14035, false);
                    break fn__830;
                }
            }
            @Nullable Double lt__837 = opts__829.getLessThan();
            if (lt__837 != null) {
                double lt_2467 = lt__837;
                if (Double.doubleToLongBits(num__834) >= Double.doubleToLongBits(lt_2467)) {
                    List<ChangesetError> eb__838 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__838, new ChangesetError(field__828.getSqlValue(), "must be less than " + Core.float64ToString(lt_2467)));
                    t_7929 = this._tableDef;
                    t_7930 = this._params;
                    t_7931 = this._changes;
                    t_14043 = List.copyOf(eb__838);
                    return__416 = new ChangesetImpl(t_7929, t_7930, t_7931, t_14043, false);
                    break fn__830;
                }
            }
            @Nullable Double gte__839 = opts__829.getGreaterThanOrEqual();
            if (gte__839 != null) {
                double gte_2468 = gte__839;
                if (Double.doubleToLongBits(num__834) < Double.doubleToLongBits(gte_2468)) {
                    List<ChangesetError> eb__840 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__840, new ChangesetError(field__828.getSqlValue(), "must be greater than or equal to " + Core.float64ToString(gte_2468)));
                    t_7939 = this._tableDef;
                    t_7940 = this._params;
                    t_7941 = this._changes;
                    t_14051 = List.copyOf(eb__840);
                    return__416 = new ChangesetImpl(t_7939, t_7940, t_7941, t_14051, false);
                    break fn__830;
                }
            }
            @Nullable Double lte__841 = opts__829.getLessThanOrEqual();
            if (lte__841 != null) {
                double lte_2469 = lte__841;
                if (Double.doubleToLongBits(num__834) > Double.doubleToLongBits(lte_2469)) {
                    List<ChangesetError> eb__842 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__842, new ChangesetError(field__828.getSqlValue(), "must be less than or equal to " + Core.float64ToString(lte_2469)));
                    t_7949 = this._tableDef;
                    t_7950 = this._params;
                    t_7951 = this._changes;
                    t_14059 = List.copyOf(eb__842);
                    return__416 = new ChangesetImpl(t_7949, t_7950, t_7951, t_14059, false);
                    break fn__830;
                }
            }
            @Nullable Double eq__843 = opts__829.getEqualTo();
            if (eq__843 != null) {
                double eq_2470 = eq__843;
                if (Double.doubleToLongBits(num__834) != Double.doubleToLongBits(eq_2470)) {
                    List<ChangesetError> eb__844 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__844, new ChangesetError(field__828.getSqlValue(), "must be equal to " + Core.float64ToString(eq_2470)));
                    t_7959 = this._tableDef;
                    t_7960 = this._params;
                    t_7961 = this._changes;
                    t_14067 = List.copyOf(eb__844);
                    return__416 = new ChangesetImpl(t_7959, t_7960, t_7961, t_14067, false);
                    break fn__830;
                }
            }
            return__416 = this;
        }
        return return__416;
    }
    public Changeset validateAcceptance(SafeIdentifier field__846) {
        Changeset return__417;
        String t_14009;
        String t_14011;
        List<ChangesetError> t_14017;
        boolean t_7884;
        boolean t_7885;
        TableDef t_7892;
        Map<String, String> t_7893;
        Map<String, String> t_7894;
        fn__847: {
            if (!this._isValid) {
                return__417 = this;
                break fn__847;
            }
            t_14009 = field__846.getSqlValue();
            if (!this._changes.containsKey(t_14009)) {
                return__417 = this;
                break fn__847;
            }
            t_14011 = field__846.getSqlValue();
            String val__848 = this._changes.getOrDefault(t_14011, "");
            boolean accepted__849;
            if (val__848.equals("true")) {
                accepted__849 = true;
            } else {
                if (val__848.equals("1")) {
                    t_7885 = true;
                } else {
                    if (val__848.equals("yes")) {
                        t_7884 = true;
                    } else {
                        t_7884 = val__848.equals("on");
                    }
                    t_7885 = t_7884;
                }
                accepted__849 = t_7885;
            }
            if (!accepted__849) {
                List<ChangesetError> eb__850 = new ArrayList<>(this._errors);
                Core.listAdd(eb__850, new ChangesetError(field__846.getSqlValue(), "must be accepted"));
                t_7892 = this._tableDef;
                t_7893 = this._params;
                t_7894 = this._changes;
                t_14017 = List.copyOf(eb__850);
                return__417 = new ChangesetImpl(t_7892, t_7893, t_7894, t_14017, false);
                break fn__847;
            }
            return__417 = this;
        }
        return return__417;
    }
    public Changeset validateConfirmation(SafeIdentifier field__852, SafeIdentifier confirmationField__853) {
        Changeset return__418;
        String t_13997;
        String t_13999;
        String t_14001;
        List<ChangesetError> t_14007;
        TableDef t_7876;
        Map<String, String> t_7877;
        Map<String, String> t_7878;
        fn__854: {
            if (!this._isValid) {
                return__418 = this;
                break fn__854;
            }
            t_13997 = field__852.getSqlValue();
            if (!this._changes.containsKey(t_13997)) {
                return__418 = this;
                break fn__854;
            }
            t_13999 = field__852.getSqlValue();
            String val__855 = this._changes.getOrDefault(t_13999, "");
            t_14001 = confirmationField__853.getSqlValue();
            String conf__856 = this._changes.getOrDefault(t_14001, "");
            if (!val__855.equals(conf__856)) {
                List<ChangesetError> eb__857 = new ArrayList<>(this._errors);
                Core.listAdd(eb__857, new ChangesetError(confirmationField__853.getSqlValue(), "does not match"));
                t_7876 = this._tableDef;
                t_7877 = this._params;
                t_7878 = this._changes;
                t_14007 = List.copyOf(eb__857);
                return__418 = new ChangesetImpl(t_7876, t_7877, t_7878, t_14007, false);
                break fn__854;
            }
            return__418 = this;
        }
        return return__418;
    }
    public Changeset validateContains(SafeIdentifier field__859, String substring__860) {
        Changeset return__419;
        String t_13985;
        String t_13987;
        List<ChangesetError> t_13995;
        TableDef t_7861;
        Map<String, String> t_7862;
        Map<String, String> t_7863;
        fn__861: {
            if (!this._isValid) {
                return__419 = this;
                break fn__861;
            }
            t_13985 = field__859.getSqlValue();
            if (!this._changes.containsKey(t_13985)) {
                return__419 = this;
                break fn__861;
            }
            t_13987 = field__859.getSqlValue();
            String val__862 = this._changes.getOrDefault(t_13987, "");
            if (val__862.indexOf(substring__860) < 0) {
                List<ChangesetError> eb__863 = new ArrayList<>(this._errors);
                Core.listAdd(eb__863, new ChangesetError(field__859.getSqlValue(), "must contain the given substring"));
                t_7861 = this._tableDef;
                t_7862 = this._params;
                t_7863 = this._changes;
                t_13995 = List.copyOf(eb__863);
                return__419 = new ChangesetImpl(t_7861, t_7862, t_7863, t_13995, false);
                break fn__861;
            }
            return__419 = this;
        }
        return return__419;
    }
    public Changeset validateStartsWith(SafeIdentifier field__865, String prefix__866) {
        Changeset return__420;
        String t_13972;
        String t_13974;
        int t_13978;
        List<ChangesetError> t_13983;
        TableDef t_7845;
        Map<String, String> t_7846;
        Map<String, String> t_7847;
        fn__867: {
            if (!this._isValid) {
                return__420 = this;
                break fn__867;
            }
            t_13972 = field__865.getSqlValue();
            if (!this._changes.containsKey(t_13972)) {
                return__420 = this;
                break fn__867;
            }
            t_13974 = field__865.getSqlValue();
            String val__868 = this._changes.getOrDefault(t_13974, "");
            int idx__869 = val__868.indexOf(prefix__866);
            boolean starts__870;
            if (idx__869 >= 0) {
                t_13978 = Core.stringCountBetween(val__868, 0, Core.requireStringIndex(idx__869));
                starts__870 = t_13978 == 0;
            } else {
                starts__870 = false;
            }
            if (!starts__870) {
                List<ChangesetError> eb__871 = new ArrayList<>(this._errors);
                Core.listAdd(eb__871, new ChangesetError(field__865.getSqlValue(), "must start with the given prefix"));
                t_7845 = this._tableDef;
                t_7846 = this._params;
                t_7847 = this._changes;
                t_13983 = List.copyOf(eb__871);
                return__420 = new ChangesetImpl(t_7845, t_7846, t_7847, t_13983, false);
                break fn__867;
            }
            return__420 = this;
        }
        return return__420;
    }
    public Changeset validateEndsWith(SafeIdentifier field__873, String suffix__874) {
        Changeset return__421;
        String t_13944;
        String t_13946;
        int t_13951;
        List<ChangesetError> t_13957;
        int t_13959;
        boolean t_13960;
        int t_13964;
        int t_13965;
        List<ChangesetError> t_13970;
        TableDef t_7810;
        Map<String, String> t_7811;
        Map<String, String> t_7812;
        boolean t_7816;
        TableDef t_7827;
        Map<String, String> t_7828;
        Map<String, String> t_7829;
        fn__875: {
            if (!this._isValid) {
                return__421 = this;
                break fn__875;
            }
            t_13944 = field__873.getSqlValue();
            if (!this._changes.containsKey(t_13944)) {
                return__421 = this;
                break fn__875;
            }
            t_13946 = field__873.getSqlValue();
            String val__876 = this._changes.getOrDefault(t_13946, "");
            int valLen__877 = Core.stringCountBetween(val__876, 0, val__876.length());
            t_13951 = suffix__874.length();
            int suffixLen__878 = Core.stringCountBetween(suffix__874, 0, t_13951);
            if (valLen__877 < suffixLen__878) {
                List<ChangesetError> eb__879 = new ArrayList<>(this._errors);
                Core.listAdd(eb__879, new ChangesetError(field__873.getSqlValue(), "must end with the given suffix"));
                t_7810 = this._tableDef;
                t_7811 = this._params;
                t_7812 = this._changes;
                t_13957 = List.copyOf(eb__879);
                return__421 = new ChangesetImpl(t_7810, t_7811, t_7812, t_13957, false);
                break fn__875;
            }
            int skipCount__880 = valLen__877 - suffixLen__878;
            int strIdx__881 = 0;
            int i__882 = 0;
            while (i__882 < skipCount__880) {
                t_13959 = Core.stringNext(val__876, strIdx__881);
                strIdx__881 = t_13959;
                i__882 = i__882 + 1;
            }
            int sufIdx__883 = 0;
            boolean matches__884 = true;
            while (true) {
                if (matches__884) {
                    t_13960 = Core.stringHasIndex(suffix__874, sufIdx__883);
                    t_7816 = t_13960;
                } else {
                    t_7816 = false;
                }
                if (!t_7816) {
                    break;
                }
                if (!Core.stringHasIndex(val__876, strIdx__881)) {
                    matches__884 = false;
                } else if (val__876.codePointAt(strIdx__881) != suffix__874.codePointAt(sufIdx__883)) {
                    matches__884 = false;
                } else {
                    t_13964 = Core.stringNext(val__876, strIdx__881);
                    strIdx__881 = t_13964;
                    t_13965 = Core.stringNext(suffix__874, sufIdx__883);
                    sufIdx__883 = t_13965;
                }
            }
            if (!matches__884) {
                List<ChangesetError> eb__885 = new ArrayList<>(this._errors);
                Core.listAdd(eb__885, new ChangesetError(field__873.getSqlValue(), "must end with the given suffix"));
                t_7827 = this._tableDef;
                t_7828 = this._params;
                t_7829 = this._changes;
                t_13970 = List.copyOf(eb__885);
                return__421 = new ChangesetImpl(t_7827, t_7828, t_7829, t_13970, false);
                break fn__875;
            }
            return__421 = this;
        }
        return return__421;
    }
    SqlBoolean parseBoolSqlPart(String val__887) {
        SqlBoolean return__422;
        boolean t_7787;
        boolean t_7788;
        boolean t_7789;
        boolean t_7791;
        boolean t_7792;
        boolean t_7793;
        fn__888: {
            if (val__887.equals("true")) {
                t_7789 = true;
            } else {
                if (val__887.equals("1")) {
                    t_7788 = true;
                } else {
                    if (val__887.equals("yes")) {
                        t_7787 = true;
                    } else {
                        t_7787 = val__887.equals("on");
                    }
                    t_7788 = t_7787;
                }
                t_7789 = t_7788;
            }
            if (t_7789) {
                return__422 = new SqlBoolean(true);
                break fn__888;
            }
            if (val__887.equals("false")) {
                t_7793 = true;
            } else {
                if (val__887.equals("0")) {
                    t_7792 = true;
                } else {
                    if (val__887.equals("no")) {
                        t_7791 = true;
                    } else {
                        t_7791 = val__887.equals("off");
                    }
                    t_7792 = t_7791;
                }
                t_7793 = t_7792;
            }
            if (t_7793) {
                return__422 = new SqlBoolean(false);
                break fn__888;
            }
            throw Core.bubble();
        }
        return return__422;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__890, String val__891) {
        SqlPart return__423;
        int t_7774;
        long t_7777;
        double t_7780;
        LocalDate t_7785;
        fn__892: {
            FieldType ft__893 = fieldDef__890.getFieldType();
            if (ft__893 instanceof StringField) {
                return__423 = new SqlString(val__891);
                break fn__892;
            }
            if (ft__893 instanceof IntField) {
                t_7774 = Core.stringToInt(val__891);
                return__423 = new SqlInt32(t_7774);
                break fn__892;
            }
            if (ft__893 instanceof Int64Field) {
                t_7777 = Core.stringToInt64(val__891);
                return__423 = new SqlInt64(t_7777);
                break fn__892;
            }
            if (ft__893 instanceof FloatField) {
                t_7780 = Core.stringToFloat64(val__891);
                return__423 = new SqlFloat64(t_7780);
                break fn__892;
            }
            if (ft__893 instanceof BoolField) {
                return__423 = this.parseBoolSqlPart(val__891);
                break fn__892;
            }
            if (ft__893 instanceof DateField) {
                t_7785 = LocalDate.parse(val__891);
                return__423 = new SqlDate(t_7785);
                break fn__892;
            }
            throw Core.bubble();
        }
        return return__423;
    }
    public SqlFragment toInsertSql() {
        int t_13892;
        String t_13897;
        boolean t_13898;
        int t_13903;
        String t_13905;
        String t_13909;
        int t_13924;
        boolean t_7738;
        FieldDef t_7746;
        SqlPart t_7751;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__896 = 0;
        while (true) {
            t_13892 = this._tableDef.getFields().size();
            if (i__896 >= t_13892) {
                break;
            }
            FieldDef f__897 = Core.listGet(this._tableDef.getFields(), i__896);
            if (!f__897.isNullable()) {
                t_13897 = f__897.getName().getSqlValue();
                t_13898 = this._changes.containsKey(t_13897);
                t_7738 = !t_13898;
            } else {
                t_7738 = false;
            }
            if (t_7738) {
                throw Core.bubble();
            }
            i__896 = i__896 + 1;
        }
        List<Entry<String, String>> pairs__898 = Core.mappedToList(this._changes);
        if (pairs__898.size() == 0) {
            throw Core.bubble();
        }
        List<String> colNames__899 = new ArrayList<>();
        List<SqlPart> valParts__900 = new ArrayList<>();
        int i__901 = 0;
        while (true) {
            t_13903 = pairs__898.size();
            if (i__901 >= t_13903) {
                break;
            }
            Entry<String, String> pair__902 = Core.listGet(pairs__898, i__901);
            t_13905 = pair__902.getKey();
            t_7746 = this._tableDef.field(t_13905);
            FieldDef fd__903 = t_7746;
            Core.listAdd(colNames__899, fd__903.getName().getSqlValue());
            t_13909 = pair__902.getValue();
            t_7751 = this.valueToSqlPart(fd__903, t_13909);
            Core.listAdd(valParts__900, t_7751);
            i__901 = i__901 + 1;
        }
        SqlBuilder b__904 = new SqlBuilder();
        b__904.appendSafe("INSERT INTO ");
        b__904.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__904.appendSafe(" (");
        List<String> t_13917 = List.copyOf(colNames__899);
        Function<String, String> fn__13890 = c__905 -> c__905;
        b__904.appendSafe(Core.listJoinObj(t_13917, ", ", fn__13890));
        b__904.appendSafe(") VALUES (");
        b__904.appendPart(Core.listGet(valParts__900, 0));
        int j__906 = 1;
        while (true) {
            t_13924 = valParts__900.size();
            if (j__906 >= t_13924) {
                break;
            }
            b__904.appendSafe(", ");
            b__904.appendPart(Core.listGet(valParts__900, j__906));
            j__906 = j__906 + 1;
        }
        b__904.appendSafe(")");
        return b__904.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__908) {
        int t_13877;
        String t_13880;
        String t_13885;
        FieldDef t_7719;
        SqlPart t_7725;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__910 = Core.mappedToList(this._changes);
        if (pairs__910.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__911 = new SqlBuilder();
        b__911.appendSafe("UPDATE ");
        b__911.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__911.appendSafe(" SET ");
        int i__912 = 0;
        while (true) {
            t_13877 = pairs__910.size();
            if (i__912 >= t_13877) {
                break;
            }
            if (i__912 > 0) {
                b__911.appendSafe(", ");
            }
            Entry<String, String> pair__913 = Core.listGet(pairs__910, i__912);
            t_13880 = pair__913.getKey();
            t_7719 = this._tableDef.field(t_13880);
            FieldDef fd__914 = t_7719;
            b__911.appendSafe(fd__914.getName().getSqlValue());
            b__911.appendSafe(" = ");
            t_13885 = pair__913.getValue();
            t_7725 = this.valueToSqlPart(fd__914, t_13885);
            b__911.appendPart(t_7725);
            i__912 = i__912 + 1;
        }
        b__911.appendSafe(" WHERE id = ");
        b__911.appendInt32(id__908);
        return b__911.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__916, Map<String, String> _params__917, Map<String, String> _changes__918, List<ChangesetError> _errors__919, boolean _isValid__920) {
        this._tableDef = _tableDef__916;
        this._params = _params__917;
        this._changes = _changes__918;
        this._errors = _errors__919;
        this._isValid = _isValid__920;
    }
}
