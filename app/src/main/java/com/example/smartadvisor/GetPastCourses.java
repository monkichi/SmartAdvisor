package com.example.smartadvisor;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

    ArrayList<Course> past;//past course the user has taken
    String link = null;// This is the link to the main page for the major
    String baseplan = null; //link to 4 year plan that I will use as a basis
    ArrayList<String> courses;//courses pulled from major website
    ArrayList<String> clinks; //course links that will contain info like prereqs
    public static final String PREFS_NAME = "MyProfsFile"; // name of stored data table
    String url = "http://www.csun.edu/catalog/academics/ece/courses/";
    String name = null;

    public GetPastCourses() {
        // Required empty public constructor
        courses = new ArrayList<String>();
        clinks = new ArrayList<String>();
        past = new ArrayList<Course>();
    }
    List<String> majors;
    public void setLink(String l){
        link = l;
    }
    public void setName(String n){name = n;}
    public String getName(){return name;}
    public void setmajors(List<String> m){majors = m;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_get_past_courses, container, false);
        new GetCourses().execute();
        courses.add(link);
        ArrayAdapter<String> co = new ArrayAdapter<String>(rootview.getContext(),
                R.layout.list_item, courses);
        ListView l = (ListView) rootview.findViewById(R.id.courselist);
        l.setAdapter(co);


        return rootview;
    }

    private class GetCourses extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                //It can't seem to connect and get the title
                //This doesn't seem to add it to the courses list and display
                Document doc = Jsoup.connect(link).get();
                /*Element element = doc.select("#inset-content").first()
                        .select("div.row").first()
                        .select("div.col-xs-12.col-sm-6.col-md-8.col-lg-8").first();*/
                courses.add(doc.title());
                //Element e = element.select("p");
                /*Elements e = doc.select("#inset-content").first()
                        .select("div.row").first()
                        .select("div.col-xs-12.col-sm-6.col-md-8.col-lg-8");
                for(int i=0; i<e.size(); i++){
                    //courses.add(e.get(i).text());
                    Elements a = e.get(i).select("a");
                    if(a.size() >0){
                        for(int j=0; j<a.size(); j++){
                            clinks.add(a.get(j).attr("href"));
                            courses.add(a.text());
                        }
                    }
                }*/
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
