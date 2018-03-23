package com.muzi.library.bean;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 */

public class SelectBean implements Cloneable {

    private int selectRv;//月位置
    private int selectDay;//日位置
    private DayBean dayBean;//日信息

    public SelectBean() {
    }

    public SelectBean(int selectRv, int selectDay) {
        this.selectRv = selectRv;
        this.selectDay = selectDay;
    }

    public SelectBean(int selectRv, int selectDay, DayBean dayBean) {
        this.selectRv = selectRv;
        this.selectDay = selectDay;
        this.dayBean = dayBean;
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

    public SelectBean clone() throws CloneNotSupportedException {
        SelectBean selectBean = (SelectBean) super.clone();
        selectBean.setDayBean(dayBean.clone());
        return selectBean;
    }

}
