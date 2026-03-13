package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class DeleteQuery {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public DeleteQuery where(SqlFragment condition__1441) {
        List<WhereClause> nb__1443 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1443, new AndCondition(condition__1441));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1443), this.limitVal);
    }
    public DeleteQuery orWhere(SqlFragment condition__1445) {
        List<WhereClause> nb__1447 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1447, new OrCondition(condition__1445));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1447), this.limitVal);
    }
    public DeleteQuery limit(int n__1449) {
        if (n__1449 < 0) {
            throw Core.bubble();
        }
        return new DeleteQuery(this.tableName, this.conditions, n__1449);
    }
    public SqlFragment toSql() {
        int t_12517;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1453 = new SqlBuilder();
        b__1453.appendSafe("DELETE FROM ");
        b__1453.appendSafe(this.tableName.getSqlValue());
        b__1453.appendSafe(" WHERE ");
        b__1453.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1454 = 1;
        while (true) {
            t_12517 = this.conditions.size();
            if (i__1454 >= t_12517) {
                break;
            }
            b__1453.appendSafe(" ");
            b__1453.appendSafe(Core.listGet(this.conditions, i__1454).keyword());
            b__1453.appendSafe(" ");
            b__1453.appendFragment(Core.listGet(this.conditions, i__1454).getCondition());
            i__1454 = i__1454 + 1;
        }
        @Nullable Integer lv__1455 = this.limitVal;
        if (lv__1455 != null) {
            int lv_2485 = lv__1455;
            b__1453.appendSafe(" LIMIT ");
            b__1453.appendInt32(lv_2485);
        }
        return b__1453.getAccumulated();
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<WhereClause> conditions;
        public Builder conditions(List<WhereClause> conditions) {
            this.conditions = conditions;
            return this;
        }
        @Nullable Integer limitVal;
        boolean limitVal__set;
        public Builder limitVal(@Nullable Integer limitVal) {
            limitVal__set = true;
            this.limitVal = limitVal;
            return this;
        }
        public DeleteQuery build() {
            if (!limitVal__set || tableName == null || conditions == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new DeleteQuery(tableName, conditions, limitVal);
        }
    }
    public DeleteQuery(SafeIdentifier tableName__1457, List<WhereClause> conditions__1458, @Nullable Integer limitVal__1459) {
        this.tableName = tableName__1457;
        this.conditions = conditions__1458;
        this.limitVal = limitVal__1459;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<WhereClause> getConditions() {
        return this.conditions;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
}
