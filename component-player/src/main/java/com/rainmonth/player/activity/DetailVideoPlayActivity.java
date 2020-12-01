package com.rainmonth.player.activity;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.player.R;

/**
 * 详情页视频播放
 * 说明：播放视窗在底部，底部可以展示其他内容（如视频列表、视频详情等）
 * 功能：
 * 1、顶部播放视图支持固定；
 * 2、顶部播放视图支持全屏；
 * @author 张豪成
 * @date 2019-11-01 14:46
 */
public class DetailVideoPlayActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_detail_player_play;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
