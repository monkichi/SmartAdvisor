package com.example.smartadvisor;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseChart {

    private HashMap<String,Course> map;
    private ArrayList<Course> starters;

    public CourseChart(){
        starters = new ArrayList<Course>();
        map = new HashMap<String, Course>();
        Course a = new Course(null, "Comp 108"),
               b = new Course(null, "Math 102"),
               c = new Course(null, "Phil 230");

        map.put(a.getName(), a);
        map.put(b.getName(), b);
        map.put(c.getName(), c);

        addtomap(a.getName(),b.getName(),"Comp 110");
        addtomap("Math 102", "Math 104");
        addtomap("Math 104", "Math 150A");
        addtomap("Math 150A", "Math 150B");
        addtomap("Math 150B", "Math 262");
        addtomap("Math 150B", "Math 341");
        addtomap("Math 262", "Math 482");
        addtomap("Math 104", "Comp 110", "Comp 122/L");
        addtomap("Math 104", "Comp 110", "Comp 182/L");
        addtomap("Comp 182/L", "Comp 282");
        addtomap("Comp 182/L", "Comp 122/L", "Comp 222");
        addtomap("Comp 182/L", "Math 150A", "Phil 230", "Comp 256/L");
        addtomap("Comp 122/L", "Comp 182/L", "Comp 333");
        addtomap("Comp 256/L", "Comp 333", "Comp 310");
        addtomap("Comp 282" , "Comp 380/L");
        addtomap("Comp 222", "Comp 322/L");
        addtomap("Comp 380/L", "Comp 490/L");
        addtomap("Comp 490/L", "Comp 491L");
        starters.add(a);
        starters.add(b);
    }

    public void addtomap(String p, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        map.get(p).next.add(new Course(pre,n));
        map.put(n, new Course(pre,n));
    }

    public void addtomap(String p, String p2, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        pre.add(map.get(p2));
        for(int i=0; i < pre.size(); i++){
            map.get(pre.get(i).getName()).next.add(new Course(pre,n));
        }
        map.put(n, new Course(pre,n));
    }

    public void addtomap(String p, String p2, String p3, String n){
        ArrayList<Course> pre = new ArrayList<Course>();
        pre.add(map.get(p));
        pre.add(map.get(p2));
        pre.add(map.get(p3));
        for(int i=0; i < pre.size(); i++){
            map.get(pre.get(i).getName()).next.add(new Course(pre,n));
        }
        map.put(n, new Course(pre,n));
    }

    public ArrayList<Course> getStarters(){ return starters; }

    public Course getCourse(String n){
        return map.get(n);
    }
}
