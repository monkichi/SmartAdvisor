package com.example.smartadvisor;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        past = new ArrayList<Course>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_get_past_courses, container, false);
        onCheckboxClicked(rootview);
        return rootview;
    }

    public void onCheckboxClicked(View view) {
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
                break;
            case R.id.math104check:
                break;
            case R.id.math150Acheck:
                break;
            case R.id.math150Bcheck:
                break;
            case R.id.math262check:
                break;
            case R.id.phil230check:
                break;
        }
    }


}
