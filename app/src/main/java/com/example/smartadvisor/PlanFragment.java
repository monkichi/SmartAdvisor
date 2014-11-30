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

    public PlanFragment(){
        past = null;
        plan = null;
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
        if(past != null){
            //make plan based off past courses
            findLastCourseTaken(last, temp.getStarters());
            makePlan(last, 0, sem, y);
        }else{
            makePlan(courseChart.getStarters(), 0, sem, y);
        }
    }

    public void findLastCourseTaken(ArrayList<Course> l, ArrayList<Course> n){
        for(int i=0; i < n.size(); i++){
            for(int j=0; j < n.get(i).next.size(); i++){
                if(!past.contains(n.get(i).next.get(j))){
                    l.add(n.get(i));
                }
            }
        }
    }

    public void makePlan(ArrayList<Course> l, int num, String sem, int y){
        Semester s;
        for(int i=0; i < l.size(); i++){
            Course curr = l.get(i);
            if(curr.next.size() != 0) {
                if (sem.equalsIgnoreCase("Fall")) {
                    sem = "Spring";
                    y++;
                    w.add(sem + year);
                    s = new Semester(sem + (y + 1));
                }
                else {
                    sem = "Fall";
                    w.add(sem + y);
                    s = new Semester(sem + y);
                }
                for (int k = 0; k < curr.next.size(); k++) {
                    for (int f = 0; f < curr.next.get(k).getPrereqs().size(); f++) {
                        if (!past.contains(curr.next.get(k).getPrereqs().get(f))) {
                            s.courses.add(curr.next.get(k).getPrereqs().get(f));
                        }
                    }
                }
                plan.add(s);
            }
        }
        if(plan.get(l.size()-1).courses.size() != 0){
            makePlan(plan.get(l.size()-1).courses, num + 1, sem, year);
        }else{
            planmade = true;
            numsemesters = plan.size();
            web = new String[w.size()];
            for(int i=0; i < w.size(); i++){
                web[i] = w.get(i);
            }
            storeResults();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        getResults();
        GridView grid;
        CustomGrid adapter = new CustomGrid(getActivity(), web, R.color.red);
        grid=(GridView)rootview.findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });


        return rootview;
    }
}
