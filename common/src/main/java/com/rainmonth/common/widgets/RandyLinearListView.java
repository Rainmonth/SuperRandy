package com.rainmonth.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.R;
import com.rainmonth.common.adapter.RandyFixedSizeAdapter;
import com.rainmonth.common.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-26 11:16
 */
public class RandyLinearListView<T extends BaseBean> extends FrameLayout {

    private InnerRecyclerView mListView;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;
    private OnRenderCallback<T> mOnRenderCallback;
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 首个item理顶部边距，通常和每个item边距不一样，所以单独设定
     */
    private int mFirstSpace;
    /**
     * 最后一个item的间距
     */
    private int mLastSpace;

    /**
     * 因为在未测量之间需要，单独提出来，常见的宽度都是屏幕宽度
     * 额外的边距，如果其它view，控件以外的宽度总量，一般控件的宽度都是屏幕宽度
     */
    private int mExtraSpace;

    /**
     * 固定的时候，距离中心轴的距离
     */
    private int mFixedSpace;

    /**
     * 展示的数量，默认是两个
     */
    private int mShowCount = 2;

    /**
     * item之间的间隔
     */
    private int mSpace;

    /**
     * 首次渲染靠右的item露出的偏移量
     */
    private int mOffset;

    /**
     * 资源Id
     */
    private int mLayoutId;

    /**
     * 数据源
     */
    @NonNull
    private List<T> mData;

    /**
     * 整个控件的宽高比，默认-1，优先级高于item的宽高比
     */
    private float mRate = -1;

    /**
     * 每个item的宽高比，默认1:1
     */
    private float mItemRate = 1;

    /**
     * 是否固定，关联展示数量，若不固定将根据数据长度调整展示数量
     */
    private boolean mIsFixed = true;

    /**
     * 每个Item的宽度，如果未设置那么会通过计算比例得出宽度
     */
    private int mItemWidth;

    /**
     * 滑动停下预留空间
     */
    private int mRetainStart;

    /**
     * 是否是缓存，如果前后加载数据一致将不走绘制流程以免导致卡顿，原理是利用equals，因此如果要精确比较两个集合，集合的泛型结构自己实现equals方法
     */
    private boolean isCache = false;
    private LinearSpaceItemDecoration mSpaceItemDecoration;
    private int mOrientation;
    private int mPaddingStart;
    private int mPaddingEnd;
    /**
     * view宽度，默认是屏幕短边，暂时不支持稀奇古怪的距离
     */
    private int mViewWidth;
    /**
     * 用于屏幕旋转，重新加载
     */
    private boolean isReload;
    private LinearAdapter<T> mAdapter;

    public RandyLinearListView(@NonNull Context context) {
        this(context, null);
    }

