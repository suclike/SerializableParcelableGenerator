package blue.stack.serializableParcelablegenerator.typeserializers;

import com.intellij.psi.PsiType;
import blue.stack.serializableParcelablegenerator.typeserializers.serializers.ParcelableObjectSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;


public class ParcelableSerializerFactory implements TypeSerializerFactory {

    private ParcelableObjectSerializer mSerializer = new ParcelableObjectSerializer();

    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
        if (PsiUtils.isOfType(psiType, "android.os.Parcelable")) {
            return mSerializer;
        }

        return null;
    }
}
