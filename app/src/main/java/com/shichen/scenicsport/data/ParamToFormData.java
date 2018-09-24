package com.shichen.scenicsport.data;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 发起请求时的参数，实现该类以便进行post请求
 */
public interface ParamToFormData {
    MediaType buildMediaType();
    RequestBody buildRequestBody();
    Request buildRequest();
    Call buildCall(OkHttpClient client);
}
