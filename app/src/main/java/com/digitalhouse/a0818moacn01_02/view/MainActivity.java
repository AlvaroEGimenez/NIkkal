package com.digitalhouse.a0818moacn01_02.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.TracksController;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion.ItemTouchHelperCallback;
import com.digitalhouse.a0818moacn01_02.view.adapter.listaReproduccion.PistaListaReproduccionAdapter;
import com.digitalhouse.a0818moacn01_02.view.adapter.pista.RecyclerItemTouchHelper;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar.AdapatadorBusqueda;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar.BuscarFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Configuracion.ConfiguracionFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos.FavoritoFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal.CategoriaFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online.RadioFragment;
import com.facebook.AccessToken;
import com.facebook.Profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapatadorBusqueda.BusquedaInterface,
        PistaListaReproduccionAdapter.PistaListaReproduccionAdapterInterface, MediaPlayer.OnCompletionListener {

    private CategoriaFragment categoriaFragment = new CategoriaFragment();
    private BuscarFragment buscarFragment = new BuscarFragment();
    private FavoritoFragment favoritoFragment = new FavoritoFragment();
    private RadioFragment radioFragment = new RadioFragment();
    private ConfiguracionFragment configuracionFragment = new ConfiguracionFragment();
    private TextView textViewNombrePista;
    private ImageView imageViewPlay;
    private ImageView imageViewPause;
    private LinearLayout linearLayoutReproductor;
    private MediaPlayer mediaPlayer;
    private Fragment mContent;
    private BottomNavigationView bottomNavigation;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerView;
    private List<Track> pistasListaReproduccion = new ArrayList<>();
    private  PistaListaReproduccionAdapter pistaAlbumRecyclerView;
    private FloatingActionButton btnListaReproduccion;
    private Integer posicionActualLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.navigationView);
        ActionBar actionBar = getSupportActionBar();
        textViewNombrePista = findViewById(R.id.tvNombreReproductor);
        imageViewPlay = findViewById(R.id.btnRepoductorPlay);
        imageViewPause = findViewById(R.id.btnReproductorPause);
        linearLayoutReproductor = findViewById(R.id.layoutPlayer);

        reemplazarFragment(categoriaFragment);

        mediaPlayer = new MediaPlayer();
        navigationView = findViewById(R.id.navigationMainActivity);
        headerView = navigationView.inflateHeaderView(R.layout.header_navigation_view);
        btnListaReproduccion =  findViewById(R.id.btnListaReproduccion);
        btnListaReproduccion.setOnClickListener(listaReproducction);
        cargarImagenHeaderNavigationView();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.albumFragment:
                        reemplazarFragment(categoriaFragment);
                        return true;
                    case R.id.buscarFragment:
                        reemplazarFragment(buscarFragment);
                        return true;

                    case R.id.favoritoFragment:
                        reemplazarFragment(favoritoFragment);
                        return true;
                    case R.id.radioFragment:
                        reemplazarFragment(radioFragment);
                        return true;
                }
                return false;
            }
        });
        List<Integer> tracksId = new ArrayList<>();
        tracksId.add(3135553);
        tracksId.add(3135653);
        tracksId.add(3135453);
        tracksId.add(3135753);
        tracksId.add(3132553);
        tracksId.add(3135563);
        tracksId.add(3135453);
        tracksId.add(3137553);
        tracksId.add(3135353);
        tracksId.add(3135453);
        tracksId.add(3535553);
        tracksId.add(3165553);

        cargarListaReproduccion(tracksId);
    }


    public void reemplazarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null).commit();

    }

    @Override
    public void busquedaClick(Track track, Integer posicion) {
        linearLayoutReproductor.setVisibility(View.VISIBLE);
        textViewNombrePista.setText(track.getTitle() + " - " + track.getArtist().getName());
        String urlMp3 = track.getPreview();
        reproducirMp3(urlMp3, mediaPlayer);
        textViewNombrePista.setSelected(true);
    }

    public void reproducirMp3(final String url, final MediaPlayer mediaPlayer) {

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
                imageViewPause.setVisibility(View.VISIBLE);


            } else {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }


            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.pause();
                    imageViewPlay.setVisibility(View.VISIBLE);
                    imageViewPause.setVisibility(View.INVISIBLE);
                }
            });


            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mediaPlayer != null) {

                        mediaPlayer.start();
                        imageViewPause.setVisibility(View.VISIBLE);
                        imageViewPlay.setVisibility(View.INVISIBLE);
                    }
                }
            });


        } catch (
                IOException e)

        {

            e.printStackTrace();
        } catch (
                IllegalArgumentException e)

        {
            e.printStackTrace();
        } catch (
                SecurityException e)

        {
            e.printStackTrace();
        } catch (
                IllegalStateException e)

        {
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void visibilidadReproductor(Boolean esVisible) {
        if (esVisible) {
            linearLayoutReproductor.setVisibility(View.VISIBLE);
            imageViewPlay.setVisibility(View.INVISIBLE);
            imageViewPause.setVisibility(View.VISIBLE);
            textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
            imageViewPause.setVisibility(View.VISIBLE);
        } else {
            linearLayoutReproductor.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            linearLayoutReproductor.setVisibility(View.GONE);
        }
    }


    public Boolean estaLogeado(final Context context) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (!isLoggedIn) {
            alertDialogInicionSesion(context);
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private void alertDialogInicionSesion(final Context context) {
        String mensajeSi = getResources().getString(R.string.si);
        String mensajeNo = getResources().getString(R.string.no);
        String mensajeCabecera = getResources().getString(R.string.cabeceraLogin);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(mensajeCabecera);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                mensajeSi,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                mensajeNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void cargarImagenHeaderNavigationView() {
        ImageView ivUSuario = headerView.findViewById(R.id.usuarioListaReproducion);

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Uri uri = profile.getProfilePictureUri(200, 200);
            Glide.with(this).load(uri).into(ivUSuario);
        } else {
            ivUSuario.setImageResource(R.drawable.ic_person_outline_black_24dp);
        }

    }


    private void cargarListaReproduccion(final List<Integer> tracksId ) {
        TracksController tracksController = new TracksController();

        for(Integer pistaId : tracksId) {
            tracksController.getPista(new ResultListener<Track>() {
                @Override
                public void finish(Track resultado) {
                    pistasListaReproduccion.add(resultado);
                    if (pistasListaReproduccion.size() ==  tracksId.size()) {
                        crearListaReproduccionRecyclerView();
                    }

                }
            }, getApplicationContext(), pistaId);
        }
    }

    private void crearListaReproduccionRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvListaReproduccion);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        pistaAlbumRecyclerView = new PistaListaReproduccionAdapter(pistasListaReproduccion, R.layout.cardview_pista_listado_reproduccion, this, this);

        recyclerView.setAdapter(pistaAlbumRecyclerView);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(pistaAlbumRecyclerView);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public BottomNavigationView getBottomNavigation() {
        return bottomNavigation;
    }

    public List<Track> getPistasListaReproduccion() {
        return pistasListaReproduccion;
    }

    public PistaListaReproduccionAdapter getPistaAlbumRecyclerView() {
        return pistaAlbumRecyclerView;
    }

    View.OnClickListener listaReproducction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            posicionActualLista = 0;
            pistaListaReproduccionAdapterInterface(posicionActualLista++);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                  if(posicionActualLista < pistasListaReproduccion.size()) {
                      pistaListaReproduccionAdapterInterface(posicionActualLista++);
                  }else{
                      posicionActualLista = 0;
                      return;
                  }
                }
            });
        }
    };

    @Override
    public void pistaListaReproduccionAdapterInterface(Integer posicion) {
        mediaPlayer.reset();
        Track pista = pistasListaReproduccion.get(posicion);
        busquedaClick(pista, posicion);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Track pista = pistasListaReproduccion.get(5);
        busquedaClick(pista, 5);



    }
}
