package com.xclib.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;

public class ColorGroupSectionTitleIndicator extends SectionTitleIndicator<ISectionData> {

    public ColorGroupSectionTitleIndicator(Context context) {
        super(context);
    }

    public ColorGroupSectionTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorGroupSectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(ISectionData colorGroup) {
        // Example of using a single character
        setTitleText(colorGroup.getSectionHeader());

        // Example of using a longer string
        // setTitleText(colorGroup.getName());

//        setIndicatorTextColor(colorGroup.getAsColor());
    }

}