package com.cs442.group17.classmanagment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.IntegerRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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

        sharedPreferences = getSharedPreferences(LoginFragment.MyPREFERENCES, MODE_PRIVATE);
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
                dbHandler.ApplyLeave(sharedPreferences.getInt(LoginFragment.userIdPref, 0), date, dbHandler.getApproverBySubjectId(subjectId));

                /*int day = 1;
                int month = 1;
                int year = 2001;
                ArrayList<Integer> dayEnt = new ArrayList<>();


                if(!date.equals(""))
                    for (String s: date.split("/")) {
                        dayEnt.add(Integer.parseInt(s));
                    }
                day = dayEnt.get(0);
                month = dayEnt.get(1);
                year = dayEnt.get(2);
                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(year, month, day, 9, 00);
                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(year, month, day, 9, 00);
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, "Leave on " + date);
                values.put(CalendarContract.Events.DESCRIPTION, "Leave Status: Applied");
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
                Uri uri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (ActivityCompat.checkSelfPermission(CalenderActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                }

// get the event ID that is the last element in the Uri
                long eventID = Long.parseLong(uri.getLastPathSegment());*/



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

        findViewById(R.id.activity_calender).setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(CalenderActivity.this, RosterActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(CalenderActivity.this, CommunicateActivity.class);
                intent.putExtra("subjectId", subjectId);
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