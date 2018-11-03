package com.digitalhouse.a0818moacn01_02.DAO;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class DaoHelper {

    protected Retrofit retrofit;

    public static final String KEY_BASE_URL = "https://api.deezer.com/";

    public DaoHelper() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(KEY_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
    }

}
