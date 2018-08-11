package com.rainmonth.image.api;

public class Consts {
    // Unsplash API 请求地址
    public static final String BASE_URL = "https://api.unsplash.com/";
    // 获取用户信息
    public static final String GET_USER_INFO = BASE_URL + "users/{username}";
    // 获取用户喜欢的图片
    public static final String GET_USER_LIKE_PHOTOS = BASE_URL + "users/{username}/likes";
    // 获取用户的个人网站
    public static final String GET_USER_PERSONAL_SITE = BASE_URL + "users/{username}/portfolio";
    // 获取用户的合集
    public static final String GET_USER_COLLECTIONS = BASE_URL + "users/{username}/collections";

}
