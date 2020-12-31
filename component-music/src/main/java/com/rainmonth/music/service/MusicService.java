package com.rainmonth.music.service;

import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.componentbase.service.music.IMusicService;

/**
 * @author 张豪成
 * @date 2019-11-05 20:34
 */
public class MusicService implements IMusicService {
    @Override
    public void goMusicMain() {
        LogUtils.d("MusicService", "goMusicMain");
    }

    @Override
    public void playMusic(String musicUrl) {
        LogUtils.d("MusicService", "playMusic with url->" + musicUrl);
    }
}
