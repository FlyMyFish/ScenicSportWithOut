package com.shichen.scenicsport.data.source;

import android.support.annotation.NonNull;

import com.shichen.scenicsport.data.Sport;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SportsRepository implements SportDataSource {
    private static SportsRepository INSTANCE = null;
    private final SportDataSource mSportRemoteDataSource;
    private final SportDataSource mSportLocalDataSource;
    boolean mCacheIsDirty = false;

    private SportsRepository(@NonNull SportDataSource sportRemoteDataSource,
                             @NonNull SportDataSource sportLocalDataSource) {
        mSportRemoteDataSource = checkNotNull(sportRemoteDataSource);
        mSportLocalDataSource = checkNotNull(sportLocalDataSource);
    }

    public static SportsRepository getInstance(SportDataSource sportRemoteDataSource,
                                               SportDataSource sportLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SportsRepository(sportRemoteDataSource, sportLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    @Override
    public void getAll(@NonNull final LoadSportsCallBack callBack) {
        checkNotNull(callBack);

        if (mCacheIsDirty) {
            getSportsFromRemoteDataSource("1", "", callBack);
        } else {
            mSportLocalDataSource.getAll(new LoadSportsCallBack() {
                @Override
                public void onSportsLoaded(List<Sport> sports) {
                    callBack.onSportsLoaded(sports);
                }

                @Override
                public void onDataNotAvailable(Exception e) {
                    getSportsFromRemoteDataSource("1", "", callBack);
                }
            });
        }
    }

    @Override
    public void getSports(@NonNull final String curPage, @NonNull final String keyword, @NonNull final LoadSportsCallBack callBack) {
        checkNotNull(callBack);

        if (mCacheIsDirty) {
            getSportsFromRemoteDataSource(curPage, keyword, callBack);
        } else {
            mSportLocalDataSource.getSports(curPage, keyword, new LoadSportsCallBack() {
                @Override
                public void onSportsLoaded(List<Sport> sports) {
                    callBack.onSportsLoaded(new ArrayList<Sport>(sports));
                }

                @Override
                public void onDataNotAvailable(Exception e) {
                    getSportsFromRemoteDataSource(curPage, keyword, callBack);
                }
            });
        }
    }

    @Override
    public void getSport(@NonNull final String param_value, @NonNull final GetSportCallBack callBack) {
        checkNotNull(param_value);
        checkNotNull(callBack);

        mSportLocalDataSource.getSport(param_value, new GetSportCallBack() {
            @Override
            public void onSportLoaded(Sport sport) {
                callBack.onSportLoaded(sport);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mSportRemoteDataSource.getSport(param_value, new GetSportCallBack() {
                    @Override
                    public void onSportLoaded(Sport sport) {
                        callBack.onSportLoaded(sport);
                    }

                    @Override
                    public void onDataNotAvailable(Exception e) {
                        callBack.onDataNotAvailable(e);
                    }
                });
            }
        });
    }

    @Override
    public void saveSport(@NonNull Sport sport) {
        checkNotNull(sport);
        mSportRemoteDataSource.saveSport(sport);
        mSportLocalDataSource.saveSport(sport);

    }

    @Override
    public void refreshSports() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllSports() {
        mSportRemoteDataSource.deleteAllSports();
        mSportLocalDataSource.deleteAllSports();
    }

    @Override
    public void deleteSports(@NonNull String param_value) {
        mSportRemoteDataSource.deleteSports(param_value);
        mSportLocalDataSource.deleteSports(param_value);
    }

    private void getSportsFromRemoteDataSource(@NonNull String curPage, @NonNull String keyword, @NonNull final LoadSportsCallBack callBack) {
        mSportRemoteDataSource.getSports(curPage, keyword, new LoadSportsCallBack() {
            @Override
            public void onSportsLoaded(List<Sport> sports) {
                refreshLocalDataSource(sports);
                callBack.onSportsLoaded(sports);
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                callBack.onDataNotAvailable(e);
            }
        });
    }

    private void refreshLocalDataSource(List<Sport> sports) {
        mSportLocalDataSource.deleteAllSports();
        for (Sport sport : sports) {
            mSportLocalDataSource.saveSport(sport);
        }
    }
}
