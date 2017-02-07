package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentBean;

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
     * @param renContentBeanList renContentBeanList
     */
    void initContentList(List<RenContentBean> renContentBeanList);

    /**
     * 进入content详情页
     *
     * @param renContentBean renContentBean
     */
    void navToDetail(RenContentBean renContentBean);
}
