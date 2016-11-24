package com.cs442.group17.classmanagment;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by USER on 13-11-16.
 */

public class CollegePickerFragment extends ListFragment {

    public static final String RESULT_COLLEGEID = "RESULT_COLLEGEID";

    private ArrayList<String> menuArray;

    private ResultListener resultListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        resultListener = (ResultListener)getActivity();//assumes this will get MainActivity

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ListView listView = getListView();
        menuArray = new ArrayList<String>();

        LinearLayout header = (LinearLayout) inflater.inflate(R.layout.college_picker_list_header, listView, false);
        listView.addHeaderView(header, null, false);

        menuArray.add("IIT");
        menuArray.add("MIT");
        menuArray.add("UIC");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, menuArray);
        setListAdapter(adapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString(RESULT_COLLEGEID, item);
                resultListener.notifyResult(bundle);
            }
        });
    }
}
