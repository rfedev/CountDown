package com.rfe_contracts.countdown.CounterAdd;

import android.app.FragmentTransaction;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfe_contracts.countdown.Constants;
import com.rfe_contracts.countdown.CounterDB.CounterEntity;
import com.rfe_contracts.countdown.Functions;
import com.rfe_contracts.countdown.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CounterAddActivity extends LifecycleActivity {

    private long id;
    private EditText name;
    private TextView dateText;
    private TextView timeText;
    private EditText desc;
    private EditText note;

    //Bundles to store the default date for the date/time pickers
    private Bundle dateArgs = new Bundle();
    private Bundle timeArgs = new Bundle();

    private CounterAddViewModel counterAddViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        name = (EditText) findViewById(R.id.addCounter_eventName);
        dateText = (TextView) findViewById(R.id.addCounter_eventDate);
        timeText = (TextView) findViewById(R.id.addCounter_eventTime);
        desc = (EditText) findViewById(R.id.addCounter_eventDesc);
        note = (EditText) findViewById(R.id.addCounter_eventNote);


        counterAddViewModel = ViewModelProviders.of(this).get(CounterAddViewModel.class);

        Intent intent = new Intent(getIntent());
        id = intent.getLongExtra("id",-1);

        //If an ID was passed, load the data from the database
        Date defaultDate; //used to the bundle arguments so the pickers remember the previously selected date/time
        if (id != -1) {
            CounterEntity counterEntity = counterAddViewModel.getCounter(id);
            name.setText(counterEntity.name);
            defaultDate = counterEntity.date;
            desc.setText(counterEntity.desc);
            note.setText(counterEntity.note);
        } else {
            //If creating a new event, just put the current time in the date/time views.
            defaultDate = Calendar.getInstance().getTime();
        }

        //set the default date/time
        dateText.setText(Functions.getStringFromDate(defaultDate, Constants.DATE_STRING_FORMAT));
        timeText.setText(Functions.getStringFromTime(defaultDate));
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(defaultDate);
        setDefaultDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        setDefaultTime(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));


        //Cancel the activity
        final ImageView close = (ImageView) findViewById(R.id.addCounter_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Save the activity
        final TextView save = (TextView) findViewById(R.id.addCounter_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Date (including time)
                Date eventDate = Functions.getDateFromString(dateText.getText().toString() + " " + timeText.getText().toString(), Constants.DATE_STRING_FORMAT + " " + Constants.TIME_STRING_FORMAT);

                //Set a default name if it wasn't entered
                if (name.getText().toString().equals("")) {
                    name.setText("(New Event)");
                }

                //Save new record or update if and id was passed
                if (id == -1) {
                    //save new record
                    CounterEntity build = CounterEntity.builder().setName(name.getText().toString()).setDate(eventDate).setDesc(desc.getText().toString())
                            .setCategory("").setLocation("").setNote(note.getText().toString()).setArchive(false).build();
                    counterAddViewModel.addCounter(build);
                } else {
                    //Update old record
                    CounterEntity build = CounterEntity.builder().setId(id).setName(name.getText().toString()).setDate(eventDate).setDesc(desc.getText().toString())
                            .setCategory("").setLocation("").setNote(note.getText().toString()).setArchive(false).build();
                    counterAddViewModel.updateCounter(build);
                }
                finish();
            }
        });

        // TODO: 18/09/2017 check if this is needed for more than the event name clearfocus
        //Hide the keyboard if clicked outside the keyboard
        findViewById(R.id.addCounter_MainLayout).setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event){
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(R.id.addCounter_MainLayout).getWindowToken(),0);
                findViewById(R.id.addCounter_eventName).clearFocus();
                return false;
            }
        });


        //Pick the date
        final TextView dateText = (TextView) findViewById(R.id.addCounter_eventDate);
        dateText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                EventDateDialog eventDialog = new EventDateDialog();
                eventDialog.setArguments(dateArgs);
                eventDialog.show(ft,"DatePicker");
            }
        });

        //Pick the time
        final TextView timeText = (TextView) findViewById(R.id.addCounter_eventTime);
        timeText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                EventTimeDialog eventDialog = new EventTimeDialog();
                eventDialog.setArguments(timeArgs);
                eventDialog.show(ft,"TimePicker");
            }
        });


    }

    public void setDefaultDate(int year, int month, int day){
        dateArgs.putInt("year",year);
        dateArgs.putInt("month",month);
        dateArgs.putInt("day",day);
    }
    public void setDefaultTime(int hour, int minute){
        timeArgs.putInt("hour",hour);
        timeArgs.putInt("minute",minute);
    }

}
