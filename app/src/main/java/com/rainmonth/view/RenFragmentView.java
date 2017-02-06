package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;

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
     * @param renContentInfoList renContentInfoList
     */
    void initContentList(List<RenContentInfo> renContentInfoList);

    /**
     * 进入content详情页
     *
     * @param renContentInfo renContentInfo
     */
    void navToDetail(RenContentInfo renContentInfo);
}
