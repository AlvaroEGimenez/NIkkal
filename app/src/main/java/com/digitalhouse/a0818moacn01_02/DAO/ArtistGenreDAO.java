package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorGenre;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorRock;
import com.digitalhouse.a0818moacn01_02.service.ServiceArtistGenre;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistGenreDAO extends DaoHelper {
    private ServiceArtistGenre serviceArtistGenre;




    public ArtistGenreDAO() {
        super();
        serviceArtistGenre = retrofit.create(ServiceArtistGenre.class);
    }


    public void getArtist(final ResultListener<List<ArtistDeezer>> listenerDelController,Integer genreId){
        Call<ContenedorArtists> call = serviceArtistGenre.getGenre(genreId);

        call.enqueue(new Callback<ContenedorArtists>() {
            @Override
            public void onResponse(Call<ContenedorArtists> call, Response<ContenedorArtists> response) {

                ContenedorArtists contenedorArtists = response.body();

                List<ArtistDeezer> tracksList = contenedorArtists.getArtistDeezers();

                listenerDelController.finish(tracksList);
            }

            @Override
            public void onFailure(Call<ContenedorArtists> call, Throwable t) {
                Log.e("ERROR",t.toString());
            }
        });
    }
}
