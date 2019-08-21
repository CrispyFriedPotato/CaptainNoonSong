package com.example.sookchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;


public class Tour_View extends Activity implements OnMapReadyCallback {
    // NaverMap API 3.0
    private MapView mapView;
    private LocationButtonView locationButtonView;

    //for transfering between fragment and activity
    private int tourId;
    private static final String LOG_TAG = "Tour_View";
    private static final String TAG = "Tour_View";

    // FusedLocationSource (Google)
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_view);

        Intent intent = getIntent();
        tourId = intent.getIntExtra("tourId", 0);
        Log.e(TAG, "tourId = " + tourId);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);


    }
    private void gpsCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void onMapReady(@NonNull final NaverMap naverMap) {
        //map을 생성할 준비가 되었을때 가장 먼저 호출되는 오버라이드 메소드
        // 지도에 관한 설정(초기화)은 모두 onMapReady에서

        //naverMap.getUiSettings().setLocationButtonEnabled(true);
        //locationButtonView.setMap(naverMap);

        // Location Change Listener을 사용하기 위한 FusedLocationSource 설정
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
        naverMap.setBuildingHeight(0.5f);
        naverMap.setIndoorEnabled(true);

        //여기서 사용자가 어느 캠퍼스를 선택했냐에 따라 보여주는 카메라 포지션과 서북단 동남단 제한이 달라지면 될듯요.
        //**********2019.08.21 근데 카메라 포지션이 지금 안먹힌다 ㅠㅠㅠㅠ 이 안에서는 안먹히고
        //activity_tour_view.xml에서 카메라 포지션 썻을떄는 먹힘. 그러나 캠퍼스에 따라 다르게 카메라를 보여주려면
        //어떻게 해야할지 아직 해결책을 모르겠음
        LatLng location = new LatLng(37.487936, 126.825071);
        CameraPosition cameraPosition = new CameraPosition(location, 17);
        naverMap.setCameraPosition(cameraPosition);
        // 카메라 영역 제한...이것도 안먹힘. 왜지?
        LatLng northWest = new LatLng(37.546836, 126.962978);   //서북단
        LatLng southEast = new LatLng(37.543722, 126.966657);   //동남단
        naverMap.setExtent(new LatLngBounds(northWest, southEast));

    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        LocationManager locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsCheck();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}



