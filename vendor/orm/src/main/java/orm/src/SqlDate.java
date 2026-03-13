package orm.src;
import java.time.LocalDate;
import temper.core.Core;
import java.util.function.IntConsumer;
public final class SqlDate implements SqlPart {
    public final LocalDate value;
    public void formatTo(StringBuilder builder__1178) {
        builder__1178.append("'");
        String t_8772 = this.value.toString();
        IntConsumer fn__8770 = c__1180 -> {
            if (c__1180 == 39) {
                builder__1178.append("''");
            } else {
                Core.stringBuilderAppendCodePoint(builder__1178, c__1180);
            }
        };
        Core.stringForEach(t_8772, fn__8770);
        builder__1178.append("'");
    }
    public SqlDate(LocalDate value__1182) {
        this.value = value__1182;
    }
    public LocalDate getValue() {
        return this.value;
    }
}
