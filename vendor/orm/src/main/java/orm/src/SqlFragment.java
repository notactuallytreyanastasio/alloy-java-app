package orm.src;
import java.util.List;
import temper.core.Core;
public final class SqlFragment {
    public final List<SqlPart> parts;
    public SqlSource toSource() {
        return new SqlSource(this.toString());
    }
    public String toString() {
        int t_8791;
        StringBuilder builder__1157 = new StringBuilder();
        int i__1158 = 0;
        while (true) {
            t_8791 = this.parts.size();
            if (i__1158 >= t_8791) {
                break;
            }
            Core.listGet(this.parts, i__1158).formatTo(builder__1157);
            i__1158 = i__1158 + 1;
        }
        return builder__1157.toString();
    }
    public SqlFragment(List<SqlPart> parts__1160) {
        this.parts = parts__1160;
    }
    public List<SqlPart> getParts() {
        return this.parts;
    }
}
