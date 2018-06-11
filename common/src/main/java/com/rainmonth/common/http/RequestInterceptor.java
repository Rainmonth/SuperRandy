package com.rainmonth.common.http;

import com.rainmonth.common.utils.ZipHelper;
import com.socks.library.KLog;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络请求拦截器
 * Created by RandyZhang on 2018/6/1.
 */
@Singleton
public class RequestInterceptor implements Interceptor {
    private GlobalHttpHandler mHandler;

    @Inject
    public RequestInterceptor(GlobalHttpHandler handler) {
        this.mHandler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mHandler != null) {
            request = mHandler.onHttpRequestBefore(chain, request);
        }

        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
        }

        Response originalResponse = chain.proceed(request);

        // 读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        String encoding = originalResponse.headers().get("Content-Encoding");
        Buffer clone = buffer.clone();
        String bodyString;

        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            bodyString = ZipHelper.decompressForGzip(clone.readByteArray());
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {
            bodyString = ZipHelper.decompressToStringForZlib(clone.readByteArray());
        } else {
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
            KLog.json("Randy", bodyString);
        }
        if (mHandler != null) {
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);
        }

        return originalResponse;
    }
}
