package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_9665;
        StringBuilder builder__1241 = new StringBuilder();
        int i__1242 = 0;
        while (true) {
            t_9665 = this.parts.size();
            if (i__1242 >= t_9665) {
                break;
            }
            Core.listGet(this.parts, i__1242).formatTo(builder__1241);
            i__1242 = i__1242 + 1;
        }
        return builder__1241.toString();
    }
    public SqlFragment(List<SqlPart> parts__1244) {
        this.parts = parts__1244;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
