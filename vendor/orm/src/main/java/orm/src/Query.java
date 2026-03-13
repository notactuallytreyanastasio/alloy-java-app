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
    public final @Nullable LockMode lockMode;
    public Query where(SqlFragment condition__1202) {
        List<WhereClause> nb__1204 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1204, new AndCondition(condition__1202));
        return new Query(this.tableName, List.copyOf(nb__1204), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orWhere(SqlFragment condition__1206) {
        List<WhereClause> nb__1208 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1208, new OrCondition(condition__1206));
        return new Query(this.tableName, List.copyOf(nb__1208), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query whereNull(SafeIdentifier field__1210) {
        SqlBuilder b__1212 = new SqlBuilder();
        b__1212.appendSafe(field__1210.getSqlValue());
        b__1212.appendSafe(" IS NULL");
        SqlFragment t_12997 = b__1212.getAccumulated();
        return this.where(t_12997);
    }
    public Query whereNotNull(SafeIdentifier field__1214) {
        SqlBuilder b__1216 = new SqlBuilder();
        b__1216.appendSafe(field__1214.getSqlValue());
        b__1216.appendSafe(" IS NOT NULL");
        SqlFragment t_12991 = b__1216.getAccumulated();
        return this.where(t_12991);
    }
    public Query whereIn(SafeIdentifier field__1218, List<SqlPart> values__1219) {
        Query return__495;
        SqlFragment t_12972;
        int t_12980;
        SqlFragment t_12985;
        fn__1220: {
            if (values__1219.isEmpty()) {
                SqlBuilder b__1221 = new SqlBuilder();
                b__1221.appendSafe("1 = 0");
                t_12972 = b__1221.getAccumulated();
                return__495 = this.where(t_12972);
                break fn__1220;
            }
            SqlBuilder b__1222 = new SqlBuilder();
            b__1222.appendSafe(field__1218.getSqlValue());
            b__1222.appendSafe(" IN (");
            b__1222.appendPart(Core.listGet(values__1219, 0));
            int i__1223 = 1;
            while (true) {
                t_12980 = values__1219.size();
                if (i__1223 >= t_12980) {
                    break;
                }
                b__1222.appendSafe(", ");
                b__1222.appendPart(Core.listGet(values__1219, i__1223));
                i__1223 = i__1223 + 1;
            }
            b__1222.appendSafe(")");
            t_12985 = b__1222.getAccumulated();
            return__495 = this.where(t_12985);
        }
        return return__495;
    }
    public Query whereInSubquery(SafeIdentifier field__1225, Query sub__1226) {
        SqlBuilder b__1228 = new SqlBuilder();
        b__1228.appendSafe(field__1225.getSqlValue());
        b__1228.appendSafe(" IN (");
        b__1228.appendFragment(sub__1226.toSql());
        b__1228.appendSafe(")");
        SqlFragment t_12967 = b__1228.getAccumulated();
        return this.where(t_12967);
    }
    public Query whereNot(SqlFragment condition__1230) {
        SqlBuilder b__1232 = new SqlBuilder();
        b__1232.appendSafe("NOT (");
        b__1232.appendFragment(condition__1230);
        b__1232.appendSafe(")");
        SqlFragment t_12958 = b__1232.getAccumulated();
        return this.where(t_12958);
    }
    public Query whereBetween(SafeIdentifier field__1234, SqlPart low__1235, SqlPart high__1236) {
        SqlBuilder b__1238 = new SqlBuilder();
        b__1238.appendSafe(field__1234.getSqlValue());
        b__1238.appendSafe(" BETWEEN ");
        b__1238.appendPart(low__1235);
        b__1238.appendSafe(" AND ");
        b__1238.appendPart(high__1236);
        SqlFragment t_12952 = b__1238.getAccumulated();
        return this.where(t_12952);
    }
    public Query whereLike(SafeIdentifier field__1240, String pattern__1241) {
        SqlBuilder b__1243 = new SqlBuilder();
        b__1243.appendSafe(field__1240.getSqlValue());
        b__1243.appendSafe(" LIKE ");
        b__1243.appendString(pattern__1241);
        SqlFragment t_12943 = b__1243.getAccumulated();
        return this.where(t_12943);
    }
    public Query whereILike(SafeIdentifier field__1245, String pattern__1246) {
        SqlBuilder b__1248 = new SqlBuilder();
        b__1248.appendSafe(field__1245.getSqlValue());
        b__1248.appendSafe(" ILIKE ");
        b__1248.appendString(pattern__1246);
        SqlFragment t_12936 = b__1248.getAccumulated();
        return this.where(t_12936);
    }
    public Query select(List<SafeIdentifier> fields__1250) {
        return new Query(this.tableName, this.conditions, fields__1250, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query selectExpr(List<SqlFragment> exprs__1253) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__1253, this.lockMode);
    }
    public Query orderBy(SafeIdentifier field__1256, boolean ascending__1257) {
        List<OrderClause> nb__1259 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1259, new OrderClause(field__1256, ascending__1257, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1259), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orderByNulls(SafeIdentifier field__1261, boolean ascending__1262, NullsPosition nulls__1263) {
        List<OrderClause> nb__1265 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1265, new OrderClause(field__1261, ascending__1262, nulls__1263));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1265), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query limit(int n__1267) {
        if (n__1267 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__1267, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query offset(int n__1270) {
        if (n__1270 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__1270, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query join(JoinType joinType__1273, SafeIdentifier table__1274, SqlFragment onCondition__1275) {
        List<JoinClause> nb__1277 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1277, new JoinClause(joinType__1273, table__1274, onCondition__1275));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1277), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query innerJoin(SafeIdentifier table__1279, SqlFragment onCondition__1280) {
        InnerJoin t_12898 = new InnerJoin();
        return this.join(t_12898, table__1279, onCondition__1280);
    }
    public Query leftJoin(SafeIdentifier table__1283, SqlFragment onCondition__1284) {
        LeftJoin t_12896 = new LeftJoin();
        return this.join(t_12896, table__1283, onCondition__1284);
    }
    public Query rightJoin(SafeIdentifier table__1287, SqlFragment onCondition__1288) {
        RightJoin t_12894 = new RightJoin();
        return this.join(t_12894, table__1287, onCondition__1288);
    }
    public Query fullJoin(SafeIdentifier table__1291, SqlFragment onCondition__1292) {
        FullJoin t_12892 = new FullJoin();
        return this.join(t_12892, table__1291, onCondition__1292);
    }
    public Query crossJoin(SafeIdentifier table__1295) {
        List<JoinClause> nb__1297 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1297, new JoinClause(new CrossJoin(), table__1295, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1297), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query groupBy(SafeIdentifier field__1299) {
        List<SafeIdentifier> nb__1301 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__1301, field__1299);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__1301), this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query having(SqlFragment condition__1303) {
        List<WhereClause> nb__1305 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1305, new AndCondition(condition__1303));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1305), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orHaving(SqlFragment condition__1307) {
        List<WhereClause> nb__1309 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1309, new OrCondition(condition__1307));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1309), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs, this.lockMode);
    }
    public Query lock(LockMode mode__1313) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, mode__1313);
    }
    public SqlFragment toSql() {
        int t_12783;
        int t_12802;
        int t_12821;
        SqlBuilder b__1317 = new SqlBuilder();
        if (this.isDistinct) {
            b__1317.appendSafe("SELECT DISTINCT ");
        } else {
            b__1317.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__1317.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__1318 = 1;
            while (true) {
                t_12783 = this.selectExprs.size();
                if (i__1318 >= t_12783) {
                    break;
                }
                b__1317.appendSafe(", ");
                b__1317.appendFragment(Core.listGet(this.selectExprs, i__1318));
                i__1318 = i__1318 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__1317.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__12776 = f__1319 -> f__1319.getSqlValue();
            b__1317.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__12776));
        }
        b__1317.appendSafe(" FROM ");
        b__1317.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__12775 = jc__1320 -> {
            b__1317.appendSafe(" ");
            String t_12763 = jc__1320.getJoinType().keyword();
            b__1317.appendSafe(t_12763);
            b__1317.appendSafe(" ");
            String t_12767 = jc__1320.getTable().getSqlValue();
            b__1317.appendSafe(t_12767);
            @Nullable SqlFragment oc__1321 = jc__1320.getOnCondition();
            if (oc__1321 != null) {
                SqlFragment oc_2478 = oc__1321;
                b__1317.appendSafe(" ON ");
                b__1317.appendFragment(oc_2478);
            }
        };
        this.joinClauses.forEach(fn__12775);
        if (!this.conditions.isEmpty()) {
            b__1317.appendSafe(" WHERE ");
            b__1317.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__1322 = 1;
            while (true) {
                t_12802 = this.conditions.size();
                if (i__1322 >= t_12802) {
                    break;
                }
                b__1317.appendSafe(" ");
                b__1317.appendSafe(Core.listGet(this.conditions, i__1322).keyword());
                b__1317.appendSafe(" ");
                b__1317.appendFragment(Core.listGet(this.conditions, i__1322).getCondition());
                i__1322 = i__1322 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__1317.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__12774 = f__1323 -> f__1323.getSqlValue();
            b__1317.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__12774));
        }
        if (!this.havingConditions.isEmpty()) {
            b__1317.appendSafe(" HAVING ");
            b__1317.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__1324 = 1;
            while (true) {
                t_12821 = this.havingConditions.size();
                if (i__1324 >= t_12821) {
                    break;
                }
                b__1317.appendSafe(" ");
                b__1317.appendSafe(Core.listGet(this.havingConditions, i__1324).keyword());
                b__1317.appendSafe(" ");
                b__1317.appendFragment(Core.listGet(this.havingConditions, i__1324).getCondition());
                i__1324 = i__1324 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__1317.appendSafe(" ORDER BY ");
            class Local_4 {
                boolean first__1325 = true;
            }
            final Local_4 local$4 = new Local_4();
            Consumer<OrderClause> fn__12773 = orc__1326 -> {
                String t_12758;
                String t_6760;
                if (!local$4.first__1325) {
                    b__1317.appendSafe(", ");
                }
                local$4.first__1325 = false;
                String t_12753 = orc__1326.getField().getSqlValue();
                b__1317.appendSafe(t_12753);
                if (orc__1326.isAscending()) {
                    t_6760 = " ASC";
                } else {
                    t_6760 = " DESC";
                }
                b__1317.appendSafe(t_6760);
                @Nullable NullsPosition np__1327 = orc__1326.getNullsPos();
                if (np__1327 != null) {
                    t_12758 = np__1327.keyword();
                    b__1317.appendSafe(t_12758);
                }
            };
            this.orderClauses.forEach(fn__12773);
        }
        @Nullable Integer lv__1328 = this.limitVal;
        if (lv__1328 != null) {
            int lv_2480 = lv__1328;
            b__1317.appendSafe(" LIMIT ");
            b__1317.appendInt32(lv_2480);
        }
        @Nullable Integer ov__1329 = this.offsetVal;
        if (ov__1329 != null) {
            int ov_2481 = ov__1329;
            b__1317.appendSafe(" OFFSET ");
            b__1317.appendInt32(ov_2481);
        }
        @Nullable LockMode lm__1330 = this.lockMode;
        if (lm__1330 != null) {
            b__1317.appendSafe(lm__1330.keyword());
        }
        return b__1317.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_12722;
        int t_12741;
        SqlBuilder b__1333 = new SqlBuilder();
        b__1333.appendSafe("SELECT COUNT(*) FROM ");
        b__1333.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__12710 = jc__1334 -> {
            b__1333.appendSafe(" ");
            String t_12700 = jc__1334.getJoinType().keyword();
            b__1333.appendSafe(t_12700);
            b__1333.appendSafe(" ");
            String t_12704 = jc__1334.getTable().getSqlValue();
            b__1333.appendSafe(t_12704);
            @Nullable SqlFragment oc2__1335 = jc__1334.getOnCondition();
            if (oc2__1335 != null) {
                SqlFragment oc2_2483 = oc2__1335;
                b__1333.appendSafe(" ON ");
                b__1333.appendFragment(oc2_2483);
            }
        };
        this.joinClauses.forEach(fn__12710);
        if (!this.conditions.isEmpty()) {
            b__1333.appendSafe(" WHERE ");
            b__1333.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__1336 = 1;
            while (true) {
                t_12722 = this.conditions.size();
                if (i__1336 >= t_12722) {
                    break;
                }
                b__1333.appendSafe(" ");
                b__1333.appendSafe(Core.listGet(this.conditions, i__1336).keyword());
                b__1333.appendSafe(" ");
                b__1333.appendFragment(Core.listGet(this.conditions, i__1336).getCondition());
                i__1336 = i__1336 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__1333.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__12709 = f__1337 -> f__1337.getSqlValue();
            b__1333.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__12709));
        }
        if (!this.havingConditions.isEmpty()) {
            b__1333.appendSafe(" HAVING ");
            b__1333.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__1338 = 1;
            while (true) {
                t_12741 = this.havingConditions.size();
                if (i__1338 >= t_12741) {
                    break;
                }
                b__1333.appendSafe(" ");
                b__1333.appendSafe(Core.listGet(this.havingConditions, i__1338).keyword());
                b__1333.appendSafe(" ");
                b__1333.appendFragment(Core.listGet(this.havingConditions, i__1338).getCondition());
                i__1338 = i__1338 + 1;
            }
        }
        return b__1333.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__1340) {
        SqlFragment return__520;
        Query t_6710;
        if (defaultLimit__1340 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__520 = this.toSql();
        } else {
            t_6710 = this.limit(defaultLimit__1340);
            return__520 = t_6710.toSql();
        }
        return return__520;
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
        @Nullable LockMode lockMode;
        boolean lockMode__set;
        public Builder lockMode(@Nullable LockMode lockMode) {
            lockMode__set = true;
            this.lockMode = lockMode;
            return this;
        }
        public Query build() {
            if (!limitVal__set || !offsetVal__set || !isDistinct__set || !lockMode__set || tableName == null || conditions == null || selectedFields == null || orderClauses == null || joinClauses == null || groupByFields == null || havingConditions == null || selectExprs == null) {
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
                if (!lockMode__set) {
                    _message.append(" lockMode");
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
            return new Query(tableName, conditions, selectedFields, orderClauses, limitVal, offsetVal, joinClauses, groupByFields, havingConditions, isDistinct, selectExprs, lockMode);
        }
    }
    public Query(SafeIdentifier tableName__1343, List<WhereClause> conditions__1344, List<SafeIdentifier> selectedFields__1345, List<OrderClause> orderClauses__1346, @Nullable Integer limitVal__1347, @Nullable Integer offsetVal__1348, List<JoinClause> joinClauses__1349, List<SafeIdentifier> groupByFields__1350, List<WhereClause> havingConditions__1351, boolean isDistinct__1352, List<SqlFragment> selectExprs__1353, @Nullable LockMode lockMode__1354) {
        this.tableName = tableName__1343;
        this.conditions = conditions__1344;
        this.selectedFields = selectedFields__1345;
        this.orderClauses = orderClauses__1346;
        this.limitVal = limitVal__1347;
        this.offsetVal = offsetVal__1348;
        this.joinClauses = joinClauses__1349;
        this.groupByFields = groupByFields__1350;
        this.havingConditions = havingConditions__1351;
        this.isDistinct = isDistinct__1352;
        this.selectExprs = selectExprs__1353;
        this.lockMode = lockMode__1354;
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
    public @Nullable LockMode getLockMode() {
        return this.lockMode;
    }
}
