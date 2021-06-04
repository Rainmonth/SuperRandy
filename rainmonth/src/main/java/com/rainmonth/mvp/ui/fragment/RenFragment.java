package com.rainmonth.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.BaseWebActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.PageData;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.common.widgets.viewpager.RandyViewPager;
import com.rainmonth.di.component.DaggerRenComponent;
import com.rainmonth.di.module.RenModule;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.mvp.model.bean.TestBean;
import com.rainmonth.mvp.presenter.RenPresenter;
import com.rainmonth.mvp.ui.adapter.ArticleListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 荏Fragment，主要展示热门活动，文章列表，后续会展示力量专题
 * Created by RandyZhang on 16/6/30.
 */
public class RenFragment extends BaseLazyFragment<RenPresenter> implements RenContract.View {

    public static final String BANNER_BEAN = "banner_bean";
    @BindView(R.id.srl_container)
    SwipeRefreshLayout srlContainer;
    @BindView(R.id.rv_content)
    RecyclerView       rvContent;

    private View                       headView;
    private RandyViewPager<BannerBean> viewPager;

    private int                page      = 1;
    private boolean            isRefresh = false;
    private ArticleListAdapter mAdapter  = null;

    @Override
    public void onFirstUserVisible() {
        srlContainer.setRefreshing(true);
        isRefresh = true;
        page = 1;
        mPresenter.getArticleList(page, 10);
        mPresenter.getBannerList(1, 10, 6);

        mPresenter.test();
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ren;
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
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ren_list_head,
                rvContent, false);
        viewPager = headView.findViewById(R.id.vp_ren_fragment);
        mAdapter.addHeaderView(headView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean articleBean = mAdapter.getData().get(position);
                if (articleBean != null) {
                    showToast(articleBean.getTitle());
                    navToDetail(articleBean);
                }

            }
        });
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlContainer.setRefreshing(true);
                isRefresh = true;
                page = 1;
                mPresenter.getArticleList(page, 10);
                mPresenter.getBannerList(1, 10, 6);
            }
        });
    }

    @Override
    public void test(TestBean testBean) {
        LogUtils.i(testBean);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initHomeBanners(final List<BannerBean> bannerBeanList) {
        if (bannerBeanList != null && bannerBeanList.size() > 0) {
            viewPager.setOnPageClickListener(new RandyViewPager.PageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    BannerBean bannerBean = bannerBeanList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, bannerBean.getUrl());
                    bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, bannerBean.getTitle());
                    readyGo(BaseWebActivity.class, bundle);
                }
            });
            viewPager.setPages(bannerBeanList, new RandyViewPager.ViewHolderCreator() {
                @Override
                public RandyViewPager.ViewHolder createViewHolder() {
                    return new RandyViewPager.ViewHolder<BannerBean>() {
                        private ImageView ivBanner;
                        private TextView tvTitle;

                        @Override
                        public View createView(Context context) {
                            View view = LayoutInflater.from(context)
                                    .inflate(R.layout.ren_banner_item, null);
                            ivBanner = view.findViewById(R.id.iv_thumb);
                            tvTitle = view.findViewById(R.id.tv_title);
                            return view;
                        }

                        @Override
                        public void onBind(Context context, int position, BannerBean bannerBean) {
                            tvTitle.setText(bannerBean.getTitle());
                            mAppComponent.imageLoader().loadImage(context, GlideImageConfig
                                    .builder()
                                    .url(bannerBean.getThumb())
                                    .imageView(ivBanner)
                                    .build());
                        }
                    };
                }
            });

            viewPager.start();
        } else {
            mAdapter.removeHeaderView(headView);
        }

    }

    @Override
    public void initContentList(PageData<ArticleBean> articleBeanPageData) {
        srlContainer.setRefreshing(false);
        final int size = articleBeanPageData.getList().size();
        if (isRefresh) {
            mAdapter.setNewData(articleBeanPageData.getList());
            isRefresh = false;
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
        //  进入二级界面
        Bundle bundle = new Bundle();
        bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, "http://10.0.10.61:38890/vip-introduce/#/launcher");
        bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, articleBean.getTitle());
        readyGo(BaseWebActivity.class, bundle);
    }
}
