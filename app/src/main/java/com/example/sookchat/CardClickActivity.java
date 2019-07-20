package com.example.sookchat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.request.transition.Transition;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardClickActivity extends AppCompatActivity implements ViewAdapter.OnCardListener {


    private static final String TAG = "CardClickActivity";
    private List<ImageItem> imageList = new ArrayList<ImageItem>();
    private ViewAdapter adapter;
    private RecyclerView recyclerView;
    public static Context ccContext;
    int catid;


    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate: called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardclick);
        Intent intent = getIntent();
        catid = intent.getIntExtra("catid", 0);

        recyclerView = (RecyclerView) findViewById(R.id.view_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);



        adapter = new ViewAdapter(ccContext,imageList);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        getDataList(catid);










    }





    public void onStart() {
        Log.e(TAG,"onStart: called.");
        super.onStart();
    }

    public void onResume() {
        Log.e(TAG,"onResume: called.");
        super.onResume();
    }




    public void getDataList(int catid) {
        Log.e(TAG,"getDataList: called.");
        RetroFitApiInterface apiInterface = RetroFitApiClient.getClient().create(RetroFitApiInterface.class);
        Call<List<ImageItem>> call = apiInterface.getImage(catid);
        call.enqueue(new Callback<List<ImageItem>>() {
            @Override
            public void onResponse(Call<List<ImageItem>> call, Response<List<ImageItem>> response) {
                if (response == null) {
                    Toast.makeText(CardClickActivity.this, "Somthing Went Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    for (ImageItem imageitem : response.body()) {
                        imageList.add(imageitem);
                    }
                    Log.e(TAG,"getDataList_onResponse: " + response.body());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ImageItem>> call, Throwable t) {
                Toast.makeText(CardClickActivity.this, "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    @Override
    public void onCardClick(int position) {
        Log.e(TAG,"onCardClick: called.");
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("imageid", imageList.get(position).getImageid());
        startActivity(intent);
    }

    public void onCardDelete(){
        finish();
    }

    public void onClickLike(){
        Toast.makeText(ccContext, "LIKE!", Toast.LENGTH_LONG).show();
    }
}