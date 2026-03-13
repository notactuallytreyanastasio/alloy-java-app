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
    static SafeIdentifier csid__302(String name__447) {
        SafeIdentifier t_2783;
        t_2783 = SrcGlobal.safeIdentifier(name__447);
        return t_2783;
    }
    static TableDef userTable__303() {
        return new TableDef(SrcTest.csid__302("users"), List.of(new FieldDef(SrcTest.csid__302("name"), new StringField(), false), new FieldDef(SrcTest.csid__302("email"), new StringField(), false), new FieldDef(SrcTest.csid__302("age"), new IntField(), true), new FieldDef(SrcTest.csid__302("score"), new FloatField(), true), new FieldDef(SrcTest.csid__302("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__908() {
        Test test_22 = new Test();
        try {
            Map<String, String> params__451 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_4828 = SrcTest.userTable__303();
            SafeIdentifier t_4829 = SrcTest.csid__302("name");
            SafeIdentifier t_4830 = SrcTest.csid__302("email");
            Changeset cs__452 = SrcGlobal.changeset(t_4828, params__451).cast(List.of(t_4829, t_4830));
            boolean t_4833 = cs__452.getChanges().containsKey("name");
            Supplier<String> fn__4823 = () -> "name should be in changes";
            test_22.assert_(t_4833, fn__4823);
            boolean t_4837 = cs__452.getChanges().containsKey("email");
            Supplier<String> fn__4822 = () -> "email should be in changes";
            test_22.assert_(t_4837, fn__4822);
            boolean t_4843 = !cs__452.getChanges().containsKey("admin");
            Supplier<String> fn__4821 = () -> "admin must be dropped (not in whitelist)";
            test_22.assert_(t_4843, fn__4821);
            boolean t_4845 = cs__452.isValid();
            Supplier<String> fn__4820 = () -> "should still be valid";
            test_22.assert_(t_4845, fn__4820);
        } finally {
            test_22.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__909() {
        Test test_23 = new Test();
        try {
            Map<String, String> params__454 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_4806 = SrcTest.userTable__303();
            SafeIdentifier t_4807 = SrcTest.csid__302("name");
            Changeset cs__455 = SrcGlobal.changeset(t_4806, params__454).cast(List.of(t_4807)).cast(List.of(SrcTest.csid__302("email")));
            boolean t_4814 = !cs__455.getChanges().containsKey("name");
            Supplier<String> fn__4802 = () -> "name must be excluded by second cast";
            test_23.assert_(t_4814, fn__4802);
            boolean t_4817 = cs__455.getChanges().containsKey("email");
            Supplier<String> fn__4801 = () -> "email should be present";
            test_23.assert_(t_4817, fn__4801);
        } finally {
            test_23.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__910() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__457 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_4788 = SrcTest.userTable__303();
            SafeIdentifier t_4789 = SrcTest.csid__302("name");
            SafeIdentifier t_4790 = SrcTest.csid__302("email");
            Changeset cs__458 = SrcGlobal.changeset(t_4788, params__457).cast(List.of(t_4789, t_4790));
            boolean t_4795 = !cs__458.getChanges().containsKey("name");
            Supplier<String> fn__4784 = () -> "empty name should not be in changes";
            test_24.assert_(t_4795, fn__4784);
            boolean t_4798 = cs__458.getChanges().containsKey("email");
            Supplier<String> fn__4783 = () -> "email should be in changes";
            test_24.assert_(t_4798, fn__4783);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__911() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__460 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_4770 = SrcTest.userTable__303();
            SafeIdentifier t_4771 = SrcTest.csid__302("name");
            Changeset cs__461 = SrcGlobal.changeset(t_4770, params__460).cast(List.of(t_4771)).validateRequired(List.of(SrcTest.csid__302("name")));
            boolean t_4775 = cs__461.isValid();
            Supplier<String> fn__4767 = () -> "should be valid";
            test_25.assert_(t_4775, fn__4767);
            boolean t_4781 = cs__461.getErrors().size() == 0;
            Supplier<String> fn__4766 = () -> "no errors expected";
            test_25.assert_(t_4781, fn__4766);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__912() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__463 = Core.mapConstructor(List.of());
            TableDef t_4746 = SrcTest.userTable__303();
            SafeIdentifier t_4747 = SrcTest.csid__302("name");
            Changeset cs__464 = SrcGlobal.changeset(t_4746, params__463).cast(List.of(t_4747)).validateRequired(List.of(SrcTest.csid__302("name")));
            boolean t_4753 = !cs__464.isValid();
            Supplier<String> fn__4744 = () -> "should be invalid";
            test_26.assert_(t_4753, fn__4744);
            boolean t_4758 = cs__464.getErrors().size() == 1;
            Supplier<String> fn__4743 = () -> "should have one error";
            test_26.assert_(t_4758, fn__4743);
            boolean t_4764 = Core.listGet(cs__464.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__4742 = () -> "error should name the field";
            test_26.assert_(t_4764, fn__4742);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__913() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__466 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_4734 = SrcTest.userTable__303();
            SafeIdentifier t_4735 = SrcTest.csid__302("name");
            Changeset cs__467 = SrcGlobal.changeset(t_4734, params__466).cast(List.of(t_4735)).validateLength(SrcTest.csid__302("name"), 2, 50);
            boolean t_4739 = cs__467.isValid();
            Supplier<String> fn__4731 = () -> "should be valid";
            test_27.assert_(t_4739, fn__4731);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__914() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__469 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_4722 = SrcTest.userTable__303();
            SafeIdentifier t_4723 = SrcTest.csid__302("name");
            Changeset cs__470 = SrcGlobal.changeset(t_4722, params__469).cast(List.of(t_4723)).validateLength(SrcTest.csid__302("name"), 2, 50);
            boolean t_4729 = !cs__470.isValid();
            Supplier<String> fn__4719 = () -> "should be invalid";
            test_28.assert_(t_4729, fn__4719);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__915() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__472 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_4710 = SrcTest.userTable__303();
            SafeIdentifier t_4711 = SrcTest.csid__302("name");
            Changeset cs__473 = SrcGlobal.changeset(t_4710, params__472).cast(List.of(t_4711)).validateLength(SrcTest.csid__302("name"), 2, 10);
            boolean t_4717 = !cs__473.isValid();
            Supplier<String> fn__4707 = () -> "should be invalid";
            test_29.assert_(t_4717, fn__4707);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__916() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__475 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_4699 = SrcTest.userTable__303();
            SafeIdentifier t_4700 = SrcTest.csid__302("age");
            Changeset cs__476 = SrcGlobal.changeset(t_4699, params__475).cast(List.of(t_4700)).validateInt(SrcTest.csid__302("age"));
            boolean t_4704 = cs__476.isValid();
            Supplier<String> fn__4696 = () -> "should be valid";
            test_30.assert_(t_4704, fn__4696);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__917() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__478 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_4687 = SrcTest.userTable__303();
            SafeIdentifier t_4688 = SrcTest.csid__302("age");
            Changeset cs__479 = SrcGlobal.changeset(t_4687, params__478).cast(List.of(t_4688)).validateInt(SrcTest.csid__302("age"));
            boolean t_4694 = !cs__479.isValid();
            Supplier<String> fn__4684 = () -> "should be invalid";
            test_31.assert_(t_4694, fn__4684);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__918() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__481 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_4676 = SrcTest.userTable__303();
            SafeIdentifier t_4677 = SrcTest.csid__302("score");
            Changeset cs__482 = SrcGlobal.changeset(t_4676, params__481).cast(List.of(t_4677)).validateFloat(SrcTest.csid__302("score"));
            boolean t_4681 = cs__482.isValid();
            Supplier<String> fn__4673 = () -> "should be valid";
            test_32.assert_(t_4681, fn__4673);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__919() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__484 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_4665 = SrcTest.userTable__303();
            SafeIdentifier t_4666 = SrcTest.csid__302("age");
            Changeset cs__485 = SrcGlobal.changeset(t_4665, params__484).cast(List.of(t_4666)).validateInt64(SrcTest.csid__302("age"));
            boolean t_4670 = cs__485.isValid();
            Supplier<String> fn__4662 = () -> "should be valid";
            test_33.assert_(t_4670, fn__4662);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__920() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__487 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_4653 = SrcTest.userTable__303();
            SafeIdentifier t_4654 = SrcTest.csid__302("age");
            Changeset cs__488 = SrcGlobal.changeset(t_4653, params__487).cast(List.of(t_4654)).validateInt64(SrcTest.csid__302("age"));
            boolean t_4660 = !cs__488.isValid();
            Supplier<String> fn__4650 = () -> "should be invalid";
            test_34.assert_(t_4660, fn__4650);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__921() {
        Test test_35 = new Test();
        try {
            Consumer<String> fn__4647 = v__490 -> {
                Map<String, String> params__491 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__490)));
                TableDef t_4639 = SrcTest.userTable__303();
                SafeIdentifier t_4640 = SrcTest.csid__302("active");
                Changeset cs__492 = SrcGlobal.changeset(t_4639, params__491).cast(List.of(t_4640)).validateBool(SrcTest.csid__302("active"));
                boolean t_4644 = cs__492.isValid();
                Supplier<String> fn__4636 = () -> "should accept: " + v__490;
                test_35.assert_(t_4644, fn__4636);
            };
            List.of("true", "1", "yes", "on").forEach(fn__4647);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__922() {
        Test test_36 = new Test();
        try {
            Consumer<String> fn__4633 = v__494 -> {
                Map<String, String> params__495 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__494)));
                TableDef t_4625 = SrcTest.userTable__303();
                SafeIdentifier t_4626 = SrcTest.csid__302("active");
                Changeset cs__496 = SrcGlobal.changeset(t_4625, params__495).cast(List.of(t_4626)).validateBool(SrcTest.csid__302("active"));
                boolean t_4630 = cs__496.isValid();
                Supplier<String> fn__4622 = () -> "should accept: " + v__494;
                test_36.assert_(t_4630, fn__4622);
            };
            List.of("false", "0", "no", "off").forEach(fn__4633);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__923() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__4619 = v__498 -> {
                Map<String, String> params__499 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__498)));
                TableDef t_4610 = SrcTest.userTable__303();
                SafeIdentifier t_4611 = SrcTest.csid__302("active");
                Changeset cs__500 = SrcGlobal.changeset(t_4610, params__499).cast(List.of(t_4611)).validateBool(SrcTest.csid__302("active"));
                boolean t_4617 = !cs__500.isValid();
                Supplier<String> fn__4607 = () -> "should reject ambiguous: " + v__498;
                test_37.assert_(t_4617, fn__4607);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__4619);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__924() {
        Test test_38 = new Test();
        try {
            Map<String, String> params__502 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_4595 = SrcTest.userTable__303();
            SafeIdentifier t_4596 = SrcTest.csid__302("name");
            SafeIdentifier t_4597 = SrcTest.csid__302("email");
            Changeset cs__503 = SrcGlobal.changeset(t_4595, params__502).cast(List.of(t_4596, t_4597)).validateRequired(List.of(SrcTest.csid__302("name"), SrcTest.csid__302("email")));
            SqlFragment t_2584;
            t_2584 = cs__503.toInsertSql();
            SqlFragment sqlFrag__504 = t_2584;
            String s__505 = sqlFrag__504.toString();
            boolean t_4604 = s__505.indexOf("''") >= 0;
            Supplier<String> fn__4591 = () -> "single quote must be doubled: " + s__505;
            test_38.assert_(t_4604, fn__4591);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__925() {
        Test test_39 = new Test();
        try {
            Map<String, String> params__507 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_4575 = SrcTest.userTable__303();
            SafeIdentifier t_4576 = SrcTest.csid__302("name");
            SafeIdentifier t_4577 = SrcTest.csid__302("email");
            Changeset cs__508 = SrcGlobal.changeset(t_4575, params__507).cast(List.of(t_4576, t_4577)).validateRequired(List.of(SrcTest.csid__302("name"), SrcTest.csid__302("email")));
            SqlFragment t_2563;
            t_2563 = cs__508.toInsertSql();
            SqlFragment sqlFrag__509 = t_2563;
            String s__510 = sqlFrag__509.toString();
            boolean t_4584 = s__510.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__4571 = () -> "has INSERT INTO: " + s__510;
            test_39.assert_(t_4584, fn__4571);
            boolean t_4588 = s__510.indexOf("'Alice'") >= 0;
            Supplier<String> fn__4570 = () -> "has quoted name: " + s__510;
            test_39.assert_(t_4588, fn__4570);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__926() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__512 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_4557 = SrcTest.userTable__303();
            SafeIdentifier t_4558 = SrcTest.csid__302("name");
            SafeIdentifier t_4559 = SrcTest.csid__302("email");
            SafeIdentifier t_4560 = SrcTest.csid__302("age");
            Changeset cs__513 = SrcGlobal.changeset(t_4557, params__512).cast(List.of(t_4558, t_4559, t_4560)).validateRequired(List.of(SrcTest.csid__302("name"), SrcTest.csid__302("email")));
            SqlFragment t_2546;
            t_2546 = cs__513.toInsertSql();
            SqlFragment sqlFrag__514 = t_2546;
            String s__515 = sqlFrag__514.toString();
            boolean t_4567 = s__515.indexOf("25") >= 0;
            Supplier<String> fn__4552 = () -> "age rendered unquoted: " + s__515;
            test_40.assert_(t_4567, fn__4552);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__927() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__517 = Core.mapConstructor(List.of());
            TableDef t_4545 = SrcTest.userTable__303();
            SafeIdentifier t_4546 = SrcTest.csid__302("name");
            Changeset cs__518 = SrcGlobal.changeset(t_4545, params__517).cast(List.of(t_4546)).validateRequired(List.of(SrcTest.csid__302("name")));
            boolean didBubble__519;
            boolean didBubble_5146;
            try {
                cs__518.toInsertSql();
                didBubble_5146 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_5146 = true;
            }
            didBubble__519 = didBubble_5146;
            Supplier<String> fn__4543 = () -> "invalid changeset should bubble";
            test_41.assert_(didBubble__519, fn__4543);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__928() {
        Test test_42 = new Test();
        try {
            TableDef strictTable__521 = new TableDef(SrcTest.csid__302("posts"), List.of(new FieldDef(SrcTest.csid__302("title"), new StringField(), false), new FieldDef(SrcTest.csid__302("body"), new StringField(), true)));
            Map<String, String> params__522 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_4536 = SrcTest.csid__302("body");
            Changeset cs__523 = SrcGlobal.changeset(strictTable__521, params__522).cast(List.of(t_4536));
            boolean t_4538 = cs__523.isValid();
            Supplier<String> fn__4525 = () -> "changeset should appear valid (no explicit validation run)";
            test_42.assert_(t_4538, fn__4525);
            boolean didBubble__524;
            boolean didBubble_5147;
            try {
                cs__523.toInsertSql();
                didBubble_5147 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_5147 = true;
            }
            didBubble__524 = didBubble_5147;
            Supplier<String> fn__4524 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_42.assert_(didBubble__524, fn__4524);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__929() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__526 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_4515 = SrcTest.userTable__303();
            SafeIdentifier t_4516 = SrcTest.csid__302("name");
            Changeset cs__527 = SrcGlobal.changeset(t_4515, params__526).cast(List.of(t_4516)).validateRequired(List.of(SrcTest.csid__302("name")));
            SqlFragment t_2506;
            t_2506 = cs__527.toUpdateSql(42);
            SqlFragment sqlFrag__528 = t_2506;
            String s__529 = sqlFrag__528.toString();
            boolean t_4522 = s__529.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__4512 = () -> "got: " + s__529;
            test_43.assert_(t_4522, fn__4512);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__930() {
        Test test_44 = new Test();
        try {
            Map<String, String> params__531 = Core.mapConstructor(List.of());
            TableDef t_4505 = SrcTest.userTable__303();
            SafeIdentifier t_4506 = SrcTest.csid__302("name");
            Changeset cs__532 = SrcGlobal.changeset(t_4505, params__531).cast(List.of(t_4506)).validateRequired(List.of(SrcTest.csid__302("name")));
            boolean didBubble__533;
            boolean didBubble_5148;
            try {
                cs__532.toUpdateSql(1);
                didBubble_5148 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_5148 = true;
            }
            didBubble__533 = didBubble_5148;
            Supplier<String> fn__4503 = () -> "invalid changeset should bubble";
            test_44.assert_(didBubble__533, fn__4503);
        } finally {
            test_44.softFailToHard();
        }
    }
    static SafeIdentifier sid__304(String name__588) {
        SafeIdentifier t_2420;
        t_2420 = SrcGlobal.safeIdentifier(name__588);
        return t_2420;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__955() {
        Test test_45 = new Test();
        try {
            Query q__591 = SrcGlobal.from(SrcTest.sid__304("users"));
            boolean t_4438 = q__591.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__4433 = () -> "bare query";
            test_45.assert_(t_4438, fn__4433);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__956() {
        Test test_46 = new Test();
        try {
            SafeIdentifier t_4424 = SrcTest.sid__304("users");
            SafeIdentifier t_4425 = SrcTest.sid__304("id");
            SafeIdentifier t_4426 = SrcTest.sid__304("name");
            Query q__593 = SrcGlobal.from(t_4424).select(List.of(t_4425, t_4426));
            boolean t_4431 = q__593.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__4423 = () -> "select columns";
            test_46.assert_(t_4431, fn__4423);
        } finally {
            test_46.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__957() {
        Test test_47 = new Test();
        try {
            SafeIdentifier t_4412 = SrcTest.sid__304("users");
            SqlBuilder t_4413 = new SqlBuilder();
            t_4413.appendSafe("age > ");
            t_4413.appendInt32(18);
            SqlFragment t_4416 = t_4413.getAccumulated();
            Query q__595 = SrcGlobal.from(t_4412).where(t_4416);
            boolean t_4421 = q__595.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__4411 = () -> "where int";
            test_47.assert_(t_4421, fn__4411);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__959() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_4400 = SrcTest.sid__304("users");
            SqlBuilder t_4401 = new SqlBuilder();
            t_4401.appendSafe("active = ");
            t_4401.appendBoolean(true);
            SqlFragment t_4404 = t_4401.getAccumulated();
            Query q__597 = SrcGlobal.from(t_4400).where(t_4404);
            boolean t_4409 = q__597.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__4399 = () -> "where bool";
            test_48.assert_(t_4409, fn__4399);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__961() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_4383 = SrcTest.sid__304("users");
            SqlBuilder t_4384 = new SqlBuilder();
            t_4384.appendSafe("age > ");
            t_4384.appendInt32(18);
            SqlFragment t_4387 = t_4384.getAccumulated();
            Query t_4388 = SrcGlobal.from(t_4383).where(t_4387);
            SqlBuilder t_4389 = new SqlBuilder();
            t_4389.appendSafe("active = ");
            t_4389.appendBoolean(true);
            Query q__599 = t_4388.where(t_4389.getAccumulated());
            boolean t_4397 = q__599.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__4382 = () -> "chained where";
            test_49.assert_(t_4397, fn__4382);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__964() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_4374 = SrcTest.sid__304("users");
            SafeIdentifier t_4375 = SrcTest.sid__304("name");
            Query q__601 = SrcGlobal.from(t_4374).orderBy(t_4375, true);
            boolean t_4380 = q__601.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__4373 = () -> "order asc";
            test_50.assert_(t_4380, fn__4373);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__965() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_4365 = SrcTest.sid__304("users");
            SafeIdentifier t_4366 = SrcTest.sid__304("created_at");
            Query q__603 = SrcGlobal.from(t_4365).orderBy(t_4366, false);
            boolean t_4371 = q__603.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__4364 = () -> "order desc";
            test_51.assert_(t_4371, fn__4364);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__966() {
        Test test_52 = new Test();
        try {
            Query t_2354;
            t_2354 = SrcGlobal.from(SrcTest.sid__304("users")).limit(10);
            Query t_2355;
            t_2355 = t_2354.offset(20);
            Query q__605 = t_2355;
            boolean t_4362 = q__605.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__4357 = () -> "limit/offset";
            test_52.assert_(t_4362, fn__4357);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__967() {
        Test test_53 = new Test();
        try {
            boolean didBubble__607;
            boolean didBubble_5149;
            try {
                SrcGlobal.from(SrcTest.sid__304("users")).limit(-1);
                didBubble_5149 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_5149 = true;
            }
            didBubble__607 = didBubble_5149;
            Supplier<String> fn__4353 = () -> "negative limit should bubble";
            test_53.assert_(didBubble__607, fn__4353);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__968() {
        Test test_54 = new Test();
        try {
            boolean didBubble__609;
            boolean didBubble_5150;
            try {
                SrcGlobal.from(SrcTest.sid__304("users")).offset(-1);
                didBubble_5150 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_5150 = true;
            }
            didBubble__609 = didBubble_5150;
            Supplier<String> fn__4349 = () -> "negative offset should bubble";
            test_54.assert_(didBubble__609, fn__4349);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__969() {
        Test test_55 = new Test();
        try {
            int minAge__611 = 21;
            SafeIdentifier t_4327 = SrcTest.sid__304("users");
            SafeIdentifier t_4328 = SrcTest.sid__304("id");
            SafeIdentifier t_4329 = SrcTest.sid__304("name");
            SafeIdentifier t_4330 = SrcTest.sid__304("email");
            Query t_4331 = SrcGlobal.from(t_4327).select(List.of(t_4328, t_4329, t_4330));
            SqlBuilder t_4332 = new SqlBuilder();
            t_4332.appendSafe("age >= ");
            t_4332.appendInt32(21);
            Query t_4336 = t_4331.where(t_4332.getAccumulated());
            SqlBuilder t_4337 = new SqlBuilder();
            t_4337.appendSafe("active = ");
            t_4337.appendBoolean(true);
            Query t_2340;
            t_2340 = t_4336.where(t_4337.getAccumulated()).orderBy(SrcTest.sid__304("name"), true).limit(25);
            Query t_2341;
            t_2341 = t_2340.offset(0);
            Query q__612 = t_2341;
            boolean t_4347 = q__612.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__4326 = () -> "complex query";
            test_55.assert_(t_4347, fn__4326);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__972() {
        Test test_56 = new Test();
        try {
            Query q__614 = SrcGlobal.from(SrcTest.sid__304("users"));
            SqlFragment t_2317;
            t_2317 = q__614.safeToSql(100);
            SqlFragment t_2318 = t_2317;
            String s__615 = t_2318.toString();
            boolean t_4324 = s__615.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__4320 = () -> "should have limit: " + s__615;
            test_56.assert_(t_4324, fn__4320);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__973() {
        Test test_57 = new Test();
        try {
            Query t_2309;
            t_2309 = SrcGlobal.from(SrcTest.sid__304("users")).limit(5);
            Query q__617 = t_2309;
            SqlFragment t_2312;
            t_2312 = q__617.safeToSql(100);
            SqlFragment t_2313 = t_2312;
            String s__618 = t_2313.toString();
            boolean t_4318 = s__618.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__4314 = () -> "explicit limit preserved: " + s__618;
            test_57.assert_(t_4318, fn__4314);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__974() {
        Test test_58 = new Test();
        try {
            boolean didBubble__620;
            boolean didBubble_5151;
            try {
                SrcGlobal.from(SrcTest.sid__304("users")).safeToSql(-1);
                didBubble_5151 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_5151 = true;
            }
            didBubble__620 = didBubble_5151;
            Supplier<String> fn__4310 = () -> "negative defaultLimit should bubble";
            test_58.assert_(didBubble__620, fn__4310);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__975() {
        Test test_59 = new Test();
        try {
            String evil__622 = "'; DROP TABLE users; --";
            SafeIdentifier t_4294 = SrcTest.sid__304("users");
            SqlBuilder t_4295 = new SqlBuilder();
            t_4295.appendSafe("name = ");
            t_4295.appendString("'; DROP TABLE users; --");
            SqlFragment t_4298 = t_4295.getAccumulated();
            Query q__623 = SrcGlobal.from(t_4294).where(t_4298);
            String s__624 = q__623.toSql().toString();
            boolean t_4303 = s__624.indexOf("''") >= 0;
            Supplier<String> fn__4293 = () -> "quotes must be doubled: " + s__624;
            test_59.assert_(t_4303, fn__4293);
            boolean t_4307 = s__624.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__4292 = () -> "structure intact: " + s__624;
            test_59.assert_(t_4307, fn__4292);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__977() {
        Test test_60 = new Test();
        try {
            String attack__626 = "users; DROP TABLE users; --";
            boolean didBubble__627;
            boolean didBubble_5152;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_5152 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_5152 = true;
            }
            didBubble__627 = didBubble_5152;
            Supplier<String> fn__4289 = () -> "metacharacter-containing name must be rejected at construction";
            test_60.assert_(didBubble__627, fn__4289);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__978() {
        Test test_67 = new Test();
        try {
            SafeIdentifier t_2282;
            t_2282 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__665 = t_2282;
            boolean t_4287 = id__665.getSqlValue().equals("user_name");
            Supplier<String> fn__4284 = () -> "value should round-trip";
            test_67.assert_(t_4287, fn__4284);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__979() {
        Test test_68 = new Test();
        try {
            boolean didBubble__667;
            boolean didBubble_5153;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_5153 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_5153 = true;
            }
            didBubble__667 = didBubble_5153;
            Supplier<String> fn__4281 = () -> "empty string should bubble";
            test_68.assert_(didBubble__667, fn__4281);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__980() {
        Test test_69 = new Test();
        try {
            boolean didBubble__669;
            boolean didBubble_5154;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_5154 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_5154 = true;
            }
            didBubble__669 = didBubble_5154;
            Supplier<String> fn__4278 = () -> "leading digit should bubble";
            test_69.assert_(didBubble__669, fn__4278);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__981() {
        Test test_70 = new Test();
        try {
            List<String> cases__671 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__4275 = c__672 -> {
                boolean didBubble__673;
                boolean didBubble_5155;
                try {
                    SrcGlobal.safeIdentifier(c__672);
                    didBubble_5155 = false;
                } catch (RuntimeException ignored$13) {
                    didBubble_5155 = true;
                }
                didBubble__673 = didBubble_5155;
                Supplier<String> fn__4272 = () -> "should reject: " + c__672;
                test_70.assert_(didBubble__673, fn__4272);
            };
            cases__671.forEach(fn__4275);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__982() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_2259;
            t_2259 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_2260 = t_2259;
            SafeIdentifier t_2261;
            t_2261 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_2262 = t_2261;
            StringField t_4262 = new StringField();
            FieldDef t_4263 = new FieldDef(t_2262, t_4262, false);
            SafeIdentifier t_2265;
            t_2265 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_2266 = t_2265;
            IntField t_4264 = new IntField();
            FieldDef t_4265 = new FieldDef(t_2266, t_4264, false);
            TableDef td__675 = new TableDef(t_2260, List.of(t_4263, t_4265));
            FieldDef t_2270;
            t_2270 = td__675.field("age");
            FieldDef f__676 = t_2270;
            boolean t_4270 = f__676.getName().getSqlValue().equals("age");
            Supplier<String> fn__4261 = () -> "should find age field";
            test_71.assert_(t_4270, fn__4261);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__983() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_2250;
            t_2250 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_2251 = t_2250;
            SafeIdentifier t_2252;
            t_2252 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_2253 = t_2252;
            StringField t_4256 = new StringField();
            FieldDef t_4257 = new FieldDef(t_2253, t_4256, false);
            TableDef td__678 = new TableDef(t_2251, List.of(t_4257));
            boolean didBubble__679;
            boolean didBubble_5156;
            try {
                td__678.field("nonexistent");
                didBubble_5156 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_5156 = true;
            }
            didBubble__679 = didBubble_5156;
            Supplier<String> fn__4255 = () -> "unknown field should bubble";
            test_72.assert_(didBubble__679, fn__4255);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__984() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_2238;
            t_2238 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_2239 = t_2238;
            StringField t_4244 = new StringField();
            FieldDef required__681 = new FieldDef(t_2239, t_4244, false);
            SafeIdentifier t_2242;
            t_2242 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_2243 = t_2242;
            StringField t_4246 = new StringField();
            FieldDef optional__682 = new FieldDef(t_2243, t_4246, true);
            boolean t_4250 = !required__681.isNullable();
            Supplier<String> fn__4243 = () -> "required field should not be nullable";
            test_73.assert_(t_4250, fn__4243);
            boolean t_4252 = optional__682.isNullable();
            Supplier<String> fn__4242 = () -> "optional field should be nullable";
            test_73.assert_(t_4252, fn__4242);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__985() {
        Test test_77 = new Test();
        try {
            Function<String, String> build__808 = name__810 -> {
                SqlBuilder t_4224 = new SqlBuilder();
                t_4224.appendSafe("select * from hi where name = ");
                t_4224.appendString(name__810);
                return t_4224.getAccumulated().toString();
            };
            Function<String, String> buildWrong__809 = name__812 -> "select * from hi where name = '" + name__812 + "'";
            String actual_987 = build__808.apply("world");
            boolean t_4234 = actual_987.equals("select * from hi where name = 'world'");
            Supplier<String> fn__4231 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_987 + ")";
            test_77.assert_(t_4234, fn__4231);
            String bobbyTables__814 = "Robert'); drop table hi;--";
            String actual_989 = build__808.apply("Robert'); drop table hi;--");
            boolean t_4238 = actual_989.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__4230 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_989 + ")";
            test_77.assert_(t_4238, fn__4230);
            Supplier<String> fn__4229 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_77.assert_(true, fn__4229);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__993() {
        Test test_78 = new Test();
        try {
            SqlBuilder t_4192 = new SqlBuilder();
            t_4192.appendSafe("v = ");
            t_4192.appendString("");
            String actual_994 = t_4192.getAccumulated().toString();
            boolean t_4198 = actual_994.equals("v = ''");
            Supplier<String> fn__4191 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_994 + ")";
            test_78.assert_(t_4198, fn__4191);
            SqlBuilder t_4200 = new SqlBuilder();
            t_4200.appendSafe("v = ");
            t_4200.appendString("a''b");
            String actual_997 = t_4200.getAccumulated().toString();
            boolean t_4206 = actual_997.equals("v = 'a''''b'");
            Supplier<String> fn__4190 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_997 + ")";
            test_78.assert_(t_4206, fn__4190);
            SqlBuilder t_4208 = new SqlBuilder();
            t_4208.appendSafe("v = ");
            t_4208.appendString("Hello \u4e16\u754c");
            String actual_1000 = t_4208.getAccumulated().toString();
            boolean t_4214 = actual_1000.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__4189 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1000 + ")";
            test_78.assert_(t_4214, fn__4189);
            SqlBuilder t_4216 = new SqlBuilder();
            t_4216.appendSafe("v = ");
            t_4216.appendString("Line1\nLine2");
            String actual_1003 = t_4216.getAccumulated().toString();
            boolean t_4222 = actual_1003.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__4188 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1003 + ")";
            test_78.assert_(t_4222, fn__4188);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1006() {
        Test test_79 = new Test();
        try {
            SqlBuilder t_4163 = new SqlBuilder();
            t_4163.appendSafe("select ");
            t_4163.appendInt32(42);
            t_4163.appendSafe(", ");
            t_4163.appendInt64(43);
            t_4163.appendSafe(", ");
            t_4163.appendFloat64(19.99D);
            t_4163.appendSafe(", ");
            t_4163.appendBoolean(true);
            t_4163.appendSafe(", ");
            t_4163.appendBoolean(false);
            String actual_1007 = t_4163.getAccumulated().toString();
            boolean t_4177 = actual_1007.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__4162 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1007 + ")";
            test_79.assert_(t_4177, fn__4162);
            LocalDate t_2183;
            t_2183 = LocalDate.of(2024, 12, 25);
            LocalDate date__817 = t_2183;
            SqlBuilder t_4179 = new SqlBuilder();
            t_4179.appendSafe("insert into t values (");
            t_4179.appendDate(date__817);
            t_4179.appendSafe(")");
            String actual_1010 = t_4179.getAccumulated().toString();
            boolean t_4186 = actual_1010.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__4161 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1010 + ")";
            test_79.assert_(t_4186, fn__4161);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1013() {
        Test test_80 = new Test();
        try {
            SqlBuilder t_4107 = new SqlBuilder();
            t_4107.appendSafe("v IN (");
            t_4107.appendStringList(List.of("a", "b", "c'd"));
            t_4107.appendSafe(")");
            String actual_1014 = t_4107.getAccumulated().toString();
            boolean t_4114 = actual_1014.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__4106 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1014 + ")";
            test_80.assert_(t_4114, fn__4106);
            SqlBuilder t_4116 = new SqlBuilder();
            t_4116.appendSafe("v IN (");
            t_4116.appendInt32List(List.of(1, 2, 3));
            t_4116.appendSafe(")");
            String actual_1017 = t_4116.getAccumulated().toString();
            boolean t_4123 = actual_1017.equals("v IN (1, 2, 3)");
            Supplier<String> fn__4105 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1017 + ")";
            test_80.assert_(t_4123, fn__4105);
            SqlBuilder t_4125 = new SqlBuilder();
            t_4125.appendSafe("v IN (");
            t_4125.appendInt64List(List.of(1, 2));
            t_4125.appendSafe(")");
            String actual_1020 = t_4125.getAccumulated().toString();
            boolean t_4132 = actual_1020.equals("v IN (1, 2)");
            Supplier<String> fn__4104 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1020 + ")";
            test_80.assert_(t_4132, fn__4104);
            SqlBuilder t_4134 = new SqlBuilder();
            t_4134.appendSafe("v IN (");
            t_4134.appendFloat64List(List.of(1.0D, 2.0D));
            t_4134.appendSafe(")");
            String actual_1023 = t_4134.getAccumulated().toString();
            boolean t_4141 = actual_1023.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__4103 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1023 + ")";
            test_80.assert_(t_4141, fn__4103);
            SqlBuilder t_4143 = new SqlBuilder();
            t_4143.appendSafe("v IN (");
            t_4143.appendBooleanList(List.of(true, false));
            t_4143.appendSafe(")");
            String actual_1026 = t_4143.getAccumulated().toString();
            boolean t_4150 = actual_1026.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__4102 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1026 + ")";
            test_80.assert_(t_4150, fn__4102);
            LocalDate t_2155;
            t_2155 = LocalDate.of(2024, 1, 1);
            LocalDate t_2156 = t_2155;
            LocalDate t_2157;
            t_2157 = LocalDate.of(2024, 12, 25);
            LocalDate t_2158 = t_2157;
            List<LocalDate> dates__819 = List.of(t_2156, t_2158);
            SqlBuilder t_4152 = new SqlBuilder();
            t_4152.appendSafe("v IN (");
            t_4152.appendDateList(dates__819);
            t_4152.appendSafe(")");
            String actual_1029 = t_4152.getAccumulated().toString();
            boolean t_4159 = actual_1029.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__4101 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1029 + ")";
            test_80.assert_(t_4159, fn__4101);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1032() {
        Test test_81 = new Test();
        try {
            double nan__821;
            nan__821 = 0.0D / 0.0D;
            SqlBuilder t_4093 = new SqlBuilder();
            t_4093.appendSafe("v = ");
            t_4093.appendFloat64(nan__821);
            String actual_1033 = t_4093.getAccumulated().toString();
            boolean t_4099 = actual_1033.equals("v = NULL");
            Supplier<String> fn__4092 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1033 + ")";
            test_81.assert_(t_4099, fn__4092);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1036() {
        Test test_82 = new Test();
        try {
            double inf__823;
            inf__823 = 1.0D / 0.0D;
            SqlBuilder t_4084 = new SqlBuilder();
            t_4084.appendSafe("v = ");
            t_4084.appendFloat64(inf__823);
            String actual_1037 = t_4084.getAccumulated().toString();
            boolean t_4090 = actual_1037.equals("v = NULL");
            Supplier<String> fn__4083 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1037 + ")";
            test_82.assert_(t_4090, fn__4083);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1040() {
        Test test_83 = new Test();
        try {
            double ninf__825;
            ninf__825 = -1.0D / 0.0D;
            SqlBuilder t_4075 = new SqlBuilder();
            t_4075.appendSafe("v = ");
            t_4075.appendFloat64(ninf__825);
            String actual_1041 = t_4075.getAccumulated().toString();
            boolean t_4081 = actual_1041.equals("v = NULL");
            Supplier<String> fn__4074 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1041 + ")";
            test_83.assert_(t_4081, fn__4074);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1044() {
        Test test_84 = new Test();
        try {
            SqlBuilder t_4050 = new SqlBuilder();
            t_4050.appendSafe("v = ");
            t_4050.appendFloat64(3.14D);
            String actual_1045 = t_4050.getAccumulated().toString();
            boolean t_4056 = actual_1045.equals("v = 3.14");
            Supplier<String> fn__4049 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1045 + ")";
            test_84.assert_(t_4056, fn__4049);
            SqlBuilder t_4058 = new SqlBuilder();
            t_4058.appendSafe("v = ");
            t_4058.appendFloat64(0.0D);
            String actual_1048 = t_4058.getAccumulated().toString();
            boolean t_4064 = actual_1048.equals("v = 0.0");
            Supplier<String> fn__4048 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1048 + ")";
            test_84.assert_(t_4064, fn__4048);
            SqlBuilder t_4066 = new SqlBuilder();
            t_4066.appendSafe("v = ");
            t_4066.appendFloat64(-42.5D);
            String actual_1051 = t_4066.getAccumulated().toString();
            boolean t_4072 = actual_1051.equals("v = -42.5");
            Supplier<String> fn__4047 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1051 + ")";
            test_84.assert_(t_4072, fn__4047);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1054() {
        Test test_85 = new Test();
        try {
            LocalDate t_2051;
            t_2051 = LocalDate.of(2024, 6, 15);
            LocalDate d__828 = t_2051;
            SqlBuilder t_4039 = new SqlBuilder();
            t_4039.appendSafe("v = ");
            t_4039.appendDate(d__828);
            String actual_1055 = t_4039.getAccumulated().toString();
            boolean t_4045 = actual_1055.equals("v = '2024-06-15'");
            Supplier<String> fn__4038 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1055 + ")";
            test_85.assert_(t_4045, fn__4038);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1058() {
        Test test_86 = new Test();
        try {
            String name__830 = "Someone";
            SqlBuilder t_4007 = new SqlBuilder();
            t_4007.appendSafe("where p.last_name = ");
            t_4007.appendString("Someone");
            SqlFragment condition__831 = t_4007.getAccumulated();
            SqlBuilder t_4011 = new SqlBuilder();
            t_4011.appendSafe("select p.id from person p ");
            t_4011.appendFragment(condition__831);
            String actual_1060 = t_4011.getAccumulated().toString();
            boolean t_4017 = actual_1060.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__4006 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1060 + ")";
            test_86.assert_(t_4017, fn__4006);
            SqlBuilder t_4019 = new SqlBuilder();
            t_4019.appendSafe("select p.id from person p ");
            t_4019.appendPart(condition__831.toSource());
            String actual_1063 = t_4019.getAccumulated().toString();
            boolean t_4026 = actual_1063.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__4005 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1063 + ")";
            test_86.assert_(t_4026, fn__4005);
            List<SqlPart> parts__832 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_4030 = new SqlBuilder();
            t_4030.appendSafe("select ");
            t_4030.appendPartList(parts__832);
            String actual_1066 = t_4030.getAccumulated().toString();
            boolean t_4036 = actual_1066.equals("select 'a''b', 3");
            Supplier<String> fn__4004 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1066 + ")";
            test_86.assert_(t_4036, fn__4004);
        } finally {
            test_86.softFailToHard();
        }
    }
}
