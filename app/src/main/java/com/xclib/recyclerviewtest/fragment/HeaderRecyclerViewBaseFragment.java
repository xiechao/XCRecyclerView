package com.xclib.recyclerviewtest.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xclib.recyclerview.EmptyView;
import com.xclib.recyclerview.HeaderRecyclerViewAdapter;
import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerview.XCRecycleView;
import com.xclib.recyclerviewtest.R;

public abstract class HeaderRecyclerViewBaseFragment extends Fragment {

    private int scrollXValue = 0;
    private int scrollYValue = 0;

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            scrollXValue += dx;
            scrollYValue += dy;


            if (getActivity() instanceof HeaderViewProvider) {
                HeaderViewProvider headerViewProvider = (HeaderViewProvider) getActivity();

                if (scrollYValue < headerViewProvider.getHeaderViewHeight() + 200)
                    headerViewProvider.onHeadScrollHScrolled(scrollYValue);
            }
        }
    };

    protected XCRecycleView xcRecycleView;
    protected EmptyView emptyView;
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
        View rootView = inflater.inflate(R.layout.fragment_recycler_view_common, container, false);
        xcRecycleView = (XCRecycleView) rootView.findViewById(R.id.xc_recycler_view);

        emptyView = new EmptyView(getContext());

        headerView = new LinearLayout(getContext());

        if (getActivity() instanceof HeaderViewProvider) {
            HeaderViewProvider headerViewProvider = (HeaderViewProvider) getActivity();

            View view = new View(getContext());
            headerView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerViewProvider.getHeaderViewHeight()));
        }

        baseAdapter = getAdapter();
        adapter = new HeaderRecyclerViewAdapter(getContext(), baseAdapter, headerView, emptyView);

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

        xcRecycleView.clear();
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
        int getHeaderViewHeight();

        void onHeadScrollHScrolled(int scrollY);
    }

}
