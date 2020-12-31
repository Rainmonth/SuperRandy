package com.rainmonth.common.component;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.R;
import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;
import com.rainmonth.common.widgets.RandyLinearListView;

/**
 * 水平方向ItemProvider
 *
 * @author 张豪成
 * @date 2019-11-25 13:50
 */
public abstract class RandyHListItemProvider<Item extends BaseBean> extends RandyBaseItemProvider<RandyMultiBean<Item>> {
    protected int mLayoutId;
    private RandyLinearListView<Item> rvList;

    public RandyHListItemProvider(RandyLogicBean logicBean) {
        super(logicBean);
    }

    @Override
    public int layout() {
        return Const.Layout.RANDY_H_LIST;
    }

    @Override
    public void randyConvert(@NonNull BaseViewHolder helper, RandyMultiBean<Item> data, int position) {
        rvList = helper.getView(R.id.rv_list);
        rvList.setItemLayoutId(getItemLayoutId())
                .setOnRenderCallback(this::onRealRender)
                .setOnItemClickListener(this::onRealItemClick)
                .load(data.items)
                .render();
    }

    public abstract int getItemLayoutId();

    public abstract void onRealRender(BaseViewHolder holder, Item data);

    public abstract void onRealItemClick(BaseQuickAdapter adapter, View view, int position);
}
