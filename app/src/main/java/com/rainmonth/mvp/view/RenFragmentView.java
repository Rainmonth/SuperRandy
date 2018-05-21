package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.ArticleGroupBean;
import com.rainmonth.mvp.model.bean.BannerBean;

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
