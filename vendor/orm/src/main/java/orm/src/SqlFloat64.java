package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__1938) {
        boolean t_8839;
        boolean t_8840;
        String s__1940 = Core.float64ToString(this.value);
        if (s__1940.equals("NaN")) {
            t_8840 = true;
        } else {
            if (s__1940.equals("Infinity")) {
                t_8839 = true;
            } else {
                t_8839 = s__1940.equals("-Infinity");
            }
            t_8840 = t_8839;
        }
        if (t_8840) {
            builder__1938.append("NULL");
        } else {
            builder__1938.append(s__1940);
        }
    }
    public SqlFloat64(double value__1942) {
        this.value = value__1942;
    }
    public double getValue() {
        return this.value;
    }
}
