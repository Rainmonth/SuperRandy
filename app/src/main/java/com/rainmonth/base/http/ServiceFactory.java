package com.rainmonth.base.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.support.tinker.util.SuperRandyApplicationContext;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author RandyZhang
 * @description Service 工厂类，用于创建请求数据所需要的Service
 */
public class ServiceFactory {
    private volatile static OkHttpClient okHttpClient;
    private volatile static Retrofit retrofit;

    private static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 20;//默认缓存大小20M
    private static final int DEFAULT_MAX_AGE = 60 * 60;// 默认缓存时间单位
    private static final int DEFAULT_MAX_STALE_ONLINE = DEFAULT_MAX_AGE * 24;// 默认在线缓存时间
    private static final int DEFAULT_MAX_STALE_OFFLINE = DEFAULT_MAX_AGE * 24 * 7;// 默认离线缓存时间

    /**
     * 保存cookies拦截器
     */
    private static Interceptor SAVE_COOKIES_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            SharedPreferences sharedPreferences = SuperRandyApplicationContext.context
                    .getSharedPreferences("cookie_sp", Context.MODE_PRIVATE);
            Response originalResponse = chain.proceed(chain.request());
            // 如果cookie为空，保存cookie到sp文件
            if (CommonUtils.isNullOrEmpty(sharedPreferences.getString("cookie", ""))) {
                // 获取请求返回的cookies
                if (!CommonUtils.isNullOrEmpty(originalResponse.header("Set-Cookie")) &&
                        !originalResponse.header("Set-Cookie").isEmpty()) {
                    final StringBuffer cookieBuffer = new StringBuffer();

                    //todo save cookies
//                    Observable.fromArray(originalResponse.headers("Set-Cookie")).map()
//                    Observable.from(originalResponse.headers("Set-Cookie"))
//                            .map(new Func1<String, String>() {
//                                @Override
//                                public String call(String s) {
//                                    String[] cookieArray = s.split(";");
//                                    return cookieArray[0];
//                                }
//                            })
//                            .subscribe(new Action1<String>() {
//                                @Override
//                                public void call(String s) {
//                                    cookieBuffer.append(s).append(";");
//                                }
//                            });
                    KLog.e("save_cookie", cookieBuffer.toString());
                    // 保存cookies到sp文件
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cookie", cookieBuffer.deleteCharAt(cookieBuffer.length() - 1).toString());
                    editor.apply();
                }
            }

            return originalResponse;
        }
    };

    /**
     * 添加cookies拦截器
     */
    private static Interceptor ADD_COOKIES_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sharedPreferences = SuperRandyApplicationContext.context
                    .getSharedPreferences("cookie_sp", Context.MODE_PRIVATE);
            // todo add cookies
//            Observable.just(sharedPreferences.getString("cookie", ""))
//                    .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String s) {
//                            builder.addHeader("Cookie", s);
//                        }
//                    });
            KLog.e("add_cookie", sharedPreferences.getString("cookie", ""));
            return chain.proceed(builder.build());
        }
    };

    /**
     * request 拦截器定义
     */
    private static final Interceptor REQUEST_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            int maxStale = DEFAULT_MAX_STALE_ONLINE;
            //向服务期请求数据缓存1个小时
            CacheControl tempCacheControl = new CacheControl.Builder()
//                .onlyIfCached()
                    .maxStale(5, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
            return chain.proceed(request);
        }
    };

    /**
     * response 拦截器定义
     */
    private static final Interceptor RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //针对那些服务器不支持缓存策略的情况下，使用强制修改响应头，达到缓存的效果
            //响应拦截只不过是出于规范，向服务器发出请求，至于服务器搭不搭理我们我们不管他，我们在响应里面做手脚，有网没有情况下的缓存策略
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            int maxAge;
            // 缓存的数据
            if (!NetworkUtils.isConnected(SuperRandyApplicationContext.context)) {
                maxAge = DEFAULT_MAX_STALE_OFFLINE;
            } else {
                maxAge = DEFAULT_MAX_STALE_ONLINE;
            }
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };
    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor LOGGING_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                long t1 = System.nanoTime();
                Log.i("OkHttp:", String.format("Sending %s request %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers()));
                long t2 = System.nanoTime();
                Log.i("OkHttp:", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(buffer.clone().readString(charset));
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

    /**
     * 获取OkHttpClient实例
     *
     * @return OkHttpClient实例
     */
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {// 同步访问
                if (okHttpClient == null) {
                    // 网络请求相关缓存
                    File cacheFile = new File(SuperRandyApplicationContext.application.getCacheDir(), "responses");
                    Cache cache = new Cache(cacheFile, DEFAULT_CACHE_SIZE);
                    okHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            // 添加相关拦截器
                            .addInterceptor(SAVE_COOKIES_INTERCEPTOR)
                            .addInterceptor(ADD_COOKIES_INTERCEPTOR)
                            .addNetworkInterceptor(RESPONSE_INTERCEPTOR)
                            .addInterceptor(REQUEST_INTERCEPTOR)
                            .addInterceptor(LOGGING_INTERCEPTOR)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    /**
     * 获取retrofit实例
     *
     * @param baseUrl baseURL
     * @return retrofit实例
     */
    public static Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 创建Service
     *
     * @param baseUrl      base url
     * @param serviceClazz 定义的服务类（通常是接口）
     * @param <T>          泛形，对应与定义的服务类
     * @return 定义的服务类
     */
    public static <T> T createService(String baseUrl, Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }
}
