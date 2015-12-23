package com.xclib.recyclerviewtest.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.adapter.RecyclerViewAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ObservableFragment3 extends HeaderRecyclerFragment {
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerViewAdapter = new RecyclerViewAdapter(getContext());

        initData();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected RecyclerViewBaseAdapter getAdapter() {
        return recyclerViewAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3);
    }

    private void initData() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Person user = new Person("Fragment3 Name " + i);
            personList.add(user);
        }

        recyclerViewAdapter.resetData(personList);
    }
}
