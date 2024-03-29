package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online;


import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.model.Radio;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment implements AdaptadorRadio.RadioInterface {

    ReproducirMp3 reproducirMp3;
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
        MainActivity parent = (MainActivity) getActivity();
        reproducirMp3 = parent.getReproducirMp3();

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
        AdaptadorRadio adaptadorRadio = new AdaptadorRadio(radios, this);
        recyclerView.setAdapter(adaptadorRadio);

        mediaPlayer = parent.getMediaPlayer();

        return view;

    }

    @Override
    public void OnRadioClick(Radio radio) {
        String url = radio.getUrl();
        reproducirMp3.reproducirRadio(url,mediaPlayer,radio,(MainActivity)getActivity());

    }




}



