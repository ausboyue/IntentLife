package cn.icheny.intentlife;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Creator.
 * </pre>
 */
final class JavaFileCreator {
    static void create(Elements elementUtils, Filer filer, Map<TypeElement, List<TargetField>> targetClasses) {
        for (Map.Entry<TypeElement, List<TargetField>> entry : targetClasses.entrySet()) {

            final List<TargetField> fields = entry.getValue();
            if (fields == null || fields.isEmpty()) {
                continue;
            }
            final TypeElement activityTypeElement = entry.getKey();
            // Activity package name,like "cn.icheny.intentlife.sample".
            final String packageName = elementUtils.getPackageOf(activityTypeElement).getQualifiedName().toString();
            // Activity class name ,like "cn.icheny.intentlife.sample.MainActivity"
            final String className = activityTypeElement.getQualifiedName().toString();
            // helper class simple name,like "MainActivity_Binder"
            final String helperClassName = className.substring(packageName.length() + 1) + "_Binder";

            // constructor method
            final MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className), "target");
            methodBuilder.addStatement("android.content.Intent intent=target.getIntent()");
            for (TargetField field : fields) {
                // like "java.lang.String"
                final String typeNameStr = field.fieldType;
                methodBuilder.beginControlFlow("if (intent.hasExtra($S))", field.keyName);
                if (field.isParcelable) {
                    // like "target.userName=(java.lang.String)intent.getParcelableExtra("key_user");"
                    methodBuilder.addStatement("target.$N=($L)intent.getParcelableExtra($S)"
                            , field.fieldName, typeNameStr, field.keyName);
                } else {
                    // like "target.userName=(java.lang.String)intent.getSerializableExtra("key_user");"
                    methodBuilder.addStatement("target.$N=($L)intent.getSerializableExtra($S)"
                            , field.fieldName, typeNameStr, field.keyName);
                }
                methodBuilder.endControlFlow();

            }
            final TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(helperClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build());
            final JavaFile file = JavaFile.builder(packageName, typeBuilder.build())
                    .addFileComment("This class is generated automatically by IntentLife. Do not modify!")
                    .build();
            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
