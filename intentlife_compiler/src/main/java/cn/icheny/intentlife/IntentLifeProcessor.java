package cn.icheny.intentlife;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import cn.icheny.intentlife.annotation.BindIntentKey;


/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Annotation  Processor.
 * </pre>
 */
@AutoService(Processor.class)
public class IntentLifeProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Types mTypes;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // Support java8
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // Target annotation class:BindIntentKey
        return Collections.singleton(BindIntentKey.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment pe) {
        super.init(pe);
        mElementUtils = pe.getElementUtils();
        mTypes = pe.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        JavaFileCreator.create(mElementUtils, processingEnv.getFiler(), findTargetClasses(roundEnvironment));
        return false;
    }

    /**
     * Find all target "Activity" class that its fields are annotated by "BindIntentKey".
     */
    private Map<TypeElement, List<TargetField>> findTargetClasses(RoundEnvironment re) {
        if (re == null) {
            return Collections.emptyMap();
        }

        final Set<? extends Element> elements = re.getElementsAnnotatedWith(BindIntentKey.class);
        if (elements == null || elements.isEmpty()) {
            return Collections.emptyMap();
        }

        /**
         *  Save "TargetClass" by map
         *  @key TypeElement is Target Activity
         *  @key List<TargetField> are fields of Target Activity
         */
        final Map<TypeElement, List<TargetField>> targetClassMap = new HashMap<>();
        TargetField field = null;
        for (Element element : elements) {
            field = new TargetField();
            // like "userName"
            field.fieldName = element.getSimpleName().toString();
            // like "key_user"
            field.keyName = element.getAnnotation(BindIntentKey.class).value();
            // like "cn.icheny.intentlife.sample.User"
            field.fieldType = element.asType().toString();
            // Convert to TypeElement
            field.isParcelable = isParcelable((TypeElement) mTypes.asElement(element.asType()));
            // Target Activity,like "cn.icheny.intentlife.sample.MainActivity" what is encapsulated in "TypeElement"
            TypeElement activityTypeElement = (TypeElement) element.getEnclosingElement();
            List<TargetField> fields = targetClassMap.get(activityTypeElement);
            if (fields == null) {
                fields = new ArrayList<>();
                targetClassMap.put(activityTypeElement, fields);
            }
            fields.add(field);
        }
        return targetClassMap;
    }

    /**
     * Find all parent classes and interfaces from current class.
     */
    private boolean isParcelable(TypeElement currentClass) {

        if (null == currentClass) {
            return false;
        }
        for (TypeMirror mirror : currentClass.getInterfaces()) {
            if (mirror.toString().equals("android.os.Parcelable")) {
                return true;
            }
        }
        final TypeMirror superClassType = currentClass.getSuperclass();
        if (superClassType.getKind() == TypeKind.NONE) {
            // "TypeKind.NONE" meanings currentClass=java.lang.Object
            return false;
        }
        final TypeElement superClass = (TypeElement) mTypes.asElement(superClassType);
        return isParcelable(superClass);
    }

    /**
     * The method is only for testing.
     *
     * @param msg error log
     */
    private void showError(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
