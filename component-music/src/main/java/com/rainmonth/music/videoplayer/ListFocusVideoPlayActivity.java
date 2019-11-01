package com.rainmonth.music.videoplayer;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 视频列表焦点播放
 * 功能：
 * 1、列表滑动时取消视频播放，列表idle状态是播放当前焦点的视频
 * 2、列表预览页的图片的提取
 *
 * @author 张豪成
 * @date 2019-11-01 14:55
 */
public class ListFocusVideoPlayActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_list_focus_video_play;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
