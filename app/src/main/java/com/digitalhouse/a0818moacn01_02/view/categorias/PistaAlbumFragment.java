package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.DAOLocal;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.TopChartLocal;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAdapterViewPage;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.PistaAlbumRecyclerView;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;

import java.io.IOException;
import java.util.ArrayList;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

public class PistaAlbumFragment extends Fragment implements PistaAlbumRecyclerView.PistaAdapterInterface, PistaAdapterViewPage.PistaViewPageInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public static final String KEY_IMAGEN_CABECERA_ALBUM_PISTA = "imgCabeceraAlbumPista";
    public static final String KEY_NOMBRE_CABECERA_ALBUM_PISTA = "nombreCabeceraAlbumPista";

    private ImageView imgCabeceraAlbumPista;
    private Toolbar toolbaarNombreCabeceraAlbumPista;
    private RecyclerView recyclerView;
    private View view;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageButton btnPlay;
    private ImageButton btnPause;

    private ArrayList<TopChartLocal> pistas = new ArrayList<>();
    AutoScrollViewPager autoScrollViewPager;

    public PistaAlbumFragment() {
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

        cargarImagen(imgCabeceraAlbumPista, urlImagenCabecera);
        toolbaarNombreCabeceraAlbumPista.setTitle(nombreCabeceraPistaAlbum);

        crearAlbumRecyclerView(R.id.rvPistaAlbum);

        DAOLocal daoLocal = new DAOLocal();
        daoLocal.ObtenerTopChar(pistas);


        return view;
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(getContext()).load(url).into(imageView);
    }

    private void crearAlbumRecyclerView(Integer idLayout) {
        recyclerView = view.findViewById(idLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        PistaAlbumRecyclerView pistaAlbumRecyclerView = new PistaAlbumRecyclerView(this.pistas, R.layout.cardview_pista_album, getActivity(), this);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(pistaAlbumRecyclerView);
    }

    private void setFavoritoPista(TopChartLocal pista, ImageView favoritoPista) {
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
    public void favoritoListener(TopChartLocal pista, ImageView favoritoPista) {
        setFavoritoPista(pista, favoritoPista);
    }

    @Override
    public void playListListener(TopChartLocal pista) {

        Toast.makeText(getContext(), "Se ha agregado al playList: " + pista.getNombreTrack(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void compartirListener(TopChartLocal pista) {
        Toast.makeText(getContext(), "Compartir pista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pistaViewPageListener(Integer posicion, View itemViewSelected) {
        Dialog dialog = new Dialog(getContext(), R.style.pistaViewPage);
        dialog.setContentView(R.layout.pista_view_page_content);

        PistaAdapterViewPage pistaAdapterViewPage = new PistaAdapterViewPage(pistas, getContext(), this);
        autoScrollViewPager = dialog.findViewById(R.id.pistaViewPagerScroll);
        autoScrollViewPager.setAdapter(pistaAdapterViewPage);
        autoScrollViewPager.setCurrentItem(posicion);
        dialog.show();


    }

    private void cargarImagen(ImageView imageView, Integer idDrawable) {
        Glide.with(getContext()).load(idDrawable).into(imageView);
    }

    @Override
    public void pistaAnterior(Integer position) {
        autoScrollViewPager.setCurrentItem(position);
    }

    @Override
    public void pistaSiguiente(Integer position) {
        autoScrollViewPager.setCurrentItem(position);
        long itemId = recyclerView.getAdapter().getItemId(position);

    }

    @Override
    public void pistaPlayPause(TopChartLocal pista, final ProgressBar progressBar) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        final ImageButton btnPlay = view.findViewById(R.id.ic_play_pista);
        final ImageButton btnPause = view.findViewById(R.id.ic_pause_pista);

        final Handler mSeekbarUpdateHandler = new Handler();
        final Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };

        final String url = pista.getUrlMp3();


        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            progressBar.setMax(mediaPlayer.getDuration());
            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
            }

        }


    @Override
    public void favoritoListenerPista(Integer posicion, ImageView favoritoPistaReproductor) {
        recyclerView.setAdapter(null);
        TopChartLocal pista = pistas.get(posicion);
        setFavoritoPista(pista, favoritoPistaReproductor);
        PistaAlbumRecyclerView pistaAlbumRecyclerView = new PistaAlbumRecyclerView(this.pistas, R.layout.cardview_pista_album, getActivity(), this);
        recyclerView.setAdapter(pistaAlbumRecyclerView);

    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        TopChartLocal pista = pistas.get(position);
        Toast.makeText(getContext(), "Ingrese nombre del playList ", Toast.LENGTH_SHORT).show();
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemInserted(position);


    }
}
