package com.shichen.scenicsport.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.shichen.scenicsport.data.Sport;
import com.shichen.scenicsport.data.source.SportDataSource;
import com.shichen.scenicsport.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SportsLocalDataSource implements SportDataSource {

    private static volatile SportsLocalDataSource INSTANCE;
    private SportsDao mSportsDao;
    private AppExecutors mAppExecutors;

    private SportsLocalDataSource(@NonNull AppExecutors appExecutors,
                                  @NonNull SportsDao sportsDao) {
        mAppExecutors = appExecutors;
        mSportsDao = sportsDao;
    }

    public static SportsLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                    @NonNull SportsDao sportsDao) {
        if (INSTANCE == null) {
            synchronized (SportsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SportsLocalDataSource(appExecutors, sportsDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getAll(@NonNull final LoadSportsCallBack callBack) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                final List<Sport> sports=mSportsDao.getAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (sports.isEmpty()){
                            callBack.onDataNotAvailable(new IllegalArgumentException("Database don't has data"));
                        }else {
                            callBack.onSportsLoaded(sports);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSports(@NonNull final String curPage, @NonNull final String keyword, @NonNull final LoadSportsCallBack callBack) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                final List<Sport> sports=mSportsDao.getSports(curPage,keyword);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (sports.isEmpty()){
                            callBack.onDataNotAvailable(new IllegalArgumentException("Database don't has data"));
                        }else {
                            callBack.onSportsLoaded(sports);
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSport(@NonNull final String param_value, @NonNull final GetSportCallBack callBack) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                final Sport sport=mSportsDao.getSportByParamValue(param_value);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (sport!=null){
                            callBack.onSportLoaded(sport);
                        }else {
                            callBack.onDataNotAvailable(new IllegalArgumentException("Database don't has data"));
                        }
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveSport(@NonNull final Sport sport) {
        checkNotNull(sport);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                mSportsDao.insertSport(sport);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshSports() {

    }

    @Override
    public void deleteAllSports() {
        Log.e(getClass().getSimpleName(),"deleteAllSports",new IllegalArgumentException("Do not support api"));
    }

    @Override
    public void deleteSports(@NonNull final String param_value) {
        Runnable deleteRunnable=new Runnable() {
            @Override
            public void run() {
                mSportsDao.deleteSportByParamValue(param_value);
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance(){
        INSTANCE=null;
    }
}
