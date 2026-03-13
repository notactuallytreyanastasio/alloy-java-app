package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__480);
    Changeset validateRequired(List<SafeIdentifier> fields__483);
    Changeset validateLength(SafeIdentifier field__486, int min__487, int max__488);
    Changeset validateInt(SafeIdentifier field__491);
    Changeset validateInt64(SafeIdentifier field__494);
    Changeset validateFloat(SafeIdentifier field__497);
    Changeset validateBool(SafeIdentifier field__500);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__505);
}
