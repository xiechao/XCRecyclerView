package com.xclib.recyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.ISectionData;

import java.util.List;

public class BigramHeaderAdapter <T extends ISectionData> implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {

    private List<T> items;

    public BigramHeaderAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_header, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.title.setText(items.get(position).getSectionHeader());
    }

    @Override
    public long getHeaderId(int position) {
        return items.get(position).getSectionHeader().hashCode();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
