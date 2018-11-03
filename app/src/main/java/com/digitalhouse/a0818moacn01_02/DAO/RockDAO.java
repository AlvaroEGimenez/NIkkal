package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRock;
import com.digitalhouse.a0818moacn01_02.service.ServiceArtistasRock;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RockDAO extends DaoHelper {
    private ServiceArtistasRock serviceArtistasRock;


    public static final String base_url = "https://api.deezer.com/";


    public RockDAO() {
        super(base_url);
        serviceArtistasRock = retrofit.create(ServiceArtistasRock.class);
    }


    public void getTracks(final ResultListener<List<ArtistDeezer>> listenerDelController){
        Call<ContenedorRock> call = serviceArtistasRock.getTopChart();

        call.enqueue(new Callback<ContenedorRock>() {
            @Override
            public void onResponse(Call<ContenedorRock> call, Response<ContenedorRock> response) {

                ContenedorRock contenedorRock = response.body();

                List<ArtistDeezer> tracksList = contenedorRock.getArtistRock();

                listenerDelController.finish(tracksList);
            }

            @Override
            public void onFailure(Call<ContenedorRock> call, Throwable t) {
                Log.e("ERROR",t.toString());
            }
        });
    }
}
