package com.rainmonth.image.mvp.demo;

import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseListActivity;
import com.rainmonth.image.R;

/**
 * @author RandyZhang
 * @date 2021/10/12 9:49 上午
 */
public class DecorationDemoActivity extends BaseListActivity<DemoBean> {

    @Override
    protected void initLocalData() {

        for (int i = 0; i < 50; i++) {
            datas.add(new DemoBean("标题" + i));
        }
    }

    @Override
    protected int getItemViewLayoutId() {
        return R.layout.image_demo_item;
    }

    @Override
    protected void bindItem(BaseViewHolder holder, DemoBean item) {
        holder.setText(R.id.tv_title, item.text);
    }

    @Override
    protected boolean isListLoadMoreEnabled() {
        return false;
    }
}
