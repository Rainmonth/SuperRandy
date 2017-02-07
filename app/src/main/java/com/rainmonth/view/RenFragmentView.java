package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.ArticleBean;
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
     * @param articleBeanList renContentBeanList
     */
    void initContentList(List<ArticleBean> articleBeanList);

    /**
     * 进入content详情页
     *
     * @param articleBean articleBean
     */
    void navToDetail(ArticleBean articleBean);
}
