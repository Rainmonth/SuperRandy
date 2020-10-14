package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.image.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RandyZhang
 * @date 2020/10/13 7:42 PM
 */
public class BookShelfView extends ConstraintLayout {
    private static final String TAG = BookShelfView.class.getSimpleName();

    TextView tvLabel;
    BookShelfPageView bookShelfPageView;
    LinearLayout llIndicatorContainer;

    private List<BaseBean> mSubscribeList = new ArrayList<>();
    private List<List<BaseBean>> mPagedSubscribeList = new ArrayList<>();
    private int mPageNum = 1;

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
        View.inflate(context, R.layout.image_book_shelf_view, this);

        tvLabel = findViewById(R.id.tv_label);
        bookShelfPageView = findViewById(R.id.book_shelf_page_view);
        llIndicatorContainer = findViewById(R.id.ll_continue_play_container);

        Log.d("Randy", "shelfView init");

    }

    public void update(List<BaseBean> subscribeList) {
        this.mSubscribeList = subscribeList;
        if (subscribeList == null) {
            return;
        }

        int size = subscribeList.size();
        if (size == 0) {
            return;
        }
        // 超过 30 个 需要支持 左滑查看更多
        mNeedSupportSlideViewMore = size > 30;
        mNeedInterceptTouchEvent = size > 5;

        if (mNeedInterceptTouchEvent) { // 不允许父View拦截时间
            requestDisallowInterceptTouchEvent(true);
        } else {

        }

        mPageNum = getPageNum(size);

        if (mPageNum == 1) { // 只有一页
            hideIndicator();
        } else { // 找过一页
            showIndicator();
        }

    }

    private int getPageNum(int size) {
        int pageNum;
        if (size > 0 && size <= 5) {
            pageNum = 1;
            mPagedSubscribeList.add(mSubscribeList);
        } else if (size > 5 && size <= 11) {
            pageNum = 2;
        } else if (size > 11 && size <= 17) {
            pageNum = 3;
        } else if (size > 17 && size <= 23) {
            pageNum = 4;
        } else if (size > 23 && size <= 29) {
            pageNum = 5;
        } else {
            pageNum = 5;
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
}
