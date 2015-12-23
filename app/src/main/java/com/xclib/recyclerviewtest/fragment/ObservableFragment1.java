package com.xclib.recyclerviewtest.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.DividerItemDecoration;
import com.xclib.recyclerviewtest.adapter.RecyclerViewAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ObservableFragment1 extends HeaderRecyclerViewBaseFragment {
    private RecyclerViewAdapter recyclerViewAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerViewAdapter = new RecyclerViewAdapter(getContext());

        initData();

        View view = super.onCreateView(inflater, container, savedInstanceState);

        emptyView.setText("empty test!");

        xcRecycleView.setOnLoadMoreListener(onLoadMoreListener);

        xcRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    @Override
    protected RecyclerViewBaseAdapter getAdapter() {
        return recyclerViewAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @SuppressWarnings("EmptyMethod")
    private void initData() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person user = new Person("Fragment1 Name " + i);
            personList.add(user);
        }

        recyclerViewAdapter.resetData(personList);
    }

    private void doLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Person> personList = new ArrayList<>();
                for (int i = recyclerViewAdapter.getCommonItemCount(); i < recyclerViewAdapter.getCommonItemCount() + 20; i++) {
                    Person person = new Person("Name " + i);
                    personList.add(person);
                }

                recyclerViewAdapter.addAll(personList);

                xcRecycleView.setLoadMoreEnd(true);
            }
        }, 1500);
    }
}
