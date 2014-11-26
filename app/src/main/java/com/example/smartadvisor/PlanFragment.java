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

    public void makePlan(){
        CourseChart temp = new CourseChart();
        Semester s = new Semester("Spring 2015");
        if(past != null){
            //make plan based off past courses
            ArrayList<Course> last = new ArrayList<Course>();
            findLastCourseTaken(last, temp.getStarters());
        }else{
            //assume they are a freshman and do a regular plan
        }
    }

    public void findLastCourseTaken(ArrayList<Course> l, ArrayList<Course> n){
        for(int i=0; i < n.size(); i++){
            for(int j=0; j < n.get(i).next.size(); i++){
                if(!past.contains(n.get(i).next.get(j))){

                }
            }
        }
    }

    public void makePlan(String c, String c2){
        boolean done = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        if(plan == null){
            makePlan();
            //then display class grid
        }else{
            //display class grid
        }
        return rootview;
    }


}
