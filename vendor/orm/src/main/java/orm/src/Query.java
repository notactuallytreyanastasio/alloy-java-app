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
    public Query where(SqlFragment condition__751) {
        List<WhereClause> nb__753 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__753, new AndCondition(condition__751));
        return new Query(this.tableName, List.copyOf(nb__753), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query orWhere(SqlFragment condition__755) {
        List<WhereClause> nb__757 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__757, new OrCondition(condition__755));
        return new Query(this.tableName, List.copyOf(nb__757), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query whereNull(SafeIdentifier field__759) {
        SqlBuilder b__761 = new SqlBuilder();
        b__761.appendSafe(field__759.getSqlValue());
        b__761.appendSafe(" IS NULL");
        SqlFragment t_9050 = b__761.getAccumulated();
        return this.where(t_9050);
    }
    public Query whereNotNull(SafeIdentifier field__763) {
        SqlBuilder b__765 = new SqlBuilder();
        b__765.appendSafe(field__763.getSqlValue());
        b__765.appendSafe(" IS NOT NULL");
        SqlFragment t_9044 = b__765.getAccumulated();
        return this.where(t_9044);
    }
    public Query whereIn(SafeIdentifier field__767, List<SqlPart> values__768) {
        Query return__343;
        SqlFragment t_9025;
        int t_9033;
        SqlFragment t_9038;
        fn__769: {
            if (values__768.isEmpty()) {
                SqlBuilder b__770 = new SqlBuilder();
                b__770.appendSafe("1 = 0");
                t_9025 = b__770.getAccumulated();
                return__343 = this.where(t_9025);
                break fn__769;
            }
            SqlBuilder b__771 = new SqlBuilder();
            b__771.appendSafe(field__767.getSqlValue());
            b__771.appendSafe(" IN (");
            b__771.appendPart(Core.listGet(values__768, 0));
            int i__772 = 1;
            while (true) {
                t_9033 = values__768.size();
                if (i__772 >= t_9033) {
                    break;
                }
                b__771.appendSafe(", ");
                b__771.appendPart(Core.listGet(values__768, i__772));
                i__772 = i__772 + 1;
            }
            b__771.appendSafe(")");
            t_9038 = b__771.getAccumulated();
            return__343 = this.where(t_9038);
        }
        return return__343;
    }
    public Query whereInSubquery(SafeIdentifier field__774, Query sub__775) {
        SqlBuilder b__777 = new SqlBuilder();
        b__777.appendSafe(field__774.getSqlValue());
        b__777.appendSafe(" IN (");
        b__777.appendFragment(sub__775.toSql());
        b__777.appendSafe(")");
        SqlFragment t_9020 = b__777.getAccumulated();
        return this.where(t_9020);
    }
    public Query whereNot(SqlFragment condition__779) {
        SqlBuilder b__781 = new SqlBuilder();
        b__781.appendSafe("NOT (");
        b__781.appendFragment(condition__779);
        b__781.appendSafe(")");
        SqlFragment t_9011 = b__781.getAccumulated();
        return this.where(t_9011);
    }
    public Query whereBetween(SafeIdentifier field__783, SqlPart low__784, SqlPart high__785) {
        SqlBuilder b__787 = new SqlBuilder();
        b__787.appendSafe(field__783.getSqlValue());
        b__787.appendSafe(" BETWEEN ");
        b__787.appendPart(low__784);
        b__787.appendSafe(" AND ");
        b__787.appendPart(high__785);
        SqlFragment t_9005 = b__787.getAccumulated();
        return this.where(t_9005);
    }
    public Query whereLike(SafeIdentifier field__789, String pattern__790) {
        SqlBuilder b__792 = new SqlBuilder();
        b__792.appendSafe(field__789.getSqlValue());
        b__792.appendSafe(" LIKE ");
        b__792.appendString(pattern__790);
        SqlFragment t_8996 = b__792.getAccumulated();
        return this.where(t_8996);
    }
    public Query whereILike(SafeIdentifier field__794, String pattern__795) {
        SqlBuilder b__797 = new SqlBuilder();
        b__797.appendSafe(field__794.getSqlValue());
        b__797.appendSafe(" ILIKE ");
        b__797.appendString(pattern__795);
        SqlFragment t_8989 = b__797.getAccumulated();
        return this.where(t_8989);
    }
    public Query select(List<SafeIdentifier> fields__799) {
        return new Query(this.tableName, this.conditions, fields__799, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query selectExpr(List<SqlFragment> exprs__802) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__802);
    }
    public Query orderBy(SafeIdentifier field__805, boolean ascending__806) {
        List<OrderClause> nb__808 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__808, new OrderClause(field__805, ascending__806));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__808), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query limit(int n__810) {
        if (n__810 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__810, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query offset(int n__813) {
        if (n__813 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__813, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query join(JoinType joinType__816, SafeIdentifier table__817, SqlFragment onCondition__818) {
        List<JoinClause> nb__820 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__820, new JoinClause(joinType__816, table__817, onCondition__818));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__820), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query innerJoin(SafeIdentifier table__822, SqlFragment onCondition__823) {
        InnerJoin t_8959 = new InnerJoin();
        return this.join(t_8959, table__822, onCondition__823);
    }
    public Query leftJoin(SafeIdentifier table__826, SqlFragment onCondition__827) {
        LeftJoin t_8957 = new LeftJoin();
        return this.join(t_8957, table__826, onCondition__827);
    }
    public Query rightJoin(SafeIdentifier table__830, SqlFragment onCondition__831) {
        RightJoin t_8955 = new RightJoin();
        return this.join(t_8955, table__830, onCondition__831);
    }
    public Query fullJoin(SafeIdentifier table__834, SqlFragment onCondition__835) {
        FullJoin t_8953 = new FullJoin();
        return this.join(t_8953, table__834, onCondition__835);
    }
    public Query groupBy(SafeIdentifier field__838) {
        List<SafeIdentifier> nb__840 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__840, field__838);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__840), this.havingConditions, this.isDistinct, this.selectExprs);
    }
    public Query having(SqlFragment condition__842) {
        List<WhereClause> nb__844 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__844, new AndCondition(condition__842));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__844), this.isDistinct, this.selectExprs);
    }
    public Query orHaving(SqlFragment condition__846) {
        List<WhereClause> nb__848 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__848, new OrCondition(condition__846));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__848), this.isDistinct, this.selectExprs);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs);
    }
    public SqlFragment toSql() {
        int t_8859;
        int t_8878;
        int t_8897;
        SqlBuilder b__853 = new SqlBuilder();
        if (this.isDistinct) {
            b__853.appendSafe("SELECT DISTINCT ");
        } else {
            b__853.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__853.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__854 = 1;
            while (true) {
                t_8859 = this.selectExprs.size();
                if (i__854 >= t_8859) {
                    break;
                }
                b__853.appendSafe(", ");
                b__853.appendFragment(Core.listGet(this.selectExprs, i__854));
                i__854 = i__854 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__853.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__8852 = f__855 -> f__855.getSqlValue();
            b__853.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__8852));
        }
        b__853.appendSafe(" FROM ");
        b__853.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__8851 = jc__856 -> {
            b__853.appendSafe(" ");
            String t_8839 = jc__856.getJoinType().keyword();
            b__853.appendSafe(t_8839);
            b__853.appendSafe(" ");
            String t_8843 = jc__856.getTable().getSqlValue();
            b__853.appendSafe(t_8843);
            b__853.appendSafe(" ON ");
            SqlFragment t_8846 = jc__856.getOnCondition();
            b__853.appendFragment(t_8846);
        };
        this.joinClauses.forEach(fn__8851);
        if (!this.conditions.isEmpty()) {
            b__853.appendSafe(" WHERE ");
            b__853.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__857 = 1;
            while (true) {
                t_8878 = this.conditions.size();
                if (i__857 >= t_8878) {
                    break;
                }
                b__853.appendSafe(" ");
                b__853.appendSafe(Core.listGet(this.conditions, i__857).keyword());
                b__853.appendSafe(" ");
                b__853.appendFragment(Core.listGet(this.conditions, i__857).getCondition());
                i__857 = i__857 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__853.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__8850 = f__858 -> f__858.getSqlValue();
            b__853.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__8850));
        }
        if (!this.havingConditions.isEmpty()) {
            b__853.appendSafe(" HAVING ");
            b__853.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__859 = 1;
            while (true) {
                t_8897 = this.havingConditions.size();
                if (i__859 >= t_8897) {
                    break;
                }
                b__853.appendSafe(" ");
                b__853.appendSafe(Core.listGet(this.havingConditions, i__859).keyword());
                b__853.appendSafe(" ");
                b__853.appendFragment(Core.listGet(this.havingConditions, i__859).getCondition());
                i__859 = i__859 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__853.appendSafe(" ORDER BY ");
            class Local_2 {
                boolean first__860 = true;
            }
            final Local_2 local$2 = new Local_2();
            Consumer<OrderClause> fn__8849 = oc__861 -> {
                String t_4769;
                if (!local$2.first__860) {
                    b__853.appendSafe(", ");
                }
                local$2.first__860 = false;
                String t_8832 = oc__861.getField().getSqlValue();
                b__853.appendSafe(t_8832);
                if (oc__861.isAscending()) {
                    t_4769 = " ASC";
                } else {
                    t_4769 = " DESC";
                }
                b__853.appendSafe(t_4769);
            };
            this.orderClauses.forEach(fn__8849);
        }
        @Nullable Integer lv__862 = this.limitVal;
        if (lv__862 != null) {
            int lv_1742 = lv__862;
            b__853.appendSafe(" LIMIT ");
            b__853.appendInt32(lv_1742);
        }
        @Nullable Integer ov__863 = this.offsetVal;
        if (ov__863 != null) {
            int ov_1743 = ov__863;
            b__853.appendSafe(" OFFSET ");
            b__853.appendInt32(ov_1743);
        }
        return b__853.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_8801;
        int t_8820;
        SqlBuilder b__866 = new SqlBuilder();
        b__866.appendSafe("SELECT COUNT(*) FROM ");
        b__866.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__8789 = jc__867 -> {
            b__866.appendSafe(" ");
            String t_8779 = jc__867.getJoinType().keyword();
            b__866.appendSafe(t_8779);
            b__866.appendSafe(" ");
            String t_8783 = jc__867.getTable().getSqlValue();
            b__866.appendSafe(t_8783);
            b__866.appendSafe(" ON ");
            SqlFragment t_8786 = jc__867.getOnCondition();
            b__866.appendFragment(t_8786);
        };
        this.joinClauses.forEach(fn__8789);
        if (!this.conditions.isEmpty()) {
            b__866.appendSafe(" WHERE ");
            b__866.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__868 = 1;
            while (true) {
                t_8801 = this.conditions.size();
                if (i__868 >= t_8801) {
                    break;
                }
                b__866.appendSafe(" ");
                b__866.appendSafe(Core.listGet(this.conditions, i__868).keyword());
                b__866.appendSafe(" ");
                b__866.appendFragment(Core.listGet(this.conditions, i__868).getCondition());
                i__868 = i__868 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__866.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__8788 = f__869 -> f__869.getSqlValue();
            b__866.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__8788));
        }
        if (!this.havingConditions.isEmpty()) {
            b__866.appendSafe(" HAVING ");
            b__866.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__870 = 1;
            while (true) {
                t_8820 = this.havingConditions.size();
                if (i__870 >= t_8820) {
                    break;
                }
                b__866.appendSafe(" ");
                b__866.appendSafe(Core.listGet(this.havingConditions, i__870).keyword());
                b__866.appendSafe(" ");
                b__866.appendFragment(Core.listGet(this.havingConditions, i__870).getCondition());
                i__870 = i__870 + 1;
            }
        }
        return b__866.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__872) {
        SqlFragment return__365;
        Query t_4718;
        if (defaultLimit__872 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__365 = this.toSql();
        } else {
            t_4718 = this.limit(defaultLimit__872);
            return__365 = t_4718.toSql();
        }
        return return__365;
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
    public Query(SafeIdentifier tableName__875, List<WhereClause> conditions__876, List<SafeIdentifier> selectedFields__877, List<OrderClause> orderClauses__878, @Nullable Integer limitVal__879, @Nullable Integer offsetVal__880, List<JoinClause> joinClauses__881, List<SafeIdentifier> groupByFields__882, List<WhereClause> havingConditions__883, boolean isDistinct__884, List<SqlFragment> selectExprs__885) {
        this.tableName = tableName__875;
        this.conditions = conditions__876;
        this.selectedFields = selectedFields__877;
        this.orderClauses = orderClauses__878;
        this.limitVal = limitVal__879;
        this.offsetVal = offsetVal__880;
        this.joinClauses = joinClauses__881;
        this.groupByFields = groupByFields__882;
        this.havingConditions = havingConditions__883;
        this.isDistinct = isDistinct__884;
        this.selectExprs = selectExprs__885;
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
