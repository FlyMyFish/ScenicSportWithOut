package com.shichen.scenicsport.data;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 景点地区的请求参数
 */
public class SportParam implements ParamToFormData {
    public static final String SPORT_URL = "http://www.idataapi.cn/api/get_api_range_values";
    private String api_id = "283";
    private String param_code = "cityid";
    private String page;
    private String keyword = "";

    public SportParam(String page, String keyword) {
        this.page = page;
        if (keyword!=null){
            this.keyword = keyword;
        }
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public MediaType buildMediaType() {
        return MediaType.parse("text/html; charset=UTF-8");
    }

    @Override
    public RequestBody buildRequestBody() {
        checkNotNull(page);
        checkNotNull(keyword);
        return new FormBody.Builder()
                .add("api_id", api_id)
                .add("param_code", param_code)
                .add("page", page)
                .add("keyword", keyword)
                .build();
    }

    @Override
    public Request buildRequest() {
        return new Request.Builder()
                .url(SPORT_URL)
                .post(buildRequestBody())
                .build();
    }

    private Call curCall;

    @Override
    public Call buildCall(OkHttpClient client) {
        checkNotNull(client);
        curCall=client.newCall(buildRequest());
        return curCall;
    }

    public Call getCurCall() {
        return curCall;
    }
}
