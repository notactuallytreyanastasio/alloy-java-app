package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__462);
    Changeset validateRequired(List<SafeIdentifier> fields__465);
    Changeset validateLength(SafeIdentifier field__468, int min__469, int max__470);
    Changeset validateInt(SafeIdentifier field__473);
    Changeset validateInt64(SafeIdentifier field__476);
    Changeset validateFloat(SafeIdentifier field__479);
    Changeset validateBool(SafeIdentifier field__482);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__487);
}
