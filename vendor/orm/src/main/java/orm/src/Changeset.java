package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__323);
    Changeset validateRequired(List<SafeIdentifier> fields__326);
    Changeset validateLength(SafeIdentifier field__329, int min__330, int max__331);
    Changeset validateInt(SafeIdentifier field__334);
    Changeset validateInt64(SafeIdentifier field__337);
    Changeset validateFloat(SafeIdentifier field__340);
    Changeset validateBool(SafeIdentifier field__343);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__348);
}
