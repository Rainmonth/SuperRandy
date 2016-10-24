package com.rainmonth.base.mvp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class BaseResponse<T> {
    @SerializedName(value = "code", alternate = {"boolen"})
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
