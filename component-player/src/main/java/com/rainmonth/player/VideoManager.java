package com.rainmonth.player;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.rainmonth.player.listener.GSYMediaPlayerListener;
import com.rainmonth.player.utils.CommonUtil;
import com.rainmonth.player.video.base.VideoPlayer;

import static com.rainmonth.player.utils.CommonUtil.hideNavKey;


/**
 * 视频管理，单例
 */
public class VideoManager extends VideoBaseManager {

    public static final int SMALL_ID = R.id.player_small_id;

    public static final int FULLSCREEN_ID = R.id.player_full_id;

    public static String TAG = VideoManager.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static VideoManager videoManager;


    private VideoManager() {
        init();
    }

    /**
     * 单例管理器
     */
    public static synchronized VideoManager instance() {
        if (videoManager == null) {
            videoManager = new VideoManager();
        }
        return videoManager;
    }

    /**
     * 同步创建一个临时管理器
     */
    public static synchronized VideoManager tmpInstance(GSYMediaPlayerListener listener) {
        VideoManager gsyVideoManager = new VideoManager();
        gsyVideoManager.bufferPoint = videoManager.bufferPoint;
        gsyVideoManager.optionModelList = videoManager.optionModelList;
        gsyVideoManager.playTag = videoManager.playTag;
        gsyVideoManager.currentVideoWidth = videoManager.currentVideoWidth;
        gsyVideoManager.currentVideoHeight = videoManager.currentVideoHeight;
        gsyVideoManager.context = videoManager.context;
        gsyVideoManager.lastState = videoManager.lastState;
        gsyVideoManager.playPosition = videoManager.playPosition;
        gsyVideoManager.timeOut = videoManager.timeOut;
        gsyVideoManager.needMute = videoManager.needMute;
        gsyVideoManager.needTimeOutOther = videoManager.needTimeOutOther;
        gsyVideoManager.setListener(listener);
        return gsyVideoManager;
    }

    /**
     * 替换管理器
     */
    public static synchronized void changeManager(VideoManager gsyVideoManager) {
        videoManager = gsyVideoManager;
    }

    /**
     * 退出全屏，主要用于返回键
     *
     * @return 返回是否全屏
     */
    @SuppressWarnings("ResourceType")
    public static boolean backFromWindowFull(Context context) {
        boolean backFrom = false;
        ViewGroup vp = (ViewGroup) (CommonUtil.scanForActivity(context)).findViewById(Window.ID_ANDROID_CONTENT);
        View oldF = vp.findViewById(FULLSCREEN_ID);
        if (oldF != null) {
            backFrom = true;
            hideNavKey(context);
            if (VideoManager.instance().lastListener() != null) {
                VideoManager.instance().lastListener().onBackFullscreen();
            }
        }
        return backFrom;
    }

    /**
     * 页面销毁了记得调用是否所有的video
     */
    public static void releaseAllVideos() {
        if (VideoManager.instance().listener() != null) {
            VideoManager.instance().listener().onCompletion();
        }
        VideoManager.instance().releaseMediaPlayer();
    }


    /**
     * 暂停播放
     */
    public static void onPause() {
        if (VideoManager.instance().listener() != null) {
            VideoManager.instance().listener().onVideoPause();
        }
    }

    /**
     * 恢复播放
     */
    public static void onResume() {
        if (VideoManager.instance().listener() != null) {
            VideoManager.instance().listener().onVideoResume();
        }
    }


    /**
     * 恢复暂停状态
     *
     * @param seek 是否产生seek动作,直播设置为false
     */
    public static void onResume(boolean seek) {
        if (VideoManager.instance().listener() != null) {
            VideoManager.instance().listener().onVideoResume(seek);
        }
    }

    /**
     * 当前是否全屏状态
     *
     * @return 当前是否全屏状态， true代表是。
     */
    @SuppressWarnings("ResourceType")
    public static boolean isFullState(Activity activity) {
        ViewGroup vp = (ViewGroup) (CommonUtil.scanForActivity(activity)).findViewById(Window.ID_ANDROID_CONTENT);
        final View full = vp.findViewById(FULLSCREEN_ID);
        VideoPlayer gsyVideoPlayer = null;
        if (full != null) {
            gsyVideoPlayer = (VideoPlayer) full;
        }
        return gsyVideoPlayer != null;
    }

}