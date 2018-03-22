package com.muzi.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.muzi.library.adapter.CalendarAdapter;
import com.muzi.library.bean.DayBean;
import com.muzi.library.bean.MonthBean;
import com.muzi.library.bean.SelectBean;
import com.muzi.library.manager.MLinearLayoutManager;
import com.muzi.library.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by muzi on 2018/3/21.
 * 727784430@qq.com
 */

public class CalendarView extends RelativeLayout {

    /**
     * 月数
     */
    private int maxMonth = 6;
    /**
     * 日历数据
     */
    private DayBean dayBean;
    private MonthBean monthBean;
    private List<DayBean> dayList;
    private List<MonthBean> monthList = new ArrayList<>();

    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    /**
     * 开始日期
     */
    private TextView textMonthStart;
    /**
     * 租期结束
     */
    private TextView textEnd;
    /**
     * 结束日期
     */
    private TextView textMonthEnd;
    private TextView textMontEndMark;

    private Calendar calendar;
    private Calendar todayCalendar;
    private Calendar curreCalendar;

    private int tempYear;
    private int tempMonth;
    private String tempWeek;
    private int tempEmptyDayNum;

    private int curreYear;
    private int curreMonth;
    private int curreDay;

    /**
     * 当月天数
     */
    private int numDay;

    private int rvPosition = -1;

    /**
     * 选中日期
     */
    private DayBean selectDayBean;
    private List<DayBean> tempDayBeanList;

