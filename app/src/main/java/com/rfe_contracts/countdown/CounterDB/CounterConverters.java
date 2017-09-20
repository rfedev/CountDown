package com.rfe_contracts.countdown.CounterDB;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Richard on 14/09/2017.
 */

public class CounterConverters {

    @TypeConverter
    public static Date longToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }
}
