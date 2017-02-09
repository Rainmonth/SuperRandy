package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.ArticleBean;
import com.rainmonth.bean.ArticleGroupBean;
import com.rainmonth.bean.BannerBean;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RenFragmentView extends IBaseView {

    /**
     * 初始化首页banner
     *
     * @param bannerBeanList bannerBeanList
     */
    void initHomeBanners(List<BannerBean> bannerBeanList);

    /**
     * 初始化首页内容列表
     *
     * @param articleGroupBeanList articleGroupBeanList
     */
    void initContentList(List<ArticleGroupBean> articleGroupBeanList);

    /**
     * 进入content详情页
     *
     * @param articleBean articleBean
     */
    void navToDetail(ArticleBean articleBean);
}
