package com.cs442.group17.classmanagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by USER on 23-11-16.
 */

public class LoginFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPass = (EditText) view.findViewById(R.id.editTextPass);
        dbHandler = new MyDBHandler(getActivity(), null, null, 1);//move thisto main activity, pass in to fragment

        Button buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().isEmpty() || editTextPass.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Email and Password can not be blank.", Toast.LENGTH_SHORT).show();
                }
                else if(dbHandler.checkLogin(editTextEmail.getText().toString(), editTextPass.getText().toString()))
                {
                    String em = editTextEmail.getText().toString();
                    int userId = dbHandler.getUserIdByEmail(em);
                    String name = dbHandler.getNameById(userId);
                    roleId = dbHandler.getRoleByUserId(userId);

                    SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES, getActivity().MODE_PRIVATE);
                    String userNamePrefs = prefs.getString(userNamePref, null);
                    if (userNamePrefs == null) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(userNamePref, name);
                        editor.putInt(userIdPref, userId);
                        editor.putString(userEmailPref, em);
                        editor.putInt(userRoleIdPref, roleId);
                        editor.commit();

                        Toast.makeText(getActivity(), "Hi, " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ChoiceActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Invalid Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonRegister = (Button) view.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button buttonClear = (Button) view.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPreferences();
            }
        });

        return view;
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
}
