package cn.icheny.intentlife;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.06.15
 *     @desc   : Use this class to simplify finding data from Intent or Bundle by binding them
 *               with annotations.
 * </pre>
 */
public class IntentLife {
    private static final String TAG = "IntentLife";
    private static boolean loggable;

    /**
     * Control whether debug logging is enabled.
     */
    public static void setLoggable(boolean loggable) {
        IntentLife.loggable = loggable;
    }

    /**
     * BindIntentKey annotated fields in the specified {@link Activity}. The current intent
     * extras is data source.
     *
     * @param target Target activity for data binding.
     */
    public static void bind(@NonNull Activity target) {
        final Bundle sourceBundle = target.getIntent().getExtras();
        if (sourceBundle == null) {
            return;
        }
        createBinder(target, sourceBundle);
    }

    /**
     * BindIntentKey annotated fields in the specified {@link android.app.Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    public static void bind(@NonNull android.app.Fragment target) {
        Bundle sourceBundle = target.getArguments();
        if (sourceBundle == null) {
            return;
        }
        createBinder(target, sourceBundle);
    }

    /**
     * BindIntentKey annotated fields in the specified {@link Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    public static void bind(@NonNull Fragment target) {
        Bundle sourceBundle = target.getArguments();
        if (sourceBundle == null) {
            return;
        }
        createBinder(target, sourceBundle);
    }

    /**
     * BindIntentKey annotated fields in the specified {@code target} using the {@code source}
     * {@link Bundle}.
     *
     * @param target Target class for data binding.
     * @param source Data on which key will be looked up.
     */
    public static void bind(@NonNull Object target, @NonNull Bundle source) {
        createBinder(target, source);
    }

    /**
     * Create binder.
     */
    private static void createBinder(@NonNull Object target, @NonNull Bundle source) {
        final String name = target.getClass().getName();
        final String binderName = name + "_Binder";
        try {
            final Class<?> clazz = Class.forName(binderName);
            final Constructor<?> constructor = clazz.getConstructor(target.getClass(), source.getClass());
            constructor.newInstance(target, source);
        } catch (ClassNotFoundException e) {
            if (loggable) Log.d(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke newInstance(target, source)", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke newInstance(target, source)", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binder constructor for " + binderName, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to create binder instance.", cause);
        }
    }
}
