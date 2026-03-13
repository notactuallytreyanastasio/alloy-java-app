package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__770) {
        String t_3032;
        if (this.value) {
            t_3032 = "TRUE";
        } else {
            t_3032 = "FALSE";
        }
        builder__770.append(t_3032);
    }
    public SqlBoolean(boolean value__773) {
        this.value = value__773;
    }
    public boolean isValue() {
        return this.value;
    }
}
