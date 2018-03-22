package com.muzi.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 *
 */

public class MLinearLayout extends LinearLayout {

    public MLinearLayout(Context context) {
        super(context);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,width);
    }
}
