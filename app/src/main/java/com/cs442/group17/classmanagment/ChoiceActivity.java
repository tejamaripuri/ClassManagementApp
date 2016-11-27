package com.cs442.group17.classmanagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChoiceActivity extends AppCompatActivity {
    Intent intent;
    boolean doubleBackToExitPressedOnce = false;
    MyDBHandler dbHandler;
    SharedPreferences sharedpreferences;
    int selectedRole = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        dbHandler = new MyDBHandler(this, null, null, 1);
        final int roleId = dbHandler.getRoleByUserId(sharedpreferences.getInt(LoginActivity.userIdPref,0));

        intent = new Intent(ChoiceActivity.this, ClassSelStuActivity.class);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupChoice);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getText().equals("Student"))
                {
                       selectedRole = 1;
                }
                else
                {
                       selectedRole = 2;
                }
            }
        });

        Button btn = (Button) findViewById(R.id.buttonSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpreferences.getInt(LoginActivity.userRoleIdPref,0) == selectedRole)
                {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ChoiceActivity.this, "Incorrect Role Chosen.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnLogOut = (Button) findViewById(R.id.buttonLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public  void logout(View view){
        stopService(new Intent(getBaseContext(), NotiService.class));
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        intent = new Intent(ChoiceActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
