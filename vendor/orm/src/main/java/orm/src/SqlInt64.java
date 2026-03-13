package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__796) {
        String t_5084 = Long.toString(this.value);
        builder__796.append(t_5084);
    }
    public SqlInt64(long value__799) {
        this.value = value__799;
    }
    public long getValue() {
        return this.value;
    }
}
