package com.example.sookchat;


import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardClickActivity extends AppCompatActivity {
        private ImageButton btnClose;
        private Button btnLike;
        private Button btnComment;
        private List<ImageItem> imageList = new ArrayList<ImageItem>();
        private ViewAdapter adapter;
        public static Context ccContext;
        Data data;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cardclick);
            ccContext=this;
            Intent intent = getIntent();
            int catid = intent.getIntExtra("catid", 0);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.view_recycler);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);



            adapter = new ViewAdapter(this,imageList);
            recyclerView.setAdapter(adapter);

            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            getDataList(catid);

            btnClose= findViewById(R.id.btn_close);
            btnLike=findViewById(R.id.btn_like);
            btnComment=findViewById(R.id.btn_comment);

            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CardClickActivity.this, "Like!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CardClickActivity.this, "Like!", Toast.LENGTH_SHORT).show();
                }
            });



        }


    @Override
    public void onBackPressed(){

        super.onBackPressed();
    }


    public void getDataList(int catid) {
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
                    Log.i("RESPONSE: ", "" + response.toString());

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

}