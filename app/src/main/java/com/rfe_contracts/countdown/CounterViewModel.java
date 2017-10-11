package com.rfe_contracts.countdown;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rfe_contracts.countdown.CounterDB.CounterDb;
import com.rfe_contracts.countdown.CounterDB.CounterEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data so that the data survives configuration changes such as screen rotations.
 * Takes some of the work away from the activity/fragment
 *
 * This viewmodel is handling the tasks for the counter database.
 * As getAllCounters is LiveData, the Room database is responsible for providing the data.
 * The deleteCounter is not observable and therefore needs to be run via an AsyncTask
 */

public class CounterViewModel extends AndroidViewModel {

    private final LiveData<List<CounterEntity>> allCounters;
    private final LiveData<List<CounterEntity>> countersPast;
    private final LiveData<List<CounterEntity>> countersFuture;

    private CounterDb counterDb;

    public CounterViewModel(Application application) {
        super(application);

        counterDb = CounterDb.getDatabase(application);
        allCounters = counterDb.counterModel().getAllCounters();

        Date date = Calendar.getInstance().getTime();
        countersPast = counterDb.counterModel().getCountersBeforeDate(date);
        countersFuture = counterDb.counterModel().getCountersAfterDate(date);
    }

    public LiveData<List<CounterEntity>> getAllCounters(){
        return this.allCounters;
    }

    public LiveData<List<CounterEntity>> getCountersPast(){
        return this.countersPast;
    }

    public LiveData<List<CounterEntity>> getCountersFuture(){
        return this.countersFuture;
    }


    public void deleteCounter(CounterEntity counterEntity){
        new DeleteCounterAsyncTask(counterDb).execute(counterEntity);
    }


    private static class DeleteCounterAsyncTask extends AsyncTask<CounterEntity,Void,Void>{

        private CounterDb counterDb;

        public DeleteCounterAsyncTask(CounterDb counterDb) {
            this.counterDb = counterDb;
        }

        @Override
        protected Void doInBackground(final CounterEntity... counterEntity) {
            counterDb.counterModel().removeCounter(counterEntity[0].id);
            return null;
        }
    }

}
