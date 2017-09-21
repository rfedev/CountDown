package com.rfe_contracts.countdown.CounterAdd;

import android.app.FragmentTransaction;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfe_contracts.countdown.Constants;
import com.rfe_contracts.countdown.CounterDB.CounterDb;
import com.rfe_contracts.countdown.CounterDB.CounterEntity;
import com.rfe_contracts.countdown.EventDateDialog;
import com.rfe_contracts.countdown.EventTimeDialog;
import com.rfe_contracts.countdown.Functions;
import com.rfe_contracts.countdown.R;

import java.util.Date;


public class CounterAddActivity extends LifecycleActivity {

    private long id;
    private EditText name;
    private TextView dateText;
    private TextView timeText;
    private EditText desc;
    private EditText note;

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
        if (id != -1) {
            CounterEntity counterEntity = counterAddViewModel.getCounter(id);
            name.setText(counterEntity.name);
            dateText.setText(Functions.getStringFromDate(counterEntity.date, Constants.DATE_STRING_FORMAT));
            timeText.setText(Functions.getStringFromTime(counterEntity.date));
            desc.setText(counterEntity.desc);
            note.setText(counterEntity.note);
        }

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
                EventDateDialog eventDialog = new EventDateDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                eventDialog.show(ft,"DatePicker");
            }
        });

        //Pick the time
        final TextView timeText = (TextView) findViewById(R.id.addCounter_eventTime);
        timeText.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventTimeDialog eventDialog = new EventTimeDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                eventDialog.show(ft,"TimePicker");
            }
        });
    }


}
