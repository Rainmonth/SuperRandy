package com.rainmonth.player.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseListActivity;
import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.componentbase.ServiceFactory;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.R;
import com.rainmonth.router.RouterConstant;

/**
 * 视频播放主界面
 *
 * @author 张豪成
 * @date 2019-05-15 12:37
 */
@Route(path = RouterConstant.PATH_VIDEO_HOME)
public class VideoMainActivity extends BaseListActivity<ExampleBean> {

    @Override
    protected void initLocalData() {
        super.initLocalData();
        datas = DemoDataFactory.getExampleDataList();
    }

    @Override
    protected void configLoadMore() {
        super.configLoadMore();
        adapter.setEnableLoadMore(false);
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.player_example_list_item_view;
    }

    @Override
    protected void bindItem(BaseViewHolder holder, ExampleBean item) {
        TextView tvExName = holder.getView(R.id.tv_example_name);
        TextView tvExDes = holder.getView(R.id.tv_example_des);
        TextView tvStatus = holder.getView(R.id.tv_status);

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

    @Override
    protected void onListItemClick(ExampleBean baseBean, int position) {
        super.onListItemClick(baseBean, position);
        if (position == 1) {
            ServiceFactory.getInstance().getMusicService().playMusic("https://www.baidu.com/test.mp3");
        }
        if (datas.size() == 0) {
            return;
        }
        ExampleBean exampleBean = datas.get(position);
        if (exampleBean == null) {
            return;
        }
        try {
            readyGo(exampleBean.clazz);
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
}
