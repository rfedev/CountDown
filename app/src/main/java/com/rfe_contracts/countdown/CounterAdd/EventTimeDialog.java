package com.rfe_contracts.countdown.CounterAdd;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rfe_contracts.countdown.Functions;
import com.rfe_contracts.countdown.R;

import java.util.Calendar;

public class EventTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

//        final Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
        int hour = getArguments().getInt("hour");
        int minute = getArguments().getInt("minute");

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hour, int minute){

        //Save the selected date and pass it back to the calling activity
        CounterAddActivity activity = (CounterAddActivity) getActivity();
        activity.setDefaultTime(hour, minute);

        TextView timeTextView = (TextView) getActivity().findViewById(R.id.addCounter_eventTime);
        timeTextView.setText(Functions.getStringFromTime(hour,minute));
    }

}
