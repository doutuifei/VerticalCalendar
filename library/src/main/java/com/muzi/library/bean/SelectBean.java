package com.muzi.library.bean;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 */

public class SelectBean {

    private int selectRv;
    private int selectDay;
    private DayBean dayBean;

    public SelectBean() {
    }

    public SelectBean(int selectRv, int selectDay) {
        this.selectRv = selectRv;
        this.selectDay = selectDay;
    }

    public int getSelectRv() {
        return selectRv;
    }

    public void setSelectRv(int selectRv) {
        this.selectRv = selectRv;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        this.selectDay = selectDay;
    }

    public DayBean getDayBean() {
        return dayBean;
    }

    public void setDayBean(DayBean dayBean) {
        this.dayBean = dayBean;
    }
}
