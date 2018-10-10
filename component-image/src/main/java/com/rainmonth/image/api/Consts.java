package com.rainmonth.image.api;

public class Consts {
    // Base
    // Unsplash API 请求地址
    public static final String BASE_URL = "https://api.unsplash.com/";
    public static final String NO_API_BASE_URL = "https://unsplash.com/";
    //------------------用户相关------------------
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

    //------------------搜索相关------------------
    // 搜索用户
    public static final String SEARCH_USER = BASE_URL + "search/users";
    // 搜索图片
    public static final String SEARCH_PHOTOS = BASE_URL + "search/photos";
    // 搜索合集
    public static final String SEARCH_COLLECTIONS = BASE_URL + "search/collections";

    //------------------合集相关------------------
    // 获取合集
    public static final String GET_COLLECTIONS = BASE_URL + "collections";
    // 获取精选合集
    public static final String GET_FEATURED_COLLECTIONS = BASE_URL + "collections/featured";
    // 获取策划合集
    public static final String GET_CURATED_COLLECTIONS = BASE_URL + "collections/curated";
    // 获取策划合集的图片
    public static final String GET_CURATED_COLLECTION_PHOTOS = BASE_URL + "collections/curated/{id}/photos";
    // 获取合集图片
    public static final String GET_COLLECTION_PHOTOS = BASE_URL + "collections/{id}/photos";
    // 获取合集详情
    public static final String GET_COLLECTION_DETAIL_INFO = BASE_URL + "collections/{id}";
    // 添加合集
    public static final String ADD_COLLECTION = BASE_URL + "collections";
    // 添加图片到合集
    public static final String ADD_PHOTO_TO_COLLECTION = BASE_URL + "collections/{collection_id}/add";
    // 删除合集
    public static final String DELETE_COLLECTION = BASE_URL + "collections/{id}";
    // 从合集删除图片
    public static final String DELETE_PHOTO_FROM_COLLECTION = BASE_URL + "collections/{collection_id}/remove";
    // 跟新合集
    public static final String UPDATE_COLLECTION = BASE_URL + "collections/{id}";
    // 获取合集相关合集
    public static final String GET_RELATED_COLLECTIONS = BASE_URL + "collections/{id}/related";

    // noapi
    public static final String GET_DAILY_PHOTO = NO_API_BASE_URL + "napi/feeds/home";

    //------------------图片相关------------------
    // 获取图片
    public static final String GET_PHOTOS = BASE_URL + "photos";
    // 获取策划图片
    public static final String GET_CURATED_PHOTOS = BASE_URL + "photos/curated";
    // 获取某张图片的统计数据
    public static final String GET_PHOTO_STATISTICS = BASE_URL + "photos/{id}/statistics";


    public static final String PHOTO_LIST = "photo_list";
    public static final String CURRENT_PAGE = "current_page";
    public static final String CURRENT_INDEX = "current_position";
    public static final String PAGE_SIZE = "page_size";
    public static final String PHOTO_BEAN = "photo_bean";
    public static final String COLLECTION_ID = "collection_id";
    public static final String FROM_PHOTO = "from_photo";
    public static final String FROM_COLLECTION = "from_collection";
    public static final String ORDER_BY = "orderBy";
    public static final String FROM = "from";
    public static final String SEARch_KEY = "search_key";
}
