package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1172) {
        String t_5061;
        if (this.value) {
            t_5061 = "TRUE";
        } else {
            t_5061 = "FALSE";
        }
        builder__1172.append(t_5061);
    }
    public SqlBoolean(boolean value__1175) {
        this.value = value__1175;
    }
    public boolean isValue() {
        return this.value;
    }
}
