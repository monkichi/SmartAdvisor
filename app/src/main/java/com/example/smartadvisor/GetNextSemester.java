package com.example.smartadvisor;



import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 *
 */

//TODO: get next semester

public class GetNextSemester extends Fragment {

    private ArrayList<Course> past;
    private int year;

    public GetNextSemester() {
        // Required empty public constructor
    }

    public void setPast(ArrayList<Course> c){
        past = c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_next_semester, container, false);

        RadioGroup radioGroup = (RadioGroup)rootview.findViewById(R.id.get_year);
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < radioGroup .getChildCount(); i++) {
            int y = c.get(Calendar.YEAR) + i;
            ((RadioButton) radioGroup.getChildAt(i)).setText(String.valueOf(y));
        }

        Button button = (Button) rootview.findViewById(R.id.next_semester_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                PlanFragment plan = new PlanFragment();
                SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);
                String type = shared.getString(getString(R.string.type), "Spring ");
                Calendar c = Calendar.getInstance();
                year = shared.getInt(getString(R.string.year), (c.get(Calendar.YEAR) + 1));
                plan.setCurrsem(type,year);
                plan.setPast(past);
                ft.replace(R.id.container, plan);
                ft.commit();
            }
        });
        return rootview;
    }


}
