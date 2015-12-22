package com.xclib.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;

public class GroupSectionTitleIndicator extends SectionTitleIndicator<ISectionData> {

    public GroupSectionTitleIndicator(Context context) {
        super(context);
    }

    public GroupSectionTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupSectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
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