package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1856) {
        String t_14263 = Long.toString(this.value);
        builder__1856.append(t_14263);
    }
    public SqlInt64(long value__1859) {
        this.value = value__1859;
    }
    public long getValue() {
        return this.value;
    }
}
