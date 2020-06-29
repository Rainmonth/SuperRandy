package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.BaseResponse;
import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface RegisterContract {
    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {
        void naveToAfterRegister(BaseResponse response);
    }
}
