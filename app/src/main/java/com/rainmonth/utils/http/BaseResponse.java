package com.rainmonth.utils.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class BaseResponse {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;

    public BaseResponse() {
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
