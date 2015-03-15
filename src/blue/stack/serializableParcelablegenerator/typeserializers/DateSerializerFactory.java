package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.DateSerializer;
import com.intellij.psi.PsiType;


public class DateSerializerFactory implements TypeSerializerFactory {
    private final DateSerializer mSerializer;

    public DateSerializerFactory() {
        mSerializer = new DateSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if ("java.util.Date".equals(psiType.getCanonicalText())) {
            return mSerializer;
        }

        return null;
    }
}
