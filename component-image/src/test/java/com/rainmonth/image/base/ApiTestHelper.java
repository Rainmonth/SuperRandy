package com.rainmonth.image.base;

import com.rainmonth.common.http.LoggingInterceptor;
import com.rainmonth.image.api.Consts;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTestHelper {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static <T> T createApiClass(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
    }
}
