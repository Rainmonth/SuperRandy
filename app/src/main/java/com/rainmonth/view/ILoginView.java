package com.rainmonth.view;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.base.mvp.BaseView;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface ILoginView extends BaseView {
    void naveToAfterLogin(BaseResponse response);
}
