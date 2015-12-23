package com.xclib.recyclerviewtest.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.fragment.HeaderRecyclerViewBaseFragment;
import com.xclib.recyclerviewtest.fragment.ObservableFragment1;
import com.xclib.recyclerviewtest.fragment.ObservableFragment2;
import com.xclib.recyclerviewtest.fragment.ObservableFragment3;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ObservableRecyclerViewActivity extends BaseActivity implements HeaderRecyclerViewBaseFragment.HeaderViewProvider {

    private static final int TAB_COUNT = 3;
    private static final String[] tabTitles = new String[]{"Fragment1", "Fragment2", "Fragment3"};
    @Bind(R.id.layout_fragment_content)
    FrameLayout layoutFragmentContent;
    @Bind(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.ll_header_layout)
    LinearLayout llHeaderLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_observable_recycler_view);

        ButterKnife.bind(this);

        tvHeaderTitle.setText("Header Text!");

        setUpTab();

        TabLayout.Tab tab = slidingTabs.getTabAt(0);
        if (tab != null) {
            tab.select();
        }
    }

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
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) llHeaderLayout.getLayoutParams();

        layoutParams.setMargins(0, 0, 0, 0);
        llHeaderLayout.setLayoutParams(layoutParams);

        Fragment fragment;
        if (position == 0) {
            fragment = new ObservableFragment1();
        } else if (position == 1) {
            fragment = new ObservableFragment2();
        } else {
            fragment = new ObservableFragment3();
        }

        Bundle args = new Bundle();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment_content, fragment, fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public int getHeaderViewHeight() {
        return (int) (this.getResources().getDisplayMetrics().density * 200 + 0.5f);
    }

    @Override
    public void onHeadScrollHScrolled(int scrollY) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) llHeaderLayout.getLayoutParams();

        layoutParams.setMargins(0, 0 - scrollY, 0, 0 - scrollY);
        llHeaderLayout.setLayoutParams(layoutParams);
    }
}
