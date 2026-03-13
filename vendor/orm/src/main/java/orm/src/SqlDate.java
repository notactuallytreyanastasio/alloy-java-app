package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__776) {
        builder__776.append("'");
        String t_5077 = this.value.toString();
        IntConsumer fn__5075 = c__778 -> {
            if (c__778 == 39) {
                builder__776.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__776, c__778);
            }
        };
        Core.stringForEach(t_5077, fn__5075);
        builder__776.append("'");
    }
    public SqlDate(LocalDate value__780) {
        this.value = value__780;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
