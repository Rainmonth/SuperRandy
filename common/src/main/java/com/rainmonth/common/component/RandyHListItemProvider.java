package com.rainmonth.common.component;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;

/**
 * 水平方向ItemProvider
 *
 * @author 张豪成
 * @date 2019-11-25 13:50
 */
public class RandyHListItemProvider<T extends RandyMultiBean, K extends BaseViewHolder> extends RandyBaseItemProvider<T, K> {

    public RandyHListItemProvider(RandyLogicBean mLogicBean) {
        super(mLogicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_H_LIST;
    }

    @Override
    public int layout() {
        return Const.Layout.RANDY_H_LIST;
    }

    @Override
    public void randyConvert(@NonNull BaseViewHolder helper, RandyMultiBean data, int position) {

    }
}
