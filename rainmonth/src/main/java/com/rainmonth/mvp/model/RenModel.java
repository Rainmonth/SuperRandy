package com.rainmonth.mvp.model;

import com.rainmonth.api.ArticleService;
import com.rainmonth.api.BannerService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.http.Result;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * 荏Fragment数据获取
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class RenModel extends BaseModel implements RenContract.Model {
    @Inject
    public RenModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Flowable<Result<List<BannerBean>>> getBannerList(int page, int pageSize, int type) {
        return mRepositoryManager.obtainRetrofitService(BannerService.class)
                .getHomeBannerList(page, pageSize, type)
                .map(new Function<Response<Result<List<BannerBean>>>, Result<List<BannerBean>>>() {
                    @Override
                    public Result<List<BannerBean>> apply(Response<Result<List<BannerBean>>> resultResponse) throws Exception {
                        return resultResponse.body();
                    }
                });
    }

    @Override
    public Flowable<PageResult<ArticleBean>> getArticleList(int page, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ArticleService.class)
                .getArticleList(page, pageSize)
                .map(new Function<Response<PageResult<ArticleBean>>, PageResult<ArticleBean>>() {
                    @Override
                    public PageResult<ArticleBean> apply(Response<PageResult<ArticleBean>> pageResultResponse) throws Exception {
                        return pageResultResponse.body();
                    }
                });
    }
}
