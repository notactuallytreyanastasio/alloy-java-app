package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1836) {
        builder__1836.append("'");
        String t_14256 = this.value.toString();
        IntConsumer fn__14254 = c__1838 -> {
            if (c__1838 == 39) {
                builder__1836.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1836, c__1838);
            }
        };
        Core.stringForEach(t_14256, fn__14254);
        builder__1836.append("'");
    }
    public SqlDate(LocalDate value__1840) {
        this.value = value__1840;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
