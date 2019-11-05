package com.rainmonth.video.service;

import com.rainmonth.componentbase.service.video.IVideoService;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-11-05 20:37
 */
public class VideoService implements IVideoService {
    @Override
    public void goVideoMain() {
        KLog.d("VideoService", "goVideoMain");
    }

    @Override
    public void playVideo(String videoUrl) {
        KLog.d("VideoService", "playVideo with url->" + videoUrl);
    }
}
