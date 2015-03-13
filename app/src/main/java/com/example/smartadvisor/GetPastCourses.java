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

//TODO: Make a list view (or grid view) of all the classes for them to select
//If the group decides then we can set it up for the user to type each course.
//Although to I try to minimize typing so a grid view or list view seems easier

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

        return rootview;
    }
}
