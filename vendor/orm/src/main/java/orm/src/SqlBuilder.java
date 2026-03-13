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
    public void appendSafe(String sqlSource__1171) {
        SqlSource t_9641 = new SqlSource(sqlSource__1171);
        Core.listAdd(this.buffer, t_9641);
    }
    public void appendFragment(SqlFragment fragment__1174) {
        List<SqlPart> t_9639 = fragment__1174.getParts();
        Core.listAddAll(this.buffer, t_9639);
    }
    public void appendPart(SqlPart part__1177) {
        Core.listAdd(this.buffer, part__1177);
    }
    public void appendPartList(List<SqlPart> values__1180) {
        Consumer<SqlPart> fn__9635 = x__1182 -> {
            this.appendPart(x__1182);
        };
        this.appendList(values__1180, fn__9635);
    }
    public void appendBoolean(boolean value__1184) {
        SqlBoolean t_9632 = new SqlBoolean(value__1184);
        Core.listAdd(this.buffer, t_9632);
    }
    public void appendBooleanList(List<Boolean> values__1187) {
        Consumer<Boolean> fn__9629 = x__1189 -> {
            this.appendBoolean(x__1189);
        };
        this.appendList(values__1187, fn__9629);
    }
    public void appendDate(LocalDate value__1191) {
        SqlDate t_9626 = new SqlDate(value__1191);
        Core.listAdd(this.buffer, t_9626);
    }
    public void appendDateList(List<LocalDate> values__1194) {
        Consumer<LocalDate> fn__9623 = x__1196 -> {
            this.appendDate(x__1196);
        };
        this.appendList(values__1194, fn__9623);
    }
    public void appendFloat64(double value__1198) {
        SqlFloat64 t_9620 = new SqlFloat64(value__1198);
        Core.listAdd(this.buffer, t_9620);
    }
    public void appendFloat64List(List<Double> values__1201) {
        DoubleConsumer fn__9617 = x__1203 -> {
            this.appendFloat64(x__1203);
        };
        this.appendList(values__1201, fn__9617 :: accept);
    }
    public void appendInt32(int value__1205) {
        SqlInt32 t_9614 = new SqlInt32(value__1205);
        Core.listAdd(this.buffer, t_9614);
    }
    public void appendInt32List(List<Integer> values__1208) {
        IntConsumer fn__9611 = x__1210 -> {
            this.appendInt32(x__1210);
        };
        this.appendList(values__1208, fn__9611 :: accept);
    }
    public void appendInt64(long value__1212) {
        SqlInt64 t_9608 = new SqlInt64(value__1212);
        Core.listAdd(this.buffer, t_9608);
    }
    public void appendInt64List(List<Long> values__1215) {
        Consumer<Long> fn__9605 = x__1217 -> {
            this.appendInt64(x__1217);
        };
        this.appendList(values__1215, fn__9605);
    }
    public void appendString(String value__1219) {
        SqlString t_9602 = new SqlString(value__1219);
        Core.listAdd(this.buffer, t_9602);
    }
    public void appendStringList(List<String> values__1222) {
        Consumer<String> fn__9599 = x__1224 -> {
            this.appendString(x__1224);
        };
        this.appendList(values__1222, fn__9599);
    }
    <T__237> void appendList(List<T__237> values__1226, Consumer<T__237> appendValue__1227) {
        int t_9594;
        T__237 t_9596;
        int i__1229 = 0;
        while (true) {
            t_9594 = values__1226.size();
            if (i__1229 >= t_9594) {
                break;
            }
            if (i__1229 > 0) {
                this.appendSafe(", ");
            }
            t_9596 = Core.listGet(values__1226, i__1229);
            appendValue__1227.accept(t_9596);
            i__1229 = i__1229 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_9591 = new ArrayList<>();
        this.buffer = t_9591;
    }
}
