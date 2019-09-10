package com.example.sookchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2017-08-07.
 */

public class RouteBuildingAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    private ArrayList<Building_list> nameAndcampus = new ArrayList<Building_list>() ;
    public RouteBuildingAdapter() {
            this.list = list;
            this.context = context;
            this.inflate = LayoutInflater.from(context);
        }

    @Override
    public int getCount() {
        return nameAndcampus.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.route_view, parent, false);}
//            viewHolder = new ViewHolder();
//            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
//            viewHolder.campus= (TextView) convertView.findViewById(R.id.campus);
//
//            convertView.setTag(viewHolder);
//        } else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//
//         // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
//        viewHolder.name.setText(list.get(position));
//        viewHolder.campus.setText(list.get(position));

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            TextView titleTextView = (TextView) convertView.findViewById(R.id.name);
            TextView descTextView = (TextView) convertView.findViewById(R.id.campus);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            Building_list listViewItem = nameAndcampus.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            titleTextView.setText(listViewItem.getName());
            descTextView.setText(listViewItem.getCampus());

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId ( int position){
            return position;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem ( int position){
            return nameAndcampus.get(position);
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem (String name, String campus){
            Building_list item = new Building_list();


            item.setName(name);
            item.setCampus(campus);

            nameAndcampus.add(item);
        }

        class ViewHolder {
            public TextView name;
            public TextView campus;
        }
    }

//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        if(convertView == null){
//            convertView = inflate.inflate(R.layout.route_view,null);
//
//            viewHolder = new ViewHolder();
//            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
//            viewHolder.campus= (TextView) convertView.findViewById(R.id.campus);
//
//            convertView.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//
//        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
//        viewHolder.name.setText(list.get(position));
//        viewHolder.campus.setText(list.get(position));
//
//        return convertView;
//    }
//
//


//source : https://sharp57dev.tistory.com/11