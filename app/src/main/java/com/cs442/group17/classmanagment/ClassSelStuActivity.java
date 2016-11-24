package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClassSelStuActivity extends AppCompatActivity {

    ArrayList<String> classNamestList;
    ArrayAdapter classNamesListAdap;
    SharedPreferences sharedPreferences;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_sel_stu);

        sharedPreferences  = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);

        classNamestList =new ArrayList<>();

        classNamestList = dbHandler.getSubjectsByUserIdRoleId(sharedPreferences.getInt(LoginActivity.userIdPref,0), sharedPreferences.getInt(LoginActivity.userRoleIdPref,0));

        classNamesListAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classNamestList);
        final ListView itemListView = (ListView) findViewById(R.id.listViewStuClasses);
        itemListView.setAdapter(classNamesListAdap);


        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                int subjectId = dbHandler.getSubjectIdBySubjectName(item);
                Intent intent = new Intent(ClassSelStuActivity.this, InClassActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });
    }
}
