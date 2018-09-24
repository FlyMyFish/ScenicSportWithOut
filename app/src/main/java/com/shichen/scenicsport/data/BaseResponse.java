package com.shichen.scenicsport.data;

import android.util.Log;

import com.shichen.scenicsport.util.GsonUtils;

import okhttp3.Protocol;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseResponse {
    int code;
    Protocol protocol;
    String message;
    public BaseResponse(Response response){
        checkNotNull(response);
        code=response.code();
        protocol=response.protocol();
        message=response.message();
    }

    public int getCode() {
        return code;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getMessage() {
        return message;
    }

    public void parseSuccess(){
        Log.e("onResponse", GsonUtils.getInstance().get().toJson(this));
    }
}
