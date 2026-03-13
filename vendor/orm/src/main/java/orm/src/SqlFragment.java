package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_14275;
        StringBuilder builder__1815 = new StringBuilder();
        int i__1816 = 0;
        while (true) {
            t_14275 = this.parts.size();
            if (i__1816 >= t_14275) {
                break;
            }
            Core.listGet(this.parts, i__1816).formatTo(builder__1815);
            i__1816 = i__1816 + 1;
        }
        return builder__1815.toString();
    }
    public SqlFragment(List<SqlPart> parts__1818) {
        this.parts = parts__1818;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
