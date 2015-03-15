package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.SParcelableListSerializer;
import com.intellij.psi.PsiType;
import blue.stack.serializableParcelablegenerator.typeserializers.serializers.ParcelableListSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;


public class ParcelableListSerializerFactory implements TypeSerializerFactory {

    private ParcelableListSerializer mSerializer;
private SParcelableListSerializer mSPSerializer;
    public ParcelableListSerializerFactory() {
        mSerializer = new ParcelableListSerializer();
        mSPSerializer=new SParcelableListSerializer();
    }

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isTypedClass(psiType, "java.util.List", "blue.stack.serializableParcelable.IParcelable")) {
            return mSPSerializer;
        }

        // There might actually be a way to do this w/ a Collection, but it might not be order-safe
        if (PsiUtils.isTypedClass(psiType, "java.util.List", "android.os.Parcelable")) {
            return mSerializer;
        }

        return null;
    }
}
