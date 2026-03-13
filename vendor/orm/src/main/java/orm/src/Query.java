package orm.src;
import java.util.List;
import temper.core.Core;
import temper.core.Nullable;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Consumer;
public final class Query {
    public final SafeIdentifier tableName;
    public final List<WhereClause> conditions;
    public final List<SafeIdentifier> selectedFields;
    public final List<OrderClause> orderClauses;
    public final @Nullable Integer limitVal;
    public final @Nullable Integer offsetVal;
    public final List<JoinClause> joinClauses;
    public final List<SafeIdentifier> groupByFields;
    public final List<WhereClause> havingConditions;
    public final boolean isDistinct;
    public final List<SqlFragment> selectExprs;
    public Query where(SqlFragment condition__733) {
        List<WhereClause> nb__735 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__735, new AndCondition(condition__733));
        return new Query(this.tableName, List.copyOf(nb__735), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query orWhere(SqlFragment condition__737) {
        List<WhereClause> nb__739 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__739, new OrCondition(condition__737));
        return new Query(this.tableName, List.copyOf(nb__739), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query whereNull(SafeIdentifier field__741) {
        SqlBuilder b__743 = new SqlBuilder();
        b__743.appendSafe(field__741.getSqlValue());
        b__743.appendSafe(" IS NULL");
        SqlFragment t_8176 = b__743.getAccumulated();
        return this.where(t_8176);
    }
    public Query whereNotNull(SafeIdentifier field__745) {
        SqlBuilder b__747 = new SqlBuilder();
        b__747.appendSafe(field__745.getSqlValue());
        b__747.appendSafe(" IS NOT NULL");
        SqlFragment t_8170 = b__747.getAccumulated();
        return this.where(t_8170);
    }
    public Query whereIn(SafeIdentifier field__749, List<SqlPart> values__750) {
        Query return__332;
        SqlFragment t_8151;
        int t_8159;
        SqlFragment t_8164;
        fn__751: {
            if (values__750.isEmpty()) {
                SqlBuilder b__752 = new SqlBuilder();
                b__752.appendSafe("1 = 0");
                t_8151 = b__752.getAccumulated();
                return__332 = this.where(t_8151);
                break fn__751;
            }
            SqlBuilder b__753 = new SqlBuilder();
            b__753.appendSafe(field__749.getSqlValue());
            b__753.appendSafe(" IN (");
            b__753.appendPart(Core.listGet(values__750, 0));
            int i__754 = 1;
            while (true) {
                t_8159 = values__750.size();
                if (i__754 >= t_8159) {
                    break;
                }
                b__753.appendSafe(", ");
                b__753.appendPart(Core.listGet(values__750, i__754));
                i__754 = i__754 + 1;
            }
            b__753.appendSafe(")");
            t_8164 = b__753.getAccumulated();
            return__332 = this.where(t_8164);
        }
        return return__332;
    }
    public Query whereNot(SqlFragment condition__756) {
        SqlBuilder b__758 = new SqlBuilder();
        b__758.appendSafe("NOT (");
        b__758.appendFragment(condition__756);
        b__758.appendSafe(")");
        SqlFragment t_8146 = b__758.getAccumulated();
        return this.where(t_8146);
    }
    public Query whereBetween(SafeIdentifier field__760, SqlPart low__761, SqlPart high__762) {
        SqlBuilder b__764 = new SqlBuilder();
        b__764.appendSafe(field__760.getSqlValue());
        b__764.appendSafe(" BETWEEN ");
        b__764.appendPart(low__761);
        b__764.appendSafe(" AND ");
        b__764.appendPart(high__762);
        SqlFragment t_8140 = b__764.getAccumulated();
        return this.where(t_8140);
    }
    public Query whereLike(SafeIdentifier field__766, String pattern__767) {
        SqlBuilder b__769 = new SqlBuilder();
        b__769.appendSafe(field__766.getSqlValue());
        b__769.appendSafe(" LIKE ");
        b__769.appendString(pattern__767);
        SqlFragment t_8131 = b__769.getAccumulated();
        return this.where(t_8131);
    }
    public Query whereILike(SafeIdentifier field__771, String pattern__772) {
        SqlBuilder b__774 = new SqlBuilder();
        b__774.appendSafe(field__771.getSqlValue());
        b__774.appendSafe(" ILIKE ");
        b__774.appendString(pattern__772);
        SqlFragment t_8124 = b__774.getAccumulated();
        return this.where(t_8124);
    }
    public Query select(List<SafeIdentifier> fields__776) {
        return new Query(this.tableName, this.conditions, fields__776, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query selectExpr(List<SqlFragment> exprs__779) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__779);
    }
    public Query orderBy(SafeIdentifier field__782, boolean ascending__783) {
        List<OrderClause> nb__785 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__785, new OrderClause(field__782, ascending__783));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__785), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query limit(int n__787) {
        if (n__787 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__787, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query offset(int n__790) {
        if (n__790 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__790, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query join(JoinType joinType__793, SafeIdentifier table__794, SqlFragment onCondition__795) {
        List<JoinClause> nb__797 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__797, new JoinClause(joinType__793, table__794, onCondition__795));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__797), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query innerJoin(SafeIdentifier table__799, SqlFragment onCondition__800) {
        InnerJoin t_8094 = new InnerJoin();
        return this.join(t_8094, table__799, onCondition__800);
    }
    public Query leftJoin(SafeIdentifier table__803, SqlFragment onCondition__804) {
        LeftJoin t_8092 = new LeftJoin();
        return this.join(t_8092, table__803, onCondition__804);
    }
    public Query rightJoin(SafeIdentifier table__807, SqlFragment onCondition__808) {
        RightJoin t_8090 = new RightJoin();
        return this.join(t_8090, table__807, onCondition__808);
    }
    public Query fullJoin(SafeIdentifier table__811, SqlFragment onCondition__812) {
        FullJoin t_8088 = new FullJoin();
        return this.join(t_8088, table__811, onCondition__812);
    }
    public Query groupBy(SafeIdentifier field__815) {
        List<SafeIdentifier> nb__817 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__817, field__815);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__817), this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query having(SqlFragment condition__819) {
        List<WhereClause> nb__821 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__821, new AndCondition(condition__819));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__821), this.isDistinct, this.selectExprs);
    }
    public Query orHaving(SqlFragment condition__823) {
        List<WhereClause> nb__825 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__825, new OrCondition(condition__823));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__825), this.isDistinct, this.selectExprs);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs);
    }
    public SqlFragment toSql() {
        int t_7994;
        int t_8013;
        int t_8032;
        SqlBuilder b__830 = new SqlBuilder();
        if (this.isDistinct) {
            b__830.appendSafe("SELECT DISTINCT ");
        } else {
            b__830.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__830.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__831 = 1;
            while (true) {
                t_7994 = this.selectExprs.size();
                if (i__831 >= t_7994) {
                    break;
                }
                b__830.appendSafe(", ");
                b__830.appendFragment(Core.listGet(this.selectExprs, i__831));
                i__831 = i__831 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__830.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__7987 = f__832 -> f__832.getSqlValue();
            b__830.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__7987));
        }
        b__830.appendSafe(" FROM ");
        b__830.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__7986 = jc__833 -> {
            b__830.appendSafe(" ");
            String t_7974 = jc__833.getJoinType().keyword();
            b__830.appendSafe(t_7974);
            b__830.appendSafe(" ");
            String t_7978 = jc__833.getTable().getSqlValue();
            b__830.appendSafe(t_7978);
            b__830.appendSafe(" ON ");
            SqlFragment t_7981 = jc__833.getOnCondition();
            b__830.appendFragment(t_7981);
        };
        this.joinClauses.forEach(fn__7986);
        if (!this.conditions.isEmpty()) {
            b__830.appendSafe(" WHERE ");
            b__830.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__834 = 1;
            while (true) {
                t_8013 = this.conditions.size();
                if (i__834 >= t_8013) {
                    break;
                }
                b__830.appendSafe(" ");
                b__830.appendSafe(Core.listGet(this.conditions, i__834).keyword());
                b__830.appendSafe(" ");
                b__830.appendFragment(Core.listGet(this.conditions, i__834).getCondition());
                i__834 = i__834 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__830.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__7985 = f__835 -> f__835.getSqlValue();
            b__830.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__7985));
        }
        if (!this.havingConditions.isEmpty()) {
            b__830.appendSafe(" HAVING ");
            b__830.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__836 = 1;
            while (true) {
                t_8032 = this.havingConditions.size();
                if (i__836 >= t_8032) {
                    break;
                }
                b__830.appendSafe(" ");
                b__830.appendSafe(Core.listGet(this.havingConditions, i__836).keyword());
                b__830.appendSafe(" ");
                b__830.appendFragment(Core.listGet(this.havingConditions, i__836).getCondition());
                i__836 = i__836 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__830.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__837 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__7984 = oc__838 -> {
                String t_4304;
                if (!local$2.first__837) {
                    b__830.appendSafe(", ");
                }
                local$2.first__837 = false;
                String t_7967 = oc__838.getField().getSqlValue();
                b__830.appendSafe(t_7967);
                if (oc__838.isAscending()) {
                    t_4304 = " ASC";
                } else {
                    t_4304 = " DESC";
                }
                b__830.appendSafe(t_4304);
            };
            this.orderClauses.forEach(fn__7984);
        }
        @Nullable Integer lv__839 = this.limitVal;
        if (lv__839 != null) {
            int lv_1638 = lv__839;
            b__830.appendSafe(" LIMIT ");
            b__830.appendInt32(lv_1638);
        }
        @Nullable Integer ov__840 = this.offsetVal;
        if (ov__840 != null) {
            int ov_1639 = ov__840;
            b__830.appendSafe(" OFFSET ");
            b__830.appendInt32(ov_1639);
        }
        return b__830.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_7936;
        int t_7955;
        SqlBuilder b__843 = new SqlBuilder();
        b__843.appendSafe("SELECT COUNT(*) FROM ");
        b__843.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__7924 = jc__844 -> {
            b__843.appendSafe(" ");
            String t_7914 = jc__844.getJoinType().keyword();
            b__843.appendSafe(t_7914);
            b__843.appendSafe(" ");
            String t_7918 = jc__844.getTable().getSqlValue();
            b__843.appendSafe(t_7918);
            b__843.appendSafe(" ON ");
            SqlFragment t_7921 = jc__844.getOnCondition();
            b__843.appendFragment(t_7921);
        };
        this.joinClauses.forEach(fn__7924);
        if (!this.conditions.isEmpty()) {
            b__843.appendSafe(" WHERE ");
            b__843.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__845 = 1;
            while (true) {
                t_7936 = this.conditions.size();
                if (i__845 >= t_7936) {
                    break;
                }
                b__843.appendSafe(" ");
                b__843.appendSafe(Core.listGet(this.conditions, i__845).keyword());
                b__843.appendSafe(" ");
                b__843.appendFragment(Core.listGet(this.conditions, i__845).getCondition());
                i__845 = i__845 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__843.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__7923 = f__846 -> f__846.getSqlValue();
            b__843.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__7923));
        }
        if (!this.havingConditions.isEmpty()) {
            b__843.appendSafe(" HAVING ");
            b__843.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__847 = 1;
            while (true) {
                t_7955 = this.havingConditions.size();
                if (i__847 >= t_7955) {
                    break;
                }
                b__843.appendSafe(" ");
                b__843.appendSafe(Core.listGet(this.havingConditions, i__847).keyword());
                b__843.appendSafe(" ");
                b__843.appendFragment(Core.listGet(this.havingConditions, i__847).getCondition());
                i__847 = i__847 + 1;
            }
        }
        return b__843.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__849) {
        SqlFragment return__353;
        Query t_4253;
        if (defaultLimit__849 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__353 = this.toSql();
        } else {
            t_4253 = this.limit(defaultLimit__849);
            return__353 = t_4253.toSql();
        }
        return return__353;
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
        List<JoinClause> joinClauses;
        public Builder joinClauses(List<JoinClause> joinClauses) {
            this.joinClauses = joinClauses;
            return this;
        }
        List<SafeIdentifier> groupByFields;
        public Builder groupByFields(List<SafeIdentifier> groupByFields) {
            this.groupByFields = groupByFields;
            return this;
        }
        List<WhereClause> havingConditions;
        public Builder havingConditions(List<WhereClause> havingConditions) {
            this.havingConditions = havingConditions;
            return this;
        }
        boolean isDistinct;
        boolean isDistinct__set;
        public Builder isDistinct(boolean isDistinct) {
            isDistinct__set = true;
            this.isDistinct = isDistinct;
            return this;
        }
        List<SqlFragment> selectExprs;
        public Builder selectExprs(List<SqlFragment> selectExprs) {
            this.selectExprs = selectExprs;
            return this;
        }
        public Query build() {
            if (!limitVal__set || !offsetVal__set || !isDistinct__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null || joinClauses == null || groupByFields == null || havingConditions == null || selectExprs == null) {
                StringBuilder _message = new StringBuilder("Missing required fields:");
                if (!limitVal__set) {
                    _message.append(" limitVal");
                }
                if (!offsetVal__set) {
                    _message.append(" offsetVal");
                }
                if (!isDistinct__set) {
                    _message.append(" isDistinct");
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
                if (joinClauses == null) {
                    _message.append(" joinClauses");
                }
                if (groupByFields == null) {
                    _message.append(" groupByFields");
                }
                if (havingConditions == null) {
                    _message.append(" havingConditions");
                }
                if (selectExprs == null) {
                    _message.append(" selectExprs");
                }
                throw new IllegalStateException(_message.toString());
            }
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal, joinClauses, groupByFields, havingConditions, isDistinct, selectExprs);
        }
    }
    public Query(SafeIdentifier tableName__852, List<WhereClause> conditions__853, List<SafeIdentifier> selectedFields__854, List<OrderClause> orderClauses__855, @Nullable Integer limitVal__856, @Nullable Integer offsetVal__857, List<JoinClause> joinClauses__858, List<SafeIdentifier> groupByFields__859, List<WhereClause> havingConditions__860, boolean isDistinct__861, List<SqlFragment> selectExprs__862) {
        this.tableName = tableName__852;
        this.conditions = conditions__853;
        this.selectedFields = selectedFields__854;
        this.orderClauses = orderClauses__855;
        this.limitVal = limitVal__856;
        this.offsetVal = offsetVal__857;
        this.joinClauses = joinClauses__858;
        this.groupByFields = groupByFields__859;
        this.havingConditions = havingConditions__860;
        this.isDistinct = isDistinct__861;
        this.selectExprs = selectExprs__862;
    }
    public SafeIdentifier getTableName() {
        return this.tableName;
    }
    public List<WhereClause> getConditions() {
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
    public List<JoinClause> getJoinClauses() {
        return this.joinClauses;
    }
    public List<SafeIdentifier> getGroupByFields() {
        return this.groupByFields;
    }
    public List<WhereClause> getHavingConditions() {
        return this.havingConditions;
    }
    public boolean isDistinct() {
        return this.isDistinct;
    }
    public List<SqlFragment> getSelectExprs() {
        return this.selectExprs;
    }
}
