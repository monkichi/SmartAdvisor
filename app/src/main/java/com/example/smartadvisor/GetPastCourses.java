package com.example.smartadvisor;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.RoundingMode;
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
    //public static final String PREFS_NAME = "MyProfsFile"; // name of stored data table
    ProgressDialog mProgressDialog;
    View rootview;
    ListView l;

    public GetPastCourses() {
        // Required empty public constructor
        courses = new ArrayList<String>();
        clinks = new ArrayList<String>();
        past = new ArrayList<Course>();
    }
    public void setLink(String courselink){link = courselink;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_get_past_courses, container, false);
        GetCourses g = new GetCourses();
        g.execute();
        return rootview;
    }

    private class GetCourses extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            /*mProgressDialog = new ProgressDialog(rootview.getContext());
            mProgressDialog.setTitle("Getting Courses");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                //Below i am getting all the links from a certain section that contains
                //the list of classes needed to be taken to acquire the major. This includes
                //classes not under the major name as well.
                Document doc = Jsoup.connect(link).get();
                Elements e = doc.select("#inset-content").first().select("div.section-content");
                //this needs a little cleaning up because it will get the links but the whole
                //name isn't always linked.
                for(int i=0; i<e.size(); i++){
                    Elements a = e.get(i).select("p").select("a");
                    if(a.size() >0){
                        for(int j=0; j<a.size(); j++){
                            clinks.add(a.get(j).attr("href"));
                            courses.add(a.get(j).text());
                        }
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //mProgressDialog.dismiss();
            final ArrayAdapter<String> co = new ArrayAdapter<String>(rootview.getContext(),
                    R.layout.list_item, courses);
            l = (ListView) rootview.findViewById(R.id.courselist);
            l.setAdapter(co);

        }
    }
}
