package com.example.sookchat;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
/**This is an adapter for list in route_select**/

public class RouteBuildingAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;
    Route_Select_list mROuteSelectList;

    public RouteBuildingAdapter(List<String> list, Context context) {
            this.list = list;
            this.context = context;
            this.inflate = LayoutInflater.from(context);
        }

    @Override
    public int getCount() {
        return list.size() ;
    }
    @Override
    public long getItemId ( int position){
        return position;
    }
    @Override
    public Object getItem ( int position){
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
         convertView =inflate.inflate(R.layout.route_view,null);

            viewHolder = new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

            viewHolder.name.setText(list.get(position));
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.i("ONCLICK","YOU clicked");
            }

        });
            return convertView;
        }


        class ViewHolder {
            public TextView name;
        }




}
