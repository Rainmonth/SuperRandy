package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.image.R;

import butterknife.BindView;

/**
 * 一页最多两行
 *
 * @author RandyZhang
 * @date 2020/10/13 8:10 PM
 */
public class BookShelfPageView extends FrameLayout {
    @BindView(R.id.shelf_row_one)
    BookShelfRowView shelfRowOne;
    @BindView(R.id.shelf_row_two)
    BookShelfRowView shelfRowTwo;

    public BookShelfPageView(@NonNull Context context) {
        super(context);
    }

    public BookShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BookShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.image_book_shelf_page_view, this);
    }
}
