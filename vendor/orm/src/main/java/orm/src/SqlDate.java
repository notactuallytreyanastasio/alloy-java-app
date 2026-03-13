package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1262) {
        builder__1262.append("'");
        String t_9646 = this.value.toString();
        IntConsumer fn__9644 = c__1264 -> {
            if (c__1264 == 39) {
                builder__1262.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1262, c__1264);
            }
        };
        Core.stringForEach(t_9646, fn__9644);
        builder__1262.append("'");
    }
    public SqlDate(LocalDate value__1266) {
        this.value = value__1266;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
