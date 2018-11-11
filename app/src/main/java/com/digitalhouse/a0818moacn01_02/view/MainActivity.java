package com.digitalhouse.a0818moacn01_02.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar.AdapatadorBusqueda;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar.BuscarFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Configuracion.ConfiguracionFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Favoritos.FavoritoFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Pantalla_Principal.CategoriaFragment;
import com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online.RadioFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapatadorBusqueda.BusquedaInterface {

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

        mediaPlayer = new MediaPlayer();

        reemplazarFragment(categoriaFragment);

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
        }else{
            linearLayoutReproductor.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            imageViewPause.setVisibility(View.GONE);
            linearLayoutReproductor.setVisibility(View.GONE);
        }
    }


    public Boolean estaLogeado(final Context context){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (!isLoggedIn){
            alertDialogInicionSesion(context);
            return Boolean.FALSE;
        }else {
            return Boolean.TRUE;
        }
    }

    private void alertDialogInicionSesion(final Context context){
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


    public BottomNavigationView getBottomNavigation() {
        return bottomNavigation;
    }

}
