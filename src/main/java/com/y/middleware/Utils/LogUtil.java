package com.y.middleware.Utils;

import android.text.TextUtils;
import android.util.Log;


/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * Created by yangwenke on 2016/8/24.
 */

public class LogUtil {

    //是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDbug;

    public static String customTagPrefix = "y_log";

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void v(String content) {
        if (!isDbug) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void d(String content) {
        if (!isDbug) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void i(String content) {
        if (!isDbug) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void w(String content) {
        if (!isDbug) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void e(String content) {
        if (!isDbug) return;
        String tag = generateTag();

        Log.e(tag, content);
    }


}
