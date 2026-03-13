package orm.src;
public final class ChangesetError {
    public final String field;
    public final String message;
    public static final class Builder {
        String field;
        public Builder field(String field) {
            this.field = field;
            return this;
        }
        String message;
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public ChangesetError build() {
            return new ChangesetError(field, message);
        }
    }
    public ChangesetError(String field__670, String message__671) {
        this.field = field__670;
        this.message = message__671;
    }
    public String getField() {
        return this.field;
    }
    public String getMessage() {
        return this.message;
    }
}
