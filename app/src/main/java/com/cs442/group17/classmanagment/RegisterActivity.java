package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword;
    RadioGroup radioGroupRegChoice;
    MyDBHandler dbHandler;
    String regChoice;
    int role = 1;
    int collId;
    String collegeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName= (EditText) findViewById(R.id.editTextName);
        dbHandler = new MyDBHandler(this, null, null, 1);


        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        // Spinner Drop down elements
        List<String> colleges;
        colleges = dbHandler.getColleges();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colleges);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collegeName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        radioGroupRegChoice = (RadioGroup) findViewById(R.id.radioGroupRegChoice);
        radioGroupRegChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getText().equals("Student"))
                {
                    regChoice = "Student";
                }
                else
                {
                    regChoice = "Faculty";
                    role = 2;
                }
            }
        });

        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "All fields are Mandatory.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    collId = dbHandler.getCollegeIdByCollegeName(collegeName);
                    Users user = new Users(role, editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                    int res = dbHandler.addUser(user, collId);
                    if(res == 1)
                    {
                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else if (res == 2)
                    {
                        Toast.makeText(RegisterActivity.this, "Already Registered", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 3)
                    {
                        Toast.makeText(RegisterActivity.this, "Incorrect College Entry.", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 4)
                    {
                        Toast.makeText(RegisterActivity.this, "Incorrect Role Selection.", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 9)
                    {
                        Toast.makeText(RegisterActivity.this, "No such credentials match with your College DataBase", Toast.LENGTH_LONG).show();
                    }
                    else if (res == -99)
                    {
                        Toast.makeText(RegisterActivity.this, "Something Went Wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }




}
