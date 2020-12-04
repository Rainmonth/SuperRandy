package com.rainmonth.player.activity.simple;

import android.text.TextUtils;
import android.widget.TextView;

import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseListActivity;
import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.R;
import com.rainmonth.player.model.SimplePlayExampleBean;

/**
 * @author RandyZhang
 * @date 2020/12/2 11:28 AM
 */
public class SimplePlayDemoListActivity extends BaseListActivity<SimplePlayExampleBean> {
    @Override
    protected void configLoadMore() {
        super.configLoadMore();
        adapter.setEnableLoadMore(false);
    }

    @Override
    protected void initLocalData() {
        super.initLocalData();
        datas = DemoDataFactory.getSimplePlayExampleList();
    }

    @Override
    protected void onListItemClick(SimplePlayExampleBean baseBean, int position) {
        super.onListItemClick(baseBean, position);
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

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.player_example_list_item_view;
    }

    @Override
    protected void bindItem(BaseViewHolder holder, SimplePlayExampleBean item) {
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
}
