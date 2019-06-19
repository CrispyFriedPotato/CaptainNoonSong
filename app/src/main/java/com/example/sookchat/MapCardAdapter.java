package com.example.sookchat;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MapCardAdapter extends RecyclerView.Adapter<MapCardAdapter.MyViewHolder>  {

    private LayoutInflater inflater;
    private ArrayList<MapCardFragment> imageModelArrayList;
    private onItemClickListener mListener;
    private Context mContext;

    private int[] myImageList = new int[]{R.drawable.school,R.drawable.newmyeong, R.drawable.myeong,
            R.drawable.law,R.drawable.sun,R.drawable.student,R.drawable.hang,R.drawable.su,R.drawable.noon,R.drawable.injae,
            R.drawable.science,R.drawable.library,R.drawable.sociology,R.drawable.art,
            R.drawable.medicine,R.drawable.millenium,R.drawable.music,};
    private String[] myImageNameList = new String[]{"숙명여자대학교","새힘관","명신관","진리관","순헌관",
            "학생회관","행정관","수련관","행파관","인재관",
            "이과대학","중앙도서관","사회교육관","미술대학","약학대학","백주년기념관","음악대학"};


    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MapCardAdapter.onItemClickListener listener){
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ClipData.Item currentItem;
        public TextView time;
        public ImageView iv;
        public View view;
        public CardView cv;

        public MyViewHolder(View itemView, final onItemClickListener listener)  {
            super(itemView);
            view = itemView;
            time = (TextView) itemView.findViewById(R.id.tv);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            cv = (CardView) itemView.findViewById(R.id.card_view);

        }

    }

    //Constructor, imageModelArrayList는 MapCardFragment class에서 object의 이미지 배열 리스트 받음
    public MapCardAdapter(Context ctx, ArrayList<MapCardFragment> imageModelArrayList){
        this.mContext = ctx;
        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
    }

    @Override
    public MapCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_map, parent, false);
        MyViewHolder holder = new MyViewHolder(view,mListener);


        holder.itemView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick (View view){
                Intent intent = new Intent(mContext, MapBuilding.class);
                mContext.startActivity(new Intent(mContext, MapBuilding.class).putExtra("id", 1));
            }


        });

        return holder;
    }

    //Under map, slide. This is for setting text in textview and image in imageview, row 갯수만큼 컴파이얼러가 이 함수 부름
    @Override
    public void onBindViewHolder(MapCardAdapter.MyViewHolder holder, int position) {
        holder.iv.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        holder.time.setText(imageModelArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

}

