package com.muzi.library;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by muzi on 2018/3/22.
 * 727784430@qq.com
 */

public class SelectState {

    public static final int UNABLE = -1;//不可点击

    //可以被选中
    public static final int NONE = 0;//没有选中
    public static final int START = 1;//开始
    public static final int BETWEEN = 2;//中间值
    public static final int END = 3;//结束
    public static final int SINGLE = 4;//开始和结束合并
    public static final int PREVIEW_START = 5;//准备开始

    @IntDef({UNABLE, NONE, START, BETWEEN, END, SINGLE, PREVIEW_START})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

}
