package com.example.smartadvisor;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseChart {

    HashMap<String,Course> map;

    public CourseChart(){
        map = new HashMap<String, Course>();

        ArrayList<Course> p = new ArrayList<Course>();
        Course a = new Course(null, "Comp 108"),
               b = new Course(null, "Math 102"),
               c = new Course(null, "Phil 230");

        map.put(a.getName(), a);
        map.put(b.getName(), b);
        map.put(c.getName(), c);

        addtomap(a.getName(),b.getName(),"Comp110");
        addtomap("Math 102", "Math104");
        addtomap("Math 104", "Math 150A");
        addtomap("Math 150A", "Math 150B");
        addtomap("Math 150B", "Math 262");
        addtomap("Math 150B", "Math 341");
        addtomap("Math 262", "Math 482");
        addtomap("Math 104", "Comp 110", "Comp 122/L");
        addtomap("Math 104", "Comp 110", "comp 182/L");
        addtomap("Comp 182/L", "Comp 282");
        addtomap("Comp 182/L", "Comp 122/L", "Comp 222");
        addtomap("Comp 182/L", "Math 150A", "Phil 230", "Comp 256/L");
        addtomap("Comp 122/L", "Comp 182/L", "Comp 333");
        addtomap("Comp 256/L", "Comp 333", "Comp 310");
        addtomap("Comp 282" , "Comp 380/L");
        addtomap("Comp 222", "Comp 322/L");
        addtomap("Comp 380/L", "Comp 490/L");
        addtomap("Comp 490/L", "Comp 491L");
    }

    public void addtomap(String p, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        map.put(n, new Course(pre,n));
    }

    public void addtomap(String p, String p2, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        pre.add(map.get(p2));
        map.put(n, new Course(pre,n));
    }

    public void addtomap(String p, String p2, String p3, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        pre.add(map.get(p2));
        pre.add(map.get(p3));
        map.put(n, new Course(pre,n));
    }
}
