package com.example.sookchat;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sookchat.Agora.AgoraAdapter;
import com.example.sookchat.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class Route_Select_list extends ListFragment {
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private RecyclerView recycler_view;

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



        // Inflate the layout for this fragment
        return v;
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
