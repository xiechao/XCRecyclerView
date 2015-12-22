//package com.xclib.recyclerviewtest.activities;
//
//import android.annotation.TargetApi;
//import android.content.res.Configuration;
//import android.content.res.TypedArray;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.ArrayAdapter;
//import android.widget.GridView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.xclib.recyclerviewtest.R;
//
//import java.util.ArrayList;
//
///**
// * Created by xiechao on 22/12/15.
// */
//public class FlexibleSpaceWithImageWithViewPagerTabActivity  extends AppCompatActivity {
//
//    protected static final float MAX_TEXT_SCALE_DELTA = 0.3f;
//
//    private ViewPager mPager;
//    private NavigationAdapter mPagerAdapter;
//    private SlidingTabLayout mSlidingTabLayout;
//    private int mFlexibleSpaceHeight;
//    private int mTabHeight;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_flexiblespacewithimagewithviewpagertab);
//
//        mPagerAdapter = new NavigationAdapter(getSupportFragmentManager());
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(mPagerAdapter);
//        mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
//        mTabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
//
//        TextView titleView = (TextView) findViewById(R.id.title);
//        titleView.setText("Flexible Space");
//
//        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
//        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
//        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
//        mSlidingTabLayout.setDistributeEvenly(true);
//        mSlidingTabLayout.setViewPager(mPager);
//
//        // Initialize the first Fragment's state when layout is completed.
//        ScrollUtils.addOnGlobalLayoutListener(mSlidingTabLayout, new Runnable() {
//            @Override
//            public void run() {
//                translateTab(0, false);
//            }
//        });
//    }
//
//    /**
//     * Called by children Fragments when their scrollY are changed.
//     * They all call this method even when they are inactive
//     * but this Activity should listen only the active child,
//     * so each Fragments will pass themselves for Activity to check if they are active.
//     *
//     * @param scrollY scroll position of Scrollable
//     * @param s       caller Scrollable view
//     */
//    public void onScrollChanged(int scrollY, Scrollable s) {
//        FlexibleSpaceWithImageBaseFragment fragment =
//                (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(mPager.getCurrentItem());
//        if (fragment == null) {
//            return;
//        }
//        View view = fragment.getView();
//        if (view == null) {
//            return;
//        }
//        Scrollable scrollable = (Scrollable) view.findViewById(R.id.scroll);
//        if (scrollable == null) {
//            return;
//        }
//        if (scrollable == s) {
//            // This method is called by not only the current fragment but also other fragments
//            // when their scrollY is changed.
//            // So we need to check the caller(S) is the current fragment.
//            int adjustedScrollY = Math.min(scrollY, mFlexibleSpaceHeight - mTabHeight);
//            translateTab(adjustedScrollY, false);
//            propagateScroll(adjustedScrollY);
//        }
//    }
//
//    private void translateTab(int scrollY, boolean animated) {
//        int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
//        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
//        View imageView = findViewById(R.id.image);
//        View overlayView = findViewById(R.id.overlay);
//        TextView titleView = (TextView) findViewById(R.id.title);
//
//        // Translate overlay and image
//        float flexibleRange = flexibleSpaceImageHeight - getActionBarSize();
//        int minOverlayTransitionY = tabHeight - overlayView.getHeight();
//        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
//        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));
//
//        // Change alpha of overlay
//        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
//
//        // Scale title text
//        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY - tabHeight) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
//        setPivotXToTitle(titleView);
//        ViewHelper.setPivotY(titleView, 0);
//        ViewHelper.setScaleX(titleView, scale);
//        ViewHelper.setScaleY(titleView, scale);
//
//        // Translate title text
//        int maxTitleTranslationY = flexibleSpaceImageHeight - tabHeight - getActionBarSize();
//        int titleTranslationY = maxTitleTranslationY - scrollY;
//        ViewHelper.setTranslationY(titleView, titleTranslationY);
//
//        // If tabs are moving, cancel it to start a new animation.
//        ViewPropertyAnimator.animate(mSlidingTabLayout).cancel();
//        // Tabs will move between the top of the screen to the bottom of the image.
//        float translationY = ScrollUtils.getFloat(-scrollY + mFlexibleSpaceHeight - mTabHeight, 0, mFlexibleSpaceHeight - mTabHeight);
//        if (animated) {
//            // Animation will be invoked only when the current tab is changed.
//            ViewPropertyAnimator.animate(mSlidingTabLayout)
//                    .translationY(translationY)
//                    .setDuration(200)
//                    .start();
//        } else {
//            // When Fragments' scroll, translate tabs immediately (without animation).
//            ViewHelper.setTranslationY(mSlidingTabLayout, translationY);
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private void setPivotXToTitle(View view) {
//        final TextView mTitleView = (TextView) view.findViewById(R.id.title);
//        Configuration config = getResources().getConfiguration();
//        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
//                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//            ViewHelper.setPivotX(mTitleView, view.findViewById(android.R.id.content).getWidth());
//        } else {
//            ViewHelper.setPivotX(mTitleView, 0);
//        }
//    }
//
//    private void propagateScroll(int scrollY) {
//        // Set scrollY for the fragments that are not created yet
//        mPagerAdapter.setScrollY(scrollY);
//
//        // Set scrollY for the active fragments
//        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
//            // Skip current item
//            if (i == mPager.getCurrentItem()) {
//                continue;
//            }
//
//            // Skip destroyed or not created item
//            FlexibleSpaceWithImageBaseFragment f =
//                    (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(i);
//            if (f == null) {
//                continue;
//            }
//
//            View view = f.getView();
//            if (view == null) {
//                continue;
//            }
//            f.setScrollY(scrollY, mFlexibleSpaceHeight);
//            f.updateFlexibleSpace(scrollY);
//        }
//    }
//
//    /**
//     * This adapter provides three types of fragments as an example.
//     * {@linkplain #createItem(int)} should be modified if you use this example for your app.
//     */
//    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {
//
//        private static final String[] TITLES = new String[]{"Applepie", "Butter Cookie", "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop"};
//
//        private int mScrollY;
//
//        public NavigationAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void setScrollY(int scrollY) {
//            mScrollY = scrollY;
//        }
//
//        @Override
//        protected Fragment createItem(int position) {
//            FlexibleSpaceWithImageBaseFragment f;
//            final int pattern = position % 4;
//            switch (pattern) {
//                case 0: {
//                    f = new FlexibleSpaceWithImageScrollViewFragment();
//                    break;
//                }
//                case 1: {
//                    f = new FlexibleSpaceWithImageListViewFragment();
//                    break;
//                }
//                case 2: {
//                    f = new FlexibleSpaceWithImageRecyclerViewFragment();
//                    break;
//                }
//                case 3:
//                default: {
//                    f = new FlexibleSpaceWithImageGridViewFragment();
//                    break;
//                }
//            }
//            f.setArguments(mScrollY);
//            return f;
//        }
//
//        @Override
//        public int getCount() {
//            return TITLES.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLES[position];
//        }
//    }
//}
