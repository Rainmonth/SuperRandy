package com.rainmonth.player.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseListActivity;
import com.rainmonth.common.bean.ExampleBean;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.player.R;

import java.util.List;

/**
 * @author RandyZhang
 * @date 2020/12/21 4:23 PM
 */
public abstract class BasePlayerListActivity<T extends ExampleBean> extends BaseListActivity<T> {

    @Override
    protected void initLocalData() {
        datas = getExampleList();
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
    protected void onListItemClick(T baseBean, int position) {
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

    protected abstract List<T> getExampleList();
}
