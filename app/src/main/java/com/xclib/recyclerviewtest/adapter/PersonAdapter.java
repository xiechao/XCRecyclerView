package com.xclib.recyclerviewtest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xclib.recyclerview.XCRecyclerViewBaseAdapter;
import com.xclib.recyclerviewtest.R;
import com.xclib.recyclerviewtest.model.Person;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonAdapter extends XCRecyclerViewBaseAdapter<Person> {
    public PersonAdapter(Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getCommonItemViewType(int position, Person data) {
        return 0;
    }

    @Override
    protected int getViewResourceId(int viewType) {
        return R.layout.list_item;
    }

    @Override
    protected GTViewHolderBase onCommonCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends GTViewHolderBase {
        @Bind(R.id.name)
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void render(Person data) {
            name.setText(data.getName());
        }
    }

    //
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
//
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return items.get(position).hashCode();
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.label.setText(items.get(position).toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView label;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            this.label = (TextView) itemView.findViewById(R.id.name);
//
//        }
//    }


}
