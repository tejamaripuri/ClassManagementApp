package com.cs442.group17.classmanagment;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class NotiService extends Service {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userIdPref = "userIdPref";
    public static final String servicePref = "servicePref";
    public static final String userRoleIdPref = "userRoleIdPref";

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
    SharedPreferences sharedpreferences;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    long ftime = System.currentTimeMillis()+ 10000;
                    synchronized (this)
                    {
                        try
                        {
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            if(sharedpreferences != null)
                            {
                                wait(ftime- System.currentTimeMillis());
                                if(sharedpreferences.getInt(userRoleIdPref,0) == 2)
                                checkNotifications(sharedpreferences.getInt(userIdPref,0));
                                Log.d("cm_log", "Service running.");
                            }
                        }
                        catch (Exception e)
                        {
                            Log.d("cm_log ", "Something went Wrong");
                        }
                    }
                }
            }
        };

        //Staring the Thread
        Thread t =new Thread(r);
        t.start();

        //Using sticky because the service should be alive after app kill
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(servicePref,0);
        editor.commit();
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private void checkNotifications(int userId) {
        ArrayList<Notifications> notiCollection = dbHandler.getNotificatiosFromDB(userId);

        if(notiCollection.size() == 0)
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

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