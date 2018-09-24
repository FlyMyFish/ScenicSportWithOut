package com.shichen.scenicsport.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.shichen.scenicsport.data.Sport;

import java.util.List;

@Dao
public interface SportsDao {
    @Query("SELECT * FROM SPORTS")
    List<Sport> getAll();
    @Query("SELECT * FROM SPORTS WHERE des LIKE '%'+:keyword+'%' LIMIT (:curPage -1)*10,10 ")
    List<Sport> getSports(String curPage, String keyword);

    @Query("SELECT * FROM SPORTS WHERE entryid=:param_value")
    Sport getSportByParamValue(@NonNull String param_value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSport(@NonNull Sport sport);

    @Update
    int updataSport(@NonNull Sport sport);

    @Query("DELETE FROM SPORTS WHERE entryid=:param_value")
    int deleteSportByParamValue(@NonNull String param_value);

    @Query("DELETE FROM SPORTS")
    void deleteSports();
}
