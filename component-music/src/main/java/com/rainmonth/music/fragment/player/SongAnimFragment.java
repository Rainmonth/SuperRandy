package com.rainmonth.music.fragment.player;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 歌曲播放动画界面
 *
 * @author 张豪成
 * @date 2019-11-27 09:49
 */
public class SongAnimFragment extends BaseLazyFragment {
    public static SongAnimFragment newInstance(Bundle args) {
        SongAnimFragment fragment = new SongAnimFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_fragment_player_song_anim;
    }
}
