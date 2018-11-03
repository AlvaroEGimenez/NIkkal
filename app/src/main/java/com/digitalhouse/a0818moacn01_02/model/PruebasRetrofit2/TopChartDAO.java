package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopChartDAO extends DaoHelper{
    private ServiceTopChart serviceTopChart;


    public static final String base_url = "https://api.deezer.com/";


    public TopChartDAO() {
        super(base_url);
        serviceTopChart = retrofit.create(ServiceTopChart.class);
    }


    public void getTracks(final ResultListener<List<Track>> listenerDelController){
        Call<ContenedorTracks> call = serviceTopChart.getTopChart();

        call.enqueue(new Callback<ContenedorTracks>() {
            @Override
            public void onResponse(Call<ContenedorTracks> call, Response<ContenedorTracks> response) {

                ContenedorTracks contenedorTracks = response.body();

                List<Track> tracksList = contenedorTracks.getTracks().getTrackList();

                listenerDelController.finish(tracksList);
            }

            @Override
            public void onFailure(Call<ContenedorTracks> call, Throwable t) {
                Log.e("ERROR",t.toString());
            }
        });
    }
}
