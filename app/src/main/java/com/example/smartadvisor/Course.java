package com.example.smartadvisor;

import java.util.ArrayList;


public class Course {

    private ArrayList<Course> prereqs;
    public ArrayList<Course> next;
    private String name;
    private boolean elective;

    public Course(ArrayList<Course> p, String n){
        prereqs = p;
        name = n;
        elective = false;
        next = new ArrayList<Course>();
    }

    public ArrayList<Course> getPrereqs(){
        return prereqs;
    }

    public String getName(){
        return name;
    }

    public void setElectiveTrue(){
        elective = true;
    }

    public boolean isElective(){
        return elective;
    }

    @Override
    public String toString() {
        return name;
    }
}
