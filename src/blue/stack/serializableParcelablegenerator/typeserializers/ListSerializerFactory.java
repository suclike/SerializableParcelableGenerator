
package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.ListPrimitiveSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;
import com.intellij.psi.PsiType;

public class ListSerializerFactory implements TypeSerializerFactory {
    private TypeSerializer mSerializer = new ListPrimitiveSerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "java.util.List")) {
            return mSerializer;
        }

        return null;
    }
}
