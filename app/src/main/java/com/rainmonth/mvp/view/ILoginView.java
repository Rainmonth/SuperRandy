package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.BaseResponse;
import com.rainmonth.common.base.mvp.IBaseView;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface ILoginView extends IBaseView {
    void naveToAfterLogin(BaseResponse response);
}
