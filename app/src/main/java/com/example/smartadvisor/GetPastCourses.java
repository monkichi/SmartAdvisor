package com.example.smartadvisor;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GetPastCourses extends Fragment {

    ArrayList<Course> past;
    CourseChart c = new CourseChart();

    public GetPastCourses() {
        // Required empty public constructor
    }

    public ArrayList<Course> getPast() {
        return past;
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
                GetNextSemester nextSem = new GetNextSemester();
                SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);
                Set<String> p = new HashSet<String>();
                p = shared.getStringSet(getString(R.string.past), p);
                Iterator<String> it = p.iterator();
                if(p != null){
                    while (it.hasNext()){
                        past.add(c.getCourse(it.next()));
                    }
                }
                nextSem.setPast(past);
                ft.replace(R.id.container, nextSem);
                ft.commit();
            }
        });
        return rootview;
    }
}
