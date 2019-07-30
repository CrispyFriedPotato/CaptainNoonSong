package com.example.sookchat;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Tour_Select extends Fragment {

    public Tour_Select(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)

    {

        View v = inflater.inflate(R.layout.fragment_tour_select,container,false);
        ImageButton btn1 = v.findViewById(R.id.btn_1);
        ImageButton btn2 = v.findViewById(R.id.btn_2);

       /* btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

            }

        });

        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

            }

        });*/

        return v;

    }



}