package com.rainmonth.video;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.componentbase.ServiceFactory;
import com.rainmonth.router.RouterConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频播放主界面
 *
 * @author 张豪成
 * @date 2019-05-15 12:37
 */
@Route(path = RouterConstant.PATH_VIDEO_HOME)
public class VideoMainActivity extends BaseActivity {
    RecyclerView                                  rvAudioVideoExample;
    BaseQuickAdapter<ExampleBean, BaseViewHolder> exampleAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.video_activity_video_main;
    }

    @Override
    protected void initViewsAndEvents() {
        rvAudioVideoExample = findViewById(R.id.rv_audio_video_example);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayout.HORIZONTAL);
        // todo 这个目前无效，稍后查找原因
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.video_example_list_item_decoration_bg));
        rvAudioVideoExample.addItemDecoration(itemDecoration);
        rvAudioVideoExample.setLayoutManager(layoutManager);
        exampleAdapter = new BaseQuickAdapter<ExampleBean, BaseViewHolder>(R.layout.video_example_list_item_view) {
            @Override
            protected void convert(BaseViewHolder helper, ExampleBean item) {
                TextView tvExName = helper.getView(R.id.tv_example_name);
                TextView tvExDes = helper.getView(R.id.tv_example_des);
                TextView tvStatus = helper.getView(R.id.tv_status);

                if (item != null) {
                    if (!TextUtils.isEmpty(item.title)) {
                        tvExName.setText(item.title);
                    } else {
                        tvExName.setText("");
                    }
                    if (!TextUtils.isEmpty(item.desc)) {
                        tvExDes.setText(item.desc);
                    } else {
                        tvExDes.setText("");
                    }
                    if (item.status == ExampleBean.STATE_TODO) {
                        tvStatus.setText("计划做");
                        tvStatus.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                    } else if (item.status == ExampleBean.STATE_UNDER) {
                        tvStatus.setText("正在做");
                        tvStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    } else {
                        tvStatus.setText("已完成");
                        tvStatus.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                    }
                }

            }
        };
        rvAudioVideoExample.setAdapter(exampleAdapter);
        exampleAdapter.addData(getExampleDataList());
        exampleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 1) {
                    ServiceFactory.getInstance().getMusicService().playMusic("https://www.baidu.com/test.mp3");
                }
                List<ExampleBean> exampleBeans = exampleAdapter.getData();
                ExampleBean exampleBean = exampleBeans.get(position);
                if (exampleBean == null) {
                    return;
                }
                try {
                    readyGo(exampleBean.clazz);
                } catch (Exception e) {
                    LogUtils.e(e);
                }
            }
        });

    }

    private List<ExampleBean> getExampleDataList() {
        List<ExampleBean> exampleBeans = new ArrayList<>();
        exampleBeans.add(new ExampleBean("单个视频播放", "仅仅播放控件", ExampleBean.STATE_TODO, VideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("可配置的视频播放", "支持切换清晰度、切换视窗大小、手势调节明暗、声音等", ExampleBean.STATE_TODO, ConfigVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("详情页视频播放", "类似于爱奇艺播放页", ExampleBean.STATE_TODO, DetailVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("列表焦点播放", "多个视频组成的列表，列表停止滑动是播放焦点上的那一个", ExampleBean.STATE_TODO, ListFocusVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("抖音式播放", "类似于抖音的那种播放效果", ExampleBean.STATE_TODO, DyVideoPlayActivity.class));
        exampleBeans.add(new ExampleBean("支持回退时悬浮窗播放", "很多游戏直播软件在退出播放页后仍可以小窗播放", ExampleBean.STATE_TODO, FloatVideoPlayerActivity.class));
        exampleBeans.add(new ExampleBean("全局悬浮窗播放", "全局浮窗播放", ExampleBean.STATE_TODO, GlobalVideoPlayerActivity.class));
        return exampleBeans;
    }
}
