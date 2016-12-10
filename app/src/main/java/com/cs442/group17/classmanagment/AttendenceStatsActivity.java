package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AttendenceStatsActivity extends AppCompatActivity {
    int subjectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_stats);

        Intent i = getIntent();
        subjectId = i.getIntExtra("subjectId", 0);
        findViewById(R.id.activity_roster).setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent = new Intent(AttendenceStatsActivity.this, CommunicateActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSwipeRight() {
                Intent intent = new Intent(AttendenceStatsActivity.this, ApprovalsActivity.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
                finish();
            }
        });
    }
}
