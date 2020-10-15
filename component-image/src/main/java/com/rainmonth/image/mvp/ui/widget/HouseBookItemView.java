package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.widgets.test.BorderRoundItemView;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

/**
 * 1、支持不规则边框
 * 2、支持不规则圆角
 * 3、支持设置宽高比
 *
 * @author RandyZhang
 * @date 2020/10/13 3:39 PM
 */
public class HouseBookItemView extends BorderRoundItemView<SubscribeBean> {

    private int[] demoImageId = {R.drawable.sample_cover_1, R.drawable.sample_cover_2, R.drawable.sample_cover_3,};

    private Context mContext;

    public HouseBookItemView(@NonNull Context context) {
        this(context, null);
    }

    public HouseBookItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseBookItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        Log.d("Randy", "itemView init");
//        View.inflate(context, R.layout.image_book_shelf_item_view, this);

    }

    @Override
    public void update(SubscribeBean subscribeBean) {
        super.update(subscribeBean);
        if (subscribeBean != null) {
            if (subscribeBean.isRedirectInfo) {
                showCover(R.drawable.book_shelf_more);
                handleCount("", false);
            } else {
                showCover(demoImageId[subscribeBean.index % 3]);
                handleCount(String.valueOf(subscribeBean.index + 1), true);
            }
        }
    }
}
