package com.xclib.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class XCRecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<XCRecyclerViewBaseAdapter.GTViewHolderBase> {
    private static final int VIEW_TYPE_HEADER_BASE = 1000;
    private static final int VIEW_TYPE_FOOTER_BASE = 2000;
    private static final int VIEW_TYPE_LOAD_MORE = 3000;
    private static final int LOAD_MORE_ANI_TIME = 300;

    private boolean mIsLoading;
    private ArrayList<T> dataArrayList = new ArrayList<>();
    private XCRecycleView.OnLoadMoreListener onLoadMoreListener;
    private ArrayList<View> headerViewList = new ArrayList<>();
    private ArrayList<View> footerViewList = new ArrayList<>();
    private RelativeLayout loadMoreViewContainer = null;
    private View loadMoreViewContent = null;

    @SuppressLint("InflateParams")
    public XCRecyclerViewBaseAdapter(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        loadMoreViewContainer = (RelativeLayout) inflater.inflate(R.layout.item_loading_container, null, false);
        loadMoreViewContent = inflater.inflate(R.layout.item_loading, loadMoreViewContainer, false);

        loadMoreViewContainer.addView(loadMoreViewContent, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    }

    public void resetData(List<T> dataList) {
        int oldData = dataArrayList.size();
        dataArrayList.clear();
        notifyItemRangeRemoved(headerViewList.size(), oldData);

        dataArrayList.addAll(dataList);
        notifyItemRangeInserted(headerViewList.size(), dataArrayList.size());
    }

    public void addAll(List<T> dataList) {
        dataArrayList.addAll(dataList);

        notifyItemRangeInserted(headerViewList.size() + dataArrayList.size(), dataList.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < headerViewList.size()) {
            return VIEW_TYPE_HEADER_BASE + position;
        } else if (position >= headerViewList.size() + dataArrayList.size() && position < headerViewList.size() + dataArrayList.size() + footerViewList.size()) {
            return VIEW_TYPE_FOOTER_BASE + (position - (headerViewList.size() + dataArrayList.size()));
        } else if (mIsLoading && position == headerViewList.size() + dataArrayList.size() + footerViewList.size()) {
            return VIEW_TYPE_LOAD_MORE;
        } else {
            return getCommonItemViewType(position, getItem(position));
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    boolean isHeaderView(int position) {
        return position >= 0 && position < headerViewList.size();
    }

    boolean isFooterView(int position) {
        return position >= headerViewList.size() + dataArrayList.size() && position < headerViewList.size() + dataArrayList.size() + footerViewList.size();
    }

    boolean isLoadMoreView(int position) {
        return mIsLoading && position == headerViewList.size() + dataArrayList.size() + footerViewList.size();
    }

    public abstract int getCommonItemViewType(int position, T data);

    @Override
    public GTViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType >= VIEW_TYPE_HEADER_BASE && viewType < VIEW_TYPE_FOOTER_BASE) {
            return new HeaderViewHolder(headerViewList.get(viewType - VIEW_TYPE_HEADER_BASE));
        } else if (viewType >= VIEW_TYPE_FOOTER_BASE && viewType < VIEW_TYPE_LOAD_MORE) {
            return new FooterViewHolder(footerViewList.get(viewType - VIEW_TYPE_FOOTER_BASE));
        } else if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new LoadMoreViewHolder(loadMoreViewContainer);
        } else {
            return onCommonCreateViewHolder(LayoutInflater.from(parent.getContext()).inflate(getCommonViewResourceId(viewType), parent, false));
        }
    }


    protected abstract int getCommonViewResourceId(int viewType);

    protected abstract GTViewHolderBase onCommonCreateViewHolder(View view);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(XCRecyclerViewBaseAdapter.GTViewHolderBase holder, int position) {
        if (position >= 0 && position < headerViewList.size()) {
            holder.render(null);
        } else if (position >= headerViewList.size() + dataArrayList.size() && position < headerViewList.size() + dataArrayList.size() + footerViewList.size()) {
            holder.render(null);
        } else if (mIsLoading && position == headerViewList.size() + dataArrayList.size() + footerViewList.size()) {
            holder.render(null);
        } else {
            holder.render(getItem(position));
        }

        onBindViewHolderSuccess(holder.itemView, position);
    }

    protected void onBindViewHolderSuccess(View view, int position) {

    }

    protected T getItem(int position) {
        return dataArrayList.get(position - headerViewList.size());
    }

    public void setOnLoadMoreListener(XCRecycleView.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return headerViewList.size() + dataArrayList.size() + footerViewList.size() + (mIsLoading ? 1 : 0);
    }

    public int getCommonItemCount() {
        return dataArrayList.size();
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
        return headerViewList.size();
    }

    public void addFooterView(View v) {
        footerViewList.add(v);
    }

    public boolean removeFooterView(View v) {
        return footerViewList.remove(v);
    }

    public int getFooterViewsCount() {
        return footerViewList.size();
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
        int oldHeaderCount = headerViewList.size();

        headerViewList.clear();
        notifyItemRangeRemoved(0, oldHeaderCount);

        headerViewList.addAll(headerViewArrayList);

        notifyItemRangeInserted(0, headerViewList.size());
    }

    public void resetFooterViewsCount(ArrayList<View> footerViewArrayList) {
        int oldFooterCount = footerViewList.size();

        footerViewList.clear();
        notifyItemRangeRemoved(headerViewList.size() + dataArrayList.size(), oldFooterCount);

        footerViewList.addAll(footerViewArrayList);

        notifyItemRangeInserted(headerViewList.size() + dataArrayList.size(), footerViewList.size());
    }

    public abstract class GTViewHolderBase extends RecyclerView.ViewHolder {
        public GTViewHolderBase(View itemView) {
            super(itemView);
        }

        public abstract void render(T data);
    }

    private class HeaderViewHolder extends GTViewHolderBase {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

    private class FooterViewHolder extends GTViewHolderBase {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

    private class LoadMoreViewHolder extends GTViewHolderBase {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void render(Object data) {

        }
    }

}
