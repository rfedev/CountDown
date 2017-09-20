package com.rfe_contracts.countdown.CounterDB;

import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CounterEntity.class}, version = 1, exportSchema = false)
@TypeConverters({CounterConverters.class})
public abstract class CounterDb extends RoomDatabase {

    private static CounterDb INSTANCE;

    public abstract CounterDao counterModel();

    public static CounterDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, CounterDb.class, "Counters")
//                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            // TODO: dont let it run on the main thread.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
