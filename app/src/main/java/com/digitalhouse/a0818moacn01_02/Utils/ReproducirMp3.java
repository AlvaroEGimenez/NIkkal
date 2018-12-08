package com.digitalhouse.a0818moacn01_02.Utils;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Radio;

import java.io.IOException;

public class ReproducirMp3 {

    public void reproducirMp3(final String url, final MediaPlayer mediaPlayer, Activity activity) {
        final ImageView imageViewPlay = activity.findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = activity.findViewById(R.id.btnReproductorPause);
        final LinearLayout linearLayout = activity.findViewById(R.id.layoutPlayer);


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
                linearLayout.setVisibility(View.VISIBLE);
                imageViewPause.setVisibility(View.VISIBLE);
                imageViewPlay.setVisibility(View.INVISIBLE);

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

                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {

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

    public void ReproducirMp3Activity(String url, MediaPlayer mediaPlayer){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();


            } else {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
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

    public void reproducirRadio(final String url, final MediaPlayer mediaPlayer, final Radio radio, Activity activity) {

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
        progressBar.setVisibility(View.VISIBLE);

        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                textViewNombrePista.setAnimation(animationNone);
                progressBar.setVisibility(View.INVISIBLE);
                mediaPlayer.start();
                textViewNombrePista.setText(radio.getNombre() + " - " + radio.getSintonia());
                imageViewPause.setVisibility(View.VISIBLE);

            } else {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.start();
            }

            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (progressBar.getVisibility() == View.VISIBLE){
                        
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
}
