package com.rainmonth.music.fragment.main;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 音乐馆Fragment
 */
public class MusicHallFragment extends BaseLazyFragment {

    public static MusicHallFragment newInstance(Bundle args) {
        MusicHallFragment fragment = new MusicHallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_main_fragment_music_hall;
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
}