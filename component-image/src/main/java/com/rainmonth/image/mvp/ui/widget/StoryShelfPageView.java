package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

import java.util.List;

/**
 * 一页最多两行
 *
 * @author RandyZhang
 * @date 2020/10/13 8:10 PM
 */
public class StoryShelfPageView extends RelativeLayout {
    HouseBookItemView storyItemView1;
    HouseBookItemView storyItemView2;
    HouseBookItemView storyItemView3;
    LinearLayout llItemContainer;
    ImageView ivBookShelfBg;

    private int mBelongPage;

    OnPageItemClickListener mPageListener;

    private View.OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPageListener != null) {
                mPageListener.onPageItemClick(mBelongPage, (SubscribeBean) v.getTag());
            }
        }
    };

    public StoryShelfPageView(@NonNull Context context) {
        this(context, null);
    }

    public StoryShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoryShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.image_story_shelf_page_view, this);
        storyItemView1 = findViewById(R.id.book_item_view_1);
        storyItemView2 = findViewById(R.id.book_item_view_2);
        storyItemView3 = findViewById(R.id.book_item_view_3);
        
        Log.d("Randy", "storyShelfPage init");
    }

    public void update(int pageIndex, List<SubscribeBean> subscribeList) {
        mBelongPage = pageIndex;
        if (subscribeList == null || subscribeList.size() == 0) {
            hideBookItem(storyItemView1);
            hideBookItem(storyItemView2);
            hideBookItem(storyItemView3);
            return;
        }

        int size = subscribeList.size();
        if (size == 1) {
            showBookItem(storyItemView1, subscribeList.get(0));
            hideBookItem(storyItemView2);
            hideBookItem(storyItemView3);
        } else if (size == 2) {
            showBookItem(storyItemView1, subscribeList.get(0));
            showBookItem(storyItemView2, subscribeList.get(1));
            hideBookItem(storyItemView3);
        } else {
            showBookItem(storyItemView1, subscribeList.get(0));
            showBookItem(storyItemView2, subscribeList.get(1));
            showBookItem(storyItemView3, subscribeList.get(2));
        }
    }

    private void showBookItem(HouseBookItemView view, SubscribeBean subscribeBean) {
        view.setVisibility(VISIBLE);
        view.update(subscribeBean);
        view.setTag(subscribeBean);
        view.setOnClickListener(listener);
    }

    private void hideBookItem(HouseBookItemView view) {
        view.setVisibility(INVISIBLE);
        view.setOnClickListener(null);
        view.setTag(null);
    }

    public void setOnPageItemClickListener(OnPageItemClickListener listener) {
//        this.mOnPageItemClickListener = listener;
//        shelfRowOne.setOnPageItemClickListener(mOnPageItemClickListener);
//        shelfRowTwo.setOnPageItemClickListener(mOnPageItemClickListener);
    }

    public interface OnPageItemClickListener {
        void onPageItemClick(int pageNum, SubscribeBean subscribeBean);
    }
}
