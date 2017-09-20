package com.rfe_contracts.countdown.CounterDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CounterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCounter(CounterEntity counter);

    @Query("select * from Counters")
    public List<CounterEntity> getAllCounters();

    @Query("select * from Counters where id = :id")
    public CounterEntity getCounter(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCounter(CounterEntity counter);

    @Query("delete from Counters")
    void removeAllCounters();

    @Query("delete from Counters where id = :id")
    void removeCounter(long id);
}
