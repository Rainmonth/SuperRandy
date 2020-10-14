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
 * @author RandyZhang
 * @date 2020/10/13 8:10 PM
 */
public class BookShelfRowView extends RelativeLayout {
    HouseBookItemView bookItemView1;
    HouseBookItemView bookItemView2;
    HouseBookItemView bookItemView3;
    LinearLayout llItemContainer;
    ImageView ivBookShelfBg;

    private int mBelongPage;
    BookShelfPageView.OnPageItemClickListener mPageListener;

    private View.OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPageListener != null) {
                mPageListener.onPageItemClick(mBelongPage, (SubscribeBean) v.getTag());
            }
        }
    };

    public BookShelfRowView(@NonNull Context context) {
        this(context, null);
    }

    public BookShelfRowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookShelfRowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.image_book_shelf_row_view, this);
        bookItemView1 = findViewById(R.id.book_item_view_1);
        bookItemView2 = findViewById(R.id.book_item_view_2);
        bookItemView3 = findViewById(R.id.book_item_view_3);
//        LayoutInflater.from(context).inflate(R.layout.image_book_shelf_row_view, this);
        Log.d("Randy", "row init");
    }

    public void update(int pageIndex, List<SubscribeBean> subscribeList) {
        mBelongPage = pageIndex;
        if (subscribeList == null || subscribeList.size() == 0) {
            hideBookItem(bookItemView1);
            hideBookItem(bookItemView2);
            hideBookItem(bookItemView3);
            return;
        }

        int size = subscribeList.size();
        if (size == 1) {
            showBookItem(bookItemView1, subscribeList.get(0));
            hideBookItem(bookItemView2);
            hideBookItem(bookItemView3);
        } else if (size == 2) {
            showBookItem(bookItemView1, subscribeList.get(0));
            showBookItem(bookItemView2, subscribeList.get(1));
            hideBookItem(bookItemView3);
        } else {
            showBookItem(bookItemView1, subscribeList.get(0));
            showBookItem(bookItemView2, subscribeList.get(1));
            showBookItem(bookItemView3, subscribeList.get(2));
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

    public void setOnPageItemClickListener(BookShelfPageView.OnPageItemClickListener listener) {
        this.mPageListener = listener;
    }
}
