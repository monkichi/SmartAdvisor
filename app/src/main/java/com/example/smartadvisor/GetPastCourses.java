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
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                PlanFragment makePlan = new PlanFragment();
                ft.add(R.id.container, makePlan);
                ft.commit();
            }
        });
        return rootview;
    }

    /*public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        CourseChart courseChart = new CourseChart();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.comp108check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 180"));
                break;
            case R.id.comp110check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 110/L"));
                break;
            case R.id.comp122check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 122/L"));
                break;
            case R.id.comp182check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 182/L"));
                break;
            case R.id.comp222check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 222"));
                break;
            case R.id.comp256_Lcheck:
                if (checked)
                    past.add(courseChart.getCourse("Comp 256/L"));
                break;
            case R.id.comp282check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 282"));
                break;
            case R.id.math102check:
                if (checked)
                    past.add(courseChart.getCourse("Math 102"));
                break;
            case R.id.math104check:
                if (checked)
                    past.add(courseChart.getCourse("Math 104"));
                break;
            case R.id.math150Acheck:
                if (checked)
                    past.add(courseChart.getCourse("Math 150A"));
                break;
            case R.id.math150Bcheck:
                if (checked)
                    past.add(courseChart.getCourse("Math 150B"));
                break;
            case R.id.math262check:
                if (checked)
                    past.add(courseChart.getCourse("Math 262"));
                break;
            case R.id.phil230check:
                if (checked)
                    past.add(courseChart.getCourse("Phil 230"));
                break;
        }
    }*/


}
