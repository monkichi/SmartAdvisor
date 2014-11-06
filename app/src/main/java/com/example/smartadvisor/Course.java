package com.example.smartadvisor;

import java.util.ArrayList;


public class Course {

    private ArrayList<Course> prereqs;
    private String name;
    private boolean elective;

    public Course(ArrayList<Course> p, String n, boolean e){
        prereqs = p;
        name = n;
        elective = e;
    }

    public ArrayList<Course> getPrereqs(){
        return prereqs;
    }

    public String getName(){
        return name;
    }

    public boolean isElective(){
        return elective;
    }
}
