package com.rainmonth.utils.http;

import com.google.gson.annotations.SerializedName;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserInfo;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class UserLoginResponse extends BaseResponse {

    @SerializedName("user_info")
    protected UserInfo userInfo;

    public UserLoginResponse() {

    }

    public UserLoginResponse(String code, String message) {
        super(code, message);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
