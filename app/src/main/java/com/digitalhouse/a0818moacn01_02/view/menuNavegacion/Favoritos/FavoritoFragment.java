package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.Util;
import com.digitalhouse.a0818moacn01_02.controller.FavoritoController;
import com.digitalhouse.a0818moacn01_02.Utils.MediaPlayerNikkal;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.RadioController;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.Favorito;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.categorias.AlbumFragment;
import com.digitalhouse.a0818moacn01_02.view.categorias.PistaAlbumFragment;

import java.util.ArrayList;
import java.util.List;

public class FavoritoFragment extends Fragment implements AdaptadorFavoritos.FavoritosAdapterInterface { //implements AdaptadorFavoritos.AartistaFavoritodAdapterInterface {
    private AdaptadorFavoritos adaptadorFavoritos;
    private MainActivity parent;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView tvTiuloSeleccionFavorito;
    private String tituloArtista;
    private String tituloAlbum;
    private String tituloPista;
    private List<Favorito> favoritoList;
    private FavoritoController favoritoController;
    private ProgressBar pbFavorito;
    private RelativeLayout escuchadasRecientemente;
    private  RecyclerView recyclerView;

    public FavoritoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_favorito, container, false);
        parent = (MainActivity) getActivity();
        tvTiuloSeleccionFavorito = view.findViewById(R.id.tvTiuloSeleccionFavorito);
        pbFavorito = view.findViewById(R.id.pbFavorito);
        escuchadasRecientemente = view.findViewById(R.id.escuchadasRecientemente);

        RelativeLayout celdaAlbumFavorito = view.findViewById(R.id.celdaAlbumFavorito);
        RelativeLayout celdaCancionFavorita = view.findViewById(R.id.celdaPsitaFavorito);
        RelativeLayout celdaArtistaFavorito = view.findViewById(R.id.celdaArtista);
        RelativeLayout celdaListaFavorita = view.findViewById(R.id.celdaListaDeReproduccion);

        tituloArtista = getResources().getString(R.string.artistas);
        tituloAlbum = getResources().getString(R.string.album_favorito);
        tituloPista = getResources().getString(R.string.pistas);

        if (parent.estaLogeado(getContext())) {
            tvTiuloSeleccionFavorito.setText(R.string.bienvenido);

            celdaAlbumFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickAlbum();
                }
            });

            celdaArtistaFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickArtista();
                }
            });

            celdaCancionFavorita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPista();
                }
            });

            celdaListaFavorita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListaReproduccion();
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
            setHasOptionsMenu(true);
        } else {
            tvTiuloSeleccionFavorito.setText(R.string.mensajeLoginFavorito);
        }
        return view;
    }

    private void clickListaReproduccion() {
        MisListasFragment misListasFragment = new MisListasFragment();
        parent.reemplazarFragment(misListasFragment, R.id.favorito_fragemnt);
    }

    private void clickPista() {
        visibilidadRecyclerVew();
        tvTiuloSeleccionFavorito.setText(tituloPista);
        setDatosFirebase(FavoritoController.KEY_TIPO_PISTA);
    }

    private void clickArtista() {
        visibilidadRecyclerVew();
        tvTiuloSeleccionFavorito.setText(tituloArtista);
        setDatosFirebase(FavoritoController.KEY_TIPO_ARTISTA);
    }

    private void clickAlbum() {
        visibilidadRecyclerVew();
        tvTiuloSeleccionFavorito.setText(tituloAlbum);
        setDatosFirebase(FavoritoController.KEY_TIPO_ALBUM);
    }

    private void visibilidadRecyclerVew(){
        pbFavorito.setVisibility(View.VISIBLE);
        escuchadasRecientemente.setVisibility(View.GONE);
    }

    private void cargarRecyclerView(View view) {
        //instancio adaptador
        adaptadorFavoritos = new AdaptadorFavoritos(new ArrayList<Favorito>(), this);

        // buscamos la View
         recyclerView = view.findViewById(R.id.recyclerFavoritos);

        //DATOS instanciamos un controller
        RadioController radioController = new RadioController();

        //performance
    //    recyclerView.setHasFixedSize(true);

        //layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        ItemTouchHelper.Callback callback = new FavoritoItemTouchHelperCallback(adaptadorFavoritos);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adaptadorFavoritos);

        int resId = R.anim.grid_layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);

        recyclerView.setAdapter(adaptadorFavoritos);
    }

    private void setDatosFirebase(String categoriaSeleccionada) {
        favoritoController = new FavoritoController(categoriaSeleccionada, getContext());
        favoritoController.getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> resultado) {
                favoritoList = resultado;
                adaptadorFavoritos.setFavorito(resultado);
                setAnimacion();
                pbFavorito.setVisibility(View.GONE);
                escuchadasRecientemente.setVisibility(View.VISIBLE);
            }
        });

    }


    private void setAnimacion(){
        int resId = R.anim.grid_layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        recyclerView.setLayoutAnimation(animation);
    }

    @Override
    public void onClickFavorito(Favorito favorito) {
        if (tituloArtista.equals(tvTiuloSeleccionFavorito.getText().toString())) {
            irAlbums(favorito);
        } else if (tituloAlbum.equals(tvTiuloSeleccionFavorito.getText().toString())) {
            irPistasAlbum(favorito);
        } else if (tituloPista.equals(tvTiuloSeleccionFavorito.getText().toString())) {
            reproducirPista(favorito);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        Favorito favorito = favoritoList.get(position);
        favoritoController.eliminar(favorito.getId());
        favoritoList.remove(favorito);
        adaptadorFavoritos.notifyDataSetChanged();
        String msj = getResources().getString(R.string.elemento_eliminado);
        Toast.makeText(parent, favorito.getTitulo() + ": " + msj, Toast.LENGTH_SHORT).show();
    }

    private void irAlbums(Favorito favorito) {
        AlbumFragment albumFragment = new AlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AlbumFragment.KEY_IMAGEN_ARTISTA, favorito.getUrlImagen());
        bundle.putString(AlbumFragment.KEY_NOMBRE_ARTISTA, favorito.getTitulo());
        bundle.putInt(AlbumFragment.KEY_ID_ARTISTA, favorito.getId());
        bundle.putBoolean(AlbumFragment.KEY_FAVORITO_ARTISTA, Boolean.TRUE);

        albumFragment.setArguments(bundle);
        parent.reemplazarFragment(albumFragment, R.id.genero_fragment);
    }

    private void irPistasAlbum(Favorito favorito) {
        PistaAlbumFragment pistaAlbumFragment = new PistaAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PistaAlbumFragment.KEY_IMAGEN_CABECERA_ALBUM_PISTA, favorito.getUrlImagen());
        bundle.putString(PistaAlbumFragment.KEY_NOMBRE_CABECERA_ALBUM_PISTA, favorito.getTitulo());
        bundle.putInt(PistaAlbumFragment.KEY_PISTA_ID_ALBUM_PISTA, favorito.getId());
        bundle.putString(PistaAlbumFragment.KEY_CATEGORIA, "pistaAlbum");

        pistaAlbumFragment.setArguments(bundle);
        parent.reemplazarFragment(pistaAlbumFragment, R.id.genero_fragment);
    }

    private void reproducirPista(Favorito favorito) {
        if(!Util.hayInternet(getContext())){
            Toast.makeText(parent, getResources().getString(R.string.sin_conexion_reproducir), Toast.LENGTH_SHORT).show();
        }
        TracksController tracksController = new TracksController();
        tracksController.getPista(new ResultListener<Track>() {
            @Override
            public void finish(Track pista) {
                TextView textViewNombrePista = parent.findViewById(R.id.tvNombreReproductor);
                textViewNombrePista.setSelected(true);
                textViewNombrePista.setText(pista.getArtist().getName() + " - " + pista.getTitle());
                List<Track> pistas = new ArrayList<>();
                pistas.add(pista);
                MediaPlayerNikkal.getInstance().getMediaPlayer().stop();
                parent.getReproducirMp3().reproducirMp3(pistas, 0,  (MainActivity)getActivity());
                parent.visibilidadReproductor(true);
            }
        }, getContext(), favorito.getId());
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == R.id.itemAlbumFavorito) {
            clickAlbum();
        }
        if (res_id == R.id.itemArtistaFavorito) {
            clickArtista();
        }
        if (res_id == R.id.itemCancionFavorita) {
            clickPista();
        }
        if (res_id == R.id.itemListasFavoritas) {
            clickListaReproduccion();
        }


        return true;
    }
}
