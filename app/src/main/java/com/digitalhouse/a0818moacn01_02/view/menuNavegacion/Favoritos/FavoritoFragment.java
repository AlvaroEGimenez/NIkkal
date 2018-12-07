package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritoFragment extends Fragment implements AdaptadorFavoritos.RadioFavoritodAdapterInterface { //implements AdaptadorFavoritos.AartistaFavoritodAdapterInterface {
    /* public static final String KEY_IMAGEN_ARTISTA_FAVORITO ="imagentArtistaFavorito";
     public static final String KEY_NOMBRE_ARTISTA_FAVORITO ="nombreArtistaFavorito";
     public static final String KEY_LISTA_ARTISTA_FAVORITO = "listaArtistaFavorito";*/
    private static final String KEY_SUGERENCIA = "sugerencia";
    private AdaptadorFavoritos adaptadorFavoritos;
    private interfacePasadorDeInformacion notificador;
    private MainActivity parent;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
    //private Integer trackId = 3135556;
    //private View view;
    private InterfaceNotificadorAlbumFavoritos notificadorAlbumFavoritos;
    /*private InterfaceNotificadorArtistFavoritos notificadorArtistFavoritos;
    private InterfaceNotificadorCancionesFavoritos notificadorCancionesFavoritos;
    private InterfaceNotificadorListaFavoritos notificadorListaFavoritos;*/


    public FavoritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notificador = (interfacePasadorDeInformacion) context;
        notificadorAlbumFavoritos = (InterfaceNotificadorAlbumFavoritos) context;
        /*notificadorArtistFavoritos = (InterfaceNotificadorArtistFavoritos) context;
        notificadorCancionesFavoritos = (InterfaceNotificadorCancionesFavoritos) context;
        notificadorListaFavoritos = (InterfaceNotificadorListaFavoritos) context;*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorito, container, false);
        parent = (MainActivity) getActivity();

        RelativeLayout celdaAlbumFavorito = view.findViewById(R.id.celdaAlbumFavorito);
        RelativeLayout celdaCancionFavorita = view.findViewById(R.id.celdaPsitaFavorito);
        RelativeLayout celdaArtistaFavorito = view.findViewById(R.id.celdaArtista);
        RelativeLayout celdaListaFavorita = view.findViewById(R.id.celdaListaDeReproduccion);

        celdaAlbumFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(parent, "Album Favorito", Toast.LENGTH_SHORT).show();
                parent.reemplazarFragment(new MisAlbumsFragment());
                //notificadorAlbumFavoritos.notificar(new MisAlbumsFragment());

            }
        });

        celdaArtistaFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Artista favorito", Toast.LENGTH_SHORT).show();
                parent.reemplazarFragment(new MisArtistasFragment());
                //notificadorAlbumFavoritos.notificar(new MisArtistasFragment());
            }
        });

        celdaCancionFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Cancion Favorita", Toast.LENGTH_SHORT).show();
                parent.reemplazarFragment(new MisCancionesFragment());
                //notificadorAlbumFavoritos.notificar(new MisCancionesFragment());
            }
        });

        celdaListaFavorita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent, "Lista Favorita", Toast.LENGTH_SHORT).show();
                parent.reemplazarFragment(new MisListasFragment());
                //notificadorAlbumFavoritos.notificar(new MisListasFragment());
            }
        });


        /*this.view = view;
        container = view.findViewById(R.id.rlayoutFavoritosContainer);*/
        //TOOLBAR

        Toolbar toolbar = view.findViewById(R.id.toolbarFavoritos);
        //casteado
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //instancio adaptador
        adaptadorFavoritos = new AdaptadorFavoritos(new ArrayList<RadioDeezer>(), this);

        // buscamos la View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavoritos);

        //DATOS instanciamos un controller
        RadioController radioController = new RadioController();


        radioController.getRadios(new ResultListener<List<RadioDeezer>>() {
            @Override
            public void finish(List<RadioDeezer> Resultado) {
                adaptadorFavoritos.setRadioDeezerList(Resultado);

            }
        }, this.getContext());


        //performance
        recyclerView.setHasFixedSize(true);

        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adaptadorFavoritos);

        //traigo la actividad que contiene este fragment

        collapsingToolbarLayout = view.findViewById(R.id.collapsingFavoritos);
        appBarLayout = view.findViewById(R.id.appBarFavoritos);
        appBarLayout.addOnOffsetChangedListener(appBarlistener);

        return view;

    }

    @Override
    public void cambiarDeActividad(RadioDeezer radioDeezer) {
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, radioDeezer.getPictureBig());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, radioDeezer.getTitle());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, Integer.parseInt(radioDeezer.getId()));
        bundle.putString(PistaAlbumFragment.KEY_CATEGORIA, KEY_SUGERENCIA);

        pistaAlbumFragment.setArguments(bundle);
        parent.reemplazarFragment(pistaAlbumFragment);


    }

   /* public interface InterfaceNotificadorArtistFavoritos{
        public void notificar (Fragment fragment);
    }*/

    public interface InterfaceNotificadorAlbumFavoritos {
        public void notificar(Fragment fragment);
    }

    /*public interface InterfaceNotificadorCancionesFavoritos{
         public void notificar (Fragment fragment);
     }

     public interface InterfaceNotificadorListaFavoritos {
         public void notificar (Fragment fragment);
     }
 */
    public interface interfacePasadorDeInformacion {
        public void recibirmensaje(RadioDeezer radioDeezer);
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
