package blue.stack.serializableParcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import blue.stack.serializableParcelablegenerator.typeserializers.serializers.ParcelableListSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;


public class ParcelableListSerializerFactory implements TypeSerializerFactory {

    private ParcelableListSerializer mSerializer;

    public ParcelableListSerializerFactory() {
        mSerializer = new ParcelableListSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        // There might actually be a way to do this w/ a Collection, but it might not be order-safe
        if (PsiUtils.isTypedClass(psiType, "java.util.List", "android.os.Parcelable")) {
            return mSerializer;
        }

        return null;
    }
}
