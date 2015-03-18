package com.example.smartadvisor;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass used to implement a radial button.
 *
 */

//This is to get the type of student. Maybe we can use this to display a selected number of classes
    //for them to select which ones they have taken?

public class RadialFragment extends Fragment {

    public RadialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_incoming_student, container, false);

        final Button btnDisplay = (Button) rootView.findViewById(R.id.btnDisplay);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                /*GetPastCourses get = new GetPastCourses();
                ft.add(R.id.container, get);
                ft.addToBackStack(null);
                ft.commit();*/
            }

        });

        return rootView;
    }

}
