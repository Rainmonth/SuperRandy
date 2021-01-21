package com.rainmonth.player.activity.other.audio;

import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.activity.BasePlayerListActivity;

import java.util.List;

/**
 * 全局悬浮窗播放
 *
 * @author 张豪成
 * @date 2019-11-01 15:13
 */
public class AudioBasicLearnActivity extends BasePlayerListActivity<ExampleBean> {


    @Override
    protected List<ExampleBean> getExampleList() {
        return DemoDataFactory.getAudioLearnList();
    }
}
