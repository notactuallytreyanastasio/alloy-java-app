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
    static SafeIdentifier csid__459(String name__604) {
        SafeIdentifier t_5286;
        t_5286 = SrcGlobal.safeIdentifier(name__604);
        return t_5286;
    }
    static TableDef userTable__460() {
        return new TableDef(SrcTest.csid__459("users"), List.of(new FieldDef(SrcTest.csid__459("name"), new StringField(), false), new FieldDef(SrcTest.csid__459("email"), new StringField(), false), new FieldDef(SrcTest.csid__459("age"), new IntField(), true), new FieldDef(SrcTest.csid__459("score"), new FloatField(), true), new FieldDef(SrcTest.csid__459("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1404() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__608 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_9397 = SrcTest.userTable__460();
            SafeIdentifier t_9398 = SrcTest.csid__459("name");
            SafeIdentifier t_9399 = SrcTest.csid__459("email");
            Changeset cs__609 = SrcGlobal.changeset(t_9397, params__608).cast(List.of(t_9398, t_9399));
            boolean t_9402 = cs__609.getChanges().containsKey("name");
            Supplier<String> fn__9392 = () -> "name should be in changes";
            test_24.assert_(t_9402, fn__9392);
            boolean t_9406 = cs__609.getChanges().containsKey("email");
            Supplier<String> fn__9391 = () -> "email should be in changes";
            test_24.assert_(t_9406, fn__9391);
            boolean t_9412 = !cs__609.getChanges().containsKey("admin");
            Supplier<String> fn__9390 = () -> "admin must be dropped (not in whitelist)";
            test_24.assert_(t_9412, fn__9390);
            boolean t_9414 = cs__609.isValid();
            Supplier<String> fn__9389 = () -> "should still be valid";
            test_24.assert_(t_9414, fn__9389);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1405() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__611 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_9375 = SrcTest.userTable__460();
            SafeIdentifier t_9376 = SrcTest.csid__459("name");
            Changeset cs__612 = SrcGlobal.changeset(t_9375, params__611).cast(List.of(t_9376)).cast(List.of(SrcTest.csid__459("email")));
            boolean t_9383 = !cs__612.getChanges().containsKey("name");
            Supplier<String> fn__9371 = () -> "name must be excluded by second cast";
            test_25.assert_(t_9383, fn__9371);
            boolean t_9386 = cs__612.getChanges().containsKey("email");
            Supplier<String> fn__9370 = () -> "email should be present";
            test_25.assert_(t_9386, fn__9370);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1406() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__614 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_9357 = SrcTest.userTable__460();
            SafeIdentifier t_9358 = SrcTest.csid__459("name");
            SafeIdentifier t_9359 = SrcTest.csid__459("email");
            Changeset cs__615 = SrcGlobal.changeset(t_9357, params__614).cast(List.of(t_9358, t_9359));
            boolean t_9364 = !cs__615.getChanges().containsKey("name");
            Supplier<String> fn__9353 = () -> "empty name should not be in changes";
            test_26.assert_(t_9364, fn__9353);
            boolean t_9367 = cs__615.getChanges().containsKey("email");
            Supplier<String> fn__9352 = () -> "email should be in changes";
            test_26.assert_(t_9367, fn__9352);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1407() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__617 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_9339 = SrcTest.userTable__460();
            SafeIdentifier t_9340 = SrcTest.csid__459("name");
            Changeset cs__618 = SrcGlobal.changeset(t_9339, params__617).cast(List.of(t_9340)).validateRequired(List.of(SrcTest.csid__459("name")));
            boolean t_9344 = cs__618.isValid();
            Supplier<String> fn__9336 = () -> "should be valid";
            test_27.assert_(t_9344, fn__9336);
            boolean t_9350 = cs__618.getErrors().size() == 0;
            Supplier<String> fn__9335 = () -> "no errors expected";
            test_27.assert_(t_9350, fn__9335);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1408() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__620 = Core.mapConstructor(List.of());
            TableDef t_9315 = SrcTest.userTable__460();
            SafeIdentifier t_9316 = SrcTest.csid__459("name");
            Changeset cs__621 = SrcGlobal.changeset(t_9315, params__620).cast(List.of(t_9316)).validateRequired(List.of(SrcTest.csid__459("name")));
            boolean t_9322 = !cs__621.isValid();
            Supplier<String> fn__9313 = () -> "should be invalid";
            test_28.assert_(t_9322, fn__9313);
            boolean t_9327 = cs__621.getErrors().size() == 1;
            Supplier<String> fn__9312 = () -> "should have one error";
            test_28.assert_(t_9327, fn__9312);
            boolean t_9333 = Core.listGet(cs__621.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__9311 = () -> "error should name the field";
            test_28.assert_(t_9333, fn__9311);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1409() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__623 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_9303 = SrcTest.userTable__460();
            SafeIdentifier t_9304 = SrcTest.csid__459("name");
            Changeset cs__624 = SrcGlobal.changeset(t_9303, params__623).cast(List.of(t_9304)).validateLength(SrcTest.csid__459("name"), 2, 50);
            boolean t_9308 = cs__624.isValid();
            Supplier<String> fn__9300 = () -> "should be valid";
            test_29.assert_(t_9308, fn__9300);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1410() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__626 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_9291 = SrcTest.userTable__460();
            SafeIdentifier t_9292 = SrcTest.csid__459("name");
            Changeset cs__627 = SrcGlobal.changeset(t_9291, params__626).cast(List.of(t_9292)).validateLength(SrcTest.csid__459("name"), 2, 50);
            boolean t_9298 = !cs__627.isValid();
            Supplier<String> fn__9288 = () -> "should be invalid";
            test_30.assert_(t_9298, fn__9288);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1411() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__629 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_9279 = SrcTest.userTable__460();
            SafeIdentifier t_9280 = SrcTest.csid__459("name");
            Changeset cs__630 = SrcGlobal.changeset(t_9279, params__629).cast(List.of(t_9280)).validateLength(SrcTest.csid__459("name"), 2, 10);
            boolean t_9286 = !cs__630.isValid();
            Supplier<String> fn__9276 = () -> "should be invalid";
            test_31.assert_(t_9286, fn__9276);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1412() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__632 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_9268 = SrcTest.userTable__460();
            SafeIdentifier t_9269 = SrcTest.csid__459("age");
            Changeset cs__633 = SrcGlobal.changeset(t_9268, params__632).cast(List.of(t_9269)).validateInt(SrcTest.csid__459("age"));
            boolean t_9273 = cs__633.isValid();
            Supplier<String> fn__9265 = () -> "should be valid";
            test_32.assert_(t_9273, fn__9265);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1413() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__635 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_9256 = SrcTest.userTable__460();
            SafeIdentifier t_9257 = SrcTest.csid__459("age");
            Changeset cs__636 = SrcGlobal.changeset(t_9256, params__635).cast(List.of(t_9257)).validateInt(SrcTest.csid__459("age"));
            boolean t_9263 = !cs__636.isValid();
            Supplier<String> fn__9253 = () -> "should be invalid";
            test_33.assert_(t_9263, fn__9253);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1414() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__638 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_9245 = SrcTest.userTable__460();
            SafeIdentifier t_9246 = SrcTest.csid__459("score");
            Changeset cs__639 = SrcGlobal.changeset(t_9245, params__638).cast(List.of(t_9246)).validateFloat(SrcTest.csid__459("score"));
            boolean t_9250 = cs__639.isValid();
            Supplier<String> fn__9242 = () -> "should be valid";
            test_34.assert_(t_9250, fn__9242);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1415() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__641 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_9234 = SrcTest.userTable__460();
            SafeIdentifier t_9235 = SrcTest.csid__459("age");
            Changeset cs__642 = SrcGlobal.changeset(t_9234, params__641).cast(List.of(t_9235)).validateInt64(SrcTest.csid__459("age"));
            boolean t_9239 = cs__642.isValid();
            Supplier<String> fn__9231 = () -> "should be valid";
            test_35.assert_(t_9239, fn__9231);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1416() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__644 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_9222 = SrcTest.userTable__460();
            SafeIdentifier t_9223 = SrcTest.csid__459("age");
            Changeset cs__645 = SrcGlobal.changeset(t_9222, params__644).cast(List.of(t_9223)).validateInt64(SrcTest.csid__459("age"));
            boolean t_9229 = !cs__645.isValid();
            Supplier<String> fn__9219 = () -> "should be invalid";
            test_36.assert_(t_9229, fn__9219);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1417() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__9216 = v__647 -> {
                Map<String, String> params__648 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__647)));
                TableDef t_9208 = SrcTest.userTable__460();
                SafeIdentifier t_9209 = SrcTest.csid__459("active");
                Changeset cs__649 = SrcGlobal.changeset(t_9208, params__648).cast(List.of(t_9209)).validateBool(SrcTest.csid__459("active"));
                boolean t_9213 = cs__649.isValid();
                Supplier<String> fn__9205 = () -> "should accept: " + v__647;
                test_37.assert_(t_9213, fn__9205);
            };
            List.of("true", "1", "yes", "on").forEach(fn__9216);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1418() {
        Test test_38 = new Test();
        try {
            Consumer<String> fn__9202 = v__651 -> {
                Map<String, String> params__652 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__651)));
                TableDef t_9194 = SrcTest.userTable__460();
                SafeIdentifier t_9195 = SrcTest.csid__459("active");
                Changeset cs__653 = SrcGlobal.changeset(t_9194, params__652).cast(List.of(t_9195)).validateBool(SrcTest.csid__459("active"));
                boolean t_9199 = cs__653.isValid();
                Supplier<String> fn__9191 = () -> "should accept: " + v__651;
                test_38.assert_(t_9199, fn__9191);
            };
            List.of("false", "0", "no", "off").forEach(fn__9202);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1419() {
        Test test_39 = new Test();
        try {
            Consumer<String> fn__9188 = v__655 -> {
                Map<String, String> params__656 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__655)));
                TableDef t_9179 = SrcTest.userTable__460();
                SafeIdentifier t_9180 = SrcTest.csid__459("active");
                Changeset cs__657 = SrcGlobal.changeset(t_9179, params__656).cast(List.of(t_9180)).validateBool(SrcTest.csid__459("active"));
                boolean t_9186 = !cs__657.isValid();
                Supplier<String> fn__9176 = () -> "should reject ambiguous: " + v__655;
                test_39.assert_(t_9186, fn__9176);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__9188);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1420() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__659 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_9164 = SrcTest.userTable__460();
            SafeIdentifier t_9165 = SrcTest.csid__459("name");
            SafeIdentifier t_9166 = SrcTest.csid__459("email");
            Changeset cs__660 = SrcGlobal.changeset(t_9164, params__659).cast(List.of(t_9165, t_9166)).validateRequired(List.of(SrcTest.csid__459("name"), SrcTest.csid__459("email")));
            SqlFragment t_5087;
            t_5087 = cs__660.toInsertSql();
            SqlFragment sqlFrag__661 = t_5087;
            String s__662 = sqlFrag__661.toString();
            boolean t_9173 = s__662.indexOf("''") >= 0;
            Supplier<String> fn__9160 = () -> "single quote must be doubled: " + s__662;
            test_40.assert_(t_9173, fn__9160);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1421() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__664 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_9144 = SrcTest.userTable__460();
            SafeIdentifier t_9145 = SrcTest.csid__459("name");
            SafeIdentifier t_9146 = SrcTest.csid__459("email");
            Changeset cs__665 = SrcGlobal.changeset(t_9144, params__664).cast(List.of(t_9145, t_9146)).validateRequired(List.of(SrcTest.csid__459("name"), SrcTest.csid__459("email")));
            SqlFragment t_5066;
            t_5066 = cs__665.toInsertSql();
            SqlFragment sqlFrag__666 = t_5066;
            String s__667 = sqlFrag__666.toString();
            boolean t_9153 = s__667.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__9140 = () -> "has INSERT INTO: " + s__667;
            test_41.assert_(t_9153, fn__9140);
            boolean t_9157 = s__667.indexOf("'Alice'") >= 0;
            Supplier<String> fn__9139 = () -> "has quoted name: " + s__667;
            test_41.assert_(t_9157, fn__9139);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1422() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__669 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_9126 = SrcTest.userTable__460();
            SafeIdentifier t_9127 = SrcTest.csid__459("name");
            SafeIdentifier t_9128 = SrcTest.csid__459("email");
            SafeIdentifier t_9129 = SrcTest.csid__459("age");
            Changeset cs__670 = SrcGlobal.changeset(t_9126, params__669).cast(List.of(t_9127, t_9128, t_9129)).validateRequired(List.of(SrcTest.csid__459("name"), SrcTest.csid__459("email")));
            SqlFragment t_5049;
            t_5049 = cs__670.toInsertSql();
            SqlFragment sqlFrag__671 = t_5049;
            String s__672 = sqlFrag__671.toString();
            boolean t_9136 = s__672.indexOf("25") >= 0;
            Supplier<String> fn__9121 = () -> "age rendered unquoted: " + s__672;
            test_42.assert_(t_9136, fn__9121);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1423() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__674 = Core.mapConstructor(List.of());
            TableDef t_9114 = SrcTest.userTable__460();
            SafeIdentifier t_9115 = SrcTest.csid__459("name");
            Changeset cs__675 = SrcGlobal.changeset(t_9114, params__674).cast(List.of(t_9115)).validateRequired(List.of(SrcTest.csid__459("name")));
            boolean didBubble__676;
            boolean didBubble_9715;
            try {
                cs__675.toInsertSql();
                didBubble_9715 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_9715 = true;
            }
            didBubble__676 = didBubble_9715;
            Supplier<String> fn__9112 = () -> "invalid changeset should bubble";
            test_43.assert_(didBubble__676, fn__9112);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1424() {
        Test test_44 = new Test();
        try {
            TableDef strictTable__678 = new TableDef(SrcTest.csid__459("posts"), List.of(new FieldDef(SrcTest.csid__459("title"), new StringField(), false), new FieldDef(SrcTest.csid__459("body"), new StringField(), true)));
            Map<String, String> params__679 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_9105 = SrcTest.csid__459("body");
            Changeset cs__680 = SrcGlobal.changeset(strictTable__678, params__679).cast(List.of(t_9105));
            boolean t_9107 = cs__680.isValid();
            Supplier<String> fn__9094 = () -> "changeset should appear valid (no explicit validation run)";
            test_44.assert_(t_9107, fn__9094);
            boolean didBubble__681;
            boolean didBubble_9716;
            try {
                cs__680.toInsertSql();
                didBubble_9716 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_9716 = true;
            }
            didBubble__681 = didBubble_9716;
            Supplier<String> fn__9093 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_44.assert_(didBubble__681, fn__9093);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1425() {
        Test test_45 = new Test();
        try {
            Map<String, String> params__683 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_9084 = SrcTest.userTable__460();
            SafeIdentifier t_9085 = SrcTest.csid__459("name");
            Changeset cs__684 = SrcGlobal.changeset(t_9084, params__683).cast(List.of(t_9085)).validateRequired(List.of(SrcTest.csid__459("name")));
            SqlFragment t_5009;
            t_5009 = cs__684.toUpdateSql(42);
            SqlFragment sqlFrag__685 = t_5009;
            String s__686 = sqlFrag__685.toString();
            boolean t_9091 = s__686.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__9081 = () -> "got: " + s__686;
            test_45.assert_(t_9091, fn__9081);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1426() {
        Test test_46 = new Test();
        try {
            Map<String, String> params__688 = Core.mapConstructor(List.of());
            TableDef t_9074 = SrcTest.userTable__460();
            SafeIdentifier t_9075 = SrcTest.csid__459("name");
            Changeset cs__689 = SrcGlobal.changeset(t_9074, params__688).cast(List.of(t_9075)).validateRequired(List.of(SrcTest.csid__459("name")));
            boolean didBubble__690;
            boolean didBubble_9717;
            try {
                cs__689.toUpdateSql(1);
                didBubble_9717 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_9717 = true;
            }
            didBubble__690 = didBubble_9717;
            Supplier<String> fn__9072 = () -> "invalid changeset should bubble";
            test_46.assert_(didBubble__690, fn__9072);
        } finally {
            test_46.softFailToHard();
        }
    }
    static SafeIdentifier sid__461(String name__932) {
        SafeIdentifier t_4603;
        t_4603 = SrcGlobal.safeIdentifier(name__932);
        return t_4603;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1475() {
        Test test_47 = new Test();
        try {
            Query q__935 = SrcGlobal.from(SrcTest.sid__461("users"));
            boolean t_8681 = q__935.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__8676 = () -> "bare query";
            test_47.assert_(t_8681, fn__8676);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1476() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_8667 = SrcTest.sid__461("users");
            SafeIdentifier t_8668 = SrcTest.sid__461("id");
            SafeIdentifier t_8669 = SrcTest.sid__461("name");
            Query q__937 = SrcGlobal.from(t_8667).select(List.of(t_8668, t_8669));
            boolean t_8674 = q__937.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__8666 = () -> "select columns";
            test_48.assert_(t_8674, fn__8666);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1477() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_8655 = SrcTest.sid__461("users");
            SqlBuilder t_8656 = new SqlBuilder();
            t_8656.appendSafe("age > ");
            t_8656.appendInt32(18);
            SqlFragment t_8659 = t_8656.getAccumulated();
            Query q__939 = SrcGlobal.from(t_8655).where(t_8659);
            boolean t_8664 = q__939.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__8654 = () -> "where int";
            test_49.assert_(t_8664, fn__8654);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1479() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_8643 = SrcTest.sid__461("users");
            SqlBuilder t_8644 = new SqlBuilder();
            t_8644.appendSafe("active = ");
            t_8644.appendBoolean(true);
            SqlFragment t_8647 = t_8644.getAccumulated();
            Query q__941 = SrcGlobal.from(t_8643).where(t_8647);
            boolean t_8652 = q__941.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__8642 = () -> "where bool";
            test_50.assert_(t_8652, fn__8642);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1481() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_8626 = SrcTest.sid__461("users");
            SqlBuilder t_8627 = new SqlBuilder();
            t_8627.appendSafe("age > ");
            t_8627.appendInt32(18);
            SqlFragment t_8630 = t_8627.getAccumulated();
            Query t_8631 = SrcGlobal.from(t_8626).where(t_8630);
            SqlBuilder t_8632 = new SqlBuilder();
            t_8632.appendSafe("active = ");
            t_8632.appendBoolean(true);
            Query q__943 = t_8631.where(t_8632.getAccumulated());
            boolean t_8640 = q__943.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__8625 = () -> "chained where";
            test_51.assert_(t_8640, fn__8625);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1484() {
        Test test_52 = new Test();
        try {
            SafeIdentifier t_8617 = SrcTest.sid__461("users");
            SafeIdentifier t_8618 = SrcTest.sid__461("name");
            Query q__945 = SrcGlobal.from(t_8617).orderBy(t_8618, true);
            boolean t_8623 = q__945.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__8616 = () -> "order asc";
            test_52.assert_(t_8623, fn__8616);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1485() {
        Test test_53 = new Test();
        try {
            SafeIdentifier t_8608 = SrcTest.sid__461("users");
            SafeIdentifier t_8609 = SrcTest.sid__461("created_at");
            Query q__947 = SrcGlobal.from(t_8608).orderBy(t_8609, false);
            boolean t_8614 = q__947.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__8607 = () -> "order desc";
            test_53.assert_(t_8614, fn__8607);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1486() {
        Test test_54 = new Test();
        try {
            Query t_4537;
            t_4537 = SrcGlobal.from(SrcTest.sid__461("users")).limit(10);
            Query t_4538;
            t_4538 = t_4537.offset(20);
            Query q__949 = t_4538;
            boolean t_8605 = q__949.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__8600 = () -> "limit/offset";
            test_54.assert_(t_8605, fn__8600);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1487() {
        Test test_55 = new Test();
        try {
            boolean didBubble__951;
            boolean didBubble_9718;
            try {
                SrcGlobal.from(SrcTest.sid__461("users")).limit(-1);
                didBubble_9718 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_9718 = true;
            }
            didBubble__951 = didBubble_9718;
            Supplier<String> fn__8596 = () -> "negative limit should bubble";
            test_55.assert_(didBubble__951, fn__8596);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1488() {
        Test test_56 = new Test();
        try {
            boolean didBubble__953;
            boolean didBubble_9719;
            try {
                SrcGlobal.from(SrcTest.sid__461("users")).offset(-1);
                didBubble_9719 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_9719 = true;
            }
            didBubble__953 = didBubble_9719;
            Supplier<String> fn__8592 = () -> "negative offset should bubble";
            test_56.assert_(didBubble__953, fn__8592);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1489() {
        Test test_57 = new Test();
        try {
            int minAge__955 = 21;
            SafeIdentifier t_8570 = SrcTest.sid__461("users");
            SafeIdentifier t_8571 = SrcTest.sid__461("id");
            SafeIdentifier t_8572 = SrcTest.sid__461("name");
            SafeIdentifier t_8573 = SrcTest.sid__461("email");
            Query t_8574 = SrcGlobal.from(t_8570).select(List.of(t_8571, t_8572, t_8573));
            SqlBuilder t_8575 = new SqlBuilder();
            t_8575.appendSafe("age >= ");
            t_8575.appendInt32(21);
            Query t_8579 = t_8574.where(t_8575.getAccumulated());
            SqlBuilder t_8580 = new SqlBuilder();
            t_8580.appendSafe("active = ");
            t_8580.appendBoolean(true);
            Query t_4523;
            t_4523 = t_8579.where(t_8580.getAccumulated()).orderBy(SrcTest.sid__461("name"), true).limit(25);
            Query t_4524;
            t_4524 = t_4523.offset(0);
            Query q__956 = t_4524;
            boolean t_8590 = q__956.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__8569 = () -> "complex query";
            test_57.assert_(t_8590, fn__8569);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1492() {
        Test test_58 = new Test();
        try {
            Query q__958 = SrcGlobal.from(SrcTest.sid__461("users"));
            SqlFragment t_4500;
            t_4500 = q__958.safeToSql(100);
            SqlFragment t_4501 = t_4500;
            String s__959 = t_4501.toString();
            boolean t_8567 = s__959.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__8563 = () -> "should have limit: " + s__959;
            test_58.assert_(t_8567, fn__8563);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1493() {
        Test test_59 = new Test();
        try {
            Query t_4492;
            t_4492 = SrcGlobal.from(SrcTest.sid__461("users")).limit(5);
            Query q__961 = t_4492;
            SqlFragment t_4495;
            t_4495 = q__961.safeToSql(100);
            SqlFragment t_4496 = t_4495;
            String s__962 = t_4496.toString();
            boolean t_8561 = s__962.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__8557 = () -> "explicit limit preserved: " + s__962;
            test_59.assert_(t_8561, fn__8557);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1494() {
        Test test_60 = new Test();
        try {
            boolean didBubble__964;
            boolean didBubble_9720;
            try {
                SrcGlobal.from(SrcTest.sid__461("users")).safeToSql(-1);
                didBubble_9720 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_9720 = true;
            }
            didBubble__964 = didBubble_9720;
            Supplier<String> fn__8553 = () -> "negative defaultLimit should bubble";
            test_60.assert_(didBubble__964, fn__8553);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1495() {
        Test test_61 = new Test();
        try {
            String evil__966 = "'; DROP TABLE users; --";
            SafeIdentifier t_8537 = SrcTest.sid__461("users");
            SqlBuilder t_8538 = new SqlBuilder();
            t_8538.appendSafe("name = ");
            t_8538.appendString("'; DROP TABLE users; --");
            SqlFragment t_8541 = t_8538.getAccumulated();
            Query q__967 = SrcGlobal.from(t_8537).where(t_8541);
            String s__968 = q__967.toSql().toString();
            boolean t_8546 = s__968.indexOf("''") >= 0;
            Supplier<String> fn__8536 = () -> "quotes must be doubled: " + s__968;
            test_61.assert_(t_8546, fn__8536);
            boolean t_8550 = s__968.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__8535 = () -> "structure intact: " + s__968;
            test_61.assert_(t_8550, fn__8535);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1497() {
        Test test_62 = new Test();
        try {
            String attack__970 = "users; DROP TABLE users; --";
            boolean didBubble__971;
            boolean didBubble_9721;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_9721 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_9721 = true;
            }
            didBubble__971 = didBubble_9721;
            Supplier<String> fn__8532 = () -> "metacharacter-containing name must be rejected at construction";
            test_62.assert_(didBubble__971, fn__8532);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1498() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_8521 = SrcTest.sid__461("users");
            SafeIdentifier t_8522 = SrcTest.sid__461("orders");
            SqlBuilder t_8523 = new SqlBuilder();
            t_8523.appendSafe("users.id = orders.user_id");
            SqlFragment t_8525 = t_8523.getAccumulated();
            Query q__973 = SrcGlobal.from(t_8521).innerJoin(t_8522, t_8525);
            boolean t_8530 = q__973.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__8520 = () -> "inner join";
            test_63.assert_(t_8530, fn__8520);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1500() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_8509 = SrcTest.sid__461("users");
            SafeIdentifier t_8510 = SrcTest.sid__461("profiles");
            SqlBuilder t_8511 = new SqlBuilder();
            t_8511.appendSafe("users.id = profiles.user_id");
            SqlFragment t_8513 = t_8511.getAccumulated();
            Query q__975 = SrcGlobal.from(t_8509).leftJoin(t_8510, t_8513);
            boolean t_8518 = q__975.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__8508 = () -> "left join";
            test_64.assert_(t_8518, fn__8508);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1502() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_8497 = SrcTest.sid__461("orders");
            SafeIdentifier t_8498 = SrcTest.sid__461("users");
            SqlBuilder t_8499 = new SqlBuilder();
            t_8499.appendSafe("orders.user_id = users.id");
            SqlFragment t_8501 = t_8499.getAccumulated();
            Query q__977 = SrcGlobal.from(t_8497).rightJoin(t_8498, t_8501);
            boolean t_8506 = q__977.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__8496 = () -> "right join";
            test_65.assert_(t_8506, fn__8496);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1504() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_8485 = SrcTest.sid__461("users");
            SafeIdentifier t_8486 = SrcTest.sid__461("orders");
            SqlBuilder t_8487 = new SqlBuilder();
            t_8487.appendSafe("users.id = orders.user_id");
            SqlFragment t_8489 = t_8487.getAccumulated();
            Query q__979 = SrcGlobal.from(t_8485).fullJoin(t_8486, t_8489);
            boolean t_8494 = q__979.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__8484 = () -> "full join";
            test_66.assert_(t_8494, fn__8484);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1506() {
        Test test_67 = new Test();
        try {
            SafeIdentifier t_8468 = SrcTest.sid__461("users");
            SafeIdentifier t_8469 = SrcTest.sid__461("orders");
            SqlBuilder t_8470 = new SqlBuilder();
            t_8470.appendSafe("users.id = orders.user_id");
            SqlFragment t_8472 = t_8470.getAccumulated();
            Query t_8473 = SrcGlobal.from(t_8468).innerJoin(t_8469, t_8472);
            SafeIdentifier t_8474 = SrcTest.sid__461("profiles");
            SqlBuilder t_8475 = new SqlBuilder();
            t_8475.appendSafe("users.id = profiles.user_id");
            Query q__981 = t_8473.leftJoin(t_8474, t_8475.getAccumulated());
            boolean t_8482 = q__981.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__8467 = () -> "chained joins";
            test_67.assert_(t_8482, fn__8467);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1509() {
        Test test_68 = new Test();
        try {
            SafeIdentifier t_8449 = SrcTest.sid__461("users");
            SafeIdentifier t_8450 = SrcTest.sid__461("orders");
            SqlBuilder t_8451 = new SqlBuilder();
            t_8451.appendSafe("users.id = orders.user_id");
            SqlFragment t_8453 = t_8451.getAccumulated();
            Query t_8454 = SrcGlobal.from(t_8449).innerJoin(t_8450, t_8453);
            SqlBuilder t_8455 = new SqlBuilder();
            t_8455.appendSafe("orders.total > ");
            t_8455.appendInt32(100);
            Query t_4407;
            t_4407 = t_8454.where(t_8455.getAccumulated()).orderBy(SrcTest.sid__461("name"), true).limit(10);
            Query q__983 = t_4407;
            boolean t_8465 = q__983.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__8448 = () -> "join with where/order/limit";
            test_68.assert_(t_8465, fn__8448);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1512() {
        Test test_69 = new Test();
        try {
            SqlFragment c__985 = SrcGlobal.col(SrcTest.sid__461("users"), SrcTest.sid__461("id"));
            boolean t_8446 = c__985.toString().equals("users.id");
            Supplier<String> fn__8440 = () -> "col helper";
            test_69.assert_(t_8446, fn__8440);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1513() {
        Test test_70 = new Test();
        try {
            SqlFragment onCond__987 = SrcGlobal.col(SrcTest.sid__461("users"), SrcTest.sid__461("id"));
            SqlBuilder b__988 = new SqlBuilder();
            b__988.appendFragment(onCond__987);
            b__988.appendSafe(" = ");
            b__988.appendFragment(SrcGlobal.col(SrcTest.sid__461("orders"), SrcTest.sid__461("user_id")));
            SafeIdentifier t_8431 = SrcTest.sid__461("users");
            SafeIdentifier t_8432 = SrcTest.sid__461("orders");
            SqlFragment t_8433 = b__988.getAccumulated();
            Query q__989 = SrcGlobal.from(t_8431).innerJoin(t_8432, t_8433);
            boolean t_8438 = q__989.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__8420 = () -> "join with col";
            test_70.assert_(t_8438, fn__8420);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__1514() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_8409 = SrcTest.sid__461("users");
            SqlBuilder t_8410 = new SqlBuilder();
            t_8410.appendSafe("status = ");
            t_8410.appendString("active");
            SqlFragment t_8413 = t_8410.getAccumulated();
            Query q__991 = SrcGlobal.from(t_8409).orWhere(t_8413);
            boolean t_8418 = q__991.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__8408 = () -> "orWhere basic";
            test_71.assert_(t_8418, fn__8408);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__1516() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_8392 = SrcTest.sid__461("users");
            SqlBuilder t_8393 = new SqlBuilder();
            t_8393.appendSafe("age > ");
            t_8393.appendInt32(18);
            SqlFragment t_8396 = t_8393.getAccumulated();
            Query t_8397 = SrcGlobal.from(t_8392).where(t_8396);
            SqlBuilder t_8398 = new SqlBuilder();
            t_8398.appendSafe("vip = ");
            t_8398.appendBoolean(true);
            Query q__993 = t_8397.orWhere(t_8398.getAccumulated());
            boolean t_8406 = q__993.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__8391 = () -> "where then orWhere";
            test_72.assert_(t_8406, fn__8391);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__1519() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_8370 = SrcTest.sid__461("users");
            SqlBuilder t_8371 = new SqlBuilder();
            t_8371.appendSafe("active = ");
            t_8371.appendBoolean(true);
            SqlFragment t_8374 = t_8371.getAccumulated();
            Query t_8375 = SrcGlobal.from(t_8370).where(t_8374);
            SqlBuilder t_8376 = new SqlBuilder();
            t_8376.appendSafe("role = ");
            t_8376.appendString("admin");
            Query t_8380 = t_8375.orWhere(t_8376.getAccumulated());
            SqlBuilder t_8381 = new SqlBuilder();
            t_8381.appendSafe("role = ");
            t_8381.appendString("moderator");
            Query q__995 = t_8380.orWhere(t_8381.getAccumulated());
            boolean t_8389 = q__995.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__8369 = () -> "multiple orWhere";
            test_73.assert_(t_8389, fn__8369);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__1523() {
        Test test_74 = new Test();
        try {
            SafeIdentifier t_8348 = SrcTest.sid__461("users");
            SqlBuilder t_8349 = new SqlBuilder();
            t_8349.appendSafe("age > ");
            t_8349.appendInt32(18);
            SqlFragment t_8352 = t_8349.getAccumulated();
            Query t_8353 = SrcGlobal.from(t_8348).where(t_8352);
            SqlBuilder t_8354 = new SqlBuilder();
            t_8354.appendSafe("active = ");
            t_8354.appendBoolean(true);
            Query t_8358 = t_8353.where(t_8354.getAccumulated());
            SqlBuilder t_8359 = new SqlBuilder();
            t_8359.appendSafe("vip = ");
            t_8359.appendBoolean(true);
            Query q__997 = t_8358.orWhere(t_8359.getAccumulated());
            boolean t_8367 = q__997.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__8347 = () -> "mixed where and orWhere";
            test_74.assert_(t_8367, fn__8347);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__1527() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_8339 = SrcTest.sid__461("users");
            SafeIdentifier t_8340 = SrcTest.sid__461("deleted_at");
            Query q__999 = SrcGlobal.from(t_8339).whereNull(t_8340);
            boolean t_8345 = q__999.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__8338 = () -> "whereNull";
            test_75.assert_(t_8345, fn__8338);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__1528() {
        Test test_76 = new Test();
        try {
            SafeIdentifier t_8330 = SrcTest.sid__461("users");
            SafeIdentifier t_8331 = SrcTest.sid__461("email");
            Query q__1001 = SrcGlobal.from(t_8330).whereNotNull(t_8331);
            boolean t_8336 = q__1001.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__8329 = () -> "whereNotNull";
            test_76.assert_(t_8336, fn__8329);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__1529() {
        Test test_77 = new Test();
        try {
            SafeIdentifier t_8316 = SrcTest.sid__461("users");
            SqlBuilder t_8317 = new SqlBuilder();
            t_8317.appendSafe("active = ");
            t_8317.appendBoolean(true);
            SqlFragment t_8320 = t_8317.getAccumulated();
            Query q__1003 = SrcGlobal.from(t_8316).where(t_8320).whereNull(SrcTest.sid__461("deleted_at"));
            boolean t_8327 = q__1003.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__8315 = () -> "whereNull chained";
            test_77.assert_(t_8327, fn__8315);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__1531() {
        Test test_78 = new Test();
        try {
            SafeIdentifier t_8302 = SrcTest.sid__461("users");
            SafeIdentifier t_8303 = SrcTest.sid__461("deleted_at");
            Query t_8304 = SrcGlobal.from(t_8302).whereNull(t_8303);
            SqlBuilder t_8305 = new SqlBuilder();
            t_8305.appendSafe("role = ");
            t_8305.appendString("admin");
            Query q__1005 = t_8304.orWhere(t_8305.getAccumulated());
            boolean t_8313 = q__1005.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__8301 = () -> "whereNotNull with orWhere";
            test_78.assert_(t_8313, fn__8301);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__1533() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_8290 = SrcTest.sid__461("users");
            SafeIdentifier t_8291 = SrcTest.sid__461("id");
            SqlInt32 t_8292 = new SqlInt32(1);
            SqlInt32 t_8293 = new SqlInt32(2);
            SqlInt32 t_8294 = new SqlInt32(3);
            Query q__1007 = SrcGlobal.from(t_8290).whereIn(t_8291, List.of(t_8292, t_8293, t_8294));
            boolean t_8299 = q__1007.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__8289 = () -> "whereIn ints";
            test_79.assert_(t_8299, fn__8289);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__1534() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_8279 = SrcTest.sid__461("users");
            SafeIdentifier t_8280 = SrcTest.sid__461("name");
            SqlString t_8281 = new SqlString("Alice");
            SqlString t_8282 = new SqlString("Bob's");
            Query q__1009 = SrcGlobal.from(t_8279).whereIn(t_8280, List.of(t_8281, t_8282));
            boolean t_8287 = q__1009.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__8278 = () -> "whereIn strings";
            test_80.assert_(t_8287, fn__8278);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__1535() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_8270 = SrcTest.sid__461("users");
            SafeIdentifier t_8271 = SrcTest.sid__461("id");
            Query q__1011 = SrcGlobal.from(t_8270).whereIn(t_8271, List.of());
            boolean t_8276 = q__1011.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__8269 = () -> "whereIn empty";
            test_81.assert_(t_8276, fn__8269);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__1536() {
        Test test_82 = new Test();
        try {
            SafeIdentifier t_8254 = SrcTest.sid__461("users");
            SqlBuilder t_8255 = new SqlBuilder();
            t_8255.appendSafe("active = ");
            t_8255.appendBoolean(true);
            SqlFragment t_8258 = t_8255.getAccumulated();
            Query q__1013 = SrcGlobal.from(t_8254).where(t_8258).whereIn(SrcTest.sid__461("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_8267 = q__1013.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__8253 = () -> "whereIn chained";
            test_82.assert_(t_8267, fn__8253);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__1538() {
        Test test_83 = new Test();
        try {
            SafeIdentifier t_8244 = SrcTest.sid__461("users");
            SafeIdentifier t_8245 = SrcTest.sid__461("id");
            SqlInt32 t_8246 = new SqlInt32(42);
            Query q__1015 = SrcGlobal.from(t_8244).whereIn(t_8245, List.of(t_8246));
            boolean t_8251 = q__1015.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__8243 = () -> "whereIn single";
            test_83.assert_(t_8251, fn__8243);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__1539() {
        Test test_84 = new Test();
        try {
            SafeIdentifier t_8232 = SrcTest.sid__461("users");
            SqlBuilder t_8233 = new SqlBuilder();
            t_8233.appendSafe("active = ");
            t_8233.appendBoolean(true);
            SqlFragment t_8236 = t_8233.getAccumulated();
            Query q__1017 = SrcGlobal.from(t_8232).whereNot(t_8236);
            boolean t_8241 = q__1017.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__8231 = () -> "whereNot";
            test_84.assert_(t_8241, fn__8231);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__1541() {
        Test test_85 = new Test();
        try {
            SafeIdentifier t_8215 = SrcTest.sid__461("users");
            SqlBuilder t_8216 = new SqlBuilder();
            t_8216.appendSafe("age > ");
            t_8216.appendInt32(18);
            SqlFragment t_8219 = t_8216.getAccumulated();
            Query t_8220 = SrcGlobal.from(t_8215).where(t_8219);
            SqlBuilder t_8221 = new SqlBuilder();
            t_8221.appendSafe("banned = ");
            t_8221.appendBoolean(true);
            Query q__1019 = t_8220.whereNot(t_8221.getAccumulated());
            boolean t_8229 = q__1019.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__8214 = () -> "whereNot chained";
            test_85.assert_(t_8229, fn__8214);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__1544() {
        Test test_86 = new Test();
        try {
            SafeIdentifier t_8204 = SrcTest.sid__461("users");
            SafeIdentifier t_8205 = SrcTest.sid__461("age");
            SqlInt32 t_8206 = new SqlInt32(18);
            SqlInt32 t_8207 = new SqlInt32(65);
            Query q__1021 = SrcGlobal.from(t_8204).whereBetween(t_8205, t_8206, t_8207);
            boolean t_8212 = q__1021.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__8203 = () -> "whereBetween ints";
            test_86.assert_(t_8212, fn__8203);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__1545() {
        Test test_87 = new Test();
        try {
            SafeIdentifier t_8188 = SrcTest.sid__461("users");
            SqlBuilder t_8189 = new SqlBuilder();
            t_8189.appendSafe("active = ");
            t_8189.appendBoolean(true);
            SqlFragment t_8192 = t_8189.getAccumulated();
            Query q__1023 = SrcGlobal.from(t_8188).where(t_8192).whereBetween(SrcTest.sid__461("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_8201 = q__1023.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__8187 = () -> "whereBetween chained";
            test_87.assert_(t_8201, fn__8187);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__1547() {
        Test test_88 = new Test();
        try {
            SafeIdentifier t_8179 = SrcTest.sid__461("users");
            SafeIdentifier t_8180 = SrcTest.sid__461("name");
            Query q__1025 = SrcGlobal.from(t_8179).whereLike(t_8180, "John%");
            boolean t_8185 = q__1025.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__8178 = () -> "whereLike";
            test_88.assert_(t_8185, fn__8178);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__1548() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_8170 = SrcTest.sid__461("users");
            SafeIdentifier t_8171 = SrcTest.sid__461("email");
            Query q__1027 = SrcGlobal.from(t_8170).whereILike(t_8171, "%@gmail.com");
            boolean t_8176 = q__1027.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__8169 = () -> "whereILike";
            test_89.assert_(t_8176, fn__8169);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__1549() {
        Test test_90 = new Test();
        try {
            SafeIdentifier t_8156 = SrcTest.sid__461("users");
            SafeIdentifier t_8157 = SrcTest.sid__461("name");
            Query q__1029 = SrcGlobal.from(t_8156).whereLike(t_8157, "'; DROP TABLE users; --");
            String s__1030 = q__1029.toSql().toString();
            boolean t_8162 = s__1030.indexOf("''") >= 0;
            Supplier<String> fn__8155 = () -> "like injection escaped: " + s__1030;
            test_90.assert_(t_8162, fn__8155);
            boolean t_8166 = s__1030.indexOf("LIKE") >= 0;
            Supplier<String> fn__8154 = () -> "like structure intact: " + s__1030;
            test_90.assert_(t_8166, fn__8154);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__1550() {
        Test test_91 = new Test();
        try {
            SafeIdentifier t_8146 = SrcTest.sid__461("users");
            SafeIdentifier t_8147 = SrcTest.sid__461("name");
            Query q__1032 = SrcGlobal.from(t_8146).whereLike(t_8147, "%son%");
            boolean t_8152 = q__1032.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__8145 = () -> "whereLike wildcard";
            test_91.assert_(t_8152, fn__8145);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__1551() {
        Test test_92 = new Test();
        try {
            SqlFragment f__1034 = SrcGlobal.countAll();
            boolean t_8143 = f__1034.toString().equals("COUNT(*)");
            Supplier<String> fn__8139 = () -> "countAll";
            test_92.assert_(t_8143, fn__8139);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__1552() {
        Test test_93 = new Test();
        try {
            SqlFragment f__1036 = SrcGlobal.countCol(SrcTest.sid__461("id"));
            boolean t_8137 = f__1036.toString().equals("COUNT(id)");
            Supplier<String> fn__8132 = () -> "countCol";
            test_93.assert_(t_8137, fn__8132);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__1553() {
        Test test_94 = new Test();
        try {
            SqlFragment f__1038 = SrcGlobal.sumCol(SrcTest.sid__461("amount"));
            boolean t_8130 = f__1038.toString().equals("SUM(amount)");
            Supplier<String> fn__8125 = () -> "sumCol";
            test_94.assert_(t_8130, fn__8125);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__1554() {
        Test test_95 = new Test();
        try {
            SqlFragment f__1040 = SrcGlobal.avgCol(SrcTest.sid__461("price"));
            boolean t_8123 = f__1040.toString().equals("AVG(price)");
            Supplier<String> fn__8118 = () -> "avgCol";
            test_95.assert_(t_8123, fn__8118);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__1555() {
        Test test_96 = new Test();
        try {
            SqlFragment f__1042 = SrcGlobal.minCol(SrcTest.sid__461("created_at"));
            boolean t_8116 = f__1042.toString().equals("MIN(created_at)");
            Supplier<String> fn__8111 = () -> "minCol";
            test_96.assert_(t_8116, fn__8111);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__1556() {
        Test test_97 = new Test();
        try {
            SqlFragment f__1044 = SrcGlobal.maxCol(SrcTest.sid__461("score"));
            boolean t_8109 = f__1044.toString().equals("MAX(score)");
            Supplier<String> fn__8104 = () -> "maxCol";
            test_97.assert_(t_8109, fn__8104);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__1557() {
        Test test_98 = new Test();
        try {
            SafeIdentifier t_8096 = SrcTest.sid__461("orders");
            SqlFragment t_8097 = SrcGlobal.countAll();
            Query q__1046 = SrcGlobal.from(t_8096).selectExpr(List.of(t_8097));
            boolean t_8102 = q__1046.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__8095 = () -> "selectExpr count";
            test_98.assert_(t_8102, fn__8095);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__1558() {
        Test test_99 = new Test();
        try {
            SqlFragment nameFrag__1048 = SrcGlobal.col(SrcTest.sid__461("users"), SrcTest.sid__461("name"));
            SafeIdentifier t_8087 = SrcTest.sid__461("users");
            SqlFragment t_8088 = SrcGlobal.countAll();
            Query q__1049 = SrcGlobal.from(t_8087).selectExpr(List.of(nameFrag__1048, t_8088));
            boolean t_8093 = q__1049.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__8083 = () -> "selectExpr multi";
            test_99.assert_(t_8093, fn__8083);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__1559() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_8072 = SrcTest.sid__461("users");
            SafeIdentifier t_8073 = SrcTest.sid__461("id");
            SafeIdentifier t_8074 = SrcTest.sid__461("name");
            Query q__1051 = SrcGlobal.from(t_8072).select(List.of(t_8073, t_8074)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_8081 = q__1051.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__8071 = () -> "selectExpr overrides select";
            test_100.assert_(t_8081, fn__8071);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__1560() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_8058 = SrcTest.sid__461("orders");
            SqlFragment t_8061 = SrcGlobal.col(SrcTest.sid__461("orders"), SrcTest.sid__461("status"));
            SqlFragment t_8062 = SrcGlobal.countAll();
            Query q__1053 = SrcGlobal.from(t_8058).selectExpr(List.of(t_8061, t_8062)).groupBy(SrcTest.sid__461("status"));
            boolean t_8069 = q__1053.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__8057 = () -> "groupBy single";
            test_101.assert_(t_8069, fn__8057);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__1561() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_8047 = SrcTest.sid__461("orders");
            SafeIdentifier t_8048 = SrcTest.sid__461("status");
            Query q__1055 = SrcGlobal.from(t_8047).groupBy(t_8048).groupBy(SrcTest.sid__461("category"));
            boolean t_8055 = q__1055.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__8046 = () -> "groupBy multiple";
            test_102.assert_(t_8055, fn__8046);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__1562() {
        Test test_103 = new Test();
        try {
            SafeIdentifier t_8028 = SrcTest.sid__461("orders");
            SqlFragment t_8031 = SrcGlobal.col(SrcTest.sid__461("orders"), SrcTest.sid__461("status"));
            SqlFragment t_8032 = SrcGlobal.countAll();
            Query t_8035 = SrcGlobal.from(t_8028).selectExpr(List.of(t_8031, t_8032)).groupBy(SrcTest.sid__461("status"));
            SqlBuilder t_8036 = new SqlBuilder();
            t_8036.appendSafe("COUNT(*) > ");
            t_8036.appendInt32(5);
            Query q__1057 = t_8035.having(t_8036.getAccumulated());
            boolean t_8044 = q__1057.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__8027 = () -> "having basic";
            test_103.assert_(t_8044, fn__8027);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__1564() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_8009 = SrcTest.sid__461("orders");
            SafeIdentifier t_8010 = SrcTest.sid__461("status");
            Query t_8011 = SrcGlobal.from(t_8009).groupBy(t_8010);
            SqlBuilder t_8012 = new SqlBuilder();
            t_8012.appendSafe("COUNT(*) > ");
            t_8012.appendInt32(5);
            Query t_8016 = t_8011.having(t_8012.getAccumulated());
            SqlBuilder t_8017 = new SqlBuilder();
            t_8017.appendSafe("SUM(total) > ");
            t_8017.appendInt32(1000);
            Query q__1059 = t_8016.orHaving(t_8017.getAccumulated());
            boolean t_8025 = q__1059.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__8008 = () -> "orHaving";
            test_104.assert_(t_8025, fn__8008);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__1567() {
        Test test_105 = new Test();
        try {
            SafeIdentifier t_7999 = SrcTest.sid__461("users");
            SafeIdentifier t_8000 = SrcTest.sid__461("name");
            Query q__1061 = SrcGlobal.from(t_7999).select(List.of(t_8000)).distinct();
            boolean t_8006 = q__1061.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__7998 = () -> "distinct";
            test_105.assert_(t_8006, fn__7998);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__1568() {
        Test test_106 = new Test();
        try {
            SafeIdentifier t_7984 = SrcTest.sid__461("users");
            SafeIdentifier t_7985 = SrcTest.sid__461("email");
            Query t_7986 = SrcGlobal.from(t_7984).select(List.of(t_7985));
            SqlBuilder t_7987 = new SqlBuilder();
            t_7987.appendSafe("active = ");
            t_7987.appendBoolean(true);
            Query q__1063 = t_7986.where(t_7987.getAccumulated()).distinct();
            boolean t_7996 = q__1063.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__7983 = () -> "distinct with where";
            test_106.assert_(t_7996, fn__7983);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__1570() {
        Test test_107 = new Test();
        try {
            Query q__1065 = SrcGlobal.from(SrcTest.sid__461("users"));
            boolean t_7981 = q__1065.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__7976 = () -> "countSql bare";
            test_107.assert_(t_7981, fn__7976);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__1571() {
        Test test_108 = new Test();
        try {
            SafeIdentifier t_7965 = SrcTest.sid__461("users");
            SqlBuilder t_7966 = new SqlBuilder();
            t_7966.appendSafe("active = ");
            t_7966.appendBoolean(true);
            SqlFragment t_7969 = t_7966.getAccumulated();
            Query q__1067 = SrcGlobal.from(t_7965).where(t_7969);
            boolean t_7974 = q__1067.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__7964 = () -> "countSql with where";
            test_108.assert_(t_7974, fn__7964);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__1573() {
        Test test_109 = new Test();
        try {
            SafeIdentifier t_7948 = SrcTest.sid__461("users");
            SafeIdentifier t_7949 = SrcTest.sid__461("orders");
            SqlBuilder t_7950 = new SqlBuilder();
            t_7950.appendSafe("users.id = orders.user_id");
            SqlFragment t_7952 = t_7950.getAccumulated();
            Query t_7953 = SrcGlobal.from(t_7948).innerJoin(t_7949, t_7952);
            SqlBuilder t_7954 = new SqlBuilder();
            t_7954.appendSafe("orders.total > ");
            t_7954.appendInt32(100);
            Query q__1069 = t_7953.where(t_7954.getAccumulated());
            boolean t_7962 = q__1069.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__7947 = () -> "countSql with join";
            test_109.assert_(t_7962, fn__7947);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__1576() {
        Test test_110 = new Test();
        try {
            SafeIdentifier t_7934 = SrcTest.sid__461("users");
            SqlBuilder t_7935 = new SqlBuilder();
            t_7935.appendSafe("active = ");
            t_7935.appendBoolean(true);
            SqlFragment t_7938 = t_7935.getAccumulated();
            Query t_3983;
            t_3983 = SrcGlobal.from(t_7934).where(t_7938).orderBy(SrcTest.sid__461("name"), true).limit(10);
            Query t_3984;
            t_3984 = t_3983.offset(20);
            Query q__1071 = t_3984;
            String s__1072 = q__1071.countSql().toString();
            boolean t_7945 = s__1072.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__7933 = () -> "countSql drops extras: " + s__1072;
            test_110.assert_(t_7945, fn__7933);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__1578() {
        Test test_111 = new Test();
        try {
            SafeIdentifier t_7901 = SrcTest.sid__461("orders");
            SqlFragment t_7904 = SrcGlobal.col(SrcTest.sid__461("orders"), SrcTest.sid__461("status"));
            SqlFragment t_7905 = SrcGlobal.countAll();
            SqlFragment t_7907 = SrcGlobal.sumCol(SrcTest.sid__461("total"));
            Query t_7908 = SrcGlobal.from(t_7901).selectExpr(List.of(t_7904, t_7905, t_7907));
            SafeIdentifier t_7909 = SrcTest.sid__461("users");
            SqlBuilder t_7910 = new SqlBuilder();
            t_7910.appendSafe("orders.user_id = users.id");
            Query t_7913 = t_7908.innerJoin(t_7909, t_7910.getAccumulated());
            SqlBuilder t_7914 = new SqlBuilder();
            t_7914.appendSafe("users.active = ");
            t_7914.appendBoolean(true);
            Query t_7920 = t_7913.where(t_7914.getAccumulated()).groupBy(SrcTest.sid__461("status"));
            SqlBuilder t_7921 = new SqlBuilder();
            t_7921.appendSafe("COUNT(*) > ");
            t_7921.appendInt32(3);
            Query q__1074 = t_7920.having(t_7921.getAccumulated()).orderBy(SrcTest.sid__461("status"), true);
            String expected__1075 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_7931 = q__1074.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__7900 = () -> "full aggregation";
            test_111.assert_(t_7931, fn__7900);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionSql__1582() {
        Test test_112 = new Test();
        try {
            SafeIdentifier t_7883 = SrcTest.sid__461("users");
            SqlBuilder t_7884 = new SqlBuilder();
            t_7884.appendSafe("role = ");
            t_7884.appendString("admin");
            SqlFragment t_7887 = t_7884.getAccumulated();
            Query a__1077 = SrcGlobal.from(t_7883).where(t_7887);
            SafeIdentifier t_7889 = SrcTest.sid__461("users");
            SqlBuilder t_7890 = new SqlBuilder();
            t_7890.appendSafe("role = ");
            t_7890.appendString("moderator");
            SqlFragment t_7893 = t_7890.getAccumulated();
            Query b__1078 = SrcGlobal.from(t_7889).where(t_7893);
            String s__1079 = SrcGlobal.unionSql(a__1077, b__1078).toString();
            boolean t_7898 = s__1079.equals("(SELECT * FROM users WHERE role = 'admin') UNION (SELECT * FROM users WHERE role = 'moderator')");
            Supplier<String> fn__7882 = () -> "unionSql: " + s__1079;
            test_112.assert_(t_7898, fn__7882);
        } finally {
            test_112.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void unionAllSql__1585() {
        Test test_113 = new Test();
        try {
            SafeIdentifier t_7871 = SrcTest.sid__461("users");
            SafeIdentifier t_7872 = SrcTest.sid__461("name");
            Query a__1081 = SrcGlobal.from(t_7871).select(List.of(t_7872));
            SafeIdentifier t_7874 = SrcTest.sid__461("contacts");
            SafeIdentifier t_7875 = SrcTest.sid__461("name");
            Query b__1082 = SrcGlobal.from(t_7874).select(List.of(t_7875));
            String s__1083 = SrcGlobal.unionAllSql(a__1081, b__1082).toString();
            boolean t_7880 = s__1083.equals("(SELECT name FROM users) UNION ALL (SELECT name FROM contacts)");
            Supplier<String> fn__7870 = () -> "unionAllSql: " + s__1083;
            test_113.assert_(t_7880, fn__7870);
        } finally {
            test_113.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void intersectSql__1586() {
        Test test_114 = new Test();
        try {
            SafeIdentifier t_7859 = SrcTest.sid__461("users");
            SafeIdentifier t_7860 = SrcTest.sid__461("email");
            Query a__1085 = SrcGlobal.from(t_7859).select(List.of(t_7860));
            SafeIdentifier t_7862 = SrcTest.sid__461("subscribers");
            SafeIdentifier t_7863 = SrcTest.sid__461("email");
            Query b__1086 = SrcGlobal.from(t_7862).select(List.of(t_7863));
            String s__1087 = SrcGlobal.intersectSql(a__1085, b__1086).toString();
            boolean t_7868 = s__1087.equals("(SELECT email FROM users) INTERSECT (SELECT email FROM subscribers)");
            Supplier<String> fn__7858 = () -> "intersectSql: " + s__1087;
            test_114.assert_(t_7868, fn__7858);
        } finally {
            test_114.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void exceptSql__1587() {
        Test test_115 = new Test();
        try {
            SafeIdentifier t_7847 = SrcTest.sid__461("users");
            SafeIdentifier t_7848 = SrcTest.sid__461("id");
            Query a__1089 = SrcGlobal.from(t_7847).select(List.of(t_7848));
            SafeIdentifier t_7850 = SrcTest.sid__461("banned");
            SafeIdentifier t_7851 = SrcTest.sid__461("id");
            Query b__1090 = SrcGlobal.from(t_7850).select(List.of(t_7851));
            String s__1091 = SrcGlobal.exceptSql(a__1089, b__1090).toString();
            boolean t_7856 = s__1091.equals("(SELECT id FROM users) EXCEPT (SELECT id FROM banned)");
            Supplier<String> fn__7846 = () -> "exceptSql: " + s__1091;
            test_115.assert_(t_7856, fn__7846);
        } finally {
            test_115.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void subqueryWithAlias__1588() {
        Test test_116 = new Test();
        try {
            SafeIdentifier t_7832 = SrcTest.sid__461("orders");
            SafeIdentifier t_7833 = SrcTest.sid__461("user_id");
            Query t_7834 = SrcGlobal.from(t_7832).select(List.of(t_7833));
            SqlBuilder t_7835 = new SqlBuilder();
            t_7835.appendSafe("total > ");
            t_7835.appendInt32(100);
            Query inner__1093 = t_7834.where(t_7835.getAccumulated());
            String s__1094 = SrcGlobal.subquery(inner__1093, SrcTest.sid__461("big_orders")).toString();
            boolean t_7844 = s__1094.equals("(SELECT user_id FROM orders WHERE total > 100) AS big_orders");
            Supplier<String> fn__7831 = () -> "subquery: " + s__1094;
            test_116.assert_(t_7844, fn__7831);
        } finally {
            test_116.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSql__1590() {
        Test test_117 = new Test();
        try {
            SafeIdentifier t_7821 = SrcTest.sid__461("orders");
            SqlBuilder t_7822 = new SqlBuilder();
            t_7822.appendSafe("orders.user_id = users.id");
            SqlFragment t_7824 = t_7822.getAccumulated();
            Query inner__1096 = SrcGlobal.from(t_7821).where(t_7824);
            String s__1097 = SrcGlobal.existsSql(inner__1096).toString();
            boolean t_7829 = s__1097.equals("EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__7820 = () -> "existsSql: " + s__1097;
            test_117.assert_(t_7829, fn__7820);
        } finally {
            test_117.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubquery__1592() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_7804 = SrcTest.sid__461("orders");
            SafeIdentifier t_7805 = SrcTest.sid__461("user_id");
            Query t_7806 = SrcGlobal.from(t_7804).select(List.of(t_7805));
            SqlBuilder t_7807 = new SqlBuilder();
            t_7807.appendSafe("total > ");
            t_7807.appendInt32(1000);
            Query sub__1099 = t_7806.where(t_7807.getAccumulated());
            SafeIdentifier t_7812 = SrcTest.sid__461("users");
            SafeIdentifier t_7813 = SrcTest.sid__461("id");
            Query q__1100 = SrcGlobal.from(t_7812).whereInSubquery(t_7813, sub__1099);
            String s__1101 = q__1100.toSql().toString();
            boolean t_7818 = s__1101.equals("SELECT * FROM users WHERE id IN (SELECT user_id FROM orders WHERE total > 1000)");
            Supplier<String> fn__7803 = () -> "whereInSubquery: " + s__1101;
            test_118.assert_(t_7818, fn__7803);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void setOperationWithWhereOnEachSide__1594() {
        Test test_119 = new Test();
        try {
            SafeIdentifier t_7781 = SrcTest.sid__461("users");
            SqlBuilder t_7782 = new SqlBuilder();
            t_7782.appendSafe("age > ");
            t_7782.appendInt32(18);
            SqlFragment t_7785 = t_7782.getAccumulated();
            Query t_7786 = SrcGlobal.from(t_7781).where(t_7785);
            SqlBuilder t_7787 = new SqlBuilder();
            t_7787.appendSafe("active = ");
            t_7787.appendBoolean(true);
            Query a__1103 = t_7786.where(t_7787.getAccumulated());
            SafeIdentifier t_7792 = SrcTest.sid__461("users");
            SqlBuilder t_7793 = new SqlBuilder();
            t_7793.appendSafe("role = ");
            t_7793.appendString("vip");
            SqlFragment t_7796 = t_7793.getAccumulated();
            Query b__1104 = SrcGlobal.from(t_7792).where(t_7796);
            String s__1105 = SrcGlobal.unionSql(a__1103, b__1104).toString();
            boolean t_7801 = s__1105.equals("(SELECT * FROM users WHERE age > 18 AND active = TRUE) UNION (SELECT * FROM users WHERE role = 'vip')");
            Supplier<String> fn__7780 = () -> "union with where: " + s__1105;
            test_119.assert_(t_7801, fn__7780);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSubqueryChainedWithWhere__1598() {
        Test test_120 = new Test();
        try {
            SafeIdentifier t_7764 = SrcTest.sid__461("orders");
            SafeIdentifier t_7765 = SrcTest.sid__461("user_id");
            Query sub__1107 = SrcGlobal.from(t_7764).select(List.of(t_7765));
            SafeIdentifier t_7767 = SrcTest.sid__461("users");
            SqlBuilder t_7768 = new SqlBuilder();
            t_7768.appendSafe("active = ");
            t_7768.appendBoolean(true);
            SqlFragment t_7771 = t_7768.getAccumulated();
            Query q__1108 = SrcGlobal.from(t_7767).where(t_7771).whereInSubquery(SrcTest.sid__461("id"), sub__1107);
            String s__1109 = q__1108.toSql().toString();
            boolean t_7778 = s__1109.equals("SELECT * FROM users WHERE active = TRUE AND id IN (SELECT user_id FROM orders)");
            Supplier<String> fn__7763 = () -> "whereInSubquery chained: " + s__1109;
            test_120.assert_(t_7778, fn__7763);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void existsSqlUsedInWhere__1600() {
        Test test_121 = new Test();
        try {
            SafeIdentifier t_7750 = SrcTest.sid__461("orders");
            SqlBuilder t_7751 = new SqlBuilder();
            t_7751.appendSafe("orders.user_id = users.id");
            SqlFragment t_7753 = t_7751.getAccumulated();
            Query sub__1111 = SrcGlobal.from(t_7750).where(t_7753);
            SafeIdentifier t_7755 = SrcTest.sid__461("users");
            SqlFragment t_7756 = SrcGlobal.existsSql(sub__1111);
            Query q__1112 = SrcGlobal.from(t_7755).where(t_7756);
            String s__1113 = q__1112.toSql().toString();
            boolean t_7761 = s__1113.equals("SELECT * FROM users WHERE EXISTS (SELECT * FROM orders WHERE orders.user_id = users.id)");
            Supplier<String> fn__7749 = () -> "exists in where: " + s__1113;
            test_121.assert_(t_7761, fn__7749);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1602() {
        Test test_128 = new Test();
        try {
            SafeIdentifier t_3806;
            t_3806 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1151 = t_3806;
            boolean t_7747 = id__1151.getSqlValue().equals("user_name");
            Supplier<String> fn__7744 = () -> "value should round-trip";
            test_128.assert_(t_7747, fn__7744);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1603() {
        Test test_129 = new Test();
        try {
            boolean didBubble__1153;
            boolean didBubble_9722;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_9722 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_9722 = true;
            }
            didBubble__1153 = didBubble_9722;
            Supplier<String> fn__7741 = () -> "empty string should bubble";
            test_129.assert_(didBubble__1153, fn__7741);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1604() {
        Test test_130 = new Test();
        try {
            boolean didBubble__1155;
            boolean didBubble_9723;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_9723 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_9723 = true;
            }
            didBubble__1155 = didBubble_9723;
            Supplier<String> fn__7738 = () -> "leading digit should bubble";
            test_130.assert_(didBubble__1155, fn__7738);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1605() {
        Test test_131 = new Test();
        try {
            List<String> cases__1157 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__7735 = c__1158 -> {
                boolean didBubble__1159;
                boolean didBubble_9724;
                try {
                    SrcGlobal.safeIdentifier(c__1158);
                    didBubble_9724 = false;
                } catch (RuntimeException ignored$13) {
                    didBubble_9724 = true;
                }
                didBubble__1159 = didBubble_9724;
                Supplier<String> fn__7732 = () -> "should reject: " + c__1158;
                test_131.assert_(didBubble__1159, fn__7732);
            };
            cases__1157.forEach(fn__7735);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1606() {
        Test test_132 = new Test();
        try {
            SafeIdentifier t_3783;
            t_3783 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_3784 = t_3783;
            SafeIdentifier t_3785;
            t_3785 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_3786 = t_3785;
            StringField t_7722 = new StringField();
            FieldDef t_7723 = new FieldDef(t_3786, t_7722, false);
            SafeIdentifier t_3789;
            t_3789 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_3790 = t_3789;
            IntField t_7724 = new IntField();
            FieldDef t_7725 = new FieldDef(t_3790, t_7724, false);
            TableDef td__1161 = new TableDef(t_3784, List.of(t_7723, t_7725));
            FieldDef t_3794;
            t_3794 = td__1161.field("age");
            FieldDef f__1162 = t_3794;
            boolean t_7730 = f__1162.getName().getSqlValue().equals("age");
            Supplier<String> fn__7721 = () -> "should find age field";
            test_132.assert_(t_7730, fn__7721);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1607() {
        Test test_133 = new Test();
        try {
            SafeIdentifier t_3774;
            t_3774 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_3775 = t_3774;
            SafeIdentifier t_3776;
            t_3776 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_3777 = t_3776;
            StringField t_7716 = new StringField();
            FieldDef t_7717 = new FieldDef(t_3777, t_7716, false);
            TableDef td__1164 = new TableDef(t_3775, List.of(t_7717));
            boolean didBubble__1165;
            boolean didBubble_9725;
            try {
                td__1164.field("nonexistent");
                didBubble_9725 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_9725 = true;
            }
            didBubble__1165 = didBubble_9725;
            Supplier<String> fn__7715 = () -> "unknown field should bubble";
            test_133.assert_(didBubble__1165, fn__7715);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1608() {
        Test test_134 = new Test();
        try {
            SafeIdentifier t_3762;
            t_3762 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_3763 = t_3762;
            StringField t_7704 = new StringField();
            FieldDef required__1167 = new FieldDef(t_3763, t_7704, false);
            SafeIdentifier t_3766;
            t_3766 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_3767 = t_3766;
            StringField t_7706 = new StringField();
            FieldDef optional__1168 = new FieldDef(t_3767, t_7706, true);
            boolean t_7710 = !required__1167.isNullable();
            Supplier<String> fn__7703 = () -> "required field should not be nullable";
            test_134.assert_(t_7710, fn__7703);
            boolean t_7712 = optional__1168.isNullable();
            Supplier<String> fn__7702 = () -> "optional field should be nullable";
            test_134.assert_(t_7712, fn__7702);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1609() {
        Test test_138 = new Test();
        try {
            Function<String, String> build__1294 = name__1296 -> {
                SqlBuilder t_7684 = new SqlBuilder();
                t_7684.appendSafe("select * from hi where name = ");
                t_7684.appendString(name__1296);
                return t_7684.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1295 = name__1298 -> "select * from hi where name = '" + name__1298 + "'";
            String actual_1611 = build__1294.apply("world");
            boolean t_7694 = actual_1611.equals("select * from hi where name = 'world'");
            Supplier<String> fn__7691 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1611 + ")";
            test_138.assert_(t_7694, fn__7691);
            String bobbyTables__1300 = "Robert'); drop table hi;--";
            String actual_1613 = build__1294.apply("Robert'); drop table hi;--");
            boolean t_7698 = actual_1613.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__7690 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1613 + ")";
            test_138.assert_(t_7698, fn__7690);
            Supplier<String> fn__7689 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_138.assert_(true, fn__7689);
        } finally {
            test_138.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1617() {
        Test test_139 = new Test();
        try {
            SqlBuilder t_7652 = new SqlBuilder();
            t_7652.appendSafe("v = ");
            t_7652.appendString("");
            String actual_1618 = t_7652.getAccumulated().toString();
            boolean t_7658 = actual_1618.equals("v = ''");
            Supplier<String> fn__7651 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1618 + ")";
            test_139.assert_(t_7658, fn__7651);
            SqlBuilder t_7660 = new SqlBuilder();
            t_7660.appendSafe("v = ");
            t_7660.appendString("a''b");
            String actual_1621 = t_7660.getAccumulated().toString();
            boolean t_7666 = actual_1621.equals("v = 'a''''b'");
            Supplier<String> fn__7650 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1621 + ")";
            test_139.assert_(t_7666, fn__7650);
            SqlBuilder t_7668 = new SqlBuilder();
            t_7668.appendSafe("v = ");
            t_7668.appendString("Hello \u4e16\u754c");
            String actual_1624 = t_7668.getAccumulated().toString();
            boolean t_7674 = actual_1624.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__7649 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1624 + ")";
            test_139.assert_(t_7674, fn__7649);
            SqlBuilder t_7676 = new SqlBuilder();
            t_7676.appendSafe("v = ");
            t_7676.appendString("Line1\nLine2");
            String actual_1627 = t_7676.getAccumulated().toString();
            boolean t_7682 = actual_1627.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__7648 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1627 + ")";
            test_139.assert_(t_7682, fn__7648);
        } finally {
            test_139.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1630() {
        Test test_140 = new Test();
        try {
            SqlBuilder t_7623 = new SqlBuilder();
            t_7623.appendSafe("select ");
            t_7623.appendInt32(42);
            t_7623.appendSafe(", ");
            t_7623.appendInt64(43);
            t_7623.appendSafe(", ");
            t_7623.appendFloat64(19.99D);
            t_7623.appendSafe(", ");
            t_7623.appendBoolean(true);
            t_7623.appendSafe(", ");
            t_7623.appendBoolean(false);
            String actual_1631 = t_7623.getAccumulated().toString();
            boolean t_7637 = actual_1631.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__7622 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1631 + ")";
            test_140.assert_(t_7637, fn__7622);
            LocalDate t_3707;
            t_3707 = LocalDate.of(2024, 12, 25);
            LocalDate date__1303 = t_3707;
            SqlBuilder t_7639 = new SqlBuilder();
            t_7639.appendSafe("insert into t values (");
            t_7639.appendDate(date__1303);
            t_7639.appendSafe(")");
            String actual_1634 = t_7639.getAccumulated().toString();
            boolean t_7646 = actual_1634.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__7621 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1634 + ")";
            test_140.assert_(t_7646, fn__7621);
        } finally {
            test_140.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1637() {
        Test test_141 = new Test();
        try {
            SqlBuilder t_7567 = new SqlBuilder();
            t_7567.appendSafe("v IN (");
            t_7567.appendStringList(List.of("a", "b", "c'd"));
            t_7567.appendSafe(")");
            String actual_1638 = t_7567.getAccumulated().toString();
            boolean t_7574 = actual_1638.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__7566 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1638 + ")";
            test_141.assert_(t_7574, fn__7566);
            SqlBuilder t_7576 = new SqlBuilder();
            t_7576.appendSafe("v IN (");
            t_7576.appendInt32List(List.of(1, 2, 3));
            t_7576.appendSafe(")");
            String actual_1641 = t_7576.getAccumulated().toString();
            boolean t_7583 = actual_1641.equals("v IN (1, 2, 3)");
            Supplier<String> fn__7565 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1641 + ")";
            test_141.assert_(t_7583, fn__7565);
            SqlBuilder t_7585 = new SqlBuilder();
            t_7585.appendSafe("v IN (");
            t_7585.appendInt64List(List.of(1, 2));
            t_7585.appendSafe(")");
            String actual_1644 = t_7585.getAccumulated().toString();
            boolean t_7592 = actual_1644.equals("v IN (1, 2)");
            Supplier<String> fn__7564 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1644 + ")";
            test_141.assert_(t_7592, fn__7564);
            SqlBuilder t_7594 = new SqlBuilder();
            t_7594.appendSafe("v IN (");
            t_7594.appendFloat64List(List.of(1.0D, 2.0D));
            t_7594.appendSafe(")");
            String actual_1647 = t_7594.getAccumulated().toString();
            boolean t_7601 = actual_1647.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__7563 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1647 + ")";
            test_141.assert_(t_7601, fn__7563);
            SqlBuilder t_7603 = new SqlBuilder();
            t_7603.appendSafe("v IN (");
            t_7603.appendBooleanList(List.of(true, false));
            t_7603.appendSafe(")");
            String actual_1650 = t_7603.getAccumulated().toString();
            boolean t_7610 = actual_1650.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__7562 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1650 + ")";
            test_141.assert_(t_7610, fn__7562);
            LocalDate t_3679;
            t_3679 = LocalDate.of(2024, 1, 1);
            LocalDate t_3680 = t_3679;
            LocalDate t_3681;
            t_3681 = LocalDate.of(2024, 12, 25);
            LocalDate t_3682 = t_3681;
            List<LocalDate> dates__1305 = List.of(t_3680, t_3682);
            SqlBuilder t_7612 = new SqlBuilder();
            t_7612.appendSafe("v IN (");
            t_7612.appendDateList(dates__1305);
            t_7612.appendSafe(")");
            String actual_1653 = t_7612.getAccumulated().toString();
            boolean t_7619 = actual_1653.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__7561 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1653 + ")";
            test_141.assert_(t_7619, fn__7561);
        } finally {
            test_141.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1656() {
        Test test_142 = new Test();
        try {
            double nan__1307;
            nan__1307 = 0.0D / 0.0D;
            SqlBuilder t_7553 = new SqlBuilder();
            t_7553.appendSafe("v = ");
            t_7553.appendFloat64(nan__1307);
            String actual_1657 = t_7553.getAccumulated().toString();
            boolean t_7559 = actual_1657.equals("v = NULL");
            Supplier<String> fn__7552 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1657 + ")";
            test_142.assert_(t_7559, fn__7552);
        } finally {
            test_142.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1660() {
        Test test_143 = new Test();
        try {
            double inf__1309;
            inf__1309 = 1.0D / 0.0D;
            SqlBuilder t_7544 = new SqlBuilder();
            t_7544.appendSafe("v = ");
            t_7544.appendFloat64(inf__1309);
            String actual_1661 = t_7544.getAccumulated().toString();
            boolean t_7550 = actual_1661.equals("v = NULL");
            Supplier<String> fn__7543 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1661 + ")";
            test_143.assert_(t_7550, fn__7543);
        } finally {
            test_143.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1664() {
        Test test_144 = new Test();
        try {
            double ninf__1311;
            ninf__1311 = -1.0D / 0.0D;
            SqlBuilder t_7535 = new SqlBuilder();
            t_7535.appendSafe("v = ");
            t_7535.appendFloat64(ninf__1311);
            String actual_1665 = t_7535.getAccumulated().toString();
            boolean t_7541 = actual_1665.equals("v = NULL");
            Supplier<String> fn__7534 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1665 + ")";
            test_144.assert_(t_7541, fn__7534);
        } finally {
            test_144.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1668() {
        Test test_145 = new Test();
        try {
            SqlBuilder t_7510 = new SqlBuilder();
            t_7510.appendSafe("v = ");
            t_7510.appendFloat64(3.14D);
            String actual_1669 = t_7510.getAccumulated().toString();
            boolean t_7516 = actual_1669.equals("v = 3.14");
            Supplier<String> fn__7509 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1669 + ")";
            test_145.assert_(t_7516, fn__7509);
            SqlBuilder t_7518 = new SqlBuilder();
            t_7518.appendSafe("v = ");
            t_7518.appendFloat64(0.0D);
            String actual_1672 = t_7518.getAccumulated().toString();
            boolean t_7524 = actual_1672.equals("v = 0.0");
            Supplier<String> fn__7508 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1672 + ")";
            test_145.assert_(t_7524, fn__7508);
            SqlBuilder t_7526 = new SqlBuilder();
            t_7526.appendSafe("v = ");
            t_7526.appendFloat64(-42.5D);
            String actual_1675 = t_7526.getAccumulated().toString();
            boolean t_7532 = actual_1675.equals("v = -42.5");
            Supplier<String> fn__7507 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1675 + ")";
            test_145.assert_(t_7532, fn__7507);
        } finally {
            test_145.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1678() {
        Test test_146 = new Test();
        try {
            LocalDate t_3575;
            t_3575 = LocalDate.of(2024, 6, 15);
            LocalDate d__1314 = t_3575;
            SqlBuilder t_7499 = new SqlBuilder();
            t_7499.appendSafe("v = ");
            t_7499.appendDate(d__1314);
            String actual_1679 = t_7499.getAccumulated().toString();
            boolean t_7505 = actual_1679.equals("v = '2024-06-15'");
            Supplier<String> fn__7498 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1679 + ")";
            test_146.assert_(t_7505, fn__7498);
        } finally {
            test_146.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1682() {
        Test test_147 = new Test();
        try {
            String name__1316 = "Someone";
            SqlBuilder t_7467 = new SqlBuilder();
            t_7467.appendSafe("where p.last_name = ");
            t_7467.appendString("Someone");
            SqlFragment condition__1317 = t_7467.getAccumulated();
            SqlBuilder t_7471 = new SqlBuilder();
            t_7471.appendSafe("select p.id from person p ");
            t_7471.appendFragment(condition__1317);
            String actual_1684 = t_7471.getAccumulated().toString();
            boolean t_7477 = actual_1684.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__7466 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1684 + ")";
            test_147.assert_(t_7477, fn__7466);
            SqlBuilder t_7479 = new SqlBuilder();
            t_7479.appendSafe("select p.id from person p ");
            t_7479.appendPart(condition__1317.toSource());
            String actual_1687 = t_7479.getAccumulated().toString();
            boolean t_7486 = actual_1687.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__7465 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1687 + ")";
            test_147.assert_(t_7486, fn__7465);
            List<SqlPart> parts__1318 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_7490 = new SqlBuilder();
            t_7490.appendSafe("select ");
            t_7490.appendPartList(parts__1318);
            String actual_1690 = t_7490.getAccumulated().toString();
            boolean t_7496 = actual_1690.equals("select 'a''b', 3");
            Supplier<String> fn__7464 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1690 + ")";
            test_147.assert_(t_7496, fn__7464);
        } finally {
            test_147.softFailToHard();
        }
    }
}
