package com.xclib.recyclerviewtest;

import android.content.Context;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

public abstract class PTRUtil {
    public static void init(Context context, PtrFrameLayout ptrFrameLayout) {
        final MaterialHeader header = new MaterialHeader(context);
        int[] colors = context.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, dip2px(context, 15), 0, dip2px(context, 10));
        header.setPtrFrameLayout(ptrFrameLayout);

        // the following are default settings
        ptrFrameLayout.setResistance(1.7f);
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrameLayout.setDurationToClose(200);
        ptrFrameLayout.setDurationToCloseHeader(1000);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        // default is false
        ptrFrameLayout.setPullToRefresh(false);
        // default is true
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

