package com.digitalhouse.a0818moacn01_02.model.PruebasRetrofit2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("chart")
    Call<ModeloRespuesta> getTopChart();
}
