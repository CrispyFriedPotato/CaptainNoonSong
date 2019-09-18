package com.example.sookchat.Main;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sookchat.Agora.AgoraFragment;
import com.example.sookchat.FragmentReplaceable;
import com.example.sookchat.MapFragment;
import com.example.sookchat.Map_Select;
import com.example.sookchat.R;
import com.example.sookchat.Route_Select;
import com.example.sookchat.Route_Select_list;
import com.example.sookchat.Tour_Select;
import com.example.sookchat.Watson.ChatbotFragment;

import okhttp3.Route;

public class MainActivity extends AppCompatActivity implements FragmentReplaceable ,Route_Select_list.OnFragmentInteractionListener {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private AgoraFragment agoraFragment = new AgoraFragment();
    private ChatbotFragment chatbotFragment = new ChatbotFragment();
    private Map_Select map_select = new Map_Select();

    private Tour_Select tourFragment = new Tour_Select();
    private Route_Select routeFragment = new Route_Select();
    private MapFragment mapFragment = new MapFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //splash
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, chatbotFragment).commitAllowingStateLoss();
        bottomNavigationView.setSelectedItemId(R.id.navigation_menu2);

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, agoraFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, chatbotFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, map_select).commitAllowingStateLoss();
                        //transaction.replace(R.id.frame_layout, mapFragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });

    }

    @Override
    public void onFragmentInteraction(String text) {
        routeFragment.setText(text);
    }

    public void replaceFragment(int fragmentId){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragmentId ==1){
            transaction.replace(R.id.frame_layout, tourFragment);
        }
        if(fragmentId ==2){
            transaction.replace(R.id.frame_layout, routeFragment);
        }
        if(fragmentId ==3){
            transaction.replace(R.id.frame_layout, mapFragment);
        }
        if(fragmentId==4){
            //from route_select to route_select_list
            transaction.replace(R.id.frame_layout,new Route_Select_list());
        }
        if(fragmentId==5){
            //from route_select_list to route_select
            transaction.replace(R.id.frame_layout,new Route_Select());
        }

        //Back 버튼 클릭 시 이전 프래그먼트로 이동시키도록 한다.
        transaction.addToBackStack(null);


        //fragment의 변경사항을 반영시킨다.
        transaction.commit();
    }

}
