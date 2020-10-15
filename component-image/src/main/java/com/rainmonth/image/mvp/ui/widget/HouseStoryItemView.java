package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.widgets.test.BorderRoundItemView;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

/**
 * @author RandyZhang
 * @date 2020/10/13 3:38 PM
 */
public class HouseStoryItemView extends BorderRoundItemView {
    private int[] demoImageId = {R.drawable.sample_cover_1, R.drawable.sample_cover_2, R.drawable.sample_cover_3,};

    private Context mContext;


    public HouseStoryItemView(@NonNull Context context) {
        this(context, null);
    }

    public HouseStoryItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseStoryItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        Log.d("Randy", "HouseStoryItemView init");
//        View.inflate(context, R.layout.image_book_shelf_item_view, this);

    }

    public void update(SubscribeBean subscribeBean) {
        if (subscribeBean != null) {
            if (subscribeBean.isRedirectInfo) {
                handleCover(R.drawable.book_shelf_more);
                handleCount(false, "");
            } else {
                handleCover(demoImageId[subscribeBean.index % 3]);
                handleCount(true, String.valueOf(subscribeBean.index + 1));
            }
        }
    }
}
