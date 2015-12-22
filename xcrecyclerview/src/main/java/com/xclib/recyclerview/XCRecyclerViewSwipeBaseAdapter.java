package com.xclib.recyclerview;

import android.content.Context;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;

import java.util.List;

public abstract class XCRecyclerViewSwipeBaseAdapter<T> extends XCRecyclerViewBaseAdapter<T> implements SwipeItemMangerInterface, SwipeAdapterInterface {
    protected XCRecyclerViewSwipeBaseAdapter(Context context) {
        super(context);
    }

    private final SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

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
        return !(isHeaderView(position) || isFooterView(position) || isLoadMoreView(position)) && isCommonItemSupportSwipe(position, getItem(position));
    }

    @SuppressWarnings("SameReturnValue")
    protected abstract boolean isCommonItemSupportSwipe(int position, T data);

    @Override
    protected void onBindViewHolderSuccess(View view, int position) {

        if (isSupportSwipe(position)) {
            mItemManger.bindView(view, position);
        }
    }
}
