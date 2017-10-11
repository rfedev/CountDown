package com.rfe_contracts.countdown.CounterAdd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rfe_contracts.countdown.Constants;
import com.rfe_contracts.countdown.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Richard on 05/09/2017.
 */

public class EventDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {

//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        //Save the selected date and pass it back to the calling activity
        CounterAddActivity activity = (CounterAddActivity) getActivity();
        activity.setDefaultDate(year, month, day);

        TextView dateTextView = (TextView) getActivity().findViewById(R.id.addCounter_eventDate);
        String dateText = day + "-" + (month + 1) + "-" + year;

        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outFormat = new SimpleDateFormat(Constants.DATE_STRING_FORMAT);
        try {
            Date eventDate = inFormat.parse(dateText);
            dateText = outFormat.format(eventDate);
        } catch (ParseException e) {
            dateText = "Invalid Date";
        }
        dateTextView.setText(dateText);
    }

}
