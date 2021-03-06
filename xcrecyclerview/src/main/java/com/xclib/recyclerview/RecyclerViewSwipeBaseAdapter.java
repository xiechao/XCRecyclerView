package com.xclib.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;

import java.util.List;

public abstract class RecyclerViewSwipeBaseAdapter<T> extends RecyclerViewBaseAdapter<T> implements SwipeItemMangerInterface, SwipeAdapterInterface {
    private final SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    protected RecyclerViewSwipeBaseAdapter(Context context) {
        super(context);
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }


    private boolean isSupportSwipe(int position) {
        return !(isHeaderViewByPosition(position) || isFooterViewByPosition(position) || isLoadMoreViewByPosition(position)) && isCommonItemSupportSwipe(position, getItem(position));
    }

    @SuppressWarnings("SameReturnValue")
    protected abstract boolean isCommonItemSupportSwipe(int position, T data);

    @Override
    protected void onBindViewHolderSuccess(View view, int position) {

        if (isSupportSwipe(position)) {
            mItemManger.bindView(view, position);
        }
    }

    public boolean isOpening(RecyclerView recyclerView) {
        for (int position = 0; position < recyclerView.getChildCount(); position++) {
            View childView = recyclerView.getChildAt(position);

            if (childView != null) {
                SwipeLayout swipeLayout = (SwipeLayout) childView.findViewById(getSwipeLayoutResourceId(position));

                if (swipeLayout != null) {
                    if (swipeLayout.getOpenStatus() == SwipeLayout.Status.Middle) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
