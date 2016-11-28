package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

/**
 * Created by USER on 27-11-16.
 */

public class RegisterFragment extends Fragment{
    EditText editTextName, editTextEmail, editTextPassword;
    RadioGroup radioGroupRegChoice;
    MyDBHandler dbHandler;
    String regChoice;
    int role = 1;
    int collId;
    String collegeName;

    public static final String RESULT_COLLEGEID = "RESULT_COLLEGEID";
    private ResultListener resultListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        resultListener = (ResultListener) getActivity();//assumes this will get RegisterActivity
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_fragment, container, false);

        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextName= (EditText) view.findViewById(R.id.editTextName);
        dbHandler = new MyDBHandler(getActivity(), null, null, 1);


        Spinner spinner = (Spinner) view.findViewById(R.id.spinner2);

        // Spinner Drop down elements
        List<String> colleges;
        colleges = dbHandler.getColleges();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, colleges);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collegeName = adapterView.getItemAtPosition(i).toString();

                Bundle bundle = new Bundle();
                bundle.putString(RESULT_COLLEGEID, collegeName);
                resultListener.notifyResult(bundle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        radioGroupRegChoice = (RadioGroup) view.findViewById(R.id.radioGroupRegChoice);
        radioGroupRegChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)view.findViewById(checkedId);
                if(rb.getText().equals("Student"))
                {
                    regChoice = "Student";
                    role = 1;
                }
                else
                {
                    regChoice = "Faculty";
                    role = 2;
                }
            }
        });

        Button buttonRegister = (Button) view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "All fields are Mandatory.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    collId = dbHandler.getCollegeIdByCollegeName(collegeName);
                    Users user = new Users(role, editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), editTextName.getText().toString().trim());
                    int res = dbHandler.addUser(user, collId);
                    if(res == 1)
                    {
                        Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else if (res == 2)
                    {
                        Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 3)
                    {
                        Toast.makeText(getActivity(), "Incorrect College Entry.", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 4)
                    {
                        Toast.makeText(getActivity(), "Incorrect Role Selection.", Toast.LENGTH_LONG).show();
                    }
                    else if (res == 9)
                    {
                        Toast.makeText(getActivity(), "No such credentials match with your College DataBase", Toast.LENGTH_LONG).show();
                    }
                    else if (res == -99)
                    {
                        Toast.makeText(getActivity(), "Something Went Wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

}
