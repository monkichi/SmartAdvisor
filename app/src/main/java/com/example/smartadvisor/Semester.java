package com.example.smartadvisor;

import java.util.ArrayList;

public class Semester {
    public ArrayList<Course> courses;
    private String name;

    public Semester(String n){
        name = n;
        courses = new ArrayList<Course>();
    }

    public String getName(){return name;}
}
