package com.rainmonth.music.fragment.player;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 歌词详情页面
 *
 * @author 张豪成
 * @date 2019-11-27 09:51
 */
public class SongLyricFragment extends BaseLazyFragment {

    public static SongLyricFragment newInstance(Bundle args) {
        SongLyricFragment fragment = new SongLyricFragment();
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
        return R.layout.music_player_fragment_song_lyric;
    }
}
