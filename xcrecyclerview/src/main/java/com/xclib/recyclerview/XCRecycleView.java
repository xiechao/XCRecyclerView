package com.xclib.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class XCRecycleView extends RecyclerView {
    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();

//            int visibleThreshold = 5;

            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

//            linearLayoutManager.findLastVisibleItemPosition()

            if (totalItemCount <= (lastVisibleItem + 3)) {
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

    private void tryDoLoadMore() {
        if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
            XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

            XCRecyclerViewBaseAdapter.tryDoLoadMore();
        }
    }

    public void setLoadMoreEnd(@SuppressWarnings("SameParameterValue") final boolean isImmediate) {
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

    private final ArrayList<View> headerViewArrayList = new ArrayList<>();
    private final ArrayList<View> footerViewArrayList = new ArrayList<>();

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

        @SuppressWarnings("SameReturnValue")
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

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (layout != null && layout instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) getLayoutManager();

                    if (getAdapter() instanceof XCRecyclerViewBaseAdapter) {
                        XCRecyclerViewBaseAdapter XCRecyclerViewBaseAdapter = (XCRecyclerViewBaseAdapter) getAdapter();

                        if (
                                XCRecyclerViewBaseAdapter.isHeaderView(position) ||
                                        XCRecyclerViewBaseAdapter.isFooterView(position) ||
                                        XCRecyclerViewBaseAdapter.isLoadMoreView(position)) {
                            return gridLayoutManager.getSpanCount();
                        } else {
                            return 1;
                        }
                    }

                    return gridLayoutManager.getSpanCount();
                }
            });
        }
    }
}
