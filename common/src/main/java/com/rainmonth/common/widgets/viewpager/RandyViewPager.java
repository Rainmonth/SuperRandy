package com.rainmonth.common.widgets.viewpager;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.rainmonth.common.R;
import com.rainmonth.common.widgets.transformer.CoverModeTransformer;
import com.rainmonth.common.widgets.transformer.ScaleYTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 实现ViewPager
 * 1、可以配置是否自动滚动
 * 2、可以配置是否无限循环
 * 3、可以配置ViewPager动画效果
 */
public class RandyViewPager<T> extends RelativeLayout {
    private static final String TAG = RandyViewPager.class.getSimpleName();

    private CustomViewPager mViewPager;               // ViewPager实例
    private RandyPagerAdapter mAdapter;         // PagerAdapter实例
    private List<T> mDatas;                     // 数据源
    private boolean mIsAutoPlay = true;         // 是否自动播放，默认为true
    private int mCurrentItem = 0;               // 当前位置
    private Handler mHandler = new Handler();   // 可能内存泄漏（todo fix it through weak reference)
    private int mDelayedTime = 3000;            // ViewPager 默认切换时间
    private ViewPagerScroller mViewPageScroller;// 控制ViewPager滑动速度的Scroller
    private boolean mIsOpenMzEffect = true;     // 是否开启魅族效果，默认为true
    private boolean mIsMiddlePageCover = true;  // 中间Page是否覆盖两边，默认覆盖
    private boolean mIsCanLoop = true;          // 是否无限循环
    private LinearLayout mIndicatorContainer;   // indicator容器
    private ArrayList<ImageView> mIndicators = new ArrayList<>();
    // 0为未选中，1为选中 （todo ）
    private int[] mIndicatorRes = new int[]{R.drawable.indicator_normal,
            R.drawable.indicator_selected};
    private int mIndicatorPaddingLeft = 0;      // indicator 距离左边的距离
    private int mIndicatorPaddingRight = 0;     // indicator 距离右边的距离
    private int mIndicatorPaddingTop = 0;       // indicator 距离上边的距离
    private int mIndicatorPaddingBottom = 0;    // indicator 距离下边的距离
    // 在仿魅族模式下，由于前后显示了上下一个页面的部分，因此需要计算这部分padding
    private int mMZModePadding = 0;
    private int mIndicatorAlign = 1;            // 对其方式
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PageClickListener mPageClickListener;

    public enum IndicatorAlign {
        LEFT,//做对齐
        CENTER,//居中对齐
        RIGHT //右对齐
    }

    public RandyViewPager(Context context) {
        super(context);
        init();
    }

