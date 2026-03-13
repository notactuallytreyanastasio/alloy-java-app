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
    public Query where(SqlFragment condition__1275) {
        List<WhereClause> nb__1277 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1277, new AndCondition(condition__1275));
        return new Query(this.tableName, List.copyOf(nb__1277), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orWhere(SqlFragment condition__1279) {
        List<WhereClause> nb__1281 = new ArrayList<>(this.conditions);
        Core.listAdd(nb__1281, new OrCondition(condition__1279));
        return new Query(this.tableName, List.copyOf(nb__1281), this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query whereNull(SafeIdentifier field__1283) {
        SqlBuilder b__1285 = new SqlBuilder();
        b__1285.appendSafe(field__1283.getSqlValue());
        b__1285.appendSafe(" IS NULL");
        SqlFragment t_14044 = b__1285.getAccumulated();
        return this.where(t_14044);
    }
    public Query whereNotNull(SafeIdentifier field__1287) {
        SqlBuilder b__1289 = new SqlBuilder();
        b__1289.appendSafe(field__1287.getSqlValue());
        b__1289.appendSafe(" IS NOT NULL");
        SqlFragment t_14038 = b__1289.getAccumulated();
        return this.where(t_14038);
    }
    public Query whereIn(SafeIdentifier field__1291, List<SqlPart> values__1292) {
        Query return__512;
        SqlFragment t_14019;
        int t_14027;
        SqlFragment t_14032;
        fn__1293: {
            if (values__1292.isEmpty()) {
                SqlBuilder b__1294 = new SqlBuilder();
                b__1294.appendSafe("1 = 0");
                t_14019 = b__1294.getAccumulated();
                return__512 = this.where(t_14019);
                break fn__1293;
            }
            SqlBuilder b__1295 = new SqlBuilder();
            b__1295.appendSafe(field__1291.getSqlValue());
            b__1295.appendSafe(" IN (");
            b__1295.appendPart(Core.listGet(values__1292, 0));
            int i__1296 = 1;
            while (true) {
                t_14027 = values__1292.size();
                if (i__1296 >= t_14027) {
                    break;
                }
                b__1295.appendSafe(", ");
                b__1295.appendPart(Core.listGet(values__1292, i__1296));
                i__1296 = i__1296 + 1;
            }
            b__1295.appendSafe(")");
            t_14032 = b__1295.getAccumulated();
            return__512 = this.where(t_14032);
        }
        return return__512;
    }
    public Query whereInSubquery(SafeIdentifier field__1298, Query sub__1299) {
        SqlBuilder b__1301 = new SqlBuilder();
        b__1301.appendSafe(field__1298.getSqlValue());
        b__1301.appendSafe(" IN (");
        b__1301.appendFragment(sub__1299.toSql());
        b__1301.appendSafe(")");
        SqlFragment t_14014 = b__1301.getAccumulated();
        return this.where(t_14014);
    }
    public Query whereNot(SqlFragment condition__1303) {
        SqlBuilder b__1305 = new SqlBuilder();
        b__1305.appendSafe("NOT (");
        b__1305.appendFragment(condition__1303);
        b__1305.appendSafe(")");
        SqlFragment t_14005 = b__1305.getAccumulated();
        return this.where(t_14005);
    }
    public Query whereBetween(SafeIdentifier field__1307, SqlPart low__1308, SqlPart high__1309) {
        SqlBuilder b__1311 = new SqlBuilder();
        b__1311.appendSafe(field__1307.getSqlValue());
        b__1311.appendSafe(" BETWEEN ");
        b__1311.appendPart(low__1308);
        b__1311.appendSafe(" AND ");
        b__1311.appendPart(high__1309);
        SqlFragment t_13999 = b__1311.getAccumulated();
        return this.where(t_13999);
    }
    public Query whereLike(SafeIdentifier field__1313, String pattern__1314) {
        SqlBuilder b__1316 = new SqlBuilder();
        b__1316.appendSafe(field__1313.getSqlValue());
        b__1316.appendSafe(" LIKE ");
        b__1316.appendString(pattern__1314);
        SqlFragment t_13990 = b__1316.getAccumulated();
        return this.where(t_13990);
    }
    public Query whereILike(SafeIdentifier field__1318, String pattern__1319) {
        SqlBuilder b__1321 = new SqlBuilder();
        b__1321.appendSafe(field__1318.getSqlValue());
        b__1321.appendSafe(" ILIKE ");
        b__1321.appendString(pattern__1319);
        SqlFragment t_13983 = b__1321.getAccumulated();
        return this.where(t_13983);
    }
    public Query select(List<SafeIdentifier> fields__1323) {
        return new Query(this.tableName, this.conditions, fields__1323, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query selectExpr(List<SqlFragment> exprs__1326) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, exprs__1326, this.lockMode);
    }
    public Query orderBy(SafeIdentifier field__1329, boolean ascending__1330) {
        List<OrderClause> nb__1332 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1332, new OrderClause(field__1329, ascending__1330, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1332), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orderByNulls(SafeIdentifier field__1334, boolean ascending__1335, NullsPosition nulls__1336) {
        List<OrderClause> nb__1338 = new ArrayList<>(this.orderClauses);
        Core.listAdd(nb__1338, new OrderClause(field__1334, ascending__1335, nulls__1336));
        return new Query(this.tableName, this.conditions, this.selectedFields, List.copyOf(nb__1338), this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query limit(int n__1340) {
        if (n__1340 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, n__1340, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query offset(int n__1343) {
        if (n__1343 < 0) {
            throw Core.bubble();
        }
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, n__1343, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query join(JoinType joinType__1346, SafeIdentifier table__1347, SqlFragment onCondition__1348) {
        List<JoinClause> nb__1350 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1350, new JoinClause(joinType__1346, table__1347, onCondition__1348));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1350), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query innerJoin(SafeIdentifier table__1352, SqlFragment onCondition__1353) {
        InnerJoin t_13945 = new InnerJoin();
        return this.join(t_13945, table__1352, onCondition__1353);
    }
    public Query leftJoin(SafeIdentifier table__1356, SqlFragment onCondition__1357) {
        LeftJoin t_13943 = new LeftJoin();
        return this.join(t_13943, table__1356, onCondition__1357);
    }
    public Query rightJoin(SafeIdentifier table__1360, SqlFragment onCondition__1361) {
        RightJoin t_13941 = new RightJoin();
        return this.join(t_13941, table__1360, onCondition__1361);
    }
    public Query fullJoin(SafeIdentifier table__1364, SqlFragment onCondition__1365) {
        FullJoin t_13939 = new FullJoin();
        return this.join(t_13939, table__1364, onCondition__1365);
    }
    public Query crossJoin(SafeIdentifier table__1368) {
        List<JoinClause> nb__1370 = new ArrayList<>(this.joinClauses);
        Core.listAdd(nb__1370, new JoinClause(new CrossJoin(), table__1368, null));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, List.copyOf(nb__1370), this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query groupBy(SafeIdentifier field__1372) {
        List<SafeIdentifier> nb__1374 = new ArrayList<>(this.groupByFields);
        Core.listAdd(nb__1374, field__1372);
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, List.copyOf(nb__1374), this.havingConditions, this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query having(SqlFragment condition__1376) {
        List<WhereClause> nb__1378 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1378, new AndCondition(condition__1376));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1378), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query orHaving(SqlFragment condition__1380) {
        List<WhereClause> nb__1382 = new ArrayList<>(this.havingConditions);
        Core.listAdd(nb__1382, new OrCondition(condition__1380));
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, List.copyOf(nb__1382), this.isDistinct, this.selectExprs, this.lockMode);
    }
    public Query distinct() {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, true, this.selectExprs, this.lockMode);
    }
    public Query lock(LockMode mode__1386) {
        return new Query(this.tableName, this.conditions, this.selectedFields, this.orderClauses, this.limitVal, this.offsetVal, this.joinClauses, this.groupByFields, this.havingConditions, this.isDistinct, this.selectExprs, mode__1386);
    }
    public SqlFragment toSql() {
        int t_13830;
        int t_13849;
        int t_13868;
        SqlBuilder b__1390 = new SqlBuilder();
        if (this.isDistinct) {
            b__1390.appendSafe("SELECT DISTINCT ");
        } else {
            b__1390.appendSafe("SELECT ");
        }
        if (!this.selectExprs.isEmpty()) {
            b__1390.appendFragment(Core.listGet(this.selectExprs, 0));
            int i__1391 = 1;
            while (true) {
                t_13830 = this.selectExprs.size();
                if (i__1391 >= t_13830) {
                    break;
                }
                b__1390.appendSafe(", ");
                b__1390.appendFragment(Core.listGet(this.selectExprs, i__1391));
                i__1391 = i__1391 + 1;
            }
        } else if (this.selectedFields.isEmpty()) {
            b__1390.appendSafe("*");
        } else {
            Function<SafeIdentifier, String> fn__13823 = f__1392 -> f__1392.getSqlValue();
            b__1390.appendSafe(Core.listJoinObj(this.selectedFields, ", ", fn__13823));
        }
        b__1390.appendSafe(" FROM ");
        b__1390.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__13822 = jc__1393 -> {
            b__1390.appendSafe(" ");
            String t_13810 = jc__1393.getJoinType().keyword();
            b__1390.appendSafe(t_13810);
            b__1390.appendSafe(" ");
            String t_13814 = jc__1393.getTable().getSqlValue();
            b__1390.appendSafe(t_13814);
            @Nullable SqlFragment oc__1394 = jc__1393.getOnCondition();
            if (oc__1394 != null) {
                SqlFragment oc_2623 = oc__1394;
                b__1390.appendSafe(" ON ");
                b__1390.appendFragment(oc_2623);
            }
        };
        this.joinClauses.forEach(fn__13822);
        if (!this.conditions.isEmpty()) {
            b__1390.appendSafe(" WHERE ");
            b__1390.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__1395 = 1;
            while (true) {
                t_13849 = this.conditions.size();
                if (i__1395 >= t_13849) {
                    break;
                }
                b__1390.appendSafe(" ");
                b__1390.appendSafe(Core.listGet(this.conditions, i__1395).keyword());
                b__1390.appendSafe(" ");
                b__1390.appendFragment(Core.listGet(this.conditions, i__1395).getCondition());
                i__1395 = i__1395 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__1390.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__13821 = f__1396 -> f__1396.getSqlValue();
            b__1390.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__13821));
        }
        if (!this.havingConditions.isEmpty()) {
            b__1390.appendSafe(" HAVING ");
            b__1390.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__1397 = 1;
            while (true) {
                t_13868 = this.havingConditions.size();
                if (i__1397 >= t_13868) {
                    break;
                }
                b__1390.appendSafe(" ");
                b__1390.appendSafe(Core.listGet(this.havingConditions, i__1397).keyword());
                b__1390.appendSafe(" ");
                b__1390.appendFragment(Core.listGet(this.havingConditions, i__1397).getCondition());
                i__1397 = i__1397 + 1;
            }
        }
        if (!this.orderClauses.isEmpty()) {
            b__1390.appendSafe(" ORDER BY ");
            class Local_4 {
                boolean first__1398 = true;
            }
            final Local_4 local$4 = new Local_4();
            Consumer<OrderClause> fn__13820 = orc__1399 -> {
                String t_13805;
                String t_7240;
                if (!local$4.first__1398) {
                    b__1390.appendSafe(", ");
                }
                local$4.first__1398 = false;
                String t_13800 = orc__1399.getField().getSqlValue();
                b__1390.appendSafe(t_13800);
                if (orc__1399.isAscending()) {
                    t_7240 = " ASC";
                } else {
                    t_7240 = " DESC";
                }
                b__1390.appendSafe(t_7240);
                @Nullable NullsPosition np__1400 = orc__1399.getNullsPos();
                if (np__1400 != null) {
                    t_13805 = np__1400.keyword();
                    b__1390.appendSafe(t_13805);
                }
            };
            this.orderClauses.forEach(fn__13820);
        }
        @Nullable Integer lv__1401 = this.limitVal;
        if (lv__1401 != null) {
            int lv_2625 = lv__1401;
            b__1390.appendSafe(" LIMIT ");
            b__1390.appendInt32(lv_2625);
        }
        @Nullable Integer ov__1402 = this.offsetVal;
        if (ov__1402 != null) {
            int ov_2626 = ov__1402;
            b__1390.appendSafe(" OFFSET ");
            b__1390.appendInt32(ov_2626);
        }
        @Nullable LockMode lm__1403 = this.lockMode;
        if (lm__1403 != null) {
            b__1390.appendSafe(lm__1403.keyword());
        }
        return b__1390.getAccumulated();
    }
    public SqlFragment countSql() {
        int t_13769;
        int t_13788;
        SqlBuilder b__1406 = new SqlBuilder();
        b__1406.appendSafe("SELECT COUNT(*) FROM ");
        b__1406.appendSafe(this.tableName.getSqlValue());
        Consumer<JoinClause> fn__13757 = jc__1407 -> {
            b__1406.appendSafe(" ");
            String t_13747 = jc__1407.getJoinType().keyword();
            b__1406.appendSafe(t_13747);
            b__1406.appendSafe(" ");
            String t_13751 = jc__1407.getTable().getSqlValue();
            b__1406.appendSafe(t_13751);
            @Nullable SqlFragment oc2__1408 = jc__1407.getOnCondition();
            if (oc2__1408 != null) {
                SqlFragment oc2_2628 = oc2__1408;
                b__1406.appendSafe(" ON ");
                b__1406.appendFragment(oc2_2628);
            }
        };
        this.joinClauses.forEach(fn__13757);
        if (!this.conditions.isEmpty()) {
            b__1406.appendSafe(" WHERE ");
            b__1406.appendFragment(Core.listGet(this.conditions, 0).getCondition());
            int i__1409 = 1;
            while (true) {
                t_13769 = this.conditions.size();
                if (i__1409 >= t_13769) {
                    break;
                }
                b__1406.appendSafe(" ");
                b__1406.appendSafe(Core.listGet(this.conditions, i__1409).keyword());
                b__1406.appendSafe(" ");
                b__1406.appendFragment(Core.listGet(this.conditions, i__1409).getCondition());
                i__1409 = i__1409 + 1;
            }
        }
        if (!this.groupByFields.isEmpty()) {
            b__1406.appendSafe(" GROUP BY ");
            Function<SafeIdentifier, String> fn__13756 = f__1410 -> f__1410.getSqlValue();
            b__1406.appendSafe(Core.listJoinObj(this.groupByFields, ", ", fn__13756));
        }
        if (!this.havingConditions.isEmpty()) {
            b__1406.appendSafe(" HAVING ");
            b__1406.appendFragment(Core.listGet(this.havingConditions, 0).getCondition());
            int i__1411 = 1;
            while (true) {
                t_13788 = this.havingConditions.size();
                if (i__1411 >= t_13788) {
                    break;
                }
                b__1406.appendSafe(" ");
                b__1406.appendSafe(Core.listGet(this.havingConditions, i__1411).keyword());
                b__1406.appendSafe(" ");
                b__1406.appendFragment(Core.listGet(this.havingConditions, i__1411).getCondition());
                i__1411 = i__1411 + 1;
            }
        }
        return b__1406.getAccumulated();
    }
    public SqlFragment safeToSql(int defaultLimit__1413) {
        SqlFragment return__537;
        Query t_7190;
        if (defaultLimit__1413 < 0) {
            throw Core.bubble();
        }
        if (this.limitVal != null) {
            return__537 = this.toSql();
        } else {
            t_7190 = this.limit(defaultLimit__1413);
            return__537 = t_7190.toSql();
        }
        return return__537;
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
    public Query(SafeIdentifier tableName__1416, List<WhereClause> conditions__1417, List<SafeIdentifier> selectedFields__1418, List<OrderClause> orderClauses__1419, @Nullable Integer limitVal__1420, @Nullable Integer offsetVal__1421, List<JoinClause> joinClauses__1422, List<SafeIdentifier> groupByFields__1423, List<WhereClause> havingConditions__1424, boolean isDistinct__1425, List<SqlFragment> selectExprs__1426, @Nullable LockMode lockMode__1427) {
        this.tableName = tableName__1416;
        this.conditions = conditions__1417;
        this.selectedFields = selectedFields__1418;
        this.orderClauses = orderClauses__1419;
        this.limitVal = limitVal__1420;
        this.offsetVal = offsetVal__1421;
        this.joinClauses = joinClauses__1422;
        this.groupByFields = groupByFields__1423;
        this.havingConditions = havingConditions__1424;
        this.isDistinct = isDistinct__1425;
        this.selectExprs = selectExprs__1426;
        this.lockMode = lockMode__1427;
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
