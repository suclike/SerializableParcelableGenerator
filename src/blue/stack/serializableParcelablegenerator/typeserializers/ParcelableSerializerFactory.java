package blue.stack.serializableParcelablegenerator.typeserializers;

import blue.stack.serializableParcelablegenerator.typeserializers.serializers.SParcelableObjectSerializer;
import com.intellij.psi.PsiType;
import blue.stack.serializableParcelablegenerator.typeserializers.serializers.ParcelableObjectSerializer;
import blue.stack.serializableParcelablegenerator.util.PsiUtils;


public class ParcelableSerializerFactory implements TypeSerializerFactory {

    private ParcelableObjectSerializer mSerializer = new ParcelableObjectSerializer();
    private SParcelableObjectSerializer mSParcelableObjectSerializer = new SParcelableObjectSerializer();
    @Override
    public TypeSerializer getSerializer(PsiType psiType) {
//        if (PsiUtils.isOfType(psiType, "android.os.Parcelable")) {
//            return mSerializer;
//        }else
     if(PsiUtils.isOfType(psiType, "blue.stack.serializableParcelable.IParcelable")){
            return  mSParcelableObjectSerializer;
        }

        return null;
    }
}
