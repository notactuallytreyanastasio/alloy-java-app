package orm.src;
public final class SqlInt64 implements SqlPart {
    public final long value;
    public void formatTo(StringBuilder builder__1198) {
        String t_8779 = Long.toString(this.value);
        builder__1198.append(t_8779);
    }
    public SqlInt64(long value__1201) {
        this.value = value__1201;
    }
    public long getValue() {
        return this.value;
    }
}
