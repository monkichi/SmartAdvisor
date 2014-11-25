package com.example.smartadvisor;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

//TODO: pass past courses to plan fragment. then calculate plan to graduation inside plan fragment
public class Main extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    String type;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    //variables for other purposes (data storage, info about student classes, etc)
    String filename = "future_plan_data";
    String sharedname = "future";
    ArrayList<Course> past;
    SharedPreferences shared;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        fileopen();
    }

    @Override
    public void onStop(){
        super.onStop();

        SharedPreferences shared = getSharedPreferences(sharedname, 0);
        SharedPreferences.Editor editor = shared.edit();

        // create hash set for each semester. load classes for semester into set then pass to shared preferences
    }

    public void fileopen() {
        // maybe check to see if getting info from set preferences returns null. if so then get info. if now then go to plan fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SharedPreferences shared = getSharedPreferences(sharedname, 0);
        if (shared.getString("type", null) != null) {
            PlanFragment planFragment = new PlanFragment();
            ft.replace(R.id.container, planFragment);
            ft.commit();
        } else {
            BlankFragment blankFragment = new BlankFragment();
            ft.add(R.id.container, blankFragment);
            ft.commit();
        }
        /*try{
            FileInputStream filein = new FileInputStream(filename);
        }catch (FileNotFoundException f){
            File newfile = new File(filename);
            getNewStudentData();
        }*/
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.fresh_radio:
                if(checked)
                    type = "freshman";
                break;
            case R.id.trans_radio:
                if(checked)
                    type = "transfer";
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        CourseChart courseChart = new CourseChart();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.comp108check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 180"));
                break;
            case R.id.comp110check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 110/L"));
                break;
            case R.id.comp122check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 122/L"));
                break;
            case R.id.comp182check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 182/L"));
                break;
            case R.id.comp222check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 222"));
                break;
            case R.id.comp256_Lcheck:
                if (checked)
                    past.add(courseChart.getCourse("Comp 256/L"));
                break;
            case R.id.comp282check:
                if (checked)
                    past.add(courseChart.getCourse("Comp 282"));
                break;
            case R.id.math102check:
                if (checked)
                    past.add(courseChart.getCourse("Math 102"));
                break;
            case R.id.math104check:
                if (checked)
                    past.add(courseChart.getCourse("Math 104"));
                break;
            case R.id.math150Acheck:
                if (checked)
                    past.add(courseChart.getCourse("Math 150A"));
                break;
            case R.id.math150Bcheck:
                if (checked)
                    past.add(courseChart.getCourse("Math 150B"));
                break;
            case R.id.math262check:
                if (checked)
                    past.add(courseChart.getCourse("Math 262"));
                break;
            case R.id.phil230check:
                if (checked)
                    past.add(courseChart.getCourse("Phil 230"));
                break;
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.Home);
                break;
            case 2:
                mTitle = getString(R.string.Past);
                break;
            case 3:
                mTitle = getString(R.string.Advisement);
                break;
            case 4:
                mTitle = getString(R.string.Settings);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
