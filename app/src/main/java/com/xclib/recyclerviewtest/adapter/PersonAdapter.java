package com.xclib.recyclerviewtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xclib.recyclerviewtest.R;

import java.util.List;

public class PersonAdapter <T> extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<T> items;

    public PersonAdapter(List<T> dataList) {
        this.items = dataList;

        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.label.setText(items.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;


        public ViewHolder(View itemView) {
            super(itemView);
            this.label = (TextView) itemView.findViewById(R.id.name);

        }
    }


}
