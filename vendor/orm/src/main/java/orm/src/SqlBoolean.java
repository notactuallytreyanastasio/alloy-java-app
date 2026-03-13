package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1256) {
        String t_5535;
        if (this.value) {
            t_5535 = "TRUE";
        } else {
            t_5535 = "FALSE";
        }
        builder__1256.append(t_5535);
    }
    public SqlBoolean(boolean value__1259) {
        this.value = value__1259;
    }
    public boolean isValue() {
        return this.value;
    }
}
