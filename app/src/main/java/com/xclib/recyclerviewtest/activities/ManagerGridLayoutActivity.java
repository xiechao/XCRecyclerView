package com.xclib.recyclerviewtest.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.RecycleViewAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManagerGridLayoutActivity extends AppCompatActivity {

    @Bind(R.id.recycleView)
    XCRecycleView recycleView;


    private RecycleViewAdapter recycleViewAdapter;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_layout);

        ButterKnife.bind(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View headerView1 = inflater.inflate(R.layout.item_header_view_1, null, false);
        View headerView2 = inflater.inflate(R.layout.item_header_view_2, null, false);

        View footerView1 = inflater.inflate(R.layout.item_footer_view_1, null, false);
        View footerView2 = inflater.inflate(R.layout.item_footer_view_2, null, false);

        recycleView.addHeaderView(headerView1);
        recycleView.addHeaderView(headerView2);

        recycleView.addFooterView(footerView1);
        recycleView.addFooterView(footerView2);


        List<Person> users = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            Person person = new Person();
            person.setName("Name " + i);
            person.setEmail("alibaba" + i + "@gmail.com");
            users.add(person);
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recycleView.setLayoutManager(gridLayoutManager);

        recycleViewAdapter = new RecycleViewAdapter(this, users);

        recycleView.setAdapter(recycleViewAdapter);

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
//                recycleView.setLoadMoreEnd(false);

                Log.e("haint", "Load More 2");

                List<Person> personList = new ArrayList<>();
                for (int i = recycleViewAdapter.getCommonItemCount(); i < recycleViewAdapter.getCommonItemCount() + 30; i++) {
                    Person person = new Person();
                    person.setName("Name " + i);
                    person.setEmail("alibaba" + i + "@gmail.com");
                    personList.add(person);
                }

                recycleViewAdapter.addAll(personList);

                recycleView.setLoadMoreEnd(true);
            }
        }, 1500);
    }
}