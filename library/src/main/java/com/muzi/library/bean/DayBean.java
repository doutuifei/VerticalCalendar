package com.muzi.library.bean;

import com.muzi.library.SelectState;
import com.muzi.library.utils.CalendarUtils;

import java.util.Calendar;

/**
 * Created by muzi on 2018/3/21.
 * 727784430@qq.com
 */

public class DayBean implements Cloneable {

    /**
     * 是否今天
     */
    private boolean isTodayDay;
    /**
     * 补全空白
     */
    private boolean isEmpty = false;
    /**
     * 星期：每月第一天才有
     */
    private String week;

    private int month;
    private int year;
    private int day;
    /**
     * 内容：今天、在途、开始、结束...
     */
    private String content;
    /**
     * 选中状态:
     */
    @SelectState.State
    private int selectState = SelectState.NONE;

    public DayBean() {
    }

    public DayBean(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isTodayDay() {
        return isTodayDay;
    }

    public void setTodayDay(boolean todayDay) {
        isTodayDay = todayDay;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSelectState() {
        return selectState;
    }

    public void setSelectState(@SelectState.State int selectState) {
        this.selectState = selectState;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }

    public Calendar getCalendar() {
        return CalendarUtils.getCurreCalendar(year, month - 1, day);
    }

    public DayBean clone() throws CloneNotSupportedException {
        return (DayBean) super.clone();
    }

}
