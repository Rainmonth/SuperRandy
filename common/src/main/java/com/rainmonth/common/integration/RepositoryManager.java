package com.rainmonth.common.integration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * {@link IRepositoryManager}的具体实现
 * Created by RandyZhang on 2018/6/1.
 */

public class RepositoryManager implements IRepositoryManager {

    private final Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
    private final Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();
//    private final Map<String, RealmConfiguration> mRealmConfigs = new LinkedHashMap<>();

    private Retrofit mRetrofit;
    private RxCache mRxCache;

    @Inject
    public RepositoryManager(Retrofit mRetrofit, RxCache mRxCache) {
        this.mRetrofit = mRetrofit;
        this.mRxCache = mRxCache;
    }

    @Override
    public void injectRetrofitService(Class<?>... services) {
        for (Class<?> service : services) {
            if (mRetrofitServiceCache.containsKey(service.getName())) {
                continue;
            }
            mRetrofitServiceCache.put(service.getName(), mRetrofit.create(service));
        }
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> serviceClass) {
        return (T) mRetrofitServiceCache.get(serviceClass.getName());
    }

    @Override
    public void injectCacheService(Class<?>[] services) {
        for (Class<?> service : services) {
            if (mCacheServiceCache.containsKey(service.getName())) {
                continue;
            }
            mCacheServiceCache.put(service.getName(), mRxCache.using(service));
        }
    }

    @Override
    public <T> T obtainCacheService(Class<T> cacheClass) {
        return (T) mCacheServiceCache.get(cacheClass.getName());
    }
}
