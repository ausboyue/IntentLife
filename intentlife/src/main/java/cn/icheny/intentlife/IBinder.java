package cn.icheny.intentlife;

import android.support.annotation.UiThread;

/**
 * <pre>
 *     @author : www.icheny.cn
 *     @e-mail : ausboyue@gmail.com
 *     @time   : 2019.07.25
 *     @desc   : The binder interface.All of its child will overide {@link #bind()} function.
 * </pre>
 */
public interface IBinder {
    @UiThread
    void bind();
}
