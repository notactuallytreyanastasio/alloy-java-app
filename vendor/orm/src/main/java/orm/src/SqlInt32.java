package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__790) {
        String t_5086 = Integer.toString(this.value);
        builder__790.append(t_5086);
    }
    public SqlInt32(int value__793) {
        this.value = value__793;
    }
    public int getValue() {
        return this.value;
    }
}
