package com.example.sookchat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sookchat.Main.MainActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.GraphHopperRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class Route_Select extends Fragment implements View.OnClickListener {


    private static int MENU_LAST_ID = Menu.FIRST; // Always set to last unused id
    public static final String TAG = "osmBaseFrag";

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;

    IMapController mapController;
    ArrayList<GeoPoint> waypoints;
    int MENU_VERTICAL_REPLICATION = 0;
    int MENU_HORIZTONAL_REPLICATION = 0;
    int MENU_ROTATE_CLOCKWISE = 0;
    int MENU_ROTATE_COUNTER_CLOCKWISE = 0;
    int MENU_SCALE_TILES = 0;
    public String important1=null;
    public String important2=null;
    protected MapView mMapView;
    public int flag=3; //this is for checking user enters into departure or destination


    Button button;
    Handler mHandler=null;
    View rootView;
    private Marker currentLocation = null;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private GpsInfo newGps;

    GraphHopperRoadManager roadManager;
    Road road;

    public Route_Select() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disable StrictMode.ThreadPolicy to perform network calls in the UI thread.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         Handler mHandler = new Handler(Looper.getMainLooper());
         // LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        rootView = inflater.inflate(R.layout.fragment_route_select, container, false);
        mMapView =rootView.findViewById(R.id.route_select_map);
        Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        mMapView.setMultiTouchControls(true);

        //지도 focus 부분
        mapController = mMapView.getController();
        mapController.setZoom(17.5);

        //사용자 위치 gps 버튼
//        getLocation();

        //Setting start and end points
        waypoints = new ArrayList<>();
        newGps = new GpsInfo(getActivity());
       GeoPoint main = new GeoPoint(37.545933, 126.963772);
        mapController.setCenter(main);
        //마커 추가 코드
        Marker startMarker = new Marker(mMapView);
        Marker endMarker = new Marker(mMapView);
        roadManager = new GraphHopperRoadManager("7d7c5e0b-8521-4a3c-8eec-9ede1047d099", true);
        roadManager.addRequestOption("vehicle=foot");
        GeoPoint longAndlat = null;
        GeoPoint startPoint=null,endPoint=null;
        if(important1 != null || important2 != null) {

            switch (important1) {
                case "명신관":
                    longAndlat = new GeoPoint(37.545936, 126.963731);
                    break;
                case "새힘관":
                    longAndlat = new GeoPoint(37.545936, 126.963731);
                    break;
                case "진리관":
                    longAndlat = new GeoPoint(37.546265, 126.963767);
                    break;
                case "순헌관":
                    longAndlat = new GeoPoint(37.546344, 126.964720);
                    break;
                case "행파관":
                    longAndlat = new GeoPoint(37.546587, 126.964987);
                    break;
                case "교수 수련회관":
                    longAndlat = new GeoPoint(37.546572, 126.964352);
                    break;
                case "학생회관":
                    longAndlat = new GeoPoint(37.545372, 126.964929);
                    break;
                case "행정관":
                    longAndlat = new GeoPoint(37.545476, 126.964672);
                    break;
                case "프라임관":
                    longAndlat = new GeoPoint(37.544895, 126.964846);
                    break;
                case "음악대학":
                    longAndlat = new GeoPoint(37.544267, 126.964235);
                    break;
                case "미술대학":
                    longAndlat = new GeoPoint(37.544291, 126.964804);
                    break;
                case "사회대학":
                    longAndlat = new GeoPoint(37.543887, 126.963995);
                    break;
                case "백주년 기념관":
                    longAndlat = new GeoPoint(37.543930, 126.965277);
                    break;
                case "중앙 도서관":
                    longAndlat = new GeoPoint(37.544177, 126.966092);
                    break;
                case "약학대학":
                    longAndlat = new GeoPoint(37.543915, 126.964517);
                    break;
                case "과학관":
                    longAndlat = new GeoPoint(37.544576, 126.966068);
                    break;
                default:
                    break;
            }
            switch (important2) {
                case "명신관":
                    longAndlat = new GeoPoint(37.545936, 126.963731);
                    break;
                case "새힘관":
                    longAndlat = new GeoPoint(37.545936, 126.963731);
                    break;
                case "진리관":
                    longAndlat = new GeoPoint(37.546265, 126.963767);
                    break;
                case "순헌관":
                    longAndlat = new GeoPoint(37.546344, 126.964720);
                    break;
                case "행파관":
                    longAndlat = new GeoPoint(37.546587, 126.964987);
                    break;
                case "교수 수련회관":
                    longAndlat = new GeoPoint(37.546572, 126.964352);
                    break;
                case "학생회관":
                    longAndlat = new GeoPoint(37.545372, 126.964929);
                    break;
                case "행정관":
                    longAndlat = new GeoPoint(37.545476, 126.964672);
                    break;
                case "프라임관":
                    longAndlat = new GeoPoint(37.544895, 126.964846);
                    break;
                case "음악대학":
                    longAndlat = new GeoPoint(37.544267, 126.964235);
                    break;
                case "미술대학":
                    longAndlat = new GeoPoint(37.544291, 126.964804);
                    break;
                case "사회대학":
                    longAndlat = new GeoPoint(37.543887, 126.963995);
                    break;
                case "백주년 기념관":
                    longAndlat = new GeoPoint(37.543930, 126.965277);
                    break;
                case "중앙 도서관":
                    longAndlat = new GeoPoint(37.544177, 126.966092);
                    break;
                case "약학대학":
                    longAndlat = new GeoPoint(37.543915, 126.964517);
                    break;
                case "과학관":
                    longAndlat = new GeoPoint(37.544576, 126.966068);
                    break;
                default:
                    break;
            }
            //건물별 위경도값 가져오기
            if (flag == 0) {
                if(longAndlat!=null) {
                    startPoint = longAndlat;
                    mapController.setCenter(startPoint);
                    waypoints.add(startPoint);
                    startMarker.setIcon(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_launcher));
                    startMarker.setTitle("Start point");
                }
            } else if (flag == 1) {
                if (longAndlat != null) {
                    endPoint = longAndlat;
                    waypoints.add(endPoint);
                    endMarker.setPosition(endPoint);
                    endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    mMapView.getOverlays().add(endMarker);
                }
            }
           if(startPoint!=null && endPoint!=null) {
               road = roadManager.getRoad(waypoints);
               //경로마다 점 찍어 주기
               Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
               for (int i = 0; i < road.mNodes.size(); i++) {
                   RoadNode node = road.mNodes.get(i);
                   Marker nodeMarker = new Marker(mMapView);
                   nodeMarker.setPosition(node.mLocation);
                   nodeMarker.setIcon(nodeIcon);
                   nodeMarker.setSubDescription(Road.getLengthDurationText(this.getActivity(), node.mLength, node.mDuration));
                   nodeMarker.setTitle("Step " + i);
                   mMapView.getOverlays().add(nodeMarker);
               }

               Polyline roadOverlay = GraphHopperRoadManager.buildRoadOverlay(road);
               mMapView.getOverlays().add(roadOverlay);
               mMapView.invalidate();

               if (road.mStatus != Road.STATUS_OK) {
                   //handle error... warn the user, etc.
               }
           }
        }

