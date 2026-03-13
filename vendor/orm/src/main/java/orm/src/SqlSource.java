package orm.src;
/**
 * `SqlSource` represents known-safe SQL source code that doesn't need escaped.
 */
public final class SqlSource implements SqlPart {
    public final String source;
    public void formatTo(StringBuilder builder__764) {
        builder__764.append(this.source);
    }
    public SqlSource(String source__767) {
        this.source = source__767;
    }
    public String getSource() {
        return this.source;
    }
}
