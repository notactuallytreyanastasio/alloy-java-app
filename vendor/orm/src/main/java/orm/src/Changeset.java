package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__692);
    Changeset validateRequired(List<SafeIdentifier> fields__695);
    Changeset validateLength(SafeIdentifier field__698, int min__699, int max__700);
    Changeset validateInt(SafeIdentifier field__703);
    Changeset validateInt64(SafeIdentifier field__706);
    Changeset validateFloat(SafeIdentifier field__709);
    Changeset validateBool(SafeIdentifier field__712);
    Changeset putChange(SafeIdentifier field__715, String value__716);
    String getChange(SafeIdentifier field__719);
    Changeset deleteChange(SafeIdentifier field__722);
    Changeset validateInclusion(SafeIdentifier field__725, List<String> allowed__726);
    Changeset validateExclusion(SafeIdentifier field__729, List<String> disallowed__730);
    Changeset validateNumber(SafeIdentifier field__733, NumberValidationOpts opts__734);
    Changeset validateAcceptance(SafeIdentifier field__737);
    Changeset validateConfirmation(SafeIdentifier field__740, SafeIdentifier confirmationField__741);
    Changeset validateContains(SafeIdentifier field__744, String substring__745);
    Changeset validateStartsWith(SafeIdentifier field__748, String prefix__749);
    Changeset validateEndsWith(SafeIdentifier field__752, String suffix__753);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__758);
}
