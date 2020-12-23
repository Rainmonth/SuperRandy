package com.rainmonth.player;

import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.player.activity.detail.AdDetailVideoPlayerActivity;
import com.rainmonth.player.activity.detail.ConfigAdDetailVideoPlayerActivity;
import com.rainmonth.player.activity.detail.ConfigDetailVideoPlayerActivity;
import com.rainmonth.player.activity.detail.ControlDetailVideoPlayerActivity;
import com.rainmonth.player.activity.detail.DetailPlayDemoListActivity;
import com.rainmonth.player.activity.list.DyVideoPlayActivity;
import com.rainmonth.player.activity.other.AudioChangeVoiceWithSoundTouchActivity;
import com.rainmonth.player.activity.other.AudioRecordExampleActivity;
import com.rainmonth.player.activity.other.FloatVideoPlayerActivity;
import com.rainmonth.player.activity.other.AudioBasicLearnActivity;
import com.rainmonth.player.activity.list.ListPlayDemoListActivity;
import com.rainmonth.player.activity.detail.NormalDetailVideoPlayActivity;
import com.rainmonth.player.activity.simple.SimplePlayDemoListActivity;
import com.rainmonth.player.activity.simple.ExoSimpleVideoPlayerActivity;
import com.rainmonth.player.model.DetailPlayExampleBean;
import com.rainmonth.player.model.ListPlayExampleBean;
import com.rainmonth.player.model.SimplePlayExampleBean;
import com.rainmonth.player.model.VideoListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 示例数据生成
 *
 * @author RandyZhang
 * @date 2020/12/1 9:55 AM
 */
public class DemoDataFactory {
    public static List<ExampleBean> getExampleDataList() {
        List<ExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ExampleBean("简单播放", "简单调用播放场景示例", ExampleBean.STATE_TODO, SimplePlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("详情播放", "各种详情播放场景示例", ExampleBean.STATE_UNDER, DetailPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("列表播放", "各种列表播放场景示例", ExampleBean.STATE_TODO, ListPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("抖音式播放", "类似于抖音的那种播放效果", ExampleBean.STATE_TODO, DyVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("支持回退时悬浮窗播放", "很多游戏直播软件在退出播放页后仍可以小窗播放", ExampleBean.STATE_TODO, FloatVideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("音频基础学习", "Android音频相关的API学习（AudioRecord、AudioTrack），Demo演示", ExampleBean.STATE_TODO, AudioBasicLearnActivity.class));
        exampleBeans.add(new ExampleBean("视频基础学习", "Android视频相关的API学习（CameraAPI、MediaExtractor和MediaMuxer），Demo演示", ExampleBean.STATE_TODO, AudioBasicLearnActivity.class));
        return exampleBeans;
    }

    public static List<SimplePlayExampleBean> getSimplePlayExampleList() {
        List<SimplePlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new SimplePlayExampleBean("简单播放——Exo的PlayerView", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_FINISH, ExoSimpleVideoPlayerActivity.class));
        exampleBeans.add(new SimplePlayExampleBean("简单播放——Ijk", "打开本地视频", ExampleBean.STATE_TODO, ExoSimpleVideoPlayerActivity.class));
        exampleBeans.add(new SimplePlayExampleBean("简单播放——系统MediaPlayer", "打开Url播放", ExampleBean.STATE_TODO, ExoSimpleVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<DetailPlayExampleBean> getDetailPlayExampleList() {
        List<DetailPlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_FINISH, ExoSimpleVideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "普通的详情播放", ExampleBean.STATE_FINISH, NormalDetailVideoPlayActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "支持配置项的详情播放，如切换清晰度、切换显示比例等", ExampleBean.STATE_FINISH, ConfigDetailVideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("使用GLSurfaceView详情播放", "支持配置项的详情播放，如清晰度、显示比例、播放速度、视频截图、gif生成等", ExampleBean.STATE_FINISH, ControlDetailVideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("带广告的详情播放1", "支持配置广告，先播放广告，在播放主题内容，广告支持跳过", ExampleBean.STATE_FINISH, AdDetailVideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("带广告的详情播放2", "支持配置广告，广告和内容分别采用单独的播放器，这样就可以在视频播放任意位置进行广告播放", ExampleBean.STATE_FINISH, ConfigAdDetailVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<ListPlayExampleBean> getListPlayExampleList() {
        List<ListPlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ListPlayExampleBean("列表播放", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_TODO, ExoSimpleVideoPlayerActivity.class));
        exampleBeans.add(new ListPlayExampleBean("列表播放", "打开本地视频", ExampleBean.STATE_TODO, ExoSimpleVideoPlayerActivity.class));
        exampleBeans.add(new ListPlayExampleBean("列表播放", "打开Url播放", ExampleBean.STATE_TODO, ExoSimpleVideoPlayerActivity.class));
        return exampleBeans;
    }

    /**
     * 获取不同清晰度视频列表
     *
     * @return 不同清晰度视频列表
     */
    public static List<VideoListBean> getMultiTypeVideoList() {
        List<VideoListBean> listBeans = new ArrayList<>();

        return listBeans;
    }

    public static List<VideoListBean> getSwitchClarityPlayList() {
        List<VideoListBean> listBeans = new ArrayList<>();
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String name = "普通";
        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        String name2 = "清晰";
        listBeans.add(new VideoListBean("", source1, "普通"));
        listBeans.add(new VideoListBean("", source2, "清晰"));
        return listBeans;
    }

    /**
     * 音频学习列表
     */
    public static List<ExampleBean> getAudioLearnList() {
        List<ExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ExampleBean("音频录制", "Android 音频录制实现",ExampleBean.STATE_TODO, AudioRecordExampleActivity.class));
        exampleBeans.add(new ExampleBean("利用SoundTouch实现变声", "Android 利用SoundTouch实现音频的变声",ExampleBean.STATE_TODO, AudioChangeVoiceWithSoundTouchActivity.class));
        return exampleBeans;
    }

    /**
     * 视频学习列表
     */
    public static List<ExampleBean> getVideoLearnList() {
        List<ExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ExampleBean("视频录制", "Android 视频录制实现",ExampleBean.STATE_TODO, VideoRecordExampleActivity.class));
        return exampleBeans;
    }
}
