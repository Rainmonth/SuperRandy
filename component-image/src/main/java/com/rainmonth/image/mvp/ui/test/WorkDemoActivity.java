package com.rainmonth.image.mvp.ui.test;

import android.os.Bundle;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.ui.widget.BookShelfView;
import com.rainmonth.image.mvp.ui.widget.HouseBookItemView;
import com.rainmonth.image.mvp.ui.widget.HouseStoryItemView;
import com.rainmonth.image.mvp.ui.widget.RecentPlayView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author RandyZhang
 * @date 2020/9/30 4:36 PM
 */
public class WorkDemoActivity extends BaseActivity {

    @BindView(R.id.recent_play_view)
    RecentPlayView recentPlayView;
    @BindView(R.id.book_shelf_view)
    BookShelfView bookShelfView;
    @BindView(R.id.book_item_view)
    HouseBookItemView bookItemView;
    @BindView(R.id.story_item_view)
    HouseStoryItemView storyItemView;

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_work_demo;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
