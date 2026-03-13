package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
public final class UpdateQuery {
    public final SafeIdentifier tableName;
    public final List<SetClause> setClauses;
    public final List<WhereClause> conditions;
    public final @Nullable Integer limitVal;
    public UpdateQuery set(SafeIdentifier field__1411, SqlPart value__1412) {
        List<SetClause> nb__1414 = new ArrayList<>(this.setClauses);
        Core.listAdd(nb__1414, new SetClause(field__1411, value__1412));
        return new UpdateQuery(this.tableName, List.copyOf(nb__1414), this.conditions, this.limitVal);
    }
    public UpdateQuery where(SqlFragment condition__1416) {
        List<WhereClause> nb__1418 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1418, new AndCondition(condition__1416));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1418), this.limitVal);
    }
    public UpdateQuery orWhere(SqlFragment condition__1420) {
        List<WhereClause> nb__1422 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1422, new OrCondition(condition__1420));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1422), this.limitVal);
    }
    public UpdateQuery limit(int n__1424) {
        if (n__1424 < 0) {
            throw Core.bubble();
        }
        return new UpdateQuery(this.tableName, this.setClauses, this.conditions, n__1424);
    }
    public SqlFragment toSql() {
        int t_12557;
        int t_12571;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        if (this.setClauses.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1428 = new SqlBuilder();
        b__1428.appendSafe("UPDATE ");
        b__1428.appendSafe(this.tableName.getSqlValue());
        b__1428.appendSafe(" SET ");
        b__1428.appendSafe(Core.listGet(this.setClauses, 0).getField().getSqlValue());
        b__1428.appendSafe(" = ");
        b__1428.appendPart(Core.listGet(this.setClauses, 0).getValue());
        int i__1429 = 1;
        while (true) {
            t_12557 = this.setClauses.size();
            if (i__1429 >= t_12557) {
                break;
            }
            b__1428.appendSafe(", ");
            b__1428.appendSafe(Core.listGet(this.setClauses, i__1429).getField().getSqlValue());
            b__1428.appendSafe(" = ");
            b__1428.appendPart(Core.listGet(this.setClauses, i__1429).getValue());
            i__1429 = i__1429 + 1;
        }
        b__1428.appendSafe(" WHERE ");
        b__1428.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1430 = 1;
        while (true) {
            t_12571 = this.conditions.size();
            if (i__1430 >= t_12571) {
                break;
            }
            b__1428.appendSafe(" ");
            b__1428.appendSafe(Core.listGet(this.conditions, i__1430).keyword());
            b__1428.appendSafe(" ");
            b__1428.appendFragment(Core.listGet(this.conditions, i__1430).getCondition());
            i__1430 = i__1430 + 1;
        }
        @Nullable Integer lv__1431 = this.limitVal;
        if (lv__1431 != null) {
            int lv_2484 = lv__1431;
            b__1428.appendSafe(" LIMIT ");
            b__1428.appendInt32(lv_2484);
        }
        return b__1428.getAccumulated();
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<SetClause> setClauses;
        public Builder setClauses(List<SetClause> setClauses) {
            this.setClauses = setClauses;
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
        public UpdateQuery build() {
            if (!limitVal__set || tableName == null || setClauses == null || conditions == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (setClauses == null) {
                    _message.append(" setClauses");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new UpdateQuery(tableName, setClauses, conditions, limitVal);
        }
    }
    public UpdateQuery(SafeIdentifier tableName__1433, List<SetClause> setClauses__1434, List<WhereClause> conditions__1435, @Nullable Integer limitVal__1436) {
        this.tableName = tableName__1433;
        this.setClauses = setClauses__1434;
        this.conditions = conditions__1435;
        this.limitVal = limitVal__1436;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<SetClause> getSetClauses() {
        return this.setClauses;
    }
    public List<WhereClause> getConditions() {
        return this.conditions;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
}