//        mMapView.getOverlays().add(endMarker);
        //map refresh
        mMapView.invalidate();
        //마커 클릭시 생기는 버블 안의 text



        button = rootView.findViewById(R.id.route_button);

       //출발도착 선정하러 가자
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("******여기 첫번째 툴바","여기 왔당");
                ((MainActivity) getActivity()).replaceFragment(4);
            }
        });


        mHandler.post(new Runnable() {
                        public void run() {
                            Marker startMarker = new Marker(mMapView);
                            Marker endMarker = new Marker(mMapView);
                            GeoPoint longAndlat1 = null;
                            GeoPoint longAndlat2 = null;
                            GeoPoint startPoint=null,endPoint=null;
                            if(important1 != null || important2 != null) {

                                switch (important1) {
                                    case "명신관":
                                        longAndlat1 = new GeoPoint(37.545936, 126.963731);
                                        break;
                                    case "새힘관":
                                        longAndlat1 = new GeoPoint(37.545936, 126.963731);
                                        break;
                                    case "진리관":
                                        longAndlat1 = new GeoPoint(37.546265, 126.963767);
                                        break;
                                    case "순헌관":
                                        longAndlat1 = new GeoPoint(37.546344, 126.964720);
                                        break;
                                    case "행파관":
                                        longAndlat1 = new GeoPoint(37.546587, 126.964987);
                                        break;
                                    case "교수 수련회관":
                                        longAndlat1 = new GeoPoint(37.546572, 126.964352);
                                        break;
                                    case "학생회관":
                                        longAndlat1 = new GeoPoint(37.545372, 126.964929);
                                        break;
                                    case "행정관":
                                        longAndlat1 = new GeoPoint(37.545476, 126.964672);
                                        break;
                                    case "프라임관":
                                        longAndlat1 = new GeoPoint(37.544895, 126.964846);
                                        break;
                                    case "음악대학":
                                        longAndlat1 = new GeoPoint(37.544267, 126.964235);
                                        break;
                                    case "미술대학":
                                        longAndlat1 = new GeoPoint(37.544291, 126.964804);
                                        break;
                                    case "사회대학":
                                        longAndlat1 = new GeoPoint(37.543887, 126.963995);
                                        break;
                                    case "백주년 기념관":
                                        longAndlat1 = new GeoPoint(37.543930, 126.965277);
                                        break;
                                    case "중앙 도서관":
                                        longAndlat1 = new GeoPoint(37.544177, 126.966092);
                                        break;
                                    case "약학대학":
                                        longAndlat1 = new GeoPoint(37.543915, 126.964517);
                                        break;
                                    case "과학관":
                                        longAndlat1 = new GeoPoint(37.544576, 126.966068);
                                        break;
                                    default:
                                        break;
                                }
                                switch (important2) {
                                    case "명신관":
                                        longAndlat2 = new GeoPoint(37.545936, 126.963731);
                                        break;
                                    case "새힘관":
                                        longAndlat2 = new GeoPoint(37.545936, 126.963731);
                                        break;
                                    case "진리관":
                                        longAndlat2 = new GeoPoint(37.546265, 126.963767);
                                        break;
                                    case "순헌관":
                                        longAndlat2 = new GeoPoint(37.546344, 126.964720);
                                        break;
                                    case "행파관":
                                        longAndlat2 = new GeoPoint(37.546587, 126.964987);
                                        break;
                                    case "교수 수련회관":
                                        longAndlat2 = new GeoPoint(37.546572, 126.964352);
                                        break;
                                    case "학생회관":
                                        longAndlat2 = new GeoPoint(37.545372, 126.964929);
                                        break;
                                    case "행정관":
                                        longAndlat2 = new GeoPoint(37.545476, 126.964672);
                                        break;
                                    case "프라임관":
                                        longAndlat2 = new GeoPoint(37.544895, 126.964846);
                                        break;
                                    case "음악대학":
                                        longAndlat2 = new GeoPoint(37.544267, 126.964235);
                                        break;
                                    case "미술대학":
                                        longAndlat2 = new GeoPoint(37.544291, 126.964804);
                                        break;
                                    case "사회대학":
                                        longAndlat2 = new GeoPoint(37.543887, 126.963995);
                                        break;
                                    case "백주년 기념관":
                                        longAndlat2 = new GeoPoint(37.543930, 126.965277);
                                        break;
                                    case "중앙 도서관":
                                        longAndlat2 = new GeoPoint(37.544177, 126.966092);
                                        break;
                                    case "약학대학":
                                        longAndlat2 = new GeoPoint(37.543915, 126.964517);
                                        break;
                                    case "과학관":
                                        longAndlat2 = new GeoPoint(37.544576, 126.966068);
                                        break;
                                    default:
                                        break;
                                }
                                //건물별 위경도값 가져오기

                                if(longAndlat1!=null&&longAndlat2!=null) {
                                    startPoint = longAndlat1;
                                    mapController.setCenter(startPoint);
                                    waypoints.add(startPoint);
                                    startMarker.setIcon(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_launcher));
                                    startMarker.setTitle("Start point");
                                    endPoint = longAndlat2;
                                    waypoints.add(endPoint);
                                    endMarker.setPosition(endPoint);
                                    endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                    mMapView.getOverlays().add(endMarker);
                                }
                            }
                            if(startPoint!=null && endPoint!=null) {
                                road = roadManager.getRoad(waypoints);
                                //경로마다 점 찍어 주기
                                Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
                                for (int i = 0; i < road.mNodes.size(); i++) {
                                    RoadNode node = road.mNodes.get(i);
                                    Marker nodeMarker = new Marker(mMapView);
                                    nodeMarker.setPosition(node.mLocation);
                                    nodeMarker.setIcon(nodeIcon);
                                    nodeMarker.setSubDescription(Road.getLengthDurationText(getActivity(), node.mLength, node.mDuration));
                                    nodeMarker.setTitle("Step " + i);
                                    mMapView.getOverlays().add(nodeMarker);
                                }

                                Polyline roadOverlay = GraphHopperRoadManager.buildRoadOverlay(road);
                                mMapView.getOverlays().add(roadOverlay);
                                mMapView.invalidate();

                                if (road.mStatus != Road.STATUS_OK) {
                                    //handle error... warn the user, etc.
                                }
                            }
                            mMapView.invalidate();
            }
        });
        callPermission();
        return rootView;
    }

    public void setText(String text1,String text2) {
             important1 = text1;
             important2 = text2;
    }


