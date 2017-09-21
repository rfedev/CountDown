package com.rfe_contracts.countdown.CounterDB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
@TypeConverters(CounterConverters.class)
public interface CounterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCounter(CounterEntity counter);

    @Query("select * from Counters")
    public LiveData<List<CounterEntity>> getAllCounters();

    @Query("select * from Counters where id = :id")
    public CounterEntity getCounter(Long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCounter(CounterEntity counter);

    @Query("delete from Counters")
    void removeAllCounters();

    @Query("delete from Counters where id = :id")
    void removeCounter(long id);
}
