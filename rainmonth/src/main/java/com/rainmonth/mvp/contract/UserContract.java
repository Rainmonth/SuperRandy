package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.Result;

import io.reactivex.Flowable;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface UserContract {
    interface Model extends IBaseModel {
        Flowable<Result> login(String username, String pwd);
    }
    interface View extends IBaseView {
        void naveToAfterLogin(Result response);
    }
}
