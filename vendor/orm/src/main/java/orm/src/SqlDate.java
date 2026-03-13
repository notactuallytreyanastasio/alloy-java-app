package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1931) {
        builder__1931.append("'");
        String t_15525 = this.value.toString();
        IntConsumer fn__15523 = c__1933 -> {
            if (c__1933 == 39) {
                builder__1931.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1931, c__1933);
            }
        };
        Core.stringForEach(t_15525, fn__15523);
        builder__1931.append("'");
    }
    public SqlDate(LocalDate value__1935) {
        this.value = value__1935;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
