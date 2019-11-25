package com.rainmonth.common.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.provider.BaseItemProvider;

import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-25 09:37
 */
public abstract class BaseMultiTypeAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends MultipleItemRvAdapter<T, K> {

    private List<BaseItemProvider<T, K>> providerList;

    public BaseMultiTypeAdapter(@Nullable List<T> data, List<BaseItemProvider<T, K>> providers) {
        super(data);
        providerList = providers;

        finishInitialize();
    }

    protected abstract int getViewType(T t);


    @Override
    public void registerItemProvider() {
        for (BaseItemProvider<T, K> itemProvider : providerList) {
            mProviderDelegate.registerProvider(itemProvider);
        }
    }
}
