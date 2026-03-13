package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__1144) {
        FieldDef return__407;
        fn__1145: {
            List<FieldDef> this__5723 = this.fields;
            int n__5724 = this__5723.size();
            int i__5725 = 0;
            while (i__5725 < n__5724) {
                FieldDef el__5726 = Core.listGet(this__5723, i__5725);
                i__5725 = i__5725 + 1;
                FieldDef f__1146 = el__5726;
                if (f__1146.getName().getSqlValue().equals(name__1144)) {
                    return__407 = f__1146;
                    break fn__1145;
                }
            }
            throw Core.bubble();
        }
        return return__407;
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
    public TableDef(SafeIdentifier tableName__1148, List<FieldDef> fields__1149) {
        this.tableName = tableName__1148;
        this.fields = fields__1149;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
