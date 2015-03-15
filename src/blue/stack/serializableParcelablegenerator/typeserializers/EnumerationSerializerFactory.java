package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.EnumerationSerializer;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;


public class EnumerationSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer = new EnumerationSerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (psiType instanceof PsiClassReferenceType && ((PsiClassReferenceType) psiType).resolve().isEnum()) {
            return mSerializer;
        }

        return null;
    }
}
