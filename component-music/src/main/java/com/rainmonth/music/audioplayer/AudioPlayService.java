package com.rainmonth.music.audioplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @date: 2018-12-20
 * @author: randy
 * @description: 音频播放Service
 */
public class AudioPlayService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
