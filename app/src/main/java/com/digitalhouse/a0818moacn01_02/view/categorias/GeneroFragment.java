package com.digitalhouse.a0818moacn01_02.view.categorias;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.digitalhouse.a0818moacn01_02.controller.ArtistController;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.ArtistaAdapterRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal.CategoriaFragment;

import java.util.ArrayList;
import java.util.List;

public class GeneroFragment extends Fragment implements ArtistaAdapterRecyclerView.ArtistaAdapterInterface {
    public static final String KEY_IMAGEN_GENERO = "imagenGenero";
    public static final String KEY_NOMBRE_GENERO = "nombreGenero";
    public static final String KEY_ID_GENERO = "idGenero";

    private List<ArtistDeezer> artistGenre = new ArrayList<>();
    private Integer idGenre;
    private View view;
    private ProgressBar pbCategoria;
    private LinearLayout conatiner;

    private AlbumFragment albumFragment = new AlbumFragment();

    public GeneroFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_genero, container, false);
        this.view = view;
        conatiner = view.findViewById(R.id.generoContainer);
        pbCategoria = view.findViewById(R.id.pbGenero);

        ImageView imgGenero = view.findViewById(R.id.imgGenero);
        Toolbar tvCabeceraGenero = view.findViewById(R.id.tvCabeceraGenero);

        tvCabeceraGenero.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        tvCabeceraGenero.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaFragment categoriaFragment = new CategoriaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, categoriaFragment);
                fragmentTransaction.commit();
            }
        });


        Bundle bundle = getArguments();
        String urlImagenCabecera = bundle.getString(KEY_IMAGEN_GENERO);
        String nombreGenero = bundle.getString(KEY_NOMBRE_GENERO);
        idGenre = bundle.getInt(KEY_ID_GENERO);

        cargarArtistas();

        cargarImagen(imgGenero, urlImagenCabecera);
        tvCabeceraGenero.setTitle(nombreGenero);
        return view;
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(getContext()).load(url).into(imageView);
    }

    private void crearArtistaRecyclerView(View view, Integer idLayout) {
        RecyclerView generoRecyclerView = view.findViewById(idLayout);
        generoRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        generoRecyclerView.setLayoutManager(linearLayoutManager);

        ArtistaAdapterRecyclerView categoriaAdapterRecyclerView = new ArtistaAdapterRecyclerView(artistGenre, R.layout.cardview_artista, getActivity(), this);

        generoRecyclerView.setAdapter(categoriaAdapterRecyclerView);
    }


    public void cargarArtistas() {
        ArtistController artistController = new ArtistController();
        artistController.getArtist(new ResultListener<List<ArtistDeezer>>() {
            @Override
            public void finish(List<ArtistDeezer> resultado) {
                artistGenre = resultado;
                crearArtistaRecyclerView(view, R.id.rvGeneroArtista);
                pbCategoria.setVisibility(View.GONE);
                conatiner.setVisibility(View.VISIBLE);


            }
        }, getContext(), idGenre);

    }

    @Override
    public void cambiarDeActividad(ArtistDeezer artista) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle bundle = new Bundle();
        bundle.putString(AlbumFragment.KEY_IMAGEN_ARTISTA, artista.getPictureMedium());
        bundle.putString(AlbumFragment.KEY_NOMBRE_ARTISTA, artista.getName());
        bundle.putInt(AlbumFragment.KEY_ID_ARTISTA, artista.getId());
        albumFragment.setArguments(bundle);
        mainActivity.reemplazarFragment(albumFragment);
    }

}
