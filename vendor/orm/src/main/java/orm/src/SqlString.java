package orm.src;
import temper.core.Core;
import java.util.function.IntConsumer;
/**
 * `SqlString` represents text data that needs escaped.
 */
public final class SqlString implements SqlPart {
    public final String value;
    public void formatTo(StringBuilder builder__1204) {
        builder__1204.append("'");
        IntConsumer fn__8784 = c__1206 -> {
            if (c__1206 == 39) {
                builder__1204.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1204, c__1206);
            }
        };
        Core.stringForEach(this.value, fn__8784);
        builder__1204.append("'");
    }
    public SqlString(String value__1208) {
        this.value = value__1208;
    }
    public String getValue() {
        return this.value;
    }
}
