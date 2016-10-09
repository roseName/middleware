package com.y.middleware.Utils;

import android.content.Context;
import android.os.Environment;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 边界检查类
 */
public class CheckUtil {
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9][\\w\\.-]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	public static boolean isPhone(String strPhone) {
		String strPattern = "[1-9][0-9]{6,10}";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strPhone);
		return m.matches();
	}

	public static boolean isEn(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("en"))
			return true;
		else
			return false;
	}

	public static int getLanguage(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh")) {
			String country = locale.getCountry();
			if (country.endsWith("CN"))
				return 1;
			else
				return 2;
		} else {
			return 0;
		}
	}



	/**
	 * 是否有内存卡
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
