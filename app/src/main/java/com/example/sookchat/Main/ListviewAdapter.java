package com.example.sookchat.Main;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sookchat.R;

import java.util.ArrayList;
public class ListviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Listviewitem> data;
    private int layout;
    public ListviewAdapter(Context context, int layout, ArrayList<Listviewitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getName();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        Listviewitem listviewitem=data.get(position);
        ImageView icon=(ImageView)convertView.findViewById(R.id.profile_iv);
        icon.setImageResource(listviewitem.getIcon());
        icon.setBackground(new ShapeDrawable(new OvalShape()));
        icon.setClipToOutline(true);
        TextView name=(TextView)convertView.findViewById(R.id.name_tv);
        name.setText(listviewitem.getName());

        TextView major=(TextView)convertView.findViewById(R.id.major_tv);
        major.setText(listviewitem.getMajor());
        return convertView;
    }
}