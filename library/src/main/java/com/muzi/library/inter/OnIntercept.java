package com.muzi.library.inter;

import com.muzi.library.adapter.CalendarAdapter;
import com.muzi.library.bean.MonthBean;

import java.util.Calendar;
import java.util.List;

/**
 * Created by muzi on 2018/4/2.
 * 727784430@qq.com
 */

public interface OnIntercept {

    void onIntercept(Calendar today, List<MonthBean> monthList, CalendarAdapter adapter);

}
