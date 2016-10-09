package com.y.middleware.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	/**
	 * 获取当前时间戳
	 * 
	 * @return 时间戳
	 */
	public static long getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	/**
	 * 格式：yyyy-MM-dd
	 * 
	 * @param time
	 *            日期
	 * @return
	 */
	public static String formatDate(long time) {
		return format(time, "yyyy-MM-dd");
	}

	/**
	 * 格式化12小时制<br>
	 * 格式：yyyy-MM-dd hh-MM-ss
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String format12Time(long time) {
		return format(time, "yyyy-MM-dd hh:MM:ss");
	}

	/**
	 * 格式化24小时制<br>
	 * 格式：yyyy-MM-dd HH-MM-ss
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String format24Time(long time) {
		return format(time, "yyyy-MM-dd HH:MM:ss");
	}
	

	/**
	 * 格式化时间,自定义标签
	 * 
	 * @param time
	 *            时间
	 * @param pattern
	 *            格式化时间用的标签
	 * @return
	 */
	public static String format(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(time));
	}

	/**
	 * 获取当前天
	 * 
	 * @return 天
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前月
	 * 
	 * @return 月
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH)+1;
	}

	/**
	 * 获取当前年
	 * 
	 * @return 年
	 */
	@SuppressWarnings("static-access")
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 当天的起始时间
	 * */
	public static Long getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}

	/**
	 * 当天的结束时间
	 * */
	public static Long getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime().getTime();
	}

	/**
	 * 获取增加多少天的时间
	 * 
	 * @return addDay - 增加多少天
	 */
	public static Date getAddDayDate(int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, addDay);
		return calendar.getTime();
	}

	/**
	 * @创建人： liu
	 * @创建时间： 2016-1-18 下午4:01:59
	 * 
	 * @描述： 获取前几天的日期
	 * @param addDay
	 * @return
	 */
	public static String getDateBefore(int addDay) {
		Date addDayDate = getAddDayDate(addDay);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(addDayDate);
	}

	/**
	 * @创建人： liu
	 * @创建时间： 2016-1-18 下午4:01:59
	 * 
	 * @描述： 获取前几天的日期
	 * @param addDay
	 * @return
	 */
	public static String getDateBefore2(int addDay) {
		Date addDayDate = getAddDayDate(addDay);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
		return dateFormat.format(addDayDate);
	}

	/**
	 * 根据日期获取该日期所在的星期集合
	 * 
	 * @param mdate
	 * @return
	 */
	public static List<String> dateToWeek(String date) {
		Date mdate = stringToDate(date);
		int b = 0;
		if(mdate.getDay() == 0){
			b = 7;
		}else{
			b = mdate.getDay();
		}
		Date fdate;
		List<String> list = new ArrayList<String>();
		Long fTime = mdate.getTime() - b * 24 * 3600000;
		for (int a = 0; a < 7; a++) {
			fdate = new Date();
			fdate.setTime(fTime + ((a+1) * 24 * 3600000));
			// 先将Date转换成String，然后将String分隔成年、月、日，字符串数组的第3位为日
			list.add(a, (new SimpleDateFormat("yyyy-MM-dd")).format(fdate));
		}
		return list;
	}

	/**
	 * @创建人： liu
	 * @创建时间： 2016-1-23 下午2:35:06
	 * 
	 * @描述： 根据格式化后的字符串反转为Date
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		date = java.sql.Date.valueOf(str);
		return date;
	}
}
