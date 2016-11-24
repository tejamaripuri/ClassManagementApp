package com.cs442.group17.classmanagment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AttendenceActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener listener;
    int range = 1;
    double currentLatitude, currentLongitude;
    double classLatitiude = 37.422;
    double classLongitude = -122.084;
    String selection;
    Button btnSubmit;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);



        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupSelection);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getText().equals("Absent"))
                {
                    selection = "Absent";
                }
                else
                {
                    selection = "InClass";
                }
            }
        });

        btnSubmit = (Button) findViewById(R.id.buttonAtdSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selection.equals("InClass")) {

                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    // get the last know location from your location manager.
                    if (ActivityCompat.checkSelfPermission(AttendenceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AttendenceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                                    ,10);
                        }
                        return;
                    }
                    Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(location != null) {
                        currentLatitude = Math.round(location.getLatitude() * 10000D) / 10000D;
                        currentLongitude = Math.round(location.getLongitude() * 10000D) / 10000D;

                        if (distance(classLatitiude, classLongitude, currentLatitude, currentLongitude) > range) {
                            Toast.makeText(AttendenceActivity.this, "Please be in range to the class.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AttendenceActivity.this, "Attendance Submitted.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AttendenceActivity.this, "Switch On Location Services(GPS) to file attendance.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(AttendenceActivity.this, "Absence Submitted.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        setContentView(R.layout.activity_attendence);


    }


    double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}

