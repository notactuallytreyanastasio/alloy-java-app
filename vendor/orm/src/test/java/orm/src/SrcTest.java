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
    static SafeIdentifier csid__441(String name__586) {
        SafeIdentifier t_4812;
        t_4812 = SrcGlobal.safeIdentifier(name__586);
        return t_4812;
    }
    static TableDef userTable__442() {
        return new TableDef(SrcTest.csid__441("users"), List.of(new FieldDef(SrcTest.csid__441("name"), new StringField(), false), new FieldDef(SrcTest.csid__441("email"), new StringField(), false), new FieldDef(SrcTest.csid__441("age"), new IntField(), true), new FieldDef(SrcTest.csid__441("score"), new FloatField(), true), new FieldDef(SrcTest.csid__441("active"), new BoolField(), true)));
    }
    @org.junit.jupiter.api.Test public void castWhitelistsAllowedFields__1320() {
        Test test_24 = new Test();
        try {
            Map<String, String> params__590 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com"), new SimpleImmutableEntry<>("admin", "true")));
            TableDef t_8523 = SrcTest.userTable__442();
            SafeIdentifier t_8524 = SrcTest.csid__441("name");
            SafeIdentifier t_8525 = SrcTest.csid__441("email");
            Changeset cs__591 = SrcGlobal.changeset(t_8523, params__590).cast(List.of(t_8524, t_8525));
            boolean t_8528 = cs__591.getChanges().containsKey("name");
            Supplier<String> fn__8518 = () -> "name should be in changes";
            test_24.assert_(t_8528, fn__8518);
            boolean t_8532 = cs__591.getChanges().containsKey("email");
            Supplier<String> fn__8517 = () -> "email should be in changes";
            test_24.assert_(t_8532, fn__8517);
            boolean t_8538 = !cs__591.getChanges().containsKey("admin");
            Supplier<String> fn__8516 = () -> "admin must be dropped (not in whitelist)";
            test_24.assert_(t_8538, fn__8516);
            boolean t_8540 = cs__591.isValid();
            Supplier<String> fn__8515 = () -> "should still be valid";
            test_24.assert_(t_8540, fn__8515);
        } finally {
            test_24.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIsReplacingNotAdditiveSecondCallResetsWhitelist__1321() {
        Test test_25 = new Test();
        try {
            Map<String, String> params__593 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "alice@example.com")));
            TableDef t_8501 = SrcTest.userTable__442();
            SafeIdentifier t_8502 = SrcTest.csid__441("name");
            Changeset cs__594 = SrcGlobal.changeset(t_8501, params__593).cast(List.of(t_8502)).cast(List.of(SrcTest.csid__441("email")));
            boolean t_8509 = !cs__594.getChanges().containsKey("name");
            Supplier<String> fn__8497 = () -> "name must be excluded by second cast";
            test_25.assert_(t_8509, fn__8497);
            boolean t_8512 = cs__594.getChanges().containsKey("email");
            Supplier<String> fn__8496 = () -> "email should be present";
            test_25.assert_(t_8512, fn__8496);
        } finally {
            test_25.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void castIgnoresEmptyStringValues__1322() {
        Test test_26 = new Test();
        try {
            Map<String, String> params__596 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", ""), new SimpleImmutableEntry<>("email", "bob@example.com")));
            TableDef t_8483 = SrcTest.userTable__442();
            SafeIdentifier t_8484 = SrcTest.csid__441("name");
            SafeIdentifier t_8485 = SrcTest.csid__441("email");
            Changeset cs__597 = SrcGlobal.changeset(t_8483, params__596).cast(List.of(t_8484, t_8485));
            boolean t_8490 = !cs__597.getChanges().containsKey("name");
            Supplier<String> fn__8479 = () -> "empty name should not be in changes";
            test_26.assert_(t_8490, fn__8479);
            boolean t_8493 = cs__597.getChanges().containsKey("email");
            Supplier<String> fn__8478 = () -> "email should be in changes";
            test_26.assert_(t_8493, fn__8478);
        } finally {
            test_26.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredPassesWhenFieldPresent__1323() {
        Test test_27 = new Test();
        try {
            Map<String, String> params__599 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_8465 = SrcTest.userTable__442();
            SafeIdentifier t_8466 = SrcTest.csid__441("name");
            Changeset cs__600 = SrcGlobal.changeset(t_8465, params__599).cast(List.of(t_8466)).validateRequired(List.of(SrcTest.csid__441("name")));
            boolean t_8470 = cs__600.isValid();
            Supplier<String> fn__8462 = () -> "should be valid";
            test_27.assert_(t_8470, fn__8462);
            boolean t_8476 = cs__600.getErrors().size() == 0;
            Supplier<String> fn__8461 = () -> "no errors expected";
            test_27.assert_(t_8476, fn__8461);
        } finally {
            test_27.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateRequiredFailsWhenFieldMissing__1324() {
        Test test_28 = new Test();
        try {
            Map<String, String> params__602 = Core.mapConstructor(List.of());
            TableDef t_8441 = SrcTest.userTable__442();
            SafeIdentifier t_8442 = SrcTest.csid__441("name");
            Changeset cs__603 = SrcGlobal.changeset(t_8441, params__602).cast(List.of(t_8442)).validateRequired(List.of(SrcTest.csid__441("name")));
            boolean t_8448 = !cs__603.isValid();
            Supplier<String> fn__8439 = () -> "should be invalid";
            test_28.assert_(t_8448, fn__8439);
            boolean t_8453 = cs__603.getErrors().size() == 1;
            Supplier<String> fn__8438 = () -> "should have one error";
            test_28.assert_(t_8453, fn__8438);
            boolean t_8459 = Core.listGet(cs__603.getErrors(), 0).getField().equals("name");
            Supplier<String> fn__8437 = () -> "error should name the field";
            test_28.assert_(t_8459, fn__8437);
        } finally {
            test_28.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthPassesWithinRange__1325() {
        Test test_29 = new Test();
        try {
            Map<String, String> params__605 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice")));
            TableDef t_8429 = SrcTest.userTable__442();
            SafeIdentifier t_8430 = SrcTest.csid__441("name");
            Changeset cs__606 = SrcGlobal.changeset(t_8429, params__605).cast(List.of(t_8430)).validateLength(SrcTest.csid__441("name"), 2, 50);
            boolean t_8434 = cs__606.isValid();
            Supplier<String> fn__8426 = () -> "should be valid";
            test_29.assert_(t_8434, fn__8426);
        } finally {
            test_29.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooShort__1326() {
        Test test_30 = new Test();
        try {
            Map<String, String> params__608 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "A")));
            TableDef t_8417 = SrcTest.userTable__442();
            SafeIdentifier t_8418 = SrcTest.csid__441("name");
            Changeset cs__609 = SrcGlobal.changeset(t_8417, params__608).cast(List.of(t_8418)).validateLength(SrcTest.csid__441("name"), 2, 50);
            boolean t_8424 = !cs__609.isValid();
            Supplier<String> fn__8414 = () -> "should be invalid";
            test_30.assert_(t_8424, fn__8414);
        } finally {
            test_30.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateLengthFailsWhenTooLong__1327() {
        Test test_31 = new Test();
        try {
            Map<String, String> params__611 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "ABCDEFGHIJKLMNOPQRSTUVWXYZ")));
            TableDef t_8405 = SrcTest.userTable__442();
            SafeIdentifier t_8406 = SrcTest.csid__441("name");
            Changeset cs__612 = SrcGlobal.changeset(t_8405, params__611).cast(List.of(t_8406)).validateLength(SrcTest.csid__441("name"), 2, 10);
            boolean t_8412 = !cs__612.isValid();
            Supplier<String> fn__8402 = () -> "should be invalid";
            test_31.assert_(t_8412, fn__8402);
        } finally {
            test_31.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntPassesForValidInteger__1328() {
        Test test_32 = new Test();
        try {
            Map<String, String> params__614 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "30")));
            TableDef t_8394 = SrcTest.userTable__442();
            SafeIdentifier t_8395 = SrcTest.csid__441("age");
            Changeset cs__615 = SrcGlobal.changeset(t_8394, params__614).cast(List.of(t_8395)).validateInt(SrcTest.csid__441("age"));
            boolean t_8399 = cs__615.isValid();
            Supplier<String> fn__8391 = () -> "should be valid";
            test_32.assert_(t_8399, fn__8391);
        } finally {
            test_32.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateIntFailsForNonInteger__1329() {
        Test test_33 = new Test();
        try {
            Map<String, String> params__617 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_8382 = SrcTest.userTable__442();
            SafeIdentifier t_8383 = SrcTest.csid__441("age");
            Changeset cs__618 = SrcGlobal.changeset(t_8382, params__617).cast(List.of(t_8383)).validateInt(SrcTest.csid__441("age"));
            boolean t_8389 = !cs__618.isValid();
            Supplier<String> fn__8379 = () -> "should be invalid";
            test_33.assert_(t_8389, fn__8379);
        } finally {
            test_33.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateFloatPassesForValidFloat__1330() {
        Test test_34 = new Test();
        try {
            Map<String, String> params__620 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("score", "9.5")));
            TableDef t_8371 = SrcTest.userTable__442();
            SafeIdentifier t_8372 = SrcTest.csid__441("score");
            Changeset cs__621 = SrcGlobal.changeset(t_8371, params__620).cast(List.of(t_8372)).validateFloat(SrcTest.csid__441("score"));
            boolean t_8376 = cs__621.isValid();
            Supplier<String> fn__8368 = () -> "should be valid";
            test_34.assert_(t_8376, fn__8368);
        } finally {
            test_34.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_passesForValid64_bitInteger__1331() {
        Test test_35 = new Test();
        try {
            Map<String, String> params__623 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "9999999999")));
            TableDef t_8360 = SrcTest.userTable__442();
            SafeIdentifier t_8361 = SrcTest.csid__441("age");
            Changeset cs__624 = SrcGlobal.changeset(t_8360, params__623).cast(List.of(t_8361)).validateInt64(SrcTest.csid__441("age"));
            boolean t_8365 = cs__624.isValid();
            Supplier<String> fn__8357 = () -> "should be valid";
            test_35.assert_(t_8365, fn__8357);
        } finally {
            test_35.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateInt64_failsForNonInteger__1332() {
        Test test_36 = new Test();
        try {
            Map<String, String> params__626 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("age", "not-a-number")));
            TableDef t_8348 = SrcTest.userTable__442();
            SafeIdentifier t_8349 = SrcTest.csid__441("age");
            Changeset cs__627 = SrcGlobal.changeset(t_8348, params__626).cast(List.of(t_8349)).validateInt64(SrcTest.csid__441("age"));
            boolean t_8355 = !cs__627.isValid();
            Supplier<String> fn__8345 = () -> "should be invalid";
            test_36.assert_(t_8355, fn__8345);
        } finally {
            test_36.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsTrue1_yesOn__1333() {
        Test test_37 = new Test();
        try {
            Consumer<String> fn__8342 = v__629 -> {
                Map<String, String> params__630 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__629)));
                TableDef t_8334 = SrcTest.userTable__442();
                SafeIdentifier t_8335 = SrcTest.csid__441("active");
                Changeset cs__631 = SrcGlobal.changeset(t_8334, params__630).cast(List.of(t_8335)).validateBool(SrcTest.csid__441("active"));
                boolean t_8339 = cs__631.isValid();
                Supplier<String> fn__8331 = () -> "should accept: " + v__629;
                test_37.assert_(t_8339, fn__8331);
            };
            List.of("true", "1", "yes", "on").forEach(fn__8342);
        } finally {
            test_37.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolAcceptsFalse0_noOff__1334() {
        Test test_38 = new Test();
        try {
            Consumer<String> fn__8328 = v__633 -> {
                Map<String, String> params__634 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__633)));
                TableDef t_8320 = SrcTest.userTable__442();
                SafeIdentifier t_8321 = SrcTest.csid__441("active");
                Changeset cs__635 = SrcGlobal.changeset(t_8320, params__634).cast(List.of(t_8321)).validateBool(SrcTest.csid__441("active"));
                boolean t_8325 = cs__635.isValid();
                Supplier<String> fn__8317 = () -> "should accept: " + v__633;
                test_38.assert_(t_8325, fn__8317);
            };
            List.of("false", "0", "no", "off").forEach(fn__8328);
        } finally {
            test_38.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void validateBoolRejectsAmbiguousValues__1335() {
        Test test_39 = new Test();
        try {
            Consumer<String> fn__8314 = v__637 -> {
                Map<String, String> params__638 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("active", v__637)));
                TableDef t_8305 = SrcTest.userTable__442();
                SafeIdentifier t_8306 = SrcTest.csid__441("active");
                Changeset cs__639 = SrcGlobal.changeset(t_8305, params__638).cast(List.of(t_8306)).validateBool(SrcTest.csid__441("active"));
                boolean t_8312 = !cs__639.isValid();
                Supplier<String> fn__8302 = () -> "should reject ambiguous: " + v__637;
                test_39.assert_(t_8312, fn__8302);
            };
            List.of("TRUE", "Yes", "maybe", "2", "enabled").forEach(fn__8314);
        } finally {
            test_39.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEscapesBobbyTables__1336() {
        Test test_40 = new Test();
        try {
            Map<String, String> params__641 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Robert'); DROP TABLE users;--"), new SimpleImmutableEntry<>("email", "bobby@evil.com")));
            TableDef t_8290 = SrcTest.userTable__442();
            SafeIdentifier t_8291 = SrcTest.csid__441("name");
            SafeIdentifier t_8292 = SrcTest.csid__441("email");
            Changeset cs__642 = SrcGlobal.changeset(t_8290, params__641).cast(List.of(t_8291, t_8292)).validateRequired(List.of(SrcTest.csid__441("name"), SrcTest.csid__441("email")));
            SqlFragment t_4613;
            t_4613 = cs__642.toInsertSql();
            SqlFragment sqlFrag__643 = t_4613;
            String s__644 = sqlFrag__643.toString();
            boolean t_8299 = s__644.indexOf("''") >= 0;
            Supplier<String> fn__8286 = () -> "single quote must be doubled: " + s__644;
            test_40.assert_(t_8299, fn__8286);
        } finally {
            test_40.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForStringField__1337() {
        Test test_41 = new Test();
        try {
            Map<String, String> params__646 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Alice"), new SimpleImmutableEntry<>("email", "a@example.com")));
            TableDef t_8270 = SrcTest.userTable__442();
            SafeIdentifier t_8271 = SrcTest.csid__441("name");
            SafeIdentifier t_8272 = SrcTest.csid__441("email");
            Changeset cs__647 = SrcGlobal.changeset(t_8270, params__646).cast(List.of(t_8271, t_8272)).validateRequired(List.of(SrcTest.csid__441("name"), SrcTest.csid__441("email")));
            SqlFragment t_4592;
            t_4592 = cs__647.toInsertSql();
            SqlFragment sqlFrag__648 = t_4592;
            String s__649 = sqlFrag__648.toString();
            boolean t_8279 = s__649.indexOf("INSERT INTO users") >= 0;
            Supplier<String> fn__8266 = () -> "has INSERT INTO: " + s__649;
            test_41.assert_(t_8279, fn__8266);
            boolean t_8283 = s__649.indexOf("'Alice'") >= 0;
            Supplier<String> fn__8265 = () -> "has quoted name: " + s__649;
            test_41.assert_(t_8283, fn__8265);
        } finally {
            test_41.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlProducesCorrectSqlForIntField__1338() {
        Test test_42 = new Test();
        try {
            Map<String, String> params__651 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob"), new SimpleImmutableEntry<>("email", "b@example.com"), new SimpleImmutableEntry<>("age", "25")));
            TableDef t_8252 = SrcTest.userTable__442();
            SafeIdentifier t_8253 = SrcTest.csid__441("name");
            SafeIdentifier t_8254 = SrcTest.csid__441("email");
            SafeIdentifier t_8255 = SrcTest.csid__441("age");
            Changeset cs__652 = SrcGlobal.changeset(t_8252, params__651).cast(List.of(t_8253, t_8254, t_8255)).validateRequired(List.of(SrcTest.csid__441("name"), SrcTest.csid__441("email")));
            SqlFragment t_4575;
            t_4575 = cs__652.toInsertSql();
            SqlFragment sqlFrag__653 = t_4575;
            String s__654 = sqlFrag__653.toString();
            boolean t_8262 = s__654.indexOf("25") >= 0;
            Supplier<String> fn__8247 = () -> "age rendered unquoted: " + s__654;
            test_42.assert_(t_8262, fn__8247);
        } finally {
            test_42.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlBubblesOnInvalidChangeset__1339() {
        Test test_43 = new Test();
        try {
            Map<String, String> params__656 = Core.mapConstructor(List.of());
            TableDef t_8240 = SrcTest.userTable__442();
            SafeIdentifier t_8241 = SrcTest.csid__441("name");
            Changeset cs__657 = SrcGlobal.changeset(t_8240, params__656).cast(List.of(t_8241)).validateRequired(List.of(SrcTest.csid__441("name")));
            boolean didBubble__658;
            boolean didBubble_8841;
            try {
                cs__657.toInsertSql();
                didBubble_8841 = false;
            } catch (RuntimeException ignored$4) {
                didBubble_8841 = true;
            }
            didBubble__658 = didBubble_8841;
            Supplier<String> fn__8238 = () -> "invalid changeset should bubble";
            test_43.assert_(didBubble__658, fn__8238);
        } finally {
            test_43.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toInsertSqlEnforcesNonNullableFieldsIndependentlyOfIsValid__1340() {
        Test test_44 = new Test();
        try {
            TableDef strictTable__660 = new TableDef(SrcTest.csid__441("posts"), List.of(new FieldDef(SrcTest.csid__441("title"), new StringField(), false), new FieldDef(SrcTest.csid__441("body"), new StringField(), true)));
            Map<String, String> params__661 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("body", "hello")));
            SafeIdentifier t_8231 = SrcTest.csid__441("body");
            Changeset cs__662 = SrcGlobal.changeset(strictTable__660, params__661).cast(List.of(t_8231));
            boolean t_8233 = cs__662.isValid();
            Supplier<String> fn__8220 = () -> "changeset should appear valid (no explicit validation run)";
            test_44.assert_(t_8233, fn__8220);
            boolean didBubble__663;
            boolean didBubble_8842;
            try {
                cs__662.toInsertSql();
                didBubble_8842 = false;
            } catch (RuntimeException ignored$5) {
                didBubble_8842 = true;
            }
            didBubble__663 = didBubble_8842;
            Supplier<String> fn__8219 = () -> "toInsertSql should enforce nullable regardless of isValid";
            test_44.assert_(didBubble__663, fn__8219);
        } finally {
            test_44.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlProducesCorrectSql__1341() {
        Test test_45 = new Test();
        try {
            Map<String, String> params__665 = Core.mapConstructor(List.of(new SimpleImmutableEntry<>("name", "Bob")));
            TableDef t_8210 = SrcTest.userTable__442();
            SafeIdentifier t_8211 = SrcTest.csid__441("name");
            Changeset cs__666 = SrcGlobal.changeset(t_8210, params__665).cast(List.of(t_8211)).validateRequired(List.of(SrcTest.csid__441("name")));
            SqlFragment t_4535;
            t_4535 = cs__666.toUpdateSql(42);
            SqlFragment sqlFrag__667 = t_4535;
            String s__668 = sqlFrag__667.toString();
            boolean t_8217 = s__668.equals("UPDATE users SET name = 'Bob' WHERE id = 42");
            Supplier<String> fn__8207 = () -> "got: " + s__668;
            test_45.assert_(t_8217, fn__8207);
        } finally {
            test_45.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void toUpdateSqlBubblesOnInvalidChangeset__1342() {
        Test test_46 = new Test();
        try {
            Map<String, String> params__670 = Core.mapConstructor(List.of());
            TableDef t_8200 = SrcTest.userTable__442();
            SafeIdentifier t_8201 = SrcTest.csid__441("name");
            Changeset cs__671 = SrcGlobal.changeset(t_8200, params__670).cast(List.of(t_8201)).validateRequired(List.of(SrcTest.csid__441("name")));
            boolean didBubble__672;
            boolean didBubble_8843;
            try {
                cs__671.toUpdateSql(1);
                didBubble_8843 = false;
            } catch (RuntimeException ignored$6) {
                didBubble_8843 = true;
            }
            didBubble__672 = didBubble_8843;
            Supplier<String> fn__8198 = () -> "invalid changeset should bubble";
            test_46.assert_(didBubble__672, fn__8198);
        } finally {
            test_46.softFailToHard();
        }
    }
    static SafeIdentifier sid__443(String name__886) {
        SafeIdentifier t_4188;
        t_4188 = SrcGlobal.safeIdentifier(name__886);
        return t_4188;
    }
    @org.junit.jupiter.api.Test public void bareFromProducesSelect__1391() {
        Test test_47 = new Test();
        try {
            Query q__889 = SrcGlobal.from(SrcTest.sid__443("users"));
            boolean t_7866 = q__889.toSql().toString().equals("SELECT * FROM users");
            Supplier<String> fn__7861 = () -> "bare query";
            test_47.assert_(t_7866, fn__7861);
        } finally {
            test_47.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectRestrictsColumns__1392() {
        Test test_48 = new Test();
        try {
            SafeIdentifier t_7852 = SrcTest.sid__443("users");
            SafeIdentifier t_7853 = SrcTest.sid__443("id");
            SafeIdentifier t_7854 = SrcTest.sid__443("name");
            Query q__891 = SrcGlobal.from(t_7852).select(List.of(t_7853, t_7854));
            boolean t_7859 = q__891.toSql().toString().equals("SELECT id, name FROM users");
            Supplier<String> fn__7851 = () -> "select columns";
            test_48.assert_(t_7859, fn__7851);
        } finally {
            test_48.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithIntValue__1393() {
        Test test_49 = new Test();
        try {
            SafeIdentifier t_7840 = SrcTest.sid__443("users");
            SqlBuilder t_7841 = new SqlBuilder();
            t_7841.appendSafe("age > ");
            t_7841.appendInt32(18);
            SqlFragment t_7844 = t_7841.getAccumulated();
            Query q__893 = SrcGlobal.from(t_7840).where(t_7844);
            boolean t_7849 = q__893.toSql().toString().equals("SELECT * FROM users WHERE age > 18");
            Supplier<String> fn__7839 = () -> "where int";
            test_49.assert_(t_7849, fn__7839);
        } finally {
            test_49.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereAddsConditionWithBoolValue__1395() {
        Test test_50 = new Test();
        try {
            SafeIdentifier t_7828 = SrcTest.sid__443("users");
            SqlBuilder t_7829 = new SqlBuilder();
            t_7829.appendSafe("active = ");
            t_7829.appendBoolean(true);
            SqlFragment t_7832 = t_7829.getAccumulated();
            Query q__895 = SrcGlobal.from(t_7828).where(t_7832);
            boolean t_7837 = q__895.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE");
            Supplier<String> fn__7827 = () -> "where bool";
            test_50.assert_(t_7837, fn__7827);
        } finally {
            test_50.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedWhereUsesAnd__1397() {
        Test test_51 = new Test();
        try {
            SafeIdentifier t_7811 = SrcTest.sid__443("users");
            SqlBuilder t_7812 = new SqlBuilder();
            t_7812.appendSafe("age > ");
            t_7812.appendInt32(18);
            SqlFragment t_7815 = t_7812.getAccumulated();
            Query t_7816 = SrcGlobal.from(t_7811).where(t_7815);
            SqlBuilder t_7817 = new SqlBuilder();
            t_7817.appendSafe("active = ");
            t_7817.appendBoolean(true);
            Query q__897 = t_7816.where(t_7817.getAccumulated());
            boolean t_7825 = q__897.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE");
            Supplier<String> fn__7810 = () -> "chained where";
            test_51.assert_(t_7825, fn__7810);
        } finally {
            test_51.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByAsc__1400() {
        Test test_52 = new Test();
        try {
            SafeIdentifier t_7802 = SrcTest.sid__443("users");
            SafeIdentifier t_7803 = SrcTest.sid__443("name");
            Query q__899 = SrcGlobal.from(t_7802).orderBy(t_7803, true);
            boolean t_7808 = q__899.toSql().toString().equals("SELECT * FROM users ORDER BY name ASC");
            Supplier<String> fn__7801 = () -> "order asc";
            test_52.assert_(t_7808, fn__7801);
        } finally {
            test_52.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orderByDesc__1401() {
        Test test_53 = new Test();
        try {
            SafeIdentifier t_7793 = SrcTest.sid__443("users");
            SafeIdentifier t_7794 = SrcTest.sid__443("created_at");
            Query q__901 = SrcGlobal.from(t_7793).orderBy(t_7794, false);
            boolean t_7799 = q__901.toSql().toString().equals("SELECT * FROM users ORDER BY created_at DESC");
            Supplier<String> fn__7792 = () -> "order desc";
            test_53.assert_(t_7799, fn__7792);
        } finally {
            test_53.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitAndOffset__1402() {
        Test test_54 = new Test();
        try {
            Query t_4122;
            t_4122 = SrcGlobal.from(SrcTest.sid__443("users")).limit(10);
            Query t_4123;
            t_4123 = t_4122.offset(20);
            Query q__903 = t_4123;
            boolean t_7790 = q__903.toSql().toString().equals("SELECT * FROM users LIMIT 10 OFFSET 20");
            Supplier<String> fn__7785 = () -> "limit/offset";
            test_54.assert_(t_7790, fn__7785);
        } finally {
            test_54.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void limitBubblesOnNegative__1403() {
        Test test_55 = new Test();
        try {
            boolean didBubble__905;
            boolean didBubble_8844;
            try {
                SrcGlobal.from(SrcTest.sid__443("users")).limit(-1);
                didBubble_8844 = false;
            } catch (RuntimeException ignored$7) {
                didBubble_8844 = true;
            }
            didBubble__905 = didBubble_8844;
            Supplier<String> fn__7781 = () -> "negative limit should bubble";
            test_55.assert_(didBubble__905, fn__7781);
        } finally {
            test_55.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void offsetBubblesOnNegative__1404() {
        Test test_56 = new Test();
        try {
            boolean didBubble__907;
            boolean didBubble_8845;
            try {
                SrcGlobal.from(SrcTest.sid__443("users")).offset(-1);
                didBubble_8845 = false;
            } catch (RuntimeException ignored$8) {
                didBubble_8845 = true;
            }
            didBubble__907 = didBubble_8845;
            Supplier<String> fn__7777 = () -> "negative offset should bubble";
            test_56.assert_(didBubble__907, fn__7777);
        } finally {
            test_56.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void complexComposedQuery__1405() {
        Test test_57 = new Test();
        try {
            int minAge__909 = 21;
            SafeIdentifier t_7755 = SrcTest.sid__443("users");
            SafeIdentifier t_7756 = SrcTest.sid__443("id");
            SafeIdentifier t_7757 = SrcTest.sid__443("name");
            SafeIdentifier t_7758 = SrcTest.sid__443("email");
            Query t_7759 = SrcGlobal.from(t_7755).select(List.of(t_7756, t_7757, t_7758));
            SqlBuilder t_7760 = new SqlBuilder();
            t_7760.appendSafe("age >= ");
            t_7760.appendInt32(21);
            Query t_7764 = t_7759.where(t_7760.getAccumulated());
            SqlBuilder t_7765 = new SqlBuilder();
            t_7765.appendSafe("active = ");
            t_7765.appendBoolean(true);
            Query t_4108;
            t_4108 = t_7764.where(t_7765.getAccumulated()).orderBy(SrcTest.sid__443("name"), true).limit(25);
            Query t_4109;
            t_4109 = t_4108.offset(0);
            Query q__910 = t_4109;
            boolean t_7775 = q__910.toSql().toString().equals("SELECT id, name, email FROM users WHERE age >= 21 AND active = TRUE ORDER BY name ASC LIMIT 25 OFFSET 0");
            Supplier<String> fn__7754 = () -> "complex query";
            test_57.assert_(t_7775, fn__7754);
        } finally {
            test_57.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlAppliesDefaultLimitWhenNoneSet__1408() {
        Test test_58 = new Test();
        try {
            Query q__912 = SrcGlobal.from(SrcTest.sid__443("users"));
            SqlFragment t_4085;
            t_4085 = q__912.safeToSql(100);
            SqlFragment t_4086 = t_4085;
            String s__913 = t_4086.toString();
            boolean t_7752 = s__913.equals("SELECT * FROM users LIMIT 100");
            Supplier<String> fn__7748 = () -> "should have limit: " + s__913;
            test_58.assert_(t_7752, fn__7748);
        } finally {
            test_58.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlRespectsExplicitLimit__1409() {
        Test test_59 = new Test();
        try {
            Query t_4077;
            t_4077 = SrcGlobal.from(SrcTest.sid__443("users")).limit(5);
            Query q__915 = t_4077;
            SqlFragment t_4080;
            t_4080 = q__915.safeToSql(100);
            SqlFragment t_4081 = t_4080;
            String s__916 = t_4081.toString();
            boolean t_7746 = s__916.equals("SELECT * FROM users LIMIT 5");
            Supplier<String> fn__7742 = () -> "explicit limit preserved: " + s__916;
            test_59.assert_(t_7746, fn__7742);
        } finally {
            test_59.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeToSqlBubblesOnNegativeDefaultLimit__1410() {
        Test test_60 = new Test();
        try {
            boolean didBubble__918;
            boolean didBubble_8846;
            try {
                SrcGlobal.from(SrcTest.sid__443("users")).safeToSql(-1);
                didBubble_8846 = false;
            } catch (RuntimeException ignored$9) {
                didBubble_8846 = true;
            }
            didBubble__918 = didBubble_8846;
            Supplier<String> fn__7738 = () -> "negative defaultLimit should bubble";
            test_60.assert_(didBubble__918, fn__7738);
        } finally {
            test_60.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereWithInjectionAttemptInStringValueIsEscaped__1411() {
        Test test_61 = new Test();
        try {
            String evil__920 = "'; DROP TABLE users; --";
            SafeIdentifier t_7722 = SrcTest.sid__443("users");
            SqlBuilder t_7723 = new SqlBuilder();
            t_7723.appendSafe("name = ");
            t_7723.appendString("'; DROP TABLE users; --");
            SqlFragment t_7726 = t_7723.getAccumulated();
            Query q__921 = SrcGlobal.from(t_7722).where(t_7726);
            String s__922 = q__921.toSql().toString();
            boolean t_7731 = s__922.indexOf("''") >= 0;
            Supplier<String> fn__7721 = () -> "quotes must be doubled: " + s__922;
            test_61.assert_(t_7731, fn__7721);
            boolean t_7735 = s__922.indexOf("SELECT * FROM users WHERE name =") >= 0;
            Supplier<String> fn__7720 = () -> "structure intact: " + s__922;
            test_61.assert_(t_7735, fn__7720);
        } finally {
            test_61.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsUserSuppliedTableNameWithMetacharacters__1413() {
        Test test_62 = new Test();
        try {
            String attack__924 = "users; DROP TABLE users; --";
            boolean didBubble__925;
            boolean didBubble_8847;
            try {
                SrcGlobal.safeIdentifier("users; DROP TABLE users; --");
                didBubble_8847 = false;
            } catch (RuntimeException ignored$10) {
                didBubble_8847 = true;
            }
            didBubble__925 = didBubble_8847;
            Supplier<String> fn__7717 = () -> "metacharacter-containing name must be rejected at construction";
            test_62.assert_(didBubble__925, fn__7717);
        } finally {
            test_62.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void innerJoinProducesInnerJoin__1414() {
        Test test_63 = new Test();
        try {
            SafeIdentifier t_7706 = SrcTest.sid__443("users");
            SafeIdentifier t_7707 = SrcTest.sid__443("orders");
            SqlBuilder t_7708 = new SqlBuilder();
            t_7708.appendSafe("users.id = orders.user_id");
            SqlFragment t_7710 = t_7708.getAccumulated();
            Query q__927 = SrcGlobal.from(t_7706).innerJoin(t_7707, t_7710);
            boolean t_7715 = q__927.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__7705 = () -> "inner join";
            test_63.assert_(t_7715, fn__7705);
        } finally {
            test_63.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void leftJoinProducesLeftJoin__1416() {
        Test test_64 = new Test();
        try {
            SafeIdentifier t_7694 = SrcTest.sid__443("users");
            SafeIdentifier t_7695 = SrcTest.sid__443("profiles");
            SqlBuilder t_7696 = new SqlBuilder();
            t_7696.appendSafe("users.id = profiles.user_id");
            SqlFragment t_7698 = t_7696.getAccumulated();
            Query q__929 = SrcGlobal.from(t_7694).leftJoin(t_7695, t_7698);
            boolean t_7703 = q__929.toSql().toString().equals("SELECT * FROM users LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__7693 = () -> "left join";
            test_64.assert_(t_7703, fn__7693);
        } finally {
            test_64.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void rightJoinProducesRightJoin__1418() {
        Test test_65 = new Test();
        try {
            SafeIdentifier t_7682 = SrcTest.sid__443("orders");
            SafeIdentifier t_7683 = SrcTest.sid__443("users");
            SqlBuilder t_7684 = new SqlBuilder();
            t_7684.appendSafe("orders.user_id = users.id");
            SqlFragment t_7686 = t_7684.getAccumulated();
            Query q__931 = SrcGlobal.from(t_7682).rightJoin(t_7683, t_7686);
            boolean t_7691 = q__931.toSql().toString().equals("SELECT * FROM orders RIGHT JOIN users ON orders.user_id = users.id");
            Supplier<String> fn__7681 = () -> "right join";
            test_65.assert_(t_7691, fn__7681);
        } finally {
            test_65.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullJoinProducesFullOuterJoin__1420() {
        Test test_66 = new Test();
        try {
            SafeIdentifier t_7670 = SrcTest.sid__443("users");
            SafeIdentifier t_7671 = SrcTest.sid__443("orders");
            SqlBuilder t_7672 = new SqlBuilder();
            t_7672.appendSafe("users.id = orders.user_id");
            SqlFragment t_7674 = t_7672.getAccumulated();
            Query q__933 = SrcGlobal.from(t_7670).fullJoin(t_7671, t_7674);
            boolean t_7679 = q__933.toSql().toString().equals("SELECT * FROM users FULL OUTER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__7669 = () -> "full join";
            test_66.assert_(t_7679, fn__7669);
        } finally {
            test_66.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void chainedJoins__1422() {
        Test test_67 = new Test();
        try {
            SafeIdentifier t_7653 = SrcTest.sid__443("users");
            SafeIdentifier t_7654 = SrcTest.sid__443("orders");
            SqlBuilder t_7655 = new SqlBuilder();
            t_7655.appendSafe("users.id = orders.user_id");
            SqlFragment t_7657 = t_7655.getAccumulated();
            Query t_7658 = SrcGlobal.from(t_7653).innerJoin(t_7654, t_7657);
            SafeIdentifier t_7659 = SrcTest.sid__443("profiles");
            SqlBuilder t_7660 = new SqlBuilder();
            t_7660.appendSafe("users.id = profiles.user_id");
            Query q__935 = t_7658.leftJoin(t_7659, t_7660.getAccumulated());
            boolean t_7667 = q__935.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id LEFT JOIN profiles ON users.id = profiles.user_id");
            Supplier<String> fn__7652 = () -> "chained joins";
            test_67.assert_(t_7667, fn__7652);
        } finally {
            test_67.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithWhereAndOrderBy__1425() {
        Test test_68 = new Test();
        try {
            SafeIdentifier t_7634 = SrcTest.sid__443("users");
            SafeIdentifier t_7635 = SrcTest.sid__443("orders");
            SqlBuilder t_7636 = new SqlBuilder();
            t_7636.appendSafe("users.id = orders.user_id");
            SqlFragment t_7638 = t_7636.getAccumulated();
            Query t_7639 = SrcGlobal.from(t_7634).innerJoin(t_7635, t_7638);
            SqlBuilder t_7640 = new SqlBuilder();
            t_7640.appendSafe("orders.total > ");
            t_7640.appendInt32(100);
            Query t_3992;
            t_3992 = t_7639.where(t_7640.getAccumulated()).orderBy(SrcTest.sid__443("name"), true).limit(10);
            Query q__937 = t_3992;
            boolean t_7650 = q__937.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100 ORDER BY name ASC LIMIT 10");
            Supplier<String> fn__7633 = () -> "join with where/order/limit";
            test_68.assert_(t_7650, fn__7633);
        } finally {
            test_68.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void colHelperProducesQualifiedReference__1428() {
        Test test_69 = new Test();
        try {
            SqlFragment c__939 = SrcGlobal.col(SrcTest.sid__443("users"), SrcTest.sid__443("id"));
            boolean t_7631 = c__939.toString().equals("users.id");
            Supplier<String> fn__7625 = () -> "col helper";
            test_69.assert_(t_7631, fn__7625);
        } finally {
            test_69.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void joinWithColHelper__1429() {
        Test test_70 = new Test();
        try {
            SqlFragment onCond__941 = SrcGlobal.col(SrcTest.sid__443("users"), SrcTest.sid__443("id"));
            SqlBuilder b__942 = new SqlBuilder();
            b__942.appendFragment(onCond__941);
            b__942.appendSafe(" = ");
            b__942.appendFragment(SrcGlobal.col(SrcTest.sid__443("orders"), SrcTest.sid__443("user_id")));
            SafeIdentifier t_7616 = SrcTest.sid__443("users");
            SafeIdentifier t_7617 = SrcTest.sid__443("orders");
            SqlFragment t_7618 = b__942.getAccumulated();
            Query q__943 = SrcGlobal.from(t_7616).innerJoin(t_7617, t_7618);
            boolean t_7623 = q__943.toSql().toString().equals("SELECT * FROM users INNER JOIN orders ON users.id = orders.user_id");
            Supplier<String> fn__7605 = () -> "join with col";
            test_70.assert_(t_7623, fn__7605);
        } finally {
            test_70.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orWhereBasic__1430() {
        Test test_71 = new Test();
        try {
            SafeIdentifier t_7594 = SrcTest.sid__443("users");
            SqlBuilder t_7595 = new SqlBuilder();
            t_7595.appendSafe("status = ");
            t_7595.appendString("active");
            SqlFragment t_7598 = t_7595.getAccumulated();
            Query q__945 = SrcGlobal.from(t_7594).orWhere(t_7598);
            boolean t_7603 = q__945.toSql().toString().equals("SELECT * FROM users WHERE status = 'active'");
            Supplier<String> fn__7593 = () -> "orWhere basic";
            test_71.assert_(t_7603, fn__7593);
        } finally {
            test_71.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereThenOrWhere__1432() {
        Test test_72 = new Test();
        try {
            SafeIdentifier t_7577 = SrcTest.sid__443("users");
            SqlBuilder t_7578 = new SqlBuilder();
            t_7578.appendSafe("age > ");
            t_7578.appendInt32(18);
            SqlFragment t_7581 = t_7578.getAccumulated();
            Query t_7582 = SrcGlobal.from(t_7577).where(t_7581);
            SqlBuilder t_7583 = new SqlBuilder();
            t_7583.appendSafe("vip = ");
            t_7583.appendBoolean(true);
            Query q__947 = t_7582.orWhere(t_7583.getAccumulated());
            boolean t_7591 = q__947.toSql().toString().equals("SELECT * FROM users WHERE age > 18 OR vip = TRUE");
            Supplier<String> fn__7576 = () -> "where then orWhere";
            test_72.assert_(t_7591, fn__7576);
        } finally {
            test_72.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void multipleOrWhere__1435() {
        Test test_73 = new Test();
        try {
            SafeIdentifier t_7555 = SrcTest.sid__443("users");
            SqlBuilder t_7556 = new SqlBuilder();
            t_7556.appendSafe("active = ");
            t_7556.appendBoolean(true);
            SqlFragment t_7559 = t_7556.getAccumulated();
            Query t_7560 = SrcGlobal.from(t_7555).where(t_7559);
            SqlBuilder t_7561 = new SqlBuilder();
            t_7561.appendSafe("role = ");
            t_7561.appendString("admin");
            Query t_7565 = t_7560.orWhere(t_7561.getAccumulated());
            SqlBuilder t_7566 = new SqlBuilder();
            t_7566.appendSafe("role = ");
            t_7566.appendString("moderator");
            Query q__949 = t_7565.orWhere(t_7566.getAccumulated());
            boolean t_7574 = q__949.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE OR role = 'admin' OR role = 'moderator'");
            Supplier<String> fn__7554 = () -> "multiple orWhere";
            test_73.assert_(t_7574, fn__7554);
        } finally {
            test_73.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void mixedWhereAndOrWhere__1439() {
        Test test_74 = new Test();
        try {
            SafeIdentifier t_7533 = SrcTest.sid__443("users");
            SqlBuilder t_7534 = new SqlBuilder();
            t_7534.appendSafe("age > ");
            t_7534.appendInt32(18);
            SqlFragment t_7537 = t_7534.getAccumulated();
            Query t_7538 = SrcGlobal.from(t_7533).where(t_7537);
            SqlBuilder t_7539 = new SqlBuilder();
            t_7539.appendSafe("active = ");
            t_7539.appendBoolean(true);
            Query t_7543 = t_7538.where(t_7539.getAccumulated());
            SqlBuilder t_7544 = new SqlBuilder();
            t_7544.appendSafe("vip = ");
            t_7544.appendBoolean(true);
            Query q__951 = t_7543.orWhere(t_7544.getAccumulated());
            boolean t_7552 = q__951.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND active = TRUE OR vip = TRUE");
            Supplier<String> fn__7532 = () -> "mixed where and orWhere";
            test_74.assert_(t_7552, fn__7532);
        } finally {
            test_74.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNull__1443() {
        Test test_75 = new Test();
        try {
            SafeIdentifier t_7524 = SrcTest.sid__443("users");
            SafeIdentifier t_7525 = SrcTest.sid__443("deleted_at");
            Query q__953 = SrcGlobal.from(t_7524).whereNull(t_7525);
            boolean t_7530 = q__953.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL");
            Supplier<String> fn__7523 = () -> "whereNull";
            test_75.assert_(t_7530, fn__7523);
        } finally {
            test_75.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNull__1444() {
        Test test_76 = new Test();
        try {
            SafeIdentifier t_7515 = SrcTest.sid__443("users");
            SafeIdentifier t_7516 = SrcTest.sid__443("email");
            Query q__955 = SrcGlobal.from(t_7515).whereNotNull(t_7516);
            boolean t_7521 = q__955.toSql().toString().equals("SELECT * FROM users WHERE email IS NOT NULL");
            Supplier<String> fn__7514 = () -> "whereNotNull";
            test_76.assert_(t_7521, fn__7514);
        } finally {
            test_76.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNullChainedWithWhere__1445() {
        Test test_77 = new Test();
        try {
            SafeIdentifier t_7501 = SrcTest.sid__443("users");
            SqlBuilder t_7502 = new SqlBuilder();
            t_7502.appendSafe("active = ");
            t_7502.appendBoolean(true);
            SqlFragment t_7505 = t_7502.getAccumulated();
            Query q__957 = SrcGlobal.from(t_7501).where(t_7505).whereNull(SrcTest.sid__443("deleted_at"));
            boolean t_7512 = q__957.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND deleted_at IS NULL");
            Supplier<String> fn__7500 = () -> "whereNull chained";
            test_77.assert_(t_7512, fn__7500);
        } finally {
            test_77.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotNullChainedWithOrWhere__1447() {
        Test test_78 = new Test();
        try {
            SafeIdentifier t_7487 = SrcTest.sid__443("users");
            SafeIdentifier t_7488 = SrcTest.sid__443("deleted_at");
            Query t_7489 = SrcGlobal.from(t_7487).whereNull(t_7488);
            SqlBuilder t_7490 = new SqlBuilder();
            t_7490.appendSafe("role = ");
            t_7490.appendString("admin");
            Query q__959 = t_7489.orWhere(t_7490.getAccumulated());
            boolean t_7498 = q__959.toSql().toString().equals("SELECT * FROM users WHERE deleted_at IS NULL OR role = 'admin'");
            Supplier<String> fn__7486 = () -> "whereNotNull with orWhere";
            test_78.assert_(t_7498, fn__7486);
        } finally {
            test_78.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithIntValues__1449() {
        Test test_79 = new Test();
        try {
            SafeIdentifier t_7475 = SrcTest.sid__443("users");
            SafeIdentifier t_7476 = SrcTest.sid__443("id");
            SqlInt32 t_7477 = new SqlInt32(1);
            SqlInt32 t_7478 = new SqlInt32(2);
            SqlInt32 t_7479 = new SqlInt32(3);
            Query q__961 = SrcGlobal.from(t_7475).whereIn(t_7476, List.of(t_7477, t_7478, t_7479));
            boolean t_7484 = q__961.toSql().toString().equals("SELECT * FROM users WHERE id IN (1, 2, 3)");
            Supplier<String> fn__7474 = () -> "whereIn ints";
            test_79.assert_(t_7484, fn__7474);
        } finally {
            test_79.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithStringValuesEscaping__1450() {
        Test test_80 = new Test();
        try {
            SafeIdentifier t_7464 = SrcTest.sid__443("users");
            SafeIdentifier t_7465 = SrcTest.sid__443("name");
            SqlString t_7466 = new SqlString("Alice");
            SqlString t_7467 = new SqlString("Bob's");
            Query q__963 = SrcGlobal.from(t_7464).whereIn(t_7465, List.of(t_7466, t_7467));
            boolean t_7472 = q__963.toSql().toString().equals("SELECT * FROM users WHERE name IN ('Alice', 'Bob''s')");
            Supplier<String> fn__7463 = () -> "whereIn strings";
            test_80.assert_(t_7472, fn__7463);
        } finally {
            test_80.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInWithEmptyListProduces1_0__1451() {
        Test test_81 = new Test();
        try {
            SafeIdentifier t_7455 = SrcTest.sid__443("users");
            SafeIdentifier t_7456 = SrcTest.sid__443("id");
            Query q__965 = SrcGlobal.from(t_7455).whereIn(t_7456, List.of());
            boolean t_7461 = q__965.toSql().toString().equals("SELECT * FROM users WHERE 1 = 0");
            Supplier<String> fn__7454 = () -> "whereIn empty";
            test_81.assert_(t_7461, fn__7454);
        } finally {
            test_81.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInChained__1452() {
        Test test_82 = new Test();
        try {
            SafeIdentifier t_7439 = SrcTest.sid__443("users");
            SqlBuilder t_7440 = new SqlBuilder();
            t_7440.appendSafe("active = ");
            t_7440.appendBoolean(true);
            SqlFragment t_7443 = t_7440.getAccumulated();
            Query q__967 = SrcGlobal.from(t_7439).where(t_7443).whereIn(SrcTest.sid__443("role"), List.of(new SqlString("admin"), new SqlString("user")));
            boolean t_7452 = q__967.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND role IN ('admin', 'user')");
            Supplier<String> fn__7438 = () -> "whereIn chained";
            test_82.assert_(t_7452, fn__7438);
        } finally {
            test_82.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereInSingleElement__1454() {
        Test test_83 = new Test();
        try {
            SafeIdentifier t_7429 = SrcTest.sid__443("users");
            SafeIdentifier t_7430 = SrcTest.sid__443("id");
            SqlInt32 t_7431 = new SqlInt32(42);
            Query q__969 = SrcGlobal.from(t_7429).whereIn(t_7430, List.of(t_7431));
            boolean t_7436 = q__969.toSql().toString().equals("SELECT * FROM users WHERE id IN (42)");
            Supplier<String> fn__7428 = () -> "whereIn single";
            test_83.assert_(t_7436, fn__7428);
        } finally {
            test_83.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotBasic__1455() {
        Test test_84 = new Test();
        try {
            SafeIdentifier t_7417 = SrcTest.sid__443("users");
            SqlBuilder t_7418 = new SqlBuilder();
            t_7418.appendSafe("active = ");
            t_7418.appendBoolean(true);
            SqlFragment t_7421 = t_7418.getAccumulated();
            Query q__971 = SrcGlobal.from(t_7417).whereNot(t_7421);
            boolean t_7426 = q__971.toSql().toString().equals("SELECT * FROM users WHERE NOT (active = TRUE)");
            Supplier<String> fn__7416 = () -> "whereNot";
            test_84.assert_(t_7426, fn__7416);
        } finally {
            test_84.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereNotChained__1457() {
        Test test_85 = new Test();
        try {
            SafeIdentifier t_7400 = SrcTest.sid__443("users");
            SqlBuilder t_7401 = new SqlBuilder();
            t_7401.appendSafe("age > ");
            t_7401.appendInt32(18);
            SqlFragment t_7404 = t_7401.getAccumulated();
            Query t_7405 = SrcGlobal.from(t_7400).where(t_7404);
            SqlBuilder t_7406 = new SqlBuilder();
            t_7406.appendSafe("banned = ");
            t_7406.appendBoolean(true);
            Query q__973 = t_7405.whereNot(t_7406.getAccumulated());
            boolean t_7414 = q__973.toSql().toString().equals("SELECT * FROM users WHERE age > 18 AND NOT (banned = TRUE)");
            Supplier<String> fn__7399 = () -> "whereNot chained";
            test_85.assert_(t_7414, fn__7399);
        } finally {
            test_85.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenIntegers__1460() {
        Test test_86 = new Test();
        try {
            SafeIdentifier t_7389 = SrcTest.sid__443("users");
            SafeIdentifier t_7390 = SrcTest.sid__443("age");
            SqlInt32 t_7391 = new SqlInt32(18);
            SqlInt32 t_7392 = new SqlInt32(65);
            Query q__975 = SrcGlobal.from(t_7389).whereBetween(t_7390, t_7391, t_7392);
            boolean t_7397 = q__975.toSql().toString().equals("SELECT * FROM users WHERE age BETWEEN 18 AND 65");
            Supplier<String> fn__7388 = () -> "whereBetween ints";
            test_86.assert_(t_7397, fn__7388);
        } finally {
            test_86.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereBetweenChained__1461() {
        Test test_87 = new Test();
        try {
            SafeIdentifier t_7373 = SrcTest.sid__443("users");
            SqlBuilder t_7374 = new SqlBuilder();
            t_7374.appendSafe("active = ");
            t_7374.appendBoolean(true);
            SqlFragment t_7377 = t_7374.getAccumulated();
            Query q__977 = SrcGlobal.from(t_7373).where(t_7377).whereBetween(SrcTest.sid__443("age"), new SqlInt32(21), new SqlInt32(30));
            boolean t_7386 = q__977.toSql().toString().equals("SELECT * FROM users WHERE active = TRUE AND age BETWEEN 21 AND 30");
            Supplier<String> fn__7372 = () -> "whereBetween chained";
            test_87.assert_(t_7386, fn__7372);
        } finally {
            test_87.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeBasic__1463() {
        Test test_88 = new Test();
        try {
            SafeIdentifier t_7364 = SrcTest.sid__443("users");
            SafeIdentifier t_7365 = SrcTest.sid__443("name");
            Query q__979 = SrcGlobal.from(t_7364).whereLike(t_7365, "John%");
            boolean t_7370 = q__979.toSql().toString().equals("SELECT * FROM users WHERE name LIKE 'John%'");
            Supplier<String> fn__7363 = () -> "whereLike";
            test_88.assert_(t_7370, fn__7363);
        } finally {
            test_88.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereIlikeBasic__1464() {
        Test test_89 = new Test();
        try {
            SafeIdentifier t_7355 = SrcTest.sid__443("users");
            SafeIdentifier t_7356 = SrcTest.sid__443("email");
            Query q__981 = SrcGlobal.from(t_7355).whereILike(t_7356, "%@gmail.com");
            boolean t_7361 = q__981.toSql().toString().equals("SELECT * FROM users WHERE email ILIKE '%@gmail.com'");
            Supplier<String> fn__7354 = () -> "whereILike";
            test_89.assert_(t_7361, fn__7354);
        } finally {
            test_89.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWithInjectionAttempt__1465() {
        Test test_90 = new Test();
        try {
            SafeIdentifier t_7341 = SrcTest.sid__443("users");
            SafeIdentifier t_7342 = SrcTest.sid__443("name");
            Query q__983 = SrcGlobal.from(t_7341).whereLike(t_7342, "'; DROP TABLE users; --");
            String s__984 = q__983.toSql().toString();
            boolean t_7347 = s__984.indexOf("''") >= 0;
            Supplier<String> fn__7340 = () -> "like injection escaped: " + s__984;
            test_90.assert_(t_7347, fn__7340);
            boolean t_7351 = s__984.indexOf("LIKE") >= 0;
            Supplier<String> fn__7339 = () -> "like structure intact: " + s__984;
            test_90.assert_(t_7351, fn__7339);
        } finally {
            test_90.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void whereLikeWildcardPatterns__1466() {
        Test test_91 = new Test();
        try {
            SafeIdentifier t_7331 = SrcTest.sid__443("users");
            SafeIdentifier t_7332 = SrcTest.sid__443("name");
            Query q__986 = SrcGlobal.from(t_7331).whereLike(t_7332, "%son%");
            boolean t_7337 = q__986.toSql().toString().equals("SELECT * FROM users WHERE name LIKE '%son%'");
            Supplier<String> fn__7330 = () -> "whereLike wildcard";
            test_91.assert_(t_7337, fn__7330);
        } finally {
            test_91.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countAllProducesCount__1467() {
        Test test_92 = new Test();
        try {
            SqlFragment f__988 = SrcGlobal.countAll();
            boolean t_7328 = f__988.toString().equals("COUNT(*)");
            Supplier<String> fn__7324 = () -> "countAll";
            test_92.assert_(t_7328, fn__7324);
        } finally {
            test_92.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countColProducesCountField__1468() {
        Test test_93 = new Test();
        try {
            SqlFragment f__990 = SrcGlobal.countCol(SrcTest.sid__443("id"));
            boolean t_7322 = f__990.toString().equals("COUNT(id)");
            Supplier<String> fn__7317 = () -> "countCol";
            test_93.assert_(t_7322, fn__7317);
        } finally {
            test_93.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sumColProducesSumField__1469() {
        Test test_94 = new Test();
        try {
            SqlFragment f__992 = SrcGlobal.sumCol(SrcTest.sid__443("amount"));
            boolean t_7315 = f__992.toString().equals("SUM(amount)");
            Supplier<String> fn__7310 = () -> "sumCol";
            test_94.assert_(t_7315, fn__7310);
        } finally {
            test_94.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void avgColProducesAvgField__1470() {
        Test test_95 = new Test();
        try {
            SqlFragment f__994 = SrcGlobal.avgCol(SrcTest.sid__443("price"));
            boolean t_7308 = f__994.toString().equals("AVG(price)");
            Supplier<String> fn__7303 = () -> "avgCol";
            test_95.assert_(t_7308, fn__7303);
        } finally {
            test_95.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void minColProducesMinField__1471() {
        Test test_96 = new Test();
        try {
            SqlFragment f__996 = SrcGlobal.minCol(SrcTest.sid__443("created_at"));
            boolean t_7301 = f__996.toString().equals("MIN(created_at)");
            Supplier<String> fn__7296 = () -> "minCol";
            test_96.assert_(t_7301, fn__7296);
        } finally {
            test_96.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void maxColProducesMaxField__1472() {
        Test test_97 = new Test();
        try {
            SqlFragment f__998 = SrcGlobal.maxCol(SrcTest.sid__443("score"));
            boolean t_7294 = f__998.toString().equals("MAX(score)");
            Supplier<String> fn__7289 = () -> "maxCol";
            test_97.assert_(t_7294, fn__7289);
        } finally {
            test_97.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithAggregate__1473() {
        Test test_98 = new Test();
        try {
            SafeIdentifier t_7281 = SrcTest.sid__443("orders");
            SqlFragment t_7282 = SrcGlobal.countAll();
            Query q__1000 = SrcGlobal.from(t_7281).selectExpr(List.of(t_7282));
            boolean t_7287 = q__1000.toSql().toString().equals("SELECT COUNT(*) FROM orders");
            Supplier<String> fn__7280 = () -> "selectExpr count";
            test_98.assert_(t_7287, fn__7280);
        } finally {
            test_98.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprWithMultipleExpressions__1474() {
        Test test_99 = new Test();
        try {
            SqlFragment nameFrag__1002 = SrcGlobal.col(SrcTest.sid__443("users"), SrcTest.sid__443("name"));
            SafeIdentifier t_7272 = SrcTest.sid__443("users");
            SqlFragment t_7273 = SrcGlobal.countAll();
            Query q__1003 = SrcGlobal.from(t_7272).selectExpr(List.of(nameFrag__1002, t_7273));
            boolean t_7278 = q__1003.toSql().toString().equals("SELECT users.name, COUNT(*) FROM users");
            Supplier<String> fn__7268 = () -> "selectExpr multi";
            test_99.assert_(t_7278, fn__7268);
        } finally {
            test_99.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void selectExprOverridesSelectedFields__1475() {
        Test test_100 = new Test();
        try {
            SafeIdentifier t_7257 = SrcTest.sid__443("users");
            SafeIdentifier t_7258 = SrcTest.sid__443("id");
            SafeIdentifier t_7259 = SrcTest.sid__443("name");
            Query q__1005 = SrcGlobal.from(t_7257).select(List.of(t_7258, t_7259)).selectExpr(List.of(SrcGlobal.countAll()));
            boolean t_7266 = q__1005.toSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__7256 = () -> "selectExpr overrides select";
            test_100.assert_(t_7266, fn__7256);
        } finally {
            test_100.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupBySingleField__1476() {
        Test test_101 = new Test();
        try {
            SafeIdentifier t_7243 = SrcTest.sid__443("orders");
            SqlFragment t_7246 = SrcGlobal.col(SrcTest.sid__443("orders"), SrcTest.sid__443("status"));
            SqlFragment t_7247 = SrcGlobal.countAll();
            Query q__1007 = SrcGlobal.from(t_7243).selectExpr(List.of(t_7246, t_7247)).groupBy(SrcTest.sid__443("status"));
            boolean t_7254 = q__1007.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status");
            Supplier<String> fn__7242 = () -> "groupBy single";
            test_101.assert_(t_7254, fn__7242);
        } finally {
            test_101.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void groupByMultipleFields__1477() {
        Test test_102 = new Test();
        try {
            SafeIdentifier t_7232 = SrcTest.sid__443("orders");
            SafeIdentifier t_7233 = SrcTest.sid__443("status");
            Query q__1009 = SrcGlobal.from(t_7232).groupBy(t_7233).groupBy(SrcTest.sid__443("category"));
            boolean t_7240 = q__1009.toSql().toString().equals("SELECT * FROM orders GROUP BY status, category");
            Supplier<String> fn__7231 = () -> "groupBy multiple";
            test_102.assert_(t_7240, fn__7231);
        } finally {
            test_102.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void havingBasic__1478() {
        Test test_103 = new Test();
        try {
            SafeIdentifier t_7213 = SrcTest.sid__443("orders");
            SqlFragment t_7216 = SrcGlobal.col(SrcTest.sid__443("orders"), SrcTest.sid__443("status"));
            SqlFragment t_7217 = SrcGlobal.countAll();
            Query t_7220 = SrcGlobal.from(t_7213).selectExpr(List.of(t_7216, t_7217)).groupBy(SrcTest.sid__443("status"));
            SqlBuilder t_7221 = new SqlBuilder();
            t_7221.appendSafe("COUNT(*) > ");
            t_7221.appendInt32(5);
            Query q__1011 = t_7220.having(t_7221.getAccumulated());
            boolean t_7229 = q__1011.toSql().toString().equals("SELECT orders.status, COUNT(*) FROM orders GROUP BY status HAVING COUNT(*) > 5");
            Supplier<String> fn__7212 = () -> "having basic";
            test_103.assert_(t_7229, fn__7212);
        } finally {
            test_103.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void orHaving__1480() {
        Test test_104 = new Test();
        try {
            SafeIdentifier t_7194 = SrcTest.sid__443("orders");
            SafeIdentifier t_7195 = SrcTest.sid__443("status");
            Query t_7196 = SrcGlobal.from(t_7194).groupBy(t_7195);
            SqlBuilder t_7197 = new SqlBuilder();
            t_7197.appendSafe("COUNT(*) > ");
            t_7197.appendInt32(5);
            Query t_7201 = t_7196.having(t_7197.getAccumulated());
            SqlBuilder t_7202 = new SqlBuilder();
            t_7202.appendSafe("SUM(total) > ");
            t_7202.appendInt32(1000);
            Query q__1013 = t_7201.orHaving(t_7202.getAccumulated());
            boolean t_7210 = q__1013.toSql().toString().equals("SELECT * FROM orders GROUP BY status HAVING COUNT(*) > 5 OR SUM(total) > 1000");
            Supplier<String> fn__7193 = () -> "orHaving";
            test_104.assert_(t_7210, fn__7193);
        } finally {
            test_104.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctBasic__1483() {
        Test test_105 = new Test();
        try {
            SafeIdentifier t_7184 = SrcTest.sid__443("users");
            SafeIdentifier t_7185 = SrcTest.sid__443("name");
            Query q__1015 = SrcGlobal.from(t_7184).select(List.of(t_7185)).distinct();
            boolean t_7191 = q__1015.toSql().toString().equals("SELECT DISTINCT name FROM users");
            Supplier<String> fn__7183 = () -> "distinct";
            test_105.assert_(t_7191, fn__7183);
        } finally {
            test_105.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void distinctWithWhere__1484() {
        Test test_106 = new Test();
        try {
            SafeIdentifier t_7169 = SrcTest.sid__443("users");
            SafeIdentifier t_7170 = SrcTest.sid__443("email");
            Query t_7171 = SrcGlobal.from(t_7169).select(List.of(t_7170));
            SqlBuilder t_7172 = new SqlBuilder();
            t_7172.appendSafe("active = ");
            t_7172.appendBoolean(true);
            Query q__1017 = t_7171.where(t_7172.getAccumulated()).distinct();
            boolean t_7181 = q__1017.toSql().toString().equals("SELECT DISTINCT email FROM users WHERE active = TRUE");
            Supplier<String> fn__7168 = () -> "distinct with where";
            test_106.assert_(t_7181, fn__7168);
        } finally {
            test_106.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlBare__1486() {
        Test test_107 = new Test();
        try {
            Query q__1019 = SrcGlobal.from(SrcTest.sid__443("users"));
            boolean t_7166 = q__1019.countSql().toString().equals("SELECT COUNT(*) FROM users");
            Supplier<String> fn__7161 = () -> "countSql bare";
            test_107.assert_(t_7166, fn__7161);
        } finally {
            test_107.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithWhere__1487() {
        Test test_108 = new Test();
        try {
            SafeIdentifier t_7150 = SrcTest.sid__443("users");
            SqlBuilder t_7151 = new SqlBuilder();
            t_7151.appendSafe("active = ");
            t_7151.appendBoolean(true);
            SqlFragment t_7154 = t_7151.getAccumulated();
            Query q__1021 = SrcGlobal.from(t_7150).where(t_7154);
            boolean t_7159 = q__1021.countSql().toString().equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__7149 = () -> "countSql with where";
            test_108.assert_(t_7159, fn__7149);
        } finally {
            test_108.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlWithJoin__1489() {
        Test test_109 = new Test();
        try {
            SafeIdentifier t_7133 = SrcTest.sid__443("users");
            SafeIdentifier t_7134 = SrcTest.sid__443("orders");
            SqlBuilder t_7135 = new SqlBuilder();
            t_7135.appendSafe("users.id = orders.user_id");
            SqlFragment t_7137 = t_7135.getAccumulated();
            Query t_7138 = SrcGlobal.from(t_7133).innerJoin(t_7134, t_7137);
            SqlBuilder t_7139 = new SqlBuilder();
            t_7139.appendSafe("orders.total > ");
            t_7139.appendInt32(100);
            Query q__1023 = t_7138.where(t_7139.getAccumulated());
            boolean t_7147 = q__1023.countSql().toString().equals("SELECT COUNT(*) FROM users INNER JOIN orders ON users.id = orders.user_id WHERE orders.total > 100");
            Supplier<String> fn__7132 = () -> "countSql with join";
            test_109.assert_(t_7147, fn__7132);
        } finally {
            test_109.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void countSqlDropsOrderByLimitOffset__1492() {
        Test test_110 = new Test();
        try {
            SafeIdentifier t_7119 = SrcTest.sid__443("users");
            SqlBuilder t_7120 = new SqlBuilder();
            t_7120.appendSafe("active = ");
            t_7120.appendBoolean(true);
            SqlFragment t_7123 = t_7120.getAccumulated();
            Query t_3568;
            t_3568 = SrcGlobal.from(t_7119).where(t_7123).orderBy(SrcTest.sid__443("name"), true).limit(10);
            Query t_3569;
            t_3569 = t_3568.offset(20);
            Query q__1025 = t_3569;
            String s__1026 = q__1025.countSql().toString();
            boolean t_7130 = s__1026.equals("SELECT COUNT(*) FROM users WHERE active = TRUE");
            Supplier<String> fn__7118 = () -> "countSql drops extras: " + s__1026;
            test_110.assert_(t_7130, fn__7118);
        } finally {
            test_110.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fullAggregationQuery__1494() {
        Test test_111 = new Test();
        try {
            SafeIdentifier t_7086 = SrcTest.sid__443("orders");
            SqlFragment t_7089 = SrcGlobal.col(SrcTest.sid__443("orders"), SrcTest.sid__443("status"));
            SqlFragment t_7090 = SrcGlobal.countAll();
            SqlFragment t_7092 = SrcGlobal.sumCol(SrcTest.sid__443("total"));
            Query t_7093 = SrcGlobal.from(t_7086).selectExpr(List.of(t_7089, t_7090, t_7092));
            SafeIdentifier t_7094 = SrcTest.sid__443("users");
            SqlBuilder t_7095 = new SqlBuilder();
            t_7095.appendSafe("orders.user_id = users.id");
            Query t_7098 = t_7093.innerJoin(t_7094, t_7095.getAccumulated());
            SqlBuilder t_7099 = new SqlBuilder();
            t_7099.appendSafe("users.active = ");
            t_7099.appendBoolean(true);
            Query t_7105 = t_7098.where(t_7099.getAccumulated()).groupBy(SrcTest.sid__443("status"));
            SqlBuilder t_7106 = new SqlBuilder();
            t_7106.appendSafe("COUNT(*) > ");
            t_7106.appendInt32(3);
            Query q__1028 = t_7105.having(t_7106.getAccumulated()).orderBy(SrcTest.sid__443("status"), true);
            String expected__1029 = "SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC";
            boolean t_7116 = q__1028.toSql().toString().equals("SELECT orders.status, COUNT(*), SUM(total) FROM orders INNER JOIN users ON orders.user_id = users.id WHERE users.active = TRUE GROUP BY status HAVING COUNT(*) > 3 ORDER BY status ASC");
            Supplier<String> fn__7085 = () -> "full aggregation";
            test_111.assert_(t_7116, fn__7085);
        } finally {
            test_111.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierAcceptsValidNames__1498() {
        Test test_118 = new Test();
        try {
            SafeIdentifier t_3522;
            t_3522 = SrcGlobal.safeIdentifier("user_name");
            SafeIdentifier id__1067 = t_3522;
            boolean t_7083 = id__1067.getSqlValue().equals("user_name");
            Supplier<String> fn__7080 = () -> "value should round-trip";
            test_118.assert_(t_7083, fn__7080);
        } finally {
            test_118.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsEmptyString__1499() {
        Test test_119 = new Test();
        try {
            boolean didBubble__1069;
            boolean didBubble_8848;
            try {
                SrcGlobal.safeIdentifier("");
                didBubble_8848 = false;
            } catch (RuntimeException ignored$11) {
                didBubble_8848 = true;
            }
            didBubble__1069 = didBubble_8848;
            Supplier<String> fn__7077 = () -> "empty string should bubble";
            test_119.assert_(didBubble__1069, fn__7077);
        } finally {
            test_119.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsLeadingDigit__1500() {
        Test test_120 = new Test();
        try {
            boolean didBubble__1071;
            boolean didBubble_8849;
            try {
                SrcGlobal.safeIdentifier("1col");
                didBubble_8849 = false;
            } catch (RuntimeException ignored$12) {
                didBubble_8849 = true;
            }
            didBubble__1071 = didBubble_8849;
            Supplier<String> fn__7074 = () -> "leading digit should bubble";
            test_120.assert_(didBubble__1071, fn__7074);
        } finally {
            test_120.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void safeIdentifierRejectsSqlMetacharacters__1501() {
        Test test_121 = new Test();
        try {
            List<String> cases__1073 = List.of("name); DROP TABLE", "col'", "a b", "a-b", "a.b", "a;b");
            Consumer<String> fn__7071 = c__1074 -> {
                boolean didBubble__1075;
                boolean didBubble_8850;
                try {
                    SrcGlobal.safeIdentifier(c__1074);
                    didBubble_8850 = false;
                } catch (RuntimeException ignored$13) {
                    didBubble_8850 = true;
                }
                didBubble__1075 = didBubble_8850;
                Supplier<String> fn__7068 = () -> "should reject: " + c__1074;
                test_121.assert_(didBubble__1075, fn__7068);
            };
            cases__1073.forEach(fn__7071);
        } finally {
            test_121.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupFound__1502() {
        Test test_122 = new Test();
        try {
            SafeIdentifier t_3499;
            t_3499 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_3500 = t_3499;
            SafeIdentifier t_3501;
            t_3501 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_3502 = t_3501;
            StringField t_7058 = new StringField();
            FieldDef t_7059 = new FieldDef(t_3502, t_7058, false);
            SafeIdentifier t_3505;
            t_3505 = SrcGlobal.safeIdentifier("age");
            SafeIdentifier t_3506 = t_3505;
            IntField t_7060 = new IntField();
            FieldDef t_7061 = new FieldDef(t_3506, t_7060, false);
            TableDef td__1077 = new TableDef(t_3500, List.of(t_7059, t_7061));
            FieldDef t_3510;
            t_3510 = td__1077.field("age");
            FieldDef f__1078 = t_3510;
            boolean t_7066 = f__1078.getName().getSqlValue().equals("age");
            Supplier<String> fn__7057 = () -> "should find age field";
            test_122.assert_(t_7066, fn__7057);
        } finally {
            test_122.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void tableDefFieldLookupNotFoundBubbles__1503() {
        Test test_123 = new Test();
        try {
            SafeIdentifier t_3490;
            t_3490 = SrcGlobal.safeIdentifier("users");
            SafeIdentifier t_3491 = t_3490;
            SafeIdentifier t_3492;
            t_3492 = SrcGlobal.safeIdentifier("name");
            SafeIdentifier t_3493 = t_3492;
            StringField t_7052 = new StringField();
            FieldDef t_7053 = new FieldDef(t_3493, t_7052, false);
            TableDef td__1080 = new TableDef(t_3491, List.of(t_7053));
            boolean didBubble__1081;
            boolean didBubble_8851;
            try {
                td__1080.field("nonexistent");
                didBubble_8851 = false;
            } catch (RuntimeException ignored$14) {
                didBubble_8851 = true;
            }
            didBubble__1081 = didBubble_8851;
            Supplier<String> fn__7051 = () -> "unknown field should bubble";
            test_123.assert_(didBubble__1081, fn__7051);
        } finally {
            test_123.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void fieldDefNullableFlag__1504() {
        Test test_124 = new Test();
        try {
            SafeIdentifier t_3478;
            t_3478 = SrcGlobal.safeIdentifier("email");
            SafeIdentifier t_3479 = t_3478;
            StringField t_7040 = new StringField();
            FieldDef required__1083 = new FieldDef(t_3479, t_7040, false);
            SafeIdentifier t_3482;
            t_3482 = SrcGlobal.safeIdentifier("bio");
            SafeIdentifier t_3483 = t_3482;
            StringField t_7042 = new StringField();
            FieldDef optional__1084 = new FieldDef(t_3483, t_7042, true);
            boolean t_7046 = !required__1083.isNullable();
            Supplier<String> fn__7039 = () -> "required field should not be nullable";
            test_124.assert_(t_7046, fn__7039);
            boolean t_7048 = optional__1084.isNullable();
            Supplier<String> fn__7038 = () -> "optional field should be nullable";
            test_124.assert_(t_7048, fn__7038);
        } finally {
            test_124.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEscaping__1505() {
        Test test_128 = new Test();
        try {
            Function<String, String> build__1210 = name__1212 -> {
                SqlBuilder t_7020 = new SqlBuilder();
                t_7020.appendSafe("select * from hi where name = ");
                t_7020.appendString(name__1212);
                return t_7020.getAccumulated().toString();
            };
            Function<String, String> buildWrong__1211 = name__1214 -> "select * from hi where name = '" + name__1214 + "'";
            String actual_1507 = build__1210.apply("world");
            boolean t_7030 = actual_1507.equals("select * from hi where name = 'world'");
            Supplier<String> fn__7027 = () -> "expected build(\"world\") == (" + "select * from hi where name = 'world'" + ") not (" + actual_1507 + ")";
            test_128.assert_(t_7030, fn__7027);
            String bobbyTables__1216 = "Robert'); drop table hi;--";
            String actual_1509 = build__1210.apply("Robert'); drop table hi;--");
            boolean t_7034 = actual_1509.equals("select * from hi where name = 'Robert''); drop table hi;--'");
            Supplier<String> fn__7026 = () -> "expected build(bobbyTables) == (" + "select * from hi where name = 'Robert''); drop table hi;--'" + ") not (" + actual_1509 + ")";
            test_128.assert_(t_7034, fn__7026);
            Supplier<String> fn__7025 = () -> "expected buildWrong(bobbyTables) == (select * from hi where name = 'Robert'); drop table hi;--') not (select * from hi where name = 'Robert'); drop table hi;--')";
            test_128.assert_(true, fn__7025);
        } finally {
            test_128.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void stringEdgeCases__1513() {
        Test test_129 = new Test();
        try {
            SqlBuilder t_6988 = new SqlBuilder();
            t_6988.appendSafe("v = ");
            t_6988.appendString("");
            String actual_1514 = t_6988.getAccumulated().toString();
            boolean t_6994 = actual_1514.equals("v = ''");
            Supplier<String> fn__6987 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"\").toString() == (" + "v = ''" + ") not (" + actual_1514 + ")";
            test_129.assert_(t_6994, fn__6987);
            SqlBuilder t_6996 = new SqlBuilder();
            t_6996.appendSafe("v = ");
            t_6996.appendString("a''b");
            String actual_1517 = t_6996.getAccumulated().toString();
            boolean t_7002 = actual_1517.equals("v = 'a''''b'");
            Supplier<String> fn__6986 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"a''b\").toString() == (" + "v = 'a''''b'" + ") not (" + actual_1517 + ")";
            test_129.assert_(t_7002, fn__6986);
            SqlBuilder t_7004 = new SqlBuilder();
            t_7004.appendSafe("v = ");
            t_7004.appendString("Hello \u4e16\u754c");
            String actual_1520 = t_7004.getAccumulated().toString();
            boolean t_7010 = actual_1520.equals("v = 'Hello \u4e16\u754c'");
            Supplier<String> fn__6985 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Hello \u4e16\u754c\").toString() == (" + "v = 'Hello \u4e16\u754c'" + ") not (" + actual_1520 + ")";
            test_129.assert_(t_7010, fn__6985);
            SqlBuilder t_7012 = new SqlBuilder();
            t_7012.appendSafe("v = ");
            t_7012.appendString("Line1\nLine2");
            String actual_1523 = t_7012.getAccumulated().toString();
            boolean t_7018 = actual_1523.equals("v = 'Line1\nLine2'");
            Supplier<String> fn__6984 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, \"Line1\\nLine2\").toString() == (" + "v = 'Line1\nLine2'" + ") not (" + actual_1523 + ")";
            test_129.assert_(t_7018, fn__6984);
        } finally {
            test_129.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void numbersAndBooleans__1526() {
        Test test_130 = new Test();
        try {
            SqlBuilder t_6959 = new SqlBuilder();
            t_6959.appendSafe("select ");
            t_6959.appendInt32(42);
            t_6959.appendSafe(", ");
            t_6959.appendInt64(43);
            t_6959.appendSafe(", ");
            t_6959.appendFloat64(19.99D);
            t_6959.appendSafe(", ");
            t_6959.appendBoolean(true);
            t_6959.appendSafe(", ");
            t_6959.appendBoolean(false);
            String actual_1527 = t_6959.getAccumulated().toString();
            boolean t_6973 = actual_1527.equals("select 42, 43, 19.99, TRUE, FALSE");
            Supplier<String> fn__6958 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, 42, \", \", \\interpolate, 43, \", \", \\interpolate, 19.99, \", \", \\interpolate, true, \", \", \\interpolate, false).toString() == (" + "select 42, 43, 19.99, TRUE, FALSE" + ") not (" + actual_1527 + ")";
            test_130.assert_(t_6973, fn__6958);
            LocalDate t_3423;
            t_3423 = LocalDate.of(2024, 12, 25);
            LocalDate date__1219 = t_3423;
            SqlBuilder t_6975 = new SqlBuilder();
            t_6975.appendSafe("insert into t values (");
            t_6975.appendDate(date__1219);
            t_6975.appendSafe(")");
            String actual_1530 = t_6975.getAccumulated().toString();
            boolean t_6982 = actual_1530.equals("insert into t values ('2024-12-25')");
            Supplier<String> fn__6957 = () -> "expected stringExpr(`-work//src/`.sql, true, \"insert into t values (\", \\interpolate, date, \")\").toString() == (" + "insert into t values ('2024-12-25')" + ") not (" + actual_1530 + ")";
            test_130.assert_(t_6982, fn__6957);
        } finally {
            test_130.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void lists__1533() {
        Test test_131 = new Test();
        try {
            SqlBuilder t_6903 = new SqlBuilder();
            t_6903.appendSafe("v IN (");
            t_6903.appendStringList(List.of("a", "b", "c'd"));
            t_6903.appendSafe(")");
            String actual_1534 = t_6903.getAccumulated().toString();
            boolean t_6910 = actual_1534.equals("v IN ('a', 'b', 'c''d')");
            Supplier<String> fn__6902 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(\"a\", \"b\", \"c'd\"), \")\").toString() == (" + "v IN ('a', 'b', 'c''d')" + ") not (" + actual_1534 + ")";
            test_131.assert_(t_6910, fn__6902);
            SqlBuilder t_6912 = new SqlBuilder();
            t_6912.appendSafe("v IN (");
            t_6912.appendInt32List(List.of(1, 2, 3));
            t_6912.appendSafe(")");
            String actual_1537 = t_6912.getAccumulated().toString();
            boolean t_6919 = actual_1537.equals("v IN (1, 2, 3)");
            Supplier<String> fn__6901 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2, 3), \")\").toString() == (" + "v IN (1, 2, 3)" + ") not (" + actual_1537 + ")";
            test_131.assert_(t_6919, fn__6901);
            SqlBuilder t_6921 = new SqlBuilder();
            t_6921.appendSafe("v IN (");
            t_6921.appendInt64List(List.of(1, 2));
            t_6921.appendSafe(")");
            String actual_1540 = t_6921.getAccumulated().toString();
            boolean t_6928 = actual_1540.equals("v IN (1, 2)");
            Supplier<String> fn__6900 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1, 2), \")\").toString() == (" + "v IN (1, 2)" + ") not (" + actual_1540 + ")";
            test_131.assert_(t_6928, fn__6900);
            SqlBuilder t_6930 = new SqlBuilder();
            t_6930.appendSafe("v IN (");
            t_6930.appendFloat64List(List.of(1.0D, 2.0D));
            t_6930.appendSafe(")");
            String actual_1543 = t_6930.getAccumulated().toString();
            boolean t_6937 = actual_1543.equals("v IN (1.0, 2.0)");
            Supplier<String> fn__6899 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(1.0, 2.0), \")\").toString() == (" + "v IN (1.0, 2.0)" + ") not (" + actual_1543 + ")";
            test_131.assert_(t_6937, fn__6899);
            SqlBuilder t_6939 = new SqlBuilder();
            t_6939.appendSafe("v IN (");
            t_6939.appendBooleanList(List.of(true, false));
            t_6939.appendSafe(")");
            String actual_1546 = t_6939.getAccumulated().toString();
            boolean t_6946 = actual_1546.equals("v IN (TRUE, FALSE)");
            Supplier<String> fn__6898 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, list(true, false), \")\").toString() == (" + "v IN (TRUE, FALSE)" + ") not (" + actual_1546 + ")";
            test_131.assert_(t_6946, fn__6898);
            LocalDate t_3395;
            t_3395 = LocalDate.of(2024, 1, 1);
            LocalDate t_3396 = t_3395;
            LocalDate t_3397;
            t_3397 = LocalDate.of(2024, 12, 25);
            LocalDate t_3398 = t_3397;
            List<LocalDate> dates__1221 = List.of(t_3396, t_3398);
            SqlBuilder t_6948 = new SqlBuilder();
            t_6948.appendSafe("v IN (");
            t_6948.appendDateList(dates__1221);
            t_6948.appendSafe(")");
            String actual_1549 = t_6948.getAccumulated().toString();
            boolean t_6955 = actual_1549.equals("v IN ('2024-01-01', '2024-12-25')");
            Supplier<String> fn__6897 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v IN (\", \\interpolate, dates, \")\").toString() == (" + "v IN ('2024-01-01', '2024-12-25')" + ") not (" + actual_1549 + ")";
            test_131.assert_(t_6955, fn__6897);
        } finally {
            test_131.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_naNRendersAsNull__1552() {
        Test test_132 = new Test();
        try {
            double nan__1223;
            nan__1223 = 0.0D / 0.0D;
            SqlBuilder t_6889 = new SqlBuilder();
            t_6889.appendSafe("v = ");
            t_6889.appendFloat64(nan__1223);
            String actual_1553 = t_6889.getAccumulated().toString();
            boolean t_6895 = actual_1553.equals("v = NULL");
            Supplier<String> fn__6888 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, nan).toString() == (" + "v = NULL" + ") not (" + actual_1553 + ")";
            test_132.assert_(t_6895, fn__6888);
        } finally {
            test_132.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_infinityRendersAsNull__1556() {
        Test test_133 = new Test();
        try {
            double inf__1225;
            inf__1225 = 1.0D / 0.0D;
            SqlBuilder t_6880 = new SqlBuilder();
            t_6880.appendSafe("v = ");
            t_6880.appendFloat64(inf__1225);
            String actual_1557 = t_6880.getAccumulated().toString();
            boolean t_6886 = actual_1557.equals("v = NULL");
            Supplier<String> fn__6879 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, inf).toString() == (" + "v = NULL" + ") not (" + actual_1557 + ")";
            test_133.assert_(t_6886, fn__6879);
        } finally {
            test_133.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_negativeInfinityRendersAsNull__1560() {
        Test test_134 = new Test();
        try {
            double ninf__1227;
            ninf__1227 = -1.0D / 0.0D;
            SqlBuilder t_6871 = new SqlBuilder();
            t_6871.appendSafe("v = ");
            t_6871.appendFloat64(ninf__1227);
            String actual_1561 = t_6871.getAccumulated().toString();
            boolean t_6877 = actual_1561.equals("v = NULL");
            Supplier<String> fn__6870 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, ninf).toString() == (" + "v = NULL" + ") not (" + actual_1561 + ")";
            test_134.assert_(t_6877, fn__6870);
        } finally {
            test_134.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlFloat64_normalValuesStillWork__1564() {
        Test test_135 = new Test();
        try {
            SqlBuilder t_6846 = new SqlBuilder();
            t_6846.appendSafe("v = ");
            t_6846.appendFloat64(3.14D);
            String actual_1565 = t_6846.getAccumulated().toString();
            boolean t_6852 = actual_1565.equals("v = 3.14");
            Supplier<String> fn__6845 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 3.14).toString() == (" + "v = 3.14" + ") not (" + actual_1565 + ")";
            test_135.assert_(t_6852, fn__6845);
            SqlBuilder t_6854 = new SqlBuilder();
            t_6854.appendSafe("v = ");
            t_6854.appendFloat64(0.0D);
            String actual_1568 = t_6854.getAccumulated().toString();
            boolean t_6860 = actual_1568.equals("v = 0.0");
            Supplier<String> fn__6844 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, 0.0).toString() == (" + "v = 0.0" + ") not (" + actual_1568 + ")";
            test_135.assert_(t_6860, fn__6844);
            SqlBuilder t_6862 = new SqlBuilder();
            t_6862.appendSafe("v = ");
            t_6862.appendFloat64(-42.5D);
            String actual_1571 = t_6862.getAccumulated().toString();
            boolean t_6868 = actual_1571.equals("v = -42.5");
            Supplier<String> fn__6843 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, -42.5).toString() == (" + "v = -42.5" + ") not (" + actual_1571 + ")";
            test_135.assert_(t_6868, fn__6843);
        } finally {
            test_135.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void sqlDateRendersWithQuotes__1574() {
        Test test_136 = new Test();
        try {
            LocalDate t_3291;
            t_3291 = LocalDate.of(2024, 6, 15);
            LocalDate d__1230 = t_3291;
            SqlBuilder t_6835 = new SqlBuilder();
            t_6835.appendSafe("v = ");
            t_6835.appendDate(d__1230);
            String actual_1575 = t_6835.getAccumulated().toString();
            boolean t_6841 = actual_1575.equals("v = '2024-06-15'");
            Supplier<String> fn__6834 = () -> "expected stringExpr(`-work//src/`.sql, true, \"v = \", \\interpolate, d).toString() == (" + "v = '2024-06-15'" + ") not (" + actual_1575 + ")";
            test_136.assert_(t_6841, fn__6834);
        } finally {
            test_136.softFailToHard();
        }
    }
    @org.junit.jupiter.api.Test public void nesting__1578() {
        Test test_137 = new Test();
        try {
            String name__1232 = "Someone";
            SqlBuilder t_6803 = new SqlBuilder();
            t_6803.appendSafe("where p.last_name = ");
            t_6803.appendString("Someone");
            SqlFragment condition__1233 = t_6803.getAccumulated();
            SqlBuilder t_6807 = new SqlBuilder();
            t_6807.appendSafe("select p.id from person p ");
            t_6807.appendFragment(condition__1233);
            String actual_1580 = t_6807.getAccumulated().toString();
            boolean t_6813 = actual_1580.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__6802 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1580 + ")";
            test_137.assert_(t_6813, fn__6802);
            SqlBuilder t_6815 = new SqlBuilder();
            t_6815.appendSafe("select p.id from person p ");
            t_6815.appendPart(condition__1233.toSource());
            String actual_1583 = t_6815.getAccumulated().toString();
            boolean t_6822 = actual_1583.equals("select p.id from person p where p.last_name = 'Someone'");
            Supplier<String> fn__6801 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select p.id from person p \", \\interpolate, condition.toSource()).toString() == (" + "select p.id from person p where p.last_name = 'Someone'" + ") not (" + actual_1583 + ")";
            test_137.assert_(t_6822, fn__6801);
            List<SqlPart> parts__1234 = List.of(new SqlString("a'b"), new SqlInt32(3));
            SqlBuilder t_6826 = new SqlBuilder();
            t_6826.appendSafe("select ");
            t_6826.appendPartList(parts__1234);
            String actual_1586 = t_6826.getAccumulated().toString();
            boolean t_6832 = actual_1586.equals("select 'a''b', 3");
            Supplier<String> fn__6800 = () -> "expected stringExpr(`-work//src/`.sql, true, \"select \", \\interpolate, parts).toString() == (" + "select 'a''b', 3" + ") not (" + actual_1586 + ")";
            test_137.assert_(t_6832, fn__6800);
        } finally {
            test_137.softFailToHard();
        }
    }
}
