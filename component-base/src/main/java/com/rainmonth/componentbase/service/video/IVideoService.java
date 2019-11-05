package com.rainmonth.componentbase.service.video;

/**
 * 视频模块接口
 *
 * @author 张豪成
 * @date 2019-11-05 19:54
 */
public interface IVideoService {
    /**
     * 进入Video模块的主界面
     */
    void goVideoMain();

    /**
     * 唤起Video模块播放视频界面播放视频
     *
     * @param videoUrl 视频地址
     */
    void playVideo(String videoUrl);

}
