package cn.icheny.intentlife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    @UiThread
    public static void bind(@NonNull Activity target) {
        createBinder(target, target.getIntent().getExtras());
    }

    /**
     * BindIntentKey annotated fields in the specified {@link android.app.Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    @UiThread
    public static void bind(@NonNull android.app.Fragment target) {
        createBinder(target, target.getArguments());
    }

    /**
     * BindIntentKey annotated fields in the specified {@link Fragment}. The current
     * arguments is data source.
     *
     * @param target Target fragment for data binding.
     */
    @UiThread
    public static void bind(@NonNull Fragment target) {
        createBinder(target, target.getArguments());
    }

    /**
     * BindIntentKey annotated fields in the specified {@code target} using the {@code source}
     * {@link Intent}.
     *
     * @param target Target class for data binding.
     * @param source Data source which contains Bundle.
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull Intent source) {
        createBinder(target, source.getExtras());
    }

    /**
     * BindIntentKey annotated fields in the specified {@code target} using the {@code source}
     * {@link Bundle}.
     *
     * @param target Target class for data binding.
     * @param source Data on which key will be looked up.
     */
    @UiThread
    public static void bind(@NonNull Object target, @NonNull Bundle source) {
        createBinder(target, source);
    }

    /**
     * Create binder.
     */
    private static void createBinder(Object target, Bundle source) {
        if (target == null || source == null) {
            if (loggable)
                Log.e(TAG, "The value of parameter in \"IntentLife.bind(target,source)\" is null." +
                        "IntentLife has stoped work on current bind event.");
            return;
        }
        try {
            final Class<?> clazz = Class.forName("cn.icheny.intentlife.BinderProxy");
            final Method sBindMethod = clazz.getMethod("bind", Object.class, Bundle.class);
            sBindMethod.invoke(null, target, source);
        } catch (ClassNotFoundException e) {
            if (loggable) Log.d(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke cn.icheny.intentlife.BinderProxy.bind(target, source)", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find proxy static method for bind(target, source)", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException("Unable to invoke cn.icheny.intentlife.BinderProxy.bind(target, source)", e);
        }
    }
}
