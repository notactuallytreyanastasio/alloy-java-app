package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__1060) {
        FieldDef return__389;
        fn__1061: {
            List<FieldDef> this__5239 = this.fields;
            int n__5240 = this__5239.size();
            int i__5241 = 0;
            while (i__5241 < n__5240) {
                FieldDef el__5242 = Core.listGet(this__5239, i__5241);
                i__5241 = i__5241 + 1;
                FieldDef f__1062 = el__5242;
                if (f__1062.getName().getSqlValue().equals(name__1060)) {
                    return__389 = f__1062;
                    break fn__1061;
                }
            }
            throw Core.bubble();
        }
        return return__389;
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
    public TableDef(SafeIdentifier tableName__1064, List<FieldDef> fields__1065) {
        this.tableName = tableName__1064;
        this.fields = fields__1065;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
