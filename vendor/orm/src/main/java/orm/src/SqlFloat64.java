package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1269) {
        boolean t_5524;
        boolean t_5525;
        String s__1271 = Core.float64ToString(this.value);
        if (s__1271.equals("NaN")) {
            t_5525 = true;
        } else {
            if (s__1271.equals("Infinity")) {
                t_5524 = true;
            } else {
                t_5524 = s__1271.equals("-Infinity");
            }
            t_5525 = t_5524;
        }
        if (t_5525) {
            builder__1269.append("NULL");
        } else {
            builder__1269.append(s__1271);
        }
    }
    public SqlFloat64(double value__1273) {
        this.value = value__1273;
    }
    public double getValue() {
        return this.value;
    }
}
