package com.rainmonth.mvp.ui.fragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.PageData;
import com.rainmonth.di.component.DaggerRanComponent;
import com.rainmonth.di.module.RanModule;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.MemAlbumBean;
import com.rainmonth.mvp.presenter.RanPresenter;
import com.rainmonth.mvp.ui.adapter.RanListAdapter;

import butterknife.BindView;

/**
 * 影集列表展示
 * 点击具体影集，采用ViewPager展示
 * Created by RandyZhang on 16/6/30.
 */
public class RanFragment extends BaseLazyFragment<RanPresenter> implements RanContract.View {

    @BindView(R.id.rv_ran_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_container)
    SwipeRefreshLayout srlContainer;
    private RanListAdapter mAdapter = null;

    private int page = 1;
    private boolean isRefresh = false;

    @Override
    public void onFirstUserVisible() {
        srlContainer.setRefreshing(true);
        isRefresh = true;
        mPresenter.getAlbumList(null, page, 10);
    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ran;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerRanComponent.builder()
                .appComponent(appComponent)
                .ranModule(new RanModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViewsAndEvents(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAdapter = new RanListAdapter(R.layout.adapter_ran_lv_content_item, mContext);
        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(mAdapter);

        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                srlContainer.setRefreshing(true);
                mPresenter.getAlbumList(null, page, 10);
            }
        });
    }

    @Override
    public void initViews(final PageData<MemAlbumBean> albumBeanPageData) {
        srlContainer.setRefreshing(false);
        int size = albumBeanPageData.getList().size();
        if (isRefresh) {
            mAdapter.setNewData(albumBeanPageData.getList());
            isRefresh = false;
        } else {
            if (size > 0) {
                mAdapter.addData(albumBeanPageData.getList());
            }
        }
        if (page < albumBeanPageData.getTotalPage()) {
            mAdapter.loadMoreComplete();
        } else if (page == albumBeanPageData.getTotalPage()) {
            mAdapter.loadMoreEnd();
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MemAlbumBean memAlbumBean = mAdapter.getData().get(position);
                if (null != memAlbumBean) {
                    navToDetail(memAlbumBean);
                }
            }
        });
        page++;
    }

    @Override
    public void navToDetail(MemAlbumBean memAlbumBean) {
        showToast("即将进入" + memAlbumBean.getDescription());
    }
}
