package com.shichen.scenicsport.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.Lists;
import com.shichen.scenicsport.data.source.SportDataSource;

import java.util.LinkedHashMap;
import java.util.Map;

public class FakeSportsRemoteDataSource implements SportDataSource {
    private static FakeSportsRemoteDataSource INSTANCE;
    private static final Map<String,Sport> SPORTS_SERVICE_DATA=new LinkedHashMap<>();

    private FakeSportsRemoteDataSource(){

    }

    public static FakeSportsRemoteDataSource getInstance(){
        if (INSTANCE==null){
            INSTANCE=new FakeSportsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAll(@NonNull LoadSportsCallBack callBack) {
        callBack.onSportsLoaded(Lists.<Sport>newArrayList(SPORTS_SERVICE_DATA.values()));
    }

    @Override
    public void getSports(@NonNull String curPage, @NonNull String keyword, @NonNull LoadSportsCallBack callBack) {
        callBack.onSportsLoaded(Lists.<Sport>newArrayList(SPORTS_SERVICE_DATA.values()));
    }

    @Override
    public void getSport(@NonNull String param_value, @NonNull GetSportCallBack callBack) {
        Sport sport=SPORTS_SERVICE_DATA.get(param_value);
        callBack.onSportLoaded(sport);
    }

    @Override
    public void saveSport(@NonNull Sport sport) {
        SPORTS_SERVICE_DATA.put(sport.getParam_value(),sport);
    }

    @Override
    public void refreshSports() {

    }

    @Override
    public void deleteAllSports() {
        SPORTS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteSports(@NonNull String param_value) {
        SPORTS_SERVICE_DATA.remove(param_value);
    }

    @VisibleForTesting
    public void addSports(Sport... sports){
        for (Sport sport:sports){
            SPORTS_SERVICE_DATA.put(sport.getParam_value(),sport);
        }
    }
}
