package com.example.sookchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sookchat.RetroFitApiClient.MAP_DIR;

public class MapBuilding extends AppCompatActivity {

    private List<MapItem> mapItemList = new ArrayList<MapItem>();

    private int[] myImageList = new int[]{R.drawable.school,R.drawable.newmyeong, R.drawable.myeong,
            R.drawable.law,R.drawable.sun,R.drawable.student,R.drawable.hang,R.drawable.su,R.drawable.noon,R.drawable.injae,
            R.drawable.science,R.drawable.library,R.drawable.sociology,R.drawable.art,
            R.drawable.medicine,R.drawable.millenium,R.drawable.music,};
    private String[] myImageNameList = new String[]{"숙명여자대학교","새힘관","명신관","진리관","순헌관",
            "학생회관","행정관","수련관","행파관","인재관",
            "이과대학","중앙도서관","사회교육관","미술대학","약학대학","백주년기념관","음악대학"};

    String title;
    ImageView image;
    TextView name;
    TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_building);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        image = (ImageView)findViewById(R.id.imageview_header);
        name = (TextView)findViewById(R.id.textview_title);
        txtInfo = (TextView)findViewById(R.id.textview);

        getMapList(title);

    }

    public void getMapList(String title) {
        RetroFitApiInterface apiInterface = RetroFitApiClient.getClient().create(RetroFitApiInterface.class);
        Call<List<MapItem>> call = apiInterface.getMapItem(title);
        call.enqueue(new Callback<List<MapItem>>() {
            @Override
            public void onResponse(Call<List<MapItem>> call, Response<List<MapItem>> response) {
                if (response == null) {
                    Toast.makeText(MapBuilding.this, "Somthing Went Wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    for (MapItem mapitem : response.body()) {
                        mapItemList.add(mapitem);

                        Glide.with(MapBuilding.this).clear(image);
                        name.setText(mapitem.getTitle());
                        txtInfo.setText(mapitem.getDescription());
                        Glide.with(MapBuilding.this)
                                .load( MAP_DIR + mapitem.getFilename() + ".jpg")
                                .apply(RequestOptions.circleCropTransform()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                                .centerCrop()
                                .into(image);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<MapItem>> call, Throwable t) {
                Toast.makeText(MapBuilding.this, "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }



}

