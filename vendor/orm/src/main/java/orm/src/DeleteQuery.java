package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class DeleteQuery {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public DeleteQuery where(SqlFragment condition__1514) {
        List<WhereClause> nb__1516 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1516, new AndCondition(condition__1514));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1516), this.limitVal);
    }
    public DeleteQuery orWhere(SqlFragment condition__1518) {
        List<WhereClause> nb__1520 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1520, new OrCondition(condition__1518));
        return new DeleteQuery(this.tableName, List.copyOf(nb__1520), this.limitVal);
    }
    public DeleteQuery limit(int n__1522) {
        if (n__1522 < 0) {
            throw Core.bubble();
        }
        return new DeleteQuery(this.tableName, this.conditions, n__1522);
    }
    public SqlFragment toSql() {
        int t_13564;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1526 = new SqlBuilder();
        b__1526.appendSafe("DELETE FROM ");
        b__1526.appendSafe(this.tableName.getSqlValue());
        b__1526.appendSafe(" WHERE ");
        b__1526.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1527 = 1;
        while (true) {
            t_13564 = this.conditions.size();
            if (i__1527 >= t_13564) {
                break;
            }
            b__1526.appendSafe(" ");
            b__1526.appendSafe(Core.listGet(this.conditions, i__1527).keyword());
            b__1526.appendSafe(" ");
            b__1526.appendFragment(Core.listGet(this.conditions, i__1527).getCondition());
            i__1527 = i__1527 + 1;
        }
        @Nullable Integer lv__1528 = this.limitVal;
        if (lv__1528 != null) {
            int lv_2630 = lv__1528;
            b__1526.appendSafe(" LIMIT ");
            b__1526.appendInt32(lv_2630);
        }
        return b__1526.getAccumulated();
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
    public DeleteQuery(SafeIdentifier tableName__1530, List<WhereClause> conditions__1531, @Nullable Integer limitVal__1532) {
        this.tableName = tableName__1530;
        this.conditions = conditions__1531;
        this.limitVal = limitVal__1532;
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
