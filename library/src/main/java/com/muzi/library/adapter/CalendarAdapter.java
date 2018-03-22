package com.muzi.library.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.library.R;
import com.muzi.library.bean.MonthBean;
import com.muzi.library.manager.MGridLayoutManager;

import java.util.List;

/**
 * Created by muzi on 2018/3/21.
 * 727784430@qq.com
 */

public class CalendarAdapter extends BaseQuickAdapter<MonthBean, BaseViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private MonthAdapter adapter;
    private OnItemChildClickListener listener;

    public void setListener(OnItemChildClickListener listener) {
        this.listener = listener;
    }

    public CalendarAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<MonthBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MonthBean item) {
        helper.setText(R.id.textMonth, item.getYear() + "年" + item.getMonth() + "月");
        recyclerView = helper.getView(R.id.rvMonth);
        recyclerView.setLayoutManager(new MGridLayoutManager(context, 7));

        adapter = new MonthAdapter(context, R.layout.item_day, item.getDayList());
        adapter.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return 1;
            }
        });
        recyclerView.setAdapter(adapter);

        helper.addOnClickListener(R.id.rvMonth);
        recyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (listener != null) {
                    listener.onItemChildClick(adapter, view, position);
                }
            }
        });
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter adapter, View view, int position);
    }
}
