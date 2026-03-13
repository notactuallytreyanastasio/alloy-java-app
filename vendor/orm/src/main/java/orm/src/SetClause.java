package orm.src;
public final class SetClause {
    public final SafeIdentifier field;
    public final SqlPart value;
    public static final class Builder {
        SafeIdentifier field;
        public Builder field(SafeIdentifier field) {
            this.field = field;
            return this;
        }
        SqlPart value;
        public Builder value(SqlPart value) {
            this.value = value;
            return this;
        }
        public SetClause build() {
            return new SetClause(field, value);
        }
    }
    public SetClause(SafeIdentifier field__1404, SqlPart value__1405) {
        this.field = field__1404;
        this.value = value__1405;
    }
    public SafeIdentifier getField() {
        return this.field;
    }
    public SqlPart getValue() {
        return this.value;
    }
}
