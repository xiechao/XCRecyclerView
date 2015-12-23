package com.xclib.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class HeaderRecyclerViewAdapter extends RecyclerViewBaseAdapter {
    private static final int VIEW_TYPE_OBSERVABLE_SCROLL_HEADER = 5000;
    private static final int VIEW_TYPE_EMPTY_VIEW = VIEW_TYPE_OBSERVABLE_SCROLL_HEADER + 1;

    private RecyclerViewBaseAdapter baseAdapter;
    private View headerView;
    private View emptyView;

    public HeaderRecyclerViewAdapter(Context context, RecyclerViewBaseAdapter baseAdapter, View headerView, View emptyView) {
        super(context);

        this.baseAdapter = baseAdapter;

        this.headerView = headerView;
        this.emptyView = emptyView;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1 + (baseAdapter.getItemCount() == 0 ? 1 : baseAdapter.getItemCount());
    }

    @Override
    public int getCommonItemViewType(int position, Object data) {
        if (isHeaderType(position)) {
            return VIEW_TYPE_OBSERVABLE_SCROLL_HEADER;
        } else if (isEmptyViewType(position)) {
            return VIEW_TYPE_EMPTY_VIEW;
        } else {
            return baseAdapter.getItemViewType(offsetPosition(position));
        }
    }

    boolean isSupportSeparateSpan(int position) {
        return !(isHeaderType(position) || isEmptyViewType(position)) && baseAdapter.isSupportSeparateSpan(offsetPosition(position)) && super.isSupportSeparateSpan(position - 1);
    }

    @Override
    protected int getCommonViewResourceId(int viewType) {
        return 0;
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType) {
        if (viewType == VIEW_TYPE_OBSERVABLE_SCROLL_HEADER) {
            return new ObservableScrollHeaderViewHolder(headerView);
        } else if (viewType == VIEW_TYPE_EMPTY_VIEW) {
            return new EmptyViewHolder(emptyView);
        } else {
            return baseAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public Object getItem(int position) {
        if (!isHeaderType(position) && !isEmptyViewType(position)) {
            return baseAdapter.getItem(offsetPosition(position));
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (!isHeaderType(position) && !isEmptyViewType(position)) {
            return baseAdapter.getItemId(offsetPosition(position));
        }
        return 0;
    }

    public boolean isHeaderType(int position) {
        return position == 0;
    }

    public boolean isEmptyViewType(int position) {
        return position == (1 + getHeaderViewsCount()) && baseAdapter.getItemCount() == 0;
    }

    public int offsetPosition(int position) {
        return position - (baseAdapter.getItemCount() == 0 ? 1 : 0) - getHeaderViewsCount() - 1;
    }

    private class ObservableScrollHeaderViewHolder extends ViewHolderBase {

        public ObservableScrollHeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

    private class EmptyViewHolder extends ViewHolderBase {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }
}
