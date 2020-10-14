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

import com.rainmonth.image.R;

import butterknife.BindView;

/**
 * @author RandyZhang
 * @date 2020/10/13 8:10 PM
 */
public class BookShelfRowView extends RelativeLayout {
    @BindView(R.id.book_item_view_1)
    HouseBookItemView bookItemView1;
    @BindView(R.id.book_item_view_2)
    HouseBookItemView bookItemView2;
    @BindView(R.id.book_item_view_3)
    HouseBookItemView bookItemView3;
    @BindView(R.id.ll_item_container)
    LinearLayout llItemContainer;
    @BindView(R.id.iv_book_shelf_bg)
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
//        LayoutInflater.from(context).inflate(R.layout.image_book_shelf_row_view, this);
        Log.d("Randy", "row init");
    }
}
