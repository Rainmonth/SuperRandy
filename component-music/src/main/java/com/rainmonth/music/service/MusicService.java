package com.rainmonth.music.service;

import com.rainmonth.componentbase.service.music.IMusicService;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-11-05 20:34
 */
public class MusicService implements IMusicService {
    @Override
    public void goMusicMain() {
        KLog.d("MusicService", "goMusicMain");
    }

    @Override
    public void playMusic(String musicUrl) {
        KLog.d("MusicService", "playMusic with url->" + musicUrl);
    }
}
