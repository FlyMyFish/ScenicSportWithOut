package com.shichen.scenicsport.data.source.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.shichen.scenicsport.data.Sport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class SportsDaoTest {
    private static final Sport SPORT=new Sport("1234","yyyyxx");

    private ScenicSportDatabase mDatabase;

    @Before
    public void initDb(){
        mDatabase= Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ScenicSportDatabase.class).build();
    }

    @After
    public void closeDb(){
        mDatabase.close();
    }

    @Test
    public void insertSportAndGetById(){
        mDatabase.sportDao().insertSport(SPORT);
        Sport loaded=mDatabase.sportDao().getSportByParamValue(SPORT.getParam_value());
        assertSport(loaded,SPORT.getParam_value(),SPORT.getParam_desc());
    }

    @Test
    public void insertSportReplaceOnConflict(){
        mDatabase.sportDao().insertSport(SPORT);
        Sport newSport=new Sport("1234","yyyyxx1");
        mDatabase.sportDao().insertSport(newSport);
        Sport loaded=mDatabase.sportDao().getSportByParamValue(SPORT.getParam_value());

        assertSport(loaded,"1234","yyyyxx1");
    }

    @Test
    public void insertSportAndGetSports(){
        mDatabase.sportDao().insertSport(SPORT);
        List<Sport> sports=mDatabase.sportDao().getAll();
        assertThat(sports.size(),is(1));
        assertSport(sports.get(0),"1234","yyyyxx");
    }

    @Test
    public void updateSportAndGetByParamValue(){
        mDatabase.sportDao().insertSport(SPORT);
        Sport newSport=new Sport("1234","yyyyxx1");
        mDatabase.sportDao().updataSport(newSport);

        Sport loaded=mDatabase.sportDao().getSportByParamValue(SPORT.getParam_value());

        assertSport(loaded,SPORT.getParam_value(),SPORT.getParam_desc());
    }

    @Test
    public void  deleteSportByParamValueAndGettingSports(){
        mDatabase.sportDao().insertSport(SPORT);
        mDatabase.sportDao().deleteSportByParamValue(SPORT.getParam_value());

        List<Sport> sports=mDatabase.sportDao().getAll();

        assertThat(sports.size(),is(1));
    }

    @Test
    public void deleteSportsAndGettingSports(){
        mDatabase.sportDao().insertSport(SPORT);
        mDatabase.sportDao().deleteSports();
        List<Sport> sports=mDatabase.sportDao().getAll();
        assertThat(sports.size(),is(1));
    }

    private void assertSport(Sport sport,String param_value,String param_desc){
        assertThat(sport,notNullValue());
        assertThat(sport.getParam_value(),is(param_value));
        assertThat(sport.getParam_desc(),is(param_desc));
    }
}
