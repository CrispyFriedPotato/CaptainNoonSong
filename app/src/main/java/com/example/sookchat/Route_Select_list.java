package com.example.sookchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sookchat.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class Route_Select_list extends ListFragment {

    private List<String> list;
    private ListView listview = null;
    private EditText search;
    private RouteBuildingAdapter adapter;
    private ArrayList<String> _buildings=null;
    Intent mResult;

    private OnFragmentInteractionListener mListener;

    public Route_Select_list() {
        // Required empty public constructor
    }
    public static Route_Select_list newInstance(){
        return new Route_Select_list();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_route_select_list,container,false);

        ImageView iv = v.findViewById(R.id.backarrow);
        iv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(5);
            }
        });

//        사용자 위치 받아오는 버튼, ImageButton이었으나 ImageView로 바
        ImageView btnNewLocation;
        btnNewLocation = v.findViewById(R.id.locationReset);
        btnNewLocation.setFocusable(false);
        btnNewLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).replaceFragment(5);
            }
        });

        search=v.findViewById(R.id.editTextFilter);

        listview = v.findViewById(android.R.id.list) ;

        list = new ArrayList<>();

        list.add("명신관");
        list.add("새힘관");
        list.add("진리관");
        list.add("순헌관");
        list.add("행파관");
        list.add("교수 수련회관");
        list.add("학생회관");
        list.add("행정관");
        list.add("프라임관");
        list.add("음악대학");
        list.add("미술대학");
        list.add("사회대학");
        list.add("백주년 기념관");
        list.add("중앙 도서관");
        list.add("과학관");


//        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        adapter = new RouteBuildingAdapter(list,getActivity()) ;
        listview.setAdapter(adapter);

        _buildings = new ArrayList<String>();
        _buildings.addAll(list);



//        final EditText location = v.findViewById(R.id.editTextFilter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override public void onItemClick(AdapterView<?> parent, View view,int position, long id)
//            {
////                location.setText("명신관");
//                Log.i("ONCLICK","YOU clicked");
//                Toast.makeText(getActivity(), "Stop Clicking me", Toast.LENGTH_SHORT).show();
//            }
//        });

//        listview.setFocusable(false);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Log.v(TAG,"리스너 안에는 들어오긴 하네 ");
//                Toast.makeText(getActivity(), "Stop Clicking me", Toast.LENGTH_SHORT).show();
////
//            }
//        }) ;

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String text = search.getText().toString();
                search(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;





        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        //btnNewLocation.setOnClickListener();

        return v;
    }



    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(_buildings);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < _buildings.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (_buildings.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(_buildings.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}