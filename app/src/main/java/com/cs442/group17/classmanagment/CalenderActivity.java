package com.cs442.group17.classmanagment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentUris;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CalenderActivity extends AppCompatActivity {

    static TextView mDateDisplay;
    private Button mPickDate;
    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;
    int subjectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        sharedPreferences  = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);


        mDateDisplay = (TextView) findViewById(R.id.textView4);
        mPickDate = (Button) findViewById(R.id.button2);
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // showDialog(DATE_DIALOG_ID);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        Button btnApply = (Button) findViewById(R.id.button3);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mDateDisplay.getText().toString();
                dbHandler.ApplyLeave(sharedPreferences.getInt(LoginActivity.userIdPref,0), date, dbHandler.getApproverBySubjectId(subjectId));
                Toast.makeText(CalenderActivity.this, "Applied!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnEvents = (Button) findViewById(R.id.button5);
        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);
            }
        });



    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker dpResult;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            mDateDisplay .setText(String.valueOf(day) + "/"
                    + String.valueOf(month + 1) + "/" + String.valueOf(year));
        }
    }



}