//    //gps 켜져있는지 확인
//    private void gpsCheck() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage("계속하려면 위치 기능 설정이 필요합니다.")
//                .setCancelable(false)
//                .setPositiveButton("확인",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                turnOnGps();
//                            }
//                        })
//                .setNegativeButton("아니요",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//    // show GPS Options
//    private void turnOnGps() {
//        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(gpsOptionsIntent);
//    }
//
//    //user location marker function
//    public void drawMarker(double latitude,double longitude){
//        Drawable nodeIcon = getResources().getDrawable(R.drawable.button_bg);
//        //Marker currentLocation = new Marker(mMapView);
//        if(currentLocation != null) currentLocation.remove(mMapView);
//
//
//        currentLocation = new Marker(mMapView);
//        currentLocation.setIcon(nodeIcon);
//        currentLocation.setPosition(new GeoPoint(latitude,longitude));
//        mMapView.getOverlays().add(currentLocation);
//
//    }
//
//    //gps 표시 함수
//    final LocationListener gpsLocationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//
//            drawMarker(latitude,longitude);
//
//        }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        public void onProviderEnabled(String provider) {
//        }
//
//        public void onProviderDisabled(String provider) {
//        }
//    };
//
//    //currentLocation 확인 및 마커 표시 함수
//    public void getLocation(){
//
//        final LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        Log.i(TAG, "1");
//        if ( Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//            Log.i(TAG, "2");
//            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
//                    0 );
//        }
//        else{
//
//            Log.i(TAG, "mygps is working well in tourview");
//
//            String provider = LocationManager.NETWORK_PROVIDER;
//
//            Location location = lm.getLastKnownLocation(provider);
//
//            while (location == null) {
//                location = lm.getLastKnownLocation(provider);
//            }
//
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//
//            drawMarker(latitude,longitude);
//
//
//            Log.i(TAG, "marker code is read.");
//
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    500,
//                    1,
//                    gpsLocationListener);
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                    500,
//                    1,
//                    gpsLocationListener);
//
//        }
//
//        Log.i(TAG, "3");

