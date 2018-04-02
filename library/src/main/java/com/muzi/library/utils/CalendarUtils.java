package com.muzi.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


    /**
     * cal2 比 cal1多的天数
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static int differentDays(Calendar cal1, Calendar cal2) {
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * Calendar to String
     *
     * @param calendar
     * @return
     */
    public static String getStringDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * String to Calendar
     *
     * @param s
     * @return
     */
    public static Calendar getCalendarDate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(s);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
