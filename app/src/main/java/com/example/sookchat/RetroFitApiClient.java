package com.example.sookchat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitApiClient {

    public static final String BASE_URL = "http://chatbot2019.cafe24.com/";
    public static final String IMAGE_DIR = BASE_URL + "images/";
    public static final String MAP_DIR = BASE_URL + "mapimage/";


    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create()).build();
        }
        return retrofit;
    }
}
