package com.example.sookchat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{
    //Map Recycler View
    private RecyclerView recyclerView;
    private ArrayList<MapCardFragment> imageModelArrayList;
    private MapCardAdapter adapter;
    private GoogleMap googleMap;

    private Marker mSook;
    private Marker mMyeong;
    private Marker mSae;
    private Marker mZin;
    private Marker mSun;
    private Marker mStudent;
    private Marker mHang;
    private Marker mSureon;
    private Marker mHangpa;
    private Marker mInjae;
    private Marker mScience;
    private Marker mLibrary;
    private Marker mArt;
    private Marker mMusic;
    private Marker mPharmacy;
    private Marker mHundred;
    private Marker mSociology;


    private int[] myImageList = new int[]{R.drawable.school,R.drawable.newmyeong, R.drawable.myeong,
            R.drawable.law,R.drawable.sun,R.drawable.student,R.drawable.hang,R.drawable.su,R.drawable.noon,R.drawable.injae,
            R.drawable.science,R.drawable.library,R.drawable.sociology,R.drawable.art,
            R.drawable.medicine,R.drawable.millenium,R.drawable.music,};
    private String[] myImageNameList = new String[]{"숙명여자대학교","새힘관","명신관","진리관","순헌관",
            "학생회관","행정관","수련관","행파관","인재관",
            "이과대학","중앙도서관","사회교육관","미술대학","약학대학","백주년기념관","음악대학"};

    View rootView;
    MapView mapView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ArrayList<MapCardFragment> listBuilding(){

        ArrayList<MapCardFragment> list = new ArrayList<>();
        //카드 갯수 여기서 바꾸기
        for(int i = 0; i < 17; i++){
            MapCardFragment building = new MapCardFragment();
            building.setName(myImageNameList[i]);
            building.setImage_drawable(myImageList[i]);
            list.add(building);
        }
        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        //In fragment, by Mapview, we will excute a map

        mapView = rootView.findViewById(R.id.Map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//비동기적 방식으로 구글 맵 실행



        recyclerView = rootView.findViewById(R.id.recycler_view);
        imageModelArrayList = listBuilding();
        adapter = new MapCardAdapter(getActivity(), imageModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        return rootView;
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        //MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.544740, 126.964445),17 );

        googleMap.animateCamera(cameraUpdate);
        googleMap.setOnMarkerClickListener(this);
        //숙명여자대학교 좌표 37.545945, 126.964686
        mSook = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545945, 126.964686))
                .title("숙명여자대학교")
                .snippet("자랑스러운 113년의 역사"));
        mSook.setTag("숙명여자대학교");
        //명신관 37.545709, 126.963640
        mMyeong=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545709, 126.963640))
                .title("명신관")
                .snippet("명신 닭장아리 불리지요!")
        );
        mMyeong.setTag("명신관");
        //새힘관 37.545437, 126.963882
        mSae=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545437, 126.963882))
                .title("새힘관")
                .snippet("명신관과 이어져 있어요!")
        );
        mSae.setTag("새힘관");
        //순헌관 37.546432, 126.964670
        mSun=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.546432, 126.964670))
                .title("순헌관")
                .snippet("멋진 여성 동상이 입구를 지키고 있어요!")
        );
        mSun.setTag("순헌관");
        //학생회관 37.545422, 126.965056
        mStudent=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545422, 126.965056))
                .title("학생회관")
                .snippet("동아리실이 있어요!우체국도?!")
        );
        mStudent.setTag("학생회관");
        //행정관 37.545403, 126.964536
        mHang=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545403, 126.964536))
                .title("행정관")
                .snippet("지름길로 사용되죠*^^*")
        );
        mHang.setTag("행정관");
        //진리관37.546408, 126.963901
        mZin=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.546408, 126.963901))
                .title("진리관")
                .snippet("법 도서관이 었어요!")
        );
        mZin.setTag("진리관");
        //수련교수관37.546652, 126.964367
        mSureon=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.546652, 126.964367))
                .title("수련관")
                .snippet("진리관과 순헌관 사잇길을 이용해보자!")
        );
        mSureon.setTag("수련관");
        //행파교수 37.546626, 126.965042
        mHangpa=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.546626, 126.965042))
                .title("행파관")
                .snippet("수련관과 연결되어 있어요!")
        );
        mHangpa.setTag("행파관");
        //인재관 37.546357, 126.967794
        mInjae=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.546357, 126.967794))
                .title("인재관")
                .snippet("무용과 수업을 들으러 가볼까?")
        );
        mInjae.setTag("인재관");
        //이과대학 37.544601, 126.966349
        mScience=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.545709, 126.963640))
                .title("이과대학")
                .snippet("냐옹이가 이 주변에 출몰한다구!")
        );
        mScience.setTag("이과대학");
        //중도 37.544182, 126.965869
        mLibrary=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.544189, 126.965924))
                .title("중앙 도서관")
                .snippet("열공 하는 송이들이 많아요!")
        );
        mLibrary.setTag("중앙도서관");
        //사회 교육관 37.543863, 126.964083
        mSociology=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.543863, 126.964083))
                .title("사회 교육관")
                .snippet("공부하는 당신! 멋져요!")
        );
        mSociology.setTag("사회교육관");
        //미대 37.544327, 126.965081
        mArt=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.544327, 126.965081))
                .title("미술대학")
                .snippet("야작 송이들! 힘내요!")
        );
        mArt.setTag("미술대학");
        //약대 37.543846, 126.964695
        mPharmacy=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.543846, 126.964695))
                .title("약학대학")
                .snippet("과잠에 박힌 \"약\" 짱!")
        );
        mPharmacy.setTag("약학대학");
        //백주년 기념 37.543823, 126.965374
        mHundred=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.543823, 126.965374))
                .title("백주년 기념관")
                .snippet("이쁜 언덕을 오르면 나와요!")
        );
        mHundred.setTag("백주년기념관");
        //음악 대학 37.544264, 126.964063
        mMusic=googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.544264, 126.964063))
                .title("음악 대학")
                .snippet("좋은 음악 소리~")
        );
        mMusic.setTag("음악대학");

    }

    //Marker Click Listner
    //Marker Click Listener
    public boolean onMarkerClick(Marker marker) {

        recyclerView = rootView.findViewById(R.id.recycler_view);
        //CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        //googleMap.animateCamera(center);

        String building;
        building = (String) marker.getTag();
        int temp = 0;
        int i;
        for (i = 0; i < myImageNameList.length; i++) {
            if (myImageNameList[i].equals(building)) {
                temp = i;
                break;
            }
        }
        //smoothly scroll
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
            }
       /* @Override protected int getVerticalSnapPreference() {
           return LinearSmoothScroller.SNAP_TO_START;
       }*/
        };
        smoothScroller.setTargetPosition(temp);
        ((LinearLayoutManager) recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);

        return true;
    }



    }


