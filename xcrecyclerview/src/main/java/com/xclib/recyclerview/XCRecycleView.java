package com.xclib.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class XCRecycleView extends RecyclerView {
    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            long currentTime = System.currentTimeMillis();

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();

            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

            LayoutManager layoutManager = getLayoutManager();
            int columnSpanCount = 1;
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                columnSpanCount = gridLayoutManager.getSpanCount();
            }

            if (totalItemCount <= (lastVisibleItem + 3 * columnSpanCount)) {
                tryDoLoadMore();
            }

            long timeInterval = System.currentTimeMillis() - currentTime;
            Log.d("qqqqqqqqq", "OnScrollListener timeInterval = " + timeInterval + "; dx = " + dx + "; dy = " + dy);
        }


    };
    private final ArrayList<View> headerViewArrayList = new ArrayList<>();
    private final ArrayList<View> footerViewArrayList = new ArrayList<>();
    private EmptyView emptyVIew;

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

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            recyclerViewBaseAdapter.setOnLoadMoreListener(onLoadMoreListener);
        }
    }

    public void clear() {
        removeOnScrollListener(onScrollListener);

        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(adapterDataObserver);
        }
    }

    private void tryDoLoadMore() {
        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            recyclerViewBaseAdapter.tryDoLoadMore();
        }
    }

    public void setLoadMoreEnd(@SuppressWarnings("SameParameterValue") final boolean isImmediate) {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (getAdapter() instanceof RecyclerViewBaseAdapter) {
                    RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

                    recyclerViewBaseAdapter.setLoadMoreEnd(isImmediate);
                }
            }
        });
    }

    public void addHeaderView(View v) {
        headerViewArrayList.add(v);

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            recyclerViewBaseAdapter.addHeaderView(v);
        }
    }

    public boolean removeHeaderView(View v) {
        headerViewArrayList.remove(v);

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            return recyclerViewBaseAdapter.removeHeaderView(v);
        }

        return false;
    }

    public int getHeaderViewsCount() {
        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            return recyclerViewBaseAdapter.getHeaderViewsCount();
        }

        return headerViewArrayList.size();
    }

    public void addFooterView(View v) {
        footerViewArrayList.add(v);

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            recyclerViewBaseAdapter.addFooterView(v);
        }
    }

    public boolean removeFooterView(View v) {
        footerViewArrayList.remove(v);

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            return recyclerViewBaseAdapter.removeFooterView(v);
        }

        return false;
    }

    public int getFooterViewsCount() {
        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            return recyclerViewBaseAdapter.getFooterViewsCount();
        }

        return footerViewArrayList.size();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (getAdapter() instanceof RecyclerViewBaseAdapter) {
            RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

            recyclerViewBaseAdapter.resetHeaderViewsCount(headerViewArrayList);
            recyclerViewBaseAdapter.resetFooterViewsCount(footerViewArrayList);
        }

        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            doRefreshEmptyView();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);

            doRefreshEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);

            doRefreshEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);

            doRefreshEmptyView();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);

            doRefreshEmptyView();
        }
    };

    private void doRefreshEmptyView() {
        if (emptyVIew == null) {
            return;
        }

        RecyclerView.Adapter adapter = getAdapter();

        if (adapter == null || adapter.getItemCount() == 0) {
            emptyVIew.setIsShow(true);
        } else {
            emptyVIew.setIsShow(false);
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

                    if (getAdapter() instanceof RecyclerViewBaseAdapter) {
                        RecyclerViewBaseAdapter recyclerViewBaseAdapter = (RecyclerViewBaseAdapter) getAdapter();

                        if (!recyclerViewBaseAdapter.isSupportSeparateSpan(position)) {
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

    public void setEmptyView(EmptyView emptyVIew) {
        this.emptyVIew = emptyVIew;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();

        @SuppressWarnings("SameReturnValue")
        boolean isHasMoreData();
    }
}
