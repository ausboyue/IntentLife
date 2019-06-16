package cn.icheny.intentlife;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Creator.
 * </pre>
 */
public class IntentLife {
    public static void bind(Activity activity) {
        String name = activity.getClass().getName();
        try {
            Class<?> clazz = Class.forName(name + "_Binder");
            clazz.getConstructor(activity.getClass()).newInstance(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
