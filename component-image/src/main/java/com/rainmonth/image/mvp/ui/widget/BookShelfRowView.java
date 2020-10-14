package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

import java.util.List;

import butterknife.BindView;

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

    public void update(List<SubscribeBean> subscribeList) {
        if (subscribeList == null || subscribeList.size() == 0) {
            return;
        }

        int size = subscribeList.size();
        if (size == 1) {
            bookItemView1.setVisibility(VISIBLE);
            bookItemView1.update(subscribeList.get(0));
            bookItemView2.setVisibility(INVISIBLE);
            bookItemView3.setVisibility(INVISIBLE);
        } else if (size == 2) {
            bookItemView1.setVisibility(VISIBLE);
            bookItemView1.update(subscribeList.get(0));
            bookItemView2.setVisibility(VISIBLE);
            bookItemView2.update(subscribeList.get(1));
            bookItemView3.setVisibility(INVISIBLE);
        } else {
            bookItemView1.setVisibility(VISIBLE);
            bookItemView1.update(subscribeList.get(0));
            bookItemView2.setVisibility(VISIBLE);
            bookItemView2.update(subscribeList.get(1));
            bookItemView3.setVisibility(VISIBLE);
            bookItemView3.update(subscribeList.get(2));
        }
    }
}
