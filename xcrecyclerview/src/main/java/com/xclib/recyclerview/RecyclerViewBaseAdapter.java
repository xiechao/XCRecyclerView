package com.xclib.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewBaseAdapter.ViewHolderBase> implements Filterable {
    private static final int VIEW_TYPE_HEADER_BASE = 1000;
    private static final int VIEW_TYPE_FOOTER_BASE = 2000;
    private static final int VIEW_TYPE_LOAD_MORE = 3000;
    private static final int LOAD_MORE_ANI_TIME = 300;
    private final ArrayList<T> baseItems = new ArrayList<>();
    private List<T> showItems = new ArrayList<>();
    private final ArrayList<View> headerViewList = new ArrayList<>();
    private final ArrayList<View> footerViewList = new ArrayList<>();
    private boolean mIsLoading;
    private XCRecycleView.OnLoadMoreListener onLoadMoreListener;
    private RelativeLayout loadMoreViewContainer = null;
    private View loadMoreViewContent = null;
    private CharSequence mCurrentConstraint = "";


    private Filter mFilter = new Filter() {

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            mCurrentConstraint = constraint;

            final Filter.FilterResults oReturn = new Filter.FilterResults();
            final ArrayList<T> results = new ArrayList<>();


            if (baseItems != null) {
                for (T data : baseItems) {
                    if (checkFiltering(data, constraint))
                        results.add(data);
                }
            }
            oReturn.values = results;

            return oReturn;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {

            showItems.clear();
            if (results.values != null) {
                showItems.addAll((ArrayList<T>) results.values);
            }

            notifyDataSetChanged();
        }
    };

    private Context context;

    @SuppressLint("InflateParams")
    public RecyclerViewBaseAdapter(Context context) {
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);

        loadMoreViewContainer = (RelativeLayout) inflater.inflate(R.layout.item_loading_container, null, false);
        loadMoreViewContent = inflater.inflate(R.layout.item_loading, loadMoreViewContainer, false);

        loadMoreViewContainer.addView(loadMoreViewContent, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    protected boolean checkFiltering(T data, CharSequence constraint) {
        return true;
    }

    public void addMoreItem(T newItem, boolean append) {
        if (append) {
            this.baseItems.add(newItem);
        } else {
            this.baseItems.add(0, newItem);
        }

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            checkFiltering(newItem, mCurrentConstraint);

            if (append) {
                this.showItems.add(newItem);

                notifyItemInserted(getHeaderViewsCount() + getCommonItemCount() - 1);
            } else {
                this.showItems.add(0, newItem);

                notifyItemInserted(getHeaderViewsCount());
            }
        }
    }

    public void addMoreItems(List<T> newItems, boolean append) {
        if (append) {
            this.baseItems.addAll(newItems);
        } else {
            this.baseItems.addAll(0, newItems);
        }

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            for (T data : newItems) {
                checkFiltering(data, mCurrentConstraint);
            }

            if (append) {
                showItems.addAll(newItems);
                notifyItemRangeInserted(getHeaderViewsCount() + getCommonItemCount() - newItems.size(), newItems.size());
            } else {
                showItems.addAll(0, newItems);
                notifyItemRangeInserted(getHeaderViewsCount(), newItems.size());
            }
        }
    }


    public void removeAllItems() {
        this.baseItems.clear();

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            showItems.clear();
            notifyDataSetChanged();
        }
    }

    public void remove(T item) {
        baseItems.remove(item);

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            int index = showItems.indexOf(item);

            if (index > 0) {
                showItems.remove(index);
                notifyItemRemoved(getHeaderViewsCount() + index);
            }
        }
    }

    public void update(T item) {
        int index = baseItems.indexOf(item);
        if (index >= 0) {
            baseItems.remove(item);
            baseItems.add(index, item);

            if (!TextUtils.isEmpty(mCurrentConstraint)) {
                getFilter().filter(mCurrentConstraint);
            } else {
                checkFiltering(item, mCurrentConstraint);

                index = showItems.indexOf(item);

                if (index > 0) {
                    showItems.remove(index);
                    showItems.add(index, item);

                    notifyItemChanged(getHeaderViewsCount() + index);
                }
            }
        }
    }

    public int indexOf(T item) {
        return showItems.indexOf(item);
    }

    public void add(T item) {
        baseItems.add(item);

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            checkFiltering(item, mCurrentConstraint);

            showItems.add(item);

            notifyItemInserted(getHeaderViewsCount() + getCommonItemCount() - 1);
        }
    }

    public void add(int index, T item) {
        baseItems.add(index, item);

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            checkFiltering(item, mCurrentConstraint);

            showItems.add(index, item);
            notifyItemInserted(getHeaderViewsCount() + index);
        }
    }

    public void setItems(List<T> dataList) {
        baseItems.clear();
        baseItems.addAll(dataList);

        if (!TextUtils.isEmpty(mCurrentConstraint)) {
            getFilter().filter(mCurrentConstraint);
        } else {
            for (T data : dataList) {
                checkFiltering(data, mCurrentConstraint);
            }

            int oldData = getCommonItemCount();
            showItems.clear();
            notifyItemRangeRemoved(getHeaderViewsCount(), oldData);

            showItems.addAll(baseItems);
            notifyItemRangeInserted(getHeaderViewsCount(), getCommonItemCount());
        }

    }

    public List<T> getAllCommonItems() {
        return baseItems;
    }

    public int getAllCommonItemCount() {
        return baseItems.size();
    }

    public boolean isFiltered() {
        return baseItems.size() != showItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;

        if (getCommonItemCount() > 0 && position >= 0 && position < getHeaderViewsCount()) {
            viewType = VIEW_TYPE_HEADER_BASE + position;
        } else if (getCommonItemCount() > 0 && position >= getHeaderViewsCount() + getCommonItemCount() && position < getHeaderViewsCount() + getCommonItemCount() + getFooterViewsCount()) {
            viewType = VIEW_TYPE_FOOTER_BASE + (position - (getHeaderViewsCount() + getCommonItemCount()));
        } else if (getCommonItemCount() > 0 && mIsLoading && position == getHeaderViewsCount() + getCommonItemCount() + getFooterViewsCount()) {
            viewType = VIEW_TYPE_LOAD_MORE;
        } else if (getCommonItemCount() <= 0 && mIsLoading && position == 0) {
            viewType = VIEW_TYPE_LOAD_MORE;
        } else {
            viewType = getCommonItemViewType(position, getItem(position));
        }

        return viewType;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    boolean isHeaderViewByPosition(int position) {
        return isHeaderViewByViewType(getItemViewType(position));
    }

    boolean isFooterViewByPosition(int position) {
        return isFooterViewByViewType(getItemViewType(position));
    }

    boolean isLoadMoreViewByPosition(int position) {
        return isLoadMoreViewByViewType(getItemViewType(position));
    }

    private boolean isHeaderViewByViewType(int viewType) {
        return viewType >= VIEW_TYPE_HEADER_BASE && viewType < VIEW_TYPE_FOOTER_BASE;
    }

    private boolean isFooterViewByViewType(int viewType) {
        return viewType >= VIEW_TYPE_FOOTER_BASE && viewType < VIEW_TYPE_LOAD_MORE;

    }

    private boolean isLoadMoreViewByViewType(int viewType) {
        return viewType == VIEW_TYPE_LOAD_MORE;
    }

    boolean isSupportSeparateSpan(int position) {
        return !(isHeaderViewByPosition(position) ||
                isFooterViewByPosition(position) ||
                isLoadMoreViewByPosition(position));
    }


    public abstract int getCommonItemViewType(int position, T data);

    @Override
    public ViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderBase viewHolderBase;

        if (isHeaderViewByViewType(viewType)) {
            viewHolderBase = new HeaderViewHolder(headerViewList.get(viewType - VIEW_TYPE_HEADER_BASE));
        } else if (isFooterViewByViewType(viewType)) {
            viewHolderBase = new FooterViewHolder(footerViewList.get(viewType - VIEW_TYPE_FOOTER_BASE));
        } else if (isLoadMoreViewByViewType(viewType)) {
            viewHolderBase = new LoadMoreViewHolder(loadMoreViewContainer);
        } else {
            View viewItem = null;
            if (getCommonViewResourceId(viewType) > 0) {
                viewItem = LayoutInflater.from(parent.getContext()).inflate(getCommonViewResourceId(viewType), parent, false);
            }

            viewHolderBase = onCommonCreateViewHolder(parent, viewItem, viewType);
        }

        return viewHolderBase;
    }


    protected abstract int getCommonViewResourceId(int viewType);

    protected abstract ViewHolderBase onCommonCreateViewHolder(ViewGroup parent, View viewItem, int viewType);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerViewBaseAdapter.ViewHolderBase holder, int position) {
        holder.render(getItem(position));

        onBindViewHolderSuccess(holder.itemView, position);
    }

    void onBindViewHolderSuccess(View view, int position) {

    }

    public T getItem(int position) {
        int index = position - (getCommonItemCount() > 0 ? getHeaderViewsCount() : 0);

        if (index >= 0 && index < getCommonItemCount()) {
            return showItems.get(index);
        } else {
            return null;
        }
    }

    public void setOnLoadMoreListener(XCRecycleView.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getCommonItemCount() + getFooterViewsCount() + getLoadMoreItemCount();
    }

    public int getCommonItemCount() {
        return showItems.size();
    }

    public int getLoadMoreItemCount() {
        return mIsLoading ? 1 : 0;
    }

    public boolean tryDoLoadMore() {
        if (!mIsLoading) {
            if (null != onLoadMoreListener) {
                if (onLoadMoreListener.isHasMoreData()) {
                    loadMoreViewContent.setVisibility(View.INVISIBLE);

                    expand(loadMoreViewContainer, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            loadMoreViewContent.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            loadMoreViewContainer.clearAnimation();

                            onLoadMoreListener.onLoadMore();
                        }
                    });

                    mIsLoading = true;

                    notifyItemInserted(getItemCount() - 1);

                    return true;
                } else {
                    loadMoreViewContent.setVisibility(View.GONE);

                    return false;
                }
            }
        }
        return false;
    }

    public void setLoadMoreEnd(boolean isImmediate) {
        if (!mIsLoading) {
            return;
        }

        collapse(loadMoreViewContainer, isImmediate, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadMoreViewContainer.clearAnimation();
                loadMoreViewContent.setVisibility(View.GONE);

                mIsLoading = false;

                notifyItemRemoved(getItemCount());
            }
        });
    }

    public void addHeaderView(View v) {
        headerViewList.add(v);
    }

    public boolean removeHeaderView(View v) {
        return headerViewList.remove(v);
    }

    public int getHeaderViewsCount() {
        return getCommonItemCount() > 0 ? headerViewList.size() : 0;
    }

    public void addFooterView(View v) {
        footerViewList.add(v);
    }

    public boolean removeFooterView(View v) {
        return footerViewList.remove(v);
    }

    public int getFooterViewsCount() {
        return getCommonItemCount() > 0 ? footerViewList.size() : 0;
    }

    private void expand(View v, Animation.AnimationListener collapseListener) {

        TranslateAnimation anim;
        // if (expand) {
        anim = new TranslateAnimation(0.0f, 0.0f, 40 * v.getContext().getResources().getDisplayMetrics().scaledDensity, 0.0f);
        v.setVisibility(View.VISIBLE);

        anim.setAnimationListener(collapseListener);

        anim.setDuration(LOAD_MORE_ANI_TIME);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }

    private void collapse(final View v, boolean isImmediately, Animation.AnimationListener collapseListener) {
        if (isImmediately) {

            collapseListener.onAnimationEnd(null);
            return;
        }


        final int initialHeight = (int) (40 * v.getContext().getResources().getDisplayMetrics().scaledDensity);

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    v.getLayoutParams().height = AbsListView.LayoutParams.WRAP_CONTENT;
                    v.requestLayout();
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        anim.setAnimationListener(collapseListener);

        anim.setDuration(LOAD_MORE_ANI_TIME);
        v.startAnimation(anim);
    }

    public void resetHeaderViewsCount(ArrayList<View> headerViewArrayList) {
        int oldHeaderCount = getHeaderViewsCount();

        headerViewList.clear();
        notifyItemRangeRemoved(0, oldHeaderCount);

        headerViewList.addAll(headerViewArrayList);

        notifyItemRangeInserted(0, getHeaderViewsCount());
    }

    public void resetFooterViewsCount(ArrayList<View> footerViewArrayList) {
        int oldFooterCount = getFooterViewsCount();

        footerViewList.clear();
        notifyItemRangeRemoved(getHeaderViewsCount() + getCommonItemCount(), oldFooterCount);

        footerViewList.addAll(footerViewArrayList);

        notifyItemRangeInserted(getHeaderViewsCount() + getCommonItemCount(), getFooterViewsCount());
    }

    public abstract class ViewHolderBase extends RecyclerView.ViewHolder {
        public ViewHolderBase(View itemView) {
            super(itemView);
        }

        public abstract void render(T data);
    }

    private class HeaderViewHolder extends ViewHolderBase {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

    private class FooterViewHolder extends ViewHolderBase {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

    private class LoadMoreViewHolder extends ViewHolderBase {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

}
