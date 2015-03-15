package blue.stack.serializableParcelablegenerator.typeserializers.serializers;

import blue.stack.serializableParcelablegenerator.typeserializers.TypeSerializer;
import com.intellij.psi.PsiField;

/**
 * Serializer for types implementing Parcelable
 *
 * @author Dallas Gutauckis [dallas@gutauckis.com]
 */
public class SParcelableObjectSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeParcelable(this." + field.getName() + ", 0);";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = ("+field.getType().getCanonicalText()+")" + parcel + ".readParcelable();";
    }
}
