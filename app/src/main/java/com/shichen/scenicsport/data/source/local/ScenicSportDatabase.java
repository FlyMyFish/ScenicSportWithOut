package com.shichen.scenicsport.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.shichen.scenicsport.data.Sport;

@Database(entities = {Sport.class}, version = 1, exportSchema = false)
public abstract class ScenicSportDatabase extends RoomDatabase {
    private static ScenicSportDatabase INSTANCE;

    public abstract SportsDao sportDao();

    private static final Object sLock = new Object();

    public static ScenicSportDatabase getInstance(@NonNull Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ScenicSportDatabase.class, "ScenicSports.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
