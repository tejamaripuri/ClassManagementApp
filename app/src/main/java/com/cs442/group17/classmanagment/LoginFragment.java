package com.cs442.group17.classmanagment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.cs442.group17.classmanagment.SplashScreen.servicePref;

/**
 * Created by USER on 23-11-16.
 */

public class LoginFragment extends Fragment {
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

    public static final String RESULT_COLLEGEID = "RESULT_COLLEGEID";
    private ResultListener resultListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        resultListener = (ResultListener) getActivity();//assumes this will get MainActivity
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPass = (EditText) view.findViewById(R.id.editTextPass);
        dbHandler = new MyDBHandler(getActivity(), null, null, 1);//move thisto main activity, pass in to fragment

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collegeName = adapterView.getItemAtPosition(i).toString();

                Bundle bundle = new Bundle();
                bundle.putString(RESULT_COLLEGEID, collegeName);
                resultListener.notifyResult(bundle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        // Spinner Drop down elements
        List<String> colleges;
        colleges = dbHandler.getColleges();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, colleges);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Button buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collId = dbHandler.getCollegeIdByCollegeName(collegeName);
                if(editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Email and Password can not be blank", Toast.LENGTH_SHORT).show();
                }
                else if(dbHandler.checkLogin(editTextEmail.getText().toString(), editTextPass.getText().toString(), collId))
                {
                    String em = editTextEmail.getText().toString();
                    int userId = dbHandler.getUserIdByEmail(em);
                    String name = dbHandler.getNameById(userId);
                    roleId = dbHandler.getRoleByUserId(userId);

                    SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, getActivity().MODE_PRIVATE);

                    if(roleId == 2)
                    {
                        int isStarted = prefs.getInt(servicePref,0);
                        if(isStarted != 1)
                        {
                            checkNotifications(userId);

                            getActivity().startService(new Intent(getActivity().getBaseContext(), NotiService.class));
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(servicePref, 1);
                            editor.commit();
                        }

                    }

                    String userNamePrefs = prefs.getString(userNamePref, null);
                    if (userNamePrefs == null) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(userNamePref, name);
                        editor.putInt(userIdPref, userId);
                        editor.putString(userEmailPref, em);
                        editor.putInt(userRoleIdPref, roleId);
                        editor.putInt(userCollIdPref, collId);
                        editor.commit();

                        Toast.makeText(getActivity(), "Hi, " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ChoiceActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Invalid login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonRegister = (Button) view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button buttonClear = (Button) view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
            }
        });

        return view;
    }

    public void checkNotifications(int userId) {
        ArrayList<Notifications> notiCollection = dbHandler.getNotificatiosFromDB(userId);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getActivity());

        //Building Notification
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("Ticker.....");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("You have " + notiCollection.size() + " new approvals");

        //notification.setContentText("Body");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(alarmSound);
        long[] vibrate = { 0, 100, 200, 300 };
        notification.setVibrate(vibrate);

        Intent intent = new Intent(getActivity(), ClassSelStuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Issuing Notification
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        nm.notify(5526, notification.build());

        dbHandler.makeNotiRead(userId);
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
}
