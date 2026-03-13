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
import java.util.ArrayList;
public final class SrcTest {
    private SrcTest() {
    }
    static SafeIdentifier csid__660(String name__953) {
        SafeIdentifier t_8354;
        t_8354 = SrcGlobal.safeIdentifier(name__953);
        return t_8354;
    }
    static TableDef userTable__661() {
        return new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("email"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("age"), new IntField(), true, null, false), new FieldDef(SrcTest.csid__660("score"), new FloatField(), true, null, false), new FieldDef(SrcTest.csid__660("active"), new BoolField(), true, null, false)), null);
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__2120() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__957 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_15072 = SrcTest.userTable__661();
            SafeIdentifier t_15073 = SrcTest.csid__660("name");
            SafeIdentifier t_15074 = SrcTest.csid__660("email");
            Changeset cs__958 = SrcGlobal.changeset(t_15072, params__957).cast(List.of(t_15073, t_15074));
            boolean t_15077 = cs__958.getChanges().containsKey("name");
            Supplier<String> fn__15067 = () -> "name should be in changes";
            test_32.assert_(t_15077, fn__15067);
            boolean t_15081 = cs__958.getChanges().containsKey("email");
            Supplier<String> fn__15066 = () -> "email should be in changes";
            test_32.assert_(t_15081, fn__15066);
            boolean t_15087 = !cs__958.getChanges().containsKey("admin");
            Supplier<String> fn__15065 = () -> "admin must be dropped (not in whitelist)";
            test_32.assert_(t_15087, fn__15065);
            boolean t_15089 = cs__958.isValid();
            Supplier<String> fn__15064 = () -> "should still be valid";
            test_32.assert_(t_15089, fn__15064);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__2121() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__960 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_15050 = SrcTest.userTable__661();
            SafeIdentifier t_15051 = SrcTest.csid__660("name");
            Changeset cs__961 = SrcGlobal.changeset(t_15050, params__960).cast(List.of(t_15051)).cast(List.of(SrcTest.csid__660("email")));
            boolean t_15058 = !cs__961.getChanges().containsKey("name");
            Supplier<String> fn__15046 = () -> "name must be excluded by second cast";
            test_33.assert_(t_15058, fn__15046);
            boolean t_15061 = cs__961.getChanges().containsKey("email");
            Supplier<String> fn__15045 = () -> "email should be present";
            test_33.assert_(t_15061, fn__15045);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__2122() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__963 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_15032 = SrcTest.userTable__661();
            SafeIdentifier t_15033 = SrcTest.csid__660("name");
            SafeIdentifier t_15034 = SrcTest.csid__660("email");
            Changeset cs__964 = SrcGlobal.changeset(t_15032, params__963).cast(List.of(t_15033, t_15034));
            boolean t_15039 = !cs__964.getChanges().containsKey("name");
            Supplier<String> fn__15028 = () -> "empty name should not be in changes";
            test_34.assert_(t_15039, fn__15028);
            boolean t_15042 = cs__964.getChanges().containsKey("email");
            Supplier<String> fn__15027 = () -> "email should be in changes";
            test_34.assert_(t_15042, fn__15027);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__2123() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__966 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_15014 = SrcTest.userTable__661();
            SafeIdentifier t_15015 = SrcTest.csid__660("name");
            Changeset cs__967 = SrcGlobal.changeset(t_15014, params__966).cast(List.of(t_15015)).validateRequired(List.of(SrcTest.csid__660("name")));
            boolean t_15019 = cs__967.isValid();
            Supplier<String> fn__15011 = () -> "should be valid";
            test_35.assert_(t_15019, fn__15011);
            boolean t_15025 = cs__967.getErrors().size() == 0;
            Supplier<String> fn__15010 = () -> "no errors expected";
            test_35.assert_(t_15025, fn__15010);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__2124() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__969 = Core.mapConstructor(List.of());
            TableDef t_14990 = SrcTest.userTable__661();
            SafeIdentifier t_14991 = SrcTest.csid__660("name");
            Changeset cs__970 = SrcGlobal.changeset(t_14990, params__969).cast(List.of(t_14991)).validateRequired(List.of(SrcTest.csid__660("name")));
            boolean t_14997 = !cs__970.isValid();
            Supplier<String> fn__14988 = () -> "should be invalid";
            test_36.assert_(t_14997, fn__14988);
            boolean t_15002 = cs__970.getErrors().size() == 1;
            Supplier<String> fn__14987 = () -> "should have one error";
            test_36.assert_(t_15002, fn__14987);
            boolean t_15008 = Core.listGet(cs__970.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__14986 = () -> "error should name the field";
            test_36.assert_(t_15008, fn__14986);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__2125() {
        Test test_37 = new Test();
        try {
            Map<String, String> params__972 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14978 = SrcTest.userTable__661();
            SafeIdentifier t_14979 = SrcTest.csid__660("name");
            Changeset cs__973 = SrcGlobal.changeset(t_14978, params__972).cast(List.of(t_14979)).validateLength(SrcTest.csid__660("name"), 2, 50);
            boolean t_14983 = cs__973.isValid();
            Supplier<String> fn__14975 = () -> "should be valid";
            test_37.assert_(t_14983, fn__14975);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__2126() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__975 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_14966 = SrcTest.userTable__661();
            SafeIdentifier t_14967 = SrcTest.csid__660("name");
            Changeset cs__976 = SrcGlobal.changeset(t_14966, params__975).cast(List.of(t_14967)).validateLength(SrcTest.csid__660("name"), 2, 50);
            boolean t_14973 = !cs__976.isValid();
            Supplier<String> fn__14963 = () -> "should be invalid";
            test_38.assert_(t_14973, fn__14963);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__2127() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__978 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_14954 = SrcTest.userTable__661();
            SafeIdentifier t_14955 = SrcTest.csid__660("name");
            Changeset cs__979 = SrcGlobal.changeset(t_14954, params__978).cast(List.of(t_14955)).validateLength(SrcTest.csid__660("name"), 2, 10);
            boolean t_14961 = !cs__979.isValid();
            Supplier<String> fn__14951 = () -> "should be invalid";
            test_39.assert_(t_14961, fn__14951);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__2128() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__981 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_14943 = SrcTest.userTable__661();
            SafeIdentifier t_14944 = SrcTest.csid__660("age");
            Changeset cs__982 = SrcGlobal.changeset(t_14943, params__981).cast(List.of(t_14944)).validateInt(SrcTest.csid__660("age"));
            boolean t_14948 = cs__982.isValid();
            Supplier<String> fn__14940 = () -> "should be valid";
            test_40.assert_(t_14948, fn__14940);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__2129() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__984 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_14931 = SrcTest.userTable__661();
            SafeIdentifier t_14932 = SrcTest.csid__660("age");
            Changeset cs__985 = SrcGlobal.changeset(t_14931, params__984).cast(List.of(t_14932)).validateInt(SrcTest.csid__660("age"));
            boolean t_14938 = !cs__985.isValid();
            Supplier<String> fn__14928 = () -> "should be invalid";
            test_41.assert_(t_14938, fn__14928);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__2130() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__987 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_14920 = SrcTest.userTable__661();
            SafeIdentifier t_14921 = SrcTest.csid__660("score");
            Changeset cs__988 = SrcGlobal.changeset(t_14920, params__987).cast(List.of(t_14921)).validateFloat(SrcTest.csid__660("score"));
            boolean t_14925 = cs__988.isValid();
            Supplier<String> fn__14917 = () -> "should be valid";
            test_42.assert_(t_14925, fn__14917);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__2131() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__990 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_14909 = SrcTest.userTable__661();
            SafeIdentifier t_14910 = SrcTest.csid__660("age");
            Changeset cs__991 = SrcGlobal.changeset(t_14909, params__990).cast(List.of(t_14910)).validateInt64(SrcTest.csid__660("age"));
            boolean t_14914 = cs__991.isValid();
            Supplier<String> fn__14906 = () -> "should be valid";
            test_43.assert_(t_14914, fn__14906);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__2132() {
        Test test_44 = new Test();
        try {
            Map<String, String> params__993 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_14897 = SrcTest.userTable__661();
            SafeIdentifier t_14898 = SrcTest.csid__660("age");
            Changeset cs__994 = SrcGlobal.changeset(t_14897, params__993).cast(List.of(t_14898)).validateInt64(SrcTest.csid__660("age"));
            boolean t_14904 = !cs__994.isValid();
            Supplier<String> fn__14894 = () -> "should be invalid";
            test_44.assert_(t_14904, fn__14894);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__2133() {
        Test test_45 = new Test();
        try {
            Consumer<String> fn__14891 = v__996 -> {
                Map<String, String> params__997 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__996)));
                TableDef t_14883 = SrcTest.userTable__661();
                SafeIdentifier t_14884 = SrcTest.csid__660("active");
                Changeset cs__998 = SrcGlobal.changeset(t_14883, params__997).cast(List.of(t_14884)).validateBool(SrcTest.csid__660("active"));
                boolean t_14888 = cs__998.isValid();
                Supplier<String> fn__14880 = () -> "should accept: " + v__996;
                test_45.assert_(t_14888, fn__14880);
            };
            List.of("true", "1", "yes", "on").forEach(fn__14891);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__2134() {
        Test test_46 = new Test();
        try {
            Consumer<String> fn__14877 = v__1000 -> {
                Map<String, String> params__1001 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1000)));
                TableDef t_14869 = SrcTest.userTable__661();
                SafeIdentifier t_14870 = SrcTest.csid__660("active");
                Changeset cs__1002 = SrcGlobal.changeset(t_14869, params__1001).cast(List.of(t_14870)).validateBool(SrcTest.csid__660("active"));
                boolean t_14874 = cs__1002.isValid();
                Supplier<String> fn__14866 = () -> "should accept: " + v__1000;
                test_46.assert_(t_14874, fn__14866);
            };
            List.of("false", "0", "no", "off").forEach(fn__14877);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__2135() {
        Test test_47 = new Test();
        try {
            Consumer<String> fn__14863 = v__1004 -> {
                Map<String, String> params__1005 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1004)));
                TableDef t_14854 = SrcTest.userTable__661();
                SafeIdentifier t_14855 = SrcTest.csid__660("active");
                Changeset cs__1006 = SrcGlobal.changeset(t_14854, params__1005).cast(List.of(t_14855)).validateBool(SrcTest.csid__660("active"));
                boolean t_14861 = !cs__1006.isValid();
                Supplier<String> fn__14851 = () -> "should reject ambiguous: " + v__1004;
                test_47.assert_(t_14861, fn__14851);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__14863);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__2136() {
        Test test_48 = new Test();
        try {
            Map<String, String> params__1008 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_14839 = SrcTest.userTable__661();
            SafeIdentifier t_14840 = SrcTest.csid__660("name");
            SafeIdentifier t_14841 = SrcTest.csid__660("email");
            Changeset cs__1009 = SrcGlobal.changeset(t_14839, params__1008).cast(List.of(t_14840, t_14841)).validateRequired(List.of(SrcTest.csid__660("name"), SrcTest.csid__660("email")));
            SqlFragment t_8155;
            t_8155 = cs__1009.toInsertSql();
            SqlFragment sqlFrag__1010 = t_8155;
            String s__1011 = sqlFrag__1010.toString();
            boolean t_14848 = s__1011.indexOf("''") >= 0;
            Supplier<String> fn__14835 = () -> "single quote must be doubled: " + s__1011;
            test_48.assert_(t_14848, fn__14835);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__2137() {
        Test test_49 = new Test();
        try {
            Map<String, String> params__1013 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_14819 = SrcTest.userTable__661();
            SafeIdentifier t_14820 = SrcTest.csid__660("name");
            SafeIdentifier t_14821 = SrcTest.csid__660("email");
            Changeset cs__1014 = SrcGlobal.changeset(t_14819, params__1013).cast(List.of(t_14820, t_14821)).validateRequired(List.of(SrcTest.csid__660("name"), SrcTest.csid__660("email")));
            SqlFragment t_8134;
            t_8134 = cs__1014.toInsertSql();
            SqlFragment sqlFrag__1015 = t_8134;
            String s__1016 = sqlFrag__1015.toString();
            boolean t_14828 = s__1016.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__14815 = () -> "has INSERT INTO: " + s__1016;
            test_49.assert_(t_14828, fn__14815);
            boolean t_14832 = s__1016.indexOf("'Alice'") >= 0;
            Supplier<String> fn__14814 = () -> "has quoted name: " + s__1016;
            test_49.assert_(t_14832, fn__14814);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__2138() {
        Test test_50 = new Test();
        try {
            Map<String, String> params__1018 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_14801 = SrcTest.userTable__661();
            SafeIdentifier t_14802 = SrcTest.csid__660("name");
            SafeIdentifier t_14803 = SrcTest.csid__660("email");
            SafeIdentifier t_14804 = SrcTest.csid__660("age");
            Changeset cs__1019 = SrcGlobal.changeset(t_14801, params__1018).cast(List.of(t_14802, t_14803, t_14804)).validateRequired(List.of(SrcTest.csid__660("name"), SrcTest.csid__660("email")));
            SqlFragment t_8117;
            t_8117 = cs__1019.toInsertSql();
            SqlFragment sqlFrag__1020 = t_8117;
            String s__1021 = sqlFrag__1020.toString();
            boolean t_14811 = s__1021.indexOf("25") >= 0;
            Supplier<String> fn__14796 = () -> "age rendered unquoted: " + s__1021;
            test_50.assert_(t_14811, fn__14796);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__2139() {
        Test test_51 = new Test();
        try {
            Map<String, String> params__1023 = Core.mapConstructor(List.of());
            TableDef t_14789 = SrcTest.userTable__661();
            SafeIdentifier t_14790 = SrcTest.csid__660("name");
            Changeset cs__1024 = SrcGlobal.changeset(t_14789, params__1023).cast(List.of(t_14790)).validateRequired(List.of(SrcTest.csid__660("name")));
            boolean didBubble__1025;
            boolean didBubble_15602;
            try {
                cs__1024.toInsertSql();
                didBubble_15602 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_15602 = true;
            }
            didBubble__1025 = didBubble_15602;
            Supplier<String> fn__14787 = () -> "invalid changeset should bubble";
            test_51.assert_(didBubble__1025, fn__14787);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__2140() {
        Test test_52 = new Test();
        try {
            TableDef strictTable__1027 = new TableDef(SrcTest.csid__660("posts"), List.of(new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("body"), new StringField(), true, null, false)), null);
            Map<String, String> params__1028 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_14780 = SrcTest.csid__660("body");
            Changeset cs__1029 = SrcGlobal.changeset(strictTable__1027, params__1028).cast(List.of(t_14780));
            boolean t_14782 = cs__1029.isValid();
            Supplier<String> fn__14769 = () -> "changeset should appear valid (no explicit validation run)";
            test_52.assert_(t_14782, fn__14769);
            boolean didBubble__1030;
            boolean didBubble_15603;
            try {
                cs__1029.toInsertSql();
                didBubble_15603 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_15603 = true;
            }
            didBubble__1030 = didBubble_15603;
            Supplier<String> fn__14768 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_52.assert_(didBubble__1030, fn__14768);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__2141() {
        Test test_53 = new Test();
        try {
            Map<String, String> params__1032 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_14759 = SrcTest.userTable__661();
            SafeIdentifier t_14760 = SrcTest.csid__660("name");
            Changeset cs__1033 = SrcGlobal.changeset(t_14759, params__1032).cast(List.of(t_14760)).validateRequired(List.of(SrcTest.csid__660("name")));
            SqlFragment t_8077;
            t_8077 = cs__1033.toUpdateSql(42);
            SqlFragment sqlFrag__1034 = t_8077;
            String s__1035 = sqlFrag__1034.toString();
            boolean t_14766 = s__1035.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__14756 = () -> "got: " + s__1035;
            test_53.assert_(t_14766, fn__14756);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__2142() {
        Test test_54 = new Test();
        try {
            Map<String, String> params__1037 = Core.mapConstructor(List.of());
            TableDef t_14749 = SrcTest.userTable__661();
            SafeIdentifier t_14750 = SrcTest.csid__660("name");
            Changeset cs__1038 = SrcGlobal.changeset(t_14749, params__1037).cast(List.of(t_14750)).validateRequired(List.of(SrcTest.csid__660("name")));
            boolean didBubble__1039;
            boolean didBubble_15604;
            try {
                cs__1038.toUpdateSql(1);
                didBubble_15604 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_15604 = true;
            }
            didBubble__1039 = didBubble_15604;
            Supplier<String> fn__14747 = () -> "invalid changeset should bubble";
            test_54.assert_(didBubble__1039, fn__14747);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeAddsANewField__2143() {
        Test test_55 = new Test();
        try {
            Map<String, String> params__1041 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14733 = SrcTest.userTable__661();
            SafeIdentifier t_14734 = SrcTest.csid__660("name");
            Changeset cs__1042 = SrcGlobal.changeset(t_14733, params__1041).cast(List.of(t_14734)).putChange(SrcTest.csid__660("email"), "alice@example.com");
            boolean t_14739 = cs__1042.getChanges().containsKey("email");
            Supplier<String> fn__14730 = () -> "email should be in changes";
            test_55.assert_(t_14739, fn__14730);
            boolean t_14745 = cs__1042.getChanges().getOrDefault("email", "").equals("alice@example.com");
            Supplier<String> fn__14729 = () -> "email value";
            test_55.assert_(t_14745, fn__14729);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeOverwritesExistingField__2144() {
        Test test_56 = new Test();
        try {
            Map<String, String> params__1044 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14719 = SrcTest.userTable__661();
            SafeIdentifier t_14720 = SrcTest.csid__660("name");
            Changeset cs__1045 = SrcGlobal.changeset(t_14719, params__1044).cast(List.of(t_14720)).putChange(SrcTest.csid__660("name"), "Bob");
            boolean t_14727 = cs__1045.getChanges().getOrDefault("name", "").equals("Bob");
            Supplier<String> fn__14716 = () -> "name should be overwritten";
            test_56.assert_(t_14727, fn__14716);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void putChangeValueAppearsInToInsertSql__2145() {
        Test test_57 = new Test();
        try {
            Map<String, String> params__1047 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_14705 = SrcTest.userTable__661();
            SafeIdentifier t_14706 = SrcTest.csid__660("name");
            SafeIdentifier t_14707 = SrcTest.csid__660("email");
            Changeset cs__1048 = SrcGlobal.changeset(t_14705, params__1047).cast(List.of(t_14706, t_14707)).putChange(SrcTest.csid__660("name"), "Bob");
            SqlFragment t_8032;
            t_8032 = cs__1048.toInsertSql();
            SqlFragment t_8033 = t_8032;
            String s__1049 = t_8033.toString();
            boolean t_14713 = s__1049.indexOf("'Bob'") >= 0;
            Supplier<String> fn__14701 = () -> "should use putChange value: " + s__1049;
            test_57.assert_(t_14713, fn__14701);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeReturnsValueForExistingField__2146() {
        Test test_58 = new Test();
        try {
            Map<String, String> params__1051 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14694 = SrcTest.userTable__661();
            SafeIdentifier t_14695 = SrcTest.csid__660("name");
            Changeset cs__1052 = SrcGlobal.changeset(t_14694, params__1051).cast(List.of(t_14695));
            String t_8020;
            t_8020 = cs__1052.getChange(SrcTest.csid__660("name"));
            String val__1053 = t_8020;
            boolean t_14699 = val__1053.equals("Alice");
            Supplier<String> fn__14691 = () -> "should return Alice";
            test_58.assert_(t_14699, fn__14691);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void getChangeBubblesOnMissingField__2147() {
        Test test_59 = new Test();
        try {
            Map<String, String> params__1055 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14685 = SrcTest.userTable__661();
            SafeIdentifier t_14686 = SrcTest.csid__660("name");
            Changeset cs__1056 = SrcGlobal.changeset(t_14685, params__1055).cast(List.of(t_14686));
            boolean didBubble__1057;
            boolean didBubble_15605;
            try {
                cs__1056.getChange(SrcTest.csid__660("email"));
                didBubble_15605 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_15605 = true;
            }
            didBubble__1057 = didBubble_15605;
            Supplier<String> fn__14682 = () -> "should bubble for missing field";
            test_59.assert_(didBubble__1057, fn__14682);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeRemovesField__2148() {
        Test test_60 = new Test();
        try {
            Map<String, String> params__1059 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_14667 = SrcTest.userTable__661();
            SafeIdentifier t_14668 = SrcTest.csid__660("name");
            SafeIdentifier t_14669 = SrcTest.csid__660("email");
            Changeset cs__1060 = SrcGlobal.changeset(t_14667, params__1059).cast(List.of(t_14668, t_14669)).deleteChange(SrcTest.csid__660("email"));
            boolean t_14676 = !cs__1060.getChanges().containsKey("email");
            Supplier<String> fn__14663 = () -> "email should be removed";
            test_60.assert_(t_14676, fn__14663);
            boolean t_14679 = cs__1060.getChanges().containsKey("name");
            Supplier<String> fn__14662 = () -> "name should remain";
            test_60.assert_(t_14679, fn__14662);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteChangeOnNonexistentFieldIsNoOp__2149() {
        Test test_61 = new Test();
        try {
            Map<String, String> params__1062 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14650 = SrcTest.userTable__661();
            SafeIdentifier t_14651 = SrcTest.csid__660("name");
            Changeset cs__1063 = SrcGlobal.changeset(t_14650, params__1062).cast(List.of(t_14651)).deleteChange(SrcTest.csid__660("email"));
            boolean t_14656 = cs__1063.getChanges().containsKey("name");
            Supplier<String> fn__14647 = () -> "name should still be present";
            test_61.assert_(t_14656, fn__14647);
            boolean t_14659 = cs__1063.isValid();
            Supplier<String> fn__14646 = () -> "should still be valid";
            test_61.assert_(t_14659, fn__14646);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionPassesWhenValueInList__2150() {
        Test test_62 = new Test();
        try {
            Map<String, String> params__1065 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_14638 = SrcTest.userTable__661();
            SafeIdentifier t_14639 = SrcTest.csid__660("name");
            Changeset cs__1066 = SrcGlobal.changeset(t_14638, params__1065).cast(List.of(t_14639)).validateInclusion(SrcTest.csid__660("name"), List.of("admin", "user", "guest"));
            boolean t_14643 = cs__1066.isValid();
            Supplier<String> fn__14635 = () -> "should be valid";
            test_62.assert_(t_14643, fn__14635);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionFailsWhenValueNotInList__2151() {
        Test test_63 = new Test();
        try {
            Map<String, String> params__1068 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "hacker")));
            TableDef t_14620 = SrcTest.userTable__661();
            SafeIdentifier t_14621 = SrcTest.csid__660("name");
            Changeset cs__1069 = SrcGlobal.changeset(t_14620, params__1068).cast(List.of(t_14621)).validateInclusion(SrcTest.csid__660("name"), List.of("admin", "user", "guest"));
            boolean t_14627 = !cs__1069.isValid();
            Supplier<String> fn__14617 = () -> "should be invalid";
            test_63.assert_(t_14627, fn__14617);
            boolean t_14633 = Core.listGet(cs__1069.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__14616 = () -> "error on name";
            test_63.assert_(t_14633, fn__14616);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInclusionSkipsWhenFieldNotInChanges__2152() {
        Test test_64 = new Test();
        try {
            Map<String, String> params__1071 = Core.mapConstructor(List.of());
            TableDef t_14608 = SrcTest.userTable__661();
            SafeIdentifier t_14609 = SrcTest.csid__660("name");
            Changeset cs__1072 = SrcGlobal.changeset(t_14608, params__1071).cast(List.of(t_14609)).validateInclusion(SrcTest.csid__660("name"), List.of("admin", "user"));
            boolean t_14613 = cs__1072.isValid();
            Supplier<String> fn__14606 = () -> "should be valid when field absent";
            test_64.assert_(t_14613, fn__14606);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionPassesWhenValueNotInList__2153() {
        Test test_65 = new Test();
        try {
            Map<String, String> params__1074 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_14598 = SrcTest.userTable__661();
            SafeIdentifier t_14599 = SrcTest.csid__660("name");
            Changeset cs__1075 = SrcGlobal.changeset(t_14598, params__1074).cast(List.of(t_14599)).validateExclusion(SrcTest.csid__660("name"), List.of("root", "admin", "superuser"));
            boolean t_14603 = cs__1075.isValid();
            Supplier<String> fn__14595 = () -> "should be valid";
            test_65.assert_(t_14603, fn__14595);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionFailsWhenValueInList__2154() {
        Test test_66 = new Test();
        try {
            Map<String, String> params__1077 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "admin")));
            TableDef t_14580 = SrcTest.userTable__661();
            SafeIdentifier t_14581 = SrcTest.csid__660("name");
            Changeset cs__1078 = SrcGlobal.changeset(t_14580, params__1077).cast(List.of(t_14581)).validateExclusion(SrcTest.csid__660("name"), List.of("root", "admin", "superuser"));
            boolean t_14587 = !cs__1078.isValid();
            Supplier<String> fn__14577 = () -> "should be invalid";
            test_66.assert_(t_14587, fn__14577);
            boolean t_14593 = Core.listGet(cs__1078.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__14576 = () -> "error on name";
            test_66.assert_(t_14593, fn__14576);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateExclusionSkipsWhenFieldNotInChanges__2155() {
        Test test_67 = new Test();
        try {
            Map<String, String> params__1080 = Core.mapConstructor(List.of());
            TableDef t_14568 = SrcTest.userTable__661();
            SafeIdentifier t_14569 = SrcTest.csid__660("name");
            Changeset cs__1081 = SrcGlobal.changeset(t_14568, params__1080).cast(List.of(t_14569)).validateExclusion(SrcTest.csid__660("name"), List.of("root", "admin"));
            boolean t_14573 = cs__1081.isValid();
            Supplier<String> fn__14566 = () -> "should be valid when field absent";
            test_67.assert_(t_14573, fn__14566);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanPasses__2156() {
        Test test_68 = new Test();
        try {
            Map<String, String> params__1083 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "25")));
            TableDef t_14557 = SrcTest.userTable__661();
            SafeIdentifier t_14558 = SrcTest.csid__660("age");
            Changeset cs__1084 = SrcGlobal.changeset(t_14557, params__1083).cast(List.of(t_14558)).validateNumber(SrcTest.csid__660("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_14563 = cs__1084.isValid();
            Supplier<String> fn__14554 = () -> "25 > 18 should pass";
            test_68.assert_(t_14563, fn__14554);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanFails__2157() {
        Test test_69 = new Test();
        try {
            Map<String, String> params__1086 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "15")));
            TableDef t_14544 = SrcTest.userTable__661();
            SafeIdentifier t_14545 = SrcTest.csid__660("age");
            Changeset cs__1087 = SrcGlobal.changeset(t_14544, params__1086).cast(List.of(t_14545)).validateNumber(SrcTest.csid__660("age"), new NumberValidationOpts(18.0D, null, null, null, null));
            boolean t_14552 = !cs__1087.isValid();
            Supplier<String> fn__14541 = () -> "15 > 18 should fail";
            test_69.assert_(t_14552, fn__14541);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanPasses__2158() {
        Test test_70 = new Test();
        try {
            Map<String, String> params__1089 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "8.5")));
            TableDef t_14532 = SrcTest.userTable__661();
            SafeIdentifier t_14533 = SrcTest.csid__660("score");
            Changeset cs__1090 = SrcGlobal.changeset(t_14532, params__1089).cast(List.of(t_14533)).validateNumber(SrcTest.csid__660("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_14538 = cs__1090.isValid();
            Supplier<String> fn__14529 = () -> "8.5 < 10 should pass";
            test_70.assert_(t_14538, fn__14529);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberLessThanFails__2159() {
        Test test_71 = new Test();
        try {
            Map<String, String> params__1092 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "12.0")));
            TableDef t_14519 = SrcTest.userTable__661();
            SafeIdentifier t_14520 = SrcTest.csid__660("score");
            Changeset cs__1093 = SrcGlobal.changeset(t_14519, params__1092).cast(List.of(t_14520)).validateNumber(SrcTest.csid__660("score"), new NumberValidationOpts(null, 10.0D, null, null, null));
            boolean t_14527 = !cs__1093.isValid();
            Supplier<String> fn__14516 = () -> "12 < 10 should fail";
            test_71.assert_(t_14527, fn__14516);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberGreaterThanOrEqualBoundary__2160() {
        Test test_72 = new Test();
        try {
            Map<String, String> params__1095 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "18")));
            TableDef t_14507 = SrcTest.userTable__661();
            SafeIdentifier t_14508 = SrcTest.csid__660("age");
            Changeset cs__1096 = SrcGlobal.changeset(t_14507, params__1095).cast(List.of(t_14508)).validateNumber(SrcTest.csid__660("age"), new NumberValidationOpts(null, null, 18.0D, null, null));
            boolean t_14513 = cs__1096.isValid();
            Supplier<String> fn__14504 = () -> "18 >= 18 should pass";
            test_72.assert_(t_14513, fn__14504);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberCombinedOptions__2161() {
        Test test_73 = new Test();
        try {
            Map<String, String> params__1098 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "5.0")));
            TableDef t_14495 = SrcTest.userTable__661();
            SafeIdentifier t_14496 = SrcTest.csid__660("score");
            Changeset cs__1099 = SrcGlobal.changeset(t_14495, params__1098).cast(List.of(t_14496)).validateNumber(SrcTest.csid__660("score"), new NumberValidationOpts(0.0D, 10.0D, null, null, null));
            boolean t_14501 = cs__1099.isValid();
            Supplier<String> fn__14492 = () -> "5 > 0 and < 10 should pass";
            test_73.assert_(t_14501, fn__14492);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberNonNumericValue__2162() {
        Test test_74 = new Test();
        try {
            Map<String, String> params__1101 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "abc")));
            TableDef t_14476 = SrcTest.userTable__661();
            SafeIdentifier t_14477 = SrcTest.csid__660("age");
            Changeset cs__1102 = SrcGlobal.changeset(t_14476, params__1101).cast(List.of(t_14477)).validateNumber(SrcTest.csid__660("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_14484 = !cs__1102.isValid();
            Supplier<String> fn__14473 = () -> "non-numeric should fail";
            test_74.assert_(t_14484, fn__14473);
            boolean t_14490 = Core.listGet(cs__1102.getErrors(), 0).getMessage().equals("must be a number");
            Supplier<String> fn__14472 = () -> "correct error message";
            test_74.assert_(t_14490, fn__14472);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateNumberSkipsWhenFieldNotInChanges__2163() {
        Test test_75 = new Test();
        try {
            Map<String, String> params__1104 = Core.mapConstructor(List.of());
            TableDef t_14463 = SrcTest.userTable__661();
            SafeIdentifier t_14464 = SrcTest.csid__660("age");
            Changeset cs__1105 = SrcGlobal.changeset(t_14463, params__1104).cast(List.of(t_14464)).validateNumber(SrcTest.csid__660("age"), new NumberValidationOpts(0.0D, null, null, null, null));
            boolean t_14469 = cs__1105.isValid();
            Supplier<String> fn__14461 = () -> "should be valid when field absent";
            test_75.assert_(t_14469, fn__14461);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptancePassesForTrueValues__2164() {
        Test test_76 = new Test();
        try {
            Consumer<String> fn__14458 = v__1107 -> {
                Map<String, String> params__1108 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__1107)));
                TableDef t_14450 = SrcTest.userTable__661();
                SafeIdentifier t_14451 = SrcTest.csid__660("active");
                Changeset cs__1109 = SrcGlobal.changeset(t_14450, params__1108).cast(List.of(t_14451)).validateAcceptance(SrcTest.csid__660("active"));
                boolean t_14455 = cs__1109.isValid();
                Supplier<String> fn__14447 = () -> "should accept: " + v__1107;
                test_76.assert_(t_14455, fn__14447);
            };
            List.of("true", "1", "yes", "on").forEach(fn__14458);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateAcceptanceFailsForNonTrueValues__2165() {
        Test test_77 = new Test();
        try {
            Map<String, String> params__1111 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", "false")));
            TableDef t_14432 = SrcTest.userTable__661();
            SafeIdentifier t_14433 = SrcTest.csid__660("active");
            Changeset cs__1112 = SrcGlobal.changeset(t_14432, params__1111).cast(List.of(t_14433)).validateAcceptance(SrcTest.csid__660("active"));
            boolean t_14439 = !cs__1112.isValid();
            Supplier<String> fn__14429 = () -> "false should not be accepted";
            test_77.assert_(t_14439, fn__14429);
            boolean t_14445 = Core.listGet(cs__1112.getErrors(), 0).getMessage().equals("must be accepted");
            Supplier<String> fn__14428 = () -> "correct message";
            test_77.assert_(t_14445, fn__14428);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationPassesWhenFieldsMatch__2166() {
        Test test_78 = new Test();
        try {
            TableDef tbl__1114 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1115 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "secret123")));
            SafeIdentifier t_14419 = SrcTest.csid__660("password");
            SafeIdentifier t_14420 = SrcTest.csid__660("password_confirmation");
            Changeset cs__1116 = SrcGlobal.changeset(tbl__1114, params__1115).cast(List.of(t_14419, t_14420)).validateConfirmation(SrcTest.csid__660("password"), SrcTest.csid__660("password_confirmation"));
            boolean t_14425 = cs__1116.isValid();
            Supplier<String> fn__14407 = () -> "matching fields should pass";
            test_78.assert_(t_14425, fn__14407);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenFieldsDiffer__2167() {
        Test test_79 = new Test();
        try {
            TableDef tbl__1118 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1119 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123"), new SimpleImmutableEntry<>("password_confirmation", "wrong456")));
            SafeIdentifier t_14391 = SrcTest.csid__660("password");
            SafeIdentifier t_14392 = SrcTest.csid__660("password_confirmation");
            Changeset cs__1120 = SrcGlobal.changeset(tbl__1118, params__1119).cast(List.of(t_14391, t_14392)).validateConfirmation(SrcTest.csid__660("password"), SrcTest.csid__660("password_confirmation"));
            boolean t_14399 = !cs__1120.isValid();
            Supplier<String> fn__14379 = () -> "mismatched fields should fail";
            test_79.assert_(t_14399, fn__14379);
            boolean t_14405 = Core.listGet(cs__1120.getErrors(), 0).getField().equals("password_confirmation");
            Supplier<String> fn__14378 = () -> "error on confirmation field";
            test_79.assert_(t_14405, fn__14378);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateConfirmationFailsWhenConfirmationMissing__2168() {
        Test test_80 = new Test();
        try {
            TableDef tbl__1122 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("password"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("password_confirmation"), new StringField(), true, null, false)), null);
            Map<String, String> params__1123 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("password", "secret123")));
            SafeIdentifier t_14369 = SrcTest.csid__660("password");
            Changeset cs__1124 = SrcGlobal.changeset(tbl__1122, params__1123).cast(List.of(t_14369)).validateConfirmation(SrcTest.csid__660("password"), SrcTest.csid__660("password_confirmation"));
            boolean t_14376 = !cs__1124.isValid();
            Supplier<String> fn__14358 = () -> "missing confirmation should fail";
            test_80.assert_(t_14376, fn__14358);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsPassesWhenSubstringFound__2169() {
        Test test_81 = new Test();
        try {
            Map<String, String> params__1126 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_14350 = SrcTest.userTable__661();
            SafeIdentifier t_14351 = SrcTest.csid__660("email");
            Changeset cs__1127 = SrcGlobal.changeset(t_14350, params__1126).cast(List.of(t_14351)).validateContains(SrcTest.csid__660("email"), "@");
            boolean t_14355 = cs__1127.isValid();
            Supplier<String> fn__14347 = () -> "should pass when @ present";
            test_81.assert_(t_14355, fn__14347);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsFailsWhenSubstringNotFound__2170() {
        Test test_82 = new Test();
        try {
            Map<String, String> params__1129 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice-example.com")));
            TableDef t_14338 = SrcTest.userTable__661();
            SafeIdentifier t_14339 = SrcTest.csid__660("email");
            Changeset cs__1130 = SrcGlobal.changeset(t_14338, params__1129).cast(List.of(t_14339)).validateContains(SrcTest.csid__660("email"), "@");
            boolean t_14345 = !cs__1130.isValid();
            Supplier<String> fn__14335 = () -> "should fail when @ absent";
            test_82.assert_(t_14345, fn__14335);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateContainsSkipsWhenFieldNotInChanges__2171() {
        Test test_83 = new Test();
        try {
            Map<String, String> params__1132 = Core.mapConstructor(List.of());
            TableDef t_14327 = SrcTest.userTable__661();
            SafeIdentifier t_14328 = SrcTest.csid__660("email");
            Changeset cs__1133 = SrcGlobal.changeset(t_14327, params__1132).cast(List.of(t_14328)).validateContains(SrcTest.csid__660("email"), "@");
            boolean t_14332 = cs__1133.isValid();
            Supplier<String> fn__14325 = () -> "should be valid when field absent";
            test_83.assert_(t_14332, fn__14325);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithPasses__2172() {
        Test test_84 = new Test();
        try {
            Map<String, String> params__1135 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Dr. Smith")));
            TableDef t_14317 = SrcTest.userTable__661();
            SafeIdentifier t_14318 = SrcTest.csid__660("name");
            Changeset cs__1136 = SrcGlobal.changeset(t_14317, params__1135).cast(List.of(t_14318)).validateStartsWith(SrcTest.csid__660("name"), "Dr.");
            boolean t_14322 = cs__1136.isValid();
            Supplier<String> fn__14314 = () -> "should pass for Dr. prefix";
            test_84.assert_(t_14322, fn__14314);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateStartsWithFails__2173() {
        Test test_85 = new Test();
        try {
            Map<String, String> params__1138 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Mr. Smith")));
            TableDef t_14305 = SrcTest.userTable__661();
            SafeIdentifier t_14306 = SrcTest.csid__660("name");
            Changeset cs__1139 = SrcGlobal.changeset(t_14305, params__1138).cast(List.of(t_14306)).validateStartsWith(SrcTest.csid__660("name"), "Dr.");
            boolean t_14312 = !cs__1139.isValid();
            Supplier<String> fn__14302 = () -> "should fail for Mr. prefix";
            test_85.assert_(t_14312, fn__14302);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithPasses__2174() {
        Test test_86 = new Test();
        try {
            Map<String, String> params__1141 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_14294 = SrcTest.userTable__661();
            SafeIdentifier t_14295 = SrcTest.csid__660("email");
            Changeset cs__1142 = SrcGlobal.changeset(t_14294, params__1141).cast(List.of(t_14295)).validateEndsWith(SrcTest.csid__660("email"), ".com");
            boolean t_14299 = cs__1142.isValid();
            Supplier<String> fn__14291 = () -> "should pass for .com suffix";
            test_86.assert_(t_14299, fn__14291);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithFails__2175() {
        Test test_87 = new Test();
        try {
            Map<String, String> params__1144 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("email", "alice@example.org")));
            TableDef t_14282 = SrcTest.userTable__661();
            SafeIdentifier t_14283 = SrcTest.csid__660("email");
            Changeset cs__1145 = SrcGlobal.changeset(t_14282, params__1144).cast(List.of(t_14283)).validateEndsWith(SrcTest.csid__660("email"), ".com");
            boolean t_14289 = !cs__1145.isValid();
            Supplier<String> fn__14279 = () -> "should fail for .org when expecting .com";
            test_87.assert_(t_14289, fn__14279);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateEndsWithHandlesRepeatedSuffixCorrectly__2176() {
        Test test_88 = new Test();
        try {
            Map<String, String> params__1147 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "abcabc")));
            TableDef t_14271 = SrcTest.userTable__661();
            SafeIdentifier t_14272 = SrcTest.csid__660("name");
            Changeset cs__1148 = SrcGlobal.changeset(t_14271, params__1147).cast(List.of(t_14272)).validateEndsWith(SrcTest.csid__660("name"), "abc");
            boolean t_14276 = cs__1148.isValid();
            Supplier<String> fn__14268 = () -> "abcabc should end with abc";
            test_88.assert_(t_14276, fn__14268);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlUsesDefaultValueWhenFieldNotInChanges__2177() {
        Test test_89 = new Test();
        try {
            TableDef tbl__1150 = new TableDef(SrcTest.csid__660("posts"), List.of(new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("status"), new StringField(), false, new SqlDefault(), false)), null);
            Map<String, String> params__1151 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Hello")));
            SafeIdentifier t_14252 = SrcTest.csid__660("title");
            Changeset cs__1152 = SrcGlobal.changeset(tbl__1150, params__1151).cast(List.of(t_14252));
            SqlFragment t_7668;
            t_7668 = cs__1152.toInsertSql();
            SqlFragment t_7669 = t_7668;
            String s__1153 = t_7669.toString();
            boolean t_14256 = s__1153.indexOf("INSERT INTO posts") >= 0;
            Supplier<String> fn__14240 = () -> "has INSERT INTO: " + s__1153;
            test_89.assert_(t_14256, fn__14240);
            boolean t_14260 = s__1153.indexOf("'Hello'") >= 0;
            Supplier<String> fn__14239 = () -> "has title value: " + s__1153;
            test_89.assert_(t_14260, fn__14239);
            boolean t_14264 = s__1153.indexOf("DEFAULT") >= 0;
            Supplier<String> fn__14238 = () -> "status should use DEFAULT: " + s__1153;
            test_89.assert_(t_14264, fn__14238);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlChangeOverridesDefaultValue__2178() {
        Test test_90 = new Test();
        try {
            TableDef tbl__1155 = new TableDef(SrcTest.csid__660("posts"), List.of(new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("status"), new StringField(), false, new SqlDefault(), false)), null);
            Map<String, String> params__1156 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Hello"), new SimpleImmutableEntry<>("status", "published")));
            SafeIdentifier t_14230 = SrcTest.csid__660("title");
            SafeIdentifier t_14231 = SrcTest.csid__660("status");
            Changeset cs__1157 = SrcGlobal.changeset(tbl__1155, params__1156).cast(List.of(t_14230, t_14231));
            SqlFragment t_7648;
            t_7648 = cs__1157.toInsertSql();
            SqlFragment t_7649 = t_7648;
            String s__1158 = t_7649.toString();
            boolean t_14235 = s__1158.indexOf("'published'") >= 0;
            Supplier<String> fn__14217 = () -> "should use provided value: " + s__1158;
            test_90.assert_(t_14235, fn__14217);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlWithTimestampsUsesDefault__2179() {
        Test test_91 = new Test();
        try {
            List<FieldDef> t_7595;
            t_7595 = SrcGlobal.timestamps();
            List<FieldDef> ts__1160 = t_7595;
            List<FieldDef> fields__1161 = new ArrayList<>();
            Core.listAdd(fields__1161, new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false));
            Consumer<FieldDef> fn__14183 = t__1162 -> {
                Core.listAdd(fields__1161, t__1162);
            };
            ts__1160.forEach(fn__14183);
            TableDef tbl__1163 = new TableDef(SrcTest.csid__660("articles"), List.copyOf(fields__1161), null);
            Map<String, String> params__1164 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "News")));
            SafeIdentifier t_14196 = SrcTest.csid__660("title");
            Changeset cs__1165 = SrcGlobal.changeset(tbl__1163, params__1164).cast(List.of(t_14196));
            SqlFragment t_7610;
            t_7610 = cs__1165.toInsertSql();
            SqlFragment t_7611 = t_7610;
            String s__1166 = t_7611.toString();
            boolean t_14200 = s__1166.indexOf("inserted_at") >= 0;
            Supplier<String> fn__14182 = () -> "should include inserted_at: " + s__1166;
            test_91.assert_(t_14200, fn__14182);
            boolean t_14204 = s__1166.indexOf("updated_at") >= 0;
            Supplier<String> fn__14181 = () -> "should include updated_at: " + s__1166;
            test_91.assert_(t_14204, fn__14181);
            boolean t_14208 = s__1166.indexOf("DEFAULT") >= 0;
            Supplier<String> fn__14180 = () -> "timestamps should use DEFAULT: " + s__1166;
            test_91.assert_(t_14208, fn__14180);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlSkipsVirtualFields__2180() {
        Test test_92 = new Test();
        try {
            TableDef tbl__1168 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("full_name"), new StringField(), true, null, true)), null);
            Map<String, String> params__1169 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("full_name", "Alice Smith")));
            SafeIdentifier t_14166 = SrcTest.csid__660("name");
            SafeIdentifier t_14167 = SrcTest.csid__660("full_name");
            Changeset cs__1170 = SrcGlobal.changeset(tbl__1168, params__1169).cast(List.of(t_14166, t_14167));
            SqlFragment t_7584;
            t_7584 = cs__1170.toInsertSql();
            SqlFragment t_7585 = t_7584;
            String s__1171 = t_7585.toString();
            boolean t_14171 = s__1171.indexOf("'Alice'") >= 0;
            Supplier<String> fn__14154 = () -> "name should be included: " + s__1171;
            test_92.assert_(t_14171, fn__14154);
            boolean t_14177 = s__1171.indexOf("full_name") < 0;
            Supplier<String> fn__14153 = () -> "virtual field should be excluded: " + s__1171;
            test_92.assert_(t_14177, fn__14153);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlAllowsMissingNonNullableVirtualField__2181() {
        Test test_93 = new Test();
        try {
            TableDef tbl__1173 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("computed"), new StringField(), false, null, true)), null);
            Map<String, String> params__1174 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            SafeIdentifier t_14146 = SrcTest.csid__660("name");
            Changeset cs__1175 = SrcGlobal.changeset(tbl__1173, params__1174).cast(List.of(t_14146));
            SqlFragment t_7563;
            t_7563 = cs__1175.toInsertSql();
            SqlFragment t_7564 = t_7563;
            String s__1176 = t_7564.toString();
            boolean t_14150 = s__1176.indexOf("'Alice'") >= 0;
            Supplier<String> fn__14135 = () -> "should succeed: " + s__1176;
            test_93.assert_(t_14150, fn__14135);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlSkipsVirtualFields__2182() {
        Test test_94 = new Test();
        try {
            TableDef tbl__1178 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("name"), new StringField(), false, null, false), new FieldDef(SrcTest.csid__660("display"), new StringField(), true, null, true)), null);
            Map<String, String> params__1179 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("display", "Bobby")));
            SafeIdentifier t_14122 = SrcTest.csid__660("name");
            SafeIdentifier t_14123 = SrcTest.csid__660("display");
            Changeset cs__1180 = SrcGlobal.changeset(tbl__1178, params__1179).cast(List.of(t_14122, t_14123));
            SqlFragment t_7540;
            t_7540 = cs__1180.toUpdateSql(1);
            SqlFragment t_7541 = t_7540;
            String s__1181 = t_7541.toString();
            boolean t_14127 = s__1181.indexOf("name = 'Bob'") >= 0;
            Supplier<String> fn__14110 = () -> "name should be in SET: " + s__1181;
            test_94.assert_(t_14127, fn__14110);
            boolean t_14133 = s__1181.indexOf("display") < 0;
            Supplier<String> fn__14109 = () -> "virtual field excluded from UPDATE: " + s__1181;
            test_94.assert_(t_14133, fn__14109);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlUsesCustomPrimaryKey__2183() {
        Test test_95 = new Test();
        try {
            TableDef tbl__1183 = new TableDef(SrcTest.csid__660("posts"), List.of(new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false)), SrcTest.csid__660("post_id"));
            Map<String, String> params__1184 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("title", "Updated")));
            SafeIdentifier t_14103 = SrcTest.csid__660("title");
            Changeset cs__1185 = SrcGlobal.changeset(tbl__1183, params__1184).cast(List.of(t_14103));
            SqlFragment t_7522;
            t_7522 = cs__1185.toUpdateSql(99);
            SqlFragment t_7523 = t_7522;
            String s__1186 = t_7523.toString();
            boolean t_14107 = s__1186.equals("UPDATE posts SET title = 'Updated' WHERE post_id = 99");
            Supplier<String> fn__14093 = () -> "got: " + s__1186;
            test_95.assert_(t_14107, fn__14093);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteSqlUsesCustomPrimaryKey__2184() {
        Test test_96 = new Test();
        try {
            TableDef tbl__1188 = new TableDef(SrcTest.csid__660("posts"), List.of(new FieldDef(SrcTest.csid__660("title"), new StringField(), false, null, false)), SrcTest.csid__660("post_id"));
            String s__1189 = SrcGlobal.deleteSql(tbl__1188, 42).toString();
            boolean t_14080 = s__1189.equals("DELETE FROM posts WHERE post_id = 42");
            Supplier<String> fn__14069 = () -> "got: " + s__1189;
            test_96.assert_(t_14080, fn__14069);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteSqlUsesDefaultIdWhenPrimaryKeyNull__2185() {
        Test test_97 = new Test();
        try {
            TableDef tbl__1191 = new TableDef(SrcTest.csid__660("users"), List.of(new FieldDef(SrcTest.csid__660("name"), new StringField(), false, null, false)), null);
            String s__1192 = SrcGlobal.deleteSql(tbl__1191, 7).toString();
            boolean t_14067 = s__1192.equals("DELETE FROM users WHERE id = 7");
            Supplier<String> fn__14058 = () -> "got: " + s__1192;
            test_97.assert_(t_14067, fn__14058);
        } finally {
            test_97.softFailToHard();
        }
    }
    static SafeIdentifier sid__662(String name__1537) {
        SafeIdentifier t_6965;
        t_6965 = SrcGlobal.safeIdentifier(name__1537);
        return t_6965;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__2267() {
        Test test_98 = new Test();
        try {
            Query q__1540 = SrcGlobal.from(SrcTest.sid__662("users"));
            boolean t_13551 = q__1540.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__13546 = () -> "bare query";
            test_98.assert_(t_13551, fn__13546);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__2268() {
        Test test_99 = new Test();
        try {
            SafeIdentifier t_13537 = SrcTest.sid__662("users");
            SafeIdentifier t_13538 = SrcTest.sid__662("id");
            SafeIdentifier t_13539 = SrcTest.sid__662("name");
            Query q__1542 = SrcGlobal.from(t_13537).select(List.of(t_13538, t_13539));
            boolean t_13544 = q__1542.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__13536 = () -> "select columns";
            test_99.assert_(t_13544, fn__13536);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__2269() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_13525 = SrcTest.sid__662("users");
            SqlBuilder t_13526 = new SqlBuilder();
            t_13526.appendSafe("age > ");
            t_13526.appendInt32(18);
            SqlFragment t_13529 = t_13526.getAccumulated();
            Query q__1544 = SrcGlobal.from(t_13525).where(t_13529);
            boolean t_13534 = q__1544.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__13524 = () -> "where int";
            test_100.assert_(t_13534, fn__13524);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__2271() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_13513 = SrcTest.sid__662("users");
            SqlBuilder t_13514 = new SqlBuilder();
            t_13514.appendSafe("active = ");
            t_13514.appendBoolean(true);
            SqlFragment t_13517 = t_13514.getAccumulated();
            Query q__1546 = SrcGlobal.from(t_13513).where(t_13517);
            boolean t_13522 = q__1546.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__13512 = () -> "where bool";
            test_101.assert_(t_13522, fn__13512);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__2273() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_13496 = SrcTest.sid__662("users");
            SqlBuilder t_13497 = new SqlBuilder();
            t_13497.appendSafe("age > ");
            t_13497.appendInt32(18);
            SqlFragment t_13500 = t_13497.getAccumulated();
            Query t_13501 = SrcGlobal.from(t_13496).where(t_13500);
            SqlBuilder t_13502 = new SqlBuilder();
            t_13502.appendSafe("active = ");
            t_13502.appendBoolean(true);
            Query q__1548 = t_13501.where(t_13502.getAccumulated());
            boolean t_13510 = q__1548.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__13495 = () -> "chained where";
            test_102.assert_(t_13510, fn__13495);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__2276() {
        Test test_103 = new Test();
        try {
            SafeIdentifier t_13487 = SrcTest.sid__662("users");
            SafeIdentifier t_13488 = SrcTest.sid__662("name");
            Query q__1550 = SrcGlobal.from(t_13487).orderBy(t_13488, true);
            boolean t_13493 = q__1550.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__13486 = () -> "order asc";
            test_103.assert_(t_13493, fn__13486);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__2277() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_13478 = SrcTest.sid__662("users");
            SafeIdentifier t_13479 = SrcTest.sid__662("created_at");
            Query q__1552 = SrcGlobal.from(t_13478).orderBy(t_13479, false);
            boolean t_13484 = q__1552.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__13477 = () -> "order desc";
            test_104.assert_(t_13484, fn__13477);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__2278() {
        Test test_105 = new Test();
        try {
            Query t_6899;
            t_6899 = SrcGlobal.from(SrcTest.sid__662("users")).limit(10);
            Query t_6900;
            t_6900 = t_6899.offset(20);
            Query q__1554 = t_6900;
            boolean t_13475 = q__1554.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__13470 = () -> "limit/offset";
            test_105.assert_(t_13475, fn__13470);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__2279() {
        Test test_106 = new Test();
        try {
            boolean didBubble__1556;
            boolean didBubble_15606;
            try {
                SrcGlobal.from(SrcTest.sid__662("users")).limit(-1);
                didBubble_15606 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_15606 = true;
            }
            didBubble__1556 = didBubble_15606;
            Supplier<String> fn__13466 = () -> "negative limit should bubble";
            test_106.assert_(didBubble__1556, fn__13466);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__2280() {
        Test test_107 = new Test();
        try {
            boolean didBubble__1558;
            boolean didBubble_15607;
            try {
                SrcGlobal.from(SrcTest.sid__662("users")).offset(-1);
                didBubble_15607 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_15607 = true;
            }
            didBubble__1558 = didBubble_15607;
            Supplier<String> fn__13462 = () -> "negative offset should bubble";
            test_107.assert_(didBubble__1558, fn__13462);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__2281() {
        Test test_108 = new Test();
        try {
            int minAge__1560 = 21;
            SafeIdentifier t_13440 = SrcTest.sid__662("users");
            SafeIdentifier t_13441 = SrcTest.sid__662("id");
            SafeIdentifier t_13442 = SrcTest.sid__662("name");
            SafeIdentifier t_13443 = SrcTest.sid__662("email");
            Query t_13444 = SrcGlobal.from(t_13440).select(List.of(t_13441, t_13442, t_13443));
            SqlBuilder t_13445 = new SqlBuilder();
            t_13445.appendSafe("age >= ");
            t_13445.appendInt32(21);
            Query t_13449 = t_13444.where(t_13445.getAccumulated());
            SqlBuilder t_13450 = new SqlBuilder();
            t_13450.appendSafe("active = ");
            t_13450.appendBoolean(true);
            Query t_6885;
            t_6885 = t_13449.where(t_13450.getAccumulated()).orderBy(SrcTest.sid__662("name"), true).limit(25);
            Query t_6886;
            t_6886 = t_6885.offset(0);
            Query q__1561 = t_6886;
            boolean t_13460 = q__1561.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__13439 = () -> "complex query";
            test_108.assert_(t_13460, fn__13439);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__2284() {
        Test test_109 = new Test();
        try {
            Query q__1563 = SrcGlobal.from(SrcTest.sid__662("users"));
            SqlFragment t_6862;
            t_6862 = q__1563.safeToSql(100);
            SqlFragment t_6863 = t_6862;
            String s__1564 = t_6863.toString();
            boolean t_13437 = s__1564.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__13433 = () -> "should have limit: " + s__1564;
            test_109.assert_(t_13437, fn__13433);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__2285() {
        Test test_110 = new Test();
        try {
            Query t_6854;
            t_6854 = SrcGlobal.from(SrcTest.sid__662("users")).limit(5);
            Query q__1566 = t_6854;
            SqlFragment t_6857;
            t_6857 = q__1566.safeToSql(100);
            SqlFragment t_6858 = t_6857;
            String s__1567 = t_6858.toString();
            boolean t_13431 = s__1567.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__13427 = () -> "explicit limit preserved: " + s__1567;
            test_110.assert_(t_13431, fn__13427);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__2286() {
        Test test_111 = new Test();
        try {
            boolean didBubble__1569;
            boolean didBubble_15608;
            try {
                SrcGlobal.from(SrcTest.sid__662("users")).safeToSql(-1);
                didBubble_15608 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_15608 = true;
            }
            didBubble__1569 = didBubble_15608;
            Supplier<String> fn__13423 = () -> "negative defaultLimit should bubble";
            test_111.assert_(didBubble__1569, fn__13423);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__2287() {
        Test test_112 = new Test();
        try {
            String evil__1571 = "'; DROP TABLE users; --";
            SafeIdentifier t_13407 = SrcTest.sid__662("users");
            SqlBuilder t_13408 = new SqlBuilder();
            t_13408.appendSafe("name = ");
            t_13408.appendString("'; DROP TABLE users; --");
            SqlFragment t_13411 = t_13408.getAccumulated();
            Query q__1572 = SrcGlobal.from(t_13407).where(t_13411);
            String s__1573 = q__1572.toSql().toString();
            boolean t_13416 = s__1573.indexOf("''") >= 0;
            Supplier<String> fn__13406 = () -> "quotes must be doubled: " + s__1573;
            test_112.assert_(t_13416, fn__13406);
            boolean t_13420 = s__1573.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__13405 = () -> "structure intact: " + s__1573;
            test_112.assert_(t_13420, fn__13405);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__2289() {
        Test test_113 = new Test();
        try {
            String attack__1575 = "users; DROP TABLE users; --";
            boolean didBubble__1576;
            boolean didBubble_15609;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_15609 = false;
            } catch (RuntimeException ignored$13) {
                didBubble_15609 = true;
            }
            didBubble__1576 = didBubble_15609;
            Supplier<String> fn__13402 = () -> "metacharacter-containing name must be rejected at construction";
            test_113.assert_(didBubble__1576, fn__13402);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__2290() {
        Test test_114 = new Test();
        try {
            SafeIdentifier t_13391 = SrcTest.sid__662("users");
            SafeIdentifier t_13392 = SrcTest.sid__662("orders");
            SqlBuilder t_13393 = new SqlBuilder();
            t_13393.appendSafe("users.id = orders.user_id");
            SqlFragment t_13395 = t_13393.getAccumulated();
            Query q__1578 = SrcGlobal.from(t_13391).innerJoin(t_13392, t_13395);
            boolean t_13400 = q__1578.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__13390 = () -> "inner join";
            test_114.assert_(t_13400, fn__13390);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__2292() {
        Test test_115 = new Test();
        try {
            SafeIdentifier t_13379 = SrcTest.sid__662("users");
            SafeIdentifier t_13380 = SrcTest.sid__662("profiles");
            SqlBuilder t_13381 = new SqlBuilder();
            t_13381.appendSafe("users.id = profiles.user_id");
            SqlFragment t_13383 = t_13381.getAccumulated();
            Query q__1580 = SrcGlobal.from(t_13379).leftJoin(t_13380, t_13383);
            boolean t_13388 = q__1580.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__13378 = () -> "left join";
            test_115.assert_(t_13388, fn__13378);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__2294() {
        Test test_116 = new Test();
        try {
            SafeIdentifier t_13367 = SrcTest.sid__662("orders");
            SafeIdentifier t_13368 = SrcTest.sid__662("users");
            SqlBuilder t_13369 = new SqlBuilder();
            t_13369.appendSafe("orders.user_id = users.id");
            SqlFragment t_13371 = t_13369.getAccumulated();
            Query q__1582 = SrcGlobal.from(t_13367).rightJoin(t_13368, t_13371);
            boolean t_13376 = q__1582.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__13366 = () -> "right join";
            test_116.assert_(t_13376, fn__13366);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__2296() {
        Test test_117 = new Test();
        try {
            SafeIdentifier t_13355 = SrcTest.sid__662("users");
            SafeIdentifier t_13356 = SrcTest.sid__662("orders");
            SqlBuilder t_13357 = new SqlBuilder();
            t_13357.appendSafe("users.id = orders.user_id");
            SqlFragment t_13359 = t_13357.getAccumulated();
            Query q__1584 = SrcGlobal.from(t_13355).fullJoin(t_13356, t_13359);
            boolean t_13364 = q__1584.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__13354 = () -> "full join";
            test_117.assert_(t_13364, fn__13354);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__2298() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_13338 = SrcTest.sid__662("users");
            SafeIdentifier t_13339 = SrcTest.sid__662("orders");
            SqlBuilder t_13340 = new SqlBuilder();
            t_13340.appendSafe("users.id = orders.user_id");
            SqlFragment t_13342 = t_13340.getAccumulated();
            Query t_13343 = SrcGlobal.from(t_13338).innerJoin(t_13339, t_13342);
            SafeIdentifier t_13344 = SrcTest.sid__662("profiles");
            SqlBuilder t_13345 = new SqlBuilder();
            t_13345.appendSafe("users.id = profiles.user_id");
            Query q__1586 = t_13343.leftJoin(t_13344, t_13345.getAccumulated());
            boolean t_13352 = q__1586.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__13337 = () -> "chained joins";
            test_118.assert_(t_13352, fn__13337);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__2301() {
        Test test_119 = new Test();
        try {
            SafeIdentifier t_13319 = SrcTest.sid__662("users");
            SafeIdentifier t_13320 = SrcTest.sid__662("orders");
            SqlBuilder t_13321 = new SqlBuilder();
            t_13321.appendSafe("users.id = orders.user_id");
            SqlFragment t_13323 = t_13321.getAccumulated();
            Query t_13324 = SrcGlobal.from(t_13319).innerJoin(t_13320, t_13323);
            SqlBuilder t_13325 = new SqlBuilder();
            t_13325.appendSafe("orders.total > ");
            t_13325.appendInt32(100);
            Query t_6769;
            t_6769 = t_13324.where(t_13325.getAccumulated()).orderBy(SrcTest.sid__662("name"), true).limit(10);
            Query q__1588 = t_6769;
            boolean t_13335 = q__1588.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__13318 = () -> "join with where/order/limit";
            test_119.assert_(t_13335, fn__13318);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__2304() {
        Test test_120 = new Test();
        try {
            SqlFragment c__1590 = SrcGlobal.col(SrcTest.sid__662("users"), SrcTest.sid__662("id"));
            boolean t_13316 = c__1590.toString().equals("users.id");
            Supplier<String> fn__13310 = () -> "col helper";
            test_120.assert_(t_13316, fn__13310);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__2305() {
        Test test_121 = new Test();
        try {
            SqlFragment onCond__1592 = SrcGlobal.col(SrcTest.sid__662("users"), SrcTest.sid__662("id"));
            SqlBuilder b__1593 = new SqlBuilder();
            b__1593.appendFragment(onCond__1592);
            b__1593.appendSafe(" = ");
            b__1593.appendFragment(SrcGlobal.col(SrcTest.sid__662("orders"), SrcTest.sid__662("user_id")));
            SafeIdentifier t_13301 = SrcTest.sid__662("users");
            SafeIdentifier t_13302 = SrcTest.sid__662("orders");
            SqlFragment t_13303 = b__1593.getAccumulated();
            Query q__1594 = SrcGlobal.from(t_13301).innerJoin(t_13302, t_13303);
            boolean t_13308 = q__1594.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__13290 = () -> "join with col";
            test_121.assert_(t_13308, fn__13290);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__2306() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_13279 = SrcTest.sid__662("users");
            SqlBuilder t_13280 = new SqlBuilder();
            t_13280.appendSafe("status = ");
            t_13280.appendString("active");
            SqlFragment t_13283 = t_13280.getAccumulated();
            Query q__1596 = SrcGlobal.from(t_13279).orWhere(t_13283);
            boolean t_13288 = q__1596.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__13278 = () -> "orWhere basic";
            test_122.assert_(t_13288, fn__13278);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__2308() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_13262 = SrcTest.sid__662("users");
            SqlBuilder t_13263 = new SqlBuilder();
            t_13263.appendSafe("age > ");
            t_13263.appendInt32(18);
            SqlFragment t_13266 = t_13263.getAccumulated();
            Query t_13267 = SrcGlobal.from(t_13262).where(t_13266);
            SqlBuilder t_13268 = new SqlBuilder();
            t_13268.appendSafe("vip = ");
            t_13268.appendBoolean(true);
            Query q__1598 = t_13267.orWhere(t_13268.getAccumulated());
            boolean t_13276 = q__1598.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__13261 = () -> "where then orWhere";
            test_123.assert_(t_13276, fn__13261);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__2311() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_13240 = SrcTest.sid__662("users");
            SqlBuilder t_13241 = new SqlBuilder();
            t_13241.appendSafe("active = ");
            t_13241.appendBoolean(true);
            SqlFragment t_13244 = t_13241.getAccumulated();
            Query t_13245 = SrcGlobal.from(t_13240).where(t_13244);
            SqlBuilder t_13246 = new SqlBuilder();
            t_13246.appendSafe("role = ");
            t_13246.appendString("admin");
            Query t_13250 = t_13245.orWhere(t_13246.getAccumulated());
            SqlBuilder t_13251 = new SqlBuilder();
            t_13251.appendSafe("role = ");
            t_13251.appendString("moderator");
            Query q__1600 = t_13250.orWhere(t_13251.getAccumulated());
            boolean t_13259 = q__1600.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__13239 = () -> "multiple orWhere";
            test_124.assert_(t_13259, fn__13239);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__2315() {
        Test test_125 = new Test();
        try {
            SafeIdentifier t_13218 = SrcTest.sid__662("users");
            SqlBuilder t_13219 = new SqlBuilder();
            t_13219.appendSafe("age > ");
            t_13219.appendInt32(18);
            SqlFragment t_13222 = t_13219.getAccumulated();
            Query t_13223 = SrcGlobal.from(t_13218).where(t_13222);
            SqlBuilder t_13224 = new SqlBuilder();
            t_13224.appendSafe("active = ");
            t_13224.appendBoolean(true);
            Query t_13228 = t_13223.where(t_13224.getAccumulated());
            SqlBuilder t_13229 = new SqlBuilder();
            t_13229.appendSafe("vip = ");
            t_13229.appendBoolean(true);
            Query q__1602 = t_13228.orWhere(t_13229.getAccumulated());
            boolean t_13237 = q__1602.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__13217 = () -> "mixed where and orWhere";
            test_125.assert_(t_13237, fn__13217);
        } finally {
            test_125.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__2319() {
        Test test_126 = new Test();
        try {
            SafeIdentifier t_13209 = SrcTest.sid__662("users");
            SafeIdentifier t_13210 = SrcTest.sid__662("deleted_at");
            Query q__1604 = SrcGlobal.from(t_13209).whereNull(t_13210);
            boolean t_13215 = q__1604.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__13208 = () -> "whereNull";
            test_126.assert_(t_13215, fn__13208);
        } finally {
            test_126.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__2320() {
        Test test_127 = new Test();
        try {
            SafeIdentifier t_13200 = SrcTest.sid__662("users");
            SafeIdentifier t_13201 = SrcTest.sid__662("email");
            Query q__1606 = SrcGlobal.from(t_13200).whereNotNull(t_13201);
            boolean t_13206 = q__1606.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__13199 = () -> "whereNotNull";
            test_127.assert_(t_13206, fn__13199);
        } finally {
            test_127.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__2321() {
        Test test_128 = new Test();
        try {
            SafeIdentifier t_13186 = SrcTest.sid__662("users");
            SqlBuilder t_13187 = new SqlBuilder();
            t_13187.appendSafe("active = ");
            t_13187.appendBoolean(true);
            SqlFragment t_13190 = t_13187.getAccumulated();
            Query q__1608 = SrcGlobal.from(t_13186).where(t_13190).whereNull(SrcTest.sid__662("deleted_at"));
            boolean t_13197 = q__1608.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__13185 = () -> "whereNull chained";
            test_128.assert_(t_13197, fn__13185);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__2323() {
        Test test_129 = new Test();
        try {
            SafeIdentifier t_13172 = SrcTest.sid__662("users");
            SafeIdentifier t_13173 = SrcTest.sid__662("deleted_at");
            Query t_13174 = SrcGlobal.from(t_13172).whereNull(t_13173);
            SqlBuilder t_13175 = new SqlBuilder();
            t_13175.appendSafe("role = ");
            t_13175.appendString("admin");
            Query q__1610 = t_13174.orWhere(t_13175.getAccumulated());
            boolean t_13183 = q__1610.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__13171 = () -> "whereNotNull with orWhere";
            test_129.assert_(t_13183, fn__13171);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__2325() {
        Test test_130 = new Test();
        try {
            SafeIdentifier t_13160 = SrcTest.sid__662("users");
            SafeIdentifier t_13161 = SrcTest.sid__662("id");
            SqlInt32 t_13162 = new SqlInt32(1);
            SqlInt32 t_13163 = new SqlInt32(2);
            SqlInt32 t_13164 = new SqlInt32(3);
            Query q__1612 = SrcGlobal.from(t_13160).whereIn(t_13161, List.of(t_13162, t_13163, t_13164));
            boolean t_13169 = q__1612.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__13159 = () -> "whereIn ints";
            test_130.assert_(t_13169, fn__13159);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__2326() {
        Test test_131 = new Test();
        try {
            SafeIdentifier t_13149 = SrcTest.sid__662("users");
            SafeIdentifier t_13150 = SrcTest.sid__662("name");
            SqlString t_13151 = new SqlString("Alice");
            SqlString t_13152 = new SqlString("Bob's");
            Query q__1614 = SrcGlobal.from(t_13149).whereIn(t_13150, List.of(t_13151, t_13152));
            boolean t_13157 = q__1614.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__13148 = () -> "whereIn strings";
            test_131.assert_(t_13157, fn__13148);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__2327() {
        Test test_132 = new Test();
        try {
            SafeIdentifier t_13140 = SrcTest.sid__662("users");
            SafeIdentifier t_13141 = SrcTest.sid__662("id");
            Query q__1616 = SrcGlobal.from(t_13140).whereIn(t_13141, List.of());
            boolean t_13146 = q__1616.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__13139 = () -> "whereIn empty";
            test_132.assert_(t_13146, fn__13139);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__2328() {
        Test test_133 = new Test();
        try {
            SafeIdentifier t_13124 = SrcTest.sid__662("users");
            SqlBuilder t_13125 = new SqlBuilder();
            t_13125.appendSafe("active = ");
            t_13125.appendBoolean(true);
            SqlFragment t_13128 = t_13125.getAccumulated();
            Query q__1618 = SrcGlobal.from(t_13124).where(t_13128).whereIn(SrcTest.sid__662("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_13137 = q__1618.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__13123 = () -> "whereIn chained";
            test_133.assert_(t_13137, fn__13123);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__2330() {
        Test test_134 = new Test();
        try {
            SafeIdentifier t_13114 = SrcTest.sid__662("users");
            SafeIdentifier t_13115 = SrcTest.sid__662("id");
            SqlInt32 t_13116 = new SqlInt32(42);
            Query q__1620 = SrcGlobal.from(t_13114).whereIn(t_13115, List.of(t_13116));
            boolean t_13121 = q__1620.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__13113 = () -> "whereIn single";
            test_134.assert_(t_13121, fn__13113);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__2331() {
        Test test_135 = new Test();
        try {
            SafeIdentifier t_13102 = SrcTest.sid__662("users");
            SqlBuilder t_13103 = new SqlBuilder();
            t_13103.appendSafe("active = ");
            t_13103.appendBoolean(true);
            SqlFragment t_13106 = t_13103.getAccumulated();
            Query q__1622 = SrcGlobal.from(t_13102).whereNot(t_13106);
            boolean t_13111 = q__1622.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__13101 = () -> "whereNot";
            test_135.assert_(t_13111, fn__13101);
        } finally {
            test_135.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__2333() {
        Test test_136 = new Test();
        try {
            SafeIdentifier t_13085 = SrcTest.sid__662("users");
            SqlBuilder t_13086 = new SqlBuilder();
            t_13086.appendSafe("age > ");
            t_13086.appendInt32(18);
            SqlFragment t_13089 = t_13086.getAccumulated();
            Query t_13090 = SrcGlobal.from(t_13085).where(t_13089);
            SqlBuilder t_13091 = new SqlBuilder();
            t_13091.appendSafe("banned = ");
            t_13091.appendBoolean(true);
            Query q__1624 = t_13090.whereNot(t_13091.getAccumulated());
            boolean t_13099 = q__1624.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__13084 = () -> "whereNot chained";
            test_136.assert_(t_13099, fn__13084);
        } finally {
            test_136.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__2336() {
        Test test_137 = new Test();
        try {
            SafeIdentifier t_13074 = SrcTest.sid__662("users");
            SafeIdentifier t_13075 = SrcTest.sid__662("age");
            SqlInt32 t_13076 = new SqlInt32(18);
            SqlInt32 t_13077 = new SqlInt32(65);
            Query q__1626 = SrcGlobal.from(t_13074).whereBetween(t_13075, t_13076, t_13077);
            boolean t_13082 = q__1626.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__13073 = () -> "whereBetween ints";
            test_137.assert_(t_13082, fn__13073);
        } finally {
            test_137.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__2337() {
        Test test_138 = new Test();
        try {
            SafeIdentifier t_13058 = SrcTest.sid__662("users");
            SqlBuilder t_13059 = new SqlBuilder();
            t_13059.appendSafe("active = ");
            t_13059.appendBoolean(true);
            SqlFragment t_13062 = t_13059.getAccumulated();
            Query q__1628 = SrcGlobal.from(t_13058).where(t_13062).whereBetween(SrcTest.sid__662("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_13071 = q__1628.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__13057 = () -> "whereBetween chained";
            test_138.assert_(t_13071, fn__13057);
        } finally {
            test_138.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__2339() {
        Test test_139 = new Test();
        try {
            SafeIdentifier t_13049 = SrcTest.sid__662("users");
            SafeIdentifier t_13050 = SrcTest.sid__662("name");
            Query q__1630 = SrcGlobal.from(t_13049).whereLike(t_13050, "John%");
            boolean t_13055 = q__1630.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__13048 = () -> "whereLike";
            test_139.assert_(t_13055, fn__13048);
        } finally {
            test_139.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__2340() {
        Test test_140 = new Test();
        try {
            SafeIdentifier t_13040 = SrcTest.sid__662("users");
            SafeIdentifier t_13041 = SrcTest.sid__662("email");
            Query q__1632 = SrcGlobal.from(t_13040).whereILike(t_13041, "%@gmail.com");
            boolean t_13046 = q__1632.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__13039 = () -> "whereILike";
            test_140.assert_(t_13046, fn__13039);
        } finally {
            test_140.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__2341() {
        Test test_141 = new Test();
        try {
            SafeIdentifier t_13026 = SrcTest.sid__662("users");
            SafeIdentifier t_13027 = SrcTest.sid__662("name");
            Query q__1634 = SrcGlobal.from(t_13026).whereLike(t_13027, "'; DROP TABLE users; --");
            String s__1635 = q__1634.toSql().toString();
            boolean t_13032 = s__1635.indexOf("''") >= 0;
            Supplier<String> fn__13025 = () -> "like injection escaped: " + s__1635;
            test_141.assert_(t_13032, fn__13025);
            boolean t_13036 = s__1635.indexOf("LIKE") >= 0;
            Supplier<String> fn__13024 = () -> "like structure intact: " + s__1635;
            test_141.assert_(t_13036, fn__13024);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__2342() {
        Test test_142 = new Test();
        try {
            SafeIdentifier t_13016 = SrcTest.sid__662("users");
            SafeIdentifier t_13017 = SrcTest.sid__662("name");
            Query q__1637 = SrcGlobal.from(t_13016).whereLike(t_13017, "%son%");
            boolean t_13022 = q__1637.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__13015 = () -> "whereLike wildcard";
            test_142.assert_(t_13022, fn__13015);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__2343() {
        Test test_143 = new Test();
        try {
            SqlFragment f__1639 = SrcGlobal.countAll();
            boolean t_13013 = f__1639.toString().equals("COUNT(*)");
            Supplier<String> fn__13009 = () -> "countAll";
            test_143.assert_(t_13013, fn__13009);
        } finally {
            test_143.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__2344() {
        Test test_144 = new Test();
        try {
            SqlFragment f__1641 = SrcGlobal.countCol(SrcTest.sid__662("id"));
            boolean t_13007 = f__1641.toString().equals("COUNT(id)");
            Supplier<String> fn__13002 = () -> "countCol";
            test_144.assert_(t_13007, fn__13002);
        } finally {
            test_144.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__2345() {
        Test test_145 = new Test();
        try {
            SqlFragment f__1643 = SrcGlobal.sumCol(SrcTest.sid__662("amount"));
            boolean t_13000 = f__1643.toString().equals("SUM(amount)");
            Supplier<String> fn__12995 = () -> "sumCol";
            test_145.assert_(t_13000, fn__12995);
        } finally {
            test_145.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__2346() {
        Test test_146 = new Test();
        try {
            SqlFragment f__1645 = SrcGlobal.avgCol(SrcTest.sid__662("price"));
            boolean t_12993 = f__1645.toString().equals("AVG(price)");
            Supplier<String> fn__12988 = () -> "avgCol";
            test_146.assert_(t_12993, fn__12988);
        } finally {
            test_146.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__2347() {
        Test test_147 = new Test();
        try {
            SqlFragment f__1647 = SrcGlobal.minCol(SrcTest.sid__662("created_at"));
            boolean t_12986 = f__1647.toString().equals("MIN(created_at)");
            Supplier<String> fn__12981 = () -> "minCol";
            test_147.assert_(t_12986, fn__12981);
        } finally {
            test_147.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__2348() {
        Test test_148 = new Test();
        try {
            SqlFragment f__1649 = SrcGlobal.maxCol(SrcTest.sid__662("score"));
            boolean t_12979 = f__1649.toString().equals("MAX(score)");
            Supplier<String> fn__12974 = () -> "maxCol";
            test_148.assert_(t_12979, fn__12974);
        } finally {
            test_148.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__2349() {
        Test test_149 = new Test();
        try {
            SafeIdentifier t_12966 = SrcTest.sid__662("orders");
            SqlFragment t_12967 = SrcGlobal.countAll();
            Query q__1651 = SrcGlobal.from(t_12966).selectExpr(List.of(t_12967));
            boolean t_12972 = q__1651.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__12965 = () -> "selectExpr count";
            test_149.assert_(t_12972, fn__12965);
        } finally {
            test_149.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__2350() {
        Test test_150 = new Test();
        try {
            SqlFragment nameFrag__1653 = SrcGlobal.col(SrcTest.sid__662("users"), SrcTest.sid__662("name"));
            SafeIdentifier t_12957 = SrcTest.sid__662("users");
            SqlFragment t_12958 = SrcGlobal.countAll();
            Query q__1654 = SrcGlobal.from(t_12957).selectExpr(List.of(nameFrag__1653, t_12958));
            boolean t_12963 = q__1654.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__12953 = () -> "selectExpr multi";
            test_150.assert_(t_12963, fn__12953);
        } finally {
            test_150.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__2351() {
        Test test_151 = new Test();
        try {
            SafeIdentifier t_12942 = SrcTest.sid__662("users");
            SafeIdentifier t_12943 = SrcTest.sid__662("id");
            SafeIdentifier t_12944 = SrcTest.sid__662("name");
            Query q__1656 = SrcGlobal.from(t_12942).select(List.of(t_12943, t_12944)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_12951 = q__1656.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__12941 = () -> "selectExpr overrides select";
            test_151.assert_(t_12951, fn__12941);
        } finally {
            test_151.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__2352() {
        Test test_152 = new Test();
        try {
            SafeIdentifier t_12928 = SrcTest.sid__662("orders");
            SqlFragment t_12931 = SrcGlobal.col(SrcTest.sid__662("orders"), SrcTest.sid__662("status"));
            SqlFragment t_12932 = SrcGlobal.countAll();
            Query q__1658 = SrcGlobal.from(t_12928).selectExpr(List.of(t_12931, t_12932)).groupBy(SrcTest.sid__662("status"));
            boolean t_12939 = q__1658.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__12927 = () -> "groupBy single";
            test_152.assert_(t_12939, fn__12927);
        } finally {
            test_152.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__2353() {
        Test test_153 = new Test();
        try {
            SafeIdentifier t_12917 = SrcTest.sid__662("orders");
            SafeIdentifier t_12918 = SrcTest.sid__662("status");
            Query q__1660 = SrcGlobal.from(t_12917).groupBy(t_12918).groupBy(SrcTest.sid__662("category"));
            boolean t_12925 = q__1660.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__12916 = () -> "groupBy multiple";
            test_153.assert_(t_12925, fn__12916);
        } finally {
            test_153.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__2354() {
        Test test_154 = new Test();
        try {
            SafeIdentifier t_12898 = SrcTest.sid__662("orders");
            SqlFragment t_12901 = SrcGlobal.col(SrcTest.sid__662("orders"), SrcTest.sid__662("status"));
            SqlFragment t_12902 = SrcGlobal.countAll();
            Query t_12905 = SrcGlobal.from(t_12898).selectExpr(List.of(t_12901, t_12902)).groupBy(SrcTest.sid__662("status"));
            SqlBuilder t_12906 = new SqlBuilder();
            t_12906.appendSafe("COUNT(*) > ");
            t_12906.appendInt32(5);
            Query q__1662 = t_12905.having(t_12906.getAccumulated());
            boolean t_12914 = q__1662.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__12897 = () -> "having basic";
            test_154.assert_(t_12914, fn__12897);
        } finally {
            test_154.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__2356() {
        Test test_155 = new Test();
        try {
            SafeIdentifier t_12879 = SrcTest.sid__662("orders");
            SafeIdentifier t_12880 = SrcTest.sid__662("status");
            Query t_12881 = SrcGlobal.from(t_12879).groupBy(t_12880);
            SqlBuilder t_12882 = new SqlBuilder();
            t_12882.appendSafe("COUNT(*) > ");
            t_12882.appendInt32(5);
            Query t_12886 = t_12881.having(t_12882.getAccumulated());
            SqlBuilder t_12887 = new SqlBuilder();
            t_12887.appendSafe("SUM(total) > ");
            t_12887.appendInt32(1000);
            Query q__1664 = t_12886.orHaving(t_12887.getAccumulated());
            boolean t_12895 = q__1664.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__12878 = () -> "orHaving";
            test_155.assert_(t_12895, fn__12878);
        } finally {
            test_155.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__2359() {
        Test test_156 = new Test();
        try {
            SafeIdentifier t_12869 = SrcTest.sid__662("users");
            SafeIdentifier t_12870 = SrcTest.sid__662("name");
            Query q__1666 = SrcGlobal.from(t_12869).select(List.of(t_12870)).distinct();
            boolean t_12876 = q__1666.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__12868 = () -> "distinct";
            test_156.assert_(t_12876, fn__12868);
        } finally {
            test_156.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__2360() {
        Test test_157 = new Test();
        try {
            SafeIdentifier t_12854 = SrcTest.sid__662("users");
            SafeIdentifier t_12855 = SrcTest.sid__662("email");
            Query t_12856 = SrcGlobal.from(t_12854).select(List.of(t_12855));
            SqlBuilder t_12857 = new SqlBuilder();
            t_12857.appendSafe("active = ");
            t_12857.appendBoolean(true);
            Query q__1668 = t_12856.where(t_12857.getAccumulated()).distinct();
            boolean t_12866 = q__1668.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__12853 = () -> "distinct with where";
            test_157.assert_(t_12866, fn__12853);
        } finally {
            test_157.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__2362() {
        Test test_158 = new Test();
        try {
            Query q__1670 = SrcGlobal.from(SrcTest.sid__662("users"));
            boolean t_12851 = q__1670.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__12846 = () -> "countSql bare";
            test_158.assert_(t_12851, fn__12846);
        } finally {
            test_158.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__2363() {
        Test test_159 = new Test();
        try {
            SafeIdentifier t_12835 = SrcTest.sid__662("users");
            SqlBuilder t_12836 = new SqlBuilder();
            t_12836.appendSafe("active = ");
            t_12836.appendBoolean(true);
            SqlFragment t_12839 = t_12836.getAccumulated();
            Query q__1672 = SrcGlobal.from(t_12835).where(t_12839);
            boolean t_12844 = q__1672.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__12834 = () -> "countSql with where";
            test_159.assert_(t_12844, fn__12834);
        } finally {
            test_159.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__2365() {
        Test test_160 = new Test();
        try {
            SafeIdentifier t_12818 = SrcTest.sid__662("users");
            SafeIdentifier t_12819 = SrcTest.sid__662("orders");
            SqlBuilder t_12820 = new SqlBuilder();
            t_12820.appendSafe("users.id = orders.user_id");
            SqlFragment t_12822 = t_12820.getAccumulated();
            Query t_12823 = SrcGlobal.from(t_12818).innerJoin(t_12819, t_12822);
            SqlBuilder t_12824 = new SqlBuilder();
            t_12824.appendSafe("orders.total > ");
            t_12824.appendInt32(100);
            Query q__1674 = t_12823.where(t_12824.getAccumulated());
            boolean t_12832 = q__1674.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__12817 = () -> "countSql with join";
            test_160.assert_(t_12832, fn__12817);
        } finally {
            test_160.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__2368() {
        Test test_161 = new Test();
        try {
            SafeIdentifier t_12804 = SrcTest.sid__662("users");
            SqlBuilder t_12805 = new SqlBuilder();
            t_12805.appendSafe("active = ");
            t_12805.appendBoolean(true);
            SqlFragment t_12808 = t_12805.getAccumulated();
            Query t_6345;
            t_6345 = SrcGlobal.from(t_12804).where(t_12808).orderBy(SrcTest.sid__662("name"), true).limit(10);
            Query t_6346;
            t_6346 = t_6345.offset(20);
            Query q__1676 = t_6346;
            String s__1677 = q__1676.countSql().toString();
            boolean t_12815 = s__1677.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__12803 = () -> "countSql drops extras: " + s__1677;
            test_161.assert_(t_12815, fn__12803);
        } finally {
            test_161.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__2370() {
        Test test_162 = new Test();
        try {
            SafeIdentifier t_12771 = SrcTest.sid__662("orders");
            SqlFragment t_12774 = SrcGlobal.col(SrcTest.sid__662("orders"), SrcTest.sid__662("status"));
            SqlFragment t_12775 = SrcGlobal.countAll();
            SqlFragment t_12777 = SrcGlobal.sumCol(SrcTest.sid__662("total"));
            Query t_12778 = SrcGlobal.from(t_12771).selectExpr(List.of(t_12774, t_12775, t_12777));
            SafeIdentifier t_12779 = SrcTest.sid__662("users");
            SqlBuilder t_12780 = new SqlBuilder();
            t_12780.appendSafe("orders.user_id = users.id");
            Query t_12783 = t_12778.innerJoin(t_12779, t_12780.getAccumulated());
            SqlBuilder t_12784 = new SqlBuilder();
            t_12784.appendSafe("users.active = ");
            t_12784.appendBoolean(true);
            Query t_12790 = t_12783.where(t_12784.getAccumulated()).groupBy(SrcTest.sid__662("status"));
            SqlBuilder t_12791 = new SqlBuilder();
            t_12791.appendSafe("COUNT(*) > ");
            t_12791.appendInt32(3);
            Query q__1679 = t_12790.having(t_12791.getAccumulated()).orderBy(SrcTest.sid__662("status"), true);
            String expected__1680 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_12801 = q__1679.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__12770 = () -> "full aggregation";
            test_162.assert_(t_12801, fn__12770);
        } finally {
            test_162.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__2374() {
        Test test_163 = new Test();
        try {
            SafeIdentifier t_12753 = SrcTest.sid__662("users");
            SqlBuilder t_12754 = new SqlBuilder();
            t_12754.appendSafe("role = ");
            t_12754.appendString("admin");
            SqlFragment t_12757 = t_12754.getAccumulated();
            Query a__1682 = SrcGlobal.from(t_12753).where(t_12757);
            SafeIdentifier t_12759 = SrcTest.sid__662("users");
            SqlBuilder t_12760 = new SqlBuilder();
            t_12760.appendSafe("role = ");
            t_12760.appendString("moderator");
            SqlFragment t_12763 = t_12760.getAccumulated();
            Query b__1683 = SrcGlobal.from(t_12759).where(t_12763);
            String s__1684 = SrcGlobal.unionSql(a__1682, b__1683).toString();
            boolean t_12768 = s__1684.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__12752 = () -> "unionSql: " + s__1684;
            test_163.assert_(t_12768, fn__12752);
        } finally {
            test_163.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__2377() {
        Test test_164 = new Test();
        try {
            SafeIdentifier t_12741 = SrcTest.sid__662("users");
            SafeIdentifier t_12742 = SrcTest.sid__662("name");
            Query a__1686 = SrcGlobal.from(t_12741).select(List.of(t_12742));
            SafeIdentifier t_12744 = SrcTest.sid__662("contacts");
            SafeIdentifier t_12745 = SrcTest.sid__662("name");
            Query b__1687 = SrcGlobal.from(t_12744).select(List.of(t_12745));
            String s__1688 = SrcGlobal.unionAllSql(a__1686, b__1687).toString();
            boolean t_12750 = s__1688.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__12740 = () -> "unionAllSql: " + s__1688;
            test_164.assert_(t_12750, fn__12740);
        } finally {
            test_164.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__2378() {
        Test test_165 = new Test();
        try {
            SafeIdentifier t_12729 = SrcTest.sid__662("users");
            SafeIdentifier t_12730 = SrcTest.sid__662("email");
            Query a__1690 = SrcGlobal.from(t_12729).select(List.of(t_12730));
            SafeIdentifier t_12732 = SrcTest.sid__662("subscribers");
            SafeIdentifier t_12733 = SrcTest.sid__662("email");
            Query b__1691 = SrcGlobal.from(t_12732).select(List.of(t_12733));
            String s__1692 = SrcGlobal.intersectSql(a__1690, b__1691).toString();
            boolean t_12738 = s__1692.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__12728 = () -> "intersectSql: " + s__1692;
            test_165.assert_(t_12738, fn__12728);
        } finally {
            test_165.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__2379() {
        Test test_166 = new Test();
        try {
            SafeIdentifier t_12717 = SrcTest.sid__662("users");
            SafeIdentifier t_12718 = SrcTest.sid__662("id");
            Query a__1694 = SrcGlobal.from(t_12717).select(List.of(t_12718));
            SafeIdentifier t_12720 = SrcTest.sid__662("banned");
            SafeIdentifier t_12721 = SrcTest.sid__662("id");
            Query b__1695 = SrcGlobal.from(t_12720).select(List.of(t_12721));
            String s__1696 = SrcGlobal.exceptSql(a__1694, b__1695).toString();
            boolean t_12726 = s__1696.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__12716 = () -> "exceptSql: " + s__1696;
            test_166.assert_(t_12726, fn__12716);
        } finally {
            test_166.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__2380() {
        Test test_167 = new Test();
        try {
            SafeIdentifier t_12702 = SrcTest.sid__662("orders");
            SafeIdentifier t_12703 = SrcTest.sid__662("user_id");
            Query t_12704 = SrcGlobal.from(t_12702).select(List.of(t_12703));
            SqlBuilder t_12705 = new SqlBuilder();
            t_12705.appendSafe("total > ");
            t_12705.appendInt32(100);
            Query inner__1698 = t_12704.where(t_12705.getAccumulated());
            String s__1699 = SrcGlobal.subquery(inner__1698, SrcTest.sid__662("big_orders")).toString();
            boolean t_12714 = s__1699.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__12701 = () -> "subquery: " + s__1699;
            test_167.assert_(t_12714, fn__12701);
        } finally {
            test_167.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__2382() {
        Test test_168 = new Test();
        try {
            SafeIdentifier t_12691 = SrcTest.sid__662("orders");
            SqlBuilder t_12692 = new SqlBuilder();
            t_12692.appendSafe("orders.user_id = users.id");
            SqlFragment t_12694 = t_12692.getAccumulated();
            Query inner__1701 = SrcGlobal.from(t_12691).where(t_12694);
            String s__1702 = SrcGlobal.existsSql(inner__1701).toString();
            boolean t_12699 = s__1702.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__12690 = () -> "existsSql: " + s__1702;
            test_168.assert_(t_12699, fn__12690);
        } finally {
            test_168.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__2384() {
        Test test_169 = new Test();
        try {
            SafeIdentifier t_12674 = SrcTest.sid__662("orders");
            SafeIdentifier t_12675 = SrcTest.sid__662("user_id");
            Query t_12676 = SrcGlobal.from(t_12674).select(List.of(t_12675));
            SqlBuilder t_12677 = new SqlBuilder();
            t_12677.appendSafe("total > ");
            t_12677.appendInt32(1000);
            Query sub__1704 = t_12676.where(t_12677.getAccumulated());
            SafeIdentifier t_12682 = SrcTest.sid__662("users");
            SafeIdentifier t_12683 = SrcTest.sid__662("id");
            Query q__1705 = SrcGlobal.from(t_12682).whereInSubquery(t_12683, sub__1704);
            String s__1706 = q__1705.toSql().toString();
            boolean t_12688 = s__1706.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__12673 = () -> "whereInSubquery: " + s__1706;
            test_169.assert_(t_12688, fn__12673);
        } finally {
            test_169.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__2386() {
        Test test_170 = new Test();
        try {
            SafeIdentifier t_12651 = SrcTest.sid__662("users");
            SqlBuilder t_12652 = new SqlBuilder();
            t_12652.appendSafe("age > ");
            t_12652.appendInt32(18);
            SqlFragment t_12655 = t_12652.getAccumulated();
            Query t_12656 = SrcGlobal.from(t_12651).where(t_12655);
            SqlBuilder t_12657 = new SqlBuilder();
            t_12657.appendSafe("active = ");
            t_12657.appendBoolean(true);
            Query a__1708 = t_12656.where(t_12657.getAccumulated());
            SafeIdentifier t_12662 = SrcTest.sid__662("users");
            SqlBuilder t_12663 = new SqlBuilder();
            t_12663.appendSafe("role = ");
            t_12663.appendString("vip");
            SqlFragment t_12666 = t_12663.getAccumulated();
            Query b__1709 = SrcGlobal.from(t_12662).where(t_12666);
            String s__1710 = SrcGlobal.unionSql(a__1708, b__1709).toString();
            boolean t_12671 = s__1710.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__12650 = () -> "union with where: " + s__1710;
            test_170.assert_(t_12671, fn__12650);
        } finally {
            test_170.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__2390() {
        Test test_171 = new Test();
        try {
            SafeIdentifier t_12634 = SrcTest.sid__662("orders");
            SafeIdentifier t_12635 = SrcTest.sid__662("user_id");
            Query sub__1712 = SrcGlobal.from(t_12634).select(List.of(t_12635));
            SafeIdentifier t_12637 = SrcTest.sid__662("users");
            SqlBuilder t_12638 = new SqlBuilder();
            t_12638.appendSafe("active = ");
            t_12638.appendBoolean(true);
            SqlFragment t_12641 = t_12638.getAccumulated();
            Query q__1713 = SrcGlobal.from(t_12637).where(t_12641).whereInSubquery(SrcTest.sid__662("id"), sub__1712);
            String s__1714 = q__1713.toSql().toString();
            boolean t_12648 = s__1714.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__12633 = () -> "whereInSubquery chained: " + s__1714;
            test_171.assert_(t_12648, fn__12633);
        } finally {
            test_171.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__2392() {
        Test test_172 = new Test();
        try {
            SafeIdentifier t_12620 = SrcTest.sid__662("orders");
            SqlBuilder t_12621 = new SqlBuilder();
            t_12621.appendSafe("orders.user_id = users.id");
            SqlFragment t_12623 = t_12621.getAccumulated();
            Query sub__1716 = SrcGlobal.from(t_12620).where(t_12623);
            SafeIdentifier t_12625 = SrcTest.sid__662("users");
            SqlFragment t_12626 = SrcGlobal.existsSql(sub__1716);
            Query q__1717 = SrcGlobal.from(t_12625).where(t_12626);
            String s__1718 = q__1717.toSql().toString();
            boolean t_12631 = s__1718.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__12619 = () -> "exists in where: " + s__1718;
            test_172.assert_(t_12631, fn__12619);
        } finally {
            test_172.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBasic__2394() {
        Test test_173 = new Test();
        try {
            SafeIdentifier t_12606 = SrcTest.sid__662("users");
            SafeIdentifier t_12607 = SrcTest.sid__662("name");
            SqlString t_12608 = new SqlString("Alice");
            UpdateQuery t_12609 = SrcGlobal.update(t_12606).set(t_12607, t_12608);
            SqlBuilder t_12610 = new SqlBuilder();
            t_12610.appendSafe("id = ");
            t_12610.appendInt32(1);
            SqlFragment t_6167;
            t_6167 = t_12609.where(t_12610.getAccumulated()).toSql();
            SqlFragment q__1720 = t_6167;
            boolean t_12617 = q__1720.toString().equals("UPDATE users SET name = 'Alice' WHERE id = 1");
            Supplier<String> fn__12605 = () -> "update basic";
            test_173.assert_(t_12617, fn__12605);
        } finally {
            test_173.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleSet__2396() {
        Test test_174 = new Test();
        try {
            SafeIdentifier t_12589 = SrcTest.sid__662("users");
            SafeIdentifier t_12590 = SrcTest.sid__662("name");
            SqlString t_12591 = new SqlString("Bob");
            UpdateQuery t_12595 = SrcGlobal.update(t_12589).set(t_12590, t_12591).set(SrcTest.sid__662("age"), new SqlInt32(30));
            SqlBuilder t_12596 = new SqlBuilder();
            t_12596.appendSafe("id = ");
            t_12596.appendInt32(2);
            SqlFragment t_6152;
            t_6152 = t_12595.where(t_12596.getAccumulated()).toSql();
            SqlFragment q__1722 = t_6152;
            boolean t_12603 = q__1722.toString().equals("UPDATE users SET name = 'Bob', age = 30 WHERE id = 2");
            Supplier<String> fn__12588 = () -> "update multi set";
            test_174.assert_(t_12603, fn__12588);
        } finally {
            test_174.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryMultipleWhere__2398() {
        Test test_175 = new Test();
        try {
            SafeIdentifier t_12570 = SrcTest.sid__662("users");
            SafeIdentifier t_12571 = SrcTest.sid__662("active");
            SqlBoolean t_12572 = new SqlBoolean(false);
            UpdateQuery t_12573 = SrcGlobal.update(t_12570).set(t_12571, t_12572);
            SqlBuilder t_12574 = new SqlBuilder();
            t_12574.appendSafe("age < ");
            t_12574.appendInt32(18);
            UpdateQuery t_12578 = t_12573.where(t_12574.getAccumulated());
            SqlBuilder t_12579 = new SqlBuilder();
            t_12579.appendSafe("role = ");
            t_12579.appendString("guest");
            SqlFragment t_6134;
            t_6134 = t_12578.where(t_12579.getAccumulated()).toSql();
            SqlFragment q__1724 = t_6134;
            boolean t_12586 = q__1724.toString().equals("UPDATE users SET active = FALSE WHERE age < 18 AND role = 'guest'");
            Supplier<String> fn__12569 = () -> "update multi where";
            test_175.assert_(t_12586, fn__12569);
        } finally {
            test_175.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryOrWhere__2401() {
        Test test_176 = new Test();
        try {
            SafeIdentifier t_12551 = SrcTest.sid__662("users");
            SafeIdentifier t_12552 = SrcTest.sid__662("status");
            SqlString t_12553 = new SqlString("banned");
            UpdateQuery t_12554 = SrcGlobal.update(t_12551).set(t_12552, t_12553);
            SqlBuilder t_12555 = new SqlBuilder();
            t_12555.appendSafe("spam_count > ");
            t_12555.appendInt32(10);
            UpdateQuery t_12559 = t_12554.where(t_12555.getAccumulated());
            SqlBuilder t_12560 = new SqlBuilder();
            t_12560.appendSafe("reported = ");
            t_12560.appendBoolean(true);
            SqlFragment t_6113;
            t_6113 = t_12559.orWhere(t_12560.getAccumulated()).toSql();
            SqlFragment q__1726 = t_6113;
            boolean t_12567 = q__1726.toString().equals("UPDATE users SET status = 'banned' WHERE spam_count > 10 OR reported = TRUE");
            Supplier<String> fn__12550 = () -> "update orWhere";
            test_176.assert_(t_12567, fn__12550);
        } finally {
            test_176.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutWhere__2404() {
        Test test_177 = new Test();
        try {
            SafeIdentifier t_12544;
            SafeIdentifier t_12545;
            SqlInt32 t_12546;
            boolean didBubble__1728;
            boolean didBubble_15610;
            try {
                t_12544 = SrcTest.sid__662("users");
                t_12545 = SrcTest.sid__662("x");
                t_12546 = new SqlInt32(1);
                SrcGlobal.update(t_12544).set(t_12545, t_12546).toSql();
                didBubble_15610 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_15610 = true;
            }
            didBubble__1728 = didBubble_15610;
            Supplier<String> fn__12543 = () -> "update without WHERE should bubble";
            test_177.assert_(didBubble__1728, fn__12543);
        } finally {
            test_177.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryBubblesWithoutSet__2405() {
        Test test_178 = new Test();
        try {
            SafeIdentifier t_12535;
            SqlBuilder t_12536;
            SqlFragment t_12539;
            boolean didBubble__1730;
            boolean didBubble_15611;
            try {
                t_12535 = SrcTest.sid__662("users");
                t_12536 = new SqlBuilder();
                t_12536.appendSafe("id = ");
                t_12536.appendInt32(1);
                t_12539 = t_12536.getAccumulated();
                SrcGlobal.update(t_12535).where(t_12539).toSql();
                didBubble_15611 = false;
            } catch (RuntimeException ignored$15) {
                didBubble_15611 = true;
            }
            didBubble__1730 = didBubble_15611;
            Supplier<String> fn__12534 = () -> "update without SET should bubble";
            test_178.assert_(didBubble__1730, fn__12534);
        } finally {
            test_178.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryWithLimit__2407() {
        Test test_179 = new Test();
        try {
            SafeIdentifier t_12521 = SrcTest.sid__662("users");
            SafeIdentifier t_12522 = SrcTest.sid__662("active");
            SqlBoolean t_12523 = new SqlBoolean(false);
            UpdateQuery t_12524 = SrcGlobal.update(t_12521).set(t_12522, t_12523);
            SqlBuilder t_12525 = new SqlBuilder();
            t_12525.appendSafe("last_login < ");
            t_12525.appendString("2024-01-01");
            UpdateQuery t_6076;
            t_6076 = t_12524.where(t_12525.getAccumulated()).limit(100);
            SqlFragment t_6077;
            t_6077 = t_6076.toSql();
            SqlFragment q__1732 = t_6077;
            boolean t_12532 = q__1732.toString().equals("UPDATE users SET active = FALSE WHERE last_login < '2024-01-01' LIMIT 100");
            Supplier<String> fn__12520 = () -> "update limit";
            test_179.assert_(t_12532, fn__12520);
        } finally {
            test_179.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void updateQueryEscaping__2409() {
        Test test_180 = new Test();
        try {
            SafeIdentifier t_12507 = SrcTest.sid__662("users");
            SafeIdentifier t_12508 = SrcTest.sid__662("bio");
            SqlString t_12509 = new SqlString("It's a test");
            UpdateQuery t_12510 = SrcGlobal.update(t_12507).set(t_12508, t_12509);
            SqlBuilder t_12511 = new SqlBuilder();
            t_12511.appendSafe("id = ");
            t_12511.appendInt32(1);
            SqlFragment t_6061;
            t_6061 = t_12510.where(t_12511.getAccumulated()).toSql();
            SqlFragment q__1734 = t_6061;
            boolean t_12518 = q__1734.toString().equals("UPDATE users SET bio = 'It''s a test' WHERE id = 1");
            Supplier<String> fn__12506 = () -> "update escaping";
            test_180.assert_(t_12518, fn__12506);
        } finally {
            test_180.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBasic__2411() {
        Test test_181 = new Test();
        try {
            SafeIdentifier t_12496 = SrcTest.sid__662("users");
            SqlBuilder t_12497 = new SqlBuilder();
            t_12497.appendSafe("id = ");
            t_12497.appendInt32(1);
            SqlFragment t_12500 = t_12497.getAccumulated();
            SqlFragment t_6046;
            t_6046 = SrcGlobal.deleteFrom(t_12496).where(t_12500).toSql();
            SqlFragment q__1736 = t_6046;
            boolean t_12504 = q__1736.toString().equals("DELETE FROM users WHERE id = 1");
            Supplier<String> fn__12495 = () -> "delete basic";
            test_181.assert_(t_12504, fn__12495);
        } finally {
            test_181.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryMultipleWhere__2413() {
        Test test_182 = new Test();
        try {
            SafeIdentifier t_12480 = SrcTest.sid__662("logs");
            SqlBuilder t_12481 = new SqlBuilder();
            t_12481.appendSafe("created_at < ");
            t_12481.appendString("2024-01-01");
            SqlFragment t_12484 = t_12481.getAccumulated();
            DeleteQuery t_12485 = SrcGlobal.deleteFrom(t_12480).where(t_12484);
            SqlBuilder t_12486 = new SqlBuilder();
            t_12486.appendSafe("level = ");
            t_12486.appendString("debug");
            SqlFragment t_6034;
            t_6034 = t_12485.where(t_12486.getAccumulated()).toSql();
            SqlFragment q__1738 = t_6034;
            boolean t_12493 = q__1738.toString().equals("DELETE FROM logs WHERE created_at < '2024-01-01' AND level = 'debug'");
            Supplier<String> fn__12479 = () -> "delete multi where";
            test_182.assert_(t_12493, fn__12479);
        } finally {
            test_182.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryBubblesWithoutWhere__2416() {
        Test test_183 = new Test();
        try {
            boolean didBubble__1740;
            boolean didBubble_15612;
            try {
                SrcGlobal.deleteFrom(SrcTest.sid__662("users")).toSql();
                didBubble_15612 = false;
            } catch (RuntimeException ignored$16) {
                didBubble_15612 = true;
            }
            didBubble__1740 = didBubble_15612;
            Supplier<String> fn__12475 = () -> "delete without WHERE should bubble";
            test_183.assert_(didBubble__1740, fn__12475);
        } finally {
            test_183.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryOrWhere__2417() {
        Test test_184 = new Test();
        try {
            SafeIdentifier t_12460 = SrcTest.sid__662("sessions");
            SqlBuilder t_12461 = new SqlBuilder();
            t_12461.appendSafe("expired = ");
            t_12461.appendBoolean(true);
            SqlFragment t_12464 = t_12461.getAccumulated();
            DeleteQuery t_12465 = SrcGlobal.deleteFrom(t_12460).where(t_12464);
            SqlBuilder t_12466 = new SqlBuilder();
            t_12466.appendSafe("created_at < ");
            t_12466.appendString("2023-01-01");
            SqlFragment t_6013;
            t_6013 = t_12465.orWhere(t_12466.getAccumulated()).toSql();
            SqlFragment q__1742 = t_6013;
            boolean t_12473 = q__1742.toString().equals("DELETE FROM sessions WHERE expired = TRUE OR created_at < '2023-01-01'");
            Supplier<String> fn__12459 = () -> "delete orWhere";
            test_184.assert_(t_12473, fn__12459);
        } finally {
            test_184.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void deleteQueryWithLimit__2420() {
        Test test_185 = new Test();
        try {
            SafeIdentifier t_12449 = SrcTest.sid__662("logs");
            SqlBuilder t_12450 = new SqlBuilder();
            t_12450.appendSafe("level = ");
            t_12450.appendString("debug");
            SqlFragment t_12453 = t_12450.getAccumulated();
            DeleteQuery t_5994;
            t_5994 = SrcGlobal.deleteFrom(t_12449).where(t_12453).limit(1000);
            SqlFragment t_5995;
            t_5995 = t_5994.toSql();
            SqlFragment q__1744 = t_5995;
            boolean t_12457 = q__1744.toString().equals("DELETE FROM logs WHERE level = 'debug' LIMIT 1000");
            Supplier<String> fn__12448 = () -> "delete limit";
            test_185.assert_(t_12457, fn__12448);
        } finally {
            test_185.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsFirst__2422() {
        Test test_186 = new Test();
        try {
            SafeIdentifier t_12439 = SrcTest.sid__662("users");
            SafeIdentifier t_12440 = SrcTest.sid__662("email");
            NullsFirst t_12441 = new NullsFirst();
            Query q__1746 = SrcGlobal.from(t_12439).orderByNulls(t_12440, true, t_12441);
            boolean t_12446 = q__1746.toSql().toString().equals("SELECT * FROM users ORDER BY email ASC NULLS FIRST");
            Supplier<String> fn__12438 = () -> "nulls first";
            test_186.assert_(t_12446, fn__12438);
        } finally {
            test_186.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByNullsNullsLast__2423() {
        Test test_187 = new Test();
        try {
            SafeIdentifier t_12429 = SrcTest.sid__662("users");
            SafeIdentifier t_12430 = SrcTest.sid__662("score");
            NullsLast t_12431 = new NullsLast();
            Query q__1748 = SrcGlobal.from(t_12429).orderByNulls(t_12430, false, t_12431);
            boolean t_12436 = q__1748.toSql().toString().equals("SELECT * FROM users ORDER BY score DESC NULLS LAST");
            Supplier<String> fn__12428 = () -> "nulls last";
            test_187.assert_(t_12436, fn__12428);
        } finally {
            test_187.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedOrderByAndOrderByNulls__2424() {
        Test test_188 = new Test();
        try {
            SafeIdentifier t_12417 = SrcTest.sid__662("users");
            SafeIdentifier t_12418 = SrcTest.sid__662("name");
            Query q__1750 = SrcGlobal.from(t_12417).orderBy(t_12418, true).orderByNulls(SrcTest.sid__662("email"), true, new NullsFirst());
            boolean t_12426 = q__1750.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC, email ASC NULLS FIRST");
            Supplier<String> fn__12416 = () -> "mixed order";
            test_188.assert_(t_12426, fn__12416);
        } finally {
            test_188.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoin__2425() {
        Test test_189 = new Test();
        try {
            SafeIdentifier t_12408 = SrcTest.sid__662("users");
            SafeIdentifier t_12409 = SrcTest.sid__662("colors");
            Query q__1752 = SrcGlobal.from(t_12408).crossJoin(t_12409);
            boolean t_12414 = q__1752.toSql().toString().equals("SELECT * FROM users CROSS JOIN colors");
            Supplier<String> fn__12407 = () -> "cross join";
            test_189.assert_(t_12414, fn__12407);
        } finally {
            test_189.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void crossJoinCombinedWithOtherJoins__2426() {
        Test test_190 = new Test();
        try {
            SafeIdentifier t_12394 = SrcTest.sid__662("users");
            SafeIdentifier t_12395 = SrcTest.sid__662("orders");
            SqlBuilder t_12396 = new SqlBuilder();
            t_12396.appendSafe("users.id = orders.user_id");
            SqlFragment t_12398 = t_12396.getAccumulated();
            Query q__1754 = SrcGlobal.from(t_12394).innerJoin(t_12395, t_12398).crossJoin(SrcTest.sid__662("colors"));
            boolean t_12405 = q__1754.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id CROSS JOIN colors");
            Supplier<String> fn__12393 = () -> "cross + inner join";
            test_190.assert_(t_12405, fn__12393);
        } finally {
            test_190.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForUpdate__2428() {
        Test test_191 = new Test();
        try {
            SafeIdentifier t_12380 = SrcTest.sid__662("users");
            SqlBuilder t_12381 = new SqlBuilder();
            t_12381.appendSafe("id = ");
            t_12381.appendInt32(1);
            SqlFragment t_12384 = t_12381.getAccumulated();
            Query q__1756 = SrcGlobal.from(t_12380).where(t_12384).lock(new ForUpdate());
            boolean t_12391 = q__1756.toSql().toString().equals("SELECT * FROM users WHERE id = 1 FOR UPDATE");
            Supplier<String> fn__12379 = () -> "for update";
            test_191.assert_(t_12391, fn__12379);
        } finally {
            test_191.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockForShare__2430() {
        Test test_192 = new Test();
        try {
            SafeIdentifier t_12369 = SrcTest.sid__662("users");
            SafeIdentifier t_12370 = SrcTest.sid__662("name");
            Query q__1758 = SrcGlobal.from(t_12369).select(List.of(t_12370)).lock(new ForShare());
            boolean t_12377 = q__1758.toSql().toString().equals("SELECT name FROM users FOR SHARE");
            Supplier<String> fn__12368 = () -> "for share";
            test_192.assert_(t_12377, fn__12368);
        } finally {
            test_192.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lockWithFullQuery__2431() {
        Test test_193 = new Test();
        try {
            SafeIdentifier t_12355 = SrcTest.sid__662("accounts");
            SqlBuilder t_12356 = new SqlBuilder();
            t_12356.appendSafe("id = ");
            t_12356.appendInt32(42);
            SqlFragment t_12359 = t_12356.getAccumulated();
            Query t_5918;
            t_5918 = SrcGlobal.from(t_12355).where(t_12359).limit(1);
            Query t_12362 = t_5918.lock(new ForUpdate());
            Query q__1760 = t_12362;
            boolean t_12366 = q__1760.toSql().toString().equals("SELECT * FROM accounts WHERE id = 42 LIMIT 1 FOR UPDATE");
            Supplier<String> fn__12354 = () -> "lock full query";
            test_193.assert_(t_12366, fn__12354);
        } finally {
            test_193.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__2433() {
        Test test_200 = new Test();
        try {
            SafeIdentifier t_5907;
            t_5907 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1808 = t_5907;
            boolean t_12352 = id__1808.getSqlValue().equals("user_name");
            Supplier<String> fn__12349 = () -> "value should round-trip";
            test_200.assert_(t_12352, fn__12349);
        } finally {
            test_200.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__2434() {
        Test test_201 = new Test();
        try {
            boolean didBubble__1810;
            boolean didBubble_15613;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_15613 = false;
            } catch (RuntimeException ignored$17) {
                didBubble_15613 = true;
            }
            didBubble__1810 = didBubble_15613;
            Supplier<String> fn__12346 = () -> "empty string should bubble";
            test_201.assert_(didBubble__1810, fn__12346);
        } finally {
            test_201.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__2435() {
        Test test_202 = new Test();
        try {
            boolean didBubble__1812;
            boolean didBubble_15614;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_15614 = false;
            } catch (RuntimeException ignored$18) {
                didBubble_15614 = true;
            }
            didBubble__1812 = didBubble_15614;
            Supplier<String> fn__12343 = () -> "leading digit should bubble";
            test_202.assert_(didBubble__1812, fn__12343);
        } finally {
            test_202.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__2436() {
        Test test_203 = new Test();
        try {
            List<String> cases__1814 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__12340 = c__1815 -> {
                boolean didBubble__1816;
                boolean didBubble_15615;
                try {
                    SrcGlobal.safeIdentifier(c__1815);
                    didBubble_15615 = false;
                } catch (RuntimeException ignored$19) {
                    didBubble_15615 = true;
                }
                didBubble__1816 = didBubble_15615;
                Supplier<String> fn__12337 = () -> "should reject: " + c__1815;
                test_203.assert_(didBubble__1816, fn__12337);
            };
            cases__1814.forEach(fn__12340);
        } finally {
            test_203.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__2437() {
        Test test_204 = new Test();
        try {
            SafeIdentifier t_5884;
            t_5884 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5885 = t_5884;
            SafeIdentifier t_5886;
            t_5886 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5887 = t_5886;
            StringField t_12327 = new StringField();
            FieldDef t_12328 = new FieldDef(t_5887, t_12327, false, null, false);
            SafeIdentifier t_5890;
            t_5890 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_5891 = t_5890;
            IntField t_12329 = new IntField();
            FieldDef t_12330 = new FieldDef(t_5891, t_12329, false, null, false);
            TableDef td__1818 = new TableDef(t_5885, List.of(t_12328, t_12330), null);
            FieldDef t_5895;
            t_5895 = td__1818.field("age");
            FieldDef f__1819 = t_5895;
            boolean t_12335 = f__1819.getName().getSqlValue().equals("age");
            Supplier<String> fn__12326 = () -> "should find age field";
            test_204.assert_(t_12335, fn__12326);
        } finally {
            test_204.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__2438() {
        Test test_205 = new Test();
        try {
            SafeIdentifier t_5875;
            t_5875 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5876 = t_5875;
            SafeIdentifier t_5877;
            t_5877 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5878 = t_5877;
            StringField t_12321 = new StringField();
            FieldDef t_12322 = new FieldDef(t_5878, t_12321, false, null, false);
            TableDef td__1821 = new TableDef(t_5876, List.of(t_12322), null);
            boolean didBubble__1822;
            boolean didBubble_15616;
            try {
                td__1821.field("nonexistent");
                didBubble_15616 = false;
            } catch (RuntimeException ignored$20) {
                didBubble_15616 = true;
            }
            didBubble__1822 = didBubble_15616;
            Supplier<String> fn__12320 = () -> "unknown field should bubble";
            test_205.assert_(didBubble__1822, fn__12320);
        } finally {
            test_205.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__2439() {
        Test test_206 = new Test();
        try {
            SafeIdentifier t_5863;
            t_5863 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_5864 = t_5863;
            StringField t_12309 = new StringField();
            FieldDef required__1824 = new FieldDef(t_5864, t_12309, false, null, false);
            SafeIdentifier t_5867;
            t_5867 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_5868 = t_5867;
            StringField t_12311 = new StringField();
            FieldDef optional__1825 = new FieldDef(t_5868, t_12311, true, null, false);
            boolean t_12315 = !required__1824.isNullable();
            Supplier<String> fn__12308 = () -> "required field should not be nullable";
            test_206.assert_(t_12315, fn__12308);
            boolean t_12317 = optional__1825.isNullable();
            Supplier<String> fn__12307 = () -> "optional field should be nullable";
            test_206.assert_(t_12317, fn__12307);
        } finally {
            test_206.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void pkNameDefaultsToIdWhenPrimaryKeyIsNull__2440() {
        Test test_207 = new Test();
        try {
            SafeIdentifier t_5854;
            t_5854 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5855 = t_5854;
            SafeIdentifier t_5856;
            t_5856 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5857 = t_5856;
            StringField t_12300 = new StringField();
            FieldDef t_12301 = new FieldDef(t_5857, t_12300, false, null, false);
            TableDef td__1827 = new TableDef(t_5855, List.of(t_12301), null);
            boolean t_12305 = td__1827.pkName().equals("id");
            Supplier<String> fn__12299 = () -> "default pk should be id";
            test_207.assert_(t_12305, fn__12299);
        } finally {
            test_207.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void pkNameReturnsCustomPrimaryKey__2441() {
        Test test_208 = new Test();
        try {
            SafeIdentifier t_5842;
            t_5842 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_5843 = t_5842;
            SafeIdentifier t_5844;
            t_5844 = SrcGlobal.safeIdentifier("user_id");
            SafeIdentifier t_5845 = t_5844;
            IntField t_12292 = new IntField();
            List<FieldDef> t_5850 = List.of(new FieldDef(t_5845, t_12292, false, null, false));
            SafeIdentifier t_5848;
            t_5848 = SrcGlobal.safeIdentifier("user_id");
            SafeIdentifier t_5849 = t_5848;
            TableDef td__1829 = new TableDef(t_5843, t_5850, t_5849);
            boolean t_12297 = td__1829.pkName().equals("user_id");
            Supplier<String> fn__12291 = () -> "custom pk should be user_id";
            test_208.assert_(t_12297, fn__12291);
        } finally {
            test_208.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void timestampsReturnsTwoDateFieldDefs__2442() {
        Test test_209 = new Test();
        try {
            List<FieldDef> t_5818;
            t_5818 = SrcGlobal.timestamps();
            List<FieldDef> ts__1831 = t_5818;
            boolean t_12259 = ts__1831.size() == 2;
            Supplier<String> fn__12256 = () -> "should return 2 fields";
            test_209.assert_(t_12259, fn__12256);
            boolean t_12265 = Core.listGet(ts__1831, 0).getName().getSqlValue().equals("inserted_at");
            Supplier<String> fn__12255 = () -> "first should be inserted_at";
            test_209.assert_(t_12265, fn__12255);
            boolean t_12271 = Core.listGet(ts__1831, 1).getName().getSqlValue().equals("updated_at");
            Supplier<String> fn__12254 = () -> "second should be updated_at";
            test_209.assert_(t_12271, fn__12254);
            boolean t_12274 = Core.listGet(ts__1831, 0).isNullable();
            Supplier<String> fn__12253 = () -> "inserted_at should be nullable";
            test_209.assert_(t_12274, fn__12253);
            boolean t_12278 = Core.listGet(ts__1831, 1).isNullable();
            Supplier<String> fn__12252 = () -> "updated_at should be nullable";
            test_209.assert_(t_12278, fn__12252);
            boolean t_12284 = Core.listGet(ts__1831, 0).getDefaultValue() != null;
            Supplier<String> fn__12251 = () -> "inserted_at should have default";
            test_209.assert_(t_12284, fn__12251);
            boolean t_12289 = Core.listGet(ts__1831, 1).getDefaultValue() != null;
            Supplier<String> fn__12250 = () -> "updated_at should have default";
            test_209.assert_(t_12289, fn__12250);
        } finally {
            test_209.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefDefaultValueField__2443() {
        Test test_210 = new Test();
        try {
            SafeIdentifier t_5805;
            t_5805 = SrcGlobal.safeIdentifier("status");
            SafeIdentifier t_5806 = t_5805;
            StringField t_12237 = new StringField();
            SqlDefault t_12238 = new SqlDefault();
            FieldDef withDefault__1833 = new FieldDef(t_5806, t_12237, false, t_12238, false);
            SafeIdentifier t_5810;
            t_5810 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5811 = t_5810;
            StringField t_12240 = new StringField();
            FieldDef withoutDefault__1834 = new FieldDef(t_5811, t_12240, false, null, false);
            boolean t_12244 = withDefault__1833.getDefaultValue() != null;
            Supplier<String> fn__12236 = () -> "should have default";
            test_210.assert_(t_12244, fn__12236);
            boolean t_12248 = withoutDefault__1834.getDefaultValue() == null;
            Supplier<String> fn__12235 = () -> "should not have default";
            test_210.assert_(t_12248, fn__12235);
        } finally {
            test_210.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefVirtualFlag__2444() {
        Test test_211 = new Test();
        try {
            SafeIdentifier t_5793;
            t_5793 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_5794 = t_5793;
            StringField t_12224 = new StringField();
            FieldDef normal__1836 = new FieldDef(t_5794, t_12224, false, null, false);
            SafeIdentifier t_5797;
            t_5797 = SrcGlobal.safeIdentifier("full_name");
            SafeIdentifier t_5798 = t_5797;
            StringField t_12226 = new StringField();
            FieldDef virt__1837 = new FieldDef(t_5798, t_12226, true, null, true);
            boolean t_12230 = !normal__1836.isVirtual();
            Supplier<String> fn__12223 = () -> "normal field should not be virtual";
            test_211.assert_(t_12230, fn__12223);
            boolean t_12232 = virt__1837.isVirtual();
            Supplier<String> fn__12222 = () -> "virtual field should be virtual";
            test_211.assert_(t_12232, fn__12222);
        } finally {
            test_211.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__2445() {
        Test test_215 = new Test();
        try {
            Function<String, String> build__1967 = name__1969 -> {
                SqlBuilder t_12204 = new SqlBuilder();
                t_12204.appendSafe("select * from hi where name = ");
                t_12204.appendString(name__1969);
                return t_12204.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1968 = name__1971 -> "select * from hi where name = '" + name__1971 + "'";
            String actual_2447 = build__1967.apply("world");
            boolean t_12214 = actual_2447.equals("select * from hi where name = 'world'");
            Supplier<String> fn__12211 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_2447 + ")";
            test_215.assert_(t_12214, fn__12211);
            String bobbyTables__1973 = "Robert'); drop table hi;--";
            String actual_2449 = build__1967.apply("Robert'); drop table hi;--");
            boolean t_12218 = actual_2449.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__12210 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_2449 + ")";
            test_215.assert_(t_12218, fn__12210);
            Supplier<String> fn__12209 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_215.assert_(true, fn__12209);
        } finally {
            test_215.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__2453() {
        Test test_216 = new Test();
        try {
            SqlBuilder t_12172 = new SqlBuilder();
            t_12172.appendSafe("v = ");
            t_12172.appendString("");
            String actual_2454 = t_12172.getAccumulated().toString();
            boolean t_12178 = actual_2454.equals("v = ''");
            Supplier<String> fn__12171 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_2454 + ")";
            test_216.assert_(t_12178, fn__12171);
            SqlBuilder t_12180 = new SqlBuilder();
            t_12180.appendSafe("v = ");
            t_12180.appendString("a''b");
            String actual_2457 = t_12180.getAccumulated().toString();
            boolean t_12186 = actual_2457.equals("v = 'a''''b'");
            Supplier<String> fn__12170 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_2457 + ")";
            test_216.assert_(t_12186, fn__12170);
            SqlBuilder t_12188 = new SqlBuilder();
            t_12188.appendSafe("v = ");
            t_12188.appendString("Hello \u4e16\u754c");
            String actual_2460 = t_12188.getAccumulated().toString();
            boolean t_12194 = actual_2460.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__12169 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_2460 + ")";
            test_216.assert_(t_12194, fn__12169);
            SqlBuilder t_12196 = new SqlBuilder();
            t_12196.appendSafe("v = ");
            t_12196.appendString("Line1\nLine2");
            String actual_2463 = t_12196.getAccumulated().toString();
            boolean t_12202 = actual_2463.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__12168 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_2463 + ")";
            test_216.assert_(t_12202, fn__12168);
        } finally {
            test_216.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__2466() {
        Test test_217 = new Test();
        try {
            SqlBuilder t_12143 = new SqlBuilder();
            t_12143.appendSafe("select ");
            t_12143.appendInt32(42);
            t_12143.appendSafe(", ");
            t_12143.appendInt64(43);
            t_12143.appendSafe(", ");
            t_12143.appendFloat64(19.99D);
            t_12143.appendSafe(", ");
            t_12143.appendBoolean(true);
            t_12143.appendSafe(", ");
            t_12143.appendBoolean(false);
            String actual_2467 = t_12143.getAccumulated().toString();
            boolean t_12157 = actual_2467.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__12142 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_2467 + ")";
            test_217.assert_(t_12157, fn__12142);
            LocalDate t_5738;
            t_5738 = LocalDate.of(2024, 12, 25);
            LocalDate date__1976 = t_5738;
            SqlBuilder t_12159 = new SqlBuilder();
            t_12159.appendSafe("insert into t values (");
            t_12159.appendDate(date__1976);
            t_12159.appendSafe(")");
            String actual_2470 = t_12159.getAccumulated().toString();
            boolean t_12166 = actual_2470.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__12141 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_2470 + ")";
            test_217.assert_(t_12166, fn__12141);
        } finally {
            test_217.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__2473() {
        Test test_218 = new Test();
        try {
            SqlBuilder t_12087 = new SqlBuilder();
            t_12087.appendSafe("v IN (");
            t_12087.appendStringList(List.of("a", "b", "c'd"));
            t_12087.appendSafe(")");
            String actual_2474 = t_12087.getAccumulated().toString();
            boolean t_12094 = actual_2474.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__12086 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_2474 + ")";
            test_218.assert_(t_12094, fn__12086);
            SqlBuilder t_12096 = new SqlBuilder();
            t_12096.appendSafe("v IN (");
            t_12096.appendInt32List(List.of(1, 2, 3));
            t_12096.appendSafe(")");
            String actual_2477 = t_12096.getAccumulated().toString();
            boolean t_12103 = actual_2477.equals("v IN (1, 2, 3)");
            Supplier<String> fn__12085 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_2477 + ")";
            test_218.assert_(t_12103, fn__12085);
            SqlBuilder t_12105 = new SqlBuilder();
            t_12105.appendSafe("v IN (");
            t_12105.appendInt64List(List.of(1, 2));
            t_12105.appendSafe(")");
            String actual_2480 = t_12105.getAccumulated().toString();
            boolean t_12112 = actual_2480.equals("v IN (1, 2)");
            Supplier<String> fn__12084 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_2480 + ")";
            test_218.assert_(t_12112, fn__12084);
            SqlBuilder t_12114 = new SqlBuilder();
            t_12114.appendSafe("v IN (");
            t_12114.appendFloat64List(List.of(1.0D, 2.0D));
            t_12114.appendSafe(")");
            String actual_2483 = t_12114.getAccumulated().toString();
            boolean t_12121 = actual_2483.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__12083 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_2483 + ")";
            test_218.assert_(t_12121, fn__12083);
            SqlBuilder t_12123 = new SqlBuilder();
            t_12123.appendSafe("v IN (");
            t_12123.appendBooleanList(List.of(true, false));
            t_12123.appendSafe(")");
            String actual_2486 = t_12123.getAccumulated().toString();
            boolean t_12130 = actual_2486.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__12082 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_2486 + ")";
            test_218.assert_(t_12130, fn__12082);
            LocalDate t_5710;
            t_5710 = LocalDate.of(2024, 1, 1);
            LocalDate t_5711 = t_5710;
            LocalDate t_5712;
            t_5712 = LocalDate.of(2024, 12, 25);
            LocalDate t_5713 = t_5712;
            List<LocalDate> dates__1978 = List.of(t_5711, t_5713);
            SqlBuilder t_12132 = new SqlBuilder();
            t_12132.appendSafe("v IN (");
            t_12132.appendDateList(dates__1978);
            t_12132.appendSafe(")");
            String actual_2489 = t_12132.getAccumulated().toString();
            boolean t_12139 = actual_2489.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__12081 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_2489 + ")";
            test_218.assert_(t_12139, fn__12081);
        } finally {
            test_218.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__2492() {
        Test test_219 = new Test();
        try {
            double nan__1980;
            nan__1980 = 0.0D / 0.0D;
            SqlBuilder t_12073 = new SqlBuilder();
            t_12073.appendSafe("v = ");
            t_12073.appendFloat64(nan__1980);
            String actual_2493 = t_12073.getAccumulated().toString();
            boolean t_12079 = actual_2493.equals("v = NULL");
            Supplier<String> fn__12072 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_2493 + ")";
            test_219.assert_(t_12079, fn__12072);
        } finally {
            test_219.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__2496() {
        Test test_220 = new Test();
        try {
            double inf__1982;
            inf__1982 = 1.0D / 0.0D;
            SqlBuilder t_12064 = new SqlBuilder();
            t_12064.appendSafe("v = ");
            t_12064.appendFloat64(inf__1982);
            String actual_2497 = t_12064.getAccumulated().toString();
            boolean t_12070 = actual_2497.equals("v = NULL");
            Supplier<String> fn__12063 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_2497 + ")";
            test_220.assert_(t_12070, fn__12063);
        } finally {
            test_220.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__2500() {
        Test test_221 = new Test();
        try {
            double ninf__1984;
            ninf__1984 = -1.0D / 0.0D;
            SqlBuilder t_12055 = new SqlBuilder();
            t_12055.appendSafe("v = ");
            t_12055.appendFloat64(ninf__1984);
            String actual_2501 = t_12055.getAccumulated().toString();
            boolean t_12061 = actual_2501.equals("v = NULL");
            Supplier<String> fn__12054 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_2501 + ")";
            test_221.assert_(t_12061, fn__12054);
        } finally {
            test_221.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__2504() {
        Test test_222 = new Test();
        try {
            SqlBuilder t_12030 = new SqlBuilder();
            t_12030.appendSafe("v = ");
            t_12030.appendFloat64(3.14D);
            String actual_2505 = t_12030.getAccumulated().toString();
            boolean t_12036 = actual_2505.equals("v = 3.14");
            Supplier<String> fn__12029 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_2505 + ")";
            test_222.assert_(t_12036, fn__12029);
            SqlBuilder t_12038 = new SqlBuilder();
            t_12038.appendSafe("v = ");
            t_12038.appendFloat64(0.0D);
            String actual_2508 = t_12038.getAccumulated().toString();
            boolean t_12044 = actual_2508.equals("v = 0.0");
            Supplier<String> fn__12028 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_2508 + ")";
            test_222.assert_(t_12044, fn__12028);
            SqlBuilder t_12046 = new SqlBuilder();
            t_12046.appendSafe("v = ");
            t_12046.appendFloat64(-42.5D);
            String actual_2511 = t_12046.getAccumulated().toString();
            boolean t_12052 = actual_2511.equals("v = -42.5");
            Supplier<String> fn__12027 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_2511 + ")";
            test_222.assert_(t_12052, fn__12027);
        } finally {
            test_222.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__2514() {
        Test test_223 = new Test();
        try {
            LocalDate t_5606;
            t_5606 = LocalDate.of(2024, 6, 15);
            LocalDate d__1987 = t_5606;
            SqlBuilder t_12019 = new SqlBuilder();
            t_12019.appendSafe("v = ");
            t_12019.appendDate(d__1987);
            String actual_2515 = t_12019.getAccumulated().toString();
            boolean t_12025 = actual_2515.equals("v = '2024-06-15'");
            Supplier<String> fn__12018 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_2515 + ")";
            test_223.assert_(t_12025, fn__12018);
        } finally {
            test_223.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__2518() {
        Test test_224 = new Test();
        try {
            String name__1989 = "Someone";
            SqlBuilder t_11987 = new SqlBuilder();
            t_11987.appendSafe("where p.last_name = ");
            t_11987.appendString("Someone");
            SqlFragment condition__1990 = t_11987.getAccumulated();
            SqlBuilder t_11991 = new SqlBuilder();
            t_11991.appendSafe("select p.id from person p ");
            t_11991.appendFragment(condition__1990);
            String actual_2520 = t_11991.getAccumulated().toString();
            boolean t_11997 = actual_2520.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__11986 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2520 + ")";
            test_224.assert_(t_11997, fn__11986);
            SqlBuilder t_11999 = new SqlBuilder();
            t_11999.appendSafe("select p.id from person p ");
            t_11999.appendPart(condition__1990.toSource());
            String actual_2523 = t_11999.getAccumulated().toString();
            boolean t_12006 = actual_2523.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__11985 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_2523 + ")";
            test_224.assert_(t_12006, fn__11985);
            List<SqlPart> parts__1991 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_12010 = new SqlBuilder();
            t_12010.appendSafe("select ");
            t_12010.appendPartList(parts__1991);
            String actual_2526 = t_12010.getAccumulated().toString();
            boolean t_12016 = actual_2526.equals("select 'a''b', 3");
            Supplier<String> fn__11984 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_2526 + ")";
            test_224.assert_(t_12016, fn__11984);
        } finally {
            test_224.softFailToHard();
        }
    }
}
