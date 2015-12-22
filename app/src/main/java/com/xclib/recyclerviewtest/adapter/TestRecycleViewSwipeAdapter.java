package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.xclib.recyclerview.RecyclerViewSwipeBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestRecycleViewSwipeAdapter extends RecyclerViewSwipeBaseAdapter<Person> {

    public TestRecycleViewSwipeAdapter(Context context, List<Person> personList) {
        super(context);

        resetData(personList);
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
    protected int getCommonViewResourceId(int viewType) {
        return R.layout.item_layout_swip_user;
    }

    @Override
    protected ViewHolderBase onCommonCreateViewHolder(View view) {
        return new TestViewHolder(view);
    }

    public class TestViewHolder extends ViewHolderBase {
        @Bind(R.id.btn_delete)
        Button btnDelete;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.swipe_layout)
        SwipeLayout swipeLayout;

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
