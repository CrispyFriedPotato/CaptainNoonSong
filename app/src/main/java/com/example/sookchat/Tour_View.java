package com.example.sookchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


public class Tour_View extends Activity  {
    // NaverMap API 3.0
    MapView mMapView=null;
    //private MapView mapView;
    //private LocationButtonView locationButtonView;

    //for transfering between fragment and activity
    private int tourId;
    private static final String LOG_TAG = "Tour_View";
    private static final String TAG = "*********Tour_View";
    private Button descButton;

    // FusedLocationSource (Google)
    //private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    //private FusedLocationSource locationSource;

    public Tour_View() {
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_tour_view);

        mMapView = (MapView) findViewById(R.id.tour_view_map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = mMapView.getController();
        mapController.setZoom(17.5);
        GeoPoint startPoint = new GeoPoint(37.545128, 126.964523);
        mapController.setCenter(startPoint);

        Intent intent = getIntent();
        tourId = intent.getIntExtra("tourId", 0);
        Log.e(TAG, "tourId = " + tourId);
        LocationManager locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsCheck();
        }
        descButton = (Button)findViewById(R.id.desc_button);

        descButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.e(TAG,": called.");
                Intent intent = new Intent(Tour_View.this, MyGPS.class);
                intent.putExtra("tourid", tourId);
                startActivity(intent);

            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
}



