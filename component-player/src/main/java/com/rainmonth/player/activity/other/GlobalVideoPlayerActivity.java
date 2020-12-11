package com.rainmonth.player.activity.other;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.player.R;

/**
 * 全局悬浮窗播放
 *
 * @author 张豪成
 * @date 2019-11-01 15:13
 */
public class GlobalVideoPlayerActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_global_player_play;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
