package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAdapterViewPage;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAlbumRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

public class PistaAlbumFragment extends Fragment implements PistaAlbumRecyclerView.PistaAdapterInterface, PistaAdapterViewPage.PistaViewPageInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static final String KEY_IMAGEN_CABECERA_ALBUM_PISTA = "imgCabeceraAlbumPista";
    public static final String KEY_NOMBRE_CABECERA_ALBUM_PISTA = "nombreCabeceraAlbumPista";
    public static final String KEY_PISTA_ID_ALBUM_PISTA = "pistaIdAlbumPista";
    public static final String KEY_FAVORITO_ALBUM = "idFavoritoAlbum";

    private ImageView imgCabeceraAlbumPista;
    private Toolbar toolbaarNombreCabeceraAlbumPista;
    private PistaAlbumRecyclerView pistaAlbumRecyclerView;
    private PistaAdapterViewPage pistaAdapterViewPage;
    private AutoScrollViewPager autoScrollViewPager;
    private RecyclerView recyclerView;
    private View view;
    private MediaPlayer mediaPlayer;
    private Track pistaActual;
    private MainActivity parent;
    private ProgressBar progressBar;
    private List<Track> pistas = new ArrayList<>();
    private FloatingActionButton btnReproducirAlbum;
    private Boolean reprducirAlbum = Boolean.FALSE;
    private String urlImagenCabecera;

    private Boolean favoritoAlbum;
    private FloatingActionButton btnFavorito;

    public PistaAlbumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pista_album, container, false);
        imgCabeceraAlbumPista = view.findViewById(R.id.imgCabeceraAlbumPista);
        toolbaarNombreCabeceraAlbumPista = view.findViewById(R.id.toolbaarNombreCabeceraAlbumPista);
        btnReproducirAlbum = view.findViewById(R.id.btnReproducirAlbumPista);
        btnReproducirAlbum.setOnClickListener(resproducirAlbumListener);
        btnFavorito = view.findViewById(R.id.btnFavoritoAlbum);
        btnFavorito.setOnClickListener(favoritoAlbumListener);


        Bundle bundle = getArguments();
        urlImagenCabecera = bundle.getString(KEY_IMAGEN_CABECERA_ALBUM_PISTA);
        String nombreCabeceraPistaAlbum = bundle.getString(KEY_NOMBRE_CABECERA_ALBUM_PISTA);
        Integer idPista = bundle.getInt(KEY_PISTA_ID_ALBUM_PISTA);
        favoritoAlbum  = bundle.getBoolean(KEY_FAVORITO_ALBUM);

        cargarImagen(imgCabeceraAlbumPista, urlImagenCabecera);
        toolbaarNombreCabeceraAlbumPista.setTitle(nombreCabeceraPistaAlbum);
        cargarPista(idPista);

        mediaPlayer = parent.getMediaPlayer();

        return view;
    }

    private void cargarPista(Integer idPista) {
        TracksController tracksController = new TracksController();
        tracksController.getPistas(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                crearAlbumRecyclerView(R.id.rvPistaAlbum, resultado);
            }
        }, getContext(), idPista);
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(Integer idLayout, List<Track> pistas) {
        this.pistas = pistas;
        recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        pistaAlbumRecyclerView = new PistaAlbumRecyclerView(pistas, R.layout.cardview_pista_album, getActivity(), this);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(pistaAlbumRecyclerView);
    }

    private void setFavoritoPista(Track pista, ImageView favoritoPista) {
        if (!pista.getFavorito()) {
            pista.setFavorito(true);
        } else {
            pista.setFavorito(false);
        }

        if (pista.getFavorito()) {
            cargarImagen(favoritoPista, R.drawable.ic_favorite_seleccionado);
        } else {
            cargarImagen(favoritoPista, R.drawable.ic_favorite_no_seleccion);
        }
    }

    @Override
    public void favoritoListener(Track pista, ImageView favoritoPista) {
        if (parent.estaLogeado(getContext())){
            setFavoritoPista(pista, favoritoPista);
        }
    }

    @Override
    public void playListListener(Track pista) {

        Toast.makeText(getContext(), "Se ha agregado al playList: " + pista.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void compartirListener(Track pista) {
        Toast.makeText(getContext(), "Compartir pista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pistaViewPageListener(Integer posicion, View itemViewSelected) {
        parent.getBottomNavigation().setVisibility(View.GONE);
        final Dialog dialog = new Dialog(getContext(), R.style.pistaViewPage);
        dialog.setContentView(R.layout.pista_view_page_content);
        progressBar = dialog.findViewById(R.id.progrerssBarPistaViewPage);
        pistaAdapterViewPage = new PistaAdapterViewPage(pistas, getContext(), this);
        autoScrollViewPager = dialog.findViewById(R.id.pistaViewPagerScroll);
        autoScrollViewPager.setAdapter(pistaAdapterViewPage);
        autoScrollViewPager.setCurrentItem(posicion);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    TextView textViewNombrePista = parent.findViewById(R.id.tvNombreReproductor);
                    textViewNombrePista.setSelected(true);
                    textViewNombrePista.setText(pistaActual.getArtist().getName() + " - " + pistaActual.getTitle());
                    parent.visibilidadReproductor(true);
                    parent.getBottomNavigation().setVisibility(View.VISIBLE);
                }
                return true;
            }
        });


        dialog.show();
        parent.visibilidadReproductor(false);
    }

    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(imageView);
    }

    @Override
    public void pistaAnterior(Integer posicion) {
        autoScrollViewPager.setCurrentItem(posicion);
        pistaPlay(posicion < 0 ? pistas.size() + posicion : posicion);
    }

    @Override
    public void pistaSiguiente(Integer posicion) {
        autoScrollViewPager.setCurrentItem(posicion);
        pistaPlay(posicion >= pistas.size() ? posicion - pistas.size() : posicion);
    }

    @Override
    public void pistaPlay(final Integer posicion) {
        if (mediaPlayer.getCurrentPosition() > 0 && pistaActual != null && pistaActual.getId().equals(pistas.get(posicion))) {
            mediaPlayer.reset();
            mediaPlayer.start();
            return;
        }

        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(), posicion);
        recyclerView.getAdapter().notifyDataSetChanged();

        pistaActual = pistas.get(posicion);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        final Handler mSeekbarUpdateHandler = new Handler();

        final Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
                if (reprducirAlbum && !mediaPlayer.isPlaying() && posicion <= pistas.size()) {
                    pistaSiguiente(posicion + 1);
                }
            }
        };

        final String url = pistaActual.getPreview();

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        progressBar.setMax(mediaPlayer.getDuration());
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
    }

    @Override
    public void pistaPause() {
        mediaPlayer.pause();
    }


    @Override
    public void favoritoListenerPista(Integer posicion, ImageView favoritoPistaReproductor) {
        Track pista = pistas.get(posicion);
        setFavoritoPista(pista, favoritoPistaReproductor);
        pistaAlbumRecyclerView.notifyDataSetChanged();
        pistaAdapterViewPage.notifyDataSetChanged();

    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(pistas, fromPosition, toPosition);
        pistaAlbumRecyclerView.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Toast.makeText(getContext(), "Ingrese nombre del playList ", Toast.LENGTH_SHORT).show();
        pistaAlbumRecyclerView.notifyItemRemoved(position);
        pistaAlbumRecyclerView.notifyItemInserted(position);
        Track pista = pistas.get(position);
        pista.setImagenAlbum(urlImagenCabecera);
        parent.getPistasListaReproduccion().add(pistas.get(position));
        parent.getPistaAlbumRecyclerView().notifyDataSetChanged();
    }


    View.OnClickListener resproducirAlbumListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reprducirAlbum = Boolean.TRUE;
            pistaViewPageListener(0, null);
            pistaPlay(0);
        }
    };


    private void setFavoritoPista() {

        if (favoritoAlbum) {
            favoritoAlbum = Boolean.FALSE;
            cargarImagen( R.drawable.ic_favorite_no_seleccion);
        } else {
            cargarImagen(R.drawable.ic_favorite_black_24dp);
            favoritoAlbum = Boolean.TRUE;

        }
    }


    View.OnClickListener favoritoAlbumListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (parent.estaLogeado(getContext())){
                setFavoritoPista();
            }

        }
    };

    private void cargarImagen(Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(btnFavorito);
    }


    

}