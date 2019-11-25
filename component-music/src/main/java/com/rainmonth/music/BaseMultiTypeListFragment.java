package com.rainmonth.music;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.rainmonth.common.adapter.BaseMultiTypeAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * 多种类型ViewType列表Fragment
 *
 * @author 张豪成
 * @date 2019-11-23 14:14
 */
public abstract class BaseMultiTypeListFragment<T extends MultiItemEntity> extends BaseLazyFragment {
    protected SwipeRefreshLayout srlContainer;
    protected RecyclerView rvList;

    protected List<T> datas = new ArrayList<>();

    protected BaseMultiTypeAdapter<T, BaseViewHolder> adapter;
    protected List<BaseItemProvider<T, BaseViewHolder>> providerList = new ArrayList<>();

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents(View view) {
        srlContainer = view.findViewById(R.id.srl_container);
        rvList = view.findViewById(R.id.rv_list);

        initData();

        adapter = new BaseMultiTypeAdapter<T, BaseViewHolder>(datas, getProviderList()) {
            @Override
            protected int getViewType(T t) {
                return getItemType(t);
            }
        };
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_common_base_muti_type_list_fragment;
    }

    protected void initData() {

    }

    protected abstract List<BaseItemProvider<T, BaseViewHolder>> getProviderList();

    protected int getItemType(T t) {
        return t.getItemType();
    }

    protected void doRefresh() {

    }

    protected void doLoadMore() {

    }
}
