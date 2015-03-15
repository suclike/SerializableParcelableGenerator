package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.BundleSerializer;
import com.intellij.psi.PsiType;


public class BundleSerializerFactory implements TypeSerializerFactory {
    private final BundleSerializer mSerializer;

    public BundleSerializerFactory() {
        mSerializer = new BundleSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if ("android.os.Bundle".equals(psiType.getCanonicalText())) {
            return mSerializer;
        }

        return null;
    }
}
