package com.rainmonth.view;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.base.mvp.IBaseView;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface ILoginView extends IBaseView {
    void naveToAfterLogin(BaseResponse response);
}
