package com.rainmonth.common.http;

/**
 * 基本网络返回数据
 * Created by RandyZhang on 2018/6/11.
 */
public class BaseResponse {

    /**
     * success : true
     * message : 请求成功
     * code : 1
     */

    private boolean success;
    private String message;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
