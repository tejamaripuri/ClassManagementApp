package com.cs442.group17.classmanagment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.cs442.group17.classmanagment.SplashScreen.servicePref;

public class LoginActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    EditText editTextEmail, editTextPass;
    MyDBHandler dbHandler;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userNamePref = "userNamePref";
    public static final String userIdPref = "userIdPref";
    public static final String userEmailPref = "userEmailPref";
    public static final String userRoleIdPref = "userRoleIdPref";
    public static final String userCollIdPref = "userCollIdPref";
    SharedPreferences sharedpreferences;
    int roleId;
    int collId;
    String collegeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Welcome to Class Management Application",Snackbar.LENGTH_LONG).show();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collegeName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        // Spinner Drop down elements
        List<String> colleges;
        colleges = dbHandler.getColleges();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colleges);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collId = dbHandler.getCollegeIdByCollegeName(collegeName);
                if(editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Email and Password are Mandatory.", Toast.LENGTH_SHORT).show();
                }
                else if(dbHandler.checkLogin(editTextEmail.getText().toString().trim(), editTextPass.getText().toString().trim(), collId))
                {
                    String em = editTextEmail.getText().toString().trim();
                    int userId = dbHandler.getUserIdByEmail(em);
                    String name = dbHandler.getNameById(userId);
                    roleId = dbHandler.getRoleByUserId(userId);


                    if(roleId == 2)
                    {
                        int isStarted = sharedpreferences.getInt(servicePref,0);
                        if(isStarted != 1)
                        {
                            checkNotifications(userId);
                            startService(new Intent(getBaseContext(), NotiService.class));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt(servicePref, 1);
                            editor.commit();
                        }

                    }



                    String userNamePrefs = sharedpreferences.getString(userNamePref, null);
                    if (userNamePrefs == null) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(userNamePref, name);
                        editor.putInt(userIdPref, userId);
                        editor.putString(userEmailPref, em);
                        editor.putInt(userRoleIdPref, roleId);
                        editor.putInt(userCollIdPref, collId);
                        editor.commit();

                        Toast.makeText(LoginActivity.this, "Hi, " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ChoiceActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "InValid Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
            }
        });
    }

    public void checkNotifications(int userId) {
        ArrayList<Notifications> notiCollection = dbHandler.getNotificatiosFromDB(userId);

        if(notiCollection.size() != 0) {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

            //Building Notification
            notification.setSmallIcon(R.mipmap.ic_launcher);
            notification.setTicker("Ticker.....");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("You have " + notiCollection.size() + " new approvals");

            //notification.setContentText("Body");
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notification.setSound(alarmSound);
            long[] vibrate = {0, 100, 200, 300};
            notification.setVibrate(vibrate);

            Intent intent = new Intent(this, ClassSelStuActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);

            //Issuing Notification
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(5526, notification.build());

            dbHandler.makeNotiRead(userId);
        }

    }

    private void clearPreferences() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.cs442.group17.classmanagment");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
