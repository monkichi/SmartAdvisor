package com.example.mehrzadshabez.appjam001;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.sax.Element;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehrzadshabez.appjam001.R;

public class MainActivity extends Activity {
    int count = 0 ; // this is used for counting the number of stored data
    String url = "http://www.csun.edu/catalog/academics/ece/courses/";
    ProgressDialog mProgressDialog;
    public static final String PREFS_NAME = "MyProfsFile"; // name of stored data table
    List<String> example = new ArrayList<>(); // this is attached to autocomplete text adaptor
    List<String> saved = new ArrayList<>(); //this is connected to list view to display saved items

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient());
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE); // instance of the table
//this down here , checks to see if theres any data saved before , is attaches them to the adaptor
            if(prefs.contains("last")){
                int lastitem = Integer.parseInt( prefs.getString("last", null));
                for(int i=0; i< lastitem ; i++)
                 saved.add( prefs.getString(Integer.toString(i),null) );
            }




        ListView listview = (ListView)findViewById(R.id.listView);
        example.add("example");
        example.add("second");
        saved.add("first");
//        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button descbutton = (Button) findViewById(R.id.descbutton);
    //    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_course);




        // Create an ArrayAdapter containing course names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, example);
          ArrayAdapter<String> mSaved = new ArrayAdapter<>(this,
                R.layout.list_item, saved);

        // Set the adapter for the AutoCompleteTextView
      //  textView.setAdapter(adapter);

        listview.setAdapter(mSaved);





        // Capture button click where it download the title of the page
     /*   titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });
*/
        // Capture button click where it fetched the data
        descbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask
                new Description().execute();
            }
        });

    }



    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
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
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc = "test";
        ArrayList<String> downServers = new ArrayList<>();
        ArrayList<String> savedItem = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Getting courses");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
// down here in a nutshell , I am getting to the paragraphs( being the courses)
// of the html and get each element to
// add them to the list
              //  org.jsoup.nodes.Element table = document.select("table").get(15);
                //Elements rows = table.select("p");
               Elements divs = document.select("div.col-xs-12.col-sm-12.col-md-12.col-lg-12.inner-item.clearfix");
               Elements heads = divs.select("p");
//                org.jsoup.nodes.Element link = document.select("select#NR_SSS_SOC_NWRK_STRM.PSDROPDOWNLIST").first();

                Elements options = document.select("div") ;

             //   org.jsoup.nodes.Element masthead = options.select("option").get(0);

                //Elements options = document.select("select > option");
                desc = Integer.toString(options.size());
                desc = options.get(20).toString();
                desc = Integer.toString(divs.size());
                desc = Integer.toString(heads.size());
                desc = heads.get(5).text();

                CharSequence cs1 = "Prerequis";
                if(desc.contains(cs1) ){
 //                   desc = "true";
                    String delims = "[.]";
                    String[] tokens = desc.split(delims);
                    //String[] parts = desc.split(". ");
                    String part1 = tokens[0]; // 004
                   // 034556
                    CharSequence cs2 = "and";
                    CharSequence cs3 = "or";
                   /* if(part1.contains(cs2) || part1.contains(cs3))
                    {
                        if
                    }
*/

                    String[] prereqs = part1.split(" ");
                    int numb = prereqs.length;
                    //desc = Integer.toString(numb);
                    if(numb == 3)
                    desc = prereqs[1]+ prereqs[2];
                    if(numb == 5  )
                        desc = "5 " + prereqs[1]+prereqs[2] + " " + prereqs[3] +  prereqs[4];
                    if(numb == 6  )
                        desc = prereqs[1]+prereqs[2] + " and " + prereqs[4] +  prereqs[5];


                }
                else
                    desc = "false";
//                if(!info.isEmpty())
  //                      desc = info.toString();
                //desc = info.toString();//text();//rows.get(0).text();
                /*
                for(int j=0; j<= 1 ; j++) {
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
                }*/



            } catch (IOException e) {
                e.printStackTrace();
                Log.e("table ","not found ");
            }






            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            TextView txtdesc = (TextView) findViewById(R.id.titletxt);
            txtdesc.setText(desc);
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
         // here im adding the saved items to its arraylist

           /* for(int i=1;i<count ; i++){
                saved.add(prefs.getString( Integer.toString(i),"No name defined"));
            }


            for(int i=0;i<downServers.size();i++)
            example.add(downServers.get(i));
*/
            mProgressDialog.dismiss();
        }
    }


}