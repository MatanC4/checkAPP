package com.example.matka.check.Date;

import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.DatePicker;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment  {

    String titleText = "Lets set a due date for this Check";
    private Calendar c;

    public Calendar getC() {
        return c;
    }

    public void setC(Calendar c) {
        this.c = c;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        setTitleText(titleText);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) this, year, month, day);
    }


    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String title) {
        this.titleText = title;
    }


}