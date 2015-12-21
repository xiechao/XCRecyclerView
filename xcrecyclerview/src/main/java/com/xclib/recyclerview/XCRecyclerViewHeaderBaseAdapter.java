package com.xclib.recyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class XCRecyclerViewHeaderBaseAdapter<T extends ISectionData> extends XCRecyclerViewBaseAdapter<T> implements SectionIndexer {
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

        sectionList.clear();
        long sectionHeaderId = 0;
        for (T data : dataList) {
            if (data.getSectionHeaderId() != sectionHeaderId) {
                sectionList.add(data);

                sectionHeaderId = data.getSectionHeaderId();
            }
        }

        super.resetData(dataList);

        Log.d("xxxxxxxxxx", "sectionList.size() = " + sectionList.size() + "; getItemCount() = " + getItemCount());
    }

    private ArrayList<T> sectionList = new ArrayList<>();

    @Override
    public Object[] getSections() {
        return sectionList.toArray();
    }


    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {

        int sectionIndex = -1;
        long sectionHeaderId = -1;
        for (int index = 0; index < getItemCount(); index++) {
            if (sectionHeaderId != getItem(index).getSectionHeaderId()) {
                sectionIndex++;

                sectionHeaderId = getItem(index).getSectionHeaderId();
            }

            if (/*sectionIndex + */index == position) {
                break;
            }
        }


        Log.d("xxxxxxxxxx", "getSectionForPosition sectionIndex = " + sectionIndex + "; position = " + position);

        return sectionIndex;
    }
}
