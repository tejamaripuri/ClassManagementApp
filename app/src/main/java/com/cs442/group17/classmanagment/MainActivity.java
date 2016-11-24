package com.cs442.group17.classmanagment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by USER on 13-11-16.
 */

public class MainActivity extends FragmentActivity implements ResultListener {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String collegePref = "collegePref";

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check college preference
        //if preference has been set, load the logo display fragment
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);


        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Welcome to Class Management Application",Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyResult(Bundle bundle) {
        String collegeID = bundle.getString(CollegePickerFragment.RESULT_COLLEGEID);

        Toast.makeText(this, "Logging in as a " + collegeID + " student!", Toast.LENGTH_LONG).show();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(collegePref, collegeID);

        //switch out fragement1
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
