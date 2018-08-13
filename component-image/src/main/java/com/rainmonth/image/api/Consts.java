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
    // 获取用户的统计数据
    public static final String GET_USER_STATISTICS = BASE_URL + "users/{username}/statistics";
    // 获取用户的照片
    public static final String GET_USER_PHOTOS = BASE_URL + "users/{username}/photos";

    //------------------------------------------
    // 获取当前用户信息
    public static final String GET_CURRENT_USER_INFO = BASE_URL + "me";
    // 跟新当前用户信息
    public static final String UPDATE_CURRENT_USER_INFO = BASE_URL + "me";
    // 搜索用户
    public static final String SEARCH_USER = BASE_URL + "search/users";
    // 搜索图片
    public static final String SEARCH_PHOTOS = BASE_URL + "search/photos";
    // 搜索合集
    public static final String SEARCH_COLLECTIONS = BASE_URL + "search/collections";
}
