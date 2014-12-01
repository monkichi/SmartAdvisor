package com.example.smartadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private int Imageid;
    private CourseChart courseChart;
    private ArrayList<Semester> p;
    public CustomGrid(Context c,String[] web,int Imageid, ArrayList<Semester> plan) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        p = plan;
        courseChart = new CourseChart();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            if(web[position].contains("Fall")){
                this.Imageid = R.drawable.fall_semester_up;
            }else{
                this.Imageid = R.drawable.spring_semester_over;
            }
            imageView.setImageResource(Imageid);

        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}
