
package blue.stack.serializableParcelablegenerator;

import blue.stack.serializableParcelablegenerator.typeserializers.*;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;

import java.util.List;


public class CodeGenerator {
    //  public static final String CREATOR_NAME = "CREATOR";

    private final PsiClass mClass;
    private final List<PsiField> mFields;
    private final TypeSerializerFactory mTypeSerializerFactory;

    public CodeGenerator(PsiClass psiClass, List<PsiField> fields) {
        mClass = psiClass;
        mFields = fields;

        this.mTypeSerializerFactory = new ChainSerializerFactory(
                new BundleSerializerFactory(),
                new DateSerializerFactory(),
                new EnumerationSerializerFactory(),
                new ParcelableListSerializerFactory(),
                new PrimitiveTypeSerializerFactory(),
                new PrimitiveArraySerializerFactory(),
                new ListSerializerFactory(),
                new ParcelableSerializerFactory(),
                new SerializableSerializerFactory()
        );
    }

    private String generateStaticCreator(PsiClass psiClass) {
//        StringBuilder sb = new StringBuilder("public static final blue.stack.serializableParcelable.IParcelable.Creator<");
//
//        String className = psiClass.getName();
//
//        sb.append(className).append("> CREATOR = new blue.stack.serializableParcelable.IParcelable.Creator<").append(className).append(">(){")
        StringBuilder sb = new StringBuilder("");
        sb.append("@Override public ").append(" byte").append("[] getParacelableBytes() {")
                .append("return new ").append("byte").append("[0];}")
        ;


        return sb.toString();
    }

    //  @Override
//public void createFromParcel(IParcel in)
    private String generateConstructor(List<PsiField> fields, PsiClass psiClass) {
        String className = psiClass.getName();

        StringBuilder sb = new StringBuilder("@Override public blue.stack.serializableParcelable.IParcelable createFromParcel(blue.stack.serializableParcelable.IParcel in) {");


        // Creates all of the deserialization methods for the given fields
        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).readValue(field, "in"));
        }
        sb.append("\n" +
                "return this;");
        sb.append("}");
        return sb.toString();
    }

    private String generateWriteToParcel(List<PsiField> fields) {
        StringBuilder sb = new StringBuilder("@Override public void writeToParcel(blue.stack.serializableParcelable.IParcel dest, int constructID) {");
        sb.append("dest.writeInt(constructID);\n");
        for (PsiField field : fields) {
            sb.append(getSerializerForType(field).writeValue(field, "dest", "constructID"));
        }

        sb.append("}");

        return sb.toString();
    }

    private String generategetParacelableBytes() {
        StringBuilder sb = new StringBuilder("@Override public byte[] getParacelableBytes() {");

//        ParcelObject parcelObject = new ParcelObject();
//        writeToParcel(parcelObject,constructID);
//        return parcelObject.toByteArray();
        sb.append("\n blue.stack.serializableParcelable.ParcelObject parcelObject = new blue.stack.serializableParcelable.ParcelObject(); \n");
        sb.append("writeToParcel(parcelObject,constructID); \n");
        sb.append("return parcelObject.toByteArray(); \n");
        sb.append("}");

        return sb.toString();
    }

    private TypeSerializer getSerializerForType(PsiField field) {
        return mTypeSerializerFactory.getSerializer(field.getType());
    }

