package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RandyZhang
 * @date 2020/10/13 7:42 PM
 */
public class BookShelfView extends ConstraintLayout {
    private static final String TAG = BookShelfView.class.getSimpleName();

    TextView tvLabel;
    ViewPager vpSubscribeList;
    LinearLayout llIndicatorContainer;
    private Context mContext;

    private List<SubscribeBean> mSubscribeList = new ArrayList<>();
    private List<List<SubscribeBean>> mPagedSubscribeList = new ArrayList<>();
    private int mPageNum = 1;

    private int mCurrentIndex = 0;

    private ViewPagerAdapter mAdapter;

    private boolean mNeedInterceptTouchEvent = false;
    private boolean mNeedSupportSlideViewMore = false;

    public BookShelfView(Context context) {
        this(context, null);
    }

    public BookShelfView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookShelfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View.inflate(mContext, R.layout.image_book_shelf_view, this);

        tvLabel = findViewById(R.id.tv_label);
        vpSubscribeList = findViewById(R.id.vp_subscribe_list);
        llIndicatorContainer = findViewById(R.id.ll_indicator_container);

        Log.d("Randy", "shelfView init");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void update(List<SubscribeBean> subscribeList) {
        this.mSubscribeList = subscribeList;
        if (subscribeList == null) {
            return;
        }

        int size = subscribeList.size();
        if (size == 0) {
            return;
        }
        // 超过 31 个 需要支持 左滑查看更多
        mNeedSupportSlideViewMore = size > 31;
        mNeedInterceptTouchEvent = size > 5;

        if (mNeedInterceptTouchEvent) { // 不允许父View拦截时间
            requestDisallowInterceptTouchEvent(true);
        } else {

        }

        mPageNum = getPageNum(size);
        if (mPageNum == 1 && size < 3) {
            vpSubscribeList.getLayoutParams().height = DensityUtils.dip2px(mContext, 136);
        } else {
            vpSubscribeList.getLayoutParams().height = DensityUtils.dip2px(mContext, 288);
        }
        List<View> pageViewList = new ArrayList<>();
        llIndicatorContainer.removeAllViews();
        for (int i = 0; i < mPageNum; i++) {
            BookShelfPageView bookShelfPageView = new BookShelfPageView(mContext);
            bookShelfPageView.update(i, mPagedSubscribeList.get(i));
            pageViewList.add(bookShelfPageView);

            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.image_house_book_indicator_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(mContext, 8), DensityUtils.dip2px(mContext, 8));
            params.leftMargin = DensityUtils.dip2px(mContext, 3);
            params.rightMargin = DensityUtils.dip2px(mContext, 3);
            llIndicatorContainer.addView(view, params);
        }

        mCurrentIndex = 0;
        llIndicatorContainer.getChildAt(0).setSelected(true);


        mAdapter = new ViewPagerAdapter(pageViewList);
        vpSubscribeList.setAdapter(mAdapter);

        vpSubscribeList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCurrentIndex != position) {
                    llIndicatorContainer.getChildAt(mCurrentIndex).setSelected(false);
                    llIndicatorContainer.getChildAt(position).setSelected(true);
                    mCurrentIndex = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (mPageNum == 1) { // 只有一页
            hideIndicator();
        } else { // 找过一页
            showIndicator();
        }

    }

    private int getPageNum(int size) {
        int pageNum;
        if (size > 0 && size <= 6) {
            pageNum = 1;
            mPagedSubscribeList.add(mSubscribeList);
        } else if (size > 6 && size <= 12) {
            pageNum = 2;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, size));
        } else if (size > 12 && size <= 18) {
            pageNum = 3;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, 12));
            mPagedSubscribeList.add(mSubscribeList.subList(12, size));
        } else if (size > 18 && size <= 24) {
            pageNum = 4;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, 12));
            mPagedSubscribeList.add(mSubscribeList.subList(12, 18));
            mPagedSubscribeList.add(mSubscribeList.subList(18, size));
        } else {
            pageNum = 5;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, 12));
            mPagedSubscribeList.add(mSubscribeList.subList(12, 18));
            mPagedSubscribeList.add(mSubscribeList.subList(18, 24));
            mPagedSubscribeList.add(mSubscribeList.subList(24, Math.min(size, 30)));
        }

        return pageNum;
    }

    public void showIndicator() {
        llIndicatorContainer.setVisibility(VISIBLE);
    }

    public void hideIndicator() {
        llIndicatorContainer.setVisibility(GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public ViewPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return (mViewList.get(position));
        }

        @Override
        public int getCount() {
            if (mViewList == null)
                return 0;
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
