package orm.src;
import temper.core.Nullable;
import java.util.List;
import temper.core.Core;
public final class TableDef {
    public final SafeIdentifier tableName;
    public final List<FieldDef> fields;
    public final @Nullable SafeIdentifier primaryKey;
    public FieldDef field(String name__1796) {
        FieldDef return__603;
        fn__1797: {
            List<FieldDef> this__9149 = this.fields;
            int n__9150 = this__9149.size();
            int i__9151 = 0;
            while (i__9151 < n__9150) {
                FieldDef el__9152 = Core.listGet(this__9149, i__9151);
                i__9151 = i__9151 + 1;
                FieldDef f__1798 = el__9152;
                if (f__1798.getName().getSqlValue().equals(name__1796)) {
                    return__603 = f__1798;
                    break fn__1797;
                }
            }
            throw Core.bubble();
        }
        return return__603;
    }
    public String pkName() {
        String return__604;
        fn__1800: {
            @Nullable SafeIdentifier pk__1801 = this.primaryKey;
            if (pk__1801 != null) {
                SafeIdentifier pk_2609 = pk__1801;
                return__604 = pk_2609.getSqlValue();
                break fn__1800;
            }
            return__604 = "id";
        }
        return return__604;
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
        @Nullable SafeIdentifier primaryKey;
        boolean primaryKey__set;
        public Builder primaryKey(@Nullable SafeIdentifier primaryKey) {
            primaryKey__set = true;
            this.primaryKey = primaryKey;
            return this;
        }
        public TableDef build() {
            if (!primaryKey__set || tableName == null || fields == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!primaryKey__set) {
                    _message.append(" primaryKey");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (fields == null) {
                    _message.append(" fields");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new TableDef(tableName, fields, primaryKey);
        }
    }
    public TableDef(SafeIdentifier tableName__1803, List<FieldDef> fields__1804, @Nullable SafeIdentifier primaryKey__1805) {
        this.tableName = tableName__1803;
        this.fields = fields__1804;
        this.primaryKey = primaryKey__1805;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<FieldDef> getFields() {
        return this.fields;
    }
    public @Nullable SafeIdentifier getPrimaryKey() {
        return this.primaryKey;
    }
}
