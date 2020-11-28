package com.rainmonth.common.adapter;

import androidx.annotation.Nullable;

import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.adapter.base.MultipleItemRvAdapter;
import com.rainmonth.common.adapter.entity.MultiItemEntity;
import com.rainmonth.common.component.RandyBaseItemProvider;

import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-25 09:37
 */
public abstract class BaseMultiTypeAdapter<T extends MultiItemEntity> extends MultipleItemRvAdapter<T, BaseViewHolder> {

    private List<RandyBaseItemProvider> providerList;

    public BaseMultiTypeAdapter(@Nullable List<T> data, List<RandyBaseItemProvider> providers) {
        super(data);
        providerList = providers;

        finishInitialize();
    }

    protected abstract int getViewType(T t);


    @Override
    public void registerItemProvider() {
        for (RandyBaseItemProvider itemProvider : providerList) {
            mProviderDelegate.registerProvider(itemProvider);
        }
    }
}
