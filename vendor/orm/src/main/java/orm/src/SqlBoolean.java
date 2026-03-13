package orm.src;
public final class SqlBoolean implements SqlPart {
    public final boolean value;
    public void formatTo(StringBuilder builder__1925) {
        String t_8850;
        if (this.value) {
            t_8850 = "TRUE";
        } else {
            t_8850 = "FALSE";
        }
        builder__1925.append(t_8850);
    }
    public SqlBoolean(boolean value__1928) {
        this.value = value__1928;
    }
    public boolean isValue() {
        return this.value;
    }
}
