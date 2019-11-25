package com.rainmonth.common.component;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;

/**
 * 功能分类Item提供者
 *
 * @author 张豪成
 * @date 2019-11-25 13:53
 */
public class RandyCateItemProvider<T extends RandyMultiBean, K extends BaseViewHolder> extends RandyBaseItemProvider<T, K> {
    public RandyCateItemProvider(RandyLogicBean mLogicBean) {
        super(mLogicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_CATE;
    }

    @Override
    public int layout() {
        return Const.Layout.RANDY_CATE;
    }

    @Override
    public void randyConvert(@NonNull BaseViewHolder helper, RandyMultiBean data, int position) {

    }
}
