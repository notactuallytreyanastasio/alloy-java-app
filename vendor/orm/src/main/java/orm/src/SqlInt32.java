package orm.src;
public final class SqlInt32 implements SqlPart {
    public final int value;
    public void formatTo(StringBuilder builder__1850) {
        String t_14265 = Integer.toString(this.value);
        builder__1850.append(t_14265);
    }
    public SqlInt32(int value__1853) {
        this.value = value__1853;
    }
    public int getValue() {
        return this.value;
    }
}
