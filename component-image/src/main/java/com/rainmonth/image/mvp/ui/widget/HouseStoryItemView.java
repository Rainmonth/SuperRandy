package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.widgets.test.BorderRoundItemView;
import com.rainmonth.common.widgets.test.RandyPercentageLayout;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.SubscribeBean;

/**
 * @author RandyZhang
 * @date 2020/10/13 3:38 PM
 */
public class HouseStoryItemView extends RandyPercentageLayout {
    private int[] demoImageId = {R.drawable.sample_cover_1, R.drawable.sample_cover_2, R.drawable.sample_cover_3,};

    private Context mContext;
    BorderRoundItemView borderRoundItemView;
    FrameLayout flRedirectContainer;

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
        View.inflate(context, R.layout.image_story_shelf_item_view, this);

        borderRoundItemView = findViewById(R.id.border_round_item_view);
        flRedirectContainer = findViewById(R.id.fl_redirect_container);
    }

    public void update(SubscribeBean subscribeBean) {
        if (subscribeBean != null) {
            if (subscribeBean.isRedirectInfo) {
                handleRedirectInfo(subscribeBean);
            } else {
                handleSubscribeInfo(subscribeBean);
            }
        }
    }

    public void update(RecentPlayBean recentPlayBean) {
        borderRoundItemView.setVisibility(VISIBLE);
        borderRoundItemView.handleCover(demoImageId[recentPlayBean.index % 3]);
        borderRoundItemView.handleCount(true, (recentPlayBean.index + 1) + "集");

        flRedirectContainer.setVisibility(GONE);
    }

    private void handleSubscribeInfo(SubscribeBean subscribeBean) {
        if (subscribeBean != null && !subscribeBean.isRedirectInfo) {
            borderRoundItemView.setVisibility(VISIBLE);
            borderRoundItemView.handleCover(demoImageId[subscribeBean.index % 3]);
            borderRoundItemView.handleCount(true, (subscribeBean.index + 1) + "集");

            flRedirectContainer.setVisibility(GONE);
        }
    }

    private void handleRedirectInfo(SubscribeBean subscribeBean) {
        if (subscribeBean != null && subscribeBean.isRedirectInfo) {
            borderRoundItemView.setVisibility(GONE);
            flRedirectContainer.setVisibility(VISIBLE);
        }
    }
}
