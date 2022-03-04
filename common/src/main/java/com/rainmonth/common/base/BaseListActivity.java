package com.rainmonth.common.base;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.R;
import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.common.decoration.CombineGridItemDecoration;
import com.rainmonth.utils.DensityUtils;
import com.rainmonth.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持下拉刷新和上拉加载的Activity
 *
 * @author 张豪成
 * @date 2019-11-06 10:06
 */
public abstract class BaseListActivity<T extends BaseBean> extends BaseActivity {
    RecyclerView rvList;
    protected BaseQuickAdapter<T, BaseViewHolder> adapter;
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
        rvList = findViewById(R.id.rl_list);

        // todo
        initLocalData();

        initRecyclerView();
        configLoadMore();
    }

    protected void configLoadMore() {

    }

    protected void initLocalData() {

    }

    /**
     * RecyclerView初始化
     */
    protected void initRecyclerView() {
        rvList.setLayoutManager(new GridLayoutManager(mContext, getSpanCount()));
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
        int boundSpace = DensityUtils.dip2px(mContext, 20);
        int space = DensityUtils.dip2px(mContext, 10);
        try {
            rvList.addItemDecoration(new CombineGridItemDecoration(
                    new CombineGridItemDecoration.Builder()
                            .spanCount(getSpanCount())
                            .totalCount(datas.size())
                            .enableFirstLeft(true, boundSpace)
                            .enableLastRight(true, boundSpace)
                            .enableFirstTop(true, boundSpace)
                            .enableLastBottom(true, boundSpace)
                            .left(space)
                            .right(space)
                            .top(space)
                            .bottom(space)
                            .build()));
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    protected int getSpanCount() {
        return 4;
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
