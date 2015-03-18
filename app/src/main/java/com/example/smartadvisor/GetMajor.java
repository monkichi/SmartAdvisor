package com.example.smartadvisor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GetMajor extends Fragment {

    int count = 0 ; // this is used for counting the number of stored data
    String url = "http://www.csun.edu/catalog/planning/plans/2010/computer-engineering-4/";
    ProgressDialog mProgressDialog;
    public static final String PREFS_NAME = "MyPrefsFile"; // name of stored data table
    List<String> example = new ArrayList<String>(); // this is attached to autocomplete text adaptor
    List<String> saved = new ArrayList<String>(); //this is connected to list view to display saved items


    //private OnFragmentInteractionListener mListener;
    // TODO: Rename and change types and number of parameters
    public static GetMajor newInstance(String param1, String param2) {
        GetMajor fragment = new GetMajor();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    public GetMajor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE); // instance of the table
//this down here , checks to see if theres any data saved before , is attaches them to the adaptor
        if(prefs.contains("last")){
            int lastitem = Integer.parseInt( prefs.getString("last", null));
            for(int i=0; i< lastitem ; i++)
                saved.add( prefs.getString(Integer.toString(i),null) );
        }

        example.add("example");
        example.add("second");
        saved.add("first");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_get_major, container, false);
        Button titlebutton = (Button) rootview.findViewById(R.id.titlebutton);
        Button descbutton = (Button) rootview.findViewById(R.id.descbutton);
        AutoCompleteTextView textView = (AutoCompleteTextView) rootview.findViewById(R.id.autocomplete_course);




        // Create an ArrayAdapter containing course names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, example);
        ArrayAdapter<String> mSaved = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, saved);

        // Set the adapter for the AutoCompleteTextView
        textView.setAdapter(adapter);
        ListView listview = (ListView)rootview.findViewById(R.id.listView);
        listview.setAdapter(mSaved);





        // Capture button click where it download the title of the page
        titlebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });

        // Capture button click where it fetched the data
        descbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTasK
                new Description().execute();
            }
        });
        return inflater.inflate(R.layout.fragment_get_major, container, false);
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("getting the title");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
                title = document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) getActivity().findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;
        ArrayList<String> downServers = new ArrayList<String>();
        ArrayList<String> savedItem = new ArrayList<String>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Getting courses");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE).edit();
// down here in a nutshell , I am getting to the paragraphs( being the courses)
// of the html and get each element to
// add them to the list
                org.jsoup.nodes.Element table = document.select("table").get(1);
                Elements rows = table.select("p");
                desc = rows.get(2).text();
                for(int j=0; j<= 7 ; j++) {
                    table = document.select("table").get(j);
                    rows = table.select("p");
                    for (int i = 2; i < rows.size()-1; i++) {
                        if (!rows.get(i).text().matches(("-?\\d+")) && !rows.get(i).text().matches(("Total"))) {
                            Log.v("Courses", rows.get(i).text());
                            count++;
                            editor.putString( Integer.toString(count), rows.get(i).text());
                            editor.putString( "last", Integer.toString(count));
                            //        editor.putInt("idName", i);
                            editor.commit();

                            downServers.add(rows.get(i).text());

                        }

                    }
                }



            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdesc = (TextView) getActivity().findViewById(R.id.desctxt);
            txtdesc.setText(desc);
            SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
            // here im adding the saved items to its arraylist

            for(int i=1;i<count ; i++){
                saved.add(prefs.getString( Integer.toString(i),"No name defined"));
            }


            for(int i=0;i<downServers.size();i++)
                example.add(downServers.get(i));

            mProgressDialog.dismiss();
        }
    }

}