//    private String generateDescribeContents() {
//        return "@Override public int describeContents() { return 0; }";
//    }

    public void generate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());

        removeExistingParcelableImplementation(mClass);

        // Describe contents method
        // PsiMethod describeContentsMethod = elementFactory.createMethodFromText(generateDescribeContents(), mClass);
        // Method for writing to the parcel
        PsiMethod writeToParcelMethod = elementFactory.createMethodFromText(generateWriteToParcel(mFields), mClass);

        // Default constructor if needed
        String defaultConstructorString = generateDefaultConstructor(mClass);
        PsiMethod defaultConstructor = null;
        PsiMethod generategetParacelableBytesMethod = elementFactory.createMethodFromText(generategetParacelableBytes(), mClass);
        if (defaultConstructorString != null) {
            defaultConstructor = elementFactory.createMethodFromText(defaultConstructorString, mClass);
        }

        // Constructor
        PsiMethod constructor = elementFactory.createMethodFromText(generateConstructor(mFields, mClass), mClass);
        // CREATOR
        //  PsiField creatorField = elementFactory.createFieldFromText(generateStaticCreator(mClass), mClass);

        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mClass.getProject());

        // Shorten all class references
        //  styleManager.shortenClassReferences(mClass.addBefore(describeContentsMethod, mClass.getLastChild()));
        styleManager.shortenClassReferences(mClass.addBefore(writeToParcelMethod, mClass.getLastChild()));
        styleManager.shortenClassReferences(mClass.addBefore(generategetParacelableBytesMethod, mClass.getLastChild()));
        // Only adds if available
        if (defaultConstructor != null) {
            styleManager.shortenClassReferences(mClass.addBefore(defaultConstructor, mClass.getLastChild()));
        }

        styleManager.shortenClassReferences(mClass.addBefore(constructor, mClass.getLastChild()));
        // styleManager.shortenClassReferences(mClass.addBefore(creatorField, mClass.getLastChild()));

        makeClassImplementParcelable(elementFactory);
    }

    /**
     * Strips the
     *
     * @param psiClass
     */
    private void removeExistingParcelableImplementation(PsiClass psiClass) {
        PsiField[] allFields = psiClass.getAllFields();

        // Look for an existing CREATOR and remove it
//        for (PsiField field : allFields) {
//            if (field.getName().equals(CREATOR_NAME)) {
//                // Creator already exists, need to remove/replace it
//                field.delete();
//            }
//        }
        findAndRemoveMethod(psiClass, "createFromParcel", "blue.stack.serializableParcelable.IParcel");
        findAndRemoveMethod(psiClass, "getParacelableBytes");
        //   findAndRemoveMethod(psiClass, "describeContents");
        findAndRemoveMethod(psiClass, "writeToParcel", "blue.stack.serializableParcelable.IParcel", "int");
    }

    private String generateDefaultConstructor(PsiClass clazz) {
        // Check for any constructors; if none exist, we'll make a default one
        if (clazz.getConstructors().length == 0) {
            // No constructors exist, make a default one for convenience
            StringBuilder sb = new StringBuilder();
            sb.append("public ").append(clazz.getName()).append("(){}").append('\n');
            return sb.toString();
        } else {
            return null;
        }
    }

    private void makeClassImplementParcelable(PsiElementFactory elementFactory) {
        final PsiClassType[] implementsListTypes = mClass.getImplementsListTypes();
        final String implementsType = "blue.stack.serializableParcelable.IParcelable";

        for (PsiClassType implementsListType : implementsListTypes) {
            PsiClass resolved = implementsListType.resolve();

            // Already implements Parcelable, no need to add it
            if (resolved != null && implementsType.equals(resolved.getQualifiedName())) {
                return;
            }
        }

        PsiJavaCodeReferenceElement implementsReference = elementFactory.createReferenceFromText(implementsType, mClass);
        PsiReferenceList implementsList = mClass.getImplementsList();

        if (implementsList != null) {
            implementsList.add(implementsReference);
        }
    }


    private static void findAndRemoveMethod(PsiClass clazz, String methodName, String... arguments) {
        // Maybe there's an easier way to do this with mClass.findMethodBySignature(), but I'm not an expert on Psi*
        PsiMethod[] methods = clazz.findMethodsByName(methodName, false);

        for (PsiMethod method : methods) {
            PsiParameterList parameterList = method.getParameterList();

            if (parameterList.getParametersCount() == arguments.length) {
                boolean shouldDelete = true;

                PsiParameter[] parameters = parameterList.getParameters();

                for (int i = 0; i < arguments.length; i++) {
                    if (!parameters[i].getType().getCanonicalText().equals(arguments[i])) {
                        shouldDelete = false;
                    }
                }

                if (shouldDelete) {
                    method.delete();
                }
            }
        }
    }
}
