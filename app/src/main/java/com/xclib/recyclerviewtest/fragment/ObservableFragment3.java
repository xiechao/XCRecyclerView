package com.xclib.recyclerviewtest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.adapter.RecyclerViewGridAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ObservableFragment3 extends HeaderRecyclerViewBaseFragment {
    private RecyclerViewGridAdapter recyclerViewGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerViewGridAdapter = new RecyclerViewGridAdapter(getContext());

        initData();


        View view = super.onCreateView(inflater, container, savedInstanceState);

        emptyView.setText("empty test!");
        xcRecycleView.setOnLoadMoreListener(onLoadMoreListener);

        return view;
    }


    @Override
    protected RecyclerViewBaseAdapter getAdapter() {
        return recyclerViewGridAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3);
    }

    private void initData() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person user = new Person("Fragment3 Name " + i);
            personList.add(user);
        }

        recyclerViewGridAdapter.resetData(personList);
    }

    private XCRecycleView.OnLoadMoreListener onLoadMoreListener = new XCRecycleView.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            doLoadMore();
        }

        @Override
        public boolean isHasMoreData() {
            return true;
        }
    };

    private void doLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Person> personList = new ArrayList<>();
                for (int i = recyclerViewGridAdapter.getCommonItemCount(); i < recyclerViewGridAdapter.getCommonItemCount() + 20; i++) {
                    Person person = new Person("Name " + i);
                    personList.add(person);
                }

                recyclerViewGridAdapter.addAll(personList);

                xcRecycleView.setLoadMoreEnd(true);
            }
        }, 1500);
    }
}
