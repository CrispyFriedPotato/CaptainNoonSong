package com.example.sookchat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Map_Select extends Fragment {

    private Button tourButton;
    private Button routeButton;
    private Button viewButton;



    public Map_Select() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_select, container, false);

        tourButton = (Button) view.findViewById(R.id.tour_button);
        routeButton =(Button) view.findViewById(R.id.route_button);
        viewButton = (Button) view.findViewById(R.id.view_button);

        tourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인터페이스를 통한 방법
                //FragmentReplacable 를 쓰면 어딜가더라도 쓸 수 있다.
                ((FragmentReplaceable) getActivity()).replaceFragment(1);
            }
        });

        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(2);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplaceable) getActivity()).replaceFragment(3);
            }
        });

        return view;

    }



}
