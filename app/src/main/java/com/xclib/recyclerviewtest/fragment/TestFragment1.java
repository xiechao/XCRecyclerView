package com.xclib.recyclerviewtest.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.adapter.TestRecycleViewAdapter;

public class TestFragment1 extends HeaderRecyclerFragment {
    private TestRecycleViewAdapter testRecycleViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        testRecycleViewAdapter = new TestRecycleViewAdapter(getContext());

        initData();

        View view = super.onCreateView(inflater, container, savedInstanceState);

        listEmptyView.setText("empty test!");

        return view;
    }


    @Override
    protected RecyclerViewBaseAdapter getAdapter() {
        return testRecycleViewAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @SuppressWarnings("EmptyMethod")
    private void initData() {
//        List<Person> personList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            Person user = new Person("Fragment1 Name " + i);
//            personList.add(user);
//        }

//        testRecycleViewAdapter.resetData(personList);
    }
}
