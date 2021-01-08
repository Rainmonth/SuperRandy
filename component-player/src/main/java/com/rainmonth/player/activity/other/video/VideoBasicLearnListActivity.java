package com.rainmonth.player.activity.other.video;

import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.activity.BasePlayerListActivity;

import java.util.List;

/**
 * 视频基础学习列表
 *
 * @author 张豪成
 * @date 2019-11-01 15:13
 */
public class VideoBasicLearnListActivity extends BasePlayerListActivity<ExampleBean> {

    @Override
    protected List<ExampleBean> getExampleList() {
        return DemoDataFactory.getVideoLearnList();
    }
}
