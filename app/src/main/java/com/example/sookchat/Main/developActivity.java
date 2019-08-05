package com.example.sookchat.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sookchat.R;

import java.util.ArrayList;

public class developActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        ListView listView = (ListView) findViewById(R.id.develop_list);

        ArrayList<Listviewitem> data = new ArrayList<>();
        Listviewitem jiho = new Listviewitem(R.drawable.jiho_profile,"이지호","컴퓨터과학부");
        Listviewitem youjin = new Listviewitem(R.drawable.youjin_profile,"최유진","경영학부");
        Listviewitem dahye = new Listviewitem(R.drawable.dahye_profile,"이다혜","통계학부");
        Listviewitem hyejin = new Listviewitem(R.drawable.hyejin_profile,"김혜진","경제학부");

        data.add(jiho);
        data.add(youjin);
        data.add(dahye);
        data.add(hyejin);

        ListviewAdapter adapter=new ListviewAdapter(this,R.layout.listview_developer,data);
        listView.setAdapter(adapter);


    }
}
