package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1843) {
        boolean t_8146;
        boolean t_8147;
        String s__1845 = Core.float64ToString(this.value);
        if (s__1845.equals("NaN")) {
            t_8147 = true;
        } else {
            if (s__1845.equals("Infinity")) {
                t_8146 = true;
            } else {
                t_8146 = s__1845.equals("-Infinity");
            }
            t_8147 = t_8146;
        }
        if (t_8147) {
            builder__1843.append("NULL");
        } else {
            builder__1843.append(s__1845);
        }
    }
    public SqlFloat64(double value__1847) {
        this.value = value__1847;
    }
    public double getValue() {
        return this.value;
    }
}
