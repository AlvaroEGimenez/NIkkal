package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.DAO.DAOLocal;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.model.Tracks;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAdapterViewPage;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAlbumRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

public class PistaAlbumFragment extends Fragment implements PistaAlbumRecyclerView.PistaAdapterInterface, PistaAdapterViewPage.PistaViewPageInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    public static final String KEY_IMAGEN_CABECERA_ALBUM_PISTA = "imgCabeceraAlbumPista";
    public static final String KEY_NOMBRE_CABECERA_ALBUM_PISTA = "nombreCabeceraAlbumPista";
    public static final String KEY_PISTA_ID_ALBUM_PISTA = "pistaIdAlbumPista";

    private ImageView imgCabeceraAlbumPista;
    private Toolbar toolbaarNombreCabeceraAlbumPista;
    private PistaAlbumRecyclerView pistaAlbumRecyclerView;
    private PistaAdapterViewPage pistaAdapterViewPage;
    private AutoScrollViewPager autoScrollViewPager;
    private RecyclerView recyclerView;
    private View view;
    private MediaPlayer mediaPlayer;
    private Integer  positionActual=null;
    private Track pistaActual;
    private Boolean swap = Boolean.FALSE;
    private MainActivity mainActivity;
    private List<Track> pistas = new ArrayList<>();


    public PistaAlbumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pista_album, container, false);

        imgCabeceraAlbumPista = view.findViewById(R.id.imgCabeceraAlbumPista);
        toolbaarNombreCabeceraAlbumPista = view.findViewById(R.id.toolbaarNombreCabeceraAlbumPista);

        Bundle bundle = getArguments();
        String urlImagenCabecera = bundle.getString(KEY_IMAGEN_CABECERA_ALBUM_PISTA);
        String nombreCabeceraPistaAlbum = bundle.getString(KEY_NOMBRE_CABECERA_ALBUM_PISTA);
        Integer idPista = bundle.getInt(KEY_PISTA_ID_ALBUM_PISTA);

        cargarImagen(imgCabeceraAlbumPista, urlImagenCabecera);
        toolbaarNombreCabeceraAlbumPista.setTitle(nombreCabeceraPistaAlbum);
        cargarPista(idPista);

        mediaPlayer = mainActivity.getMediaPlayer();

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
        Glide.with(getContext()).load(url).into(imageView);
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
        setFavoritoPista(pista, favoritoPista);
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
        final Dialog dialog = new Dialog(getContext(), R.style.pistaViewPage);
        dialog.setContentView(R.layout.pista_view_page_content);

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
                    swap = Boolean.FALSE;
                    TextView textViewNombrePista = mainActivity.findViewById(R.id.tvNombreReproductor);
                    textViewNombrePista.setSelected(true);
                    textViewNombrePista.setText(pistaActual.getArtist().getName() + " - " + pistaActual.getTitle());
                    mainActivity.visibilidadReproductor(true);
                }
                return true;
            }
        });


        dialog.show();
        mainActivity.visibilidadReproductor(false);
    }

    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(imageView);
    }

    @Override
    public void pistaAnterior(Integer position) {
        positionActual = position;
        autoScrollViewPager.setCurrentItem(position);

    }

    @Override
    public void pistaSiguiente(Integer position) {
        positionActual = position;
        autoScrollViewPager.setCurrentItem(position);

    }

    @Override
    public void pistaPlay(final Track pista, final ProgressBar progressBar, Integer posicion) {
        pistaActual = pista;
        swap = Boolean.TRUE;
       if(mediaPlayer.getCurrentPosition()>0 && positionActual == posicion){
           mediaPlayer.start();
            return;
       }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        final Handler mSeekbarUpdateHandler = new Handler();

        final Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };

        final String url = pista.getPreview();

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

        positionActual = posicion;
    }

    @Override
    public void pistaPause(Track pista, ProgressBar progressBar, Integer posicion) {
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
        Track pista = pistas.get(position);
        Toast.makeText(getContext(), "Ingrese nombre del playList ", Toast.LENGTH_SHORT).show();
        pistaAlbumRecyclerView.notifyItemRemoved(position);
        pistaAlbumRecyclerView.notifyItemInserted(position);
    }

    public Boolean getSwap() {
        return swap;
    }

    public void setSwap(Boolean swap) {
        this.swap = swap;
    }
}