    public RandyLinearListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RandyLinearListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RandyLinearListView);
        mFirstSpace = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_firstItemSpace, mFirstSpace);
        mLastSpace = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_lastItemSpace, mLastSpace);
        mExtraSpace = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_extraItemSpace, mExtraSpace);
        mFixedSpace = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_fixedItemSpace, mFixedSpace);
        mRetainStart = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_retainStart, mRetainStart);
        mShowCount = ta.getInteger(R.styleable.RandyLinearListView_showItemCount, mShowCount);
        mSpace = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_itemSpace, mSpace);
        mOffset = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_rightItemOffset, mOffset);
        mItemWidth = ta.getDimensionPixelSize(R.styleable.RandyLinearListView_itemWidth, mItemWidth);
        mLayoutId = ta.getResourceId(R.styleable.RandyLinearListView_itemLayoutId, mLayoutId);
        mRate = ta.getFloat(R.styleable.RandyLinearListView_listRate, mRate);
        mItemRate = ta.getFloat(R.styleable.RandyLinearListView_listItemRate, mItemRate);
        mIsFixed = ta.getBoolean(R.styleable.RandyLinearListView_fixedMode, mIsFixed);
        mOrientation = ta.getInteger(R.styleable.RandyLinearListView_android_orientation, RecyclerView.HORIZONTAL);
        ta.recycle();
        mPaddingStart = getPaddingStart();
        mPaddingEnd = getPaddingEnd();

        mListView = new InnerRecyclerView(context);
        mListView.setHasFixedSize(true);
        mListView.setNestedScrollingEnabled(false);
        mListView.setOverScrollMode(OVER_SCROLL_NEVER);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mListView, params);
        mLinearLayoutManager = new LinearLayoutManager(context, mOrientation, false);
        RecyclerView.ItemAnimator itemAnimator = mListView.getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        mLinearLayoutManager.setInitialPrefetchItemCount(mShowCount + (mOffset > 0 ? 1 : 0));
        mListView.setLayoutManager(mLinearLayoutManager);
        mSpaceItemDecoration = new LinearSpaceItemDecoration.Builder()
                .setOrientation(mOrientation)
                .setLastSpace(mLastSpace)
                .setFirstSpaceSize(mFirstSpace)
                .setSpaceSize(mSpace)
                .build();
        mListView.addItemDecoration(mSpaceItemDecoration);

        mViewWidth = context.getResources().getDisplayMetrics().widthPixels;
        final int centerStartOne = -(mViewWidth - calculateWidth()) / 2;
        final int centerStartTwo = (3 * calculateWidth() + 2 * mSpace - mViewWidth) / 2;
        final int centerStart = centerStartTwo > calculateWidth() / 2 ? centerStartOne : centerStartTwo;
        new RandySnapHelper(mRetainStart, centerStart).attachToRecyclerView(mListView);
        mData = new ArrayList<>();
    }

    public List<T> getData() {
        return mData;
    }

    public RandyLinearListView<T> load(@NonNull List<T> data) {
        if (mData.equals(data) && !isReload) {
            isCache = true;
            return this;
        }
        isCache = false;
        mData.clear();
        mData.addAll(data);
        final int size = mData.size();
        if (!mIsFixed && size <= mShowCount && size > 0) {
            mListView.setCanSideScroll(true);
            final int width = calculateWidth();
            if (mFixedSpace == 0) {
                // 排除前后间距的均分排布
                if (size == mShowCount) {
                    final int padding = Math.round((mViewWidth - width * size - mLastSpace - mFirstSpace - mExtraSpace - mPaddingStart - mPaddingEnd) * 1.f / (size - 1));
                    if (mSpaceItemDecoration.mSpaceSize == padding) {
                        return this;
                    }
                    mListView.removeItemDecoration(mSpaceItemDecoration);
                    addItemDecoration(mOrientation, mLastSpace, mFirstSpace, padding);
                } else {
                    final int padding = (mViewWidth - width * size - mLastSpace - mFirstSpace - mExtraSpace - mPaddingStart - mPaddingEnd) / (size * 2);
                    if (mSpaceItemDecoration.mSpaceSize == padding * 2) {
                        return this;
                    }
                    mListView.removeItemDecoration(mSpaceItemDecoration);
                    addItemDecoration(mOrientation, mLastSpace + padding, mFirstSpace + padding, padding * 2);
                }
            } else {
                // 根据设置等分线间隔排布
                final int padding = (mViewWidth - (size - 1) * 2 * mFixedSpace - width * size) / 2;
                if (mSpaceItemDecoration.mSpaceSize == mFixedSpace * 2) {
                    return this;
                }
                mListView.removeItemDecoration(mSpaceItemDecoration);
                addItemDecoration(mOrientation, padding, padding, mFixedSpace * 2);
            }
        } else {
            mListView.setCanSideScroll(size <= mShowCount);
            if (mSpaceItemDecoration.mSpaceSize == mSpace) {
                return this;
            }
            mListView.removeItemDecoration(mSpaceItemDecoration);
            addItemDecoration(mOrientation, mLastSpace, mFirstSpace, mSpace);
        }
        return this;
    }

    /**
     * 计算item宽度
     */
    private int calculateWidth() {
        if (mItemWidth > 0) {
            return mItemWidth;
        }
        return (mViewWidth - mExtraSpace - mPaddingStart - mPaddingEnd - mShowCount * mSpace - mOffset - mFirstSpace) / mShowCount;
    }

    private void addItemDecoration(int orientation, int lastSpace, int firstSpace, int space) {
        mSpaceItemDecoration = new LinearSpaceItemDecoration.Builder()
                .setOrientation(orientation)
                .setLastSpace(lastSpace)
                .setFirstSpaceSize(firstSpace)
                .setSpaceSize(space)
                .build();
        mListView.addItemDecoration(mSpaceItemDecoration);
    }

    public RandyLinearListView<T> setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RandyLinearListView<T> setOnRenderCallback(OnRenderCallback<T> callback) {
        mOnRenderCallback = callback;
        return this;
    }

    public RandyLinearListView<T> setItemLayoutId(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
        return this;
    }

    public void render() {
        if (mLayoutId == 0) {
            throw new RuntimeException("需要在xml中添加itemLayout");
        }
        if (isCache && !isReload) {
            return;
        }
        if (mListView == null) {
            return;
        }
        if (mAdapter == null) {
            final int width = calculateWidth();
            final int height = mRate == -1 ? Math.round(width * mItemRate) : Math.round((mViewWidth - mExtraSpace) * mRate);
            mAdapter = new LinearAdapter<>(mData, mLayoutId, width, height, mOnRenderCallback);
            mListView.setAdapter(mAdapter);
            if (mOnItemClickListener != null) {
                mAdapter.setOnItemClickListener(mOnItemClickListener);
            }
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mLinearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthPixels = getContext().getResources().getDisplayMetrics().widthPixels;
        if (mViewWidth != widthPixels && !mData.isEmpty()) {
            // 重新测量的时候重新配置，过滤相同宽度
            mViewWidth = widthPixels;
            isReload = true;
            load(mData)
                    .setOnItemClickListener(mOnItemClickListener)
                    .setOnRenderCallback(mOnRenderCallback)
                    .render();
            isReload = false;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(mViewWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
    }

    private static class LinearAdapter<T extends BaseBean> extends RandyFixedSizeAdapter<T, BaseViewHolder> {

        private OnRenderCallback<T> mOnRenderCallback;

        private LinearAdapter(List<T> data, int layoutId, int width, int height, OnRenderCallback<T> callback) {
            super(data, layoutId, width, height);
            mOnRenderCallback = callback;
        }


        @Override
        protected void convert(@NonNull BaseViewHolder helper, T item) {
            super.convert(helper, item);
            if (mOnRenderCallback != null) {
                mOnRenderCallback.onRender(helper, item);
            }
        }
    }

    public interface OnRenderCallback<T extends BaseBean> {
        /**
         * 渲染逻辑
         *
         * @param holder BaseRecyclerViewViewHolder
         * @param data   数据
         */
        void onRender(BaseViewHolder holder, T data);
    }

    private static class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private static final int DEFAULT_HEIGHT = 10;
        private int mSpaceSize;
        private int mFirstSpaceSize;
        private int mOrientation;
        private int mLastSpaceSize;

        LinearSpaceItemDecoration(Builder builder) {
            mFirstSpaceSize = builder.firstSpaceSize;
            mSpaceSize = builder.spaceSize;
            mOrientation = builder.orientation;
            mLastSpaceSize = builder.lastSpaceSize;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter == null) {
                return;
            }
            int itemCount = adapter.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            int spaceSize;
            if (position == itemCount - 1) {
                if (mLastSpaceSize > 0) {
                    spaceSize = mLastSpaceSize;
                } else {
                    spaceSize = 0;
                }
            } else {
                spaceSize = mSpaceSize;
            }
            final int left = position == 0 ? mFirstSpaceSize : 0;
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(left, 0, 0, spaceSize);
            } else {
                outRect.set(left, 0, spaceSize, 0);
            }
        }

        static class Builder {

            private int spaceSize = DEFAULT_HEIGHT;
            private int firstSpaceSize = 0;
            private int orientation = LinearLayoutManager.VERTICAL;
            private int lastSpaceSize = 0;

            Builder setLastSpace(int lastSpaceSize) {
                this.lastSpaceSize = lastSpaceSize;
                return this;
            }

            Builder setFirstSpaceSize(int firstSpaceSize) {
                this.firstSpaceSize = firstSpaceSize;
                return this;
            }

            Builder setSpaceSize(int spaceSize) {
                this.spaceSize = spaceSize;
                return this;
            }

            Builder setOrientation(@RecyclerView.Orientation int orientation) {
                this.orientation = orientation;
                return this;
            }

            LinearSpaceItemDecoration build() {
                return new LinearSpaceItemDecoration(this);
            }
        }
    }

    private static class InnerRecyclerView extends RecyclerView {

        /**
         * 水平可以顺畅滑动的范围-45~45和135~225
         */
        public static final double RANGE_VALUE_ABS = Math.PI / 4D;

        private float downX;
        private float downY;
        private boolean isCanSideScroll;

        private InnerRecyclerView(Context context) {
            super(context);
        }

        private void setCanSideScroll(boolean canSideScroll) {
            isCanSideScroll = canSideScroll;
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            // 这句话很重要，下面的事件拦截都必须配合这句话使用，因为在处理前拦截和不拦截，后续走的分支又不一样
            getParent().requestDisallowInterceptTouchEvent(true);
            final int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downX = ev.getX();
                    downY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveX = ev.getX();
                    float moveY = ev.getY();
                    if (isCanSideScroll) {
                        // 非列表状态（等分的情况），需要让父布局正常处理事件即可，这样事件就会传递到ViewPager
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        if (moveX - downX != 0) {
                            // 处理纵向RecyclerView嵌套水平RecyclerView的情况，水平方向x轴上下45度均自身拦截，不再交给父布局
                            double aTan = Math.atan(Math.abs(moveY - downY) / Math.abs(moveX - downX));
                            getParent().requestDisallowInterceptTouchEvent(aTan >= 0 && aTan <= RANGE_VALUE_ABS);
                        } else {
                            // 系统的一个问题，如果默认情况下，ViewPager嵌套水平RecyclerView滑到最后一个是被拦截的，但是在不停的滑还是可以滑到后面一个，这个让父布局不拦截，自己直接拦截掉，这样事件就不会向上传递
                            getParent().requestDisallowInterceptTouchEvent(moveY == downY);
                        }
                    }
                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }
    }
}
