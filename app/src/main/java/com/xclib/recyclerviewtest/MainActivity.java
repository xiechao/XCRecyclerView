package com.xclib.recyclerviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xclib.recyclerviewtest.activities.GridLayoutManagerActivity;
import com.xclib.recyclerviewtest.activities.LinearLayoutManagerActivity;
import com.xclib.recyclerviewtest.activities.LinearLayoutManagerSwipeActivity;
import com.xclib.recyclerviewtest.activities.ObservableRecyclerViewActivity;
import com.xclib.recyclerviewtest.activities.PinnedSectionActivity;
import com.xclib.recyclerviewtest.activities.PinnedSectionSwipeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.btn_linear_layout_manager)
    Button btnLinearLayoutManager;
    @Bind(R.id.btn_relative_layout_manager)
    Button btnRelativeLayoutManager;
    @Bind(R.id.btn_pinned_section)
    Button btnPinnedSection;
    @Bind(R.id.btn_linear_layout_swipe_manager)
    Button btnLinearLayoutSwipeManager;
    @Bind(R.id.btn_pinned_section_swipe)
    Button btnPinnedSectionSwipe;
    @Bind(R.id.btn_observable_recycler_view)
    Button btnObservableRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_linear_layout_manager})
    public void onClickLinearLayout(View view) {
        Intent intent = new Intent(this, LinearLayoutManagerActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_relative_layout_manager})
    public void onClickRelativeLayout(View view) {
        Intent intent = new Intent(this, GridLayoutManagerActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_pinned_section})
    public void onClickPinnedSection(View view) {
        Intent intent = new Intent(this, PinnedSectionActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_linear_layout_swipe_manager})
    public void onClickLinearLayoutSwipe(View view) {
        Intent intent = new Intent(this, LinearLayoutManagerSwipeActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_pinned_section_swipe})
    public void onClickPinnedSectionSwipe(View view) {
        Intent intent = new Intent(this, PinnedSectionSwipeActivity.class);

        startActivity(intent);
    }

    @OnClick({R.id.btn_observable_recycler_view})
    public void onClickObservableRecyclerView(View view) {
        Intent intent = new Intent(this, ObservableRecyclerViewActivity.class);

        startActivity(intent);
    }

}