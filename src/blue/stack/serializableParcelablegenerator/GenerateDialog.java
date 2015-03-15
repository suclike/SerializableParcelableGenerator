
package blue.stack.serializableParcelablegenerator;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class GenerateDialog extends DialogWrapper {

    private final LabeledComponent<JPanel> myComponent;
    private CollectionListModel<PsiField> myFields;

    protected GenerateDialog(PsiClass psiClass) {
        super(psiClass.getProject());
        setTitle("Select fields for SerializableParcelable generation");

        PsiField[] allFields = psiClass.getAllFields();
        PsiField[] fields = new PsiField[allFields.length];

        int i = 0;

        for (PsiField field : allFields) {
            // Exclude static fields
            if (!field.hasModifierProperty(PsiModifier.STATIC)) {
                fields[i++] = field;
            }
        }

        // i is post-incremented, so no need to add 1 for the count
        fields = Arrays.copyOfRange(fields, 0, i);

        myFields = new CollectionListModel<PsiField>(fields);

        JBList fieldList = new JBList(myFields);
        fieldList.setCellRenderer(new DefaultPsiElementCellRenderer());
        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(fieldList);
        decorator.disableAddAction();
        JPanel panel = decorator.createPanel();

        myComponent = LabeledComponent.create(panel, "Fields to include in Parcelable");

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

    public List<PsiField> getSelectedFields() {
        return myFields.getItems();
    }
}
