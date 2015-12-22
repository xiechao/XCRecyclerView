package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xclib.recyclerview.RecyclerViewHeaderBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestHeaderSectionAdapter extends RecyclerViewHeaderBaseAdapter<Person> {
    public TestHeaderSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getHeaderViewResourceId() {
        return R.layout.item_section_header;
    }


    @Override
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_user;
    }

    @Override
    protected ViewHolderBase onHeaderCreateViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class HeaderViewHolder extends ViewHolderBase {
        @Bind(R.id.title)
        TextView title;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            title.setText(data.getSectionHeader());
        }
    }

    public class ViewHolder extends ViewHolderBase {
        @Bind(R.id.tv_name)
        TextView tvName;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            tvName.setText(data.getName());
        }
    }
}
