package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xclib.recyclerview.PinnedSectionBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinnedSectionAdapter extends PinnedSectionBaseAdapter<Person> {

    public PinnedSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getHeaderViewResourceId() {
        return R.layout.item_pinned_section_header;
    }


    @Override
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_linear_common;
    }

    @Override
    protected ViewHolderBase onHeaderCreateViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType) {
        return new ViewHolder(viewItem);
    }

    public class HeaderViewHolder extends ViewHolderBase<Person> {
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

    public class ViewHolder extends ViewHolderBase<Person> {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.root_layout)
        RelativeLayout rootLayout;

        private Person person;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            this.person = data;

            tvName.setText(data.getName());
        }

        @OnClick({R.id.root_layout})
        public void onClickItem(final View view) {
            Toast.makeText(view.getContext(), "onClickItem: " + person.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
