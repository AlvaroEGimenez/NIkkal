package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online;


import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment implements AdaptadorRadio.RadioInterface {

    private AdaptadorRadio adaptadorRadio;
    private MediaPlayer mediaPlayer;
    private TextView textViewNombrePista;
    private Animation animationBlink;
    private Animation animationNone;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private ImageView imageViewPlay;
    private ImageView imageViewPause;

    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio, container, false);

        final ArrayList<Radio> radios = new ArrayList<>();


        Radio laTribu = new Radio("La Tribu", "88.7 FM", "http://vivo.fmlatribu.com:8000/latribu.mp3");
        Radio Malena = new Radio("Malena", "89.1 FM", "http://vivo.radioam750.com.ar/vivofm.mp3");
        Radio arpeggio = new Radio("Arpeggio", "89.5 FM", "http://14073.live.streamtheworld.com/ARPEGGIOAAC");
        Radio radioConVos = new Radio("Radio Con Vos", "89.9 FM", "http://server6.stweb.tv:1935/rcvos/live/chunklist.m3u8");
        Radio ESPN = new Radio("ESPN", "107.9 FM", "http://apiradio.espn-la.com/64list.m3u8");
        Radio LaBoca = new Radio("La Boca", "90.1 FM", "http://208.98.41.72:9304");
        Radio Metro = new Radio("Metro", "95.1 FM", "http://mp3.metroaudio1.stream.avstreaming.net:7200/metro");
        Radio Delta = new Radio("Delta", "90.3 FM", "http://radio.mediastre.am/radiodelta.aac");
        Radio Vorterix = new Radio("Vorterix", "92.1 FM", "http://prepublish.f.qaotic.net/a02/ngrp:Vorterix_radio1_high-20057_all/Playlist.m3u8");
        Radio NacionalRock = new Radio("Nacional Rock", "93.7 FM", "http://sa.mp3.icecast.magma.edge-access.net:7200/sc_rad39");
        Radio RadioDisney = new Radio("Radio Disney", "94.3 FM", "http://disneyargradio-lh.akamaihd.net/i/ARG_Disney_RADIO@102438/master.m3u8");
        Radio RockPop = new Radio("Rock & Pop", "95.9 FM", "http://blaster.cdn.sion.com:1935/rockandpop/audioweb/playlist.m3u8");
        Radio Vale = new Radio("Vale", "97.5 FM", "rtmp://vale.stweb.tv:1935/vale/live");
        Radio Mega = new Radio("Mega", "98.3 FM", "rtmp://server5.stweb.tv:1935/mega983/live");
        Radio La100 = new Radio("La 100", "99.9 FM", "http://buecrplb01.cienradios.com.ar/la100.aac");
        Radio Blue = new Radio("Blue", "100.7 FM", "http://mp3.metroaudio1.stream.avstreaming.net:7200/bluefmaudio1");
        Radio Latina = new Radio("Latina", "101.1 FM", "http://streaming.latina101.com.ar:8080/RadioLatina");
        Radio Aspen = new Radio("Aspen", "102.3 FM", "http://201.212.5.144/aspen?MSWMExt=.asf");

        radios.add(laTribu);
        radios.add(Malena);
        radios.add(arpeggio);
        radios.add(radioConVos);
        radios.add(LaBoca);
        radios.add(Metro);
        radios.add(Delta);
        radios.add(Vorterix);
        radios.add(NacionalRock);
        radios.add(RadioDisney);
        radios.add(RockPop);
        radios.add(Vale);
        radios.add(Mega);
        radios.add(La100);
        radios.add(Blue);
        radios.add(Latina);
        radios.add(Aspen);
        radios.add(ESPN);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerRadio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adaptadorRadio = new AdaptadorRadio(radios, this);
        recyclerView.setAdapter(adaptadorRadio);

        mediaPlayer = new MediaPlayer();

        textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
        textViewNombrePista.setSelected(true);


        animationBlink = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
        animationNone = AnimationUtils.loadAnimation(getContext(), R.anim.none);

        linearLayout = getActivity().findViewById(R.id.layoutPlayer);



        imageViewPlay = getActivity().findViewById(R.id.btnRepoductorPlay);
        imageViewPause = getActivity().findViewById(R.id.btnReproductorPause);


        return view;
    }

    @Override
    public void OnRadioClick(Radio radio) {

        progressBar = new ProgressBar(getContext());
        progressBar = getActivity().findViewById(R.id.progressBarRadio);
        linearLayout.setVisibility(View.VISIBLE);
        textViewNombrePista.setText("Cargando...");
        textViewNombrePista.setAnimation(animationBlink);
        progressBar.setVisibility(View.VISIBLE);
        String url = radio.getUrl();
        reproducirMp3(url, mediaPlayer, radio);

    }


    private void reproducirMp3(final String url, final MediaPlayer mediaPlayer, final Radio radio) {

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                textViewNombrePista.setAnimation(animationNone);
                progressBar.setVisibility(View.INVISIBLE);
                mediaPlayer.start();
                textViewNombrePista.setText(radio.getNombre() + " - " + radio.getSintonia());
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



