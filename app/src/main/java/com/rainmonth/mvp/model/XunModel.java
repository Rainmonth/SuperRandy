package com.rainmonth.mvp.model;

import com.rainmonth.api.NavService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.Result;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * XunFragment 数据获取
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class XunModel extends BaseModel implements XunContract.Model {
    @Inject
    public XunModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Flowable<Result<List<XunNavigationBean>>> getNavigationBeanList() {
        return mRepositoryManager.obtainRetrofitService(NavService.class)
                .getXunNavList()
                .map(new Function<Response<Result<List<XunNavigationBean>>>, Result<List<XunNavigationBean>>>() {
                    @Override
                    public Result<List<XunNavigationBean>> apply(Response<Result<List<XunNavigationBean>>> resultResponse) throws Exception {
                        return resultResponse.body();
                    }
                });
    }
}
