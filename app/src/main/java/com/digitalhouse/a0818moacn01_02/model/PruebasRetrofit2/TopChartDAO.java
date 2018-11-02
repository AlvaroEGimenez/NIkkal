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


    public void getTopChart(final ResultListener<List<Track>> listenerDelController){
        Call<ModeloRespuesta> call = serviceTopChart.getTopChart();
        call.enqueue(new Callback<ModeloRespuesta>() {
            @Override
            public void onResponse(Call<ModeloRespuesta> call, Response<ModeloRespuesta> response) {

                ModeloRespuesta modeloRespuesta = response.body();

                List<Track> tracks = modeloRespuesta.getTrackList();

                listenerDelController.finish(tracks);
            }

            @Override
            public void onFailure(Call<ModeloRespuesta> call, Throwable t) {
                Log.e("ERROR",t.toString());
            }
        });
    }
}