    //开始和结束
    private SelectBean startSelectBean;
    private SelectBean endSelectBean;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_calendar, this, true);
        recyclerView = findViewById(R.id.recyclerView);
        textMonthStart = findViewById(R.id.textMonthStart);
        textEnd = findViewById(R.id.textEnd);
        textMonthEnd = findViewById(R.id.textMonthEnd);
        textMontEndMark = findViewById(R.id.textMontEndMark);

        initToday();
        initCalendarList();
        initRecyclerView();
    }

    private void initToday() {
        todayCalendar = Calendar.getInstance();
        curreYear = todayCalendar.get(Calendar.YEAR);
        curreMonth = (todayCalendar.get(Calendar.MONTH) + 1);
        curreDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
        textMonthStart.setText(curreYear + "月" + curreMonth + "日");
    }

    /**
     * 初始化日历数据
     */
    private void initCalendarList() {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        for (int i = 0; i < maxMonth; i++) {
            calendar.add(Calendar.MONTH, 1);
            //当前年月
            tempYear = calendar.get(Calendar.YEAR);
            tempMonth = calendar.get(Calendar.MONTH) + 1;

            monthBean = new MonthBean();
            dayList = new ArrayList<>();

            //当月天数
            numDay = CalendarUtils.getMonthLastDay(tempYear, tempMonth - 1);

            for (int tempDay = 1; tempDay <= numDay; tempDay++) {
                dayBean = new DayBean();
                dayBean.setYear(tempYear);
                dayBean.setMonth(tempMonth);
                dayBean.setDay(tempDay);
                if (tempDay == 1) {
                    //计算第一天的星期
                    tempWeek = CalendarUtils.getDayOfWeekByDate(tempYear, tempMonth - 1);
                    dayBean.setWeek(tempWeek);
                    addEmptyDay(tempWeek, dayList);
                }

                curreCalendar = CalendarUtils.getCurreCalendar(tempYear, tempMonth - 1, tempDay);

                //是否是today
                if (CalendarUtils.equalsCalendar(curreCalendar, todayCalendar)) {
                    dayBean.setSelectState(SelectState.UNABLE);
                    dayBean.setCurreDay(true);
                    dayBean.setContent("今天");
                }

                //如果是今天之前就unable
                if (curreCalendar.before(todayCalendar)) {
                    dayBean.setSelectState(SelectState.UNABLE);
                }

                //默认选中
                onDefaultSelect(curreCalendar, dayBean);

                dayList.add(dayBean);
            }
            monthBean.setYear(tempYear);
            monthBean.setMonth(tempMonth);
            monthBean.setDayList(dayList);

            monthList.add(monthBean);
        }
//        printLog();
    }

    /**
     * 填补空白天数
     */
    private void addEmptyDay(String week, List<DayBean> dayList) {
        switch (week) {
            case "周日":
                tempEmptyDayNum = 0;
                break;
            case "周一":
                tempEmptyDayNum = 1;
                break;
            case "周二":
                tempEmptyDayNum = 2;
                break;
            case "周三":
                tempEmptyDayNum = 3;
                break;
            case "周四":
                tempEmptyDayNum = 4;
                break;
            case "周五":
                tempEmptyDayNum = 5;
                break;
            case "周六":
                tempEmptyDayNum = 6;
                break;
        }
        for (int i = 0; i < tempEmptyDayNum; i++) {
            dayList.add(new DayBean(true));
        }
        tempEmptyDayNum = 0;
    }

    /**
     * 默认选中
     *
     * @param calendar
     */
    private void onDefaultSelect(Calendar calendar, DayBean dayBean) {

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new MLinearLayoutManager(getContext()));
        calendarAdapter = new CalendarAdapter(getContext(), R.layout.item_month, monthList);
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.rvMonth) {
                    rvPosition = position;
                }
            }
        });
        calendarAdapter.setListener(new CalendarAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (rvPosition > -1) {
                    handleClick(rvPosition, position);
                    //刷新adapter
                    calendarAdapter.notifyDataSetChanged();
                    rvPosition = -1;
                }
            }
        });
    }


    /**
     * 点击事件处理
     *
     * @param rvPosition-recyclerView position
     * @param dayPosition-day         position
     */
    private void handleClick(int rvPosition, int dayPosition) {
        dayBean = monthList.get(rvPosition).getDayList().get(dayPosition);
        switch (dayBean.getSelectState()) {
            case SelectState.NONE:
                if (startSelectBean != null && endSelectBean == null) {
                    //更改开始状态
                    selectDayBean = monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay());
                    selectDayBean.setSelectState(SelectState.START);

                    //更改结束的状态
                    dayBean.setSelectState(SelectState.END);
                    dayBean.setContent("归还");
                    endSelectBean = new SelectBean();
                    endSelectBean.setDayBean(dayBean);
                    endSelectBean.setSelectRv(rvPosition);
                    endSelectBean.setSelectDay(dayPosition);

                    //更改中间的状态
                    if (startSelectBean.getSelectRv() == endSelectBean.getSelectRv()) {
                        //在同一个月
                        for (int i = startSelectBean.getSelectRv(); i <= endSelectBean.getSelectRv(); i++) {
                            tempDayBeanList = monthList.get(i).getDayList();
                            for (int j = startSelectBean.getSelectDay() + 1; j < endSelectBean.getSelectDay(); j++) {
                                tempDayBeanList.get(j).setSelectState(SelectState.BETWEEN);
                            }
                        }
                    } else {
                        //跨月
                        for (int i = startSelectBean.getSelectRv(); i <= endSelectBean.getSelectRv(); i++) {
                            tempDayBeanList = monthList.get(i).getDayList();
                            if (i == startSelectBean.getSelectRv()) {
                                //开始月份
                                for (int i1 = startSelectBean.getSelectDay() + 1; i1 < tempDayBeanList.size(); i1++) {
                                    tempDayBeanList.get(i1).setSelectState(SelectState.BETWEEN);
                                }
                            } else if (i == endSelectBean.getSelectRv()) {
                                //结束月份
                                for (int i1 = 0; i1 < endSelectBean.getSelectDay(); i1++) {
                                    tempDayBeanList.get(i1).setSelectState(SelectState.BETWEEN);
                                }
                            } else if (i > startSelectBean.getSelectRv() && i < endSelectBean.getSelectRv()) {
                                //中间月份
                                for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                                    tempDayBeanList.get(i1).setSelectState(SelectState.BETWEEN);
                                }
                            }
                        }
                    }
                } else {
                    //更改开始
                    clear();
                    dayBean.setSelectState(SelectState.SINGLE);
                    dayBean.setContent("起租");
                    startSelectBean = new SelectBean();
                    startSelectBean.setDayBean(dayBean);
                    startSelectBean.setSelectRv(rvPosition);
                    startSelectBean.setSelectDay(dayPosition);
                }
                break;
            case SelectState.SINGLE:
                //开始和结束重合
                dayBean.setSelectState(SelectState.NONE);
                break;
            case SelectState.START:
                //开始
                dayBean.setSelectState(SelectState.SINGLE);
                break;
            case SelectState.BETWEEN:
                //中间
                dayBean.setSelectState(SelectState.END);
                break;
            case SelectState.END:
                //结束
                dayBean.setSelectState(SelectState.NONE);
                break;
        }
    }


    /**
     * 清空选项
     */
    public void clear() {
        if (startSelectBean == null) {
            return;
        }
        if (endSelectBean == null) {
            //只选择开始
            monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setContent(null);
            monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setSelectState(SelectState.NONE);
        } else {
            monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setContent(null);
            monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setSelectState(SelectState.NONE);

            monthList.get(endSelectBean.getSelectRv()).getDayList().get(endSelectBean.getSelectDay()).setContent(null);
            monthList.get(endSelectBean.getSelectRv()).getDayList().get(endSelectBean.getSelectDay()).setSelectState(SelectState.NONE);

            //更改中间的状态
            if (startSelectBean.getSelectRv() == endSelectBean.getSelectRv()) {
                //在同一个月
                for (int i = startSelectBean.getSelectRv() + 1; i <= endSelectBean.getSelectRv(); i++) {
                    tempDayBeanList = monthList.get(i).getDayList();
                    for (int j = startSelectBean.getSelectDay() + 1; j < endSelectBean.getSelectDay(); j++) {
                        tempDayBeanList.get(j).setSelectState(SelectState.NONE);
                    }
                }
            } else {
                //跨月
                for (int i = startSelectBean.getSelectRv(); i <= endSelectBean.getSelectRv(); i++) {
                    tempDayBeanList = monthList.get(i).getDayList();
                    if (i == startSelectBean.getSelectRv()) {
                        //开始月份
                        for (int i1 = startSelectBean.getSelectDay() + 1; i1 < tempDayBeanList.size(); i1++) {
                            tempDayBeanList.get(i1).setSelectState(SelectState.NONE);
                        }
                    } else if (i == endSelectBean.getSelectRv()) {
                        //结束月份
                        for (int i1 = 0; i1 < endSelectBean.getSelectDay(); i1++) {
                            tempDayBeanList.get(i1).setSelectState(SelectState.NONE);
                        }
                    } else if (i > startSelectBean.getSelectRv() && i < endSelectBean.getSelectRv()) {
                        //中间月份
                        for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                            tempDayBeanList.get(i1).setSelectState(SelectState.NONE);
                        }
                    }
                }
            }
        }

        startSelectBean = null;
        endSelectBean = null;
    }

    /**
     * log打印
     */
    private void printLog() {
        for (MonthBean monthBean : monthList) {
            for (DayBean dayBean : monthBean.getDayList()) {
                String date;
                date = dayBean.getYear() + "-" + dayBean.getMonth() + "-" + dayBean.getDay() +
                        "星期" + dayBean.getWeek() + "是否今天" + dayBean.isCurreDay() + "状态" + dayBean.getSelectState();
                Log.d("CalendarView", date);
            }
        }
    }

}
