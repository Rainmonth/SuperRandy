package com.rainmonth.common.component;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;

/**
 * 垂直方向 ItemProvider
 *
 * @author 张豪成
 * @date 2019-11-25 13:50
 */
public class RandyVListItemProvider<Item extends BaseBean> extends RandyBaseItemProvider<RandyMultiBean<Item>> {

    public RandyVListItemProvider(RandyLogicBean mLogicBean) {
        super(mLogicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_V_LIST;
    }

    @Override
    public int layout() {
        return Const.Layout.RANDY_V_LIST;
    }

    @Override
    public void randyConvert(@NonNull BaseViewHolder helper, RandyMultiBean<Item> data, int position) {

    }
}
