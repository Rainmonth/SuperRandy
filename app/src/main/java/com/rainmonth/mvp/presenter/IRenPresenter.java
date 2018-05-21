package com.rainmonth.mvp.presenter;

import com.rainmonth.mvp.model.bean.ArticleBean;

/**
 * Created by RandyZhang on 2017/1/23.
 */

public interface IRenPresenter {

    void getContentList();

    void getHomeBanner();

    void navToDetail(ArticleBean articleBean);
}
