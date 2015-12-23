package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xclib.recyclerview.FilterableUtil;
import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewGridAdapter extends RecyclerViewBaseAdapter<Person> {
    public RecyclerViewGridAdapter(Context context) {
        super(context);
    }

    @Override
    protected boolean checkFiltering(Person data, CharSequence constraint) {
        if (TextUtils.isEmpty(constraint)) {
            data.setNameFilterEffect(null);
            return true;
        }

        int index = data.getName().indexOf(constraint.toString().trim());

        if (index >= 0) {
            data.setNameFilterEffect(FilterableUtil.translateFilterEffect(getContext(), data.getName(), index, constraint.length()));
        } else {
            data.setNameFilterEffect(null);
        }

        return index >= 0;
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_grid_common;
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType) {
        return new TestViewHolder(viewItem);
    }

    public class TestViewHolder extends ViewHolderBase {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.root_layout)
        RelativeLayout rootLayout;

        private Person person;

        public TestViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            this.person = data;

            if (!TextUtils.isEmpty(data.getNameFilterEffect())) {
                tvName.setText(data.getNameFilterEffect());
            } else {
                tvName.setText(data.getName());
            }
        }

        @OnClick({R.id.root_layout})
        public void onClickItem(final View view) {
            Toast.makeText(view.getContext(), "onClickItem: " + person.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
