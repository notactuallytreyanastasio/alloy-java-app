package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1830) {
        String t_8157;
        if (this.value) {
            t_8157 = "TRUE";
        } else {
            t_8157 = "FALSE";
        }
        builder__1830.append(t_8157);
    }
    public SqlBoolean(boolean value__1833) {
        this.value = value__1833;
    }
    public boolean isValue() {
        return this.value;
    }
}
