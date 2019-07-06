package cn.icheny.intentlife;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

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
 *     @time   : 2019.07.03
 *     @desc   : Factory,Produce Binder and Proxy.
 * </pre>
 */
final class BinderFactory {
    private static final String PROXY_PACKAGE_NAME = "cn.icheny.intentlife";
    private static final String PROXY_NAME = "BinderProxy";
    private static final String FILE_COMMENT = "This class is generated automatically by IntentLife. Do not modify!";
    private static final String BUNDLE_CLASS_NAME = "android.os.Bundle";
    private static final String BINDER_CLASS_SUFFIX = "_Binder";
    private static final String CONDITION_CONTAINS_KEY = "if (source.containsKey($S))";
    private static final String CONDITION_INSTANCE_OF_TARGET_CLASS = "if (target instanceof $T)";
    private static final String STATEMENT_GET_DATA = "target.$N=($L)source.get($S)";
    private static final String STATEMENT_CREATE_BINDER_OBJECT = "$T binder = new $T()";
    private static final String STATEMENT_INVOKE_BIND_METHOD = "binder.bind(($T)target,source)";
    private static final String TARGET = "target";
    private static final String SOURCE = "source";
    private static final String BIND = "bind";
    private static final String OBJECT_CLASS_NAME = "java.lang.Object";


    static void produce(Elements elementUtils, Filer filer,
                        Map<TypeElement, List<TargetField>> targetClasses) {
        // bind method for proxy class.
        MethodSpec.Builder proxyBindMethodBuilder = MethodSpec.methodBuilder(BIND)
                .addModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess(OBJECT_CLASS_NAME), TARGET, Modifier.FINAL)
                .addParameter(ClassName.bestGuess(BUNDLE_CLASS_NAME), SOURCE, Modifier.FINAL);
        for (Map.Entry<TypeElement, List<TargetField>> entry : targetClasses.entrySet()) {
            final List<TargetField> fields = entry.getValue();
            if (fields == null || fields.isEmpty()) {
                continue;
            }
            /*
              Common parameter
             */
            final TypeElement targetTypeElement = entry.getKey();
            // Activity package name,like "cn.icheny.intentlife.sample".
            final String packageName = elementUtils.getPackageOf(targetTypeElement).getQualifiedName().toString();
            // Activity class name ,like "cn.icheny.intentlife.sample.MainActivity"
            final String targetClassName = targetTypeElement.getQualifiedName().toString();
            // binder class simple name,like "MainActivity_Binder"
            final String binderSimpleClassName = targetClassName.substring(packageName.length() + 1) + BINDER_CLASS_SUFFIX;

            // Generate binder.
            final JavaFile binder = produceBinder(packageName, targetClassName, binderSimpleClassName, fields);
            generateFile(filer, binder);
            // Add proxy control.
            addProxyControl(proxyBindMethodBuilder, packageName, targetClassName, binderSimpleClassName);
        }
        // Generate proxy.
        final JavaFile proxy = produceProxy(proxyBindMethodBuilder);
        generateFile(filer, proxy);
    }

    /**
     * Produce binder.
     */
    private static JavaFile produceBinder(String packageName, String targetClassName,
                                          String binderClassName, List<TargetField> fields) {
        // constructor method
        final MethodSpec.Builder constrBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
        final MethodSpec.Builder bindMethodBuilder = MethodSpec.methodBuilder(BIND)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.bestGuess(targetClassName), TARGET)
                .addParameter(ClassName.bestGuess(BUNDLE_CLASS_NAME), SOURCE);
        for (TargetField field : fields) {
            // like "java.lang.String"
            final String typeNameStr = field.fieldType;
            bindMethodBuilder.beginControlFlow(CONDITION_CONTAINS_KEY, field.keyName);
            // like "target.userName=(java.lang.String)source.get("key_user");"
            bindMethodBuilder.addStatement(STATEMENT_GET_DATA, field.fieldName, typeNameStr, field.keyName);
            bindMethodBuilder.endControlFlow();
        }
        final TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(binderClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(constrBuilder.build())
                .addMethod(bindMethodBuilder.build());
        return JavaFile.builder(packageName, typeBuilder.build())
                .addFileComment(FILE_COMMENT)
                .build();
    }

    /**
     * Add proxy control condition.
     */
    private static void addProxyControl(MethodSpec.Builder proxyBindMethodBuilder, String packageName,
                                        String targetClassName, String binderClassName) {
        final TypeName targetTypeName = ClassName.bestGuess(targetClassName);
        final TypeName binderTypeName = ClassName.get(packageName, binderClassName);
        proxyBindMethodBuilder.beginControlFlow(CONDITION_INSTANCE_OF_TARGET_CLASS, targetTypeName)
                .addStatement(STATEMENT_CREATE_BINDER_OBJECT, binderTypeName, binderTypeName)
                .addStatement(STATEMENT_INVOKE_BIND_METHOD, targetTypeName)
                .endControlFlow();
    }


    /**
     * Produce binder proxy.
     */
    private static JavaFile produceProxy(MethodSpec.Builder proxyBindMethodBuilder) {
        final TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(PROXY_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(proxyBindMethodBuilder.build());
        return JavaFile.builder(PROXY_PACKAGE_NAME, typeBuilder.build())
                .addFileComment(FILE_COMMENT)
                .build();
    }

    /**
     * Generate file.
     */
    private static void generateFile(final Filer filer, final JavaFile file) {
        //  Generate file,finally.
        JavaFileGenerator.generate(filer, file);
    }
}
