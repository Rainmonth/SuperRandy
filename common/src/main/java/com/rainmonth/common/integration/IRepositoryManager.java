package com.rainmonth.common.integration;

/**
 * 数据仓库管理（包括网络数据、缓存数据即数据库缓存数据）
 * Created by RandyZhang on 2018/6/1.
 */

public interface IRepositoryManager {
    /**
     * 注入RetrofitService（网络请求服务）
     *
     * @param services
     */
    void injectRetrofitService(Class<?>... services);

    /**
     * 根据传入的Retrofit Service Name获取对应的Retrofit Service
     *
     * @param serviceClass 需要获取的Service名字
     * @param <T> 泛型参数
     * @return 对应的Service类
     */
    <T> T obtainRetrofitService(Class<T> serviceClass);

    /**
     * 注入CacheService（缓存服务）
     *
     * @param services
     */
    void injectCacheService(Class<?>... services);

    /**
     * 根据出入的Cache Service Name获取对应的Cache Service
     *
     * @param cacheClass
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cacheClass);


//    void injectRealmConfigs(RealmConfiguration... realmConfigurations);
//
//    RealmConfiguration obtainRealmConfig(String realmFileName);

}
