package com.rainmonth.componentbase.service.music;

/**
 * 音频播放接口
 * @author 张豪成
 * @date 2019-11-05 19:59
 */
public interface IMusicService {
    /**
     * 进入music模块的主界面
     */
    void goMusicMain();

    /**
     * 唤起Video模块播放视频界面播放视频
     *
     * @param musicUrl 视频地址
     */
    void playMusic(String musicUrl);
}
