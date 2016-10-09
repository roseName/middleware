package com.y.middleware.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * Created by ywk on 2016/8/24.
 */

public class ToastUtil {

    /**
     * 短时间显示
     *
     * @param context 上下文对象
     * @param text    要显示的文本
     */
    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示
     *
     * @param context 上下文对象
     * @param resId   要显示的文本的id
     */
    public static void showShort(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     *
     * @param context 上下文对象
     * @param text    要显示的文本
     */
    public static void showLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示
     *
     * @param context 上下文对象
     * @param resId   要显示的文本的id
     */
    public static void showLong(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }


}
