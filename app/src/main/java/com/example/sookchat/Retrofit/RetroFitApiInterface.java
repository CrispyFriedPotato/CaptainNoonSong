package com.example.sookchat.Retrofit;


import com.example.sookchat.Data.Comment;
import com.example.sookchat.Data.Data;
import com.example.sookchat.Data.ImageItem;
import com.example.sookchat.MapItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetroFitApiInterface {

    @GET("getmapitem.php")
    Call<List<MapItem>> getMapItem(@Query("title") String title);

    @GET("getlist.php")
    Call<List<Data>> getData();

    @GET("getimage.php")
    Call<List<ImageItem>> getImage(@Query("catid") int catid);

    @GET("getcomment.php")
    Call<List<Comment>> getComment(@Query("imageid") int imageid);


    @FormUrlEncoded
    @POST("postcomment.php")
    Call<Comment> postComment(@Field("imageid") int imageid, @Field("comment") String comment);


}
