package com.muzi.library.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.library.R;
import com.muzi.library.SelectState;
import com.muzi.library.bean.DayBean;
import com.muzi.library.utils.StringUtils;

import java.util.List;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 */

public class MonthAdapter extends BaseQuickAdapter<DayBean, BaseViewHolder> {

    private Context context;

    public MonthAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<DayBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DayBean item) {
        if (item.isEmpty()) {
            return;
        }
        //日期
        helper.setText(R.id.textDay, String.valueOf(item.getDay()));
        helper.addOnClickListener(R.id.viewDay);

        //内容不为空
        if (StringUtils.isEmpty(item.getContent())) {
            helper.setText(R.id.textState, null);
            helper.setGone(R.id.textState, false);
        } else {
            helper.setText(R.id.textState, item.getContent());
            helper.setGone(R.id.textState, true);
        }

        //状态判断
        switch (item.getSelectState()) {
            case SelectState.UNABLE:
                //不可用
                helper.setBackgroundRes(R.id.viewDay, 0);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.textUnable));
                helper.getView(R.id.viewDay).setEnabled(false);
                break;
            case SelectState.NONE:
                //没有选中
                helper.setBackgroundRes(R.id.viewDay, 0);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.textNormal));
                break;
            case SelectState.SINGLE:
                //开始和结束重合
                helper.setBackgroundRes(R.id.viewDay, R.drawable.bg_single);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.white));
                helper.setTextColor(R.id.textState, ContextCompat.getColor(context, R.color.white));
                break;
            case SelectState.PREVIEW_START:
                //准备开始
                helper.setBackgroundRes(R.id.viewDay, R.drawable.bg_single);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.white));
                helper.setTextColor(R.id.textState, ContextCompat.getColor(context, R.color.white));
                break;
            case SelectState.START:
                //开始
                helper.setBackgroundRes(R.id.viewDay, R.drawable.bg_start);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.white));
                helper.setTextColor(R.id.textState, ContextCompat.getColor(context, R.color.white));
                break;
            case SelectState.BETWEEN:
                //中间
                helper.setBackgroundRes(R.id.viewDay, R.drawable.bg_between);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.white));
                helper.setTextColor(R.id.textState, ContextCompat.getColor(context, R.color.white));
                break;
            case SelectState.END:
                //结束
                helper.setBackgroundRes(R.id.viewDay, R.drawable.bg_end);
                helper.setTextColor(R.id.textDay, ContextCompat.getColor(context, R.color.white));
                helper.setTextColor(R.id.textState, ContextCompat.getColor(context, R.color.white));
                break;
        }

    }
}
