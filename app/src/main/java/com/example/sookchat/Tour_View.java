package com.example.sookchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.MarkerClusterer;
import org.osmdroid.bonuspack.clustering.StaticCluster;
import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.sookchat.MyGPS.campus;


public class Tour_View<gPt> extends Activity {
    MapView mMapView = null;

    //for transfering between fragment and activity
    private int tourId;

    //LOG
    private static final String LOG_TAG = "Tour_View";
    private static final String TAG = "*********Tour_View";
    protected static int markerId;
    private Button GPSButton;
    protected static boolean markerColor;
    protected static int markerNumber;
    private ArrayList<GeoPoint> geoList;
    private static int campus = 0;
    //Buttons
    private Button descButton;
    //for getting the starting point building from MyGPS.java
    private int buildingnum=5;
    //Lists for drawing polyline
    private ArrayList<Integer> routeList;
    private ArrayList<String> buildingList;
    //Drawable nodeIcon = getResources().getDrawable(R.drawable.button_bg);
    //Marker currentLocation = new Marker(mMapView);
    private Marker currentLocation = null;
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            drawMarker(latitude,longitude);

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    public Tour_View() {
    }
    private String building = "건물을 알 수 없습니다";

    public void drawMarker(double latitude,double longitude){
        Drawable nodeIcon = getResources().getDrawable(R.drawable.button_bg);
        //Marker currentLocation = new Marker(mMapView);
        if(currentLocation != null) currentLocation.remove(mMapView);


        currentLocation = new Marker(mMapView);
        currentLocation.setIcon(nodeIcon);
        currentLocation.setPosition(new GeoPoint(latitude,longitude));
        mMapView.getOverlays().add(currentLocation);

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //Starting mapview
        MapsInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();

        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_tour_view);

        mMapView = (MapView) findViewById(R.id.tour_view_map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);


        //mMapView touchevent

//        mMapView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(
//                        getApplicationContext(),
//                        "View touched",
//                        Toast.LENGTH_LONG
//                );
//                toast.show();
//
//            }
//        });


        //map controller
        IMapController mapController = mMapView.getController();
        mapController.setZoom(19);
        GeoPoint startPoint = new GeoPoint(37.545871, 126.964413);
        mapController.setCenter(startPoint);

        //for choosing campus 1 and 2
        Intent intent = getIntent();
        tourId = intent.getIntExtra("tourId", 0);
        Log.e(TAG, "tourId = " + tourId);
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsCheck();
        }

        //Description button
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


        //GPSButton for starting point
        GPSButton = (Button) findViewById(R.id.getGPS_button);

        GPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, ": called.");
