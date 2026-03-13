package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1185) {
        boolean t_5050;
        boolean t_5051;
        String s__1187 = Core.float64ToString(this.value);
        if (s__1187.equals("NaN")) {
            t_5051 = true;
        } else {
            if (s__1187.equals("Infinity")) {
                t_5050 = true;
            } else {
                t_5050 = s__1187.equals("-Infinity");
            }
            t_5051 = t_5050;
        }
        if (t_5051) {
            builder__1185.append("NULL");
        } else {
            builder__1185.append(s__1187);
        }
    }
    public SqlFloat64(double value__1189) {
        this.value = value__1189;
    }
    public double getValue() {
        return this.value;
    }
}
