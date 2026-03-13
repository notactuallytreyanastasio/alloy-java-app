package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1961) {
        builder__1961.append("'");
        IntConsumer fn__15537 = c__1963 -> {
            if (c__1963 == 39) {
                builder__1961.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1961, c__1963);
            }
        };
        Core.stringForEach(this.value, fn__15537);
        builder__1961.append("'");
    }
    public SqlString(String value__1965) {
        this.value = value__1965;
    }
    public String getValue() {
        return this.value;
    }
}
