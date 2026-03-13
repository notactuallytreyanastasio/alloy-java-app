package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_5096;
        StringBuilder builder__755 = new StringBuilder();
        int i__756 = 0;
        while (true) {
            t_5096 = this.parts.size();
            if (i__756 >= t_5096) {
                break;
            }
            Core.listGet(this.parts, i__756).formatTo(builder__755);
            i__756 = i__756 + 1;
        }
        return builder__755.toString();
    }
    public SqlFragment(List<SqlPart> parts__758) {
        this.parts = parts__758;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
