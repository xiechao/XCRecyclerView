package com.xclib.recyclerviewtest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xclib.recyclerview.XCRecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.adapter.TestRecycleViewAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

public class TestFragment3 extends HeaderRecyclerFragment {
    private TestRecycleViewAdapter testRecycleViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        testRecycleViewAdapter = new TestRecycleViewAdapter(getContext());

        initData();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected XCRecyclerViewBaseAdapter getAdapter() {
        return testRecycleViewAdapter;
    }

    private void initData() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Person user = new Person("Fragment3 Name " + i);
            personList.add(user);
        }

        testRecycleViewAdapter.resetData(personList);
    }
}
