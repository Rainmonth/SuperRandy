package com.rainmonth.mvp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.di.component.DaggerRanComponent;
import com.rainmonth.di.module.RanModule;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.MemAlbumBean;
import com.rainmonth.mvp.presenter.RanPresenter;
import com.rainmonth.mvp.ui.adapter.RanListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 影集列表展示
 * 点击具体影集，采用ViewPager展示
 * Created by RandyZhang on 16/6/30.
 */
public class RanFragment extends BaseLazyFragment<RanPresenter> implements RanContract.View {

    @BindView(R.id.rv_ran_content)
    RecyclerView rvContent;
    private RanListAdapter mAdapter = null;

    @Override
    public void onFirstUserVisible() {
        mPresenter.getAlbumList(null, 1, 10);
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
    }

    @Override
    public void initViews(final List<MemAlbumBean> memAlbumBeanList) {
        mAdapter.setNewData(memAlbumBeanList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MemAlbumBean memAlbumBean = memAlbumBeanList.get(position);
                if (null != memAlbumBean) {
                    navToDetail(memAlbumBean);
                }
            }
        });
    }

    @Override
    public void navToDetail(MemAlbumBean memAlbumBean) {
        showToast("即将进入" + memAlbumBean.getDescription());
    }
}
