package com.rainmonth.video.service;

import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.componentbase.service.video.IVideoService;

/**
 * @author 张豪成
 * @date 2019-11-05 20:37
 */
public class VideoService implements IVideoService {
    @Override
    public void goVideoMain() {
        LogUtils.d("VideoService", "goVideoMain");
    }

    @Override
    public void playVideo(String videoUrl) {
        LogUtils.d("VideoService", "playVideo with url->" + videoUrl);
    }
}
