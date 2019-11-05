package com.rainmonth.componentbase;

import com.rainmonth.componentbase.service.music.DefaultMusicService;
import com.rainmonth.componentbase.service.music.IMusicService;
import com.rainmonth.componentbase.service.video.DefaultVideoService;
import com.rainmonth.componentbase.service.video.IVideoService;

/**
 * @author 张豪成
 * @date 2019-11-05 19:53
 */
public class ServiceFactory {
    private IMusicService musicService;
    private IVideoService videoService;

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    private static class Inner {
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    public IMusicService getMusicService() {
        if (musicService == null) {
            musicService = new DefaultMusicService();
        }
        return musicService;
    }

    public void setMusicService(IMusicService musicService) {
        this.musicService = musicService;
    }

    public IVideoService getVideoService() {
        if (videoService == null) {
            videoService = new DefaultVideoService();
        }
        return videoService;
    }

    public void setVideoService(IVideoService videoService) {
        this.videoService = videoService;
    }
}
