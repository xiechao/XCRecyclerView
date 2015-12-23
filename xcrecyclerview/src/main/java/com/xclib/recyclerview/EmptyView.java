package com.xclib.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmptyView extends LinearLayout {
    private TextView emptyTextView;

    public EmptyView(Context context) {
        super(context);

        initUI(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context);
    }

    private void initUI(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_empty, this, false);

        this.addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        emptyTextView = (TextView) findViewById(R.id.tv_empty);
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
