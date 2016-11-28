package com.cs442.group17.classmanagment;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;

import java.util.ArrayList;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userNamePref = "userNamePref";
    public static final String userIdPref = "userIdPref";
    public static final String servicePref = "servicePref";
    public static final String userRoleIdPref = "userRoleIdPref";

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String userNamePrefs = prefs.getString(userNamePref, null);
                if (userNamePrefs == null) {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    int isStarted = prefs.getInt(servicePref,0);
                    if(isStarted != 1 && prefs.getInt(LoginActivity.userRoleIdPref,0) == 2)
                    {
                        checkNotifications(prefs.getInt(LoginActivity.userIdPref,0));
                        startService(new Intent(getBaseContext(), NotiService.class));
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt(servicePref, 1);
                        editor.commit();
                    }
                    Intent i = new Intent(SplashScreen.this, ChoiceActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void checkNotifications(int userId) {
        ArrayList<Notifications> notiCollection = dbHandler.getNotificatiosFromDB(userId);

        if(notiCollection.size() == 0) {
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


}
