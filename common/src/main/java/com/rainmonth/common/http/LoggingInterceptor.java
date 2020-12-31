package com.rainmonth.common.http;

import com.rainmonth.utils.ZipHelper;
import com.rainmonth.utils.log.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {

    public LoggingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

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
            if (contentType != null && "json".equals(contentType.subtype())) {
                LogUtils.json("JsonResult", bodyString);
            }
        }

        return originalResponse;
    }
}
