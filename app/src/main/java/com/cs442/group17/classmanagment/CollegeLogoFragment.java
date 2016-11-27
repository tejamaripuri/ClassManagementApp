package com.cs442.group17.classmanagment;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by USER on 26-11-16.
 */

public class CollegeLogoFragment extends Fragment implements ResultListener {
    private ImageView collegeLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.college_logo_fragment, container, false);

        collegeLogo = (ImageView)view.findViewById(R.id.collegeLogo);
        //set initial logo to question mark (or make a "select your college" image?
        collegeLogo.setImageResource(R.drawable.default_logo);

        return view;
    }

    @Override
    public void notifyResult(Bundle bundle) {
        String collegeID = bundle.getString(LoginFragment.RESULT_COLLEGEID);

        int logoResourceID = getActivity().getResources().getIdentifier(collegeID.toLowerCase().replace(' ','_').replace('-','_'), "drawable", getActivity().getPackageName());

        if (logoResourceID != 0) {
            collegeLogo.setImageResource(logoResourceID);
        } else {
            collegeLogo.setImageResource(R.drawable.default_logo);
        }
    }
}
