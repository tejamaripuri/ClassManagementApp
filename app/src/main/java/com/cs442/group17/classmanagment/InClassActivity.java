package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class InClassActivity extends AppCompatActivity {

    ArrayList<String> inClassNamestList;
    ArrayAdapter inClassNamesListAdap;
    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;
    int subjectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class);

        sharedPreferences  = getSharedPreferences(LoginFragment.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);

        inClassNamestList =new ArrayList<>();

        int roleId = sharedPreferences.getInt(LoginFragment.userRoleIdPref, 0);

        if(roleId == 1)
        {
            inClassNamestList.add("Class Roster");
            inClassNamestList.add("Calender Events");
            inClassNamestList.add("Communicate");
            inClassNamestList.add("Attendance");
        }
        else if(roleId == 2)
        {
            inClassNamestList.add("Class Roster");
            inClassNamestList.add("Communicate");
            inClassNamestList.add("Attendance Statistics");
            inClassNamestList.add("Approvals");
        }

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);


        inClassNamesListAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,inClassNamestList);
        final ListView itemListView = (ListView) findViewById(R.id.listViewInClassList);
        itemListView.setAdapter(inClassNamesListAdap);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;


                String item = String.valueOf(parent.getItemAtPosition(position));
                switch(item) {
                    case "Class Roster" :
                        intent = new Intent(InClassActivity.this, RosterActivity.class);
                        break;
                    case "Calender Events" :
                        intent = new Intent(InClassActivity.this, CalenderActivity.class);
                        break;
                    case "Communicate" :
                        intent = new Intent(InClassActivity.this, CommunicateActivity.class);
                        break;
                    case "Attendance" :
                        intent = new Intent(InClassActivity.this, AttendenceActivity.class);
                        break;
                    case "Attendance Statistics" :
                        intent = new Intent(InClassActivity.this, AttendenceStatsActivity.class);
                        break;
                    case "Approvals" :
                        intent = new Intent(InClassActivity.this, ApprovalsActivity.class);
                        break;
                    default :
                        Toast.makeText(InClassActivity.this, "Invalid Selection", Toast.LENGTH_LONG).show();
                }

                if(intent != null)
                {
                    intent.putExtra("subjectId", subjectId);
                    startActivity(intent);
                }

            }
        });

    }
}
