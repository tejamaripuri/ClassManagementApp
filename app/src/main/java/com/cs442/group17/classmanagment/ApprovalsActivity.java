package com.cs442.group17.classmanagment;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ApprovalsActivity extends ListActivity {

    ArrayList<String> a;
    ArrayList<Integer> b;

    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;

    int subjectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvals);

        sharedPreferences  = getSharedPreferences(LoginFragment.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);

        ListView listview= getListView();
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);

        listview.setTextFilterEnabled(true);

        a = new ArrayList<>();
        b = new ArrayList<>();
        a = dbHandler.getApprovals(sharedPreferences.getInt(LoginFragment.userIdPref, 0));
        b = dbHandler.getApprovalsIds(sharedPreferences.getInt(LoginFragment.userIdPref, 0));

        ArrayAdapter inClassNamesListAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,a);
       /* setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked,appr));*/
        listview.setAdapter(inClassNamesListAdap);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);
        findViewById(R.id.activity_roster).setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(ApprovalsActivity.this, AttendenceStatsActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(ApprovalsActivity.this, RosterActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onListItemClick(ListView parent, View v, int position, long id){
        CheckedTextView item = (CheckedTextView) v;
        String msg = "";
        if(item.isChecked())
        {
            dbHandler.approve(1, b.get(position));
             msg = " has Approved.";
        }
        else
        {
            dbHandler.approve(0, b.get(position));
             msg = " has Disapproved.";
        }
        //Toast.makeText(this, appr[position] + " is Approved:" + item.isChecked(), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, a.get(position) + msg , Toast.LENGTH_SHORT).show();
    }
}
