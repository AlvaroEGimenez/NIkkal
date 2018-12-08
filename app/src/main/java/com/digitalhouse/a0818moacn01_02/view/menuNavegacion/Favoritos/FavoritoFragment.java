package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.FavoritoFirebase;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoritoFragment extends Fragment implements AdaptadorFavoritos.FavoritosAdapterInterface { //implements AdaptadorFavoritos.AartistaFavoritodAdapterInterface {
    private AdaptadorFavoritos adaptadorFavoritos;
    private MainActivity parent;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
    private TextView tvTiuloSeleccionFavorito;

    public FavoritoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_favorito, container, false);
        parent = (MainActivity) getActivity();
        tvTiuloSeleccionFavorito = view.findViewById(R.id.tvTiuloSeleccionFavorito);

        RelativeLayout celdaAlbumFavorito = view.findViewById(R.id.celdaAlbumFavorito);
        RelativeLayout celdaCancionFavorita = view.findViewById(R.id.celdaPsitaFavorito);
        RelativeLayout celdaArtistaFavorito = view.findViewById(R.id.celdaArtista);
        RelativeLayout celdaListaFavorita = view.findViewById(R.id.celdaListaDeReproduccion);

        celdaAlbumFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Album Favorito", Toast.LENGTH_SHORT).show();
                //parent.reemplazarFragment(new MisAlbumsFragment());
                String tituloSeleccion = getResources().getString(R.string.album_favorito);
                tvTiuloSeleccionFavorito.setText(tituloSeleccion);
                setDatosFirebase(FavoritoFirebase.KEY_TIPO_ALBUM);
            }
        });

        celdaArtistaFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Artista favorito", Toast.LENGTH_SHORT).show();
                //    parent.reemplazarFragment(new MisArtistasFragment());
                //notificadorAlbumFavoritos.notificar(new MisArtistasFragment());
            }
        });

        celdaCancionFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Cancion Favorita", Toast.LENGTH_SHORT).show();
                //         parent.reemplazarFragment(new MisCancionesFragment());
                //notificadorAlbumFavoritos.notificar(new MisCancionesFragment());
            }
        });

        celdaListaFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Lista Favorita", Toast.LENGTH_SHORT).show();
                //     parent.reemplazarFragment(new MisListasFragment());
                //notificadorAlbumFavoritos.notificar(new MisListasFragment());
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbarFavoritos);
        toolbar.setTitle("");
        //casteado
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        //traigo la actividad que contiene este fragment
        collapsingToolbarLayout = view.findViewById(R.id.collapsingFavoritos);
        appBarLayout = view.findViewById(R.id.appBarFavoritos);
        appBarLayout.addOnOffsetChangedListener(appBarlistener);
        cargarRecyclerView(view);

        return view;

    }

    private void cargarRecyclerView(View view) {
        //instancio adaptador
        adaptadorFavoritos = new AdaptadorFavoritos(new ArrayList<Favorito>(), this);

        // buscamos la View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavoritos);

        //DATOS instanciamos un controller
        RadioController radioController = new RadioController();

        //performance
        recyclerView.setHasFixedSize(true);

        //layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adaptadorFavoritos);
    }


    private void setDatosFirebase(String categoriaSeleccionada) {
        FavoritoFirebase favoritoFirebaseArtista = new FavoritoFirebase(categoriaSeleccionada);
        favoritoFirebaseArtista.getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> resultado) {
                adaptadorFavoritos.setFavorito(resultado);
            }
        });

    }



    @Override
    public void onClickFavorito(Favorito favorito) {

    }


    AppBarLayout.OnOffsetChangedListener appBarlistener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (parent.getMenuFavoritos() == null) {
                return;
            }

            if (Math.abs(i) - appBarLayout.getTotalScrollRange() == 0) {
                parent.getMenuFavoritos().setGroupVisible(R.id.gupofavorito, true);
            } else {
                parent.getMenuFavoritos().setGroupVisible(R.id.gupofavorito, false);

            }
        }
    };


}
