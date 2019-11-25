package com.rainmonth.common.component;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;

/**
 * @author 张豪成
 * @date 2019-11-25 14:06
 */
public abstract class RandyBaseItemProvider<T extends RandyMultiBean, K extends BaseViewHolder> extends BaseItemProvider<T, K> {
    protected RandyLogicBean mLogicBean;

    public RandyBaseItemProvider(RandyLogicBean mLogicBean) {
        this.mLogicBean = mLogicBean;
    }

    @Override
    public void convert(@NonNull K helper, T data, int position) {
        helper.setAssociatedObject(mLogicBean);
        randyConvert(helper, data, position);
    }

    public abstract void randyConvert(@NonNull BaseViewHolder helper, T data, int position);


}
