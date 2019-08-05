package com.example.sookchat;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;


public class Tour_View extends Activity {
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

    public void onMapReady(@NonNull final NaverMap naverMap) {
        //naverMap.getUiSettings().setLocationButtonEnabled(true);
        locationButtonView.setMap(naverMap);

        // Location Change Listener을 사용하기 위한 FusedLocationSource 설정
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
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



