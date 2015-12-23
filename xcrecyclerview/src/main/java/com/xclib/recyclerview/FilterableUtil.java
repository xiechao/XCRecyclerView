package com.xclib.recyclerview;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

public abstract class FilterableUtil {
    public static CharSequence translateFilterEffect(Context context, String str, int start, int length) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);

        //noinspection deprecation
        style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.primary)), start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return style;
    }
}
