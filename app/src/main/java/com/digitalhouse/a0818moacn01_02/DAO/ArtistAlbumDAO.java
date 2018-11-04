package com.digitalhouse.a0818moacn01_02.DAO;

import android.util.Log;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorAlbum;
import com.digitalhouse.a0818moacn01_02.model.Container.ContenedorArtists;
import com.digitalhouse.a0818moacn01_02.service.ServiceArtistAlbum;
import com.digitalhouse.a0818moacn01_02.service.ServiceArtistGenre;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistAlbumDAO extends DaoHelper{
    private ServiceArtistAlbum serviceArtistAlbum;

    public ArtistAlbumDAO() {
        super();
        serviceArtistAlbum = retrofit.create(ServiceArtistAlbum.class);
    }


    public void getArtistAlbum(final ResultListener<List<AlbumDeezer>> listenerDelController, Integer artistId){
        Call<ContenedorAlbum> call = serviceArtistAlbum.getArtistAlbum(artistId);


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
