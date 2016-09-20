package com.rainmonth.view;

import com.rainmonth.base.mvp.BaseView;
import com.rainmonth.utils.http.UserLoginResponse;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface ILoginView extends BaseView {
    void naveToAfterLogin(UserLoginResponse response);
}
