package com.example.smartadvisor;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GetPastCourses extends Fragment {

    ArrayList<Course> past;


    public GetPastCourses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_get_past_courses, container, false);
        past = new ArrayList<Course>();
        final Button done = (Button) rootview.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                PlanFragment makePlan = new PlanFragment();
                makePlan.setPast(past);
                ft.replace(R.id.container, makePlan);
                ft.commit();
            }
        });
        return rootview;
    }
}