//                //move to MyGPS fragment
//                Intent intent = new Intent(Tour_View.this, MyGPS.class);
//                intent.putExtra("tourid", tourId);
//                startActivity(intent);

                //get starting point and save to variable buildingnum
                buildingnum = getStartPoint();

                //Important! this is for the NullpointException. user should click the button again.
                if (buildingnum > 0) {
                    ArrayList<Integer> route = getRoute(buildingnum);
                    getLine(tourId, route, geoList);
                    mMapView.getOverlays();

//
//                    long downTime = SystemClock.uptimeMillis();
//                    long eventTime = SystemClock.uptimeMillis() + 100;
//                    float x = 0.0f;
//                    float y = 0.0f;
//                    int metaState = 0;
//                    MotionEvent motionEvent = MotionEvent.obtain(
//                            downTime,
//                            eventTime,
//                            MotionEvent.ACTION_UP,
//                            x,
//                            y,
//                            metaState
//                    );
//                    mMapView.dispatchTouchEvent(motionEvent);




                }
                else {
                    //Alert문
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tour_View.this);
                    builder.setMessage("캠퍼스 내의 위치를 파악할 수 없습니다.")
                            .setCancelable(false)
                            .setPositiveButton("okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                    while(buildingnum < 0){
                        buildingnum = getStartPoint();
                    }

                }




            }






        });



        getLocation();


        //generating info of buildings in the list
        //모든 건물에 대한 gps정보가 들어있는 배열 geoList
        geoList = new ArrayList<GeoPoint>();
        buildingList = new ArrayList<String>();

        buildingList.add("새힘관");
        buildingList.add("명신관");
        buildingList.add("진리관");
        buildingList.add("순헌관");
        buildingList.add("학생회관");
        buildingList.add("행정관");

        GeoPoint gPt1 = new GeoPoint(37.545412, 126.96391); //새힘 geolist index 0에 해당
        GeoPoint gPt2 = new GeoPoint(37.545699, 126.96366); //명신
        GeoPoint gPt3 = new GeoPoint(37.546373, 126.963866); //진리
        GeoPoint gPt4 = new GeoPoint(37.546426, 126.964711); //순헌
        GeoPoint gPt5 = new GeoPoint(37.545394, 126.965015); //학생회관
        GeoPoint gPt6 = new GeoPoint(37.545384, 126.964527); //행정관
        GeoPoint gPt7 = new GeoPoint(37.546651, 126.964323); //수련
        GeoPoint gPt8 = new GeoPoint(37.546636, 126.965055); //행파


        GeoPoint gPt9 = new GeoPoint(37.544834, 126.964923); //프라임
        GeoPoint gPt10 = new GeoPoint(37.544737, 126.964084); //박물관
        GeoPoint gPt11 = new GeoPoint(37.544256, 126.964064); //음대
        GeoPoint gPt12 = new GeoPoint(37.543823, 126.963949); //사회
        GeoPoint gPt13 = new GeoPoint(37.543861, 126.964459); //약대
        GeoPoint gPt14 = new GeoPoint(37.544329, 126.964905); //미대
        GeoPoint gPt15 = new GeoPoint(37.543800, 126.965422); //백주년
        GeoPoint gPt16 = new GeoPoint(37.544114, 126.965980); //중앙도서관
        GeoPoint gPt17 = new GeoPoint(37.544591, 126.966441); //과학

        //르네상스 백주년 중도 과학관

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
        geoList.add(gPt14);
        geoList.add(gPt15);
        geoList.add(gPt16);
        geoList.add(gPt17);





    }

    //currentLocation 확인 및 마커 표시 함수
    public void getLocation(){

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Log.i(TAG, "1");
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Log.i(TAG, "2");
            ActivityCompat.requestPermissions( Tour_View.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{

            Log.i(TAG, "mygps is working well in tourview");

            String provider = LocationManager.NETWORK_PROVIDER;

            Location location = lm.getLastKnownLocation(provider);


            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            drawMarker(latitude,longitude);


            Log.i(TAG, "marker code is read.");




            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    500,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    500,
                    1,
                    gpsLocationListener);

        }

        Log.i(TAG, "3");

    }







    //시작위치 파악

    private int getStartPoint() {
//        Log.e(TAG, "getStartPoint: called.");
//        return sp; //박물관 시작

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Log.i(TAG, "1");
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            Log.i(TAG, "2");
            ActivityCompat.requestPermissions( Tour_View.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{

            Log.i(TAG, "mygps is working well in tourview");

            String provider = LocationManager.NETWORK_PROVIDER;

            Location location = lm.getLastKnownLocation(provider);


            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            if(longitude>126.963275 && longitude<126.964115){
                if(latitude>37.546212 &&latitude<37.546789){
                    building = "진리관";
                    campus = 3;
                }
            }

            if(longitude>126.964115 &&longitude<126.965534){
                if(latitude>37.545991 &&latitude<37.546789){
                    building = "순헌관";
                    campus = 4;
                }
            }

            if(longitude>126.963275 &&longitude<126.964115){
                if(latitude>37.545600 && latitude<37.546212){
                    building = "명신관";
                    campus = 2;
                }
            }

            if(longitude>126.963275 &&longitude<126.964115){
                if(latitude>37.545076 &&latitude<37.545600){
                    building = "새힘관";
                    campus = 1;
                }
            }

            if(longitude>126.964115 &&longitude<126.964802){
                if(latitude>37.545076 && latitude<37.545991){
                    building = "행정관";
                    campus = 6;
                }
            }

            if(longitude>126.964802 &&longitude<126.965534){
                if(latitude>37.545076 &&latitude<37.545991){
                    building = "학생회관";
                    campus = 5;
                }
            }




            //update막아놓음


//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    500,
//                    1,
//                    gpsLocationListener);
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                    500,
//                    1,
//                    gpsLocationListener);

        }

        return campus;

    }




    //GPSButton 내에서 사용되는 route 구하는 함수
    private ArrayList<Integer> getRoute(int sp) {
        Log.e(TAG, "getRoute: called.");
        routeList = new ArrayList<Integer>();

        LatLng Pt1 = new LatLng(37.545412, 126.96391);


        //2캠퍼스
        if (tourId == 2) {
            sp = buildingnum;
            routeList.add(sp);
            routeList.add(10);
            routeList.add(11);
            routeList.add(12);
            routeList.add(13);
            routeList.add(8);


        }
        //1캠퍼스
        else if (tourId == 1) {
            //MyGPS 의 campus값을 starting point 로 저장
            sp = buildingnum;


            //6개의 건물 반복
            for (int i = 0; i < 6; i++) {

                if (sp <= 6) {
                    routeList.add(sp);
                } else {
                    routeList.add(sp - 6);
                }


                //nodeIcon 초기화 값 핑크마커
                Drawable nodeIcon = getResources().getDrawable(R.drawable.button_bg);



                //list 추가된 순서에 따라 마커 아이콘 변경 하는 switch 문
                switch(i){

                    case 0:
                        nodeIcon = getResources().getDrawable(R.drawable.number_one);
                        break;

                    case 1:
                        nodeIcon = getResources().getDrawable(R.drawable.number_two);
                        break;

                    case 2:
                        nodeIcon = getResources().getDrawable(R.drawable.number_three);
                        break;

                    case 3:
                        nodeIcon = getResources().getDrawable(R.drawable.number_four);
                        break;

                    case 4:
                        nodeIcon = getResources().getDrawable(R.drawable.number_five);
                        break;

                    case 5:
                        nodeIcon = getResources().getDrawable(R.drawable.number_six);
                        break;




                }


                //marker 생성
                final Marker nodeMarker = new Marker(mMapView);



                //marker 위도 경도 고정. sp-1 : list의 index와 일치
                if (sp <= 6) {
                    nodeMarker.setPosition(new GeoPoint(geoList.get(sp-1).getLatitude(),geoList.get(sp-1).getLongitude()));
                    nodeMarker.setTitle(buildingList.get(sp-1));
                    sp++;
                } else {
                    nodeMarker.setPosition(new GeoPoint(geoList.get(sp-7).getLatitude(),geoList.get(sp-7).getLongitude()));
                    nodeMarker.setTitle(buildingList.get(sp-7));
                    sp++;
                }


                //marker 이미지 저장
                nodeMarker.setIcon(nodeIcon);


                //marker 클릭 이벤트 생성
                nodeMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        String j = nodeMarker.getTitle();

                        if(j == "새힘관"){
                            markerId=4;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }

                        if(j == "명신관"){
                            markerId=3;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }

                        if(j == "진리관"){
                            markerId=1;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }

                        if(j == "순헌관"){
                            markerId=2;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }

                        if(j == "학생회관"){
                            markerId=6;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }

                        if(j == "행정관"){
                            markerId=5;
                            Intent intent = new Intent(Tour_View.this, MapDescActivity.class);
                            intent.putExtra("tourid", tourId);
                            startActivity(intent);
                        }


                        return false;
                    }
                });


                //map에 마커 표시
                mMapView.getOverlays().add(nodeMarker);



            }


        }

        return routeList;
    }





    //polyline 그리는 함수
    private void getLine(int tourId, ArrayList<Integer> routeList, ArrayList<GeoPoint> geoList) {
        Log.e(TAG, "getLine: called.");
        Log.e("tourId", "" + tourId);
        //routeList에 나열된 건물 번호  == geoList의 index
        List<GeoPoint> geoPoints = new ArrayList<>();
        for (int i : routeList) {
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


    //gps 켜져있는지 확인
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