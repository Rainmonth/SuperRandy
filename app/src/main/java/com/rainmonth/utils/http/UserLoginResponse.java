package com.rainmonth.utils.http;

import com.google.gson.annotations.SerializedName;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class UserLoginResponse extends BaseResponse {

    @SerializedName("user_info")
    protected UserBean userBean;

    public UserLoginResponse() {

    }

    public UserLoginResponse(String code, String message) {
        super(code, message);
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
