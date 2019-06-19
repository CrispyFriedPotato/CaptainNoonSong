package com.example.sookchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MapBuilding extends AppCompatActivity {

    private int[] myImageList = new int[]{R.drawable.school,R.drawable.newmyeong, R.drawable.myeong,
            R.drawable.law,R.drawable.sun,R.drawable.student,R.drawable.hang,R.drawable.su,R.drawable.noon,R.drawable.injae,
            R.drawable.science,R.drawable.library,R.drawable.sociology,R.drawable.art,
            R.drawable.medicine,R.drawable.millenium,R.drawable.music,};
    private String[] myImageNameList = new String[]{"숙명여자대학교","새힘관","명신관","진리관","순헌관",
            "학생회관","행정관","수련관","행파관","인재관",
            "이과대학","중앙도서관","사회교육관","미술대학","약학대학","백주년기념관","음악대학"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_building);

        ImageView image = (ImageView)findViewById(R.id.imageview_header);
        TextView title = (TextView)findViewById(R.id.textview_title);
        TextView txtInfo = (TextView)findViewById(R.id.textview);


    }
}

