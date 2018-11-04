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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.ArtistAlbumControler;
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.AlbumAdapterRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment implements AlbumAdapterRecyclerView.AlbumAdapterInterface {
    public static final String KEY_IMAGEN_ARTISTA = "imagenArtista";
    public static final String KEY_NOMBRE_ARTISTA = "nombreArtista";
    public static final String KEY_ID_ARTISTA = "idArtista";

    private ImageView imagenArtista;
    private Toolbar tvCabeceraArtista;
    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
    private Integer idArtist;
    private List<AlbumDeezer> albumDeezerList = new ArrayList<>();
    private View view;
    private ProgressBar pbAlbum;
    private LinearLayout conatiner;

    public AlbumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        this.view = view;
        conatiner = view.findViewById(R.id.categoriaAlbumContainer);
        pbAlbum = view.findViewById(R.id.pbAlbum);

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
                crearAlbumRecyclerView(view, R.id.rvAlbum);
                pbAlbum.setVisibility(View.GONE);
                conatiner.setVisibility(View.VISIBLE);

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

        AlbumAdapterRecyclerView albumAdapterRecyclerView = new AlbumAdapterRecyclerView(albumDeezerList, R.layout.cardview_album, getActivity(), this);

        recyclerView.setAdapter(albumAdapterRecyclerView);
    }


    @Override
    public void cambiarDeActividad(AlbumDeezer album) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, album.getCoverMedium());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, album.getTitle());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, album.getId());
        pistaAlbumFragment.setArguments(bundle);
        mainActivity.reemplazarFragment(pistaAlbumFragment);
    }
}
