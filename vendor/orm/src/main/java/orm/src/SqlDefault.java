package orm.src;
/**
 * `SqlDefault` renders the literal SQL keyword `DEFAULT`, used for columns
 * with server-side default values (e.g., `NOW()` for timestamps).
 */
public final class SqlDefault implements SqlPart {
    public void formatTo(StringBuilder builder__1956) {
        builder__1956.append("DEFAULT");
    }
    public SqlDefault() {
    }
}
