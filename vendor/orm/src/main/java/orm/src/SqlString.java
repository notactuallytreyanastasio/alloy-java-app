package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1288) {
        builder__1288.append("'");
        IntConsumer fn__9658 = c__1290 -> {
            if (c__1290 == 39) {
                builder__1288.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1288, c__1290);
            }
        };
        Core.stringForEach(this.value, fn__9658);
        builder__1288.append("'");
    }
    public SqlString(String value__1292) {
        this.value = value__1292;
    }
    public String getValue() {
        return this.value;
    }
}
