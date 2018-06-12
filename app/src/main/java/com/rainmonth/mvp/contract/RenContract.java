package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.ArticleGroupBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.common.http.Result;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by RandyZhang on 2018/5/24.
 */
public interface RenContract {
    interface Model extends IBaseModel {
        Flowable<Result<List<BannerBean>>> getBannerList(int page, int pageSize, int type);

        List<ArticleGroupBean> getArticleList();
    }

    interface View extends IBaseView {
        void initContentList(List<ArticleGroupBean> articleGroupBeanList);

        void initHomeBanners(List<BannerBean> bannerBeanList);

        void navToDetail(ArticleBean articleBean);
    }
}
