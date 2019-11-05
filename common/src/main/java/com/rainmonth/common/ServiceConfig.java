package com.rainmonth.common;

/**
 * @author 张豪成
 * @date 2019-11-05 20:32
 */
public class ServiceConfig {
    private static final String videoApp= "com.rainmonth.video.config.VideoApplication";
    private static final String musicApp = "com.rainmonth.music.config.MusicApplication";
    public static final String[] appModules = {
            videoApp,
            musicApp
    };
}
