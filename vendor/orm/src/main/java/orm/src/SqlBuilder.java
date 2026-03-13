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
    public void appendSafe(String sqlSource__1087) {
        SqlSource t_8767 = new SqlSource(sqlSource__1087);
        Core.listAdd(this.buffer, t_8767);
    }
    public void appendFragment(SqlFragment fragment__1090) {
        List<SqlPart> t_8765 = fragment__1090.getParts();
        Core.listAddAll(this.buffer, t_8765);
    }
    public void appendPart(SqlPart part__1093) {
        Core.listAdd(this.buffer, part__1093);
    }
    public void appendPartList(List<SqlPart> values__1096) {
        Consumer<SqlPart> fn__8761 = x__1098 -> {
            this.appendPart(x__1098);
        };
        this.appendList(values__1096, fn__8761);
    }
    public void appendBoolean(boolean value__1100) {
        SqlBoolean t_8758 = new SqlBoolean(value__1100);
        Core.listAdd(this.buffer, t_8758);
    }
    public void appendBooleanList(List<Boolean> values__1103) {
        Consumer<Boolean> fn__8755 = x__1105 -> {
            this.appendBoolean(x__1105);
        };
        this.appendList(values__1103, fn__8755);
    }
    public void appendDate(LocalDate value__1107) {
        SqlDate t_8752 = new SqlDate(value__1107);
        Core.listAdd(this.buffer, t_8752);
    }
    public void appendDateList(List<LocalDate> values__1110) {
        Consumer<LocalDate> fn__8749 = x__1112 -> {
            this.appendDate(x__1112);
        };
        this.appendList(values__1110, fn__8749);
    }
    public void appendFloat64(double value__1114) {
        SqlFloat64 t_8746 = new SqlFloat64(value__1114);
        Core.listAdd(this.buffer, t_8746);
    }
    public void appendFloat64List(List<Double> values__1117) {
        DoubleConsumer fn__8743 = x__1119 -> {
            this.appendFloat64(x__1119);
        };
        this.appendList(values__1117, fn__8743 :: accept);
    }
    public void appendInt32(int value__1121) {
        SqlInt32 t_8740 = new SqlInt32(value__1121);
        Core.listAdd(this.buffer, t_8740);
    }
    public void appendInt32List(List<Integer> values__1124) {
        IntConsumer fn__8737 = x__1126 -> {
            this.appendInt32(x__1126);
        };
        this.appendList(values__1124, fn__8737 :: accept);
    }
    public void appendInt64(long value__1128) {
        SqlInt64 t_8734 = new SqlInt64(value__1128);
        Core.listAdd(this.buffer, t_8734);
    }
    public void appendInt64List(List<Long> values__1131) {
        Consumer<Long> fn__8731 = x__1133 -> {
            this.appendInt64(x__1133);
        };
        this.appendList(values__1131, fn__8731);
    }
    public void appendString(String value__1135) {
        SqlString t_8728 = new SqlString(value__1135);
        Core.listAdd(this.buffer, t_8728);
    }
    public void appendStringList(List<String> values__1138) {
        Consumer<String> fn__8725 = x__1140 -> {
            this.appendString(x__1140);
        };
        this.appendList(values__1138, fn__8725);
    }
    <T__226> void appendList(List<T__226> values__1142, Consumer<T__226> appendValue__1143) {
        int t_8720;
        T__226 t_8722;
        int i__1145 = 0;
        while (true) {
            t_8720 = values__1142.size();
            if (i__1145 >= t_8720) {
                break;
            }
            if (i__1145 > 0) {
                this.appendSafe(", ");
            }
            t_8722 = Core.listGet(values__1142, i__1145);
            appendValue__1143.accept(t_8722);
            i__1145 = i__1145 + 1;
        }
    }
    public SqlFragment getAccumulated() {
        return new SqlFragment(List.copyOf(this.buffer));
    }
    public SqlBuilder() {
        List<SqlPart> t_8717 = new ArrayList<>();
        this.buffer = t_8717;
    }
}
