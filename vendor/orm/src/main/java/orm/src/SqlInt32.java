package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1945) {
        String t_15534 = Integer.toString(this.value);
        builder__1945.append(t_15534);
    }
    public SqlInt32(int value__1948) {
        this.value = value__1948;
    }
    public int getValue() {
        return this.value;
    }
}
