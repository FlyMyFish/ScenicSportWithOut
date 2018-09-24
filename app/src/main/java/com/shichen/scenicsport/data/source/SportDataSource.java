package com.shichen.scenicsport.data.source;

import android.support.annotation.NonNull;

import com.shichen.scenicsport.data.Sport;

import java.util.List;

public interface SportDataSource {
    interface LoadSportsCallBack{
        void onSportsLoaded(List<Sport> sports);
        void onDataNotAvailable(Exception e);
    }

    interface GetSportCallBack{
        void onSportLoaded(Sport sport);
        void onDataNotAvailable(Exception e);
    }

    void getAll(@NonNull LoadSportsCallBack callBack);
    void getSports(@NonNull String curPage, @NonNull String keyword, @NonNull LoadSportsCallBack callBack);
    void getSport(@NonNull String param_value, @NonNull GetSportCallBack callBack);
    void saveSport(@NonNull Sport sport);
    void refreshSports();
    void deleteAllSports();
    void deleteSports(@NonNull String param_value);
}
