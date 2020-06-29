package com.rainmonth.mvp.model;

import com.rainmonth.api.UserService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.Result;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.UserContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * 登录model
 * Created by RandyZhang on 2018/6/12.
 */
@ActivityScope
public class UserModel extends BaseModel implements UserContract.Model {
    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Flowable<Result> login(String username, String pwd) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .login(username, pwd)
                .map(new Function<Response<Result>, Result>() {
                    @Override
                    public Result apply(Response<Result> resultResponse) throws Exception {
                        return resultResponse.body();
                    }
                });
    }
}
