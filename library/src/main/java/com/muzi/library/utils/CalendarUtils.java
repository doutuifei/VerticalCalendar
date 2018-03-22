package com.muzi.library.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 */

public class CalendarUtils {

    private static Calendar tempCalendar;

    private static SimpleDateFormat format;

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        tempCalendar = Calendar.getInstance();
        tempCalendar.set(Calendar.YEAR, year);
        tempCalendar.set(Calendar.MONTH, month);
        tempCalendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        tempCalendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = tempCalendar.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取月第一天星期
     *
     * @return
     */
    public static String getDayOfWeekByDate(int year, int month) {
        if (format == null) {
            format = new SimpleDateFormat("E");
        }
        tempCalendar = Calendar.getInstance();
        tempCalendar.set(year, month, 1);
        return format.format(tempCalendar.getTime());
    }

    /**
     * 获取当前Calendar
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Calendar getCurreCalendar(int year, int month, int day) {
        tempCalendar = Calendar.getInstance();
        tempCalendar.set(year, month, day);
        return tempCalendar;
    }

    /**
     * 判断日期是否是同一天
     *
     * @param agr0
     * @param arg1
     * @return
     */
    public static boolean equalsCalendar(Calendar agr0, Calendar arg1) {
        if (agr0 == null || arg1 == null) {
            return false;
        }
        if (agr0.get(Calendar.YEAR) == arg1.get(Calendar.YEAR) &&
                agr0.get(Calendar.MONTH) == arg1.get(Calendar.MONTH) &&
                agr0.get(Calendar.DAY_OF_MONTH) == arg1.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

}
