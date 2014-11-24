package com.example.smartadvisor;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class PlanFragment extends Fragment {

    private ArrayList<Course> past;
    private ArrayList<Semester> plan;

    public PlanFragment(){
        past = null;
        plan = null;
    }

    public void setPast(ArrayList<Course> p){
        past = p;
    }

    public void makePlan(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        return rootview;
    }


}
