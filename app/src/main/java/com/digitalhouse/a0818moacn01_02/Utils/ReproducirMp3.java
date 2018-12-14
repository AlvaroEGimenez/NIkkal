package com.digitalhouse.a0818moacn01_02.Utils;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Radio;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.ReproductorActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class ReproducirMp3 {
    private MediaPlayer mediaPlayer;
    private static List<Track> listaReproduccion;
    private static Integer posicion;
    private AppCompatActivity activity;
    private Boolean isMainActivity;

    public ReproducirMp3(Boolean isMainActivity) {
        this.isMainActivity = isMainActivity;
    }

    public void reproducirMp3(final List<Track> listaReproduccion, final Integer posicion, final AppCompatActivity activity) {
        this.listaReproduccion = listaReproduccion;
        Track pista = listaReproduccion.get(posicion);
        this.posicion = posicion;
        this.activity = activity;

        final ImageView imageViewPlay = activity.findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = activity.findViewById(R.id.btnReproductorPause);
        final LinearLayout linearLayout = activity.findViewById(R.id.layoutPlayer);
        mediaPlayer = MediaPlayerNikkal.getInstance().getMediaPlayer();

        ImageView imageViewExpandir = activity.findViewById(R.id.btnActivityReproductor);

        if (imageViewExpandir != null) {
            imageViewExpandir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ReproductorActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ReproductorActivity.KEY_POSICION, posicion);
                    bundle.putSerializable(ReproductorActivity.KEY_OBJETO, (Serializable) listaReproduccion);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });

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
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        imageViewPause.setVisibility(View.VISIBLE);
                        imageViewPlay.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(pista.getPreview());
                mediaPlayer.prepare();
                mediaPlayer.start();
                if (imageViewExpandir != null) {
                    linearLayout.setVisibility(View.VISIBLE);
                    imageViewPause.setVisibility(View.VISIBLE);
                    imageViewPlay.setVisibility(View.INVISIBLE);
                }
            }
            if (!isMainActivity) {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (posicion < listaReproduccion.size() - 1) {
                            reproducirMp3(listaReproduccion, posicion + 1, activity);
                        }

                    }
                });
            }

        } catch (
                IOException e) {

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

    public void ReproducirMp3Activity(final Integer posicion) {
        mediaPlayer = MediaPlayerNikkal.getInstance().getMediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.posicion = posicion;

        Track track = this.listaReproduccion.get(posicion);
        try {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(track.getPreview());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

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


    public void reproducirRadio(final String url, final MediaPlayer mediaPlayer, final Radio radio, final AppCompatActivity activity) {

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        final ImageView imageViewPlay = activity.findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = activity.findViewById(R.id.btnReproductorPause);
        final TextView textViewNombrePista = activity.findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
        textViewNombrePista.setSelected(true);

        Animation animationBlink = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.blink);
        final Animation animationNone = AnimationUtils.loadAnimation(activity.getApplication(), R.anim.none);

        final LinearLayout linearLayout = activity.findViewById(R.id.layoutPlayer);

        final ProgressBar progressBar;
        progressBar = activity.findViewById(R.id.progressBarRadio);
        linearLayout.setVisibility(View.VISIBLE);
        textViewNombrePista.setText("Cargando...");
        textViewNombrePista.setAnimation(animationBlink);

        try {
                mediaPlayer.stop();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.start();

            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (progressBar.getVisibility() == View.VISIBLE) {

                    }
                    mediaPlayer.pause();
                    imageViewPlay.setVisibility(View.VISIBLE);
                    imageViewPause.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    textViewNombrePista.setAnimation(animationNone);
                    textViewNombrePista.setText("Pausa");
                }
            });

            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        imageViewPause.setVisibility(View.VISIBLE);
                        imageViewPlay.setVisibility(View.INVISIBLE);
                        textViewNombrePista.setText(radio.getNombre() + " - " + radio.getSintonia());
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

    public Integer getPosicion() {
        return posicion;
    }

    public List<Track> getListaReproduccion() {
        return listaReproduccion;
    }
}
