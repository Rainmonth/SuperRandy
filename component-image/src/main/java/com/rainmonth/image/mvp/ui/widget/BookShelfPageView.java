package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

import java.util.List;

/**
 * 一页最多两行
 *
 * @author RandyZhang
 * @date 2020/10/13 8:10 PM
 */
public class BookShelfPageView extends FrameLayout {
    BookShelfRowView shelfRowOne;
    BookShelfRowView shelfRowTwo;

    public BookShelfPageView(@NonNull Context context) {
        this(context, null);
    }

    public BookShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookShelfPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.image_book_shelf_page_view, this);
        shelfRowOne = findViewById(R.id.shelf_row_one);
        shelfRowTwo = findViewById(R.id.shelf_row_two);
        Log.d("Randy", "shelfPage init");
    }

    public void update(int pageIndex, List<SubscribeBean> subscribeList) {
        if (subscribeList == null || subscribeList.size() == 0) {
            setVisibility(GONE);
            return;
        }
        int size = subscribeList.size();
        if (pageIndex == 0) {
            shelfRowOne.setVisibility(VISIBLE);
            if (size >= 3) {
                shelfRowOne.update(subscribeList.subList(0, 3));
                shelfRowTwo.update(subscribeList.subList(3, size));
                shelfRowTwo.setVisibility(VISIBLE);
            } else {
                shelfRowOne.update(subscribeList.subList(0, size));
                shelfRowTwo.setVisibility(GONE);
            }
        } else {
            shelfRowOne.update(subscribeList.subList(0, 3));
            shelfRowOne.update(subscribeList.subList(3, size));
            shelfRowOne.setVisibility(VISIBLE);
            shelfRowTwo.setVisibility(VISIBLE);
        }
    }
}
