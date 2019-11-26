package com.rainmonth.common.component;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.rainmonth.common.bean.RandyLogicBean;

/**
 * @author 张豪成
 * @date 2019-11-25 14:06
 */
public abstract class RandyBaseItemProvider<T> extends BaseItemProvider<T, BaseViewHolder> {
    protected RandyLogicBean mLogicBean;

    public RandyBaseItemProvider(RandyLogicBean mLogicBean) {
        this.mLogicBean = mLogicBean;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, T data, int position) {
        helper.setAssociatedObject(mLogicBean);
        randyConvert(helper, data, position);
    }

    public abstract void randyConvert(@NonNull BaseViewHolder helper, T data, int position);


}
