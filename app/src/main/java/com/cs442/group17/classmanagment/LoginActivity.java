package com.cs442.group17.classmanagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    EditText editTextEmail, editTextPass;
    MyDBHandler dbHandler;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userNamePref = "userNamePref";
    public static final String userIdPref = "userIdPref";
    public static final String userEmailPref = "userEmailPref";
    public static final String userRoleIdPref = "userRoleIdPref";
    SharedPreferences sharedpreferences;
    int roleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Welcome to Class Management Application",Snackbar.LENGTH_LONG).show();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Email and Password can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if(dbHandler.checkLogin(editTextEmail.getText().toString(), editTextPass.getText().toString()))
                {
                    String em = editTextEmail.getText().toString();
                    int userId = dbHandler.getUserIdByEmail(em);
                    String name = dbHandler.getNameById(userId);
                    roleId = dbHandler.getRoleByUserId(userId);

                    SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                    String userNamePrefs = prefs.getString(userNamePref, null);
                    if (userNamePrefs == null) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(userNamePref, name);
                        editor.putInt(userIdPref, userId);
                        editor.putString(userEmailPref, em);
                        editor.putInt(userRoleIdPref, roleId);
                        editor.commit();

                        Toast.makeText(LoginActivity.this, "Hi, " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ChoiceActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
            }
        });
    }

    private void clearPreferences() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.cs442.group17.classmanagment");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
