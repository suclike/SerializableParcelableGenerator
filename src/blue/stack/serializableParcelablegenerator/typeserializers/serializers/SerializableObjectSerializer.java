package blue.stack.serializableParcelablegenerator.typeserializers.serializers;

import com.intellij.psi.PsiField;
import blue.stack.serializableParcelablegenerator.typeserializers.TypeSerializer;

/**
 * Modified by Dallas Gutauckis [dallas@gutauckis.com]
 */
public class SerializableObjectSerializer implements TypeSerializer {
    @Override
    public String writeValue(PsiField field, String parcel, String flags) {
        return parcel + ".writeSerializable(this." + field.getName() + ");";
    }

    @Override
    public String readValue(PsiField field, String parcel) {
        return "this." + field.getName() + " = (" + field.getType().getCanonicalText() + ") " + parcel + ".readSerializable();";
    }
}
