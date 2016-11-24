package com.cs442.group17.classmanagment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword;
    RadioGroup radioGroupRegChoice;
    MyDBHandler dbHandler;
    String regChoice;
    int role = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName= (EditText) findViewById(R.id.editTextName);
        dbHandler = new MyDBHandler(this, null, null, 1);

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
                    Users user = new Users(role, editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                    if(dbHandler.addUser(user))
                    {
                        Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
