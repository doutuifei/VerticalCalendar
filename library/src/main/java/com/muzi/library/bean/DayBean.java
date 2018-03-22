package com.muzi.library.bean;

/**
 * Created by muzi on 2018/3/21.
 * 727784430@qq.com
 */

public class DayBean {

    /**
     * 是否今天
     */
    private boolean isCurreDay;
    /**
     * 是否选中
     */
    private boolean isSelect;
    /**
     * 是否可以点击
     * 如果不可以内容置灰
     */
    private boolean enabel = true;
    /**
     * 补全空白
     */
    private boolean isEmpty = false;
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
    private int selectState;

    public DayBean() {
    }

    public DayBean(boolean isEmpty, boolean enabel) {
        this.isEmpty = isEmpty;
        this.enabel = enabel;
    }

    public boolean isCurreDay() {
        return isCurreDay;
    }

    public void setCurreDay(boolean curreDay) {
        isCurreDay = curreDay;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isEnabel() {
        return enabel;
    }

    public void setEnabel(boolean enabel) {
        this.enabel = enabel;
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


    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
