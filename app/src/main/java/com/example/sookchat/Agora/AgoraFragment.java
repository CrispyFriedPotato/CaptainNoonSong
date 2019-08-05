package com.example.sookchat.Agora;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sookchat.Data.Data;
import com.example.sookchat.R;
import com.example.sookchat.Retrofit.RetroFitApiClient;
import com.example.sookchat.Retrofit.RetroFitApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgoraFragment extends Fragment {

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private RecyclerView recycler_view;
    private AgoraAdapter myAdapter;
    private List<Data> dataList = new ArrayList<Data>();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_agora,container,false);


        //toolbar
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(dataList.size()!=0){
            dataList.clear();
        }

        sortview(view);

        recycler_view=(RecyclerView)view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(getActivity(),2);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        //getDataList();

        return view;

    }


    public void onStart(){
        dataList.clear();
        getDataList();
        myAdapter = new AgoraAdapter(getActivity(),dataList);
        recycler_view.setAdapter(myAdapter);
        super.onStart();
    }

    public void onResume(){
        super.onResume();
    }



    public void getDataList() {
        RetroFitApiInterface apiInterface = RetroFitApiClient.getClient().create(RetroFitApiInterface.class);
        Call<List<Data>> call = apiInterface.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (response==null){
                    Toast.makeText(getActivity(), "Somthing Went Wrong!", Toast.LENGTH_SHORT).show();
                }else{

                    for (Data data:response.body()){
                        dataList.add(data);
                    }

                    myAdapter = new AgoraAdapter(getActivity(),dataList);
                    recycler_view.setAdapter(myAdapter);

                    Log.i("RESPONSE: ", ""+response.toString());
                }

            }
            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }



    public void sortview(View view){
        ((TextView)view.findViewById(R.id.sortbyid))
                .setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Collections.sort(dataList, Data.ById);
                        myAdapter.notifyDataSetChanged();
                    }
                });

        ((TextView)view.findViewById(R.id.sortbyviewer))
                .setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Collections.sort(dataList, Data.ByViewers);
                        myAdapter.notifyDataSetChanged();
                    }
                });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    myAdapter.getFilter().filter(newText);
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }



}