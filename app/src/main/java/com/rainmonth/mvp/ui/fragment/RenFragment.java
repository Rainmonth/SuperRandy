package com.rainmonth.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.http.PageData;
import com.rainmonth.di.component.DaggerRenComponent;
import com.rainmonth.di.module.RenModule;
import com.rainmonth.mvp.ArticleListAdapter;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.mvp.presenter.RenPresenter;
import com.rainmonth.mvp.ui.adapter.BannerViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 荏Fragment，主要展示热门活动，文章列表，后续会展示力量专题
 * Created by RandyZhang on 16/6/30.
 */
public class RenFragment extends BaseLazyFragment<RenPresenter> implements RenContract.View {

    public static final String BANNER_BEAN = "banner_bean";
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private int page = 1;
    private boolean isRefresh = false;
    private ArticleListAdapter mAdapter = null;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ren;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }


    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerRenComponent.builder()
                .appComponent(appComponent)
                .renModule(new RenModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mAdapter = new ArticleListAdapter(R.layout.adapter_article_lv_content_item, mContext);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getData().get(position) != null)
                    showToast(mAdapter.getData().get(position).getTitle());
            }
        });
        mPresenter.getArticleList(page, 10);
        mPresenter.getBannerList(1, 10, 6);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initHomeBanners(List<BannerBean> bannerBeanList) {
        List<BaseLazyFragment> bannerFragmentList = new ArrayList<>();
        if (null != bannerBeanList && bannerBeanList.size() > 0) {
            for (int i = 0; i < bannerBeanList.size(); i++) {
                BaseLazyFragment fragment = new HomeBannerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BANNER_BEAN, bannerBeanList.get(i));
                fragment.setArguments(bundle);
                bannerFragmentList.add(fragment);
            }
        }
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ren_list_head,
                rvContent, false);
        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.vp_ren_fragment);
        viewPager.setAdapter(new BannerViewPagerAdapter(getChildFragmentManager(),
                bannerFragmentList));
        mAdapter.addHeaderView(headView);
    }

    @Override
    public void initContentList(PageData<ArticleBean> articleBeanPageData) {
        final int size = articleBeanPageData.getList().size();
        if (isRefresh) {
            mAdapter.setNewData(articleBeanPageData.getList());
        } else {
            if (size > 0) {
                mAdapter.addData(articleBeanPageData.getList());
            }
        }
        if (page < articleBeanPageData.getTotalPage()) {
            mAdapter.loadMoreComplete();
        } else if (page == articleBeanPageData.getTotalPage()) {
            mAdapter.loadMoreEnd(true);
        }
        page++;
    }

    @Override
    public void navToDetail(ArticleBean articleBean) {
        // todo 进入二级界面
    }
}
