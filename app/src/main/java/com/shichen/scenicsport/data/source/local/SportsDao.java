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
    @Query(value = "SELECT * FROM SPORTS")
    List<Sport> getAll();

    @Query(value = "SELECT * FROM SPORTS WHERE des LIKE '%'+:keyword+'%' LIMIT (:curPage -1)*10,10 ")
    List<Sport> getSports(String curPage, String keyword);

    @Query(value = "SELECT * FROM SPORTS WHERE entryid = :param_value")
    Sport getSportByParamValue(String param_value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSport(Sport sport);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updataSport(Sport sport);

    @Query(value = "DELETE FROM SPORTS WHERE entryid=:param_value")
    int deleteSportByParamValue(String param_value);

    @Query(value = "DELETE FROM SPORTS")
    void deleteSports();
}
