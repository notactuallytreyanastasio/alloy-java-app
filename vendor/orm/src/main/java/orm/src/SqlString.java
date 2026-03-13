package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__802) {
        builder__802.append("'");
        IntConsumer fn__5089 = c__804 -> {
            if (c__804 == 39) {
                builder__802.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__802, c__804);
            }
        };
        Core.stringForEach(this.value, fn__5089);
        builder__802.append("'");
    }
    public SqlString(String value__806) {
        this.value = value__806;
    }
    public String getValue() {
        return this.value;
    }
}
