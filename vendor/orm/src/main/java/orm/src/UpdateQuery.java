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
    public UpdateQuery set(SafeIdentifier field__1484, SqlPart value__1485) {
        List<SetClause> nb__1487 = new ArrayList<>(this.setClauses);
        Core.listAdd(nb__1487, new SetClause(field__1484, value__1485));
        return new UpdateQuery(this.tableName, List.copyOf(nb__1487), this.conditions, this.limitVal);
    }
    public UpdateQuery where(SqlFragment condition__1489) {
        List<WhereClause> nb__1491 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1491, new AndCondition(condition__1489));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1491), this.limitVal);
    }
    public UpdateQuery orWhere(SqlFragment condition__1493) {
        List<WhereClause> nb__1495 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1495, new OrCondition(condition__1493));
        return new UpdateQuery(this.tableName, this.setClauses, List.copyOf(nb__1495), this.limitVal);
    }
    public UpdateQuery limit(int n__1497) {
        if (n__1497 < 0) {
            throw Core.bubble();
        }
        return new UpdateQuery(this.tableName, this.setClauses, this.conditions, n__1497);
    }
    public SqlFragment toSql() {
        int t_13604;
        int t_13618;
        if (this.conditions.isEmpty()) {
            throw Core.bubble();
        }
        if (this.setClauses.isEmpty()) {
            throw Core.bubble();
        }
        SqlBuilder b__1501 = new SqlBuilder();
        b__1501.appendSafe("UPDATE ");
        b__1501.appendSafe(this.tableName.getSqlValue());
        b__1501.appendSafe(" SET ");
        b__1501.appendSafe(Core.listGet(this.setClauses, 0).getField().getSqlValue());
        b__1501.appendSafe(" = ");
        b__1501.appendPart(Core.listGet(this.setClauses, 0).getValue());
        int i__1502 = 1;
        while (true) {
            t_13604 = this.setClauses.size();
            if (i__1502 >= t_13604) {
                break;
            }
            b__1501.appendSafe(", ");
            b__1501.appendSafe(Core.listGet(this.setClauses, i__1502).getField().getSqlValue());
            b__1501.appendSafe(" = ");
            b__1501.appendPart(Core.listGet(this.setClauses, i__1502).getValue());
            i__1502 = i__1502 + 1;
        }
        b__1501.appendSafe(" WHERE ");
        b__1501.appendFragment(Core.listGet(this.conditions, 0).getCondition());
        int i__1503 = 1;
        while (true) {
            t_13618 = this.conditions.size();
            if (i__1503 >= t_13618) {
                break;
            }
            b__1501.appendSafe(" ");
            b__1501.appendSafe(Core.listGet(this.conditions, i__1503).keyword());
            b__1501.appendSafe(" ");
            b__1501.appendFragment(Core.listGet(this.conditions, i__1503).getCondition());
            i__1503 = i__1503 + 1;
        }
        @Nullable Integer lv__1504 = this.limitVal;
        if (lv__1504 != null) {
            int lv_2629 = lv__1504;
            b__1501.appendSafe(" LIMIT ");
            b__1501.appendInt32(lv_2629);
        }
        return b__1501.getAccumulated();
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
    public UpdateQuery(SafeIdentifier tableName__1506, List<SetClause> setClauses__1507, List<WhereClause> conditions__1508, @Nullable Integer limitVal__1509) {
        this.tableName = tableName__1506;
        this.setClauses = setClauses__1507;
        this.conditions = conditions__1508;
        this.limitVal = limitVal__1509;
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
