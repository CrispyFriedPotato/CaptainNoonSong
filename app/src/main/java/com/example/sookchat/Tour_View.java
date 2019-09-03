package com.example.sookchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;


public class Tour_View extends Activity {
    // NaverMap API 3.0
    MapView mMapView = null;
    //private MapView mapView;
    //private LocationButtonView locationButtonView;

    //for transfering between fragment and activity
    private int tourId;
    private static final String LOG_TAG = "Tour_View";
    private static final String TAG = "*********Tour_View";
    private Button descButton;
    private Button GPSButton;
    private int buildingnum;
    private ArrayList<Integer> routeList;
    private ArrayList<GeoPoint> geoList;
    private int sp;

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
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsCheck();
        }

        descButton = (Button) findViewById(R.id.desc_button);

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, ": called.");
                Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                intent.putExtra("tourid", tourId);
                startActivity(intent);

            }
        });


        GPSButton = (Button) findViewById(R.id.getGPS_button);

        GPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, ": called.");
                Intent intent = new Intent(Tour_View.this, MyGPS.class);
                intent.putExtra("tourid", tourId);
                startActivity(intent);

                buildingnum = MyGPS.campus;

            }
        });

        //모든 건물에 대한 gps정보가 들어있는 배열 geoList
        geoList = new ArrayList<GeoPoint>();

        GeoPoint gPt1= new GeoPoint(37.545412, 126.96391); //새힘
        GeoPoint gPt2= new GeoPoint(37.545699, 126.96366); //명신
        GeoPoint gPt3= new GeoPoint(37.546373, 126.963866); //진리
        GeoPoint gPt4= new GeoPoint(37.546426, 126.964711); //순헌
        GeoPoint gPt5= new GeoPoint(37.545394, 126.965015); //학생회관
        GeoPoint gPt6= new GeoPoint(37.546651, 126.964323); //수련
        GeoPoint gPt7= new GeoPoint(37.546636, 126.965055); //행파


        GeoPoint gPt8= new GeoPoint(37.544834, 126.964923); //프라임
        GeoPoint gPt9 = new GeoPoint(37.544737, 126.964084); //박물관
        GeoPoint gPt10= new GeoPoint(37.544256, 126.964064); //음대
        GeoPoint gPt11 = new GeoPoint(37.543823, 126.963949); //사회
        GeoPoint gPt12 = new GeoPoint(37.543861, 126.964459); //약대
        GeoPoint gPt13 = new GeoPoint(37.544329, 126.964905); //미대

        geoList.add(gPt1);
        geoList.add(gPt2);
        geoList.add(gPt3);
        geoList.add(gPt4);
        geoList.add(gPt5);
        geoList.add(gPt6);
        geoList.add(gPt7);
        geoList.add(gPt8);
        geoList.add(gPt9);
        geoList.add(gPt10);
        geoList.add(gPt11);
        geoList.add(gPt12);
        geoList.add(gPt13);


        sp  = getStartPoint(0);
        ArrayList<Integer> route = getRoute(sp);
        getLine(tourId, route, geoList);




//        startActivity(new Intent(Tour_View.this, LocationFinder.class));


    }


    private int getStartPoint(int sp){
        Log.e(TAG, "getStartPoint: called.");
        return sp; //박물관 시작
    }

    private ArrayList<Integer> getRoute(int sp){
        Log.e(TAG, "getRoute: called.");
        routeList = new ArrayList<Integer>();
        if (tourId ==2) {
            sp = 9;
            routeList.add(sp);
            routeList.add(10);
            routeList.add(11);
            routeList.add(12);
            routeList.add(13);
            routeList.add(8);
        }
        else if(tourId == 1){
            sp = 5;
            routeList.add(sp);
            routeList.add(1);
            routeList.add(2);
            routeList.add(3);
            routeList.add(4);

        }

        return routeList;
    }


    private void getLine(int tourId, ArrayList<Integer> routeList, ArrayList<GeoPoint> geoList){
        Log.e(TAG, "getLine: called.");
        Log.e("tourId", ""+tourId);
        //routeList에 나열된 건물 번호  == geoList의 index
        List<GeoPoint> geoPoints = new ArrayList<>();
        for(int i: routeList) {
            Log.e("i =", " " + i);
            Log.e("geoList.get(i-1)=", " " + geoList.get(i - 1));
            geoPoints.add(geoList.get(i - 1));
        }
        Polyline line = new Polyline();   //see note below!
        line.setPoints(geoPoints);
        line.setOnClickListener(new Polyline.OnClickListener() {
            @Override
            public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                Toast.makeText(mapView.getContext(), "polyline with " + polyline.getPoints().size() + "pts was tapped", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mMapView.getOverlayManager().add(line);


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