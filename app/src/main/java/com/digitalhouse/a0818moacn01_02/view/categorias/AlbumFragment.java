package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.ArtistAlbumControler;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AlbumAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.AlbumAdapterRecyclerView2;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment implements AlbumAdapterRecyclerView.AlbumAdapterInterface {
    public static final String KEY_IMAGEN_ARTISTA = "imagenArtista";
    public static final String KEY_NOMBRE_ARTISTA = "nombreArtista";
    public static final String KEY_ID_ARTISTA = "idArtista";

    private ImageView imagenArtista;
    private Toolbar tvCabeceraArtista;
    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
    private List<ArtistDeezer> artistDeezersRock = new ArrayList<>();
    private Integer idArtist;
    private List<AlbumDeezer> albumDeezerList = new ArrayList<>();
    private View view;

    public AlbumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        this.view = view;

        imagenArtista = view.findViewById(R.id.imagenArtista);
        tvCabeceraArtista = view.findViewById(R.id.tvCabeceraArtista);
        view.findViewById(R.id.rvAlbum);

        Bundle bundle = getArguments();
        String urlImagenCabecera = bundle.getString(KEY_IMAGEN_ARTISTA);
        String nombreGenero = bundle.getString(KEY_NOMBRE_ARTISTA);
        idArtist = bundle.getInt(KEY_ID_ARTISTA);

        cargarArtistAlbum();
        cargarImagen(imagenArtista, urlImagenCabecera);
        tvCabeceraArtista.setTitle(nombreGenero);


        return view;
    }

    private void cargarArtistAlbum() {
        ArtistAlbumControler artistAlbumControler = new ArtistAlbumControler();
        artistAlbumControler.getArtistAlbum(new ResultListener<List<AlbumDeezer>>() {
            @Override
            public void finish(List<AlbumDeezer> resultado) {
                albumDeezerList = resultado;
                crearAlbumRecyclerView(view,R.id.rvAlbum);

            }
        }, getContext(), idArtist);
    }


    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(View view, Integer idLayout) {
        RecyclerView recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView2 albumAdapterRecyclerView2 = new AlbumAdapterRecyclerView2(albumDeezerList, R.layout.cardview_album, getActivity());

        recyclerView.setAdapter(albumAdapterRecyclerView2);
    }


    @Override
    public void cambiarDeActividad(ArtistDeezer album) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, album.getPictureMedium());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, album.getName());
        pistaAlbumFragment.setArguments(bundle);
        mainActivity.reemplazarFragment(pistaAlbumFragment);
    }
}
