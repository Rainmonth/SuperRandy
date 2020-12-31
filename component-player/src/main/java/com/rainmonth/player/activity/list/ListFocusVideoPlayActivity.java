package com.rainmonth.player.activity.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.player.R;
import com.rainmonth.player.model.VideoListBean;

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
    private RecyclerView rvVideoList;
    private BaseQuickAdapter<VideoListBean, BaseViewHolder> videoListAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_list_focus_player_play;
    }

    @Override
    protected void initViewsAndEvents() {
        rvVideoList = findViewById(R.id.rv_video_list);
        rvVideoList.setLayoutManager(new LinearLayoutManager(mContext));
        videoListAdapter = new BaseQuickAdapter<VideoListBean, BaseViewHolder>(R.layout.player_activity_list_focus_list_item) {

            @Override
            protected void convert(BaseViewHolder helper, VideoListBean item) {

            }
        };
        rvVideoList.setAdapter(videoListAdapter);
    }
}
