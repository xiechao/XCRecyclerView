package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xclib.recyclerview.XCRecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecycleViewAdapter  extends XCRecyclerViewBaseAdapter<Person> {
    public RecycleViewAdapter(Context context, List<Person> personList) {
        super(context);

        resetData(personList);
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
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
        public void render(Person data) {
            tvName.setText(data.getName());
            tvEmailId.setText(data.getEmail());
        }
    }
}