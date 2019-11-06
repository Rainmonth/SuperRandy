package com.rainmonth.componentbase.service.video;

import android.util.Log;

/**
 * @author 张豪成
 * @date 2019-11-05 20:02
 */
public class DefaultVideoService implements IVideoService {
    @Override
    public void goVideoMain() {
        Log.d("VideoService", "goVideoMain default implementation");
    }

    @Override
    public void playVideo(String videoUrl) {
        Log.d("VideoService", "playVideo default implementation");
    }
}
