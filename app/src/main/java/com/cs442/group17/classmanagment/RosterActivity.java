package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RosterActivity extends AppCompatActivity {

    ArrayList<String> namestList;
    ArrayAdapter namesListAdap;
    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;
    int subjectId = 0;
    int roleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        sharedPreferences  = getSharedPreferences(LoginFragment.MyPREFERENCES, MODE_PRIVATE);
        roleId = sharedPreferences.getInt(LoginFragment.userRoleIdPref, 0);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);

        namestList =new ArrayList<>();

        namestList = dbHandler.getRosterBySubjectID(subjectId);

        namesListAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namestList);
        final ListView itemListView = (ListView) findViewById(R.id.rosterListView);
        itemListView.setAdapter(namesListAdap);


        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                String email = dbHandler.getEmailByName(item);


                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email));
                intent.putExtra(Intent.EXTRA_TEXT, "sent from class management app.");
                startActivity(intent);
            }
        });

        findViewById(R.id.activity_roster).setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent;
                if (roleId == 1) {
                    intent = new Intent(RosterActivity.this, AttendenceActivity.class);
                } else {
                    intent = new Intent(RosterActivity.this, ApprovalsActivity.class);
                }
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }

            @Override
            public void onSwipeRight() {
                Intent intent;
                if (roleId == 1) {
                    intent = new Intent(RosterActivity.this, CalenderActivity.class);
                } else {
                    intent = new Intent(RosterActivity.this, CommunicateActivity.class);
                }

                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });

    }



}
