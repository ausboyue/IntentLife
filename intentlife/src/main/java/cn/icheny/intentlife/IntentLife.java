package cn.icheny.intentlife;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import cn.icheny.intentlife.annotations.BuildConfig;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Creator.
 * </pre>
 */
public class IntentLife {
    private static final String TAG = "IntentLife";
    private static boolean debug = BuildConfig.DEBUG;

    /**
     * BindIntentKey annotated fields in the specified {@link Activity}. The current intent
     * extras is data source.
     *
     * @param target Target activity for data binding.
     */
    public static void bind(Activity target) {
        final Bundle sourceBundle = target.getIntent().getExtras();
        bind(target, sourceBundle);
    }

    /**
     * BindIntentKey annotated fields in the specified {@link android.app.Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    public static void bind(android.app.Fragment target) {
        bind(target, target.getArguments());
    }

    /**
     * BindIntentKey annotated fields in the specified {@link Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    public static void bind(Fragment target) {
        bind(target, target.getArguments());
    }

    /**
     * BindIntentKey annotated fields in the specified {@code target} using the {@code source}
     * {@link Bundle}.
     *
     * @param target Target class for data binding.
     * @param source Data on which key will be looked up.
     */
    public static void bind(Object target, Bundle source) {
        final String name = target.getClass().getName();
        final String binderName = name + "_Binder";
        try {
            Class<?> clazz = Class.forName(binderName);
            clazz.getConstructor(target.getClass(), source.getClass()).newInstance(target, source);
        } catch (ClassNotFoundException e) {
            if (debug) Log.d(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke newInstance (target, source)", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke newInstance (target, source)", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + binderName, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to create binding instance.", cause);
        }
    }
}
