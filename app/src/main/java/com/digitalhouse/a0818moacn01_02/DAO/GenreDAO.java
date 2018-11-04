package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorGenre;
import com.digitalhouse.a0818moacn01_02.model.Genre;
import com.digitalhouse.a0818moacn01_02.service.ServiceGenre;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreDAO extends DaoHelper {
    private ServiceGenre serviceGenre;

    public GenreDAO() {
        super();
        serviceGenre = retrofit.create(ServiceGenre.class);
    }


    public void getGenre(final ResultListener<List<Genre>> listenerDelController) {

        Call<ContenedorGenre> call = serviceGenre.getTopChart();

        call.enqueue(new Callback<ContenedorGenre>() {
            @Override
            public void onResponse(Call<ContenedorGenre> call, Response<ContenedorGenre> response) {

                ContenedorGenre contenedorGenre = response.body();

                List<Genre> genreList = contenedorGenre.getListaGeneros();

                listenerDelController.finish(genreList);
            }

            @Override
            public void onFailure(Call<ContenedorGenre> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

    public void getArtist(final ResultListener<List<ArtistDeezer>> listenerDelController, Integer genreId) {
        Call<ContenedorArtists> call = serviceGenre.getGenre(genreId);

        call.enqueue(new Callback<ContenedorArtists>() {
            @Override
            public void onResponse(Call<ContenedorArtists> call, Response<ContenedorArtists> response) {

                ContenedorArtists contenedorArtists = response.body();

                List<ArtistDeezer> tracksList = contenedorArtists.getArtistDeezers();

                listenerDelController.finish(tracksList);
            }

            @Override
            public void onFailure(Call<ContenedorArtists> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

    public void getArtistAlbum(final ResultListener<List<AlbumDeezer>> listenerDelController, Integer artistId){
        Call<ContenedorAlbum> call = serviceGenre.getArtistAlbum(artistId);


        call.enqueue(new Callback<ContenedorAlbum>() {
            @Override
            public void onResponse(Call<ContenedorAlbum> call, Response<ContenedorAlbum> response) {

                ContenedorAlbum contenedorAlbum = response.body();

                List<AlbumDeezer> albumList = contenedorAlbum.getAlbumDeezerList();

                listenerDelController.finish(albumList);
            }

            @Override
            public void onFailure(Call<ContenedorAlbum> call, Throwable t) {
                Log.e("ERROR",t.toString());
            }
        });
    }


}
