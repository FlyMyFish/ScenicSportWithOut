package com.shichen.scenicsport.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

public class OkHttpUtils {

    private static final long READ_TIME_OUT_VALUE=15;
    private static final long WRITE_TIME_OUT_VALUE=15;

    private OkHttpClient mOkHttpClient;

    private OkHttpUtils(){
        initClient();
    }

    private void initClient(){
        mOkHttpClient=new OkHttpClient.Builder()
                //.addInterceptor(new GZipRequestInterceptor())
                .readTimeout(READ_TIME_OUT_VALUE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_VALUE,TimeUnit.SECONDS)
                .build();
    }
    public static OkHttpUtils getInstance(){
        return OkHttpHolder.sInstance;
    }

    private static class OkHttpHolder{
        private static final OkHttpUtils sInstance=new OkHttpUtils();
    }

    public OkHttpClient client(){
        return mOkHttpClient;
    }

    /**
     * Gzip压缩
     */
    public static class GZipRequestInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest=chain.request();
            if (originalRequest.body()==null||originalRequest.header("Content-Encoding")!=null){
                return chain.proceed(originalRequest);
            }
            Request compressRequest=originalRequest.newBuilder()
                    .header("Content-Encoding","gzip")
                    .header("Content-Type","text/html; charset=UTF-8")
                    .method(originalRequest.method(),gzip(originalRequest.body()))
                    .build();
            return chain.proceed(compressRequest);
        }
        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1; // 无法提前知道压缩后的数据大小
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }
}
