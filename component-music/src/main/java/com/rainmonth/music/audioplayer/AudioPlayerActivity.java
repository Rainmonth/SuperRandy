package com.rainmonth.music.audioplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.music.R;
import com.rainmonth.router.RouterConstant;

/**
 * @date: 2018-12-20
 * @author: randy
 * @description: 音乐播放界面
 */
@Route(path = RouterConstant.PATH_MUSIC_PLAYER)
public class AudioPlayerActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "AudioPlayerActivity";

    ViewPager vpCover;
    TextView tvPlayTime;
    TextView tvTotalTime;
    LinearLayout llProgressContainer;
    ImageView btnPlayMode;
    ImageView btnPlayPrev;
    ImageView btnPlay;
    ImageView btnPlayNext;
    ImageView btnPlayList;
    RelativeLayout rlControlContainer;
    ImageView btnCollect;
    ImageView btnDownload;
    ImageView btnComment;
    ImageView btnMoreActions;
    ConstraintLayout clContainer;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_audio_player;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        vpCover = findViewById(R.id.vp_cover);
        tvPlayTime = findViewById(R.id.tv_play_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        llProgressContainer = findViewById(R.id.ll_progress_container);
        btnPlayMode = findViewById(R.id.btn_play_mode);
        btnPlayPrev = findViewById(R.id.btn_play_prev);
        btnPlay = findViewById(R.id.btn_play);
        btnPlayNext = findViewById(R.id.btn_play_next);
        btnPlayList = findViewById(R.id.btn_play_list);
        rlControlContainer = findViewById(R.id.rl_control_container);
        btnCollect = findViewById(R.id.btn_collect);
        btnDownload = findViewById(R.id.btn_download);
        btnComment = findViewById(R.id.btn_comment);
        btnMoreActions = findViewById(R.id.btn_more_actions);
        clContainer = findViewById(R.id.cl_container);
    }

    @Override
    public void initToolbar(int colorResId) {
        if (null != mActionBar) {
            mActionBar.setTitle("音乐播放界面");
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_mode:
                break;
            case R.id.btn_play_prev:
                break;
            case R.id.btn_play:
                break;
            case R.id.btn_play_next:
                break;
            case R.id.btn_play_list:
                break;
            case R.id.btn_collect:
                break;
            case R.id.btn_download:
                break;
            case R.id.btn_comment:
                break;
            case R.id.btn_more_actions:
                break;

            default:

                break;
        }
    }
}
