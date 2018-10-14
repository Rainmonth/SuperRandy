package com.rainmonth.image.api;

public class Consts {
    // Unsplash keys
    public static final String ACCESS_KEY = "ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe";
    public static final String SECRET_KEY = "614461e7e06f0aceb99cc465b63edb5f4da5736fc8c0eedbd64dd5e81af0afac";
    public static final String REDIRECT_URL = "urn:ietf:wg:oauth:2.0:oob";
    // access_token
    public static final String ACCESS_TOKEN = "a1e9039e38bd65f601404afe442a999c7a62310d0b0d76ad5571d41ed73da4fd";
//    public static final String ACCESS_TOKEN = "ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe";

    // 后面的是access_token
    public static final String BEARER_AUTHORIZATION = "Bearer " + ACCESS_TOKEN;
    // 后面的是access_token
    public static final String CLIENT_ID_AUTHORIZATION = "Client-ID " + ACCESS_TOKEN;
    public static final String HEADER_VERSION = "Accept-Version:v1";
    public static final String HEADER_BEARER_AUTHORIZATION = "Authorization:" + BEARER_AUTHORIZATION;
    public static final String HEADER_CLIENT_ID_AUTHORIZATION = "Authorization:" + CLIENT_ID_AUTHORIZATION;

    public static final String GRANT_URL = "https://unsplash.com/oauth/authorize?client_id=ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections";

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
    // 搜索（图片、用户、合集）
    public static final String SEARCH = BASE_URL + "search";
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
    public static final String SEARCH_KEY = "search_key";
    public static final String USER_NAME = "username";
}
