package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.swipe.util.Attributes;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.RecyclerViewSwipeAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LinearLayoutManagerSwipeActivity extends AppCompatActivity {

    @Bind(R.id.recycleView)
    XCRecycleView recycleView;


    private RecyclerViewSwipeAdapter testRecycleViewAdapter;
    private final XCRecycleView.OnLoadMoreListener onLoadMoreListener = new XCRecycleView.OnLoadMoreListener() {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_common);

        ButterKnife.bind(this);

        recycleView.setLayoutManager(new LinearLayoutManager(this));

        LayoutInflater inflater = LayoutInflater.from(this);

        View headerView1 = inflater.inflate(R.layout.item_header_view_1, recycleView, false);
        View headerView2 = inflater.inflate(R.layout.item_header_view_2, recycleView, false);

        View footerView1 = inflater.inflate(R.layout.item_footer_view_1, recycleView, false);
        View footerView2 = inflater.inflate(R.layout.item_footer_view_2, recycleView, false);

        recycleView.addHeaderView(headerView1);
        recycleView.addHeaderView(headerView2);

        recycleView.addFooterView(footerView1);
        recycleView.addFooterView(footerView2);


        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Person user = new Person("Name " + i);
            personList.add(user);
        }

        testRecycleViewAdapter = new RecyclerViewSwipeAdapter(this, personList);
        testRecycleViewAdapter.setMode(Attributes.Mode.Single);

        recycleView.setAdapter(testRecycleViewAdapter);

        recycleView.setOnLoadMoreListener(onLoadMoreListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recycleView.clear();
    }

    private void doLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Person> personList = new ArrayList<>();
                for (int i = testRecycleViewAdapter.getCommonItemCount(); i < testRecycleViewAdapter.getCommonItemCount() + 30; i++) {
                    Person person = new Person("Name " + i);
                    personList.add(person);
                }

                testRecycleViewAdapter.addAll(personList);

                recycleView.setLoadMoreEnd(true);
            }
        }, 1500);
    }
}