package com.example.smartadvisor;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        w = new ArrayList<String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);
        String type = shared.getString(getString(R.string.type), null);
        /*if(type == null){
            GetMajor r = new GetMajor();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, r);
            ft.addToBackStack(null);
            ft.commit();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_main, container, false);
        /*GridView grid;
        CustomGrid adapter = new CustomGrid(getActivity(), web, R.color.red, plan);
        grid=(GridView) rootview.findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getActivity(), "You Clicked at " + web[position], Toast.LENGTH_SHORT).show();
            }
        });*/
        return rootview;
    }
}
