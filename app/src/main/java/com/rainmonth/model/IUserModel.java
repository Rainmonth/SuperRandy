package com.rainmonth.model;

import com.rainmonth.bean.UserBean;
import com.rainmonth.utils.http.RequestCallback;

import rx.Subscription;

/**
 * 用户model
 * Created by RandyZhang on 16/9/19.
 *
 * @param <T> Response object
 */
public interface IUserModel<T> {

    /**
     * 登录
     *
     * @param callback callback
     * @param username 用户名
     * @param psw      密码
     * @return 订阅对象
     */
    Subscription login(RequestCallback<T> callback, String username, String psw);

    /**
     * 登出
     *
     * @param callback callback
     * @return 订阅对象
     */
    Subscription logout(RequestCallback<T> callback);

    /**
     * 注册
     *
     * @param callback callback
     * @param userBean UserBean对象
     * @return 订阅对象
     */
    Subscription register(RequestCallback<T> callback, UserBean userBean);

    /**
     * 获取用户信息
     *
     * @param callback callback
     * @param id       用户id
     * @return 订阅对象
     */
    Subscription getUserInfo(RequestCallback<T> callback, String id);

    /**
     * 更新用户信息
     *
     * @param callback callback
     * @param userBean UserBean对象
     * @return 订阅对象
     */
    Subscription updateUserInfo(RequestCallback<T> callback, UserBean userBean);
}
