package com.xclib.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class XCRecycleView extends RecyclerView {
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();

//            int visibleThreshold = 5;

            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

//            linearLayoutManager.findLastVisibleItemPosition()

            if (totalItemCount <= (lastVisibleItem + 2)) {
                tryDoLoadMore();
            }
        }


    };

    public XCRecycleView(Context context) {
        super(context);

        init();
    }

    public XCRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public XCRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        addOnScrollListener(onScrollListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if (onLoadMoreListener == null) {
            return;
        }

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            XCRecyclerViewBaseAdapter.setOnLoadMoreListener(onLoadMoreListener);
        }
    }


    public void clear() {
        removeOnScrollListener(onScrollListener);
    }

    private boolean tryDoLoadMore() {
        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            if (XCRecyclerViewBaseAdapter.tryDoLoadMore()) {
//                this.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                        scrollToPosition(getAdapter().getItemCount() - 1);
//                }, 100);

                return true;
            }
        }

        return false;
    }

    public void setLoadMoreEnd(final boolean isImmediate) {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
                    XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

                    XCRecyclerViewBaseAdapter.setLoadMoreEnd(isImmediate);
                }
            }
        });
    }

    ArrayList<View> headerViewArrayList = new ArrayList<>();
    ArrayList<View> footerViewArrayList = new ArrayList<>();

    public void addHeaderView(View v) {
        headerViewArrayList.add(v);

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            XCRecyclerViewBaseAdapter.addHeaderView(v);
        }
    }

    public boolean removeHeaderView(View v) {
        headerViewArrayList.remove(v);

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            return XCRecyclerViewBaseAdapter.removeHeaderView(v);
        }

        return false;
    }

    public int getHeaderViewsCount() {
        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            return XCRecyclerViewBaseAdapter.getHeaderViewsCount();
        }

        return headerViewArrayList.size();
    }

    public void addFooterView(View v) {
        footerViewArrayList.add(v);

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            XCRecyclerViewBaseAdapter.addFooterView(v);
        }
    }

    public boolean removeFooterView(View v) {
        footerViewArrayList.remove(v);

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            return XCRecyclerViewBaseAdapter.removeFooterView(v);
        }

        return false;
    }

    public int getFooterViewsCount() {
        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            return XCRecyclerViewBaseAdapter.getFooterViewsCount();
        }

        return footerViewArrayList.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();

        boolean isHasMoreData();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            XCRecyclerViewBaseAdapter.resetHeaderViewsCount(headerViewArrayList);
            XCRecyclerViewBaseAdapter.resetFooterViewsCount(footerViewArrayList);
        }
    }


    //    if (type == TYPE_GRID_LAYOUT) {
//        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        mRecyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
//    } else if (type == TYPE_STAGGERED_GRID_LAYOUT) {
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
//    } else {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于list view
//    }
//    mAdapter = new HeaderBottomItemAdapter(getActivity());
//    mRecyclerView.setAdapter(mAdapter);
//    if (gridLayoutManager != null) {//设置头部及底部View占据整行空间
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
//            }
//        });
//    }
}
