package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CommunicateActivity extends AppCompatActivity {

    MyDBHandler dbHandler;
    String emails = "";
    int subjectId = 0;
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        dbHandler = new MyDBHandler(this, null, null, 1);
        sharedpreferences = getSharedPreferences(LoginFragment.MyPREFERENCES, MODE_PRIVATE);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);

        Button btnSend = (Button) findViewById(R.id.button4);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emails = dbHandler.getRosterEmailBySubjectID(subjectId);
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + emails));
                intent.putExtra(Intent.EXTRA_TEXT, "sent from class management app.");
                startActivity(intent);
            }
        });

        Button btnSend2 = (Button) findViewById(R.id.button6);

        if(sharedpreferences.getInt(LoginFragment.userRoleIdPref,0) != 2)
        {
            btnSend2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + dbHandler.getEmailByUserId(dbHandler.getApproverBySubjectId(subjectId))));
                    intent.putExtra(Intent.EXTRA_TEXT, "sent from class management app.");
                    startActivity(intent);
                }
            });
        }
        else
        {
            btnSend2.setVisibility(View.GONE);
        }
    }
}


