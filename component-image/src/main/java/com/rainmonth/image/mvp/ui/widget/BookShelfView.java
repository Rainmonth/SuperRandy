package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.rainmonth.image.R;

/**
 * @author RandyZhang
 * @date 2020/10/13 7:42 PM
 */
public class BookShelfView extends ConstraintLayout {
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
    }

}
