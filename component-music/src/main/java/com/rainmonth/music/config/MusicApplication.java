package com.rainmonth.music.config;

import com.rainmonth.common.base.BaseApplication;
import com.rainmonth.componentbase.ServiceFactory;
import com.rainmonth.music.service.MusicService;

/**
 * Created by RandyZhang on 2018/5/16.
 */

public class MusicApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initModuleService();
    }

    @Override
    public void initModuleService() {
        ServiceFactory.getInstance().setMusicService(new MusicService());
    }
}
