package orm.src;
import temper.std.testing.Test;
import java.util.function.Supplier;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import temper.core.Core;
import java.util.Map;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
public final class SrcTest {
    private SrcTest() {
    }
    static SafeIdentifier csid__636(String name__924) {
        SafeIdentifier t_7681;
        t_7681 = SrcGlobal.safeIdentifier(name__924);
        return t_7681;
    }
    static TableDef userTable__637() {
        return new TableDef(SrcTest.csid__636("users"), List.of(new FieldDef(SrcTest.csid__636("name"), new StringField(), false), new FieldDef(SrcTest.csid__636("email"), new StringField(), false), new FieldDef(SrcTest.csid__636("age"), new IntField(), true), new FieldDef(SrcTest.csid__636("score"), new FloatField(), true), new FieldDef(SrcTest.csid__636("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__2009() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__928 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_13823 = SrcTest.userTable__637();
            SafeIdentifier t_13824 = SrcTest.csid__636("name");
            SafeIdentifier t_13825 = SrcTest.csid__636("email");
            Changeset cs__929 = SrcGlobal.changeset(t_13823, params__928).cast(List.of(t_13824, t_13825));
            boolean t_13828 = cs__929.getChanges().containsKey("name");
            Supplier<String> fn__13818 = () -> "name should be in changes";
            test_31.assert_(t_13828, fn__13818);
            boolean t_13832 = cs__929.getChanges().containsKey("email");
            Supplier<String> fn__13817 = () -> "email should be in changes";
            test_31.assert_(t_13832, fn__13817);
            boolean t_13838 = !cs__929.getChanges().containsKey("admin");
            Supplier<String> fn__13816 = () -> "admin must be dropped (not in whitelist)";
            test_31.assert_(t_13838, fn__13816);
            boolean t_13840 = cs__929.isValid();
            Supplier<String> fn__13815 = () -> "should still be valid";
            test_31.assert_(t_13840, fn__13815);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__2010() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__931 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_13801 = SrcTest.userTable__637();
            SafeIdentifier t_13802 = SrcTest.csid__636("name");
            Changeset cs__932 = SrcGlobal.changeset(t_13801, params__931).cast(List.of(t_13802)).cast(List.of(SrcTest.csid__636("email")));
            boolean t_13809 = !cs__932.getChanges().containsKey("name");
            Supplier<String> fn__13797 = () -> "name must be excluded by second cast";
            test_32.assert_(t_13809, fn__13797);
            boolean t_13812 = cs__932.getChanges().containsKey("email");
            Supplier<String> fn__13796 = () -> "email should be present";
            test_32.assert_(t_13812, fn__13796);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__2011() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__934 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_13783 = SrcTest.userTable__637();
            SafeIdentifier t_13784 = SrcTest.csid__636("name");
            SafeIdentifier t_13785 = SrcTest.csid__636("email");
            Changeset cs__935 = SrcGlobal.changeset(t_13783, params__934).cast(List.of(t_13784, t_13785));
            boolean t_13790 = !cs__935.getChanges().containsKey("name");
            Supplier<String> fn__13779 = () -> "empty name should not be in changes";
            test_33.assert_(t_13790, fn__13779);
            boolean t_13793 = cs__935.getChanges().containsKey("email");
            Supplier<String> fn__13778 = () -> "email should be in changes";
            test_33.assert_(t_13793, fn__13778);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__2012() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__937 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13765 = SrcTest.userTable__637();
            SafeIdentifier t_13766 = SrcTest.csid__636("name");
            Changeset cs__938 = SrcGlobal.changeset(t_13765, params__937).cast(List.of(t_13766)).validateRequired(List.of(SrcTest.csid__636("name")));
            boolean t_13770 = cs__938.isValid();
            Supplier<String> fn__13762 = () -> "should be valid";
            test_34.assert_(t_13770, fn__13762);
            boolean t_13776 = cs__938.getErrors().size() == 0;
            Supplier<String> fn__13761 = () -> "no errors expected";
            test_34.assert_(t_13776, fn__13761);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__2013() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__940 = Core.mapConstructor(List.of());
            TableDef t_13741 = SrcTest.userTable__637();
            SafeIdentifier t_13742 = SrcTest.csid__636("name");
            Changeset cs__941 = SrcGlobal.changeset(t_13741, params__940).cast(List.of(t_13742)).validateRequired(List.of(SrcTest.csid__636("name")));
            boolean t_13748 = !cs__941.isValid();
            Supplier<String> fn__13739 = () -> "should be invalid";
            test_35.assert_(t_13748, fn__13739);
            boolean t_13753 = cs__941.getErrors().size() == 1;
            Supplier<String> fn__13738 = () -> "should have one error";
            test_35.assert_(t_13753, fn__13738);
            boolean t_13759 = Core.listGet(cs__941.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__13737 = () -> "error should name the field";
            test_35.assert_(t_13759, fn__13737);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__2014() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__943 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13729 = SrcTest.userTable__637();
            SafeIdentifier t_13730 = SrcTest.csid__636("name");
            Changeset cs__944 = SrcGlobal.changeset(t_13729, params__943).cast(List.of(t_13730)).validateLength(SrcTest.csid__636("name"), 2, 50);
            boolean t_13734 = cs__944.isValid();
            Supplier<String> fn__13726 = () -> "should be valid";
            test_36.assert_(t_13734, fn__13726);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__2015() {
        Test test_37 = new Test();
        try {
            Map<String, String> params__946 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_13717 = SrcTest.userTable__637();
            SafeIdentifier t_13718 = SrcTest.csid__636("name");
            Changeset cs__947 = SrcGlobal.changeset(t_13717, params__946).cast(List.of(t_13718)).validateLength(SrcTest.csid__636("name"), 2, 50);
            boolean t_13724 = !cs__947.isValid();
            Supplier<String> fn__13714 = () -> "should be invalid";
            test_37.assert_(t_13724, fn__13714);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__2016() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__949 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_13705 = SrcTest.userTable__637();
            SafeIdentifier t_13706 = SrcTest.csid__636("name");
            Changeset cs__950 = SrcGlobal.changeset(t_13705, params__949).cast(List.of(t_13706)).validateLength(SrcTest.csid__636("name"), 2, 10);
            boolean t_13712 = !cs__950.isValid();
            Supplier<String> fn__13702 = () -> "should be invalid";
            test_38.assert_(t_13712, fn__13702);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__2017() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__952 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_13694 = SrcTest.userTable__637();
            SafeIdentifier t_13695 = SrcTest.csid__636("age");
            Changeset cs__953 = SrcGlobal.changeset(t_13694, params__952).cast(List.of(t_13695)).validateInt(SrcTest.csid__636("age"));
            boolean t_13699 = cs__953.isValid();
            Supplier<String> fn__13691 = () -> "should be valid";
            test_39.assert_(t_13699, fn__13691);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__2018() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__955 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_13682 = SrcTest.userTable__637();
            SafeIdentifier t_13683 = SrcTest.csid__636("age");
            Changeset cs__956 = SrcGlobal.changeset(t_13682, params__955).cast(List.of(t_13683)).validateInt(SrcTest.csid__636("age"));
            boolean t_13689 = !cs__956.isValid();
            Supplier<String> fn__13679 = () -> "should be invalid";
            test_40.assert_(t_13689, fn__13679);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__2019() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__958 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_13671 = SrcTest.userTable__637();
            SafeIdentifier t_13672 = SrcTest.csid__636("score");
            Changeset cs__959 = SrcGlobal.changeset(t_13671, params__958).cast(List.of(t_13672)).validateFloat(SrcTest.csid__636("score"));
            boolean t_13676 = cs__959.isValid();
            Supplier<String> fn__13668 = () -> "should be valid";
            test_41.assert_(t_13676, fn__13668);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__2020() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__961 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_13660 = SrcTest.userTable__637();
            SafeIdentifier t_13661 = SrcTest.csid__636("age");
            Changeset cs__962 = SrcGlobal.changeset(t_13660, params__961).cast(List.of(t_13661)).validateInt64(SrcTest.csid__636("age"));
            boolean t_13665 = cs__962.isValid();
            Supplier<String> fn__13657 = () -> "should be valid";
            test_42.assert_(t_13665, fn__13657);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__2021() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__964 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_13648 = SrcTest.userTable__637();
            SafeIdentifier t_13649 = SrcTest.csid__636("age");
            Changeset cs__965 = SrcGlobal.changeset(t_13648, params__964).cast(List.of(t_13649)).validateInt64(SrcTest.csid__636("age"));
            boolean t_13655 = !cs__965.isValid();
            Supplier<String> fn__13645 = () -> "should be invalid";
            test_43.assert_(t_13655, fn__13645);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__2022() {
        Test test_44 = new Test();
        try {
            Consumer<String> fn__13642 = v__967 -> {
                Map<String, String> params__968 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__967)));
                TableDef t_13634 = SrcTest.userTable__637();
                SafeIdentifier t_13635 = SrcTest.csid__636("active");
                Changeset cs__969 = SrcGlobal.changeset(t_13634, params__968).cast(List.of(t_13635)).validateBool(SrcTest.csid__636("active"));
                boolean t_13639 = cs__969.isValid();
                Supplier<String> fn__13631 = () -> "should accept: " + v__967;
                test_44.assert_(t_13639, fn__13631);
            };
            List.of("true", "1", "yes", "on").forEach(fn__13642);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__2023() {
        Test test_45 = new Test();
        try {
            Consumer<String> fn__13628 = v__971 -> {
                Map<String, String> params__972 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__971)));
                TableDef t_13620 = SrcTest.userTable__637();
                SafeIdentifier t_13621 = SrcTest.csid__636("active");
                Changeset cs__973 = SrcGlobal.changeset(t_13620, params__972).cast(List.of(t_13621)).validateBool(SrcTest.csid__636("active"));
                boolean t_13625 = cs__973.isValid();
                Supplier<String> fn__13617 = () -> "should accept: " + v__971;
                test_45.assert_(t_13625, fn__13617);
            };
            List.of("false", "0", "no", "off").forEach(fn__13628);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__2024() {
        Test test_46 = new Test();
        try {
            Consumer<String> fn__13614 = v__975 -> {
                Map<String, String> params__976 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__975)));
                TableDef t_13605 = SrcTest.userTable__637();
                SafeIdentifier t_13606 = SrcTest.csid__636("active");
                Changeset cs__977 = SrcGlobal.changeset(t_13605, params__976).cast(List.of(t_13606)).validateBool(SrcTest.csid__636("active"));
                boolean t_13612 = !cs__977.isValid();
                Supplier<String> fn__13602 = () -> "should reject ambiguous: " + v__975;
                test_46.assert_(t_13612, fn__13602);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__13614);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__2025() {
        Test test_47 = new Test();
        try {
            Map<String, String> params__979 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_13590 = SrcTest.userTable__637();
            SafeIdentifier t_13591 = SrcTest.csid__636("name");
            SafeIdentifier t_13592 = SrcTest.csid__636("email");
            Changeset cs__980 = SrcGlobal.changeset(t_13590, params__979).cast(List.of(t_13591, t_13592)).validateRequired(List.of(SrcTest.csid__636("name"), SrcTest.csid__636("email")));
            SqlFragment t_7482;
            t_7482 = cs__980.toInsertSql();
            SqlFragment sqlFrag__981 = t_7482;
            String s__982 = sqlFrag__981.toString();
            boolean t_13599 = s__982.indexOf("''") >= 0;
            Supplier<String> fn__13586 = () -> "single quote must be doubled: " + s__982;
            test_47.assert_(t_13599, fn__13586);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__2026() {
        Test test_48 = new Test();
        try {
            Map<String, String> params__984 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_13570 = SrcTest.userTable__637();
            SafeIdentifier t_13571 = SrcTest.csid__636("name");
            SafeIdentifier t_13572 = SrcTest.csid__636("email");
            Changeset cs__985 = SrcGlobal.changeset(t_13570, params__984).cast(List.of(t_13571, t_13572)).validateRequired(List.of(SrcTest.csid__636("name"), SrcTest.csid__636("email")));
            SqlFragment t_7461;
            t_7461 = cs__985.toInsertSql();
            SqlFragment sqlFrag__986 = t_7461;
            String s__987 = sqlFrag__986.toString();
            boolean t_13579 = s__987.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__13566 = () -> "has INSERT INTO: " + s__987;
            test_48.assert_(t_13579, fn__13566);
            boolean t_13583 = s__987.indexOf("'Alice'") >= 0;
            Supplier<String> fn__13565 = () -> "has quoted name: " + s__987;
            test_48.assert_(t_13583, fn__13565);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__2027() {
        Test test_49 = new Test();
        try {
            Map<String, String> params__989 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_13552 = SrcTest.userTable__637();
            SafeIdentifier t_13553 = SrcTest.csid__636("name");
            SafeIdentifier t_13554 = SrcTest.csid__636("email");
            SafeIdentifier t_13555 = SrcTest.csid__636("age");
            Changeset cs__990 = SrcGlobal.changeset(t_13552, params__989).cast(List.of(t_13553, t_13554, t_13555)).validateRequired(List.of(SrcTest.csid__636("name"), SrcTest.csid__636("email")));
            SqlFragment t_7444;
            t_7444 = cs__990.toInsertSql();
            SqlFragment sqlFrag__991 = t_7444;
            String s__992 = sqlFrag__991.toString();
            boolean t_13562 = s__992.indexOf("25") >= 0;
            Supplier<String> fn__13547 = () -> "age rendered unquoted: " + s__992;
            test_49.assert_(t_13562, fn__13547);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__2028() {
        Test test_50 = new Test();
        try {
            Map<String, String> params__994 = Core.mapConstructor(List.of());
            TableDef t_13540 = SrcTest.userTable__637();
            SafeIdentifier t_13541 = SrcTest.csid__636("name");
            Changeset cs__995 = SrcGlobal.changeset(t_13540, params__994).cast(List.of(t_13541)).validateRequired(List.of(SrcTest.csid__636("name")));
            boolean didBubble__996;
            boolean didBubble_14328;
            try {
                cs__995.toInsertSql();
                didBubble_14328 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_14328 = true;
            }
            didBubble__996 = didBubble_14328;
            Supplier<String> fn__13538 = () -> "invalid changeset should bubble";
            test_50.assert_(didBubble__996, fn__13538);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__2029() {
        Test test_51 = new Test();
        try {
            TableDef strictTable__998 = new TableDef(SrcTest.csid__636("posts"), List.of(new FieldDef(SrcTest.csid__636("title"), new StringField(), false), new FieldDef(SrcTest.csid__636("body"), new StringField(), true)));
            Map<String, String> params__999 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_13531 = SrcTest.csid__636("body");
            Changeset cs__1000 = SrcGlobal.changeset(strictTable__998, params__999).cast(List.of(t_13531));
            boolean t_13533 = cs__1000.isValid();
            Supplier<String> fn__13520 = () -> "changeset should appear valid (no explicit validation run)";
            test_51.assert_(t_13533, fn__13520);
            boolean didBubble__1001;
            boolean didBubble_14329;
            try {
                cs__1000.toInsertSql();
                didBubble_14329 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_14329 = true;
            }
            didBubble__1001 = didBubble_14329;
            Supplier<String> fn__13519 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_51.assert_(didBubble__1001, fn__13519);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__2030() {
        Test test_52 = new Test();
        try {
            Map<String, String> params__1003 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_13510 = SrcTest.userTable__637();
            SafeIdentifier t_13511 = SrcTest.csid__636("name");
            Changeset cs__1004 = SrcGlobal.changeset(t_13510, params__1003).cast(List.of(t_13511)).validateRequired(List.of(SrcTest.csid__636("name")));
            SqlFragment t_7404;
            t_7404 = cs__1004.toUpdateSql(42);
            SqlFragment sqlFrag__1005 = t_7404;
            String s__1006 = sqlFrag__1005.toString();
            boolean t_13517 = s__1006.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__13507 = () -> "got: " + s__1006;
            test_52.assert_(t_13517, fn__13507);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__2031() {
        Test test_53 = new Test();
        try {
            Map<String, String> params__1008 = Core.mapConstructor(List.of());
            TableDef t_13500 = SrcTest.userTable__637();
            SafeIdentifier t_13501 = SrcTest.csid__636("name");
            Changeset cs__1009 = SrcGlobal.changeset(t_13500, params__1008).cast(List.of(t_13501)).validateRequired(List.of(SrcTest.csid__636("name")));
            boolean didBubble__1010;
            boolean didBubble_14330;
            try {
                cs__1009.toUpdateSql(1);
                didBubble_14330 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_14330 = true;
            }
            didBubble__1010 = didBubble_14330;
            Supplier<String> fn__13498 = () -> "invalid changeset should bubble";
            test_53.assert_(didBubble__1010, fn__13498);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeAddsANewField__2032() {
        Test test_54 = new Test();
        try {
            Map<String, String> params__1012 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13484 = SrcTest.userTable__637();
            SafeIdentifier t_13485 = SrcTest.csid__636("name");
            Changeset cs__1013 = SrcGlobal.changeset(t_13484, params__1012).cast(List.of(t_13485)).putChange(SrcTest.csid__636("email"), "alice@example.com");
            boolean t_13490 = cs__1013.getChanges().containsKey("email");
            Supplier<String> fn__13481 = () -> "email should be in changes";
            test_54.assert_(t_13490, fn__13481);
            boolean t_13496 = cs__1013.getChanges().getOrDefault("email", "").equals("alice@example.com");
            Supplier<String> fn__13480 = () -> "email value";
            test_54.assert_(t_13496, fn__13480);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeOverwritesExistingField__2033() {
        Test test_55 = new Test();
        try {
            Map<String, String> params__1015 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13470 = SrcTest.userTable__637();
            SafeIdentifier t_13471 = SrcTest.csid__636("name");
            Changeset cs__1016 = SrcGlobal.changeset(t_13470, params__1015).cast(List.of(t_13471)).putChange(SrcTest.csid__636("name"), "Bob");
            boolean t_13478 = cs__1016.getChanges().getOrDefault("name", "").equals("Bob");
            Supplier<String> fn__13467 = () -> "name should be overwritten";
            test_55.assert_(t_13478, fn__13467);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeValueAppearsInToInsertSql__2034() {
        Test test_56 = new Test();
        try {
            Map<String, String> params__1018 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_13456 = SrcTest.userTable__637();
            SafeIdentifier t_13457 = SrcTest.csid__636("name");
            SafeIdentifier t_13458 = SrcTest.csid__636("email");
            Changeset cs__1019 = SrcGlobal.changeset(t_13456, params__1018).cast(List.of(t_13457, t_13458)).putChange(SrcTest.csid__636("name"), "Bob");
            SqlFragment t_7359;
            t_7359 = cs__1019.toInsertSql();
            SqlFragment t_7360 = t_7359;
            String s__1020 = t_7360.toString();
            boolean t_13464 = s__1020.indexOf("'Bob'") >= 0;
            Supplier<String> fn__13452 = () -> "should use putChange value: " + s__1020;
            test_56.assert_(t_13464, fn__13452);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeReturnsValueForExistingField__2035() {
        Test test_57 = new Test();
        try {
            Map<String, String> params__1022 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13445 = SrcTest.userTable__637();
            SafeIdentifier t_13446 = SrcTest.csid__636("name");
            Changeset cs__1023 = SrcGlobal.changeset(t_13445, params__1022).cast(List.of(t_13446));
            String t_7347;
            t_7347 = cs__1023.getChange(SrcTest.csid__636("name"));
            String val__1024 = t_7347;
            boolean t_13450 = val__1024.equals("Alice");
            Supplier<String> fn__13442 = () -> "should return Alice";
            test_57.assert_(t_13450, fn__13442);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeBubblesOnMissingField__2036() {
        Test test_58 = new Test();
        try {
            Map<String, String> params__1026 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13436 = SrcTest.userTable__637();
            SafeIdentifier t_13437 = SrcTest.csid__636("name");
            Changeset cs__1027 = SrcGlobal.changeset(t_13436, params__1026).cast(List.of(t_13437));
            boolean didBubble__1028;
            boolean didBubble_14331;
            try {
                cs__1027.getChange(SrcTest.csid__636("email"));
                didBubble_14331 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_14331 = true;
            }
            didBubble__1028 = didBubble_14331;
            Supplier<String> fn__13433 = () -> "should bubble for missing field";
            test_58.assert_(didBubble__1028, fn__13433);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeRemovesField__2037() {
        Test test_59 = new Test();
        try {
            Map<String, String> params__1030 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_13418 = SrcTest.userTable__637();
            SafeIdentifier t_13419 = SrcTest.csid__636("name");
            SafeIdentifier t_13420 = SrcTest.csid__636("email");
            Changeset cs__1031 = SrcGlobal.changeset(t_13418, params__1030).cast(List.of(t_13419, t_13420)).deleteChange(SrcTest.csid__636("email"));
            boolean t_13427 = !cs__1031.getChanges().containsKey("email");
            Supplier<String> fn__13414 = () -> "email should be removed";
            test_59.assert_(t_13427, fn__13414);
            boolean t_13430 = cs__1031.getChanges().containsKey("name");
            Supplier<String> fn__13413 = () -> "name should remain";
            test_59.assert_(t_13430, fn__13413);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeOnNonexistentFieldIsNoOp__2038() {
        Test test_60 = new Test();
        try {
            Map<String, String> params__1033 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13401 = SrcTest.userTable__637();
            SafeIdentifier t_13402 = SrcTest.csid__636("name");
            Changeset cs__1034 = SrcGlobal.changeset(t_13401, params__1033).cast(List.of(t_13402)).deleteChange(SrcTest.csid__636("email"));
            boolean t_13407 = cs__1034.getChanges().containsKey("name");
            Supplier<String> fn__13398 = () -> "name should still be present";
            test_60.assert_(t_13407, fn__13398);
            boolean t_13410 = cs__1034.isValid();
            Supplier<String> fn__13397 = () -> "should still be valid";
            test_60.assert_(t_13410, fn__13397);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionPassesWhenValueInList__2039() {
        Test test_61 = new Test();
        try {
            Map<String, String> params__1036 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_13389 = SrcTest.userTable__637();
            SafeIdentifier t_13390 = SrcTest.csid__636("name");
            Changeset cs__1037 = SrcGlobal.changeset(t_13389, params__1036).cast(List.of(t_13390)).validateInclusion(SrcTest.csid__636("name"), List.of("admin", "user", "guest"));
            boolean t_13394 = cs__1037.isValid();
            Supplier<String> fn__13386 = () -> "should be valid";
            test_61.assert_(t_13394, fn__13386);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionFailsWhenValueNotInList__2040() {
        Test test_62 = new Test();
        try {
            Map<String, String> params__1039 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "hacker")));
            TableDef t_13371 = SrcTest.userTable__637();
            SafeIdentifier t_13372 = SrcTest.csid__636("name");
            Changeset cs__1040 = SrcGlobal.changeset(t_13371, params__1039).cast(List.of(t_13372)).validateInclusion(SrcTest.csid__636("name"), List.of("admin", "user", "guest"));
            boolean t_13378 = !cs__1040.isValid();
            Supplier<String> fn__13368 = () -> "should be invalid";
            test_62.assert_(t_13378, fn__13368);
            boolean t_13384 = Core.listGet(cs__1040.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__13367 = () -> "error on name";
            test_62.assert_(t_13384, fn__13367);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionSkipsWhenFieldNotInChanges__2041() {
        Test test_63 = new Test();
        try {
            Map<String, String> params__1042 = Core.mapConstructor(List.of());
            TableDef t_13359 = SrcTest.userTable__637();
            SafeIdentifier t_13360 = SrcTest.csid__636("name");
            Changeset cs__1043 = SrcGlobal.changeset(t_13359, params__1042).cast(List.of(t_13360)).validateInclusion(SrcTest.csid__636("name"), List.of("admin", "user"));
            boolean t_13364 = cs__1043.isValid();
            Supplier<String> fn__13357 = () -> "should be valid when field absent";
            test_63.assert_(t_13364, fn__13357);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionPassesWhenValueNotInList__2042() {
        Test test_64 = new Test();
        try {
            Map<String, String> params__1045 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_13349 = SrcTest.userTable__637();
            SafeIdentifier t_13350 = SrcTest.csid__636("name");
            Changeset cs__1046 = SrcGlobal.changeset(t_13349, params__1045).cast(List.of(t_13350)).validateExclusion(SrcTest.csid__636("name"), List.of("root", "admin", "superuser"));
            boolean t_13354 = cs__1046.isValid();
            Supplier<String> fn__13346 = () -> "should be valid";
            test_64.assert_(t_13354, fn__13346);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionFailsWhenValueInList__2043() {
        Test test_65 = new Test();
        try {
            Map<String, String> params__1048 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_13331 = SrcTest.userTable__637();
            SafeIdentifier t_13332 = SrcTest.csid__636("name");
            Changeset cs__1049 = SrcGlobal.changeset(t_13331, params__1048).cast(List.of(t_13332)).validateExclusion(SrcTest.csid__636("name"), List.of("root", "admin", "superuser"));
            boolean t_13338 = !cs__1049.isValid();
            Supplier<String> fn__13328 = () -> "should be invalid";
            test_65.assert_(t_13338, fn__13328);
            boolean t_13344 = Core.listGet(cs__1049.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__13327 = () -> "error on name";
            test_65.assert_(t_13344, fn__13327);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionSkipsWhenFieldNotInChanges__2044() {
        Test test_66 = new Test();
        try {
            Map<String, String> params__1051 = Core.mapConstructor(List.of());
            TableDef t_13319 = SrcTest.userTable__637();
            SafeIdentifier t_13320 = SrcTest.csid__636("name");
            Changeset cs__1052 = SrcGlobal.changeset(t_13319, params__1051).cast(List.of(t_13320)).validateExclusion(SrcTest.csid__636("name"), List.of("root", "admin"));
            boolean t_13324 = cs__1052.isValid();
            Supplier<String> fn__13317 = () -> "should be valid when field absent";
            test_66.assert_(t_13324, fn__13317);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanPasses__2045() {
        Test test_67 = new Test();
        try {
            Map<String, String> params__1054 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "25")));
            TableDef t_13308 = SrcTest.userTable__637();
            SafeIdentifier t_13309 = SrcTest.csid__636("age");
            Changeset cs__1055 = SrcGlobal.changeset(t_13308, params__1054).cast(List.of(t_13309)).validateNumber(SrcTest.csid__636("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_13314 = cs__1055.isValid();
            Supplier<String> fn__13305 = () -> "25 > 18 should pass";
            test_67.assert_(t_13314, fn__13305);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanFails__2046() {
        Test test_68 = new Test();
        try {
            Map<String, String> params__1057 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "15")));
            TableDef t_13295 = SrcTest.userTable__637();
            SafeIdentifier t_13296 = SrcTest.csid__636("age");
            Changeset cs__1058 = SrcGlobal.changeset(t_13295, params__1057).cast(List.of(t_13296)).validateNumber(SrcTest.csid__636("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_13303 = !cs__1058.isValid();
            Supplier<String> fn__13292 = () -> "15 > 18 should fail";
            test_68.assert_(t_13303, fn__13292);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanPasses__2047() {
        Test test_69 = new Test();
        try {
            Map<String, String> params__1060 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "8.5")));
            TableDef t_13283 = SrcTest.userTable__637();
            SafeIdentifier t_13284 = SrcTest.csid__636("score");
            Changeset cs__1061 = SrcGlobal.changeset(t_13283, params__1060).cast(List.of(t_13284)).validateNumber(SrcTest.csid__636("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_13289 = cs__1061.isValid();
            Supplier<String> fn__13280 = () -> "8.5 < 10 should pass";
            test_69.assert_(t_13289, fn__13280);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanFails__2048() {
        Test test_70 = new Test();
        try {
            Map<String, String> params__1063 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "12.0")));
            TableDef t_13270 = SrcTest.userTable__637();
            SafeIdentifier t_13271 = SrcTest.csid__636("score");
            Changeset cs__1064 = SrcGlobal.changeset(t_13270, params__1063).cast(List.of(t_13271)).validateNumber(SrcTest.csid__636("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_13278 = !cs__1064.isValid();
            Supplier<String> fn__13267 = () -> "12 < 10 should fail";
            test_70.assert_(t_13278, fn__13267);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanOrEqualBoundary__2049() {
        Test test_71 = new Test();
        try {
            Map<String, String> params__1066 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "18")));
            TableDef t_13258 = SrcTest.userTable__637();
            SafeIdentifier t_13259 = SrcTest.csid__636("age");
            Changeset cs__1067 = SrcGlobal.changeset(t_13258, params__1066).cast(List.of(t_13259)).validateNumber(SrcTest.csid__636("age"), new NumberValidationOpts(null, null, 18.0D, null, null));
            boolean t_13264 = cs__1067.isValid();
            Supplier<String> fn__13255 = () -> "18 >= 18 should pass";
            test_71.assert_(t_13264, fn__13255);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberCombinedOptions__2050() {
        Test test_72 = new Test();
        try {
            Map<String, String> params__1069 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "5.0")));
            TableDef t_13246 = SrcTest.userTable__637();
            SafeIdentifier t_13247 = SrcTest.csid__636("score");
            Changeset cs__1070 = SrcGlobal.changeset(t_13246, params__1069).cast(List.of(t_13247)).validateNumber(SrcTest.csid__636("score"), new NumberValidationOpts(0.0D, 10.0D, null, null, null));
            boolean t_13252 = cs__1070.isValid();
            Supplier<String> fn__13243 = () -> "5 > 0 and < 10 should pass";
            test_72.assert_(t_13252, fn__13243);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberNonNumericValue__2051() {
        Test test_73 = new Test();
        try {
            Map<String, String> params__1072 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "abc")));
            TableDef t_13227 = SrcTest.userTable__637();
            SafeIdentifier t_13228 = SrcTest.csid__636("age");
            Changeset cs__1073 = SrcGlobal.changeset(t_13227, params__1072).cast(List.of(t_13228)).validateNumber(SrcTest.csid__636("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_13235 = !cs__1073.isValid();
            Supplier<String> fn__13224 = () -> "non-numeric should fail";
            test_73.assert_(t_13235, fn__13224);
            boolean t_13241 = Core.listGet(cs__1073.getErrors(), 0).getMessage().equals("must be a number");
            Supplier<String> fn__13223 = () -> "correct error message";
            test_73.assert_(t_13241, fn__13223);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberSkipsWhenFieldNotInChanges__2052() {
        Test test_74 = new Test();
        try {
            Map<String, String> params__1075 = Core.mapConstructor(List.of());
            TableDef t_13214 = SrcTest.userTable__637();
            SafeIdentifier t_13215 = SrcTest.csid__636("age");
            Changeset cs__1076 = SrcGlobal.changeset(t_13214, params__1075).cast(List.of(t_13215)).validateNumber(SrcTest.csid__636("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_13220 = cs__1076.isValid();
            Supplier<String> fn__13212 = () -> "should be valid when field absent";
            test_74.assert_(t_13220, fn__13212);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptancePassesForTrueValues__2053() {
        Test test_75 = new Test();
        try {
            Consumer<String> fn__13209 = v__1078 -> {
                Map<String, String> params__1079 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1078)));
                TableDef t_13201 = SrcTest.userTable__637();
                SafeIdentifier t_13202 = SrcTest.csid__636("active");
                Changeset cs__1080 = SrcGlobal.changeset(t_13201, params__1079).cast(List.of(t_13202)).validateAcceptance(SrcTest.csid__636("active"));
                boolean t_13206 = cs__1080.isValid();
                Supplier<String> fn__13198 = () -> "should accept: " + v__1078;
                test_75.assert_(t_13206, fn__13198);
            };
            List.of("true", "1", "yes", "on").forEach(fn__13209);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptanceFailsForNonTrueValues__2054() {
        Test test_76 = new Test();
        try {
            Map<String, String> params__1082 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", "false")));
            TableDef t_13183 = SrcTest.userTable__637();
            SafeIdentifier t_13184 = SrcTest.csid__636("active");
            Changeset cs__1083 = SrcGlobal.changeset(t_13183, params__1082).cast(List.of(t_13184)).validateAcceptance(SrcTest.csid__636("active"));
            boolean t_13190 = !cs__1083.isValid();
            Supplier<String> fn__13180 = () -> "false should not be accepted";
            test_76.assert_(t_13190, fn__13180);
            boolean t_13196 = Core.listGet(cs__1083.getErrors(), 0).getMessage().equals("must be accepted");
            Supplier<String> fn__13179 = () -> "correct message";
            test_76.assert_(t_13196, fn__13179);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationPassesWhenFieldsMatch__2055() {
        Test test_77 = new Test();
        try {
            TableDef tbl__1085 = new TableDef(SrcTest.csid__636("users"), List.of(new FieldDef(SrcTest.csid__636("password"), new StringField(), false), new FieldDef(SrcTest.csid__636("password_confirmation"), new StringField(), true)));
            Map<String, String> params__1086 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "secret123")));
            SafeIdentifier t_13170 = SrcTest.csid__636("password");
            SafeIdentifier t_13171 = SrcTest.csid__636("password_confirmation");
            Changeset cs__1087 = SrcGlobal.changeset(tbl__1085, params__1086).cast(List.of(t_13170, t_13171)).validateConfirmation(SrcTest.csid__636("password"), SrcTest.csid__636("password_confirmation"));
            boolean t_13176 = cs__1087.isValid();
            Supplier<String> fn__13158 = () -> "matching fields should pass";
            test_77.assert_(t_13176, fn__13158);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenFieldsDiffer__2056() {
        Test test_78 = new Test();
        try {
            TableDef tbl__1089 = new TableDef(SrcTest.csid__636("users"), List.of(new FieldDef(SrcTest.csid__636("password"), new StringField(), false), new FieldDef(SrcTest.csid__636("password_confirmation"), new StringField(), true)));
            Map<String, String> params__1090 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "wrong456")));
            SafeIdentifier t_13142 = SrcTest.csid__636("password");
            SafeIdentifier t_13143 = SrcTest.csid__636("password_confirmation");
            Changeset cs__1091 = SrcGlobal.changeset(tbl__1089, params__1090).cast(List.of(t_13142, t_13143)).validateConfirmation(SrcTest.csid__636("password"), SrcTest.csid__636("password_confirmation"));
            boolean t_13150 = !cs__1091.isValid();
            Supplier<String> fn__13130 = () -> "mismatched fields should fail";
            test_78.assert_(t_13150, fn__13130);
            boolean t_13156 = Core.listGet(cs__1091.getErrors(), 0).getField().equals("password_confirmation");
            Supplier<String> fn__13129 = () -> "error on confirmation field";
            test_78.assert_(t_13156, fn__13129);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenConfirmationMissing__2057() {
        Test test_79 = new Test();
        try {
            TableDef tbl__1093 = new TableDef(SrcTest.csid__636("users"), List.of(new FieldDef(SrcTest.csid__636("password"), new StringField(), false), new FieldDef(SrcTest.csid__636("password_confirmation"), new StringField(), true)));
            Map<String, String> params__1094 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123")));
            SafeIdentifier t_13120 = SrcTest.csid__636("password");
            Changeset cs__1095 = SrcGlobal.changeset(tbl__1093, params__1094).cast(List.of(t_13120)).validateConfirmation(SrcTest.csid__636("password"), SrcTest.csid__636("password_confirmation"));
            boolean t_13127 = !cs__1095.isValid();
            Supplier<String> fn__13109 = () -> "missing confirmation should fail";
            test_79.assert_(t_13127, fn__13109);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsPassesWhenSubstringFound__2058() {
        Test test_80 = new Test();
        try {
            Map<String, String> params__1097 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_13101 = SrcTest.userTable__637();
            SafeIdentifier t_13102 = SrcTest.csid__636("email");
            Changeset cs__1098 = SrcGlobal.changeset(t_13101, params__1097).cast(List.of(t_13102)).validateContains(SrcTest.csid__636("email"), "@");
            boolean t_13106 = cs__1098.isValid();
            Supplier<String> fn__13098 = () -> "should pass when @ present";
            test_80.assert_(t_13106, fn__13098);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsFailsWhenSubstringNotFound__2059() {
        Test test_81 = new Test();
        try {
            Map<String, String> params__1100 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice-example.com")));
            TableDef t_13089 = SrcTest.userTable__637();
            SafeIdentifier t_13090 = SrcTest.csid__636("email");
            Changeset cs__1101 = SrcGlobal.changeset(t_13089, params__1100).cast(List.of(t_13090)).validateContains(SrcTest.csid__636("email"), "@");
            boolean t_13096 = !cs__1101.isValid();
            Supplier<String> fn__13086 = () -> "should fail when @ absent";
            test_81.assert_(t_13096, fn__13086);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsSkipsWhenFieldNotInChanges__2060() {
        Test test_82 = new Test();
        try {
            Map<String, String> params__1103 = Core.mapConstructor(List.of());
            TableDef t_13078 = SrcTest.userTable__637();
            SafeIdentifier t_13079 = SrcTest.csid__636("email");
            Changeset cs__1104 = SrcGlobal.changeset(t_13078, params__1103).cast(List.of(t_13079)).validateContains(SrcTest.csid__636("email"), "@");
            boolean t_13083 = cs__1104.isValid();
            Supplier<String> fn__13076 = () -> "should be valid when field absent";
            test_82.assert_(t_13083, fn__13076);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithPasses__2061() {
        Test test_83 = new Test();
        try {
            Map<String, String> params__1106 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Dr. Smith")));
            TableDef t_13068 = SrcTest.userTable__637();
            SafeIdentifier t_13069 = SrcTest.csid__636("name");
            Changeset cs__1107 = SrcGlobal.changeset(t_13068, params__1106).cast(List.of(t_13069)).validateStartsWith(SrcTest.csid__636("name"), "Dr.");
            boolean t_13073 = cs__1107.isValid();
            Supplier<String> fn__13065 = () -> "should pass for Dr. prefix";
            test_83.assert_(t_13073, fn__13065);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithFails__2062() {
        Test test_84 = new Test();
        try {
            Map<String, String> params__1109 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Mr. Smith")));
            TableDef t_13056 = SrcTest.userTable__637();
            SafeIdentifier t_13057 = SrcTest.csid__636("name");
            Changeset cs__1110 = SrcGlobal.changeset(t_13056, params__1109).cast(List.of(t_13057)).validateStartsWith(SrcTest.csid__636("name"), "Dr.");
            boolean t_13063 = !cs__1110.isValid();
            Supplier<String> fn__13053 = () -> "should fail for Mr. prefix";
            test_84.assert_(t_13063, fn__13053);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithPasses__2063() {
        Test test_85 = new Test();
        try {
            Map<String, String> params__1112 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_13045 = SrcTest.userTable__637();
            SafeIdentifier t_13046 = SrcTest.csid__636("email");
            Changeset cs__1113 = SrcGlobal.changeset(t_13045, params__1112).cast(List.of(t_13046)).validateEndsWith(SrcTest.csid__636("email"), ".com");
            boolean t_13050 = cs__1113.isValid();
            Supplier<String> fn__13042 = () -> "should pass for .com suffix";
            test_85.assert_(t_13050, fn__13042);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithFails__2064() {
        Test test_86 = new Test();
        try {
            Map<String, String> params__1115 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.org")));
            TableDef t_13033 = SrcTest.userTable__637();
            SafeIdentifier t_13034 = SrcTest.csid__636("email");
            Changeset cs__1116 = SrcGlobal.changeset(t_13033, params__1115).cast(List.of(t_13034)).validateEndsWith(SrcTest.csid__636("email"), ".com");
            boolean t_13040 = !cs__1116.isValid();
            Supplier<String> fn__13030 = () -> "should fail for .org when expecting .com";
            test_86.assert_(t_13040, fn__13030);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithHandlesRepeatedSuffixCorrectly__2065() {
        Test test_87 = new Test();
        try {
            Map<String, String> params__1118 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "abcabc")));
            TableDef t_13022 = SrcTest.userTable__637();
            SafeIdentifier t_13023 = SrcTest.csid__636("name");
            Changeset cs__1119 = SrcGlobal.changeset(t_13022, params__1118).cast(List.of(t_13023)).validateEndsWith(SrcTest.csid__636("name"), "abc");
            boolean t_13027 = cs__1119.isValid();
            Supplier<String> fn__13019 = () -> "abcabc should end with abc";
            test_87.assert_(t_13027, fn__13019);
        } finally {
            test_87.softFailToHard();
        }
    }
    static SafeIdentifier sid__638(String name__1464) {
        SafeIdentifier t_6485;
        t_6485 = SrcGlobal.safeIdentifier(name__1464);
        return t_6485;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__2147() {
        Test test_88 = new Test();
        try {
            Query q__1467 = SrcGlobal.from(SrcTest.sid__638("users"));
            boolean t_12504 = q__1467.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__12499 = () -> "bare query";
            test_88.assert_(t_12504, fn__12499);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__2148() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_12490 = SrcTest.sid__638("users");
            SafeIdentifier t_12491 = SrcTest.sid__638("id");
            SafeIdentifier t_12492 = SrcTest.sid__638("name");
            Query q__1469 = SrcGlobal.from(t_12490).select(List.of(t_12491, t_12492));
            boolean t_12497 = q__1469.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__12489 = () -> "select columns";
            test_89.assert_(t_12497, fn__12489);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__2149() {
        Test test_90 = new Test();
        try {
            SafeIdentifier t_12478 = SrcTest.sid__638("users");
            SqlBuilder t_12479 = new SqlBuilder();
            t_12479.appendSafe("age > ");
            t_12479.appendInt32(18);
            SqlFragment t_12482 = t_12479.getAccumulated();
            Query q__1471 = SrcGlobal.from(t_12478).where(t_12482);
            boolean t_12487 = q__1471.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__12477 = () -> "where int";
            test_90.assert_(t_12487, fn__12477);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__2151() {
        Test test_91 = new Test();
        try {
            SafeIdentifier t_12466 = SrcTest.sid__638("users");
            SqlBuilder t_12467 = new SqlBuilder();
            t_12467.appendSafe("active = ");
            t_12467.appendBoolean(true);
            SqlFragment t_12470 = t_12467.getAccumulated();
            Query q__1473 = SrcGlobal.from(t_12466).where(t_12470);
            boolean t_12475 = q__1473.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__12465 = () -> "where bool";
            test_91.assert_(t_12475, fn__12465);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__2153() {
        Test test_92 = new Test();
        try {
            SafeIdentifier t_12449 = SrcTest.sid__638("users");
            SqlBuilder t_12450 = new SqlBuilder();
            t_12450.appendSafe("age > ");
            t_12450.appendInt32(18);
            SqlFragment t_12453 = t_12450.getAccumulated();
            Query t_12454 = SrcGlobal.from(t_12449).where(t_12453);
            SqlBuilder t_12455 = new SqlBuilder();
            t_12455.appendSafe("active = ");
            t_12455.appendBoolean(true);
            Query q__1475 = t_12454.where(t_12455.getAccumulated());
            boolean t_12463 = q__1475.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__12448 = () -> "chained where";
            test_92.assert_(t_12463, fn__12448);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__2156() {
        Test test_93 = new Test();
        try {
            SafeIdentifier t_12440 = SrcTest.sid__638("users");
            SafeIdentifier t_12441 = SrcTest.sid__638("name");
            Query q__1477 = SrcGlobal.from(t_12440).orderBy(t_12441, true);
            boolean t_12446 = q__1477.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__12439 = () -> "order asc";
            test_93.assert_(t_12446, fn__12439);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__2157() {
        Test test_94 = new Test();
        try {
            SafeIdentifier t_12431 = SrcTest.sid__638("users");
            SafeIdentifier t_12432 = SrcTest.sid__638("created_at");
            Query q__1479 = SrcGlobal.from(t_12431).orderBy(t_12432, false);
            boolean t_12437 = q__1479.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__12430 = () -> "order desc";
            test_94.assert_(t_12437, fn__12430);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__2158() {
        Test test_95 = new Test();
        try {
            Query t_6419;
            t_6419 = SrcGlobal.from(SrcTest.sid__638("users")).limit(10);
            Query t_6420;
            t_6420 = t_6419.offset(20);
            Query q__1481 = t_6420;
            boolean t_12428 = q__1481.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__12423 = () -> "limit/offset";
            test_95.assert_(t_12428, fn__12423);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__2159() {
        Test test_96 = new Test();
        try {
            boolean didBubble__1483;
            boolean didBubble_14332;
            try {
                SrcGlobal.from(SrcTest.sid__638("users")).limit(-1);
                didBubble_14332 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_14332 = true;
            }
            didBubble__1483 = didBubble_14332;
            Supplier<String> fn__12419 = () -> "negative limit should bubble";
            test_96.assert_(didBubble__1483, fn__12419);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__2160() {
        Test test_97 = new Test();
        try {
            boolean didBubble__1485;
            boolean didBubble_14333;
            try {
                SrcGlobal.from(SrcTest.sid__638("users")).offset(-1);
                didBubble_14333 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_14333 = true;
            }
            didBubble__1485 = didBubble_14333;
            Supplier<String> fn__12415 = () -> "negative offset should bubble";
            test_97.assert_(didBubble__1485, fn__12415);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__2161() {
        Test test_98 = new Test();
        try {
            int minAge__1487 = 21;
            SafeIdentifier t_12393 = SrcTest.sid__638("users");
            SafeIdentifier t_12394 = SrcTest.sid__638("id");
            SafeIdentifier t_12395 = SrcTest.sid__638("name");
            SafeIdentifier t_12396 = SrcTest.sid__638("email");
            Query t_12397 = SrcGlobal.from(t_12393).select(List.of(t_12394, t_12395, t_12396));
            SqlBuilder t_12398 = new SqlBuilder();
            t_12398.appendSafe("age >= ");
            t_12398.appendInt32(21);
            Query t_12402 = t_12397.where(t_12398.getAccumulated());
            SqlBuilder t_12403 = new SqlBuilder();
            t_12403.appendSafe("active = ");
            t_12403.appendBoolean(true);
            Query t_6405;
            t_6405 = t_12402.where(t_12403.getAccumulated()).orderBy(SrcTest.sid__638("name"), true).limit(25);
            Query t_6406;
            t_6406 = t_6405.offset(0);
            Query q__1488 = t_6406;
            boolean t_12413 = q__1488.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__12392 = () -> "complex query";
            test_98.assert_(t_12413, fn__12392);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__2164() {
        Test test_99 = new Test();
        try {
            Query q__1490 = SrcGlobal.from(SrcTest.sid__638("users"));
            SqlFragment t_6382;
            t_6382 = q__1490.safeToSql(100);
            SqlFragment t_6383 = t_6382;
            String s__1491 = t_6383.toString();
            boolean t_12390 = s__1491.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__12386 = () -> "should have limit: " + s__1491;
            test_99.assert_(t_12390, fn__12386);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__2165() {
        Test test_100 = new Test();
        try {
            Query t_6374;
            t_6374 = SrcGlobal.from(SrcTest.sid__638("users")).limit(5);
            Query q__1493 = t_6374;
            SqlFragment t_6377;
            t_6377 = q__1493.safeToSql(100);
            SqlFragment t_6378 = t_6377;
            String s__1494 = t_6378.toString();
            boolean t_12384 = s__1494.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__12380 = () -> "explicit limit preserved: " + s__1494;
            test_100.assert_(t_12384, fn__12380);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__2166() {
        Test test_101 = new Test();
        try {
            boolean didBubble__1496;
            boolean didBubble_14334;
            try {
                SrcGlobal.from(SrcTest.sid__638("users")).safeToSql(-1);
                didBubble_14334 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_14334 = true;
            }
            didBubble__1496 = didBubble_14334;
            Supplier<String> fn__12376 = () -> "negative defaultLimit should bubble";
            test_101.assert_(didBubble__1496, fn__12376);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__2167() {
        Test test_102 = new Test();
        try {
            String evil__1498 = "'; DROP TABLE users; --";
            SafeIdentifier t_12360 = SrcTest.sid__638("users");
            SqlBuilder t_12361 = new SqlBuilder();
            t_12361.appendSafe("name = ");
            t_12361.appendString("'; DROP TABLE users; --");
            SqlFragment t_12364 = t_12361.getAccumulated();
            Query q__1499 = SrcGlobal.from(t_12360).where(t_12364);
            String s__1500 = q__1499.toSql().toString();
            boolean t_12369 = s__1500.indexOf("''") >= 0;
            Supplier<String> fn__12359 = () -> "quotes must be doubled: " + s__1500;
            test_102.assert_(t_12369, fn__12359);
            boolean t_12373 = s__1500.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__12358 = () -> "structure intact: " + s__1500;
            test_102.assert_(t_12373, fn__12358);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__2169() {
        Test test_103 = new Test();
        try {
            String attack__1502 = "users; DROP TABLE users; --";
            boolean didBubble__1503;
            boolean didBubble_14335;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_14335 = false;
            } catch (RuntimeException ignored$13) {
                didBubble_14335 = true;
            }
            didBubble__1503 = didBubble_14335;
            Supplier<String> fn__12355 = () -> "metacharacter-containing name must be rejected at construction";
            test_103.assert_(didBubble__1503, fn__12355);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__2170() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_12344 = SrcTest.sid__638("users");
            SafeIdentifier t_12345 = SrcTest.sid__638("orders");
            SqlBuilder t_12346 = new SqlBuilder();
            t_12346.appendSafe("users.id = orders.user_id");
            SqlFragment t_12348 = t_12346.getAccumulated();
            Query q__1505 = SrcGlobal.from(t_12344).innerJoin(t_12345, t_12348);
            boolean t_12353 = q__1505.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__12343 = () -> "inner join";
            test_104.assert_(t_12353, fn__12343);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__2172() {
        Test test_105 = new Test();
        try {
            SafeIdentifier t_12332 = SrcTest.sid__638("users");
            SafeIdentifier t_12333 = SrcTest.sid__638("profiles");
            SqlBuilder t_12334 = new SqlBuilder();
            t_12334.appendSafe("users.id = profiles.user_id");
            SqlFragment t_12336 = t_12334.getAccumulated();
            Query q__1507 = SrcGlobal.from(t_12332).leftJoin(t_12333, t_12336);
            boolean t_12341 = q__1507.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__12331 = () -> "left join";
            test_105.assert_(t_12341, fn__12331);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__2174() {
        Test test_106 = new Test();
        try {
            SafeIdentifier t_12320 = SrcTest.sid__638("orders");
            SafeIdentifier t_12321 = SrcTest.sid__638("users");
            SqlBuilder t_12322 = new SqlBuilder();
            t_12322.appendSafe("orders.user_id = users.id");
            SqlFragment t_12324 = t_12322.getAccumulated();
            Query q__1509 = SrcGlobal.from(t_12320).rightJoin(t_12321, t_12324);
            boolean t_12329 = q__1509.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__12319 = () -> "right join";
            test_106.assert_(t_12329, fn__12319);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__2176() {
        Test test_107 = new Test();
        try {
            SafeIdentifier t_12308 = SrcTest.sid__638("users");
            SafeIdentifier t_12309 = SrcTest.sid__638("orders");
            SqlBuilder t_12310 = new SqlBuilder();
            t_12310.appendSafe("users.id = orders.user_id");
            SqlFragment t_12312 = t_12310.getAccumulated();
            Query q__1511 = SrcGlobal.from(t_12308).fullJoin(t_12309, t_12312);
            boolean t_12317 = q__1511.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__12307 = () -> "full join";
            test_107.assert_(t_12317, fn__12307);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__2178() {
        Test test_108 = new Test();
        try {
            SafeIdentifier t_12291 = SrcTest.sid__638("users");
            SafeIdentifier t_12292 = SrcTest.sid__638("orders");
            SqlBuilder t_12293 = new SqlBuilder();
            t_12293.appendSafe("users.id = orders.user_id");
            SqlFragment t_12295 = t_12293.getAccumulated();
            Query t_12296 = SrcGlobal.from(t_12291).innerJoin(t_12292, t_12295);
            SafeIdentifier t_12297 = SrcTest.sid__638("profiles");
            SqlBuilder t_12298 = new SqlBuilder();
            t_12298.appendSafe("users.id = profiles.user_id");
            Query q__1513 = t_12296.leftJoin(t_12297, t_12298.getAccumulated());
            boolean t_12305 = q__1513.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__12290 = () -> "chained joins";
            test_108.assert_(t_12305, fn__12290);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__2181() {
        Test test_109 = new Test();
        try {
            SafeIdentifier t_12272 = SrcTest.sid__638("users");
            SafeIdentifier t_12273 = SrcTest.sid__638("orders");
            SqlBuilder t_12274 = new SqlBuilder();
            t_12274.appendSafe("users.id = orders.user_id");
            SqlFragment t_12276 = t_12274.getAccumulated();
            Query t_12277 = SrcGlobal.from(t_12272).innerJoin(t_12273, t_12276);
            SqlBuilder t_12278 = new SqlBuilder();
            t_12278.appendSafe("orders.total > ");
            t_12278.appendInt32(100);
            Query t_6289;
            t_6289 = t_12277.where(t_12278.getAccumulated()).orderBy(SrcTest.sid__638("name"), true).limit(10);
            Query q__1515 = t_6289;
            boolean t_12288 = q__1515.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__12271 = () -> "join with where/order/limit";
            test_109.assert_(t_12288, fn__12271);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__2184() {
        Test test_110 = new Test();
        try {
            SqlFragment c__1517 = SrcGlobal.col(SrcTest.sid__638("users"), SrcTest.sid__638("id"));
            boolean t_12269 = c__1517.toString().equals("users.id");
            Supplier<String> fn__12263 = () -> "col helper";
            test_110.assert_(t_12269, fn__12263);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__2185() {
        Test test_111 = new Test();
        try {
            SqlFragment onCond__1519 = SrcGlobal.col(SrcTest.sid__638("users"), SrcTest.sid__638("id"));
            SqlBuilder b__1520 = new SqlBuilder();
            b__1520.appendFragment(onCond__1519);
            b__1520.appendSafe(" = ");
            b__1520.appendFragment(SrcGlobal.col(SrcTest.sid__638("orders"), SrcTest.sid__638("user_id")));
            SafeIdentifier t_12254 = SrcTest.sid__638("users");
            SafeIdentifier t_12255 = SrcTest.sid__638("orders");
            SqlFragment t_12256 = b__1520.getAccumulated();
            Query q__1521 = SrcGlobal.from(t_12254).innerJoin(t_12255, t_12256);
            boolean t_12261 = q__1521.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__12243 = () -> "join with col";
            test_111.assert_(t_12261, fn__12243);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__2186() {
        Test test_112 = new Test();
        try {
            SafeIdentifier t_12232 = SrcTest.sid__638("users");
            SqlBuilder t_12233 = new SqlBuilder();
            t_12233.appendSafe("status = ");
            t_12233.appendString("active");
            SqlFragment t_12236 = t_12233.getAccumulated();
            Query q__1523 = SrcGlobal.from(t_12232).orWhere(t_12236);
            boolean t_12241 = q__1523.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__12231 = () -> "orWhere basic";
            test_112.assert_(t_12241, fn__12231);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__2188() {
        Test test_113 = new Test();
        try {
            SafeIdentifier t_12215 = SrcTest.sid__638("users");
            SqlBuilder t_12216 = new SqlBuilder();
            t_12216.appendSafe("age > ");
            t_12216.appendInt32(18);
            SqlFragment t_12219 = t_12216.getAccumulated();
            Query t_12220 = SrcGlobal.from(t_12215).where(t_12219);
            SqlBuilder t_12221 = new SqlBuilder();
            t_12221.appendSafe("vip = ");
            t_12221.appendBoolean(true);
            Query q__1525 = t_12220.orWhere(t_12221.getAccumulated());
            boolean t_12229 = q__1525.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__12214 = () -> "where then orWhere";
            test_113.assert_(t_12229, fn__12214);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__2191() {
        Test test_114 = new Test();
        try {
            SafeIdentifier t_12193 = SrcTest.sid__638("users");
            SqlBuilder t_12194 = new SqlBuilder();
            t_12194.appendSafe("active = ");
            t_12194.appendBoolean(true);
            SqlFragment t_12197 = t_12194.getAccumulated();
            Query t_12198 = SrcGlobal.from(t_12193).where(t_12197);
            SqlBuilder t_12199 = new SqlBuilder();
            t_12199.appendSafe("role = ");
            t_12199.appendString("admin");
            Query t_12203 = t_12198.orWhere(t_12199.getAccumulated());
            SqlBuilder t_12204 = new SqlBuilder();
            t_12204.appendSafe("role = ");
            t_12204.appendString("moderator");
            Query q__1527 = t_12203.orWhere(t_12204.getAccumulated());
            boolean t_12212 = q__1527.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__12192 = () -> "multiple orWhere";
            test_114.assert_(t_12212, fn__12192);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__2195() {
        Test test_115 = new Test();
        try {
            SafeIdentifier t_12171 = SrcTest.sid__638("users");
            SqlBuilder t_12172 = new SqlBuilder();
            t_12172.appendSafe("age > ");
            t_12172.appendInt32(18);
            SqlFragment t_12175 = t_12172.getAccumulated();
            Query t_12176 = SrcGlobal.from(t_12171).where(t_12175);
            SqlBuilder t_12177 = new SqlBuilder();
            t_12177.appendSafe("active = ");
            t_12177.appendBoolean(true);
            Query t_12181 = t_12176.where(t_12177.getAccumulated());
            SqlBuilder t_12182 = new SqlBuilder();
            t_12182.appendSafe("vip = ");
            t_12182.appendBoolean(true);
            Query q__1529 = t_12181.orWhere(t_12182.getAccumulated());
            boolean t_12190 = q__1529.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__12170 = () -> "mixed where and orWhere";
            test_115.assert_(t_12190, fn__12170);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__2199() {
        Test test_116 = new Test();
        try {
            SafeIdentifier t_12162 = SrcTest.sid__638("users");
            SafeIdentifier t_12163 = SrcTest.sid__638("deleted_at");
            Query q__1531 = SrcGlobal.from(t_12162).whereNull(t_12163);
            boolean t_12168 = q__1531.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__12161 = () -> "whereNull";
            test_116.assert_(t_12168, fn__12161);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__2200() {
        Test test_117 = new Test();
        try {
            SafeIdentifier t_12153 = SrcTest.sid__638("users");
            SafeIdentifier t_12154 = SrcTest.sid__638("email");
            Query q__1533 = SrcGlobal.from(t_12153).whereNotNull(t_12154);
            boolean t_12159 = q__1533.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__12152 = () -> "whereNotNull";
            test_117.assert_(t_12159, fn__12152);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__2201() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_12139 = SrcTest.sid__638("users");
            SqlBuilder t_12140 = new SqlBuilder();
            t_12140.appendSafe("active = ");
            t_12140.appendBoolean(true);
            SqlFragment t_12143 = t_12140.getAccumulated();
            Query q__1535 = SrcGlobal.from(t_12139).where(t_12143).whereNull(SrcTest.sid__638("deleted_at"));
            boolean t_12150 = q__1535.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__12138 = () -> "whereNull chained";
            test_118.assert_(t_12150, fn__12138);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__2203() {
        Test test_119 = new Test();
        try {
            SafeIdentifier t_12125 = SrcTest.sid__638("users");
            SafeIdentifier t_12126 = SrcTest.sid__638("deleted_at");
            Query t_12127 = SrcGlobal.from(t_12125).whereNull(t_12126);
            SqlBuilder t_12128 = new SqlBuilder();
            t_12128.appendSafe("role = ");
            t_12128.appendString("admin");
            Query q__1537 = t_12127.orWhere(t_12128.getAccumulated());
            boolean t_12136 = q__1537.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__12124 = () -> "whereNotNull with orWhere";
            test_119.assert_(t_12136, fn__12124);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__2205() {
        Test test_120 = new Test();
        try {
            SafeIdentifier t_12113 = SrcTest.sid__638("users");
            SafeIdentifier t_12114 = SrcTest.sid__638("id");
            SqlInt32 t_12115 = new SqlInt32(1);
            SqlInt32 t_12116 = new SqlInt32(2);
            SqlInt32 t_12117 = new SqlInt32(3);
            Query q__1539 = SrcGlobal.from(t_12113).whereIn(t_12114, List.of(t_12115, t_12116, t_12117));
            boolean t_12122 = q__1539.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__12112 = () -> "whereIn ints";
            test_120.assert_(t_12122, fn__12112);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__2206() {
        Test test_121 = new Test();
        try {
            SafeIdentifier t_12102 = SrcTest.sid__638("users");
            SafeIdentifier t_12103 = SrcTest.sid__638("name");
            SqlString t_12104 = new SqlString("Alice");
            SqlString t_12105 = new SqlString("Bob's");
            Query q__1541 = SrcGlobal.from(t_12102).whereIn(t_12103, List.of(t_12104, t_12105));
            boolean t_12110 = q__1541.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__12101 = () -> "whereIn strings";
            test_121.assert_(t_12110, fn__12101);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__2207() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_12093 = SrcTest.sid__638("users");
            SafeIdentifier t_12094 = SrcTest.sid__638("id");
            Query q__1543 = SrcGlobal.from(t_12093).whereIn(t_12094, List.of());
            boolean t_12099 = q__1543.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__12092 = () -> "whereIn empty";
            test_122.assert_(t_12099, fn__12092);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__2208() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_12077 = SrcTest.sid__638("users");
            SqlBuilder t_12078 = new SqlBuilder();
            t_12078.appendSafe("active = ");
            t_12078.appendBoolean(true);
            SqlFragment t_12081 = t_12078.getAccumulated();
            Query q__1545 = SrcGlobal.from(t_12077).where(t_12081).whereIn(SrcTest.sid__638("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_12090 = q__1545.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__12076 = () -> "whereIn chained";
            test_123.assert_(t_12090, fn__12076);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__2210() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_12067 = SrcTest.sid__638("users");
            SafeIdentifier t_12068 = SrcTest.sid__638("id");
            SqlInt32 t_12069 = new SqlInt32(42);
            Query q__1547 = SrcGlobal.from(t_12067).whereIn(t_12068, List.of(t_12069));
            boolean t_12074 = q__1547.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__12066 = () -> "whereIn single";
            test_124.assert_(t_12074, fn__12066);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__2211() {
        Test test_125 = new Test();
        try {
            SafeIdentifier t_12055 = SrcTest.sid__638("users");
            SqlBuilder t_12056 = new SqlBuilder();
            t_12056.appendSafe("active = ");
            t_12056.appendBoolean(true);
            SqlFragment t_12059 = t_12056.getAccumulated();
            Query q__1549 = SrcGlobal.from(t_12055).whereNot(t_12059);
            boolean t_12064 = q__1549.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__12054 = () -> "whereNot";
            test_125.assert_(t_12064, fn__12054);
        } finally {
            test_125.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__2213() {
        Test test_126 = new Test();
        try {
            SafeIdentifier t_12038 = SrcTest.sid__638("users");
            SqlBuilder t_12039 = new SqlBuilder();
            t_12039.appendSafe("age > ");
            t_12039.appendInt32(18);
            SqlFragment t_12042 = t_12039.getAccumulated();
            Query t_12043 = SrcGlobal.from(t_12038).where(t_12042);
            SqlBuilder t_12044 = new SqlBuilder();
            t_12044.appendSafe("banned = ");
            t_12044.appendBoolean(true);
            Query q__1551 = t_12043.whereNot(t_12044.getAccumulated());
            boolean t_12052 = q__1551.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__12037 = () -> "whereNot chained";
            test_126.assert_(t_12052, fn__12037);
        } finally {
            test_126.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__2216() {
        Test test_127 = new Test();
        try {
            SafeIdentifier t_12027 = SrcTest.sid__638("users");
            SafeIdentifier t_12028 = SrcTest.sid__638("age");
            SqlInt32 t_12029 = new SqlInt32(18);
            SqlInt32 t_12030 = new SqlInt32(65);
            Query q__1553 = SrcGlobal.from(t_12027).whereBetween(t_12028, t_12029, t_12030);
            boolean t_12035 = q__1553.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__12026 = () -> "whereBetween ints";
            test_127.assert_(t_12035, fn__12026);
        } finally {
            test_127.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__2217() {
        Test test_128 = new Test();
        try {
            SafeIdentifier t_12011 = SrcTest.sid__638("users");
            SqlBuilder t_12012 = new SqlBuilder();
            t_12012.appendSafe("active = ");
            t_12012.appendBoolean(true);
            SqlFragment t_12015 = t_12012.getAccumulated();
            Query q__1555 = SrcGlobal.from(t_12011).where(t_12015).whereBetween(SrcTest.sid__638("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_12024 = q__1555.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__12010 = () -> "whereBetween chained";
            test_128.assert_(t_12024, fn__12010);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__2219() {
        Test test_129 = new Test();
        try {
            SafeIdentifier t_12002 = SrcTest.sid__638("users");
            SafeIdentifier t_12003 = SrcTest.sid__638("name");
            Query q__1557 = SrcGlobal.from(t_12002).whereLike(t_12003, "John%");
            boolean t_12008 = q__1557.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__12001 = () -> "whereLike";
            test_129.assert_(t_12008, fn__12001);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__2220() {
        Test test_130 = new Test();
        try {
            SafeIdentifier t_11993 = SrcTest.sid__638("users");
            SafeIdentifier t_11994 = SrcTest.sid__638("email");
            Query q__1559 = SrcGlobal.from(t_11993).whereILike(t_11994, "%@gmail.com");
            boolean t_11999 = q__1559.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__11992 = () -> "whereILike";
            test_130.assert_(t_11999, fn__11992);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__2221() {
        Test test_131 = new Test();
        try {
            SafeIdentifier t_11979 = SrcTest.sid__638("users");
            SafeIdentifier t_11980 = SrcTest.sid__638("name");
            Query q__1561 = SrcGlobal.from(t_11979).whereLike(t_11980, "'; DROP TABLE users; --");
            String s__1562 = q__1561.toSql().toString();
            boolean t_11985 = s__1562.indexOf("''") >= 0;
            Supplier<String> fn__11978 = () -> "like injection escaped: " + s__1562;
            test_131.assert_(t_11985, fn__11978);
            boolean t_11989 = s__1562.indexOf("LIKE") >= 0;
            Supplier<String> fn__11977 = () -> "like structure intact: " + s__1562;
            test_131.assert_(t_11989, fn__11977);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__2222() {
        Test test_132 = new Test();
        try {
            SafeIdentifier t_11969 = SrcTest.sid__638("users");
            SafeIdentifier t_11970 = SrcTest.sid__638("name");
            Query q__1564 = SrcGlobal.from(t_11969).whereLike(t_11970, "%son%");
            boolean t_11975 = q__1564.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__11968 = () -> "whereLike wildcard";
            test_132.assert_(t_11975, fn__11968);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__2223() {
        Test test_133 = new Test();
        try {
            SqlFragment f__1566 = SrcGlobal.countAll();
            boolean t_11966 = f__1566.toString().equals("COUNT(*)");
            Supplier<String> fn__11962 = () -> "countAll";
            test_133.assert_(t_11966, fn__11962);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__2224() {
        Test test_134 = new Test();
        try {
            SqlFragment f__1568 = SrcGlobal.countCol(SrcTest.sid__638("id"));
            boolean t_11960 = f__1568.toString().equals("COUNT(id)");
            Supplier<String> fn__11955 = () -> "countCol";
            test_134.assert_(t_11960, fn__11955);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__2225() {
        Test test_135 = new Test();
        try {
            SqlFragment f__1570 = SrcGlobal.sumCol(SrcTest.sid__638("amount"));
            boolean t_11953 = f__1570.toString().equals("SUM(amount)");
            Supplier<String> fn__11948 = () -> "sumCol";
            test_135.assert_(t_11953, fn__11948);
        } finally {
            test_135.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__2226() {
        Test test_136 = new Test();
        try {
            SqlFragment f__1572 = SrcGlobal.avgCol(SrcTest.sid__638("price"));
            boolean t_11946 = f__1572.toString().equals("AVG(price)");
            Supplier<String> fn__11941 = () -> "avgCol";
            test_136.assert_(t_11946, fn__11941);
        } finally {
            test_136.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__2227() {
        Test test_137 = new Test();
        try {
            SqlFragment f__1574 = SrcGlobal.minCol(SrcTest.sid__638("created_at"));
            boolean t_11939 = f__1574.toString().equals("MIN(created_at)");
            Supplier<String> fn__11934 = () -> "minCol";
            test_137.assert_(t_11939, fn__11934);
        } finally {
            test_137.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__2228() {
        Test test_138 = new Test();
        try {
            SqlFragment f__1576 = SrcGlobal.maxCol(SrcTest.sid__638("score"));
            boolean t_11932 = f__1576.toString().equals("MAX(score)");
            Supplier<String> fn__11927 = () -> "maxCol";
            test_138.assert_(t_11932, fn__11927);
        } finally {
            test_138.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__2229() {
        Test test_139 = new Test();
        try {
            SafeIdentifier t_11919 = SrcTest.sid__638("orders");
            SqlFragment t_11920 = SrcGlobal.countAll();
            Query q__1578 = SrcGlobal.from(t_11919).selectExpr(List.of(t_11920));
            boolean t_11925 = q__1578.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__11918 = () -> "selectExpr count";
            test_139.assert_(t_11925, fn__11918);
        } finally {
            test_139.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__2230() {
        Test test_140 = new Test();
        try {
            SqlFragment nameFrag__1580 = SrcGlobal.col(SrcTest.sid__638("users"), SrcTest.sid__638("name"));
            SafeIdentifier t_11910 = SrcTest.sid__638("users");
            SqlFragment t_11911 = SrcGlobal.countAll();
            Query q__1581 = SrcGlobal.from(t_11910).selectExpr(List.of(nameFrag__1580, t_11911));
            boolean t_11916 = q__1581.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__11906 = () -> "selectExpr multi";
            test_140.assert_(t_11916, fn__11906);
        } finally {
            test_140.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__2231() {
        Test test_141 = new Test();
        try {
            SafeIdentifier t_11895 = SrcTest.sid__638("users");
            SafeIdentifier t_11896 = SrcTest.sid__638("id");
            SafeIdentifier t_11897 = SrcTest.sid__638("name");
            Query q__1583 = SrcGlobal.from(t_11895).select(List.of(t_11896, t_11897)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_11904 = q__1583.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__11894 = () -> "selectExpr overrides select";
            test_141.assert_(t_11904, fn__11894);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__2232() {
        Test test_142 = new Test();
        try {
            SafeIdentifier t_11881 = SrcTest.sid__638("orders");
            SqlFragment t_11884 = SrcGlobal.col(SrcTest.sid__638("orders"), SrcTest.sid__638("status"));
            SqlFragment t_11885 = SrcGlobal.countAll();
            Query q__1585 = SrcGlobal.from(t_11881).selectExpr(List.of(t_11884, t_11885)).groupBy(SrcTest.sid__638("status"));
            boolean t_11892 = q__1585.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__11880 = () -> "groupBy single";
            test_142.assert_(t_11892, fn__11880);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__2233() {
        Test test_143 = new Test();
        try {
            SafeIdentifier t_11870 = SrcTest.sid__638("orders");
            SafeIdentifier t_11871 = SrcTest.sid__638("status");
            Query q__1587 = SrcGlobal.from(t_11870).groupBy(t_11871).groupBy(SrcTest.sid__638("category"));
            boolean t_11878 = q__1587.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__11869 = () -> "groupBy multiple";
            test_143.assert_(t_11878, fn__11869);
        } finally {
            test_143.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__2234() {
        Test test_144 = new Test();
        try {
            SafeIdentifier t_11851 = SrcTest.sid__638("orders");
            SqlFragment t_11854 = SrcGlobal.col(SrcTest.sid__638("orders"), SrcTest.sid__638("status"));
            SqlFragment t_11855 = SrcGlobal.countAll();
            Query t_11858 = SrcGlobal.from(t_11851).selectExpr(List.of(t_11854, t_11855)).groupBy(SrcTest.sid__638("status"));
            SqlBuilder t_11859 = new SqlBuilder();
            t_11859.appendSafe("COUNT(*) > ");
            t_11859.appendInt32(5);
            Query q__1589 = t_11858.having(t_11859.getAccumulated());
            boolean t_11867 = q__1589.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__11850 = () -> "having basic";
            test_144.assert_(t_11867, fn__11850);
        } finally {
            test_144.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__2236() {
        Test test_145 = new Test();
        try {
            SafeIdentifier t_11832 = SrcTest.sid__638("orders");
            SafeIdentifier t_11833 = SrcTest.sid__638("status");
            Query t_11834 = SrcGlobal.from(t_11832).groupBy(t_11833);
            SqlBuilder t_11835 = new SqlBuilder();
            t_11835.appendSafe("COUNT(*) > ");
            t_11835.appendInt32(5);
            Query t_11839 = t_11834.having(t_11835.getAccumulated());
            SqlBuilder t_11840 = new SqlBuilder();
            t_11840.appendSafe("SUM(total) > ");
            t_11840.appendInt32(1000);
            Query q__1591 = t_11839.orHaving(t_11840.getAccumulated());
            boolean t_11848 = q__1591.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__11831 = () -> "orHaving";
            test_145.assert_(t_11848, fn__11831);
        } finally {
            test_145.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__2239() {
        Test test_146 = new Test();
        try {
            SafeIdentifier t_11822 = SrcTest.sid__638("users");
            SafeIdentifier t_11823 = SrcTest.sid__638("name");
            Query q__1593 = SrcGlobal.from(t_11822).select(List.of(t_11823)).distinct();
            boolean t_11829 = q__1593.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__11821 = () -> "distinct";
            test_146.assert_(t_11829, fn__11821);
        } finally {
            test_146.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__2240() {
        Test test_147 = new Test();
        try {
            SafeIdentifier t_11807 = SrcTest.sid__638("users");
            SafeIdentifier t_11808 = SrcTest.sid__638("email");
            Query t_11809 = SrcGlobal.from(t_11807).select(List.of(t_11808));
            SqlBuilder t_11810 = new SqlBuilder();
            t_11810.appendSafe("active = ");
            t_11810.appendBoolean(true);
            Query q__1595 = t_11809.where(t_11810.getAccumulated()).distinct();
            boolean t_11819 = q__1595.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__11806 = () -> "distinct with where";
            test_147.assert_(t_11819, fn__11806);
        } finally {
            test_147.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__2242() {
        Test test_148 = new Test();
        try {
            Query q__1597 = SrcGlobal.from(SrcTest.sid__638("users"));
            boolean t_11804 = q__1597.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__11799 = () -> "countSql bare";
            test_148.assert_(t_11804, fn__11799);
        } finally {
            test_148.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__2243() {
        Test test_149 = new Test();
        try {
            SafeIdentifier t_11788 = SrcTest.sid__638("users");
            SqlBuilder t_11789 = new SqlBuilder();
            t_11789.appendSafe("active = ");
            t_11789.appendBoolean(true);
            SqlFragment t_11792 = t_11789.getAccumulated();
            Query q__1599 = SrcGlobal.from(t_11788).where(t_11792);
            boolean t_11797 = q__1599.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__11787 = () -> "countSql with where";
            test_149.assert_(t_11797, fn__11787);
        } finally {
            test_149.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__2245() {
        Test test_150 = new Test();
        try {
            SafeIdentifier t_11771 = SrcTest.sid__638("users");
            SafeIdentifier t_11772 = SrcTest.sid__638("orders");
            SqlBuilder t_11773 = new SqlBuilder();
            t_11773.appendSafe("users.id = orders.user_id");
            SqlFragment t_11775 = t_11773.getAccumulated();
            Query t_11776 = SrcGlobal.from(t_11771).innerJoin(t_11772, t_11775);
            SqlBuilder t_11777 = new SqlBuilder();
            t_11777.appendSafe("orders.total > ");
            t_11777.appendInt32(100);
            Query q__1601 = t_11776.where(t_11777.getAccumulated());
            boolean t_11785 = q__1601.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__11770 = () -> "countSql with join";
            test_150.assert_(t_11785, fn__11770);
        } finally {
            test_150.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__2248() {
        Test test_151 = new Test();
        try {
            SafeIdentifier t_11757 = SrcTest.sid__638("users");
            SqlBuilder t_11758 = new SqlBuilder();
            t_11758.appendSafe("active = ");
            t_11758.appendBoolean(true);
            SqlFragment t_11761 = t_11758.getAccumulated();
            Query t_5865;
            t_5865 = SrcGlobal.from(t_11757).where(t_11761).orderBy(SrcTest.sid__638("name"), true).limit(10);
            Query t_5866;
            t_5866 = t_5865.offset(20);
            Query q__1603 = t_5866;
            String s__1604 = q__1603.countSql().toString();
            boolean t_11768 = s__1604.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__11756 = () -> "countSql drops extras: " + s__1604;
            test_151.assert_(t_11768, fn__11756);
        } finally {
            test_151.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__2250() {
        Test test_152 = new Test();
        try {
            SafeIdentifier t_11724 = SrcTest.sid__638("orders");
            SqlFragment t_11727 = SrcGlobal.col(SrcTest.sid__638("orders"), SrcTest.sid__638("status"));
            SqlFragment t_11728 = SrcGlobal.countAll();
            SqlFragment t_11730 = SrcGlobal.sumCol(SrcTest.sid__638("total"));
            Query t_11731 = SrcGlobal.from(t_11724).selectExpr(List.of(t_11727, t_11728, t_11730));
            SafeIdentifier t_11732 = SrcTest.sid__638("users");
            SqlBuilder t_11733 = new SqlBuilder();
            t_11733.appendSafe("orders.user_id = users.id");
            Query t_11736 = t_11731.innerJoin(t_11732, t_11733.getAccumulated());
            SqlBuilder t_11737 = new SqlBuilder();
            t_11737.appendSafe("users.active = ");
            t_11737.appendBoolean(true);
            Query t_11743 = t_11736.where(t_11737.getAccumulated()).groupBy(SrcTest.sid__638("status"));
            SqlBuilder t_11744 = new SqlBuilder();
            t_11744.appendSafe("COUNT(*) > ");
            t_11744.appendInt32(3);
            Query q__1606 = t_11743.having(t_11744.getAccumulated()).orderBy(SrcTest.sid__638("status"), true);
            String expected__1607 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_11754 = q__1606.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__11723 = () -> "full aggregation";
            test_152.assert_(t_11754, fn__11723);
        } finally {
            test_152.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__2254() {
        Test test_153 = new Test();
        try {
            SafeIdentifier t_11706 = SrcTest.sid__638("users");
            SqlBuilder t_11707 = new SqlBuilder();
            t_11707.appendSafe("role = ");
            t_11707.appendString("admin");
            SqlFragment t_11710 = t_11707.getAccumulated();
            Query a__1609 = SrcGlobal.from(t_11706).where(t_11710);
            SafeIdentifier t_11712 = SrcTest.sid__638("users");
            SqlBuilder t_11713 = new SqlBuilder();
            t_11713.appendSafe("role = ");
            t_11713.appendString("moderator");
            SqlFragment t_11716 = t_11713.getAccumulated();
            Query b__1610 = SrcGlobal.from(t_11712).where(t_11716);
            String s__1611 = SrcGlobal.unionSql(a__1609, b__1610).toString();
            boolean t_11721 = s__1611.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__11705 = () -> "unionSql: " + s__1611;
            test_153.assert_(t_11721, fn__11705);
        } finally {
            test_153.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__2257() {
        Test test_154 = new Test();
        try {
            SafeIdentifier t_11694 = SrcTest.sid__638("users");
            SafeIdentifier t_11695 = SrcTest.sid__638("name");
            Query a__1613 = SrcGlobal.from(t_11694).select(List.of(t_11695));
            SafeIdentifier t_11697 = SrcTest.sid__638("contacts");
            SafeIdentifier t_11698 = SrcTest.sid__638("name");
            Query b__1614 = SrcGlobal.from(t_11697).select(List.of(t_11698));
            String s__1615 = SrcGlobal.unionAllSql(a__1613, b__1614).toString();
            boolean t_11703 = s__1615.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__11693 = () -> "unionAllSql: " + s__1615;
            test_154.assert_(t_11703, fn__11693);
        } finally {
            test_154.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__2258() {
        Test test_155 = new Test();
        try {
            SafeIdentifier t_11682 = SrcTest.sid__638("users");
            SafeIdentifier t_11683 = SrcTest.sid__638("email");
            Query a__1617 = SrcGlobal.from(t_11682).select(List.of(t_11683));
            SafeIdentifier t_11685 = SrcTest.sid__638("subscribers");
            SafeIdentifier t_11686 = SrcTest.sid__638("email");
            Query b__1618 = SrcGlobal.from(t_11685).select(List.of(t_11686));
            String s__1619 = SrcGlobal.intersectSql(a__1617, b__1618).toString();
            boolean t_11691 = s__1619.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__11681 = () -> "intersectSql: " + s__1619;
            test_155.assert_(t_11691, fn__11681);
        } finally {
            test_155.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__2259() {
        Test test_156 = new Test();
        try {
            SafeIdentifier t_11670 = SrcTest.sid__638("users");
            SafeIdentifier t_11671 = SrcTest.sid__638("id");
            Query a__1621 = SrcGlobal.from(t_11670).select(List.of(t_11671));
            SafeIdentifier t_11673 = SrcTest.sid__638("banned");
            SafeIdentifier t_11674 = SrcTest.sid__638("id");
            Query b__1622 = SrcGlobal.from(t_11673).select(List.of(t_11674));
            String s__1623 = SrcGlobal.exceptSql(a__1621, b__1622).toString();
            boolean t_11679 = s__1623.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__11669 = () -> "exceptSql: " + s__1623;
            test_156.assert_(t_11679, fn__11669);
        } finally {
            test_156.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__2260() {
        Test test_157 = new Test();
        try {
            SafeIdentifier t_11655 = SrcTest.sid__638("orders");
            SafeIdentifier t_11656 = SrcTest.sid__638("user_id");
            Query t_11657 = SrcGlobal.from(t_11655).select(List.of(t_11656));
            SqlBuilder t_11658 = new SqlBuilder();
            t_11658.appendSafe("total > ");
            t_11658.appendInt32(100);
            Query inner__1625 = t_11657.where(t_11658.getAccumulated());
            String s__1626 = SrcGlobal.subquery(inner__1625, SrcTest.sid__638("big_orders")).toString();
            boolean t_11667 = s__1626.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__11654 = () -> "subquery: " + s__1626;
            test_157.assert_(t_11667, fn__11654);
        } finally {
            test_157.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__2262() {
        Test test_158 = new Test();
        try {
            SafeIdentifier t_11644 = SrcTest.sid__638("orders");
            SqlBuilder t_11645 = new SqlBuilder();
            t_11645.appendSafe("orders.user_id = users.id");
            SqlFragment t_11647 = t_11645.getAccumulated();
            Query inner__1628 = SrcGlobal.from(t_11644).where(t_11647);
            String s__1629 = SrcGlobal.existsSql(inner__1628).toString();
            boolean t_11652 = s__1629.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__11643 = () -> "existsSql: " + s__1629;
            test_158.assert_(t_11652, fn__11643);
        } finally {
            test_158.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__2264() {
        Test test_159 = new Test();
        try {
            SafeIdentifier t_11627 = SrcTest.sid__638("orders");
            SafeIdentifier t_11628 = SrcTest.sid__638("user_id");
            Query t_11629 = SrcGlobal.from(t_11627).select(List.of(t_11628));
            SqlBuilder t_11630 = new SqlBuilder();
            t_11630.appendSafe("total > ");
            t_11630.appendInt32(1000);
            Query sub__1631 = t_11629.where(t_11630.getAccumulated());
            SafeIdentifier t_11635 = SrcTest.sid__638("users");
            SafeIdentifier t_11636 = SrcTest.sid__638("id");
            Query q__1632 = SrcGlobal.from(t_11635).whereInSubquery(t_11636, sub__1631);
            String s__1633 = q__1632.toSql().toString();
            boolean t_11641 = s__1633.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__11626 = () -> "whereInSubquery: " + s__1633;
            test_159.assert_(t_11641, fn__11626);
        } finally {
            test_159.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__2266() {
        Test test_160 = new Test();
        try {
            SafeIdentifier t_11604 = SrcTest.sid__638("users");
            SqlBuilder t_11605 = new SqlBuilder();
            t_11605.appendSafe("age > ");
            t_11605.appendInt32(18);
            SqlFragment t_11608 = t_11605.getAccumulated();
            Query t_11609 = SrcGlobal.from(t_11604).where(t_11608);
            SqlBuilder t_11610 = new SqlBuilder();
            t_11610.appendSafe("active = ");
            t_11610.appendBoolean(true);
            Query a__1635 = t_11609.where(t_11610.getAccumulated());
            SafeIdentifier t_11615 = SrcTest.sid__638("users");
            SqlBuilder t_11616 = new SqlBuilder();
            t_11616.appendSafe("role = ");
            t_11616.appendString("vip");
            SqlFragment t_11619 = t_11616.getAccumulated();
            Query b__1636 = SrcGlobal.from(t_11615).where(t_11619);
            String s__1637 = SrcGlobal.unionSql(a__1635, b__1636).toString();
            boolean t_11624 = s__1637.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__11603 = () -> "union with where: " + s__1637;
            test_160.assert_(t_11624, fn__11603);
        } finally {
            test_160.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__2270() {
        Test test_161 = new Test();
        try {
            SafeIdentifier t_11587 = SrcTest.sid__638("orders");
            SafeIdentifier t_11588 = SrcTest.sid__638("user_id");
            Query sub__1639 = SrcGlobal.from(t_11587).select(List.of(t_11588));
            SafeIdentifier t_11590 = SrcTest.sid__638("users");
            SqlBuilder t_11591 = new SqlBuilder();
            t_11591.appendSafe("active = ");
            t_11591.appendBoolean(true);
            SqlFragment t_11594 = t_11591.getAccumulated();
            Query q__1640 = SrcGlobal.from(t_11590).where(t_11594).whereInSubquery(SrcTest.sid__638("id"), sub__1639);
            String s__1641 = q__1640.toSql().toString();
            boolean t_11601 = s__1641.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__11586 = () -> "whereInSubquery chained: " + s__1641;
            test_161.assert_(t_11601, fn__11586);
        } finally {
            test_161.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__2272() {
        Test test_162 = new Test();
        try {
            SafeIdentifier t_11573 = SrcTest.sid__638("orders");
            SqlBuilder t_11574 = new SqlBuilder();
            t_11574.appendSafe("orders.user_id = users.id");
            SqlFragment t_11576 = t_11574.getAccumulated();
            Query sub__1643 = SrcGlobal.from(t_11573).where(t_11576);
            SafeIdentifier t_11578 = SrcTest.sid__638("users");
            SqlFragment t_11579 = SrcGlobal.existsSql(sub__1643);
            Query q__1644 = SrcGlobal.from(t_11578).where(t_11579);
            String s__1645 = q__1644.toSql().toString();
            boolean t_11584 = s__1645.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__11572 = () -> "exists in where: " + s__1645;
            test_162.assert_(t_11584, fn__11572);
        } finally {
            test_162.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBasic__2274() {
        Test test_163 = new Test();
        try {
            SafeIdentifier t_11559 = SrcTest.sid__638("users");
            SafeIdentifier t_11560 = SrcTest.sid__638("name");
            SqlString t_11561 = new SqlString("Alice");
            UpdateQuery t_11562 = SrcGlobal.update(t_11559).set(t_11560, t_11561);
            SqlBuilder t_11563 = new SqlBuilder();
            t_11563.appendSafe("id = ");
            t_11563.appendInt32(1);
            SqlFragment t_5687;
            t_5687 = t_11562.where(t_11563.getAccumulated()).toSql();
            SqlFragment q__1647 = t_5687;
            boolean t_11570 = q__1647.toString().equals("UPDATE users SET name = 'Alice' WHERE id = 1");
            Supplier<String> fn__11558 = () -> "update basic";
            test_163.assert_(t_11570, fn__11558);
        } finally {
            test_163.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleSet__2276() {
        Test test_164 = new Test();
        try {
            SafeIdentifier t_11542 = SrcTest.sid__638("users");
            SafeIdentifier t_11543 = SrcTest.sid__638("name");
            SqlString t_11544 = new SqlString("Bob");
            UpdateQuery t_11548 = SrcGlobal.update(t_11542).set(t_11543, t_11544).set(SrcTest.sid__638("age"), new SqlInt32(30));
            SqlBuilder t_11549 = new SqlBuilder();
            t_11549.appendSafe("id = ");
            t_11549.appendInt32(2);
            SqlFragment t_5672;
            t_5672 = t_11548.where(t_11549.getAccumulated()).toSql();
            SqlFragment q__1649 = t_5672;
            boolean t_11556 = q__1649.toString().equals("UPDATE users SET name = 'Bob', age = 30 WHERE id = 2");
            Supplier<String> fn__11541 = () -> "update multi set";
            test_164.assert_(t_11556, fn__11541);
        } finally {
            test_164.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleWhere__2278() {
        Test test_165 = new Test();
        try {
            SafeIdentifier t_11523 = SrcTest.sid__638("users");
            SafeIdentifier t_11524 = SrcTest.sid__638("active");
            SqlBoolean t_11525 = new SqlBoolean(false);
            UpdateQuery t_11526 = SrcGlobal.update(t_11523).set(t_11524, t_11525);
            SqlBuilder t_11527 = new SqlBuilder();
            t_11527.appendSafe("age < ");
            t_11527.appendInt32(18);
            UpdateQuery t_11531 = t_11526.where(t_11527.getAccumulated());
            SqlBuilder t_11532 = new SqlBuilder();
            t_11532.appendSafe("role = ");
            t_11532.appendString("guest");
            SqlFragment t_5654;
            t_5654 = t_11531.where(t_11532.getAccumulated()).toSql();
            SqlFragment q__1651 = t_5654;
            boolean t_11539 = q__1651.toString().equals("UPDATE users SET active = FALSE WHERE age < 18 AND role = 'guest'");
            Supplier<String> fn__11522 = () -> "update multi where";
            test_165.assert_(t_11539, fn__11522);
        } finally {
            test_165.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryOrWhere__2281() {
        Test test_166 = new Test();
        try {
            SafeIdentifier t_11504 = SrcTest.sid__638("users");
            SafeIdentifier t_11505 = SrcTest.sid__638("status");
            SqlString t_11506 = new SqlString("banned");
            UpdateQuery t_11507 = SrcGlobal.update(t_11504).set(t_11505, t_11506);
            SqlBuilder t_11508 = new SqlBuilder();
            t_11508.appendSafe("spam_count > ");
            t_11508.appendInt32(10);
            UpdateQuery t_11512 = t_11507.where(t_11508.getAccumulated());
            SqlBuilder t_11513 = new SqlBuilder();
            t_11513.appendSafe("reported = ");
            t_11513.appendBoolean(true);
            SqlFragment t_5633;
            t_5633 = t_11512.orWhere(t_11513.getAccumulated()).toSql();
            SqlFragment q__1653 = t_5633;
            boolean t_11520 = q__1653.toString().equals("UPDATE users SET status = 'banned' WHERE spam_count > 10 OR reported = TRUE");
            Supplier<String> fn__11503 = () -> "update orWhere";
            test_166.assert_(t_11520, fn__11503);
        } finally {
            test_166.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutWhere__2284() {
        Test test_167 = new Test();
        try {
            SafeIdentifier t_11497;
            SafeIdentifier t_11498;
            SqlInt32 t_11499;
            boolean didBubble__1655;
            boolean didBubble_14336;
            try {
                t_11497 = SrcTest.sid__638("users");
                t_11498 = SrcTest.sid__638("x");
                t_11499 = new SqlInt32(1);
                SrcGlobal.update(t_11497).set(t_11498, t_11499).toSql();
                didBubble_14336 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_14336 = true;
            }
            didBubble__1655 = didBubble_14336;
            Supplier<String> fn__11496 = () -> "update without WHERE should bubble";
            test_167.assert_(didBubble__1655, fn__11496);
        } finally {
            test_167.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutSet__2285() {
        Test test_168 = new Test();
        try {
            SafeIdentifier t_11488;
            SqlBuilder t_11489;
            SqlFragment t_11492;
            boolean didBubble__1657;
            boolean didBubble_14337;
            try {
                t_11488 = SrcTest.sid__638("users");
                t_11489 = new SqlBuilder();
                t_11489.appendSafe("id = ");
                t_11489.appendInt32(1);
                t_11492 = t_11489.getAccumulated();
                SrcGlobal.update(t_11488).where(t_11492).toSql();
                didBubble_14337 = false;
            } catch (RuntimeException ignored$15) {
                didBubble_14337 = true;
            }
            didBubble__1657 = didBubble_14337;
            Supplier<String> fn__11487 = () -> "update without SET should bubble";
            test_168.assert_(didBubble__1657, fn__11487);
        } finally {
            test_168.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryWithLimit__2287() {
        Test test_169 = new Test();
        try {
            SafeIdentifier t_11474 = SrcTest.sid__638("users");
            SafeIdentifier t_11475 = SrcTest.sid__638("active");
            SqlBoolean t_11476 = new SqlBoolean(false);
            UpdateQuery t_11477 = SrcGlobal.update(t_11474).set(t_11475, t_11476);
            SqlBuilder t_11478 = new SqlBuilder();
            t_11478.appendSafe("last_login < ");
            t_11478.appendString("2024-01-01");
            UpdateQuery t_5596;
            t_5596 = t_11477.where(t_11478.getAccumulated()).limit(100);
            SqlFragment t_5597;
            t_5597 = t_5596.toSql();
            SqlFragment q__1659 = t_5597;
            boolean t_11485 = q__1659.toString().equals("UPDATE users SET active = FALSE WHERE last_login < '2024-01-01' LIMIT 100");
            Supplier<String> fn__11473 = () -> "update limit";
            test_169.assert_(t_11485, fn__11473);
        } finally {
            test_169.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryEscaping__2289() {
        Test test_170 = new Test();
        try {
            SafeIdentifier t_11460 = SrcTest.sid__638("users");
            SafeIdentifier t_11461 = SrcTest.sid__638("bio");
            SqlString t_11462 = new SqlString("It's a test");
            UpdateQuery t_11463 = SrcGlobal.update(t_11460).set(t_11461, t_11462);
            SqlBuilder t_11464 = new SqlBuilder();
            t_11464.appendSafe("id = ");
            t_11464.appendInt32(1);
            SqlFragment t_5581;
            t_5581 = t_11463.where(t_11464.getAccumulated()).toSql();
            SqlFragment q__1661 = t_5581;
            boolean t_11471 = q__1661.toString().equals("UPDATE users SET bio = 'It''s a test' WHERE id = 1");
            Supplier<String> fn__11459 = () -> "update escaping";
            test_170.assert_(t_11471, fn__11459);
        } finally {
            test_170.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBasic__2291() {
        Test test_171 = new Test();
        try {
            SafeIdentifier t_11449 = SrcTest.sid__638("users");
            SqlBuilder t_11450 = new SqlBuilder();
            t_11450.appendSafe("id = ");
            t_11450.appendInt32(1);
            SqlFragment t_11453 = t_11450.getAccumulated();
            SqlFragment t_5566;
            t_5566 = SrcGlobal.deleteFrom(t_11449).where(t_11453).toSql();
            SqlFragment q__1663 = t_5566;
            boolean t_11457 = q__1663.toString().equals("DELETE FROM users WHERE id = 1");
            Supplier<String> fn__11448 = () -> "delete basic";
            test_171.assert_(t_11457, fn__11448);
        } finally {
            test_171.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryMultipleWhere__2293() {
        Test test_172 = new Test();
        try {
            SafeIdentifier t_11433 = SrcTest.sid__638("logs");
            SqlBuilder t_11434 = new SqlBuilder();
            t_11434.appendSafe("created_at < ");
            t_11434.appendString("2024-01-01");
            SqlFragment t_11437 = t_11434.getAccumulated();
            DeleteQuery t_11438 = SrcGlobal.deleteFrom(t_11433).where(t_11437);
            SqlBuilder t_11439 = new SqlBuilder();
            t_11439.appendSafe("level = ");
            t_11439.appendString("debug");
            SqlFragment t_5554;
            t_5554 = t_11438.where(t_11439.getAccumulated()).toSql();
            SqlFragment q__1665 = t_5554;
            boolean t_11446 = q__1665.toString().equals("DELETE FROM logs WHERE created_at < '2024-01-01' AND level = 'debug'");
            Supplier<String> fn__11432 = () -> "delete multi where";
            test_172.assert_(t_11446, fn__11432);
        } finally {
            test_172.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBubblesWithoutWhere__2296() {
        Test test_173 = new Test();
        try {
            boolean didBubble__1667;
            boolean didBubble_14338;
            try {
                SrcGlobal.deleteFrom(SrcTest.sid__638("users")).toSql();
                didBubble_14338 = false;
            } catch (RuntimeException ignored$16) {
                didBubble_14338 = true;
            }
            didBubble__1667 = didBubble_14338;
            Supplier<String> fn__11428 = () -> "delete without WHERE should bubble";
            test_173.assert_(didBubble__1667, fn__11428);
        } finally {
            test_173.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryOrWhere__2297() {
        Test test_174 = new Test();
        try {
            SafeIdentifier t_11413 = SrcTest.sid__638("sessions");
            SqlBuilder t_11414 = new SqlBuilder();
            t_11414.appendSafe("expired = ");
            t_11414.appendBoolean(true);
            SqlFragment t_11417 = t_11414.getAccumulated();
            DeleteQuery t_11418 = SrcGlobal.deleteFrom(t_11413).where(t_11417);
            SqlBuilder t_11419 = new SqlBuilder();
            t_11419.appendSafe("created_at < ");
            t_11419.appendString("2023-01-01");
            SqlFragment t_5533;
            t_5533 = t_11418.orWhere(t_11419.getAccumulated()).toSql();
            SqlFragment q__1669 = t_5533;
            boolean t_11426 = q__1669.toString().equals("DELETE FROM sessions WHERE expired = TRUE OR created_at < '2023-01-01'");
            Supplier<String> fn__11412 = () -> "delete orWhere";
            test_174.assert_(t_11426, fn__11412);
        } finally {
            test_174.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryWithLimit__2300() {
        Test test_175 = new Test();
        try {
            SafeIdentifier t_11402 = SrcTest.sid__638("logs");
            SqlBuilder t_11403 = new SqlBuilder();
            t_11403.appendSafe("level = ");
            t_11403.appendString("debug");
            SqlFragment t_11406 = t_11403.getAccumulated();
            DeleteQuery t_5514;
            t_5514 = SrcGlobal.deleteFrom(t_11402).where(t_11406).limit(1000);
            SqlFragment t_5515;
            t_5515 = t_5514.toSql();
            SqlFragment q__1671 = t_5515;
            boolean t_11410 = q__1671.toString().equals("DELETE FROM logs WHERE level = 'debug' LIMIT 1000");
            Supplier<String> fn__11401 = () -> "delete limit";
            test_175.assert_(t_11410, fn__11401);
        } finally {
            test_175.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsFirst__2302() {
        Test test_176 = new Test();
        try {
            SafeIdentifier t_11392 = SrcTest.sid__638("users");
            SafeIdentifier t_11393 = SrcTest.sid__638("email");
            NullsFirst t_11394 = new NullsFirst();
            Query q__1673 = SrcGlobal.from(t_11392).orderByNulls(t_11393, true, t_11394);
            boolean t_11399 = q__1673.toSql().toString().equals("SELECT * FROM users ORDER BY email ASC NULLS FIRST");
            Supplier<String> fn__11391 = () -> "nulls first";
            test_176.assert_(t_11399, fn__11391);
        } finally {
            test_176.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsLast__2303() {
        Test test_177 = new Test();
        try {
            SafeIdentifier t_11382 = SrcTest.sid__638("users");
            SafeIdentifier t_11383 = SrcTest.sid__638("score");
            NullsLast t_11384 = new NullsLast();
            Query q__1675 = SrcGlobal.from(t_11382).orderByNulls(t_11383, false, t_11384);
            boolean t_11389 = q__1675.toSql().toString().equals("SELECT * FROM users ORDER BY score DESC NULLS LAST");
            Supplier<String> fn__11381 = () -> "nulls last";
            test_177.assert_(t_11389, fn__11381);
        } finally {
            test_177.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedOrderByAndOrderByNulls__2304() {
        Test test_178 = new Test();
        try {
            SafeIdentifier t_11370 = SrcTest.sid__638("users");
            SafeIdentifier t_11371 = SrcTest.sid__638("name");
            Query q__1677 = SrcGlobal.from(t_11370).orderBy(t_11371, true).orderByNulls(SrcTest.sid__638("email"), true, new NullsFirst());
            boolean t_11379 = q__1677.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC, email ASC NULLS FIRST");
            Supplier<String> fn__11369 = () -> "mixed order";
            test_178.assert_(t_11379, fn__11369);
        } finally {
            test_178.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoin__2305() {
        Test test_179 = new Test();
        try {
            SafeIdentifier t_11361 = SrcTest.sid__638("users");
            SafeIdentifier t_11362 = SrcTest.sid__638("colors");
            Query q__1679 = SrcGlobal.from(t_11361).crossJoin(t_11362);
            boolean t_11367 = q__1679.toSql().toString().equals("SELECT * FROM users CROSS JOIN colors");
            Supplier<String> fn__11360 = () -> "cross join";
            test_179.assert_(t_11367, fn__11360);
        } finally {
            test_179.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoinCombinedWithOtherJoins__2306() {
        Test test_180 = new Test();
        try {
            SafeIdentifier t_11347 = SrcTest.sid__638("users");
            SafeIdentifier t_11348 = SrcTest.sid__638("orders");
            SqlBuilder t_11349 = new SqlBuilder();
            t_11349.appendSafe("users.id = orders.user_id");
            SqlFragment t_11351 = t_11349.getAccumulated();
            Query q__1681 = SrcGlobal.from(t_11347).innerJoin(t_11348, t_11351).crossJoin(SrcTest.sid__638("colors"));
            boolean t_11358 = q__1681.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id CROSS JOIN colors");
            Supplier<String> fn__11346 = () -> "cross + inner join";
            test_180.assert_(t_11358, fn__11346);
        } finally {
            test_180.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForUpdate__2308() {
        Test test_181 = new Test();
        try {
            SafeIdentifier t_11333 = SrcTest.sid__638("users");
            SqlBuilder t_11334 = new SqlBuilder();
            t_11334.appendSafe("id = ");
            t_11334.appendInt32(1);
            SqlFragment t_11337 = t_11334.getAccumulated();
            Query q__1683 = SrcGlobal.from(t_11333).where(t_11337).lock(new ForUpdate());
            boolean t_11344 = q__1683.toSql().toString().equals("SELECT * FROM users WHERE id = 1 FOR UPDATE");
            Supplier<String> fn__11332 = () -> "for update";
            test_181.assert_(t_11344, fn__11332);
        } finally {
            test_181.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForShare__2310() {
        Test test_182 = new Test();
        try {
            SafeIdentifier t_11322 = SrcTest.sid__638("users");
            SafeIdentifier t_11323 = SrcTest.sid__638("name");
            Query q__1685 = SrcGlobal.from(t_11322).select(List.of(t_11323)).lock(new ForShare());
            boolean t_11330 = q__1685.toSql().toString().equals("SELECT name FROM users FOR SHARE");
            Supplier<String> fn__11321 = () -> "for share";
            test_182.assert_(t_11330, fn__11321);
        } finally {
            test_182.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockWithFullQuery__2311() {
        Test test_183 = new Test();
        try {
            SafeIdentifier t_11308 = SrcTest.sid__638("accounts");
            SqlBuilder t_11309 = new SqlBuilder();
            t_11309.appendSafe("id = ");
            t_11309.appendInt32(42);
            SqlFragment t_11312 = t_11309.getAccumulated();
            Query t_5438;
            t_5438 = SrcGlobal.from(t_11308).where(t_11312).limit(1);
            Query t_11315 = t_5438.lock(new ForUpdate());
            Query q__1687 = t_11315;
            boolean t_11319 = q__1687.toSql().toString().equals("SELECT * FROM accounts WHERE id = 42 LIMIT 1 FOR UPDATE");
            Supplier<String> fn__11307 = () -> "lock full query";
            test_183.assert_(t_11319, fn__11307);
        } finally {
            test_183.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__2313() {
        Test test_190 = new Test();
        try {
            SafeIdentifier t_5427;
            t_5427 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1725 = t_5427;
            boolean t_11305 = id__1725.getSqlValue().equals("user_name");
            Supplier<String> fn__11302 = () -> "value should round-trip";
            test_190.assert_(t_11305, fn__11302);
        } finally {
            test_190.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__2314() {
        Test test_191 = new Test();
        try {
            boolean didBubble__1727;
            boolean didBubble_14339;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_14339 = false;
            } catch (RuntimeException ignored$17) {
                didBubble_14339 = true;
            }
            didBubble__1727 = didBubble_14339;
            Supplier<String> fn__11299 = () -> "empty string should bubble";
            test_191.assert_(didBubble__1727, fn__11299);
        } finally {
            test_191.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__2315() {
        Test test_192 = new Test();
        try {
            boolean didBubble__1729;
            boolean didBubble_14340;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_14340 = false;
            } catch (RuntimeException ignored$18) {
                didBubble_14340 = true;
            }
            didBubble__1729 = didBubble_14340;
            Supplier<String> fn__11296 = () -> "leading digit should bubble";
            test_192.assert_(didBubble__1729, fn__11296);
        } finally {
            test_192.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__2316() {
        Test test_193 = new Test();
        try {
            List<String> cases__1731 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__11293 = c__1732 -> {
                boolean didBubble__1733;
                boolean didBubble_14341;
                try {
                    SrcGlobal.safeIdentifier(c__1732);
                    didBubble_14341 = false;
                } catch (RuntimeException ignored$19) {
                    didBubble_14341 = true;
                }
                didBubble__1733 = didBubble_14341;
                Supplier<String> fn__11290 = () -> "should reject: " + c__1732;
                test_193.assert_(didBubble__1733, fn__11290);
            };
            cases__1731.forEach(fn__11293);
        } finally {
            test_193.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__2317() {
        Test test_194 = new Test();
        try {
            SafeIdentifier t_5404;
            t_5404 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5405 = t_5404;
            SafeIdentifier t_5406;
            t_5406 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5407 = t_5406;
            StringField t_11280 = new StringField();
            FieldDef t_11281 = new FieldDef(t_5407, t_11280, false);
            SafeIdentifier t_5410;
            t_5410 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_5411 = t_5410;
            IntField t_11282 = new IntField();
            FieldDef t_11283 = new FieldDef(t_5411, t_11282, false);
            TableDef td__1735 = new TableDef(t_5405, List.of(t_11281, t_11283));
            FieldDef t_5415;
            t_5415 = td__1735.field("age");
            FieldDef f__1736 = t_5415;
            boolean t_11288 = f__1736.getName().getSqlValue().equals("age");
            Supplier<String> fn__11279 = () -> "should find age field";
            test_194.assert_(t_11288, fn__11279);
        } finally {
            test_194.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__2318() {
        Test test_195 = new Test();
        try {
            SafeIdentifier t_5395;
            t_5395 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5396 = t_5395;
            SafeIdentifier t_5397;
            t_5397 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5398 = t_5397;
            StringField t_11274 = new StringField();
            FieldDef t_11275 = new FieldDef(t_5398, t_11274, false);
            TableDef td__1738 = new TableDef(t_5396, List.of(t_11275));
            boolean didBubble__1739;
            boolean didBubble_14342;
            try {
                td__1738.field("nonexistent");
                didBubble_14342 = false;
            } catch (RuntimeException ignored$20) {
                didBubble_14342 = true;
            }
            didBubble__1739 = didBubble_14342;
            Supplier<String> fn__11273 = () -> "unknown field should bubble";
            test_195.assert_(didBubble__1739, fn__11273);
        } finally {
            test_195.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__2319() {
        Test test_196 = new Test();
        try {
            SafeIdentifier t_5383;
            t_5383 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_5384 = t_5383;
            StringField t_11262 = new StringField();
            FieldDef required__1741 = new FieldDef(t_5384, t_11262, false);
            SafeIdentifier t_5387;
            t_5387 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_5388 = t_5387;
            StringField t_11264 = new StringField();
            FieldDef optional__1742 = new FieldDef(t_5388, t_11264, true);
            boolean t_11268 = !required__1741.isNullable();
            Supplier<String> fn__11261 = () -> "required field should not be nullable";
            test_196.assert_(t_11268, fn__11261);
            boolean t_11270 = optional__1742.isNullable();
            Supplier<String> fn__11260 = () -> "optional field should be nullable";
            test_196.assert_(t_11270, fn__11260);
        } finally {
            test_196.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__2320() {
        Test test_200 = new Test();
        try {
            Function<String, String> build__1868 = name__1870 -> {
                SqlBuilder t_11242 = new SqlBuilder();
                t_11242.appendSafe("select * from hi where name = ");
                t_11242.appendString(name__1870);
                return t_11242.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1869 = name__1872 -> "select * from hi where name = '" + name__1872 + "'";
            String actual_2322 = build__1868.apply("world");
            boolean t_11252 = actual_2322.equals("select * from hi where name = 'world'");
            Supplier<String> fn__11249 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_2322 + ")";
            test_200.assert_(t_11252, fn__11249);
            String bobbyTables__1874 = "Robert'); drop table hi;--";
            String actual_2324 = build__1868.apply("Robert'); drop table hi;--");
            boolean t_11256 = actual_2324.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__11248 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_2324 + ")";
            test_200.assert_(t_11256, fn__11248);
            Supplier<String> fn__11247 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_200.assert_(true, fn__11247);
        } finally {
            test_200.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__2328() {
        Test test_201 = new Test();
        try {
            SqlBuilder t_11210 = new SqlBuilder();
            t_11210.appendSafe("v = ");
            t_11210.appendString("");
            String actual_2329 = t_11210.getAccumulated().toString();
            boolean t_11216 = actual_2329.equals("v = ''");
            Supplier<String> fn__11209 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_2329 + ")";
            test_201.assert_(t_11216, fn__11209);
            SqlBuilder t_11218 = new SqlBuilder();
            t_11218.appendSafe("v = ");
            t_11218.appendString("a''b");
            String actual_2332 = t_11218.getAccumulated().toString();
            boolean t_11224 = actual_2332.equals("v = 'a''''b'");
            Supplier<String> fn__11208 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_2332 + ")";
            test_201.assert_(t_11224, fn__11208);
            SqlBuilder t_11226 = new SqlBuilder();
            t_11226.appendSafe("v = ");
            t_11226.appendString("Hello \u4e16\u754c");
            String actual_2335 = t_11226.getAccumulated().toString();
            boolean t_11232 = actual_2335.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__11207 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_2335 + ")";
            test_201.assert_(t_11232, fn__11207);
            SqlBuilder t_11234 = new SqlBuilder();
            t_11234.appendSafe("v = ");
            t_11234.appendString("Line1\nLine2");
            String actual_2338 = t_11234.getAccumulated().toString();
            boolean t_11240 = actual_2338.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__11206 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_2338 + ")";
            test_201.assert_(t_11240, fn__11206);
        } finally {
            test_201.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__2341() {
        Test test_202 = new Test();
        try {
            SqlBuilder t_11181 = new SqlBuilder();
            t_11181.appendSafe("select ");
            t_11181.appendInt32(42);
            t_11181.appendSafe(", ");
            t_11181.appendInt64(43);
            t_11181.appendSafe(", ");
            t_11181.appendFloat64(19.99D);
            t_11181.appendSafe(", ");
            t_11181.appendBoolean(true);
            t_11181.appendSafe(", ");
            t_11181.appendBoolean(false);
            String actual_2342 = t_11181.getAccumulated().toString();
            boolean t_11195 = actual_2342.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__11180 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_2342 + ")";
            test_202.assert_(t_11195, fn__11180);
            LocalDate t_5328;
            t_5328 = LocalDate.of(2024, 12, 25);
            LocalDate date__1877 = t_5328;
            SqlBuilder t_11197 = new SqlBuilder();
            t_11197.appendSafe("insert into t values (");
            t_11197.appendDate(date__1877);
            t_11197.appendSafe(")");
            String actual_2345 = t_11197.getAccumulated().toString();
            boolean t_11204 = actual_2345.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__11179 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_2345 + ")";
            test_202.assert_(t_11204, fn__11179);
        } finally {
            test_202.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__2348() {
        Test test_203 = new Test();
        try {
            SqlBuilder t_11125 = new SqlBuilder();
            t_11125.appendSafe("v IN (");
            t_11125.appendStringList(List.of("a", "b", "c'd"));
            t_11125.appendSafe(")");
            String actual_2349 = t_11125.getAccumulated().toString();
            boolean t_11132 = actual_2349.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__11124 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_2349 + ")";
            test_203.assert_(t_11132, fn__11124);
            SqlBuilder t_11134 = new SqlBuilder();
            t_11134.appendSafe("v IN (");
            t_11134.appendInt32List(List.of(1, 2, 3));
            t_11134.appendSafe(")");
            String actual_2352 = t_11134.getAccumulated().toString();
            boolean t_11141 = actual_2352.equals("v IN (1, 2, 3)");
            Supplier<String> fn__11123 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_2352 + ")";
            test_203.assert_(t_11141, fn__11123);
            SqlBuilder t_11143 = new SqlBuilder();
            t_11143.appendSafe("v IN (");
            t_11143.appendInt64List(List.of(1, 2));
            t_11143.appendSafe(")");
            String actual_2355 = t_11143.getAccumulated().toString();
            boolean t_11150 = actual_2355.equals("v IN (1, 2)");
            Supplier<String> fn__11122 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_2355 + ")";
            test_203.assert_(t_11150, fn__11122);
            SqlBuilder t_11152 = new SqlBuilder();
            t_11152.appendSafe("v IN (");
            t_11152.appendFloat64List(List.of(1.0D, 2.0D));
            t_11152.appendSafe(")");
            String actual_2358 = t_11152.getAccumulated().toString();
            boolean t_11159 = actual_2358.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__11121 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_2358 + ")";
            test_203.assert_(t_11159, fn__11121);
            SqlBuilder t_11161 = new SqlBuilder();
            t_11161.appendSafe("v IN (");
            t_11161.appendBooleanList(List.of(true, false));
            t_11161.appendSafe(")");
            String actual_2361 = t_11161.getAccumulated().toString();
            boolean t_11168 = actual_2361.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__11120 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_2361 + ")";
            test_203.assert_(t_11168, fn__11120);
            LocalDate t_5300;
            t_5300 = LocalDate.of(2024, 1, 1);
            LocalDate t_5301 = t_5300;
            LocalDate t_5302;
            t_5302 = LocalDate.of(2024, 12, 25);
            LocalDate t_5303 = t_5302;
            List<LocalDate> dates__1879 = List.of(t_5301, t_5303);
            SqlBuilder t_11170 = new SqlBuilder();
            t_11170.appendSafe("v IN (");
            t_11170.appendDateList(dates__1879);
            t_11170.appendSafe(")");
            String actual_2364 = t_11170.getAccumulated().toString();
            boolean t_11177 = actual_2364.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__11119 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_2364 + ")";
            test_203.assert_(t_11177, fn__11119);
        } finally {
            test_203.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__2367() {
        Test test_204 = new Test();
        try {
            double nan__1881;
            nan__1881 = 0.0D / 0.0D;
            SqlBuilder t_11111 = new SqlBuilder();
            t_11111.appendSafe("v = ");
            t_11111.appendFloat64(nan__1881);
            String actual_2368 = t_11111.getAccumulated().toString();
            boolean t_11117 = actual_2368.equals("v = NULL");
            Supplier<String> fn__11110 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_2368 + ")";
            test_204.assert_(t_11117, fn__11110);
        } finally {
            test_204.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__2371() {
        Test test_205 = new Test();
        try {
            double inf__1883;
            inf__1883 = 1.0D / 0.0D;
            SqlBuilder t_11102 = new SqlBuilder();
            t_11102.appendSafe("v = ");
            t_11102.appendFloat64(inf__1883);
            String actual_2372 = t_11102.getAccumulated().toString();
            boolean t_11108 = actual_2372.equals("v = NULL");
            Supplier<String> fn__11101 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_2372 + ")";
            test_205.assert_(t_11108, fn__11101);
        } finally {
            test_205.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__2375() {
        Test test_206 = new Test();
        try {
            double ninf__1885;
            ninf__1885 = -1.0D / 0.0D;
            SqlBuilder t_11093 = new SqlBuilder();
            t_11093.appendSafe("v = ");
            t_11093.appendFloat64(ninf__1885);
            String actual_2376 = t_11093.getAccumulated().toString();
            boolean t_11099 = actual_2376.equals("v = NULL");
            Supplier<String> fn__11092 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_2376 + ")";
            test_206.assert_(t_11099, fn__11092);
        } finally {
            test_206.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__2379() {
        Test test_207 = new Test();
        try {
            SqlBuilder t_11068 = new SqlBuilder();
            t_11068.appendSafe("v = ");
            t_11068.appendFloat64(3.14D);
            String actual_2380 = t_11068.getAccumulated().toString();
            boolean t_11074 = actual_2380.equals("v = 3.14");
            Supplier<String> fn__11067 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_2380 + ")";
            test_207.assert_(t_11074, fn__11067);
            SqlBuilder t_11076 = new SqlBuilder();
            t_11076.appendSafe("v = ");
            t_11076.appendFloat64(0.0D);
            String actual_2383 = t_11076.getAccumulated().toString();
            boolean t_11082 = actual_2383.equals("v = 0.0");
            Supplier<String> fn__11066 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_2383 + ")";
            test_207.assert_(t_11082, fn__11066);
            SqlBuilder t_11084 = new SqlBuilder();
            t_11084.appendSafe("v = ");
            t_11084.appendFloat64(-42.5D);
            String actual_2386 = t_11084.getAccumulated().toString();
            boolean t_11090 = actual_2386.equals("v = -42.5");
            Supplier<String> fn__11065 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_2386 + ")";
            test_207.assert_(t_11090, fn__11065);
        } finally {
            test_207.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__2389() {
        Test test_208 = new Test();
        try {
            LocalDate t_5196;
            t_5196 = LocalDate.of(2024, 6, 15);
            LocalDate d__1888 = t_5196;
            SqlBuilder t_11057 = new SqlBuilder();
            t_11057.appendSafe("v = ");
            t_11057.appendDate(d__1888);
            String actual_2390 = t_11057.getAccumulated().toString();
            boolean t_11063 = actual_2390.equals("v = '2024-06-15'");
            Supplier<String> fn__11056 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_2390 + ")";
            test_208.assert_(t_11063, fn__11056);
        } finally {
            test_208.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__2393() {
        Test test_209 = new Test();
        try {
            String name__1890 = "Someone";
            SqlBuilder t_11025 = new SqlBuilder();
            t_11025.appendSafe("where p.last_name = ");
            t_11025.appendString("Someone");
            SqlFragment condition__1891 = t_11025.getAccumulated();
            SqlBuilder t_11029 = new SqlBuilder();
            t_11029.appendSafe("select p.id from person p ");
            t_11029.appendFragment(condition__1891);
            String actual_2395 = t_11029.getAccumulated().toString();
            boolean t_11035 = actual_2395.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__11024 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2395 + ")";
            test_209.assert_(t_11035, fn__11024);
            SqlBuilder t_11037 = new SqlBuilder();
            t_11037.appendSafe("select p.id from person p ");
            t_11037.appendPart(condition__1891.toSource());
            String actual_2398 = t_11037.getAccumulated().toString();
            boolean t_11044 = actual_2398.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__11023 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2398 + ")";
            test_209.assert_(t_11044, fn__11023);
            List<SqlPart> parts__1892 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_11048 = new SqlBuilder();
            t_11048.appendSafe("select ");
            t_11048.appendPartList(parts__1892);
            String actual_2401 = t_11048.getAccumulated().toString();
            boolean t_11054 = actual_2401.equals("select 'a''b', 3");
            Supplier<String> fn__11022 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_2401 + ")";
            test_209.assert_(t_11054, fn__11022);
        } finally {
            test_209.softFailToHard();
        }
    }
}
