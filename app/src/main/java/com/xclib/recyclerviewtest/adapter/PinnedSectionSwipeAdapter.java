package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.xclib.recyclerview.PinnedSectionSwipeBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinnedSectionSwipeAdapter extends PinnedSectionSwipeBaseAdapter<Person> {

    public PinnedSectionSwipeAdapter(Context context) {
        super(context);
    }

    @Override
    protected boolean isCommonItemSupportSwipe(int position, Person data) {
        return true;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
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
        return R.layout.item_layout_swip_common;
    }

    @Override
    protected ViewHolderBase onHeaderCreateViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType) {
        return new ViewHolder(viewItem);
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
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.root_layout)
        LinearLayout rootLayout;
        @Bind(R.id.swipe_layout)
        SwipeLayout swipeLayout;

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

        @OnClick({R.id.btn_delete})
        public void onClickDeleteItem(final View view) {
            Toast.makeText(view.getContext(), "onClickDeleteItem: " + person.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
