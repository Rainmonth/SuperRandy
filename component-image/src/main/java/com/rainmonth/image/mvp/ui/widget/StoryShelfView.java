package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 听书书架View
 *
 * @author RandyZhang
 * @date 2020/10/14 5:10 PM
 */
public class StoryShelfView extends ConstraintLayout {

    private TextView tvLabel;
    private RecyclerView rvSubscribeList;
    private LinearLayout llIndicatorContainer;

    private Context mContext;

    private List<SubscribeBean> mSubscribeList = new ArrayList<>();
    private List<List<SubscribeBean>> mPagedSubscribeList = new ArrayList<>();

    private StoryShelfListAdapter mAdapter;
    private boolean mNeedInterceptTouchEvent = false;
    private boolean mNeedSupportSlideViewMore = false;
    private int mPageNum = 1;
    private int mCurrentIndex = 0;


    public StoryShelfView(Context context) {
        this(context, null);
    }

    public StoryShelfView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoryShelfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View.inflate(mContext, R.layout.image_story_shelf_view, this);
        tvLabel = findViewById(R.id.tv_label);
        rvSubscribeList = findViewById(R.id.rv_subscribe_list);
        llIndicatorContainer = findViewById(R.id.ll_indicator_container);

        Log.d("Randy", "shelfView init");
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
        rvSubscribeList.getLayoutParams().height = DensityUtils.dip2px(mContext, 136);

        generatePages(mPageNum);
        generateIndicators(mPageNum);
    }

    private void generatePages(int pageNum) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvSubscribeList.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();

        mAdapter = new StoryShelfListAdapter(mContext, mPagedSubscribeList);

        snapHelper.attachToRecyclerView(rvSubscribeList);
        rvSubscribeList.setAdapter(mAdapter);
    }

    private void generateIndicators(int pageNum) {
        llIndicatorContainer.removeAllViews();
        if (pageNum == 1) {
            hideIndicator();
            return;
        } else {
            showIndicator();
        }
        for (int i = 0; i < pageNum; i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.image_house_book_indicator_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(mContext, 8), DensityUtils.dip2px(mContext, 8));
            params.leftMargin = DensityUtils.dip2px(mContext, 3);
            params.rightMargin = DensityUtils.dip2px(mContext, 3);
            llIndicatorContainer.addView(view, params);
        }
    }

    private int getPageNum(int size) {
        int pageNum;
        if (size > 0 && size <= 3) {
            pageNum = 1;
            mPagedSubscribeList.add(mSubscribeList);
        } else if (size > 3 && size <= 6) {
            pageNum = 2;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 3));
            mPagedSubscribeList.add(mSubscribeList.subList(3, size));
        } else if (size > 6 && size <= 9) {
            pageNum = 3;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 3));
            mPagedSubscribeList.add(mSubscribeList.subList(3, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, size));
        } else if (size > 9 && size <= 12) {
            pageNum = 4;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 3));
            mPagedSubscribeList.add(mSubscribeList.subList(3, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, 9));
            mPagedSubscribeList.add(mSubscribeList.subList(9, size));
        } else {
            pageNum = 5;
            mPagedSubscribeList.add(mSubscribeList.subList(0, 3));
            mPagedSubscribeList.add(mSubscribeList.subList(3, 6));
            mPagedSubscribeList.add(mSubscribeList.subList(6, 9));
            mPagedSubscribeList.add(mSubscribeList.subList(9, 12));
            mPagedSubscribeList.add(mSubscribeList.subList(12, Math.min(size, 15)));
        }

        return pageNum;
    }

    public void showIndicator() {
        llIndicatorContainer.setVisibility(VISIBLE);
    }

    public void hideIndicator() {
        llIndicatorContainer.setVisibility(GONE);
    }

    private void onLabelClick() {
        ToastUtils.showShortToast(mContext, "查看更多内容");
    }

    public void onPageItemClick(int pageNum, SubscribeBean subscribeBean) {
        if (subscribeBean != null) {
            if (subscribeBean.isRedirectInfo) {
                ToastUtils.showShortToast(mContext, "第" + pageNum + "页，跳转item " + subscribeBean.index % 6 + " clicked!");
            } else {
                ToastUtils.showShortToast(mContext, "第" + pageNum + "页，item " + subscribeBean.index % 6 + " clicked!");
            }
        }
    }

    private static class StoryShelfListAdapter extends RecyclerView.Adapter<ShelfPageViewHolder> {

        private List<List<SubscribeBean>> mData = new ArrayList<>();

        private Context mContext;

        public StoryShelfListAdapter(Context context, List<List<SubscribeBean>> list) {
            mContext = context;
            if (list != null) {
                mData = list;
            }
        }

        @NonNull
        @Override
        public ShelfPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.image_story_shelf_page_item_view, parent, false);
            return new ShelfPageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ShelfPageViewHolder holder, int position) {
            holder.storyShelfPageView.update(position, mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private static class ShelfPageViewHolder extends RecyclerView.ViewHolder {
        StoryShelfPageView storyShelfPageView;

        public ShelfPageViewHolder(@NonNull View itemView) {
            super(itemView);
            storyShelfPageView = itemView.findViewById(R.id.story_shelf_page_view);
        }
    }
}
