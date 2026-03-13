package orm.src;
import java.util.List;
import java.util.Map;
public interface Changeset {
    TableDef getTableDef();
    Map<String, String> getChanges();
    List<ChangesetError> getErrors();
    boolean isValid();
    Changeset cast(List<SafeIdentifier> allowedFields__668);
    Changeset validateRequired(List<SafeIdentifier> fields__671);
    Changeset validateLength(SafeIdentifier field__674, int min__675, int max__676);
    Changeset validateInt(SafeIdentifier field__679);
    Changeset validateInt64(SafeIdentifier field__682);
    Changeset validateFloat(SafeIdentifier field__685);
    Changeset validateBool(SafeIdentifier field__688);
    Changeset putChange(SafeIdentifier field__691, String value__692);
    String getChange(SafeIdentifier field__695);
    Changeset deleteChange(SafeIdentifier field__698);
    Changeset validateInclusion(SafeIdentifier field__701, List<String> allowed__702);
    Changeset validateExclusion(SafeIdentifier field__705, List<String> disallowed__706);
    Changeset validateNumber(SafeIdentifier field__709, NumberValidationOpts opts__710);
    Changeset validateAcceptance(SafeIdentifier field__713);
    Changeset validateConfirmation(SafeIdentifier field__716, SafeIdentifier confirmationField__717);
    Changeset validateContains(SafeIdentifier field__720, String substring__721);
    Changeset validateStartsWith(SafeIdentifier field__724, String prefix__725);
    Changeset validateEndsWith(SafeIdentifier field__728, String suffix__729);
    SqlFragment toInsertSql();
    SqlFragment toUpdateSql(int id__734);
}
