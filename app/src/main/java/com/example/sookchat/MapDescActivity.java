package com.example.sookchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sookchat.Retrofit.RetroFitApiClient;
import com.example.sookchat.Retrofit.RetroFitApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDescActivity extends AppCompatActivity implements MapDescAdapter.OnCardListener {

    private  String TAG = "MapDescActivity" ;
    private List<MapItem> descList = new ArrayList<MapItem>();
    private MapDescAdapter mAdapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,": called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_desc);


        Bundle extras = getIntent().getExtras();
        int tourid = extras.getInt("tourid");
        Log.d(TAG, "TourId :" + tourid);


        recyclerView = findViewById(R.id.desc_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        mAdapter = new MapDescAdapter(this, descList);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setLayoutManager(layoutManager);


        mAdapter.setOnClickListener(this);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        getDataList(tourid);


    }

    public void onCardDelete(){
        finish();
    }

    public void getDataList(int tourid) {
        Log.e(TAG,"getDataList: called.");
        RetroFitApiInterface apiInterface = RetroFitApiClient.getClient().create(RetroFitApiInterface.class);
        Call<List<MapItem>> call = apiInterface.getMapDesc(tourid);
        call.enqueue(new Callback<List<MapItem>>() {
            @Override
            public void onResponse(Call<List<MapItem>> call, Response<List<MapItem>> response) {
                if (response == null) {
                    Toast.makeText(MapDescActivity.this, "Somthing Went Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    for (MapItem mapitem : response.body()) {
                        descList.add(mapitem);
                    }
                    Log.e(TAG,"getDataList_onResponse: " + response.body());

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MapItem>> call, Throwable t) {
                Toast.makeText(MapDescActivity.this, "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }




}