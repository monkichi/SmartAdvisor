package com.example.smartadvisor;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class AdvisementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<Course> allCourses;
    private ArrayList<Course> pastCourses;
    private ArrayList<Course> currentCourses;

    public static AdvisementFragment newInstance(int sectionNumber) {
        AdvisementFragment fragment = new AdvisementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AdvisementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advisement, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, updateAdvisementPage());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Main) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private String[] updateAdvisementPage() {
        // called in onCreateView to update the array before displaying it
        allCourses = new CourseChart().getStarters();
        pastCourses = new GetPastCourses().getPast();

        ArrayList<String> courseList = new ArrayList<String>();
        for (Course allCourse : allCourses) {
            courseList.add(allCourse.toString());
        }
        // not yet implemented...
        // can only calculate a plan to graduate based off of past courses
        //currentCourses = new GetCurrentCourses().getCurrent();

        String[] courseListString = courseList.toArray(new String[courseList.size()]);

        return courseListString;
    }
}