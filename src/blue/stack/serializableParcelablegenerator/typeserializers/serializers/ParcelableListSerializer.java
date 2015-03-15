package blue.stack.serializableParcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import blue.stack.serializableParcelablegenerator.typeserializers.TypeSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;

/**
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class ParcelableListSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return String.format("%s.writeTypedList(%s);", parcel, field.getName());
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        String paramType = PsiUtils.getResolvedGenerics(field.getType()).get(0).getCanonicalText();
        return String.format("%s.readTypedList(%s, %s.CREATOR);", parcel, field.getName(), paramType);
    }
}
