package com.rainmonth.music.audioplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.adapter.BaseViewPagerAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.fragment.player.SongAnimFragment;
import com.rainmonth.music.fragment.player.SongLyricFragment;
import com.rainmonth.music.fragment.player.SongRelatedFragment;
import com.rainmonth.music.widget.MusicTopBar;
import com.rainmonth.router.RouterConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2018-12-20
 * @author: randy
 * @description: 音乐播放界面
 */
@Route(path = RouterConstant.PATH_MUSIC_PLAYER)
public class AudioPlayerActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "AudioPlayerActivity";

    MusicTopBar topBar;
    ViewPager vpCover;
    private BaseViewPagerAdapter<Fragment> mAdapter;
    private List<Fragment> fragments;
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
    ImageView btnShare;
    ImageView btnComment;
    ConstraintLayout clContainer;

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected int getOverridePendingTransitionMode() {
        return TRANSITION_MODE_BOTTOM;
    }

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
        topBar = findViewById(R.id.top_bar);
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
        btnShare = findViewById(R.id.btn_share);
        btnComment = findViewById(R.id.btn_comment);
        clContainer = findViewById(R.id.cl_container);

        btnPlayMode.setOnClickListener(this);
        btnPlayPrev.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPlayNext.setOnClickListener(this);
        btnPlayList.setOnClickListener(this);
        btnCollect.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnComment.setOnClickListener(this);

        topBar.setOnClickListener(this::onTopBarClick);
        initViewPager();

    }

    private void onTopBarClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                ToastUtils.showToast(this, "点击topBar菜单");
                break;
            default:
                break;
        }
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(SongRelatedFragment.newInstance(null));
        fragments.add(SongAnimFragment.newInstance(null));
        fragments.add(SongLyricFragment.newInstance(null));
        mAdapter = new BaseViewPagerAdapter<>(getSupportFragmentManager(), fragments);
        vpCover.setAdapter(mAdapter);
        vpCover.setCurrentItem(1, false);
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
                ToastUtils.showToast(this, "点击播放模式");
                break;
            case R.id.btn_play_prev:
                ToastUtils.showToast(this, "点击前一个");
                break;
            case R.id.btn_play:
                ToastUtils.showToast(this, "点击播放");
                break;
            case R.id.btn_play_next:
                ToastUtils.showToast(this, "点击后一个");
                break;
            case R.id.btn_play_list:
                ToastUtils.showToast(this, "点击播放列表");
                break;
            case R.id.btn_collect:
                ToastUtils.showToast(this, "点击收藏");
                break;
            case R.id.btn_download:
                ToastUtils.showToast(this, "点击下载");
                break;
            case R.id.btn_share:
                ToastUtils.showToast(this, "点击分享");
                break;
            case R.id.btn_comment:
                ToastUtils.showToast(this, "点击评论");
                break;

            default:

                break;
        }
    }
}
