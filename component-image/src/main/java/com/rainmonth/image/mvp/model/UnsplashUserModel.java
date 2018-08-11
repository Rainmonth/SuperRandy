package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UUserApi;
import com.rainmonth.image.mvp.contract.UnsplashUserContract;
import com.rainmonth.image.mvp.model.bean.UserBean;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @desprition: 获取用户数据
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:17
 */
@ActivityScope
public class UnsplashUserModel extends BaseModel implements UnsplashUserContract.Model {

    @Inject
    public UnsplashUserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<UserBean> getUserInfo(String username, int w, int h) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserInfo(username, w, h);
//                .map(new Function<Response<BaseResponse>, Response<BaseResponse>>() {
//                    @Override
//                    public Response<BaseResponse> apply(Response<BaseResponse> baseResponseResponse) throws Exception {
//                        return null;
//                    }
//                });
    }
}