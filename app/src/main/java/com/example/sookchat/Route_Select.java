package com.example.sookchat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.example.sookchat.Main.MainActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.GraphHopperRoadManager;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;


public class Route_Select extends Fragment {


    private static int MENU_LAST_ID = Menu.FIRST; // Always set to last unused id
    public static final String TAG = "osmBaseFrag";

    //public abstract String getSampleTitle();

    public Route_Select() {
        // Required empty public constructor
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    protected MapView mMapView;

    public MapView getmMapView() {
        return mMapView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Disable StrictMode.ThreadPolicy to perform network calls in the UI thread.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
        int MENU_VERTICAL_REPLICATION = 0;
        int MENU_HORIZTONAL_REPLICATION = 0;
        int MENU_ROTATE_CLOCKWISE = 0;
        int MENU_ROTATE_COUNTER_CLOCKWISE = 0;
        int MENU_SCALE_TILES = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        View v = inflater.inflate(R.layout.fragment_route_select, container, false);
        mMapView = v.findViewById(R.id.route_select_map);
        Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        mMapView.setMultiTouchControls(true);


        IMapController mapController = mMapView.getController();
        mapController.setZoom(17.5);
        String provider = LocationManager.NETWORK_PROVIDER;


        //Setting start and end points
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        GeoPoint endPoint = new GeoPoint(37.545724, 126.964219);
        waypoints.add(endPoint);
        //마커 추가 코드
//        Marker startMarker = new Marker(mMapView);
        Marker endMarker = new Marker(mMapView);
//
//        startMarker.setPosition(startPoint);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        mMapView.getOverlays().add(startMarker);

        endMarker.setPosition(endPoint);
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMapView.getOverlays().add(endMarker);
        //map refresh
        mMapView.invalidate();
        //마커 클릭시 생기는 버블 안의 text
        //startMarker.setIcon(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_launcher));
        //startMarker.setTitle("Start point");

        //toolbar(from fragment to fragment)
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(4);
            }
        });


        //"Hello, Routing World!", polyline을 위에서 설정한 startpoint에서
        //아래 코드에서 설정한 endpoint까지 그림
        //RoadManager roadManager = new OSRMRoadManager(this.getActivity());
        //"Playing with the Roadmanager"
        //RoadManager roadManager = new MapQuestRoadManager("6gMYR55drKQdJM49DByIETG2JCJk4kf1");
        GraphHopperRoadManager roadManager = new GraphHopperRoadManager("6gMYR55drKQdJM49DByIETG2JCJk4kf1", true);
        //RoadManager roadManager = new OSRMRoadManager(this.getActivity());
        roadManager.addRequestOption("vehicle=foot");
        Road road = roadManager.getRoad(waypoints);


        //경로마다 점 찍어 주기
        Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
        for (int i = 0; i < road.mNodes.size(); i++) {
            RoadNode node = road.mNodes.get(i);
            Marker nodeMarker = new Marker(mMapView);
            nodeMarker.setPosition(node.mLocation);
            nodeMarker.setIcon(nodeIcon);
            nodeMarker.setSubDescription(Road.getLengthDurationText(this.getActivity(), node.mLength, node.mDuration));
            Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
            nodeMarker.setImage(icon);
            //nodeMarker.setTitle("Step "+i);
            mMapView.getOverlays().add(nodeMarker);
        }

        Polyline roadOverlay = GraphHopperRoadManager.buildRoadOverlay(road);
        mMapView.getOverlays().add(roadOverlay);
        mMapView.invalidate();

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Location location = lm.getLastKnownLocation(provider);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            //Departure setting
            GeoPoint startPoint = new GeoPoint(latitude,longitude);
            mapController.setCenter(startPoint);
            waypoints.add(startPoint);

            Marker startMarker = new Marker(mMapView);
            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mMapView.getOverlays().add(startMarker);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }
        return v;
    }

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

        /**
         * An appropriate place to override and add overlays.
         */
        protected void addOverlays() {
            //
        }

        public boolean skipOnCiTests() {
            return true;
        }

        /**
         * optional place to put automated test procedures, used during the connectCheck tests
         * this is called OFF of the UI thread. block this method call util the test is done
         */
        public void runTestProcedures() throws Exception {

        }
}