//    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

        if (mMapView != null) {
            addOverlays();

            final Context context = this.getActivity();
            final DisplayMetrics dm = context.getResources().getDisplayMetrics();

            CopyrightOverlay copyrightOverlay = new CopyrightOverlay(getActivity());
            copyrightOverlay.setTextSize(10);

            mMapView.getOverlays().add(copyrightOverlay);
            mMapView.setMultiTouchControls(true);
            mMapView.setTilesScaledToDpi(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDetach");
        if (mMapView != null)
            mMapView.onDetach();
        mMapView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.getLooper().quit();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem add = menu.add("Run Tests");
        MENU_LAST_ID++;
        MENU_VERTICAL_REPLICATION = MENU_LAST_ID;
        menu.add(0, MENU_VERTICAL_REPLICATION, Menu.NONE, "Vertical Replication").setCheckable(true);
        MENU_LAST_ID++;
        MENU_HORIZTONAL_REPLICATION = MENU_LAST_ID;
        menu.add(0, MENU_HORIZTONAL_REPLICATION, Menu.NONE, "Horizontal Replication").setCheckable(true);

        MENU_LAST_ID++;
        MENU_SCALE_TILES = MENU_LAST_ID;
        menu.add(0, MENU_SCALE_TILES, Menu.NONE, "Scale Tiles").setCheckable(true);

        MENU_LAST_ID++;
        MENU_ROTATE_CLOCKWISE = MENU_LAST_ID;
        menu.add(0, MENU_ROTATE_CLOCKWISE, Menu.NONE, "Rotate Clockwise");

        MENU_LAST_ID++;
        MENU_ROTATE_COUNTER_CLOCKWISE = MENU_LAST_ID;
        menu.add(0, MENU_ROTATE_COUNTER_CLOCKWISE, Menu.NONE, "Rotate Counter Clockwise");
        // Put overlay items first
        try {
            mMapView.getOverlayManager().onCreateOptionsMenu(menu, MENU_LAST_ID, mMapView);
        } catch (NullPointerException npe) {
            //can happen during CI tests and very rapid fragment switching
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        try {
            MenuItem item = menu.findItem(MENU_VERTICAL_REPLICATION);
            item.setChecked(mMapView.isVerticalMapRepetitionEnabled());
            item = menu.findItem(MENU_HORIZTONAL_REPLICATION);
            item.setChecked(mMapView.isHorizontalMapRepetitionEnabled());

            item = menu.findItem(MENU_SCALE_TILES);
            item.setChecked(mMapView.isTilesScaledToDpi());
            mMapView.getOverlayManager().onPrepareOptionsMenu(menu, MENU_LAST_ID, mMapView);
        } catch (NullPointerException npe) {
            //can happen during CI tests and very rapid fragment switching
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Run Tests")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runTestProcedures();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        } else if (item.getItemId() == MENU_HORIZTONAL_REPLICATION) {
            mMapView.setHorizontalMapRepetitionEnabled(!mMapView.isHorizontalMapRepetitionEnabled());
            mMapView.invalidate();
            return true;
        } else if (item.getItemId() == MENU_VERTICAL_REPLICATION) {
            mMapView.setVerticalMapRepetitionEnabled(!mMapView.isVerticalMapRepetitionEnabled());
            mMapView.invalidate();
            return true;
        } else if (item.getItemId() == MENU_SCALE_TILES) {
            mMapView.setTilesScaledToDpi(!mMapView.isTilesScaledToDpi());
            mMapView.invalidate();
            return true;
        } else if (item.getItemId() == MENU_ROTATE_CLOCKWISE) {
            float currentRotation = mMapView.getMapOrientation() + 10;
            if (currentRotation > 360)
                currentRotation = currentRotation - 360;
            mMapView.setMapOrientation(currentRotation, true);

            return true;
        } else if (item.getItemId() == MENU_ROTATE_COUNTER_CLOCKWISE) {
            float currentRotation = mMapView.getMapOrientation() - 10;
            if (currentRotation < 0)
                currentRotation = currentRotation + 360;
            mMapView.setMapOrientation(currentRotation, true);
            return true;
        } else if (mMapView.getOverlayManager().onOptionsItemSelected(item, MENU_LAST_ID, mMapView)) {
            return true;
        }
        return false;
    }

    protected void addOverlays() {
        //
    }

    public void runTestProcedures() throws Exception {

    }

    //LOCATION PERMISSION위해 CALLPERMISSION도 필요하다.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }
    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }


    @Override
    public void onClick(View v) {
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                newGps = new GpsInfo(getActivity());
                // GPS 사용유무 가져오기
                if (newGps.isGetLocation()) {

                    double latitude = newGps.getLatitude();
                    double longitude = newGps.getLongitude();

                    Toast.makeText(
                            getActivity(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    newGps.showSettingsAlert();
                }
            }

}

