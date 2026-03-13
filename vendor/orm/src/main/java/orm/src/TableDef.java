package orm.src;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public FieldDef field(String name__658) {
        FieldDef return__250;
        fn__659: {
            List<FieldDef> this__3156 = this.fields;
            int n__3157 = this__3156.size();
            int i__3158 = 0;
            while (i__3158 < n__3157) {
                FieldDef el__3159 = Core.listGet(this__3156, i__3158);
                i__3158 = i__3158 + 1;
                FieldDef f__660 = el__3159;
                if (f__660.getName().getSqlValue().equals(name__658)) {
                    return__250 = f__660;
                    break fn__659;
                }
            }
            throw Core.bubble();
        }
        return return__250;
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
    public TableDef(SafeIdentifier tableName__662, List<FieldDef> fields__663) {
        this.tableName = tableName__662;
        this.fields = fields__663;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
}
