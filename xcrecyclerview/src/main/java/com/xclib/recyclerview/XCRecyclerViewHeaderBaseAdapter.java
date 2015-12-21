package com.xclib.recyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class XCRecyclerViewHeaderBaseAdapter<T extends ISectionData> extends XCRecyclerViewBaseAdapter<T> {
    protected XCRecyclerViewHeaderBaseAdapter(Context context) {
        super(context);

        setHasStableIds(true);
    }


    private StickyHeadersAdapter<GTViewHolderBase> stickyHeadersAdapter = new StickyHeadersAdapter<GTViewHolderBase>() {
        @Override
        public GTViewHolderBase onCreateViewHolder(ViewGroup parent) {
            return onHeaderCreateViewHolder(LayoutInflater.from(parent.getContext()).inflate(getHeaderViewResourceId(), parent, false));
        }

        @Override
        public void onBindViewHolder(GTViewHolderBase gtViewHolderBase, int position) {
            gtViewHolderBase.render(getItem(position));
        }

        @Override
        public long getHeaderId(int position) {
            return XCRecyclerViewHeaderBaseAdapter.this.getHeaderId(getItem(position));
        }
    };

    protected abstract int getHeaderViewResourceId();

    protected abstract GTViewHolderBase onHeaderCreateViewHolder(View view);

    protected abstract long getHeaderId(T data);

    public StickyHeadersAdapter<GTViewHolderBase> getStickyHeadersAdapter() {
        return stickyHeadersAdapter;
    }

    @Override
    public void resetData(List<T> dataList) {

        Collections.sort(dataList, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                String lhsSortKey = lhs.getSortKey();
                String rhsSortKey = rhs.getSortKey();

                if (!TextUtils.isEmpty(lhsSortKey)) {
                    lhsSortKey = lhsSortKey.trim().toUpperCase();
                }

                if (lhsSortKey == null) {
                    lhsSortKey = "";
                }

                if (!TextUtils.isEmpty(rhsSortKey)) {
                    rhsSortKey = rhsSortKey.trim().toUpperCase();
                }

                if (rhsSortKey == null) {
                    rhsSortKey = "";
                }

                return lhsSortKey.compareTo(rhsSortKey);
            }
        });

        super.resetData(dataList);
    }
}
