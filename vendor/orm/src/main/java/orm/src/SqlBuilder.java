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
    public void appendSafe(String sqlSource__685) {
        SqlSource t_5072 = new SqlSource(sqlSource__685);
        Core.listAdd(this.buffer, t_5072);
    }
    public void appendFragment(SqlFragment fragment__688) {
        List<SqlPart> t_5070 = fragment__688.getParts();
        Core.listAddAll(this.buffer, t_5070);
    }
    public void appendPart(SqlPart part__691) {
        Core.listAdd(this.buffer, part__691);
    }
    public void appendPartList(List<SqlPart> values__694) {
        Consumer<SqlPart> fn__5066 = x__696 -> {
            this.appendPart(x__696);
        };
        this.appendList(values__694, fn__5066);
    }
    public void appendBoolean(boolean value__698) {
        SqlBoolean t_5063 = new SqlBoolean(value__698);
        Core.listAdd(this.buffer, t_5063);
    }
    public void appendBooleanList(List<Boolean> values__701) {
        Consumer<Boolean> fn__5060 = x__703 -> {
            this.appendBoolean(x__703);
        };
        this.appendList(values__701, fn__5060);
    }
    public void appendDate(LocalDate value__705) {
        SqlDate t_5057 = new SqlDate(value__705);
        Core.listAdd(this.buffer, t_5057);
    }
    public void appendDateList(List<LocalDate> values__708) {
        Consumer<LocalDate> fn__5054 = x__710 -> {
            this.appendDate(x__710);
        };
        this.appendList(values__708, fn__5054);
    }
    public void appendFloat64(double value__712) {
        SqlFloat64 t_5051 = new SqlFloat64(value__712);
        Core.listAdd(this.buffer, t_5051);
    }
    public void appendFloat64List(List<Double> values__715) {
        DoubleConsumer fn__5048 = x__717 -> {
            this.appendFloat64(x__717);
        };
        this.appendList(values__715, fn__5048 :: accept);
    }
    public void appendInt32(int value__719) {
        SqlInt32 t_5045 = new SqlInt32(value__719);
        Core.listAdd(this.buffer, t_5045);
    }
    public void appendInt32List(List<Integer> values__722) {
        IntConsumer fn__5042 = x__724 -> {
            this.appendInt32(x__724);
        };
        this.appendList(values__722, fn__5042 :: accept);
    }
    public void appendInt64(long value__726) {
        SqlInt64 t_5039 = new SqlInt64(value__726);
        Core.listAdd(this.buffer, t_5039);
    }
    public void appendInt64List(List<Long> values__729) {
        Consumer<Long> fn__5036 = x__731 -> {
            this.appendInt64(x__731);
        };
        this.appendList(values__729, fn__5036);
    }
    public void appendString(String value__733) {
        SqlString t_5033 = new SqlString(value__733);
        Core.listAdd(this.buffer, t_5033);
    }
    public void appendStringList(List<String> values__736) {
        Consumer<String> fn__5030 = x__738 -> {
            this.appendString(x__738);
        };
        this.appendList(values__736, fn__5030);
    }
    <T__145> void appendList(List<T__145> values__740, Consumer<T__145> appendValue__741) {
        int t_5025;
        T__145 t_5027;
        int i__743 = 0;
        while (true) {
            t_5025 = values__740.size();
            if (i__743 >= t_5025) {
                break;
            }
            if (i__743 > 0) {
                this.appendSafe(", ");
            }
            t_5027 = Core.listGet(values__740, i__743);
            appendValue__741.accept(t_5027);
            i__743 = i__743 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_5022 = new ArrayList<>();
        this.buffer = t_5022;
    }
}