    public RandyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public RandyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RandyViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    /**
     * 初始化xml定义的属性
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RandyViewPager);
        mIsOpenMzEffect = typedArray.getBoolean(R.styleable.RandyViewPager_open_mz_mode, true);
        mIsMiddlePageCover = typedArray.getBoolean(R.styleable.RandyViewPager_middle_page_cover, true);
        mIsCanLoop = typedArray.getBoolean(R.styleable.RandyViewPager_canLoop, true);
        mIndicatorAlign = typedArray.getInt(R.styleable.RandyViewPager_indicatorAlign, 1);
        mIndicatorPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.RandyViewPager_indicatorPaddingLeft, 0);
        mIndicatorPaddingRight = typedArray.getDimensionPixelSize(R.styleable.RandyViewPager_indicatorPaddingRight, 0);
        mIndicatorPaddingTop = typedArray.getDimensionPixelSize(R.styleable.RandyViewPager_indicatorPaddingTop, 0);
        mIndicatorPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.RandyViewPager_indicatorPaddingBottom, 0);
        typedArray.recycle();
    }

    /**
     * 初始化控件
     */
    private void init() {
        View view;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (mIsOpenMzEffect) {
            view = inflater.inflate(R.layout.mz_banner_effect_layout, this, true);
        } else {
            view = inflater.inflate(R.layout.mz_banner_normal_layout, this, true);
        }
        mIndicatorContainer = (LinearLayout) view.findViewById(R.id.banner_indicator_container);
        mViewPager = (CustomViewPager) view.findViewById(R.id.mzbanner_vp);
        mViewPager.setOffscreenPageLimit(4);
        mMZModePadding = dpToPx(30);
        // 初始化Scroller
        initViewPagerScroller();
        // indicator对齐方式处理
        if (mIndicatorAlign == 0) {
            setIndicatorAlign(IndicatorAlign.LEFT);
        } else if (mIndicatorAlign == 1) {
            setIndicatorAlign(IndicatorAlign.CENTER);
        } else {
            setIndicatorAlign(IndicatorAlign.RIGHT);
        }
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 通过反射来改变ViewPager默认的滑动速度
     */
    private void initViewPagerScroller() {
        Field mScroller;
        try {
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mViewPageScroller = new ViewPagerScroller(mViewPager.getContext());
            mScroller.set(mViewPager, mViewPageScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化指示器
     */
    private void initIndicator() {
        mIndicatorContainer.removeAllViews();
        mIndicators.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            if (mIndicatorAlign == IndicatorAlign.LEFT.ordinal()) {
                if (i == 0) {
                    int paddingLeft = mIsOpenMzEffect ? mIndicatorPaddingLeft + mMZModePadding :
                            mIndicatorPaddingLeft;
                    imageView.setPadding(paddingLeft + 6, 0, 6, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }
            } else if (mIndicatorAlign == IndicatorAlign.RIGHT.ordinal()) {
                if (i == mDatas.size() - 1) {
                    int paddingRight = mIsOpenMzEffect ? mMZModePadding + mIndicatorPaddingRight :
                            mIndicatorPaddingRight;
                    imageView.setPadding(6, 0, paddingRight, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }
            } else {
                imageView.setPadding(6, 0, 6, 0);
            }

            if (i == (mCurrentItem % mDatas.size())) {
                imageView.setImageResource(mIndicatorRes[1]);
            } else {
                imageView.setImageResource(mIndicatorRes[0]);
            }

            mIndicators.add(imageView);
            mIndicatorContainer.addView(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 如果不可循环播放
        if (!mIsCanLoop) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:

                int paddingHorizon = mViewPager.getLeft();
                float touchX = ev.getRawX();
                // 魅族模式( paddingHorizon <= touchX <= screenWidth - paddingHorizon)
                if (touchX >= paddingHorizon && touchX < getScreenWidth(getContext())
                        - paddingHorizon) {
                    mIsAutoPlay = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsAutoPlay = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 获取屏幕宽度
     *
     * @return the screen width
     */
    private static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 自动滚动核心代码
     */
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsAutoPlay) {
                mCurrentItem = mViewPager.getCurrentItem();
                mCurrentItem++;
                if (mCurrentItem == mAdapter.getCount() - 1) {
                    mCurrentItem = 0;
                    // 立即滚动到指定位置
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    mHandler.postDelayed(this, mDelayedTime);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    mHandler.postDelayed(this, mDelayedTime);
                }
            } else {
                mHandler.postDelayed(this, mDelayedTime);
            }
        }
    };

    /**
     * 开始轮播
     */
    public void start() {
        if (mAdapter == null) {
            return;
        }
        if (mDatas.size() == 0) {
            return;
        }
        if (mIsCanLoop) {
            mIsAutoPlay = true;
            mHandler.postDelayed(mRunnable, mDelayedTime);
        }
    }

    /**
     * 停止轮播
     */
    public void pause() {
        mIsAutoPlay = false;
        mHandler.removeCallbacks(mRunnable);
    }

    /**
     * 设置ViewPager切换时间间隔
     *
     * @param delayedTime 时间间隔
     */
    public void setDelayedTime(int delayedTime) {
        this.mDelayedTime = delayedTime;
    }

    public void addPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    /**
     * 设置点击时间监听
     *
     * @param pageClickListener {@link PageClickListener}
     */
    public void setOnPageClickListener(PageClickListener pageClickListener) {
        this.mPageClickListener = pageClickListener;
    }

    /**
     * 是否显示indicator
     *
     * @param visible true 显示，false 不显示
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) {
            mIndicatorContainer.setVisibility(VISIBLE);
        } else {
            mIndicatorContainer.setVisibility(GONE);
        }
    }

    /**
     * 设置indicator的两种不同状态图片
     *
     * @param unSelectRes 未选中图片
     * @param selectRes   选中图片
     */
    public void setIndicatorRes(@DrawableRes int unSelectRes, @DrawableRes int selectRes) {
        mIndicatorRes[0] = unSelectRes;
        mIndicatorRes[1] = selectRes;
    }

    /**
     * 设置indicator的对齐方式
     *
     * @param indicatorAlign 对齐方式
     */
    public void setIndicatorAlign(IndicatorAlign indicatorAlign) {
        mIndicatorAlign = indicatorAlign.ordinal();
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        if (indicatorAlign == IndicatorAlign.LEFT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (indicatorAlign == IndicatorAlign.RIGHT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
        layoutParams.setMargins(0, mIndicatorPaddingTop, 0, mIndicatorPaddingBottom);
        mIndicatorContainer.setLayoutParams(layoutParams);
    }

    public void setDuration(int duration) {
        mViewPageScroller.setDuration(duration);
    }

    public void getDuration() {
        mViewPageScroller.getDuration();
    }

    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mViewPageScroller.setIsUseDefaultDuration(useDefaultDuration);
    }

    /**
     * 获取ViewPager
     *
     * @return {@link ViewPager}
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }


    public void setPages(List<T> datas, ViewHolderCreator viewHolderCreator) {
        if (datas == null || viewHolderCreator == null) {
            return;
        }
        mDatas = datas;
        // 停止播放
        pause();
        // 数据长度小于3的处理（即魅族模式兼容处理）
        if (datas.size() < 3) {
            mIsOpenMzEffect = false;// 关闭魅族模式
            MarginLayoutParams layoutParams = (MarginLayoutParams) mViewPager.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            mViewPager.setLayoutParams(layoutParams);
            // todo 了解该函数的含义
            setClipChildren(true);
            mViewPager.setClipChildren(true);
        }
        // todo deal with diff effect
        dealWithDiffEffect();
        // 初始化indicator
        initIndicator();

        mAdapter = new RandyPagerAdapter(datas, viewHolderCreator, mIsCanLoop);
        mAdapter.setUpViewPager(mViewPager);
        mAdapter.setPageClickListener(mPageClickListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                int realPosition = position % mIndicators.size();
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(realPosition, positionOffset,
                            positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                int realSelectPosition = mCurrentItem % mIndicators.size();
                for (int i = 0; i < mDatas.size(); i++) {
                    if (i == realSelectPosition) {
                        mIndicators.get(i).setImageResource(mIndicatorRes[1]);
                    } else {
                        mIndicators.get(i).setImageResource(mIndicatorRes[0]);
                    }
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(realSelectPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mIsAutoPlay = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mIsAutoPlay = true;
                        break;
                    default:
                        mIsAutoPlay = true;
                        break;
                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });

    }

    /**
     * 不同显示效果处理（其实就是设置不同的page transformer）
     */
    private void dealWithDiffEffect() {
        if (mIsOpenMzEffect) {
            // 魅族效果
            if (mIsMiddlePageCover) {
                // 这种效果最好改变一下childView的绘制顺序
                mViewPager.setPageTransformer(true, new CoverModeTransformer(mViewPager));
            } else {
                mViewPager.setPageTransformer(false, new ScaleYTransformer());
            }
        }
    }

    /**
     * 由于ViewPager 默认的切换速度有点快，因此用一个Scroller 来控制切换的速度
     * 而实际上ViewPager 切换本来就是用的Scroller来做的，因此我们可以通过反射来
     * 获取取到ViewPager 的 mScroller 属性，然后替换成我们自己的Scroller
     */
    public static class ViewPagerScroller extends Scroller {
        // ViewPager 默认的最大值为600，这里将默认值设置成800
        private int mDuration = 800;
        private boolean mIsUseDefaultDuration = false;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? mDuration : duration);
        }

        public void setDuration(int mDuration) {
            this.mDuration = mDuration;
        }

        public boolean isUseDefaultDuration() {
            return mIsUseDefaultDuration;
        }

        public void setIsUseDefaultDuration(boolean isUseDefaultDuration) {
            this.mIsUseDefaultDuration = isUseDefaultDuration;
        }
    }

    /**
     * 点击事件监听
     */
    public interface PageClickListener {
        void onPageClick(View view, int position);
    }

    public static class RandyPagerAdapter<T> extends PagerAdapter {
        private List<T> mDatas;
        private ViewHolderCreator mViewHolderCreator;
        private ViewPager mViewPager;
        private boolean canLoop;

        private PageClickListener mPageClickListener;
        private final int mLooperCountFactor = 500;

        public RandyPagerAdapter(List<T> datas, ViewHolderCreator mViewHolderCreator, boolean canLoop) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            // 无限循环
            // mDatas.add(datas.get(datas.size()-1));// 加入最后一个
            // mDatas.add(datas.get(0));//在最后加入最前面一个

            for (T t : datas) {
                mDatas.add(t);
            }
            this.mViewHolderCreator = mViewHolderCreator;
            this.canLoop = canLoop;
        }

        public void setPageClickListener(PageClickListener pageClickListener) {
            this.mPageClickListener = pageClickListener;
        }

        /**
         * 初始化ViewPager设置
         *
         * @param viewPager 待初始化的viewPager
         */
        public void setUpViewPager(ViewPager viewPager) {
            mViewPager = viewPager;
            mViewPager.setAdapter(this);
            mViewPager.getAdapter().notifyDataSetChanged();
            int currentItem = canLoop ? getStartSelectItem() : 0;
            mViewPager.setCurrentItem(currentItem);
        }

        private int getStartSelectItem() {
            int currentItem = getRealCount() * mLooperCountFactor / 2;
            if (currentItem % getRealCount() == 0) {
                return currentItem;
            }
            while (currentItem % getRealCount() != 0) {
                currentItem++;
            }
            return currentItem;
        }

        public void setDatas(List<T> datas) {
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            // 如果getCount 的返回值为Integer.MAX_VALUE 的话，
            // 那么在setCurrentItem的时候会ANR(除了在onCreate 调用之外)
            return canLoop ? getRealCount() * mLooperCountFactor : getRealCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getView(position, container);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            // 轮播模式才执行
            if (canLoop) {
                int position = mViewPager.getCurrentItem();
                if (position == getCount() - 1) {
                    position = 0;
                    setCurrentItem(position);
                }
            }
        }

        private void setCurrentItem(int position) {
            try {
                mViewPager.setCurrentItem(position);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        private int getRealCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        private View getView(int position, ViewGroup container) {
            final int realPosition = position % getRealCount();
            ViewHolder holder;
            holder = mViewHolderCreator.createViewHolder();
            if (holder == null) {
                throw new RuntimeException("can not return a null holder");
            }
            View view = holder.createView(container.getContext());

            if (mDatas != null && mDatas.size() > 0) {
                holder.onBind(container.getContext(), position, mDatas.get(realPosition));
            }
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPageClickListener != null) {
                        mPageClickListener.onPageClick(v, realPosition);
                    }
                }
            });

            return view;
        }
    }

    public interface ViewHolderCreator<VH extends ViewHolder> {
        /**
         * 创建ViewHolder
         */
        VH createViewHolder();
    }

    public interface ViewHolder<T> {
        /**
         * 创建View
         *
         * @param context context
         * @return view
         */
        View createView(Context context);

        /**
         * 绑定数据
         *
         * @param context  context
         * @param position 绑定的位置
         * @param data     绑定的数据
         */
        void onBind(Context context, int position, T data);
    }
}
