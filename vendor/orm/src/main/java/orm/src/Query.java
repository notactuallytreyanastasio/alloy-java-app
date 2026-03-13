package orm.src;
import java.util.List;
import temper.core.Nullable;
import temper.core.Core;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Consumer;
public final class Query {
    public final SafeIdentifier tableName;
    public final List<SqlFragment> conditions;
    public final List<SafeIdentifier> selectedFields;
    public final List<OrderClause> orderClauses;
    public final @Nullable Integer limitVal;
    public final @Nullable Integer offsetVal;
    public Query where(SqlFragment condition__550) {
        List<SqlFragment> nb__552 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__552, condition__550);
        return new Query(this.tableName, List.copyOf(nb__552), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal);
    }
    public Query select(List<SafeIdentifier> fields__554) {
        return new Query(this.tableName, this.conditions, fields__554, this.orderClauses, this.limitVal, this.offsetVal);
    }
    public Query orderBy(SafeIdentifier field__557, boolean ascending__558) {
        List<OrderClause> nb__560 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__560, new OrderClause(field__557, ascending__558));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__560), this.limitVal, this.offsetVal);
    }
    public Query limit(int n__562) {
        if (n__562 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__562, this.offsetVal);
    }
    public Query offset(int n__565) {
        if (n__565 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__565);
    }
    public SqlFragment toSql() {
        int t_4466;
        SqlBuilder b__569 = new SqlBuilder();
        b__569.appendSafe("SELECT ");
        if (this.selectedFields.isEmpty()) {
            b__569.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__4451 = f__570 -> f__570.getSqlValue();
            b__569.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__4451));
        }
        b__569.appendSafe(" FROM ");
        b__569.appendSafe(this.tableName.getSqlValue());
        if (!this.conditions.isEmpty()) {
            b__569.appendSafe(" WHERE ");
            b__569.appendFragment(Core.listGet(this.conditions, 0));
            int i__571 = 1;
            while (true) {
                t_4466 = this.conditions.size();
                if (i__571 >= t_4466) {
                    break;
                }
                b__569.appendSafe(" AND ");
                b__569.appendFragment(Core.listGet(this.conditions, i__571));
                i__571 = i__571 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__569.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__572 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__4450 = oc__573 -> {
                String t_2442;
                if (!local$2.first__572) {
                    b__569.appendSafe(", ");
                }
                local$2.first__572 = false;
                String t_4445 = oc__573.getField().getSqlValue();
                b__569.appendSafe(t_4445);
                if (oc__573.isAscending()) {
                    t_2442 = " ASC";
                } else {
                    t_2442 = " DESC";
                }
                b__569.appendSafe(t_2442);
            };
            this.orderClauses.forEach(fn__4450);
        }
        @Nullable Integer lv__574 = this.limitVal;
        if (lv__574 != null) {
            int lv_1116 = lv__574;
            b__569.appendSafe(" LIMIT ");
            b__569.appendInt32(lv_1116);
        }
        @Nullable Integer ov__575 = this.offsetVal;
        if (ov__575 != null) {
            int ov_1117 = ov__575;
            b__569.appendSafe(" OFFSET ");
            b__569.appendInt32(ov_1117);
        }
        return b__569.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__577) {
        SqlFragment return__221;
        Query t_2435;
        if (defaultLimit__577 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__221 = this.toSql();
        } else {
            t_2435 = this.limit(defaultLimit__577);
            return__221 = t_2435.toSql();
        }
        return return__221;
    }
    public static final class Builder {
        SafeIdentifier tableName;
        public Builder tableName(SafeIdentifier tableName) {
            this.tableName = tableName;
            return this;
        }
        List<SqlFragment> conditions;
        public Builder conditions(List<SqlFragment> conditions) {
            this.conditions = conditions;
            return this;
        }
        List<SafeIdentifier> selectedFields;
        public Builder selectedFields(List<SafeIdentifier> selectedFields) {
            this.selectedFields = selectedFields;
            return this;
        }
        List<OrderClause> orderClauses;
        public Builder orderClauses(List<OrderClause> orderClauses) {
            this.orderClauses = orderClauses;
            return this;
        }
        @Nullable Integer limitVal;
        boolean limitVal__set;
        public Builder limitVal(@Nullable Integer limitVal) {
            limitVal__set = true;
            this.limitVal = limitVal;
            return this;
        }
        @Nullable Integer offsetVal;
        boolean offsetVal__set;
        public Builder offsetVal(@Nullable Integer offsetVal) {
            offsetVal__set = true;
            this.offsetVal = offsetVal;
            return this;
        }
        public Query build() {
            if (!limitVal__set || !offsetVal__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (!offsetVal__set) {
                    _message.append(" offsetVal");
                }
                if (tableName == null) {
                    _message.append(" tableName");
                }
                if (conditions == null) {
                    _message.append(" conditions");
                }
                if (selectedFields == null) {
                    _message.append(" selectedFields");
                }
                if (orderClauses == null) {
                    _message.append(" orderClauses");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal);
        }
    }
    public Query(SafeIdentifier tableName__580, List<SqlFragment> conditions__581, List<SafeIdentifier> selectedFields__582, List<OrderClause> orderClauses__583, @Nullable Integer limitVal__584, @Nullable Integer offsetVal__585) {
        this.tableName = tableName__580;
        this.conditions = conditions__581;
        this.selectedFields = selectedFields__582;
        this.orderClauses = orderClauses__583;
        this.limitVal = limitVal__584;
        this.offsetVal = offsetVal__585;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<SqlFragment> getConditions() {
        return this.conditions;
    }
    public List<SafeIdentifier> getSelectedFields() {
        return this.selectedFields;
    }
    public List<OrderClause> getOrderClauses() {
        return this.orderClauses;
    }
    public @Nullable Integer getLimitVal() {
        return this.limitVal;
    }
    public @Nullable Integer getOffsetVal() {
        return this.offsetVal;
    }
}
