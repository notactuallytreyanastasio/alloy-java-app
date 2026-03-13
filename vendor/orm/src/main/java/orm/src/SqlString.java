package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1862) {
        builder__1862.append("'");
        IntConsumer fn__14268 = c__1864 -> {
            if (c__1864 == 39) {
                builder__1862.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1862, c__1864);
            }
        };
        Core.stringForEach(this.value, fn__14268);
        builder__1862.append("'");
    }
    public SqlString(String value__1866) {
        this.value = value__1866;
    }
    public String getValue() {
        return this.value;
    }
}
