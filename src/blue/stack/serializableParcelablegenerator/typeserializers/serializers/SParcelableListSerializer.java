package blue.stack.serializableParcelablegenerator.typeserializers.serializers;

import blue.stack.serializableParcelablegenerator.typeserializers.TypeSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;
import com.intellij.psi.PsiField;


public class SParcelableListSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return String.format("%s.writeTypedList(%s);", parcel, field.getName());
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        String paramType = PsiUtils.getResolvedGenerics(field.getType()).get(0).getCanonicalText();
        return String.format("%s.readTypedList(%s );", parcel, field.getName());
    }
}
