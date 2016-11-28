package com.cs442.group17.classmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class RegisterActivity extends FragmentActivity implements ResultListener {

    private ResultListener resultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        resultListener = (ResultListener)getSupportFragmentManager().findFragmentById(R.id.register_fragment1);
    }

    @Override
    public void notifyResult(Bundle bundle) {
        String collegeID = bundle.getString(LoginFragment.RESULT_COLLEGEID);

        //notify register_fragment1 to reload image
        Bundle bundle_logo = new Bundle();
        bundle_logo.putString(LoginFragment.RESULT_COLLEGEID, collegeID);
        resultListener.notifyResult(bundle_logo);
    }
}
