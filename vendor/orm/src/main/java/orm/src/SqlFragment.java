package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_15544;
        StringBuilder builder__1910 = new StringBuilder();
        int i__1911 = 0;
        while (true) {
            t_15544 = this.parts.size();
            if (i__1911 >= t_15544) {
                break;
            }
            Core.listGet(this.parts, i__1911).formatTo(builder__1910);
            i__1911 = i__1911 + 1;
        }
        return builder__1910.toString();
    }
    public SqlFragment(List<SqlPart> parts__1913) {
        this.parts = parts__1913;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
