package com.xclib.recyclerviewtest.model;

import android.text.TextUtils;

public class Person implements ISectionData {
        private String name;
        private String email;

        public Person()
        {

        }

        public Person(String name, String email) {
            this.name = name;
            this.email = email;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getSortKey() {
        return getName();

    }

    @Override
    public String getSectionHeader() {
        String sectionHeader = "#";

        String nameValue = name;
        if (!TextUtils.isEmpty(nameValue)) {
            nameValue = nameValue.trim();

            if (!TextUtils.isEmpty(nameValue)) {
                sectionHeader = String.valueOf(nameValue.charAt(0));
            }
        }

        return sectionHeader;
    }
}
