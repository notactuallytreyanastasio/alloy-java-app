package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1951) {
        String t_15532 = Long.toString(this.value);
        builder__1951.append(t_15532);
    }
    public SqlInt64(long value__1954) {
        this.value = value__1954;
    }
    public long getValue() {
        return this.value;
    }
}
