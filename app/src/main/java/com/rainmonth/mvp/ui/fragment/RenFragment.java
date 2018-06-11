package com.rainmonth.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainmonth.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.di.component.DaggerRenComponent;
import com.rainmonth.di.module.RenModule;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.ArticleGroupBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.common.adapter.ListViewDataAdapter;
import com.rainmonth.common.adapter.ViewHolderBase;
import com.rainmonth.common.adapter.ViewHolderCreator;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.mvp.presenter.RenPresenter;
import com.rainmonth.mvp.ui.adapter.BannerViewPagerAdapter;
import com.rainmonth.mvp.ui.widgets.InnerListView;
import com.rainmonth.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class RenFragment extends BaseLazyFragment<RenPresenter> implements RenContract.View {

    public static final String BANNER_BEAN = "banner_bean";
    @BindView(R.id.lv_content)
    ListView lvContent;

    private ListViewDataAdapter<ArticleGroupBean> mRenContentListAdapter = null;

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
//        initContentList(renPresenter.getContentListFake());
//        initHomeBanners(renPresenter.getHomeBannerFake());
        mPresenter.init();
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
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ren_list_head, null);
        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.vp_ren_fragment);
        viewPager.setAdapter(new BannerViewPagerAdapter(getSupportFragmentManager(), bannerFragmentList));
        lvContent.addHeaderView(headView);
    }

    @Override
    public void initContentList(List<ArticleGroupBean> articleGroupBeanList) {
        mRenContentListAdapter = new ListViewDataAdapter<ArticleGroupBean>(new ViewHolderCreator<ArticleGroupBean>() {
            @Override
            public ViewHolderBase<ArticleGroupBean> createViewHolder(int position) {
                return new ViewHolderBase<ArticleGroupBean>() {
                    //                    TextView tvTagType;
                    TextView tvTypeName;
                    InnerListView lvArticle;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.adapter_ren_lv_content_item, null);
                        tvTypeName = ButterKnife.findById(convertView, R.id.tv_type_name);
                        lvArticle = ButterKnife.findById(convertView, R.id.lv_article);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, ArticleGroupBean itemData) {
                        tvTypeName.setText(itemData.getType_name());

                        final ListViewDataAdapter articleListAdapter = new ListViewDataAdapter<ArticleBean>(new ViewHolderCreator<ArticleBean>() {
                            @Override
                            public ViewHolderBase<ArticleBean> createViewHolder(int position) {
                                return new ViewHolderBase<ArticleBean>() {
                                    ImageView ivArticleAvatar;
                                    TextView tvArticleTitle;
                                    TextView tvArticleSummarize;
                                    LinearLayout llActionBtnContainer;
                                    TextView tvLike, tvView, tvCollect;

                                    @Override
                                    public View createView(LayoutInflater layoutInflater) {
                                        View convertView = layoutInflater.inflate(R.layout.adapter_article_lv_content_item, null);
                                        ivArticleAvatar = ButterKnife.findById(convertView, R.id.iv_article_avatar);
                                        tvArticleTitle = ButterKnife.findById(convertView, R.id.tv_article_title);
                                        tvArticleSummarize = ButterKnife.findById(convertView, R.id.tv_article_summarize);
                                        llActionBtnContainer = ButterKnife.findById(convertView, R.id.ll_action_btn_container);
                                        tvLike = ButterKnife.findById(convertView, R.id.tv_btn_like);
                                        tvView = ButterKnife.findById(convertView, R.id.tv_btn_view);
                                        tvCollect = ButterKnife.findById(convertView, R.id.tv_btn_collect);

                                        return convertView;
                                    }

                                    @Override
                                    public void showData(int position, ArticleBean itemData) {
                                        Glide.with(getActivity()).load(itemData.getThumb_url()).into(ivArticleAvatar);
                                        tvArticleTitle.setText(itemData.getTitle());
                                        tvArticleSummarize.setText(itemData.getSummarize());
                                        tvLike.setText(String.format("%s喜欢", itemData.getLike_num()));
                                        tvView.setText(String.format("%s浏览", itemData.getView_num()));
                                        tvCollect.setText(String.format("%s收藏", itemData.getCollect_num()));
                                    }
                                };
                            }
                        });
                        articleListAdapter.getDataList().addAll(itemData.getList());
                        lvArticle.setAdapter(articleListAdapter);
                        lvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ArticleBean articleBean = (ArticleBean) articleListAdapter.getDataList().get(position);
                                ToastUtils.showShortToast(getActivity(), "nav to article with title " + articleBean.getTitle());
                            }
                        });

                    }
                };
            }
        });
        mRenContentListAdapter.getDataList().addAll(articleGroupBeanList);
        lvContent.setAdapter(mRenContentListAdapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // nav to detail activity

            }
        });
    }

    @Override
    public void navToDetail(ArticleBean articleBean) {
        // todo 进入二级界面
    }
}