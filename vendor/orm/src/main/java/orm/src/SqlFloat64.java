package orm.src;
import temper.core.Core;
public final class SqlFloat64 implements SqlPart {
    public final double value;
    public void formatTo(StringBuilder builder__783) {
        boolean t_3021;
        boolean t_3022;
        String s__785 = Core.float64ToString(this.value);
        if (s__785.equals("NaN")) {
            t_3022 = true;
        } else {
            if (s__785.equals("Infinity")) {
                t_3021 = true;
            } else {
                t_3021 = s__785.equals("-Infinity");
            }
            t_3022 = t_3021;
        }
        if (t_3022) {
            builder__783.append("NULL");
        } else {
            builder__783.append(s__785);
        }
    }
    public SqlFloat64(double value__787) {
        this.value = value__787;
    }
    public double getValue() {
        return this.value;
    }
}
