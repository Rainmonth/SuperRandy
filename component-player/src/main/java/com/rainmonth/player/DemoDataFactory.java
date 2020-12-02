package com.rainmonth.player;

import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.player.activity.ConfigVideoPlayActivity;
import com.rainmonth.player.activity.DetailPlayDemoListActivity;
import com.rainmonth.player.activity.DetailVideoPlayActivity;
import com.rainmonth.player.activity.DyVideoPlayActivity;
import com.rainmonth.player.activity.FloatVideoPlayerActivity;
import com.rainmonth.player.activity.GlobalVideoPlayerActivity;
import com.rainmonth.player.activity.ListFocusVideoPlayActivity;
import com.rainmonth.player.activity.ListPlayDemoListActivity;
import com.rainmonth.player.activity.VideoPlayerActivity;
import com.rainmonth.player.model.DetailPlayExampleBean;

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
        exampleBeans.add(new ExampleBean("简单播放", "简单调用播放场景示例", ExampleBean.STATE_TODO, VideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("详情播放", "各种详情播放场景示例", ExampleBean.STATE_TODO, DetailPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("列表播放", "各种列表播放场景示例", ExampleBean.STATE_TODO, ListPlayDemoListActivity.class));
        exampleBeans.add(new ExampleBean("抖音式播放", "类似于抖音的那种播放效果", ExampleBean.STATE_TODO, DyVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("支持回退时悬浮窗播放", "很多游戏直播软件在退出播放页后仍可以小窗播放", ExampleBean.STATE_TODO, FloatVideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("全局悬浮窗播放场景示例", "全局浮窗播放", ExampleBean.STATE_TODO, GlobalVideoPlayerActivity.class));
        return exampleBeans;
    }

    public static List<DetailPlayExampleBean> getDetailPlayExampleBean() {
        List<DetailPlayExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "采用ExoPlayer内核，同时采用ExoPlayer2提供的PlayerView", DetailPlayExampleBean.STATE_TODO, VideoPlayerActivity.class));
        exampleBeans.add(new DetailPlayExampleBean("详情播放", "仅仅播放控件", DetailPlayExampleBean.STATE_FINISH, DetailPlayDemoListActivity.class));

        return exampleBeans;
    }
}
