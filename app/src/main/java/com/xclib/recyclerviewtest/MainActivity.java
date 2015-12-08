package com.xclib.recyclerviewtest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.xclib.recyclerview.XCRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycleView)
    XCRecycleView recycleView;


    private TestRecycleViewAdapter testRecycleViewAdapter;
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
        setContentView(R.layout.activity_main);

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


        List<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setName("Name " + i);
            user.setEmail("alibaba" + i + "@gmail.com");
            users.add(user);
        }


        recycleView.setLayoutManager(new LinearLayoutManager(this));

        testRecycleViewAdapter = new TestRecycleViewAdapter(this, users);

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
//                recycleView.setLoadMoreEnd(false);

                Log.e("haint", "Load More 2");

                List<User> users = new ArrayList<>();
                for (int i = testRecycleViewAdapter.getCommonItemCount(); i < testRecycleViewAdapter.getCommonItemCount() + 30; i++) {
                    User user = new User();
                    user.setName("Name " + i);
                    user.setEmail("alibaba" + i + "@gmail.com");
                    users.add(user);
                }

                testRecycleViewAdapter.addAll(users);

                recycleView.setLoadMoreEnd(true);
            }
        }, 1500);
    }
}