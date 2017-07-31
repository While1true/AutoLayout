package com.example.autolayoutlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

/**
 * Created by ck on 2017/7/31.
 */

public class Constance {
    //设计稿尺寸
    public static final int DESIGN_WIDTH = 750;

    public static void resetDensity(Activity context) {
        Point size = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(size);
        context.getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }
}
