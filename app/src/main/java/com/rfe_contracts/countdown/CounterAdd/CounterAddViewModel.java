package com.rfe_contracts.countdown.CounterAdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.rfe_contracts.countdown.CounterDB.CounterDb;
import com.rfe_contracts.countdown.CounterDB.CounterEntity;

import java.util.concurrent.ExecutionException;

/**
 * Created by Richard on 21/09/2017.
 */

public class CounterAddViewModel extends AndroidViewModel {

    private CounterDb counterDB;

    public CounterAddViewModel(Application application) {
        super(application);
        counterDB = CounterDb.getDatabase(application);
    }

    public CounterEntity getCounter(final Long id) {
        CounterEntity counterEntity = null;
        try {
            counterEntity = new getCounterAsyncTask(counterDB).execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return counterEntity;
    }

    public void addCounter(final CounterEntity counterEntity) {
        new addCounterAsyncTask(counterDB).execute(counterEntity);
    }

    public void updateCounter(final CounterEntity counterEntity) {
        new updateCounterAsyncTask(counterDB).execute(counterEntity);
    }

    private static class getCounterAsyncTask extends AsyncTask<Long, Void, CounterEntity> {

        private CounterDb counterDB;

        getCounterAsyncTask(CounterDb counterDB) {
            this.counterDB = counterDB;
        }

        @Override
        protected CounterEntity doInBackground(final Long... params) {
            CounterEntity counterEntity = counterDB.counterModel().getCounter(params[0]);
            return counterEntity;
        }
    }

    private static class addCounterAsyncTask extends AsyncTask<CounterEntity, Void, Void> {

        private CounterDb counterDB;

        addCounterAsyncTask(CounterDb counterDB) {
            this.counterDB = counterDB;
        }

        @Override
        protected Void doInBackground(final CounterEntity... params) {
            counterDB.counterModel().addCounter(params[0]);
            return null;
        }
    }

    private static class updateCounterAsyncTask extends AsyncTask<CounterEntity, Void, Void> {

        private CounterDb counterDB;

        updateCounterAsyncTask(CounterDb counterDB) {
            this.counterDB = counterDB;
        }

        @Override
        protected Void doInBackground(final CounterEntity... params) {
            counterDB.counterModel().updateCounter(params[0]);
            return null;
        }
    }



}
