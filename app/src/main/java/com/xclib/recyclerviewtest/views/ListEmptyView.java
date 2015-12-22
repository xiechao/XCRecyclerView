package com.xclib.recyclerviewtest.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xclib.recyclerviewtest.R;

public class ListEmptyView extends LinearLayout {
    private TextView emptyTextView;

    public ListEmptyView(Context context) {
        super(context);

        initUI(context);
    }

    public ListEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI(context);
    }

    public ListEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context);
    }

    private void initUI(Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.view_list_empty, null);

        this.addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        emptyTextView = (TextView) findViewById(R.id.tv_empty);

        this.setBackgroundColor(Color.argb(255,255,255,255));
    }

    public void setText(int resId) {
        emptyTextView.setText(resId);
    }

    public void setText(@SuppressWarnings("SameParameterValue") String text) {
        emptyTextView.setText(text);
    }

    public void setIsShow(boolean isShow) {
        emptyTextView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
