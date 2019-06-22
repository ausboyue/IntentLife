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
    private static final String FILE_COMMENT = "This class is generated automatically by IntentLife. Do not modify!";
    private static final String BUNDLE_CLASS_NAME = "android.os.Bundle";
    private static final String BINDER_CLASS_SUFFIX = "_Binder";
    private static final String CONDITION_CONTAINS_KEY = "if (source.containsKey($S))";
    private static final String STATEMENT_GET_DATA = "target.$N=($L)source.get($S)";
    private static final String TARGET = "target";
    private static final String SOURCE = "source";

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
            final String helperClassName = className.substring(packageName.length() + 1) + BINDER_CLASS_SUFFIX;

            // constructor method
            final MethodSpec.Builder privateMethodBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE);
            final MethodSpec.Builder publicMethodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className), TARGET)
                    .addParameter(ClassName.bestGuess(BUNDLE_CLASS_NAME), SOURCE);
            for (TargetField field : fields) {
                // like "java.lang.String"
                final String typeNameStr = field.fieldType;
                publicMethodBuilder.beginControlFlow(CONDITION_CONTAINS_KEY, field.keyName);
                // like "target.userName=(java.lang.String)source.get("key_user");"
                publicMethodBuilder.addStatement(STATEMENT_GET_DATA, field.fieldName, typeNameStr, field.keyName);
                publicMethodBuilder.endControlFlow();
            }
            final TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(helperClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(privateMethodBuilder.build())
                    .addMethod(publicMethodBuilder.build());
            final JavaFile file = JavaFile.builder(packageName, typeBuilder.build())
                    .addFileComment(FILE_COMMENT)
                    .build();
            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
