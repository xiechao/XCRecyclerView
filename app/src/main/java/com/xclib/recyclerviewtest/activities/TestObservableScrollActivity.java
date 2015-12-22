package com.xclib.recyclerviewtest.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.fragment.HeaderRecyclerFragment;
import com.xclib.recyclerviewtest.fragment.TestFragment1;
import com.xclib.recyclerviewtest.fragment.TestFragment2;
import com.xclib.recyclerviewtest.fragment.TestFragment3;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestObservableScrollActivity extends AppCompatActivity implements HeaderRecyclerFragment.HeaderViewProvider {
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.ll_detail_container)
    LinearLayout llDetailContainer;

    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headerView = LayoutInflater.from(this).inflate(R.layout.include_circle_detail_header, null);

        ButterKnife.bind(this, headerView);

        tvHeaderTitle.setText("Header Text!");

        setUpTab();

        TabLayout.Tab tab = slidingTabs.getTabAt(0);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    public View getHeaderView() {
        return headerView;
    }

    private static final int TAB_COUNT = 3;

    private static final String[] tabTitles = new String[]{"Fragment1", "Fragment2", "Fragment3"};

    private void setUpTab() {
        for (int i = 0; i < TAB_COUNT; i++) {
            slidingTabs.addTab(slidingTabs.newTab().setText(tabTitles[i]), false);
        }
        slidingTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int position) {
        List<Fragment> fragmentArrayList = getSupportFragmentManager().getFragments();

        if (fragmentArrayList != null) {
            for (Fragment fragment : fragmentArrayList) {
                if (fragment instanceof HeaderRecyclerFragment) {
                    HeaderRecyclerFragment headerRecyclerFragment = (HeaderRecyclerFragment) fragment;

                    headerRecyclerFragment.setHeaderAvailable(false);
                }
            }
        }

        Fragment fragment;
        if (position == 0) {
            fragment = new TestFragment1();
        } else if (position == 1) {
            fragment = new TestFragment2();
        } else {
            fragment = new TestFragment3();
        }

        Bundle args = new Bundle();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

}
