package com.rainmonth.common.widgets.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainmonth.common.R;

import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {
    public String tag = "banner";
    public static final int NOT_INDICATOR = 0;
    public static final int CIRCLE_INDICATOR = 1;
    public static final int NUM_INDICATOR = 2;
    public static final int NUM_INDICATOR_TITLE = 3;
    public static final int CIRCLE_INDICATOR_TITLE = 4;
    public static final int LEFT = 5;
    public static final int CENTER = 6;
    public static final int RIGHT = 7;
    // 指示器图片外边距
    private int mIndicatorMargin = 5;
    // 指示器高度
    private int mIndicatorWidth = 8;
    // 指示器宽度
    private int mIndicatorHeight = 8;
    // 选中指示器图片
    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    // 未选中指示器图片
    private int mIndicatorUnselectedResId = R.drawable.white_radius;
    // 指示器东湖
    private int mIndicatorAnimatorResId = R.anim.scale_with_alpha;
    // 指示器反转动画
    private int mIndicatorAnimatorReverseResId = 0;
    // banner样式，默认是圆形指示器
    private int bannerStyle = CIRCLE_INDICATOR;
    // banner图片列表长度
    private int totalCount;
    // 当前展示的item
    private int currentItem;
    // 延迟时间
    private int delayTime = 2000;
    // 指示器位置
    private int gravity = -1;
    // 是否自动播放
    private boolean isAutoPlay = true;
    // banner图屁啊列表
    private List<ImageView> imageViews;
    // 指示器图片列表
    private List<ImageView> indicatorImages;
    // context
    private Context context;
    // viewPager实例
    private ViewPager viewPager;
    // 指示器容器
    private LinearLayout indicator;
    // 处理自动播放的handler
    private Handler handler = new Handler();
    // banner点击事件监听器
    private OnBannerClickListener listener;
    // 图片加载监听器
    private OnLoadImageListener imageListener;
    // banner标题数组
    private String[] titles;
    // 显示banner标题容器
    private TextView bannerTitle;
    // 数字指示器容器
    private TextView numIndicator;
    // viewPager上次滑动的位置
    private int lastPosition = 1;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        imageViews = new ArrayList<ImageView>();
        indicatorImages = new ArrayList<ImageView>();
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        imageViews.clear();
        View view = LayoutInflater.from(context).inflate(R.layout.banner, this, true);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        bannerTitle = (TextView) view.findViewById(R.id.bannerTitle);
        numIndicator = (TextView) view.findViewById(R.id.numIndicator);
        handleTypedArray(context, attrs);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_width, 8);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_height, 8);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_margin, 5);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.Banner_indicator_drawable_selected, R.drawable.gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.Banner_indicator_drawable_unselected, R.drawable.white_radius);
        mIndicatorAnimatorResId = typedArray.getResourceId(R.styleable.Banner_indicator_animator, R.anim.scale_with_alpha);
        mIndicatorAnimatorReverseResId = typedArray.getResourceId(R.styleable.Banner_indicator_animator_reverse, 0);
        typedArray.recycle();
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void setIndicatorGravity(int type) {
        switch (type) {
            case LEFT:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case RIGHT:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
    }

    public void setBannerTitle(String[] titles) {
        this.titles = titles;
        if (bannerStyle == CIRCLE_INDICATOR_TITLE || bannerStyle == NUM_INDICATOR_TITLE) {
            if (titles != null && titles.length > 0) {
                bannerTitle.setVisibility(View.VISIBLE);
                indicator.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else {
                numIndicator.setBackgroundResource(R.drawable.black_background);
            }
        }
    }

    public void setBannerStyle(int bannerStyle) {
        this.bannerStyle = bannerStyle;
        switch (bannerStyle) {
            case CIRCLE_INDICATOR:
                indicator.setVisibility(View.VISIBLE);
                break;
            case NUM_INDICATOR:
                numIndicator.setVisibility(View.VISIBLE);
                numIndicator.setBackgroundResource(R.drawable.black_background);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 10, 10);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                numIndicator.setLayoutParams(layoutParams);
                numIndicator.setPadding(5, 6, 5, 6);
                break;
            case NUM_INDICATOR_TITLE:
                numIndicator.setVisibility(View.VISIBLE);
                break;
            case CIRCLE_INDICATOR_TITLE:
                indicator.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setImages(Object[] imagesUrl) {
        if (setImageArray(imagesUrl, null)) return;
        setData();
    }

    public void setImages(Object[] imagesUrl, OnLoadImageListener imageListener) {
        if (setImageArray(imagesUrl, imageListener)) return;
        setData();
    }

    public void setImages(List<?> imagesUrl) {
        if (setImageList(imagesUrl, null)) return;
        setData();
    }

    public void setImages(List<?> imagesUrl, OnLoadImageListener imageListener) {
        if (setImageList(imagesUrl, imageListener)) return;
        setData();
    }

    private boolean setImageArray(Object[] imagesUrl, OnLoadImageListener imageListener) {
        if (imagesUrl == null || imagesUrl.length <= 0) {
            Log.e(tag, "Please set the images data.");
            return true;
        }
        totalCount = imagesUrl.length;
        createIndicator();
        imageViews.clear();
        for (int i = 0; i <= totalCount + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.CENTER_CROP);
            Object url = null;
            if (i == 0) {
                url = imagesUrl[totalCount - 1];
            } else if (i == totalCount + 1) {
                url = imagesUrl[0];
            } else {
                url = imagesUrl[i - 1];
            }
            imageViews.add(iv);
            if (imageListener != null) {
                imageListener.OnLoadImage(iv, url);
            } else {
                Glide.with(context).load(url).into(iv);
            }
        }
        return false;
    }

    private boolean setImageList(List<?> imagesUrl, OnLoadImageListener imageListener) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            Log.e(tag, "Please set the images data.");
            return true;
        }
        totalCount = imagesUrl.size();
        createIndicator();
        imageViews.clear();
        for (int i = 0; i <= totalCount + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ScaleType.CENTER_CROP);
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(totalCount - 1);
            } else if (i == totalCount + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViews.add(iv);
            if (imageListener != null) {
                imageListener.OnLoadImage(iv, url);
            } else {
                Glide.with(context).load(url).into(iv);
            }
        }
        return false;
    }

    /**
     * 创建指示器
     */
    private void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        for (int i = 0; i < totalCount; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicator.addView(imageView, params);
            indicatorImages.add(imageView);
        }
    }


    /**
     * ViewPager绑定数据
     */
    private void setData() {
        viewPager.setAdapter(new BannerPagerAdapter());
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        currentItem = 1;
        viewPager.addOnPageChangeListener(this);
        if (gravity != -1)
            indicator.setGravity(gravity);
        if (isAutoPlay)
            startAutoPlay();
    }

    public void isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    private void startAutoPlay() {
        isAutoPlay = true;
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                currentItem = currentItem % (totalCount + 1) + 1;
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            } else {
                handler.postDelayed(task, delayTime);
            }
        }
    };

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            final ImageView view = imageViews.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnBannerClick(v, position);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isAutoPlay = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isAutoPlay = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(totalCount, false);
                } else if (viewPager.getCurrentItem() == totalCount + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                currentItem = viewPager.getCurrentItem();
                isAutoPlay = true;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // 更新之前的指示器图片为未选状态
        indicatorImages.get((lastPosition - 1 + totalCount) % totalCount).setImageResource(mIndicatorUnselectedResId);
        // 更新当前的指示器图片为已选状态
        indicatorImages.get((position - 1 + totalCount) % totalCount).setImageResource(mIndicatorSelectedResId);
        lastPosition = position;
        if (position == 0) position = 1;
        switch (bannerStyle) {
            case CIRCLE_INDICATOR:
                break;
            case NUM_INDICATOR:
                if (position > totalCount) {
                    position = totalCount;
                }
                numIndicator.setText(position + "/" + totalCount);
                break;
            case NUM_INDICATOR_TITLE:
                if (position > totalCount) {
                    position = totalCount;
                }
                numIndicator.setText(position + "/" + totalCount);
                if (titles != null && titles.length > 0) {
                    if (position > titles.length) {
                        position = titles.length;
                    }
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
            case CIRCLE_INDICATOR_TITLE:
                if (titles != null && titles.length > 0) {
                    if (position > titles.length) {
                        position = titles.length;
                    }
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
        }

    }


    public void setOnBannerClickListener(OnBannerClickListener listener) {
        this.listener = listener;
    }

    public void setOnBannerImageListener(OnLoadImageListener imageListener) {
        this.imageListener = imageListener;
    }

    public interface OnBannerClickListener {
        void OnBannerClick(View view, int position);
    }

    public interface OnLoadImageListener {
        void OnLoadImage(ImageView view, Object url);
    }
}
