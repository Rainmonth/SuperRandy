package com.rainmonth.player;

import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.player.activity.detail.ConfigDetailVideoPlayerActivity;
import com.rainmonth.player.activity.detail.DetailPlayDemoListActivity;
import com.rainmonth.player.activity.list.DyVideoPlayActivity;
import com.rainmonth.player.activity.FloatVideoPlayerActivity;
import com.rainmonth.player.activity.GlobalVideoPlayerActivity;
import com.rainmonth.player.activity.list.ListPlayDemoListActivity;
import com.rainmonth.player.activity.detail.NormalDetailVideoPlayActivity;
import com.rainmonth.player.activity.simple.SimplePlayDemoListActivity;
import com.rainmonth.player.activity.simple.SimpleVideoPlayerActivity;
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
        exampleBeans.add(new ExampleBean("详情播放", "各种详情播放场景示例", ExampleBean.STATE_TODO, DetailPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("列表播放", "各种列表播放场景示例", ExampleBean.STATE_TODO, ListPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("抖音式播放", "类似于抖音的那种播放效果", ExampleBean.STATE_TODO, DyVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("支持回退时悬浮窗播放", "很多游戏直播软件在退出播放页后仍可以小窗播放", ExampleBean.STATE_TODO, FloatVideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("全局悬浮窗播放场景示例", "全局浮窗播放", ExampleBean.STATE_TODO, GlobalVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<SimplePlayExampleBean> getSimplePlayExampleList() {
        List<SimplePlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new SimplePlayExampleBean("简单播放——Exo的PlayerView", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
        exampleBeans.add(new SimplePlayExampleBean("简单播放——Ijk", "打开本地视频", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
        exampleBeans.add(new SimplePlayExampleBean("简单播放——系统MediaPlayer", "打开Url播放", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<DetailPlayExampleBean> getDetailPlayExampleList() {
        List<DetailPlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_FINISH, SimpleVideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "普通的详情播放", ExampleBean.STATE_FINISH, NormalDetailVideoPlayActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "支持配置项的详情播放，如切换清晰度、切换显示比例等", ExampleBean.STATE_FINISH, ConfigDetailVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<ListPlayExampleBean> getListPlayExampleList() {
        List<ListPlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ListPlayExampleBean("列表播放", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
        exampleBeans.add(new ListPlayExampleBean("列表播放", "打开本地视频", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
        exampleBeans.add(new ListPlayExampleBean("列表播放", "打开Url播放", ExampleBean.STATE_TODO, SimpleVideoPlayerActivity.class));
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
}
