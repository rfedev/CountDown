package com.rfe_contracts.countdown;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rfe_contracts.countdown.CounterDB.CounterDb;
import com.rfe_contracts.countdown.CounterDB.CounterEntity;

import java.util.List;

/**
 * The ViewModel class is designed to store and manage UI-related data so that the data survives configuration changes such as screen rotations.
 * Takes some of the work away from the activity/fragment
 */

public class CounterViewModel extends AndroidViewModel {

    private final LiveData<List<CounterEntity>> allCounters;

    private CounterDb counterDb;

    public CounterViewModel(Application application) {
        super(application);

        counterDb = CounterDb.getDatabase(application);
        allCounters = counterDb.counterModel().getAllCounters();
    }

    public LiveData<List<CounterEntity>> getAllCounters(){
        return this.allCounters;
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
