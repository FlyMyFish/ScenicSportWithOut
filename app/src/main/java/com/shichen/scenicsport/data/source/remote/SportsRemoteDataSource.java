package com.shichen.scenicsport.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.shichen.scenicsport.data.BaseResponse;
import com.shichen.scenicsport.data.Sport;
import com.shichen.scenicsport.data.SportParam;
import com.shichen.scenicsport.data.SportResponse;
import com.shichen.scenicsport.data.source.SportDataSource;
import com.shichen.scenicsport.util.GsonUtils;
import com.shichen.scenicsport.util.OkHttpUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class SportsRemoteDataSource implements SportDataSource {
    private static SportsRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;
    private final static Map<String, Sport> SPORTS_SERVICE_DATA;

    static {
        SPORTS_SERVICE_DATA = new LinkedHashMap<>();
    }

    public static SportsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SportsRemoteDataSource();
        }
        return INSTANCE;
    }

    private SportsRemoteDataSource() {

    }

    private static void addSport(String param_value, String param_des) {
        Sport newSport = new Sport(param_value, param_des);
        SPORTS_SERVICE_DATA.put(param_des, newSport);
    }

    SportParam param;

    @Override
    public void getAll(@NonNull final LoadSportsCallBack callBack) {
        if (param == null) {
            param = new SportParam("1", "");
        } else {
            if (param.getCurCall() != null) {
                if (param.getCurCall().isExecuted()) {
                    if (!param.getCurCall().isCanceled()) {
                        param.getCurCall().cancel();
                    }
                }
                param.getCurCall().cancel();
            }
            param.setPage("1");
            param.setKeyword("");
        }
        param.buildCall(OkHttpUtils.getInstance().client())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onDataNotAvailable(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        new BaseResponse(response).parseSuccess();
                        checkNotNull(response);
                        if (response.body() == null) {
                            callBack.onDataNotAvailable(new IllegalArgumentException("ResponseBody is null"));
                        } else {
                            String bodyStr = response.body().string();
                            if (TextUtils.isEmpty(bodyStr)) {
                                callBack.onDataNotAvailable(new IllegalArgumentException("BodyStr is null or empty"));
                            } else {
                                SportResponse sportResponse = GsonUtils.getInstance().get().fromJson(bodyStr, SportResponse.class);
                                if (sportResponse == null) {
                                    callBack.onDataNotAvailable(new IllegalArgumentException("BodyStr is null or empty"));
                                } else {
                                    if (sportResponse.getError().equals("0")) {
                                        if (sportResponse.getValues() == null) {
                                            callBack.onDataNotAvailable(new IllegalArgumentException("List is null"));
                                        } else {
                                            callBack.onSportsLoaded(sportResponse.getValues());
                                        }
                                    }else {
                                        callBack.onDataNotAvailable(new IllegalArgumentException("error = "+sportResponse.getError()+";msg = "+sportResponse.getMsg()));
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void getSports(@NonNull String curPage, @NonNull String keyword, @NonNull final LoadSportsCallBack callBack) {
        if (keyword==null){
            keyword="";
        }
        if (param == null) {
            param = new SportParam(curPage, keyword);
        } else {
            if (param.getCurCall() != null) {
                if (param.getCurCall().isExecuted()) {
                    if (!param.getCurCall().isCanceled()) {
                        param.getCurCall().cancel();
                    }
                }
                param.getCurCall().cancel();
            }
            param.setPage(curPage);
            param.setKeyword(keyword);
        }
        param.buildCall(OkHttpUtils.getInstance().client())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onDataNotAvailable(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        new BaseResponse(response).parseSuccess();
                        checkNotNull(response);
                        if (response.body() == null) {
                            callBack.onDataNotAvailable(new IllegalArgumentException("ResponseBody is null"));
                        } else {
                            String bodyStr = response.body().string();
                            if (TextUtils.isEmpty(bodyStr)) {
                                callBack.onDataNotAvailable(new IllegalArgumentException("BodyStr is null or empty"));
                            } else {
                                SportResponse sportResponse = GsonUtils.getInstance().get().fromJson(bodyStr, SportResponse.class);
                                if (sportResponse == null) {
                                    callBack.onDataNotAvailable(new IllegalArgumentException("BodyStr is null or empty"));
                                } else {
                                    if (sportResponse.getError().equals("0")) {
                                        if (sportResponse.getValues() == null) {
                                            callBack.onDataNotAvailable(new IllegalArgumentException("List is null"));
                                        } else {
                                            callBack.onSportsLoaded(sportResponse.getValues());
                                        }
                                    }else {
                                        callBack.onDataNotAvailable(new IllegalArgumentException("error = "+sportResponse.getError()+";msg = "+sportResponse.getMsg()));
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void getSport(@NonNull String param_value, @NonNull final GetSportCallBack callBack) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onDataNotAvailable(new IllegalArgumentException("Do not support api"));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveSport(@NonNull Sport sport) {
        Log.e(getClass().getSimpleName(),"saveSport",new IllegalArgumentException("Do not support api"));
    }

    @Override
    public void refreshSports() {
        Log.e(getClass().getSimpleName(),"refreshSports",new IllegalArgumentException("Do not support api"));
    }

    @Override
    public void deleteAllSports() {
        Log.e(getClass().getSimpleName(),"deleteAllSports",new IllegalArgumentException("Do not support api"));
    }

    @Override
    public void deleteSports(@NonNull String param_value) {
        Log.e(getClass().getSimpleName(),"deleteSports",new IllegalArgumentException("Do not support api"));
    }
}
