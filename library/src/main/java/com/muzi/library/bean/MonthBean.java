package com.muzi.library.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzi on 2018/3/21.
 * 727784430@qq.com
 */

public class MonthBean implements Cloneable {

    private int year;
    private int month;
    private int days;//每月天数
    private int emptyDays;//空白天数
    private List<DayBean> dayList;//带空白的天数集合

    public MonthBean() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getEmptyDays() {
        return emptyDays;
    }

    public void setEmptyDays(int emptyDays) {
        this.emptyDays = emptyDays;
    }

    public List<DayBean> getDayList() {
        return dayList;
    }

    public void setDayList(List<DayBean> dayList) {
        this.dayList = dayList;
    }

    public MonthBean clone() throws CloneNotSupportedException {
        MonthBean monthBean = (MonthBean) super.clone();
        List<DayBean> list = new ArrayList<>();
        list.addAll(dayList);
        monthBean.setDayList(list);
        return monthBean;
    }

}
