package com.example.smartadvisor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class GetMajor extends Fragment {
    String url = "http://www.csun.edu/catalog/programs/major/";
    ProgressDialog mProgressDialog;
    public static final String PREFS_NAME = "MyPrefsFile"; // name of stored data table
    List<String> majors, links;
    View rootview;
    ArrayAdapter<String> mSaved;
    HashMap<String, String> p;

    //private OnFragmentInteractionListener mListener;
    // TODO: Rename and change types and number of parameters
    public static GetMajor newInstance(String param1, String param2) {
        GetMajor fragment = new GetMajor();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public GetMajor() {
        // Required empty public constructor
        p=new HashMap<String, String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_get_major, container, false);

        majors = new ArrayList<String>();
        links = new ArrayList<String>();

        final Button major = (Button) rootview.findViewById(R.id.majordone);
        AutoCompleteTextView textView = (AutoCompleteTextView) rootview.findViewById(R.id.autocomplete_course);

        Title title = new Title();
        title.execute();

        // Create an ArrayAdapter containing course names
        final ArrayAdapter<String> linkadapter = new ArrayAdapter<String>(rootview.getContext(),
                R.layout.list_item, links);
        mSaved = new ArrayAdapter<String>(rootview.getContext(),
                R.layout.list_item, majors);

        textView.setAdapter(mSaved);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).edit();
                editor.putString(getString(R.string.major), name);
                editor.commit();
            }
        });
        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                major.setBackgroundResource(R.drawable.nextpressed);
                SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                String m = prefs.getString(getString(R.string.major), null);
                if (m != null) {
                    FragmentManager f = getActivity().getFragmentManager();
                    FragmentTransaction ft = f.beginTransaction();
                    GetPastCourses getpast = new GetPastCourses();
                    getpast.setLink(p.get(m));
                    getpast.setmajors(majors);
                    ft.add(R.id.container, getpast);
                    ft.commit();
                }
                else{
                    major.setBackgroundResource(R.drawable.next);
                }
            }
        });

        return rootview;
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        Set<String> l;
        Document document;
        org.jsoup.nodes.Element element;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(rootview.getContext());
            mProgressDialog.setTitle("Getting Majors");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).edit();

                //So what I am doing here is getting all the <a> tags
                // and saving their text which is the major name and
                // saving the link to the major page that contains more
                //info on the major that we will parse later
                element = document.select("#wrap").first();
                Elements e = element.select("a.dept-item");
                for(int i = 0; i<e.size(); i++){
                    //I just add the major name and corresponding website link to their
                    //respective lists. Then I add them to the hashmap so each major can
                    //be associated with a link
                    //no need to save anything because only the major they pick matters
                    majors.add(e.get(i).text());
                    links.add(e.get(i).attr("href"));
                    p.put(e.get(i).text(), e.get(i).attr("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
        }
    }

}
