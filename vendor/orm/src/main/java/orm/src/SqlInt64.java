package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1282) {
        String t_9653 = Long.toString(this.value);
        builder__1282.append(t_9653);
    }
    public SqlInt64(long value__1285) {
        this.value = value__1285;
    }
    public long getValue() {
        return this.value;
    }
}
