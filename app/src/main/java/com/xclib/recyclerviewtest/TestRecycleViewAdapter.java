package com.xclib.recyclerviewtest;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xclib.recyclerview.XCRecyclerViewBaseAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestRecycleViewAdapter extends XCRecyclerViewBaseAdapter<User> {
    public TestRecycleViewAdapter(Context context, List<User> userList) {
        super(context);

        resetData(userList);
    }

    @Override
    public int getCommonItemViewType(int position, User data) {
        return 0;
    }

    @Override
    protected int getViewResourceId(int viewType) {
        return R.layout.item_layout_user;
    }

    @Override
    protected GTViewHolderBase onCommonCreateViewHolder(View view) {
        return new TestViewHolder(view);
    }

    public class TestViewHolder extends GTViewHolderBase {
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvEmailId)
        TextView tvEmailId;

        public TestViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(User data) {
            tvName.setText(data.getName());
            tvEmailId.setText(data.getEmail());
        }
    }
}
