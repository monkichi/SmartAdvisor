package com.example.smartadvisor;



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
 * A simple {@link Fragment} subclass.
 *
 */
public class BlankFragment extends Fragment {

    String type;

    public BlankFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_incoming_student, container, false);

        final RadioGroup group = (RadioGroup) rootView.findViewById(R.id.student_group);
        final Button btnDisplay = (Button) rootView.findViewById(R.id.btnDisplay);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
        RadioButton studtype;
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = group.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                studtype = (RadioButton) rootView.findViewById(selectedId);

                Toast.makeText(getActivity(),
                        studtype.getText(), Toast.LENGTH_SHORT).show();

                type = studtype.toString();
            }

        });

        return rootView;
    }

    public String getType(){
        return type;
    }

}
