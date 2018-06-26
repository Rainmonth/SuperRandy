package com.rainmonth.mvp.model;

import com.rainmonth.api.PursueService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.PursueContract;
import com.rainmonth.mvp.model.bean.PursueBean;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * @desprition: 追 数据获取
 * @author: RandyZhang
 * @date: 2018/6/26 下午3:03
 */
@FragmentScope
public class PursueModel extends BaseModel implements PursueContract.Model {
    @Inject
    public PursueModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Flowable<PageResult<PursueBean>> getPursueList(int page, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(PursueService.class)
                .getPursueList(page, pageSize)
                .map(new Function<Response<PageResult<PursueBean>>, PageResult<PursueBean>>() {
                    @Override
                    public PageResult<PursueBean> apply(Response<PageResult<PursueBean>> pageResultResponse) throws Exception {
                        return pageResultResponse.body();
                    }
                });
    }
}