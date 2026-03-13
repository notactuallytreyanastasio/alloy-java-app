package orm.src;
import temper.core.Core;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
import temper.core.Nullable;
import java.util.function.Consumer;
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
    public Changeset cast(List<SafeIdentifier> allowedFields__774) {
        Map<String, String> mb__776 = new LinkedHashMap<>();
        Consumer<SafeIdentifier> fn__15462 = f__777 -> {
            String t_15460;
            String t_15457 = f__777.getSqlValue();
            String val__778 = this._params.getOrDefault(t_15457, "");
            if (!val__778.isEmpty()) {
                t_15460 = f__777.getSqlValue();
                mb__776.put(t_15460, val__778);
            }
        };
        allowedFields__774.forEach(fn__15462);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__776), this._errors, this._isValid);
    }
    public Changeset validateRequired(List<SafeIdentifier> fields__780) {
        Changeset return__422;
        List<ChangesetError> t_15455;
        TableDef t_8795;
        Map<String, String> t_8796;
        Map<String, String> t_8797;
        fn__781: {
            if (!this._isValid) {
                return__422 = this;
                break fn__781;
            }
            List<ChangesetError> eb__782 = new ArrayList<>(this._errors);
            class Local_1 {
                boolean valid__783 = true;
            }
            final Local_1 local$1 = new Local_1();
            Consumer<SafeIdentifier> fn__15451 = f__784 -> {
                ChangesetError t_15449;
                String t_15446 = f__784.getSqlValue();
                if (!this._changes.containsKey(t_15446)) {
                    t_15449 = new ChangesetError(f__784.getSqlValue(), "is required");
                    Core.listAdd(eb__782, t_15449);
                    local$1.valid__783 = false;
                }
            };
            fields__780.forEach(fn__15451);
            t_8795 = this._tableDef;
            t_8796 = this._params;
            t_8797 = this._changes;
            t_15455 = List.copyOf(eb__782);
            return__422 = new ChangesetImpl(t_8795, t_8796, t_8797, t_15455, local$1.valid__783);
        }
        return return__422;
    }
    public Changeset validateLength(SafeIdentifier field__786, int min__787, int max__788) {
        Changeset return__423;
        String t_15433;
        List<ChangesetError> t_15444;
        boolean t_8778;
        TableDef t_8784;
        Map<String, String> t_8785;
        Map<String, String> t_8786;
        fn__789: {
            if (!this._isValid) {
                return__423 = this;
                break fn__789;
            }
            t_15433 = field__786.getSqlValue();
            String val__790 = this._changes.getOrDefault(t_15433, "");
            int len__791 = Core.stringCountBetween(val__790, 0, val__790.length());
            if (len__791 < min__787) {
                t_8778 = true;
            } else {
                t_8778 = len__791 > max__788;
            }
            if (t_8778) {
                String msg__792 = "must be between " + Integer.toString(min__787) + " and " + Integer.toString(max__788) + " characters";
                List<ChangesetError> eb__793 = new ArrayList<>(this._errors);
                Core.listAdd(eb__793, new ChangesetError(field__786.getSqlValue(), msg__792));
                t_8784 = this._tableDef;
                t_8785 = this._params;
                t_8786 = this._changes;
                t_15444 = List.copyOf(eb__793);
                return__423 = new ChangesetImpl(t_8784, t_8785, t_8786, t_15444, false);
                break fn__789;
            }
            return__423 = this;
        }
        return return__423;
    }
    public Changeset validateInt(SafeIdentifier field__795) {
        Changeset return__424;
        String t_15424;
        List<ChangesetError> t_15431;
        TableDef t_8769;
        Map<String, String> t_8770;
        Map<String, String> t_8771;
        fn__796: {
            if (!this._isValid) {
                return__424 = this;
                break fn__796;
            }
            t_15424 = field__795.getSqlValue();
            String val__797 = this._changes.getOrDefault(t_15424, "");
            if (val__797.isEmpty()) {
                return__424 = this;
                break fn__796;
            }
            boolean parseOk__798;
            boolean parseOk_15574;
            try {
                Core.stringToInt(val__797);
                parseOk_15574 = true;
            } catch (RuntimeException ignored$1) {
                parseOk_15574 = false;
            }
            parseOk__798 = parseOk_15574;
            if (!parseOk__798) {
                List<ChangesetError> eb__799 = new ArrayList<>(this._errors);
                Core.listAdd(eb__799, new ChangesetError(field__795.getSqlValue(), "must be an integer"));
                t_8769 = this._tableDef;
                t_8770 = this._params;
                t_8771 = this._changes;
                t_15431 = List.copyOf(eb__799);
                return__424 = new ChangesetImpl(t_8769, t_8770, t_8771, t_15431, false);
                break fn__796;
            }
            return__424 = this;
        }
        return return__424;
    }
    public Changeset validateInt64(SafeIdentifier field__801) {
        Changeset return__425;
        String t_15415;
        List<ChangesetError> t_15422;
        TableDef t_8756;
        Map<String, String> t_8757;
        Map<String, String> t_8758;
        fn__802: {
            if (!this._isValid) {
                return__425 = this;
                break fn__802;
            }
            t_15415 = field__801.getSqlValue();
            String val__803 = this._changes.getOrDefault(t_15415, "");
            if (val__803.isEmpty()) {
                return__425 = this;
                break fn__802;
            }
            boolean parseOk__804;
            boolean parseOk_15576;
            try {
                Core.stringToInt64(val__803);
                parseOk_15576 = true;
            } catch (RuntimeException ignored$2) {
                parseOk_15576 = false;
            }
            parseOk__804 = parseOk_15576;
            if (!parseOk__804) {
                List<ChangesetError> eb__805 = new ArrayList<>(this._errors);
                Core.listAdd(eb__805, new ChangesetError(field__801.getSqlValue(), "must be a 64-bit integer"));
                t_8756 = this._tableDef;
                t_8757 = this._params;
                t_8758 = this._changes;
                t_15422 = List.copyOf(eb__805);
                return__425 = new ChangesetImpl(t_8756, t_8757, t_8758, t_15422, false);
                break fn__802;
            }
            return__425 = this;
        }
        return return__425;
    }
    public Changeset validateFloat(SafeIdentifier field__807) {
        Changeset return__426;
        String t_15406;
        List<ChangesetError> t_15413;
        TableDef t_8743;
        Map<String, String> t_8744;
        Map<String, String> t_8745;
        fn__808: {
            if (!this._isValid) {
                return__426 = this;
                break fn__808;
            }
            t_15406 = field__807.getSqlValue();
            String val__809 = this._changes.getOrDefault(t_15406, "");
            if (val__809.isEmpty()) {
                return__426 = this;
                break fn__808;
            }
            boolean parseOk__810;
            boolean parseOk_15578;
            try {
                Core.stringToFloat64(val__809);
                parseOk_15578 = true;
            } catch (RuntimeException ignored$3) {
                parseOk_15578 = false;
            }
            parseOk__810 = parseOk_15578;
            if (!parseOk__810) {
                List<ChangesetError> eb__811 = new ArrayList<>(this._errors);
                Core.listAdd(eb__811, new ChangesetError(field__807.getSqlValue(), "must be a number"));
                t_8743 = this._tableDef;
                t_8744 = this._params;
                t_8745 = this._changes;
                t_15413 = List.copyOf(eb__811);
                return__426 = new ChangesetImpl(t_8743, t_8744, t_8745, t_15413, false);
                break fn__808;
            }
            return__426 = this;
        }
        return return__426;
    }
    public Changeset validateBool(SafeIdentifier field__813) {
        Changeset return__427;
        String t_15397;
        List<ChangesetError> t_15404;
        boolean t_8718;
        boolean t_8719;
        boolean t_8721;
        boolean t_8722;
        boolean t_8724;
        TableDef t_8730;
        Map<String, String> t_8731;
        Map<String, String> t_8732;
        fn__814: {
            if (!this._isValid) {
                return__427 = this;
                break fn__814;
            }
            t_15397 = field__813.getSqlValue();
            String val__815 = this._changes.getOrDefault(t_15397, "");
            if (val__815.isEmpty()) {
                return__427 = this;
                break fn__814;
            }
            boolean isTrue__816;
            if (val__815.equals("true")) {
                isTrue__816 = true;
            } else {
                if (val__815.equals("1")) {
                    t_8719 = true;
                } else {
                    if (val__815.equals("yes")) {
                        t_8718 = true;
                    } else {
                        t_8718 = val__815.equals("on");
                    }
                    t_8719 = t_8718;
                }
                isTrue__816 = t_8719;
            }
            boolean isFalse__817;
            if (val__815.equals("false")) {
                isFalse__817 = true;
            } else {
                if (val__815.equals("0")) {
                    t_8722 = true;
                } else {
                    if (val__815.equals("no")) {
                        t_8721 = true;
                    } else {
                        t_8721 = val__815.equals("off");
                    }
                    t_8722 = t_8721;
                }
                isFalse__817 = t_8722;
            }
            if (!isTrue__816) {
                t_8724 = !isFalse__817;
            } else {
                t_8724 = false;
            }
            if (t_8724) {
                List<ChangesetError> eb__818 = new ArrayList<>(this._errors);
                Core.listAdd(eb__818, new ChangesetError(field__813.getSqlValue(), "must be a boolean (true/false/1/0/yes/no/on/off)"));
                t_8730 = this._tableDef;
                t_8731 = this._params;
                t_8732 = this._changes;
                t_15404 = List.copyOf(eb__818);
                return__427 = new ChangesetImpl(t_8730, t_8731, t_8732, t_15404, false);
                break fn__814;
            }
            return__427 = this;
        }
        return return__427;
    }
    public Changeset putChange(SafeIdentifier field__820, String value__821) {
        int t_15385;
        Map<String, String> mb__823 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__824 = Core.mappedToList(this._changes);
        int i__825 = 0;
        while (true) {
            t_15385 = pairs__824.size();
            if (i__825 >= t_15385) {
                break;
            }
            mb__823.put(Core.listGet(pairs__824, i__825).getKey(), Core.listGet(pairs__824, i__825).getValue());
            i__825 = i__825 + 1;
        }
        mb__823.put(field__820.getSqlValue(), value__821);
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__823), this._errors, this._isValid);
    }
    public String getChange(SafeIdentifier field__827) {
        String t_15379 = field__827.getSqlValue();
        if (!this._changes.containsKey(t_15379)) {
            throw Core.bubble();
        }
        String t_15381 = field__827.getSqlValue();
        return this._changes.getOrDefault(t_15381, "");
    }
    public Changeset deleteChange(SafeIdentifier field__830) {
        int t_15366;
        Map<String, String> mb__832 = new LinkedHashMap<>();
        List<Entry<String, String>> pairs__833 = Core.mappedToList(this._changes);
        int i__834 = 0;
        while (true) {
            t_15366 = pairs__833.size();
            if (i__834 >= t_15366) {
                break;
            }
            if (!Core.listGet(pairs__833, i__834).getKey().equals(field__830.getSqlValue())) {
                mb__832.put(Core.listGet(pairs__833, i__834).getKey(), Core.listGet(pairs__833, i__834).getValue());
            }
            i__834 = i__834 + 1;
        }
        return new ChangesetImpl(this._tableDef, this._params, Core.mappedToMap(mb__832), this._errors, this._isValid);
    }
    public Changeset validateInclusion(SafeIdentifier field__836, List<String> allowed__837) {
        Changeset return__431;
        String t_15352;
        String t_15354;
        List<ChangesetError> t_15362;
        TableDef t_8680;
        Map<String, String> t_8681;
        Map<String, String> t_8682;
        fn__838: {
            if (!this._isValid) {
                return__431 = this;
                break fn__838;
            }
            t_15352 = field__836.getSqlValue();
            if (!this._changes.containsKey(t_15352)) {
                return__431 = this;
                break fn__838;
            }
            t_15354 = field__836.getSqlValue();
            String val__839 = this._changes.getOrDefault(t_15354, "");
            class Local_2 {
                boolean found__840 = false;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<String> fn__15351 = a__841 -> {
                if (a__841.equals(val__839)) {
                    local$2.found__840 = true;
                }
            };
            allowed__837.forEach(fn__15351);
            if (!local$2.found__840) {
                List<ChangesetError> eb__842 = new ArrayList<>(this._errors);
                Core.listAdd(eb__842, new ChangesetError(field__836.getSqlValue(), "is not included in the list"));
                t_8680 = this._tableDef;
                t_8681 = this._params;
                t_8682 = this._changes;
                t_15362 = List.copyOf(eb__842);
                return__431 = new ChangesetImpl(t_8680, t_8681, t_8682, t_15362, false);
                break fn__838;
            }
            return__431 = this;
        }
        return return__431;
    }
    public Changeset validateExclusion(SafeIdentifier field__844, List<String> disallowed__845) {
        Changeset return__432;
        String t_15339;
        String t_15341;
        List<ChangesetError> t_15349;
        TableDef t_8666;
        Map<String, String> t_8667;
        Map<String, String> t_8668;
        fn__846: {
            if (!this._isValid) {
                return__432 = this;
                break fn__846;
            }
            t_15339 = field__844.getSqlValue();
            if (!this._changes.containsKey(t_15339)) {
                return__432 = this;
                break fn__846;
            }
            t_15341 = field__844.getSqlValue();
            String val__847 = this._changes.getOrDefault(t_15341, "");
            class Local_3 {
                boolean found__848 = false;
            }
            final Local_3 local$3 = new Local_3();
            Consumer<String> fn__15338 = d__849 -> {
                if (d__849.equals(val__847)) {
                    local$3.found__848 = true;
                }
            };
            disallowed__845.forEach(fn__15338);
            if (local$3.found__848) {
                List<ChangesetError> eb__850 = new ArrayList<>(this._errors);
                Core.listAdd(eb__850, new ChangesetError(field__844.getSqlValue(), "is reserved"));
                t_8666 = this._tableDef;
                t_8667 = this._params;
                t_8668 = this._changes;
                t_15349 = List.copyOf(eb__850);
                return__432 = new ChangesetImpl(t_8666, t_8667, t_8668, t_15349, false);
                break fn__846;
            }
            return__432 = this;
        }
        return return__432;
    }
    public Changeset validateNumber(SafeIdentifier field__852, NumberValidationOpts opts__853) {
        Changeset return__433;
        String t_15288;
        String t_15290;
        List<ChangesetError> t_15296;
        List<ChangesetError> t_15304;
        List<ChangesetError> t_15312;
        List<ChangesetError> t_15320;
        List<ChangesetError> t_15328;
        List<ChangesetError> t_15336;
        TableDef t_8599;
        Map<String, String> t_8600;
        Map<String, String> t_8601;
        double t_8603;
        TableDef t_8612;
        Map<String, String> t_8613;
        Map<String, String> t_8614;
        TableDef t_8622;
        Map<String, String> t_8623;
        Map<String, String> t_8624;
        TableDef t_8632;
        Map<String, String> t_8633;
        Map<String, String> t_8634;
        TableDef t_8642;
        Map<String, String> t_8643;
        Map<String, String> t_8644;
        TableDef t_8652;
        Map<String, String> t_8653;
        Map<String, String> t_8654;
        fn__854: {
            if (!this._isValid) {
                return__433 = this;
                break fn__854;
            }
            t_15288 = field__852.getSqlValue();
            if (!this._changes.containsKey(t_15288)) {
                return__433 = this;
                break fn__854;
            }
            t_15290 = field__852.getSqlValue();
            String val__855 = this._changes.getOrDefault(t_15290, "");
            boolean parseOk__856;
            boolean parseOk_15582;
            try {
                Core.stringToFloat64(val__855);
                parseOk_15582 = true;
            } catch (RuntimeException ignored$4) {
                parseOk_15582 = false;
            }
            parseOk__856 = parseOk_15582;
            if (!parseOk__856) {
                List<ChangesetError> eb__857 = new ArrayList<>(this._errors);
                Core.listAdd(eb__857, new ChangesetError(field__852.getSqlValue(), "must be a number"));
                t_8599 = this._tableDef;
                t_8600 = this._params;
                t_8601 = this._changes;
                t_15296 = List.copyOf(eb__857);
                return__433 = new ChangesetImpl(t_8599, t_8600, t_8601, t_15296, false);
                break fn__854;
            }
            double num__858;
            double num_15583;
            try {
                t_8603 = Core.stringToFloat64(val__855);
                num_15583 = t_8603;
            } catch (RuntimeException ignored$5) {
                num_15583 = 0.0D;
            }
            num__858 = num_15583;
            @Nullable Double gt__859 = opts__853.getGreaterThan();
            if (gt__859 != null) {
                double gt_2610 = gt__859;
                if (Double.doubleToLongBits(num__858) <= Double.doubleToLongBits(gt_2610)) {
                    List<ChangesetError> eb__860 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__860, new ChangesetError(field__852.getSqlValue(), "must be greater than " + Core.float64ToString(gt_2610)));
                    t_8612 = this._tableDef;
                    t_8613 = this._params;
                    t_8614 = this._changes;
                    t_15304 = List.copyOf(eb__860);
                    return__433 = new ChangesetImpl(t_8612, t_8613, t_8614, t_15304, false);
                    break fn__854;
                }
            }
            @Nullable Double lt__861 = opts__853.getLessThan();
            if (lt__861 != null) {
                double lt_2611 = lt__861;
                if (Double.doubleToLongBits(num__858) >= Double.doubleToLongBits(lt_2611)) {
                    List<ChangesetError> eb__862 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__862, new ChangesetError(field__852.getSqlValue(), "must be less than " + Core.float64ToString(lt_2611)));
                    t_8622 = this._tableDef;
                    t_8623 = this._params;
                    t_8624 = this._changes;
                    t_15312 = List.copyOf(eb__862);
                    return__433 = new ChangesetImpl(t_8622, t_8623, t_8624, t_15312, false);
                    break fn__854;
                }
            }
            @Nullable Double gte__863 = opts__853.getGreaterThanOrEqual();
            if (gte__863 != null) {
                double gte_2612 = gte__863;
                if (Double.doubleToLongBits(num__858) < Double.doubleToLongBits(gte_2612)) {
                    List<ChangesetError> eb__864 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__864, new ChangesetError(field__852.getSqlValue(), "must be greater than or equal to " + Core.float64ToString(gte_2612)));
                    t_8632 = this._tableDef;
                    t_8633 = this._params;
                    t_8634 = this._changes;
                    t_15320 = List.copyOf(eb__864);
                    return__433 = new ChangesetImpl(t_8632, t_8633, t_8634, t_15320, false);
                    break fn__854;
                }
            }
            @Nullable Double lte__865 = opts__853.getLessThanOrEqual();
            if (lte__865 != null) {
                double lte_2613 = lte__865;
                if (Double.doubleToLongBits(num__858) > Double.doubleToLongBits(lte_2613)) {
                    List<ChangesetError> eb__866 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__866, new ChangesetError(field__852.getSqlValue(), "must be less than or equal to " + Core.float64ToString(lte_2613)));
                    t_8642 = this._tableDef;
                    t_8643 = this._params;
                    t_8644 = this._changes;
                    t_15328 = List.copyOf(eb__866);
                    return__433 = new ChangesetImpl(t_8642, t_8643, t_8644, t_15328, false);
                    break fn__854;
                }
            }
            @Nullable Double eq__867 = opts__853.getEqualTo();
            if (eq__867 != null) {
                double eq_2614 = eq__867;
                if (Double.doubleToLongBits(num__858) != Double.doubleToLongBits(eq_2614)) {
                    List<ChangesetError> eb__868 = new ArrayList<>(this._errors);
                    Core.listAdd(eb__868, new ChangesetError(field__852.getSqlValue(), "must be equal to " + Core.float64ToString(eq_2614)));
                    t_8652 = this._tableDef;
                    t_8653 = this._params;
                    t_8654 = this._changes;
                    t_15336 = List.copyOf(eb__868);
                    return__433 = new ChangesetImpl(t_8652, t_8653, t_8654, t_15336, false);
                    break fn__854;
                }
            }
            return__433 = this;
        }
        return return__433;
    }
    public Changeset validateAcceptance(SafeIdentifier field__870) {
        Changeset return__434;
        String t_15278;
        String t_15280;
        List<ChangesetError> t_15286;
        boolean t_8577;
        boolean t_8578;
        TableDef t_8585;
        Map<String, String> t_8586;
        Map<String, String> t_8587;
        fn__871: {
            if (!this._isValid) {
                return__434 = this;
                break fn__871;
            }
            t_15278 = field__870.getSqlValue();
            if (!this._changes.containsKey(t_15278)) {
                return__434 = this;
                break fn__871;
            }
            t_15280 = field__870.getSqlValue();
            String val__872 = this._changes.getOrDefault(t_15280, "");
            boolean accepted__873;
            if (val__872.equals("true")) {
                accepted__873 = true;
            } else {
                if (val__872.equals("1")) {
                    t_8578 = true;
                } else {
                    if (val__872.equals("yes")) {
                        t_8577 = true;
                    } else {
                        t_8577 = val__872.equals("on");
                    }
                    t_8578 = t_8577;
                }
                accepted__873 = t_8578;
            }
            if (!accepted__873) {
                List<ChangesetError> eb__874 = new ArrayList<>(this._errors);
                Core.listAdd(eb__874, new ChangesetError(field__870.getSqlValue(), "must be accepted"));
                t_8585 = this._tableDef;
                t_8586 = this._params;
                t_8587 = this._changes;
                t_15286 = List.copyOf(eb__874);
                return__434 = new ChangesetImpl(t_8585, t_8586, t_8587, t_15286, false);
                break fn__871;
            }
            return__434 = this;
        }
        return return__434;
    }
    public Changeset validateConfirmation(SafeIdentifier field__876, SafeIdentifier confirmationField__877) {
        Changeset return__435;
        String t_15266;
        String t_15268;
        String t_15270;
        List<ChangesetError> t_15276;
        TableDef t_8569;
        Map<String, String> t_8570;
        Map<String, String> t_8571;
        fn__878: {
            if (!this._isValid) {
                return__435 = this;
                break fn__878;
            }
            t_15266 = field__876.getSqlValue();
            if (!this._changes.containsKey(t_15266)) {
                return__435 = this;
                break fn__878;
            }
            t_15268 = field__876.getSqlValue();
            String val__879 = this._changes.getOrDefault(t_15268, "");
            t_15270 = confirmationField__877.getSqlValue();
            String conf__880 = this._changes.getOrDefault(t_15270, "");
            if (!val__879.equals(conf__880)) {
                List<ChangesetError> eb__881 = new ArrayList<>(this._errors);
                Core.listAdd(eb__881, new ChangesetError(confirmationField__877.getSqlValue(), "does not match"));
                t_8569 = this._tableDef;
                t_8570 = this._params;
                t_8571 = this._changes;
                t_15276 = List.copyOf(eb__881);
                return__435 = new ChangesetImpl(t_8569, t_8570, t_8571, t_15276, false);
                break fn__878;
            }
            return__435 = this;
        }
        return return__435;
    }
    public Changeset validateContains(SafeIdentifier field__883, String substring__884) {
        Changeset return__436;
        String t_15254;
        String t_15256;
        List<ChangesetError> t_15264;
        TableDef t_8554;
        Map<String, String> t_8555;
        Map<String, String> t_8556;
        fn__885: {
            if (!this._isValid) {
                return__436 = this;
                break fn__885;
            }
            t_15254 = field__883.getSqlValue();
            if (!this._changes.containsKey(t_15254)) {
                return__436 = this;
                break fn__885;
            }
            t_15256 = field__883.getSqlValue();
            String val__886 = this._changes.getOrDefault(t_15256, "");
            if (val__886.indexOf(substring__884) < 0) {
                List<ChangesetError> eb__887 = new ArrayList<>(this._errors);
                Core.listAdd(eb__887, new ChangesetError(field__883.getSqlValue(), "must contain the given substring"));
                t_8554 = this._tableDef;
                t_8555 = this._params;
                t_8556 = this._changes;
                t_15264 = List.copyOf(eb__887);
                return__436 = new ChangesetImpl(t_8554, t_8555, t_8556, t_15264, false);
                break fn__885;
            }
            return__436 = this;
        }
        return return__436;
    }
    public Changeset validateStartsWith(SafeIdentifier field__889, String prefix__890) {
        Changeset return__437;
        String t_15241;
        String t_15243;
        int t_15247;
        List<ChangesetError> t_15252;
        TableDef t_8538;
        Map<String, String> t_8539;
        Map<String, String> t_8540;
        fn__891: {
            if (!this._isValid) {
                return__437 = this;
                break fn__891;
            }
            t_15241 = field__889.getSqlValue();
            if (!this._changes.containsKey(t_15241)) {
                return__437 = this;
                break fn__891;
            }
            t_15243 = field__889.getSqlValue();
            String val__892 = this._changes.getOrDefault(t_15243, "");
            int idx__893 = val__892.indexOf(prefix__890);
            boolean starts__894;
            if (idx__893 >= 0) {
                t_15247 = Core.stringCountBetween(val__892, 0, Core.requireStringIndex(idx__893));
                starts__894 = t_15247 == 0;
            } else {
                starts__894 = false;
            }
            if (!starts__894) {
                List<ChangesetError> eb__895 = new ArrayList<>(this._errors);
                Core.listAdd(eb__895, new ChangesetError(field__889.getSqlValue(), "must start with the given prefix"));
                t_8538 = this._tableDef;
                t_8539 = this._params;
                t_8540 = this._changes;
                t_15252 = List.copyOf(eb__895);
                return__437 = new ChangesetImpl(t_8538, t_8539, t_8540, t_15252, false);
                break fn__891;
            }
            return__437 = this;
        }
        return return__437;
    }
    public Changeset validateEndsWith(SafeIdentifier field__897, String suffix__898) {
        Changeset return__438;
        String t_15213;
        String t_15215;
        int t_15220;
        List<ChangesetError> t_15226;
        int t_15228;
        boolean t_15229;
        int t_15233;
        int t_15234;
        List<ChangesetError> t_15239;
        TableDef t_8503;
        Map<String, String> t_8504;
        Map<String, String> t_8505;
        boolean t_8509;
        TableDef t_8520;
        Map<String, String> t_8521;
        Map<String, String> t_8522;
        fn__899: {
            if (!this._isValid) {
                return__438 = this;
                break fn__899;
            }
            t_15213 = field__897.getSqlValue();
            if (!this._changes.containsKey(t_15213)) {
                return__438 = this;
                break fn__899;
            }
            t_15215 = field__897.getSqlValue();
            String val__900 = this._changes.getOrDefault(t_15215, "");
            int valLen__901 = Core.stringCountBetween(val__900, 0, val__900.length());
            t_15220 = suffix__898.length();
            int suffixLen__902 = Core.stringCountBetween(suffix__898, 0, t_15220);
            if (valLen__901 < suffixLen__902) {
                List<ChangesetError> eb__903 = new ArrayList<>(this._errors);
                Core.listAdd(eb__903, new ChangesetError(field__897.getSqlValue(), "must end with the given suffix"));
                t_8503 = this._tableDef;
                t_8504 = this._params;
                t_8505 = this._changes;
                t_15226 = List.copyOf(eb__903);
                return__438 = new ChangesetImpl(t_8503, t_8504, t_8505, t_15226, false);
                break fn__899;
            }
            int skipCount__904 = valLen__901 - suffixLen__902;
            int strIdx__905 = 0;
            int i__906 = 0;
            while (i__906 < skipCount__904) {
                t_15228 = Core.stringNext(val__900, strIdx__905);
                strIdx__905 = t_15228;
                i__906 = i__906 + 1;
            }
            int sufIdx__907 = 0;
            boolean matches__908 = true;
            while (true) {
                if (matches__908) {
                    t_15229 = Core.stringHasIndex(suffix__898, sufIdx__907);
                    t_8509 = t_15229;
                } else {
                    t_8509 = false;
                }
                if (!t_8509) {
                    break;
                }
                if (!Core.stringHasIndex(val__900, strIdx__905)) {
                    matches__908 = false;
                } else if (val__900.codePointAt(strIdx__905) != suffix__898.codePointAt(sufIdx__907)) {
                    matches__908 = false;
                } else {
                    t_15233 = Core.stringNext(val__900, strIdx__905);
                    strIdx__905 = t_15233;
                    t_15234 = Core.stringNext(suffix__898, sufIdx__907);
                    sufIdx__907 = t_15234;
                }
            }
            if (!matches__908) {
                List<ChangesetError> eb__909 = new ArrayList<>(this._errors);
                Core.listAdd(eb__909, new ChangesetError(field__897.getSqlValue(), "must end with the given suffix"));
                t_8520 = this._tableDef;
                t_8521 = this._params;
                t_8522 = this._changes;
                t_15239 = List.copyOf(eb__909);
                return__438 = new ChangesetImpl(t_8520, t_8521, t_8522, t_15239, false);
                break fn__899;
            }
            return__438 = this;
        }
        return return__438;
    }
    SqlBoolean parseBoolSqlPart(String val__911) {
        SqlBoolean return__439;
        boolean t_8480;
        boolean t_8481;
        boolean t_8482;
        boolean t_8484;
        boolean t_8485;
        boolean t_8486;
        fn__912: {
            if (val__911.equals("true")) {
                t_8482 = true;
            } else {
                if (val__911.equals("1")) {
                    t_8481 = true;
                } else {
                    if (val__911.equals("yes")) {
                        t_8480 = true;
                    } else {
                        t_8480 = val__911.equals("on");
                    }
                    t_8481 = t_8480;
                }
                t_8482 = t_8481;
            }
            if (t_8482) {
                return__439 = new SqlBoolean(true);
                break fn__912;
            }
            if (val__911.equals("false")) {
                t_8486 = true;
            } else {
                if (val__911.equals("0")) {
                    t_8485 = true;
                } else {
                    if (val__911.equals("no")) {
                        t_8484 = true;
                    } else {
                        t_8484 = val__911.equals("off");
                    }
                    t_8485 = t_8484;
                }
                t_8486 = t_8485;
            }
            if (t_8486) {
                return__439 = new SqlBoolean(false);
                break fn__912;
            }
            throw Core.bubble();
        }
        return return__439;
    }
    SqlPart valueToSqlPart(FieldDef fieldDef__914, String val__915) {
        SqlPart return__440;
        int t_8467;
        long t_8470;
        double t_8473;
        LocalDate t_8478;
        fn__916: {
            FieldType ft__917 = fieldDef__914.getFieldType();
            if (ft__917 instanceof StringField) {
                return__440 = new SqlString(val__915);
                break fn__916;
            }
            if (ft__917 instanceof IntField) {
                t_8467 = Core.stringToInt(val__915);
                return__440 = new SqlInt32(t_8467);
                break fn__916;
            }
            if (ft__917 instanceof Int64Field) {
                t_8470 = Core.stringToInt64(val__915);
                return__440 = new SqlInt64(t_8470);
                break fn__916;
            }
            if (ft__917 instanceof FloatField) {
                t_8473 = Core.stringToFloat64(val__915);
                return__440 = new SqlFloat64(t_8473);
                break fn__916;
            }
            if (ft__917 instanceof BoolField) {
                return__440 = this.parseBoolSqlPart(val__915);
                break fn__916;
            }
            if (ft__917 instanceof DateField) {
                t_8478 = LocalDate.parse(val__915);
                return__440 = new SqlDate(t_8478);
                break fn__916;
            }
            throw Core.bubble();
        }
        return return__440;
    }
    public SqlFragment toInsertSql() {
        int t_15145;
        String t_15152;
        int t_15157;
        String t_15159;
        String t_15164;
        int t_15167;
        String t_15173;
        int t_15193;
        boolean t_8417;
        boolean t_8418;
        FieldDef t_8425;
        SqlPart t_8431;
        if (!this._isValid) {
            throw Core.bubble();
        }
        int i__920 = 0;
        while (true) {
            continue_15556: {
                t_15145 = this._tableDef.getFields().size();
                if (i__920 >= t_15145) {
                    break;
                }
                FieldDef f__921 = Core.listGet(this._tableDef.getFields(), i__920);
                if (f__921.isVirtual()) {
                    break continue_15556;
                }
                @Nullable SqlPart dv__922 = f__921.getDefaultValue();
                if (!f__921.isNullable()) {
                    t_15152 = f__921.getName().getSqlValue();
                    if (!this._changes.containsKey(t_15152)) {
                        t_8417 = dv__922 == null;
                    } else {
                        t_8417 = false;
                    }
                    t_8418 = t_8417;
                } else {
                    t_8418 = false;
                }
                if (t_8418) {
                    throw Core.bubble();
                }
            }
            i__920 = i__920 + 1;
        }
        List<String> colNames__923 = new ArrayList<>();
        List<SqlPart> valParts__924 = new ArrayList<>();
        List<Entry<String, String>> pairs__925 = Core.mappedToList(this._changes);
        int i__926 = 0;
        while (true) {
            continue_15557: {
                t_15157 = pairs__925.size();
                if (i__926 >= t_15157) {
                    break;
                }
                Entry<String, String> pair__927 = Core.listGet(pairs__925, i__926);
                t_15159 = pair__927.getKey();
                t_8425 = this._tableDef.field(t_15159);
                FieldDef fd__928 = t_8425;
                if (fd__928.isVirtual()) {
                    break continue_15557;
                }
                Core.listAdd(colNames__923, fd__928.getName().getSqlValue());
                t_15164 = pair__927.getValue();
                t_8431 = this.valueToSqlPart(fd__928, t_15164);
                Core.listAdd(valParts__924, t_8431);
            }
            i__926 = i__926 + 1;
        }
        int i__929 = 0;
        while (true) {
            continue_15558: {
                t_15167 = this._tableDef.getFields().size();
                if (i__929 >= t_15167) {
                    break;
                }
                FieldDef f__930 = Core.listGet(this._tableDef.getFields(), i__929);
                if (f__930.isVirtual()) {
                    break continue_15558;
                }
                @Nullable SqlPart dv__931 = f__930.getDefaultValue();
                if (dv__931 != null) {
                    SqlPart dv_2622 = dv__931;
                    t_15173 = f__930.getName().getSqlValue();
                    if (!this._changes.containsKey(t_15173)) {
                        Core.listAdd(colNames__923, f__930.getName().getSqlValue());
                        Core.listAdd(valParts__924, dv_2622);
                    }
                }
            }
            i__929 = i__929 + 1;
        }
        if (valParts__924.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__932 = new SqlBuilder();
        b__932.appendSafe("INSERT INTO ");
        b__932.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__932.appendSafe(" (");
        List<String> t_15186 = List.copyOf(colNames__923);
        Function<String, String> fn__15143 = c__933 -> c__933;
        b__932.appendSafe(Core.listJoinObj(t_15186, ", ", fn__15143));
        b__932.appendSafe(") VALUES (");
        b__932.appendPart(Core.listGet(valParts__924, 0));
        int j__934 = 1;
        while (true) {
            t_15193 = valParts__924.size();
            if (j__934 >= t_15193) {
                break;
            }
            b__932.appendSafe(", ");
            b__932.appendPart(Core.listGet(valParts__924, j__934));
            j__934 = j__934 + 1;
        }
        b__932.appendSafe(")");
        return b__932.getAccumulated();
    }
    public SqlFragment toUpdateSql(int id__936) {
        int t_15126;
        String t_15128;
        String t_15135;
        FieldDef t_8392;
        SqlPart t_8399;
        if (!this._isValid) {
            throw Core.bubble();
        }
        List<Entry<String, String>> pairs__938 = Core.mappedToList(this._changes);
        if (pairs__938.size() == 0) {
            throw Core.bubble();
        }
        SqlBuilder b__939 = new SqlBuilder();
        b__939.appendSafe("UPDATE ");
        b__939.appendSafe(this._tableDef.getTableName().getSqlValue());
        b__939.appendSafe(" SET ");
        int setCount__940 = 0;
        int i__941 = 0;
        while (true) {
            continue_15559: {
                t_15126 = pairs__938.size();
                if (i__941 >= t_15126) {
                    break;
                }
                Entry<String, String> pair__942 = Core.listGet(pairs__938, i__941);
                t_15128 = pair__942.getKey();
                t_8392 = this._tableDef.field(t_15128);
                FieldDef fd__943 = t_8392;
                if (fd__943.isVirtual()) {
                    break continue_15559;
                }
                if (setCount__940 > 0) {
                    b__939.appendSafe(", ");
                }
                b__939.appendSafe(fd__943.getName().getSqlValue());
                b__939.appendSafe(" = ");
                t_15135 = pair__942.getValue();
                t_8399 = this.valueToSqlPart(fd__943, t_15135);
                b__939.appendPart(t_8399);
                setCount__940 = setCount__940 + 1;
            }
            i__941 = i__941 + 1;
        }
        if (setCount__940 == 0) {
            throw Core.bubble();
        }
        b__939.appendSafe(" WHERE ");
        b__939.appendSafe(this._tableDef.pkName());
        b__939.appendSafe(" = ");
        b__939.appendInt32(id__936);
        return b__939.getAccumulated();
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
    public ChangesetImpl(TableDef _tableDef__945, Map<String, String> _params__946, Map<String, String> _changes__947, List<ChangesetError> _errors__948, boolean _isValid__949) {
        this._tableDef = _tableDef__945;
        this._params = _params__946;
        this._changes = _changes__947;
        this._errors = _errors__948;
        this._isValid = _isValid__949;
    }
}
