package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerViewBaseAdapter<Person> {

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_common;
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType) {
        return new TestViewHolder(viewItem);
    }

    public class TestViewHolder extends ViewHolderBase {
        @Bind(R.id.tv_name)
        TextView tvName;

        public TestViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            tvName.setText(data.getName());
        }
    }
}
