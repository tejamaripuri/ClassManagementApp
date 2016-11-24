package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RosterActivity extends AppCompatActivity {

    ArrayList<String> namestList;
    ArrayAdapter namesListAdap;
    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;
    int subjectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        sharedPreferences  = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);

        namestList =new ArrayList<>();

        namestList = dbHandler.getRosterBySubjectID(subjectId);

        namesListAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,namestList);
        final ListView itemListView = (ListView) findViewById(R.id.rosterListView);
        itemListView.setAdapter(namesListAdap);

    }



}
