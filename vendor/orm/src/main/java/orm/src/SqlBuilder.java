package orm.src;
import java.util.List;
import temper.core.Core;
import java.util.function.Consumer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.DoubleConsumer;
public final class SqlBuilder {
    final List<SqlPart> buffer;
    public void appendSafe(String sqlSource__1745) {
        SqlSource t_14251 = new SqlSource(sqlSource__1745);
        Core.listAdd(this.buffer, t_14251);
    }
    public void appendFragment(SqlFragment fragment__1748) {
        List<SqlPart> t_14249 = fragment__1748.getParts();
        Core.listAddAll(this.buffer, t_14249);
    }
    public void appendPart(SqlPart part__1751) {
        Core.listAdd(this.buffer, part__1751);
    }
    public void appendPartList(List<SqlPart> values__1754) {
        Consumer<SqlPart> fn__14245 = x__1756 -> {
            this.appendPart(x__1756);
        };
        this.appendList(values__1754, fn__14245);
    }
    public void appendBoolean(boolean value__1758) {
        SqlBoolean t_14242 = new SqlBoolean(value__1758);
        Core.listAdd(this.buffer, t_14242);
    }
    public void appendBooleanList(List<Boolean> values__1761) {
        Consumer<Boolean> fn__14239 = x__1763 -> {
            this.appendBoolean(x__1763);
        };
        this.appendList(values__1761, fn__14239);
    }
    public void appendDate(LocalDate value__1765) {
        SqlDate t_14236 = new SqlDate(value__1765);
        Core.listAdd(this.buffer, t_14236);
    }
    public void appendDateList(List<LocalDate> values__1768) {
        Consumer<LocalDate> fn__14233 = x__1770 -> {
            this.appendDate(x__1770);
        };
        this.appendList(values__1768, fn__14233);
    }
    public void appendFloat64(double value__1772) {
        SqlFloat64 t_14230 = new SqlFloat64(value__1772);
        Core.listAdd(this.buffer, t_14230);
    }
    public void appendFloat64List(List<Double> values__1775) {
        DoubleConsumer fn__14227 = x__1777 -> {
            this.appendFloat64(x__1777);
        };
        this.appendList(values__1775, fn__14227 :: accept);
    }
    public void appendInt32(int value__1779) {
        SqlInt32 t_14224 = new SqlInt32(value__1779);
        Core.listAdd(this.buffer, t_14224);
    }
    public void appendInt32List(List<Integer> values__1782) {
        IntConsumer fn__14221 = x__1784 -> {
            this.appendInt32(x__1784);
        };
        this.appendList(values__1782, fn__14221 :: accept);
    }
    public void appendInt64(long value__1786) {
        SqlInt64 t_14218 = new SqlInt64(value__1786);
        Core.listAdd(this.buffer, t_14218);
    }
    public void appendInt64List(List<Long> values__1789) {
        Consumer<Long> fn__14215 = x__1791 -> {
            this.appendInt64(x__1791);
        };
        this.appendList(values__1789, fn__14215);
    }
    public void appendString(String value__1793) {
        SqlString t_14212 = new SqlString(value__1793);
        Core.listAdd(this.buffer, t_14212);
    }
    public void appendStringList(List<String> values__1796) {
        Consumer<String> fn__14209 = x__1798 -> {
            this.appendString(x__1798);
        };
        this.appendList(values__1796, fn__14209);
    }
    <T__340> void appendList(List<T__340> values__1800, Consumer<T__340> appendValue__1801) {
        int t_14204;
        T__340 t_14206;
        int i__1803 = 0;
        while (true) {
            t_14204 = values__1800.size();
            if (i__1803 >= t_14204) {
                break;
            }
            if (i__1803 > 0) {
                this.appendSafe(", ");
            }
            t_14206 = Core.listGet(values__1800, i__1803);
            appendValue__1801.accept(t_14206);
            i__1803 = i__1803 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_14201 = new ArrayList<>();
        this.buffer = t_14201;
    }
}
