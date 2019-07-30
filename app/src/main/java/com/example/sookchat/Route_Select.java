package com.example.sookchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


public class Route_Select extends Fragment {
    private Spinner spinner1;
    private Spinner spinner2;
    private static final String[] schools = new String[]{"새힘관", "명신관", "진리관","순헌관","학생회관","행정관","수련관","행파관","이과대학","중앙 도서회관","사회 교육관","미술 대학","약학 대학","백주년 기념관","음악 대학"};
    public Route_Select() {
        // Required empty public constructor
    }

    public static Route_Select newInstance(String param1, String param2) {
        Route_Select fragment = new Route_Select();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       }

    private void gpsCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("계속하려면 위치 기능 설정이 필요합니다.")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                turnOnGps();
                            }
                        })
                .setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // show GPS Options
    private void turnOnGps() {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsCheck();
        }


        View view = inflater.inflate(R.layout.fragment_route_select,container,false);

        spinner1= (Spinner)view.findViewById(R.id.spinner_start);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,schools);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2 = (Spinner)view.findViewById(R.id.spinner_destination);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,schools);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner2.setPrompt("도착 장소 선택");
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //spinner.setOnItemSelectedListener(getActivity());
        // Inflate the layout for this fragment
        return view;
    }

    }



