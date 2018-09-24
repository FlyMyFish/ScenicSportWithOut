package com.shichen.scenicsport;

import android.content.Context;
import android.support.annotation.NonNull;

import com.shichen.scenicsport.data.FakeSportsRemoteDataSource;
import com.shichen.scenicsport.data.source.SportsRepository;
import com.shichen.scenicsport.data.source.local.ScenicSportDatabase;
import com.shichen.scenicsport.data.source.local.SportsLocalDataSource;
import com.shichen.scenicsport.data.source.remote.SportsRemoteDataSource;
import com.shichen.scenicsport.util.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {
    public static SportsRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        ScenicSportDatabase database = ScenicSportDatabase.getInstence(context);
        return SportsRepository.getInstance(FakeSportsRemoteDataSource.getInstance(),
                SportsLocalDataSource.getInstance(new AppExecutors(),
                        database.sportDao()));
    }
}
