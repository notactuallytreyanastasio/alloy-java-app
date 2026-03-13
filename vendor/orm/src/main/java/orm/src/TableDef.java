package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__1718) {
        FieldDef return__584;
        fn__1719: {
            List<FieldDef> this__8420 = this.fields;
            int n__8421 = this__8420.size();
            int i__8422 = 0;
            while (i__8422 < n__8421) {
                FieldDef el__8423 = Core.listGet(this__8420, i__8422);
                i__8422 = i__8422 + 1;
                FieldDef f__1720 = el__8423;
                if (f__1720.getName().getSqlValue().equals(name__1718)) {
                    return__584 = f__1720;
                    break fn__1719;
                }
            }
            throw Core.bubble();
        }
        return return__584;
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<FieldDef> fields;
        public Builder fields(List<FieldDef> fields) {
            this.fields = fields;
            return this;
        }
        public TableDef build() {
            return new TableDef(tableName, fields);
        }
    }
    public TableDef(SafeIdentifier tableName__1722, List<FieldDef> fields__1723) {
        this.tableName = tableName__1722;
        this.fields = fields__1723;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
