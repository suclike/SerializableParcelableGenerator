package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.SerializableObjectSerializer;
import com.intellij.psi.PsiType;


public class SerializableSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer;

    public SerializableSerializerFactory() {
        mSerializer = new SerializableObjectSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        PsiType[] superTypes = psiType.getSuperTypes();

        for (PsiType superType : superTypes) {
            String canonicalText = superType.getCanonicalText();

            if ("java.io.Serializable".equals(canonicalText)) {
                return mSerializer;
            }
        }

        return null;
    }
}
