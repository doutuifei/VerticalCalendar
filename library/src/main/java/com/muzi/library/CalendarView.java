package com.muzi.library;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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
import com.muzi.library.inter.OnIntercept;
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
    private TextView textStart;
    private TextView textMonthStart;
    private TextView textMontStartMark;

    /**
     * 租期结束
     */
    private TextView textEnd;
    private TextView textMonthEnd;
    private TextView textMontEndMark;

    private Calendar calendar;
    private Calendar todayCalendar;
    private Calendar curreCalendar;

    private int tempYear;
    private int tempMonth;
    private String tempWeek;
    private int tempEmptyDays;

    private int todayYear;
    private int todayMonth;
    private int todayDay;

    /**
     * 当月天数
     */
    private int days;

    /**
     * 点击月份position
     */
    private int rvPosition = -1;
    /**
     * 今天的position
     */
    private int todayDayPosotion = -1;

    /**
     * 选中日期
     */
    private DayBean selectDayBean;
    private List<DayBean> tempDayBeanList;
    private int selectDays;
    private SelectBean startSelectBean;
    private SelectBean endSelectBean;
    private SelectBean tempSelectBean;

    /**
     * 不可选择天数
     */
    private int unableDays;

    /**
     * 在途天数
     */
    private int onOrders;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_calendar, this, true);
        recyclerView = findViewById(R.id.recyclerView);
        textStart = findViewById(R.id.textStart);
        textMonthStart = findViewById(R.id.textMonthStart);
        textMontStartMark = findViewById(R.id.textMontStartMark);

        textEnd = findViewById(R.id.textEnd);
        textMonthEnd = findViewById(R.id.textMonthEnd);
        textMontEndMark = findViewById(R.id.textMontEndMark);

        initToday();
        initCalendarList();
        initRecyclerView();
    }

    /**
     * 初始化今天
     */
    private void initToday() {
        todayCalendar = Calendar.getInstance();
        todayYear = todayCalendar.get(Calendar.YEAR);
        todayMonth = (todayCalendar.get(Calendar.MONTH) + 1);
        todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
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

            //计算第一天的星期
            tempWeek = CalendarUtils.getDayOfWeekByDate(tempYear, tempMonth - 1);

            //补全空白天数
            addEmptyDay(tempWeek, monthBean, dayList);

            //当月天数
            days = CalendarUtils.getMonthLastDay(tempYear, tempMonth - 1);

            for (int tempDay = 1; tempDay <= days; tempDay++) {
                dayBean = new DayBean();
                dayBean.setYear(tempYear);
                dayBean.setMonth(tempMonth);
                dayBean.setDay(tempDay);

                //只保存每月第一天星期
                if (tempDay == 1) {
                    dayBean.setWeek(tempWeek);
                }

                curreCalendar = CalendarUtils.getCurreCalendar(tempYear, tempMonth - 1, tempDay);

                //是否是today
                if (CalendarUtils.equalsCalendar(curreCalendar, todayCalendar)) {
                    dayBean.setTodayDay(true);
                    dayBean.setContent(getContext().getString(R.string.today));
                }

                //设置不可选择天数
                if (curreCalendar.before(todayCalendar) ||
                        CalendarUtils.equalsCalendar(curreCalendar, todayCalendar)) {
                    dayBean.setSelectState(SelectState.UNABLE);
                }

                dayList.add(dayBean);
            }
            monthBean.setYear(tempYear);
            monthBean.setMonth(tempMonth);
            monthBean.setDays(days);
            monthBean.setDayList(dayList);

            monthList.add(monthBean);
        }

    }

    /**
     * 填补空白天数
     */
    private void addEmptyDay(String week, MonthBean monthBean, List<DayBean> dayList) {
        switch (week) {
            case "周日":
                tempEmptyDays = 0;
                break;
            case "周一":
                tempEmptyDays = 1;
                break;
            case "周二":
                tempEmptyDays = 2;
                break;
            case "周三":
                tempEmptyDays = 3;
                break;
            case "周四":
                tempEmptyDays = 4;
                break;
            case "周五":
                tempEmptyDays = 5;
                break;
            case "周六":
                tempEmptyDays = 6;
                break;
        }
        for (int i = 0; i < tempEmptyDays; i++) {
            dayList.add(new DayBean(true));
        }
        monthBean.setEmptyDays(tempEmptyDays);
        tempEmptyDays = 0;
    }

    /**
     * 添加不可选择天数
     *
     * @param days
     */
    public void setUnableDays(@IntRange(from = 0) int days) {
        if (days < 0)
            return;

        if (unableDays == 0 && days == 0)
            return;

        if (unableDays == days)
            return;

        /**
         * 遍历查到今天的position
         */
        getTodayPosition();

        if (unableDays < days) {
            resetState();
            setUnableDaysUp(days);
            unableDays = days;
        } else {
            /**
             * 设置今天之后可用
             */
            tempDayBeanList = monthList.get(0).getDayList();
            for (int i1 = todayDayPosotion + 1; i1 < tempDayBeanList.size(); i1++) {
                tempDayBeanList.get(i1).setSelectState(SelectState.NONE);
            }
            for (int i = 1; i < monthList.size(); i++) {
                tempDayBeanList = monthList.get(i).getDayList();
                for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                    if (tempDayBeanList.get(i1).isEmpty()) {
                        continue;
                    } else {
                        if (tempDayBeanList.get(i1).getSelectState() == SelectState.NONE) {
                            unableDays = 0;
                            //相当于恢复默认状态,刷新adapter
                            if (days == 0) {
                                calendarAdapter.notifyDataSetChanged();
                            }
                            setUnableDays(days);
                            return;
                        }
                        tempDayBeanList.get(i1).setSelectState(SelectState.NONE);
                    }
                }
            }
        }
        calendarAdapter.notifyDataSetChanged();
    }

    /**
     * 遍历查到今天的position
     */
    private void getTodayPosition() {
        if (todayDayPosotion < 0) {
            tempDayBeanList = monthList.get(0).getDayList();
            for (int i = todayDay; i < tempDayBeanList.size(); i++) {
                if (tempDayBeanList.get(i).getDay() == todayDay) {
                    todayDayPosotion = i;
                    break;
                }
            }
        }
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
                    dayBean.setContent(getContext().getString(R.string.calendarEnd));
                    endSelectBean = new SelectBean();
                    endSelectBean.setDayBean(dayBean);
                    endSelectBean.setSelectRv(rvPosition);
                    endSelectBean.setSelectDay(dayPosition);

                    //更改中间的状态
                    changeBetweenState(SelectState.BETWEEN);
                } else {
                    //更改开始
                    clear();
                    dayBean.setSelectState(SelectState.PREVIEW_START);
                    dayBean.setContent(getContext().getString(R.string.calendarStart));
                    startSelectBean = new SelectBean();
                    startSelectBean.setDayBean(dayBean);
                    startSelectBean.setSelectRv(rvPosition);
                    startSelectBean.setSelectDay(dayPosition);
                }
                break;
            case SelectState.SINGLE:
                //开始和结束重合
                dayBean.setSelectState(SelectState.PREVIEW_START);
                dayBean.setContent(getContext().getString(R.string.calendarStart));
                endSelectBean = null;
                break;
            case SelectState.PREVIEW_START:
                //准备开始
                dayBean.setSelectState(SelectState.SINGLE);
                dayBean.setContent(getContext().getString(R.string.onDay));
                endSelectBean = new SelectBean();
                endSelectBean.setDayBean(dayBean);
                endSelectBean.setSelectRv(rvPosition);
                endSelectBean.setSelectDay(dayPosition);
                break;
            case SelectState.START:
                //开始
                clear();
                dayBean.setSelectState(SelectState.PREVIEW_START);
                dayBean.setContent(getContext().getString(R.string.calendarStart));
                startSelectBean = new SelectBean();
                startSelectBean.setDayBean(dayBean);
                startSelectBean.setSelectRv(rvPosition);
                startSelectBean.setSelectDay(dayPosition);
                endSelectBean = null;
                break;
            case SelectState.BETWEEN:
                //中间
                clear();
                dayBean.setSelectState(SelectState.PREVIEW_START);
                dayBean.setContent(getContext().getString(R.string.calendarStart));
                startSelectBean = new SelectBean();
                startSelectBean.setDayBean(dayBean);
                startSelectBean.setSelectRv(rvPosition);
                startSelectBean.setSelectDay(dayPosition);
                endSelectBean = null;
                break;
            case SelectState.END:
                //结束
                clear();
                dayBean.setSelectState(SelectState.PREVIEW_START);
                dayBean.setContent(getContext().getString(R.string.calendarStart));
                startSelectBean = new SelectBean();
                startSelectBean.setDayBean(dayBean);
                startSelectBean.setSelectRv(rvPosition);
                startSelectBean.setSelectDay(dayPosition);
                endSelectBean = null;
                break;
        }

        //开始时间晚于结束时间，反转时间
        if (startSelectBean != null && endSelectBean != null &&
                endSelectBean.getDayBean().getCalendar().before(startSelectBean.getDayBean().getCalendar())) {

            selectDayBean = monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay());
            selectDayBean.setContent(getContext().getString(R.string.calendarEnd));
            selectDayBean.setSelectState(SelectState.END);

            selectDayBean = monthList.get(endSelectBean.getSelectRv()).getDayList().get(endSelectBean.getSelectDay());
            selectDayBean.setContent(getContext().getString(R.string.calendarStart));
            selectDayBean.setSelectState(SelectState.START);

            try {
                tempSelectBean = endSelectBean.clone();
                endSelectBean = startSelectBean.clone();
                startSelectBean = tempSelectBean;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            //选中中间
            changeBetweenState(SelectState.BETWEEN);

        }

        //显示在途
        if (endSelectBean != null) {
            setOnOrder();
        }

        //处理点击事件
        handleListener();
    }

    /**
     * 设置在途
     */
    private void setOnOrder() {
        if (unableDays == 0) {
            return;
        }
        onOrders = unableDays;
        if (startSelectBean.getDayBean().getDay() > onOrders) {
            //同月
            for (int i = startSelectBean.getSelectDay() - 1; i > startSelectBean.getSelectDay() - 1 - onOrders; i--) {
                monthList.get(startSelectBean.getSelectRv()).getDayList().get(i).setContent(getContext().getString(R.string.onOrder));
            }
        } else {
            //跨月
            /**
             * 先设置当前月
             */
            for (int i = startSelectBean.getSelectDay() - 1; i >= 0; i--) {
                if (monthList.get(startSelectBean.getSelectRv()).getDayList().get(i).isEmpty()) {
                    break;
                }
                monthList.get(startSelectBean.getSelectRv()).getDayList().get(i).setContent(getContext().getString(R.string.onOrder));
                --onOrders;
            }

            /**
             * 遍历设置剩下的月份
             */
            while (onOrders > 0) {
                if (startSelectBean.getSelectRv() - 1 < 0) {
                    return;
                }
                for (int i = startSelectBean.getSelectRv() - 1; i >= 0; i--) {
                    tempDayBeanList = monthList.get(i).getDayList();
                    for (int i1 = tempDayBeanList.size() - 1; i1 >= 0; i1--) {
                        if (tempDayBeanList.get(i1).isEmpty()) {
                            break;
                        }
                        if (onOrders <= 0) {
                            return;
                        }
                        tempDayBeanList.get(i1).setContent(getContext().getString(R.string.onOrder));
                        --onOrders;
                    }
                    if (onOrders <= 0) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * 处理回调
     */
    private void handleListener() {
        if (startSelectBean != null) {
            if (onCalendarChange != null) {
                onCalendarChange.onStart(startSelectBean.getDayBean());
            }
            changeStartText(false);
            textMonthStart.setText(startSelectBean.getDayBean().getMonth() + "月" + startSelectBean.getDayBean().getDay() + "日");
        } else {
            changeStartText(true);
        }

        if (endSelectBean != null) {
            if (onCalendarChange != null) {
                onCalendarChange.onEnd(endSelectBean.getDayBean());
            }
            changeEndText(false);
            textMonthEnd.setText(endSelectBean.getDayBean().getMonth() + "月" + endSelectBean.getDayBean().getDay() + "日");
        } else {
            changeEndText(true);
        }

        if (startSelectBean != null && endSelectBean != null) {
            selectDays = CalendarUtils.differentDays(startSelectBean.getDayBean().getCalendar(),
                    endSelectBean.getDayBean().getCalendar());
            selectDays++;
            if (onCalendarChange != null) {
                onCalendarChange.onDays(selectDays);
            }
        } else {
            selectDays = 0;
        }
    }


    /**
     * 清空选项
     */
    private void clear() {
        if (startSelectBean == null) {
            return;
        }
        clearContent();
        if (endSelectBean == null) {
            //只选择开始
            changeStartState(SelectState.NONE, null);
        } else {
            //更改开始和结束状态
            changeStartState(SelectState.NONE, null);
            changeEndState(SelectState.NONE, null);

            //更改中间的状态
            changeBetweenState(SelectState.NONE);
        }

        startSelectBean = null;
        endSelectBean = null;
        selectDays = 0;
    }

    /**
     * 清除内容-在途
     */
    private void clearContent() {
        getTodayPosition();

        /**
         * 先遍历当前月，从今天之后开始遍历
         */
        tempDayBeanList = monthList.get(0).getDayList();
        for (int i1 = todayDayPosotion + 1; i1 < tempDayBeanList.size(); i1++) {
            if (tempDayBeanList.get(i1).isEmpty() ||
                    tempDayBeanList.get(i1).isTodayDay()) {
                continue;
            }
            if (CalendarUtils.equalsCalendar(tempDayBeanList.get(i1).getCalendar(), startSelectBean.getDayBean().getCalendar())) {
                return;
            }
            tempDayBeanList.get(i1).setContent(null);
        }

        /**
         * 然后遍历剩下的月份，遍历到起租日期停止
         */
        for (int i = 1; i < monthList.size(); i++) {
            tempDayBeanList = monthList.get(i).getDayList();
            for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                if (tempDayBeanList.get(i1).isEmpty() ||
                        tempDayBeanList.get(i1).isTodayDay()) {
                    continue;
                }
                if (CalendarUtils.equalsCalendar(tempDayBeanList.get(i1).getCalendar(), startSelectBean.getDayBean().getCalendar())) {
                    return;
                }
                tempDayBeanList.get(i1).setContent(null);
            }
        }
    }

    /**
     * 重置状态
     */
    public void resetState() {
        clear();
        calendarAdapter.notifyDataSetChanged();
        changeStartText(true);
        changeEndText(true);
    }

    /**
     * 更改开始状态
     *
     * @param state
     * @param content
     */
    private void changeStartState(@SelectState.State int state, String content) {
        monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setContent(content);
        monthList.get(startSelectBean.getSelectRv()).getDayList().get(startSelectBean.getSelectDay()).setSelectState(state);
    }

    /**
     * 更改结束状态
     *
     * @param state
     * @param content
     */
    private void changeEndState(@SelectState.State int state, String content) {
        monthList.get(endSelectBean.getSelectRv()).getDayList().get(endSelectBean.getSelectDay()).setContent(content);
        monthList.get(endSelectBean.getSelectRv()).getDayList().get(endSelectBean.getSelectDay()).setSelectState(state);
    }


    /**
     * 更改区间状态
     *
     * @param state
     */
    private void changeBetweenState(@SelectState.State int state) {
        if (startSelectBean == null || endSelectBean == null) {
            return;
        }
        if (startSelectBean.getSelectRv() == endSelectBean.getSelectRv()) {
            //在同一个月
            for (int i = startSelectBean.getSelectRv(); i <= endSelectBean.getSelectRv(); i++) {
                tempDayBeanList = monthList.get(i).getDayList();
                for (int j = startSelectBean.getSelectDay() + 1; j < endSelectBean.getSelectDay(); j++) {
                    tempDayBeanList.get(j).setSelectState(state);
                }
            }
        } else {
            //跨月
            for (int i = startSelectBean.getSelectRv(); i <= endSelectBean.getSelectRv(); i++) {
                tempDayBeanList = monthList.get(i).getDayList();
                if (i == startSelectBean.getSelectRv()) {
                    //开始月份
                    for (int i1 = startSelectBean.getSelectDay() + 1; i1 < tempDayBeanList.size(); i1++) {
                        tempDayBeanList.get(i1).setSelectState(state);
                    }
                } else if (i == endSelectBean.getSelectRv()) {
                    //结束月份
                    for (int i1 = 0; i1 < endSelectBean.getSelectDay(); i1++) {
                        tempDayBeanList.get(i1).setSelectState(state);
                    }
                } else if (i > startSelectBean.getSelectRv() && i < endSelectBean.getSelectRv()) {
                    //中间月份
                    for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                        tempDayBeanList.get(i1).setSelectState(state);
                    }
                }
            }
        }
    }

    /**
     * 设置不可选择天数
     *
     * @param days
     */
    private void setUnableDaysUp(int days) {
        /**
         * 如果days大于新的unable天数
         */
        tempDayBeanList = monthList.get(0).getDayList();
        if (todayDayPosotion + days < tempDayBeanList.size()) {
            //同月
            /**
             * 从今天+1天开始遍历，将后unableDays天设置不可选择
             */
            for (int i = todayDayPosotion + 1; i < todayDayPosotion + 1 + days; i++) {
                tempDayBeanList.get(i).setSelectState(SelectState.UNABLE);
            }
        } else {
            //跨月
            /**
             * 先将当月剩余天设置不可选
             */
            for (int i = todayDayPosotion + 1; i < tempDayBeanList.size(); i++) {
                tempDayBeanList.get(i).setSelectState(SelectState.UNABLE);
                days--;
            }
            /**
             * 遍历剩下的月
             */
            while (days > 0) {
                for (int i = 1; i < monthList.size(); i++) {
                    tempDayBeanList = monthList.get(i).getDayList();
                    days = days + monthList.get(i).getEmptyDays();
                    for (int i1 = 0; i1 < tempDayBeanList.size(); i1++) {
                        days--;
                        if (tempDayBeanList.get(i1).isEmpty()) {
                            continue;
                        } else {
                            tempDayBeanList.get(i1).setSelectState(SelectState.UNABLE);
                        }
                        if (days < 1) {
                            break;
                        }
                    }
                    if (days < 1) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 是否恢复初始状态
     *
     * @param isVisiable
     */
    private void changeStartText(boolean isVisiable) {
        textStart.setVisibility(isVisiable ? VISIBLE : GONE);
        textMonthStart.setVisibility(!isVisiable ? VISIBLE : GONE);
        textMontStartMark.setVisibility(!isVisiable ? VISIBLE : GONE);
    }

    /**
     * 是否恢复初始状态
     *
     * @param isVisiable
     */
    private void changeEndText(boolean isVisiable) {
        textEnd.setVisibility(isVisiable ? VISIBLE : GONE);
        textMonthEnd.setVisibility(!isVisiable ? VISIBLE : GONE);
        textMontEndMark.setVisibility(!isVisiable ? VISIBLE : GONE);
    }

    private OnCalendarChange onCalendarChange;

    public void setOnCalendarChange(OnCalendarChange onCalendarChange) {
        this.onCalendarChange = onCalendarChange;
    }

    public static abstract class OnCalendarChange {

        public abstract void onStart(DayBean dayBean);

        public abstract void onEnd(DayBean dayBean);

        public void onDays(int day) {
        }

    }

    /**
     * 获取开始日期
     *
     * @return
     */
    public DayBean getSelectStartDays() {
        return startSelectBean.getDayBean();
    }

    /**
     * 获取结束日期
     *
     * @return
     */
    public DayBean getSelectEndDays() {
        return endSelectBean.getDayBean();
    }

    /**
     * 获取选择天数
     *
     * @return
     */
    public int getSelectDays() {
        return selectDays;
    }

    /**
     * 自定义处理
     * 必须调用adapter.notifyDataSetChanged();
     *
     * @param onIntercept
     */
    public void setOnIntercept(OnIntercept onIntercept) {
        if (onIntercept != null) {
            onIntercept.onIntercept(todayCalendar, monthList, calendarAdapter);
        }
    }

    /**
     * 设置可用范围
     *
     * @param start
     * @param end
     */
    public void setEnableRange(Calendar start, Calendar end) {
        if (start == null || end == null) {
            return;
        }
        for (MonthBean monthBean : monthList) {
            for (DayBean dayBean : monthBean.getDayList()) {
                if (dayBean.getCalendar().before(start) ||
                        dayBean.getCalendar().after(end)) {
                    dayBean.setSelectState(SelectState.UNABLE);
                }
            }
        }
        calendarAdapter.notifyDataSetChanged();
    }

    /**
     * 设置可用范围 yyyy-MM-dd HH:mm:ss
     *
     * @param startDate
     * @param endDate
     */
    public void setEnableRange(String startDate, String endDate) {
        Calendar start = CalendarUtils.getCalendarDate(startDate);
        Calendar end = CalendarUtils.getCalendarDate(endDate);
        setEnableRange(start, end);
    }

}
