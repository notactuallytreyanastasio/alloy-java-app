package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1276) {
        String t_9655 = Integer.toString(this.value);
        builder__1276.append(t_9655);
    }
    public SqlInt32(int value__1279) {
        this.value = value__1279;
    }
    public int getValue() {
        return this.value;
    }
}
