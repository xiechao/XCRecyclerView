package com.xclib.recyclerviewtest.model;

import android.text.TextUtils;

import com.xclib.recyclerview.ISectionData;

public class Person implements ISectionData {
    private final String name;
    private String sectionHeader;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getSortKey() {
        return getName();

    }


    @Override
    public String getSectionHeader() {
        if (TextUtils.isEmpty(sectionHeader)) {
            sectionHeader = "#";

            String nameValue = name;
            if (!TextUtils.isEmpty(nameValue)) {
                nameValue = nameValue.trim();

                if (!TextUtils.isEmpty(nameValue)) {
                    sectionHeader = String.valueOf(nameValue.charAt(0));
                }
            }

        }

        return sectionHeader;
    }

    @Override
    public long getSectionHeaderId() {
        return getSectionHeader().hashCode();
    }
}
