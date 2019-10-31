package com.rainmonth.music.videoplayer;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 视频播放主界面
 *
 * @author 张豪成
 * @date 2019-05-15 12:37
 */
public class VideoPlayMainActivity extends BaseActivity {
    TextView tvPlayVideo;
    RecyclerView rvAudioVideoExample;
    BaseQuickAdapter<ExampleBean, BaseViewHolder> exampleAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_video_main;
    }

    @Override
    protected void initViewsAndEvents() {
        tvPlayVideo = findViewById(R.id.tv_play_video);
        tvPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(VideoPlayerActivity.class);
            }
        });
        rvAudioVideoExample = findViewById(R.id.rv_audio_video_example);
        exampleAdapter = new BaseQuickAdapter<ExampleBean, BaseViewHolder>(R.layout.music_example_list_item_view) {
            @Override
            protected void convert(BaseViewHolder helper, ExampleBean item) {

            }
        };

    }
}
