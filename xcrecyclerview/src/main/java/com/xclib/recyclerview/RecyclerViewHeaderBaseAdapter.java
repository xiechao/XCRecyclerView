package com.xclib.recyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RecyclerViewHeaderBaseAdapter<T extends ISectionData> extends RecyclerViewBaseAdapter<T> implements SectionIndexer {
    private final StickyHeadersAdapter<ViewHolderBase> stickyHeadersAdapter = new StickyHeadersAdapter<ViewHolderBase>() {
        @Override
        public ViewHolderBase onCreateViewHolder(ViewGroup parent) {
            return onHeaderCreateViewHolder(LayoutInflater.from(parent.getContext()).inflate(getHeaderViewResourceId(), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolderBase viewHolderBase, int position) {
            viewHolderBase.render(getItem(position));
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getSectionHeaderId();
        }
    };
    private final ArrayList<T> sectionList = new ArrayList<>();

    protected RecyclerViewHeaderBaseAdapter(Context context) {
        super(context);

        setHasStableIds(true);
    }

    @SuppressWarnings("SameReturnValue")
    protected abstract int getHeaderViewResourceId();

    protected abstract ViewHolderBase onHeaderCreateViewHolder(View view);

    public StickyHeadersAdapter<ViewHolderBase> getStickyHeadersAdapter() {
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
    }

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

        return sectionIndex;
    }
}
