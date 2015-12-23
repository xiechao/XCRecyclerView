package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xclib.recyclerview.EmptyView;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.PTRUtil;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.adapter.RecyclerViewGridAdapter;
import com.xclib.recyclerviewtest.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class GridLayoutManagerActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;
    @Bind(R.id.recycler_view)
    XCRecycleView recyclerView;
    @Bind(R.id.recycler_view_empty_view)
    EmptyView recyclerViewEmptyView;


    private RecyclerViewGridAdapter recyclerViewGridAdapter;
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

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Grid Layout");
        }

        recyclerViewEmptyView.setText("empty data!");

        recyclerView.setEmptyView(recyclerViewEmptyView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        LayoutInflater inflater = LayoutInflater.from(this);

        View headerView1 = inflater.inflate(R.layout.item_header_view_1, recyclerView, false);
        View headerView2 = inflater.inflate(R.layout.item_header_view_2, recyclerView, false);

        View footerView1 = inflater.inflate(R.layout.item_footer_view_1, recyclerView, false);
        View footerView2 = inflater.inflate(R.layout.item_footer_view_2, recyclerView, false);

        recyclerView.addHeaderView(headerView1);
        recyclerView.addHeaderView(headerView2);

        recyclerView.addFooterView(footerView1);
        recyclerView.addFooterView(footerView2);


        recyclerViewGridAdapter = new RecyclerViewGridAdapter(this);


        recyclerView.setAdapter(recyclerViewGridAdapter);

        recyclerView.setOnLoadMoreListener(onLoadMoreListener);

        PTRUtil.init(this, ptrFrameLayout);

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        ptrFrameLayout.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }
        });

        refreshData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recyclerView.clear();
    }

    private void refreshData() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person("Name " + i);
            personList.add(person);
        }

        recyclerViewGridAdapter.setItems(personList);

        recyclerViewGridAdapter.notifyDataSetChanged();
    }

    private void doLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Person> personList = new ArrayList<>();
                for (int i = recyclerViewGridAdapter.getAllCommonItemCount(); i < recyclerViewGridAdapter.getAllCommonItemCount() + 20; i++) {
                    Person person = new Person("Name " + i);
                    personList.add(person);
                }

                recyclerViewGridAdapter.addMoreItems(personList, true);

                recyclerView.setLoadMoreEnd(true);
            }
        }, 1500);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);


        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewGridAdapter.getFilter().filter(newText);

                return true;
            }
        });
        searchView.setSubmitButtonEnabled(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}