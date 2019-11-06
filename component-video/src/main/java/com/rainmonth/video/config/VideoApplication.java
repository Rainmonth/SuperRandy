package com.rainmonth.video.config;

import com.rainmonth.common.base.BaseApplication;
import com.rainmonth.componentbase.ServiceFactory;
import com.rainmonth.video.service.VideoService;

public class VideoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void initModuleService() {
        ServiceFactory.getInstance().setVideoService(new VideoService());
    }
}