package com.example.smartadvisor;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class PlanFragment extends Fragment {

    private CourseChart courseChart;
    private ArrayList<Course> past;
    private ArrayList<Semester> plan;
    private String currsem;
    private int year;
    private String[] web;
    private ArrayList<String> w;
    private int numsemesters;
    private boolean planmade;
    View rootview;

    public PlanFragment(){
        past = null;
        plan = new ArrayList<Semester>();
        courseChart = new CourseChart();
        w = new ArrayList<String>();
    }

    public void setCurrsem(String sem, int y){
        currsem = sem;
        year = y;
    }

    public void setPast(ArrayList<Course> p){
        past = p;
        makePlan();
    }

    public void makePlan(){
        CourseChart temp = new CourseChart();
        String sem = currsem;
        int y = year;
        ArrayList<Course> last = new ArrayList<Course>();
        if(past.size() > 0){
            //make plan based off past courses
            findLastCourseTaken(last, temp.getStarters());
            makePlan(last, sem, y);
        }else{
            makePlan(courseChart.getStarters(), sem, y);
        }
    }

    public void findLastCourseTaken(ArrayList<Course> l, ArrayList<Course> n){
        for(int i=0; i < n.size(); i++){
            for(int j=0; j < n.get(i).next.size(); j++){
                if(!past.contains(n.get(i).next.get(j))){
                    l.add(n.get(i));
                }
            }
        }
    }

    public ArrayList<Course> findNextCourses(ArrayList<Course> l){
        // this method assumes l is the current list of classes for that semester
        ArrayList<Course> n = new ArrayList<Course>();
        for(int i=0; i < l.size(); i++){ // for each class in this semester
            if(l.get(i).next.size() > 0) { // is there a class after this?
                Course curr = l.get(i);
                for (int j = 0; j < curr.next.size(); j++) { // for all that the classes this is a direct prereq for
                    //make sure you have all the prereqs for the next class
                    for(int k=0; k < curr.next.get(j).getPrereqs().size(); k++){
                        //if it doesn't have all prereqs
                        if(!l.contains(curr.next.get(j).getPrereqs().get(k))) {
                            //add it to the current semester if it doesn't exist in the past classes
                            if (!past.contains(curr.next.get(j).getPrereqs().get(k)))
                                l.add(curr.next.get(j).getPrereqs().get(k));
                            else //add it to the next semester if it is in the past
                                n.add(curr.next.get(j).getPrereqs().get(k));
                        }
                        else // if it does have all necessary prereqs then add this class
                            n.add(curr.next.get(j));
                    }
                }
            }
        }

        return n;
    }

    public void makePlan(ArrayList<Course> l, String sem, int y){
        Course curr;
        ArrayList<Course> temp = new ArrayList<Course>();
        temp = l;
        boolean done = false;
        while(!done) {
            Semester s;
            if (sem.equalsIgnoreCase("Fall ")) {
                sem = "Spring ";
                y++;
                String t = sem;
                t = t.concat(String.valueOf(y));
                w.add(t);
                s = new Semester(t);
            } else {
                sem = "Fall ";
                String t = sem;
                t = t.concat(String.valueOf(y));
                w.add(t);
                s = new Semester(t);
            }
            s.courses = findNextCourses(temp);
            if(s.courses.size() > 0) {
                plan.add(s);
                temp = s.courses;
            }else {
                done = true;
            }
        }
        web = new String[w.size()];
        for(int i=0; i < web.length; i++){
            web[i] = w.get(i);
        }
    }

    public void storeResults(){
        SharedPreferences shared = getActivity().getSharedPreferences(getString(R.string.future), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        for(int i=0; i < plan.size(); i++){
            HashSet<String> c = new HashSet<String>(),
                            d = new HashSet<String>();
            for(int j=0; j < plan.get(i).courses.size(); j++){
                c.add(plan.get(i).courses.get(j).getName());
                d.add(w.get(i));
            }
            editor.putStringSet("web", d);
            editor.putStringSet("plan " + plan.get(i).getName(), c);
        }
        editor.putBoolean(getString(R.string.planmade), planmade);
        editor.putInt(getString(R.string.number_semesters), numsemesters);
        editor.commit();
    }

    public void getResults(){
        SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);
        planmade = shared.getBoolean(getString(R.string.planmade), false);
        numsemesters = shared.getInt(getString(R.string.number_semesters), 0);
        currsem = shared.getString(getString(R.string.current_semester), null);
        if(planmade){
            for(int i=0; i < numsemesters; i++){
                Set<String> r = new HashSet<String>(),
                            d = new HashSet<String>();
                Semester s = new Semester(String.valueOf(i));
                Iterator<String> it = r.iterator();
                d = shared.getStringSet("web", null);
                r = shared.getStringSet("plan" + String.valueOf(i), null);
                while(it.hasNext()){
                    s.courses.add(courseChart.getCourse(it.next()));
                }
                it = d.iterator();
                while(it.hasNext()){
                    w.add(it.next());
                }
                plan.add(s);
            }
        }else{
            makePlan();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        storeResults();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_main, container, false);
        GridView grid;
        CustomGrid adapter = new CustomGrid(getActivity(), web, R.color.red, plan);
        grid=(GridView) rootview.findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getActivity(), "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
            }
        });
        return rootview;
    }
}
