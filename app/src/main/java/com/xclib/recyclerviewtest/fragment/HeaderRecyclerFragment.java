package com.xclib.recyclerviewtest.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.views.ListEmptyView;

public abstract class HeaderRecyclerFragment extends Fragment {

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (getActivity() instanceof HeaderViewProvider) {
                HeaderViewProvider headerViewProvider = (HeaderViewProvider) getActivity();

                headerViewProvider.onScrolled(recyclerView, dx, dy);
            }
        }
    };
    @SuppressWarnings("unused")
    protected XCRecycleView xcRecycleView;
    protected ListEmptyView listEmptyView;
    private LinearLayout headerView;
    private RecyclerViewBaseAdapter baseAdapter;
    private RecyclerViewBaseAdapter adapter;
    private final RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);

            adapter.notifyItemRangeChanged(positionStart + 1, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);

            adapter.notifyItemRangeChanged(positionStart + 1, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);

            adapter.notifyItemRangeInserted(positionStart + 1, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);

            adapter.notifyItemRangeRemoved(positionStart + 1, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);

            adapter.notifyItemRangeRemoved(fromPosition + 1, itemCount);
        }
    };

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        xcRecycleView = (XCRecycleView) rootView.findViewById(R.id.xc_recycler_view);

        listEmptyView = new ListEmptyView(getContext());

        headerView = new LinearLayout(getContext());

        if (getActivity() instanceof HeaderViewProvider) {
            HeaderViewProvider headerViewProvider = (HeaderViewProvider) getActivity();

            View view = new View(getContext());
            headerView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerViewProvider.getHeaderViewHeight()));
        }

        baseAdapter = getAdapter();
        adapter = new HeaderListAdapter(baseAdapter);

        registerAdapterDataObserver();

        xcRecycleView.setAdapter(adapter);

        xcRecycleView.setLayoutManager(getLayoutManager());

        xcRecycleView.addOnScrollListener(onScrollListener);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterAdapterDataObserver();

        xcRecycleView.removeOnScrollListener(onScrollListener);
    }

    protected abstract RecyclerViewBaseAdapter getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    private void registerAdapterDataObserver() {
        baseAdapter.registerAdapterDataObserver(adapterDataObserver);
    }


    private void unregisterAdapterDataObserver() {
        baseAdapter.unregisterAdapterDataObserver(adapterDataObserver);
    }

    public interface HeaderViewProvider {
        //        View getHeaderView();
        int getHeaderViewHeight();

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    public class HeaderListAdapter extends RecyclerViewBaseAdapter {
        private static final int VIEW_TYPE_OBSERVABLE_SCROLL_HEADER = 5000;
        private static final int VIEW_TYPE_EMPTY_VIEW = VIEW_TYPE_OBSERVABLE_SCROLL_HEADER + 1;
        final RecyclerViewBaseAdapter mListAdapter;


        private HeaderListAdapter(RecyclerViewBaseAdapter listAdapter) {
            super(getContext());
            mListAdapter = listAdapter;
        }

        @Override
        public int getItemCount() {
            return 1 + mListAdapter.getItemCount() + (mListAdapter.getItemCount() == 0 ? 1 : 0);
        }


        @Override
        public int getCommonItemViewType(int position, Object data) {
            if (isHeaderType(position)) {
                return VIEW_TYPE_OBSERVABLE_SCROLL_HEADER;
            } else if (isEmptyViewType(position)) {
                return VIEW_TYPE_EMPTY_VIEW;
            } else {
                return mListAdapter.getItemViewType(offsetPosition(position));
            }
        }

        @Override
        public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_OBSERVABLE_SCROLL_HEADER) {
                return new ObservableScrollHeaderViewHolder(headerView);
            } else if (viewType == VIEW_TYPE_EMPTY_VIEW) {
                return new EmptyViewHolder(listEmptyView);
            } else {
                return mListAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        protected int getCommonViewResourceId(int viewType) {
            return 0;
        }

        @Override
        protected ViewHolderBase onCommonCreateViewHolder(View view) {
            return null;
        }

        @Override
        public Object getItem(int position) {
            if (!isHeaderType(position) && !isEmptyViewType(position)) {
                return mListAdapter.getItem(offsetPosition(position));
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (!isHeaderType(position) && !isEmptyViewType(position)) {
                return mListAdapter.getItemId(offsetPosition(position));
            }
            return 0;
        }

        public boolean isHeaderType(int position) {
            return position == 0;
        }

        public boolean isEmptyViewType(int position) {
            return position == 1 && mListAdapter.getItemCount() == 0;
        }

        public int offsetPosition(int position) {
            return position - (mListAdapter.getItemCount() == 0 ? 1 : 0) - 1;
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
}
