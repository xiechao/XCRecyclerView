package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xclib.recyclerview.RecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestRecycleViewAdapter extends RecyclerViewBaseAdapter<Person> {

    public TestRecycleViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_user;
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(View view) {
        return new TestViewHolder(view);
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
