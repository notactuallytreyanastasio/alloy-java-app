package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1192) {
        String t_8781 = Integer.toString(this.value);
        builder__1192.append(t_8781);
    }
    public SqlInt32(int value__1195) {
        this.value = value__1195;
    }
    public int getValue() {
        return this.value;
    }
}
