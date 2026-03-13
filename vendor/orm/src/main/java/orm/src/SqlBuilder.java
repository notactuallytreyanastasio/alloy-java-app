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
    public void appendSafe(String sqlSource__1840) {
        SqlSource t_15520 = new SqlSource(sqlSource__1840);
        Core.listAdd(this.buffer, t_15520);
    }
    public void appendFragment(SqlFragment fragment__1843) {
        List<SqlPart> t_15518 = fragment__1843.getParts();
        Core.listAddAll(this.buffer, t_15518);
    }
    public void appendPart(SqlPart part__1846) {
        Core.listAdd(this.buffer, part__1846);
    }
    public void appendPartList(List<SqlPart> values__1849) {
        Consumer<SqlPart> fn__15514 = x__1851 -> {
            this.appendPart(x__1851);
        };
        this.appendList(values__1849, fn__15514);
    }
    public void appendBoolean(boolean value__1853) {
        SqlBoolean t_15511 = new SqlBoolean(value__1853);
        Core.listAdd(this.buffer, t_15511);
    }
    public void appendBooleanList(List<Boolean> values__1856) {
        Consumer<Boolean> fn__15508 = x__1858 -> {
            this.appendBoolean(x__1858);
        };
        this.appendList(values__1856, fn__15508);
    }
    public void appendDate(LocalDate value__1860) {
        SqlDate t_15505 = new SqlDate(value__1860);
        Core.listAdd(this.buffer, t_15505);
    }
    public void appendDateList(List<LocalDate> values__1863) {
        Consumer<LocalDate> fn__15502 = x__1865 -> {
            this.appendDate(x__1865);
        };
        this.appendList(values__1863, fn__15502);
    }
    public void appendFloat64(double value__1867) {
        SqlFloat64 t_15499 = new SqlFloat64(value__1867);
        Core.listAdd(this.buffer, t_15499);
    }
    public void appendFloat64List(List<Double> values__1870) {
        DoubleConsumer fn__15496 = x__1872 -> {
            this.appendFloat64(x__1872);
        };
        this.appendList(values__1870, fn__15496 :: accept);
    }
    public void appendInt32(int value__1874) {
        SqlInt32 t_15493 = new SqlInt32(value__1874);
        Core.listAdd(this.buffer, t_15493);
    }
    public void appendInt32List(List<Integer> values__1877) {
        IntConsumer fn__15490 = x__1879 -> {
            this.appendInt32(x__1879);
        };
        this.appendList(values__1877, fn__15490 :: accept);
    }
    public void appendInt64(long value__1881) {
        SqlInt64 t_15487 = new SqlInt64(value__1881);
        Core.listAdd(this.buffer, t_15487);
    }
    public void appendInt64List(List<Long> values__1884) {
        Consumer<Long> fn__15484 = x__1886 -> {
            this.appendInt64(x__1886);
        };
        this.appendList(values__1884, fn__15484);
    }
    public void appendString(String value__1888) {
        SqlString t_15481 = new SqlString(value__1888);
        Core.listAdd(this.buffer, t_15481);
    }
    public void appendStringList(List<String> values__1891) {
        Consumer<String> fn__15478 = x__1893 -> {
            this.appendString(x__1893);
        };
        this.appendList(values__1891, fn__15478);
    }
    <T__356> void appendList(List<T__356> values__1895, Consumer<T__356> appendValue__1896) {
        int t_15473;
        T__356 t_15475;
        int i__1898 = 0;
        while (true) {
            t_15473 = values__1895.size();
            if (i__1898 >= t_15473) {
                break;
            }
            if (i__1898 > 0) {
                this.appendSafe(", ");
            }
            t_15475 = Core.listGet(values__1895, i__1898);
            appendValue__1896.accept(t_15475);
            i__1898 = i__1898 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_15470 = new ArrayList<>();
        this.buffer = t_15470;
    }
}
