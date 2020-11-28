package com.rainmonth.common.base;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rainmonth.common.R;
import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持下拉刷新和上拉加载的Activity
 *
 * @author 张豪成
 * @date 2019-11-06 10:06
 */
public abstract class BaseListActivity<T extends BaseBean> extends BaseActivity {
    SwipeRefreshLayout srlContainer;
    RecyclerView rvList;
    BaseQuickAdapter<T, BaseViewHolder> adapter;
    protected List<T> datas = new ArrayList<>();

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.common_base_list_activity;
    }

    @Override
    protected void initViewsAndEvents() {
        srlContainer = findViewById(R.id.srl_container);
        rvList = findViewById(R.id.rl_list);

        initSwipeRefreshLayout();
        initRecyclerView();

    }

    private void initSwipeRefreshLayout() {
        srlContainer.setOnRefreshListener(this::onSwipeRefresh);
    }

    /**
     * 下拉刷新处理
     */
    protected void onSwipeRefresh() {
        srlContainer.setRefreshing(true);
    }

    /**
     * 停止下拉刷新
     */
    protected void stopSwipeRefrsh() {
        srlContainer.setRefreshing(false);
    }

    /**
     * RecyclerView初始化
     */
    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemViewLayoutId(), datas) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                bindItem(helper, item);
            }
        };
        adapter.setOnItemClickListener(this::onListItemClick);
        rvList.setAdapter(adapter);
        // 是否可以加载更多
        if (isListLoadMoreEnabled()) {
            adapter.setEnableLoadMore(true);
            adapter.setOnLoadMoreListener(this::onListLoadMore, rvList);
        }
    }

    private void onListItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (datas.size() > 0) {
            T baseBean = datas.get(position);
            onListItemClick(baseBean, position);
        }
    }

    protected void onListItemClick(T baseBean, int position) {

    }

    protected void onListLoadMore() {

    }

    protected boolean isListLoadMoreEnabled() {
        return true;
    }

    protected abstract @LayoutRes
    int getItemViewLayoutId();

    protected abstract void bindItem(BaseViewHolder holder, T item);
}
