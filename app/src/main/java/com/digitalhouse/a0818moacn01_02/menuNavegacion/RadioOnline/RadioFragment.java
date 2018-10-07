package com.digitalhouse.a0818moacn01_02.menuNavegacion.RadioOnline;


import android.app.SearchManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {
    private String radioEncendidaUrl = "";


    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio, container, false);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final List<Radio> radios = new ArrayList<>();


        Radio laTribu = new Radio("La Tribu", "88.7", "http://vivo.fmlatribu.com:8000/latribu.mp3");
        Radio Malena = new Radio("Malena", "89.1", "http://vivo.radioam750.com.ar/vivofm.mp3");
        Radio arpeggio = new Radio("Arpeggio", "89.5", "http://14073.live.streamtheworld.com/ARPEGGIOAAC");
        Radio radioConVos = new Radio("Radio Con Vos", "89.9", "http://server6.stweb.tv:1935/rcvos/live/chunklist.m3u8");
        Radio ESPN = new Radio("ESPN", "107.9", "http://apiradio.espn-la.com/64list.m3u8");
        Radio LaBoca = new Radio("La Boca", "90.1", "http://208.98.41.72:9304");
        Radio Metro = new Radio("Metro", "95.1", "http://mp3.metroaudio1.stream.avstreaming.net:7200/metro");

        radios.add(laTribu);
        radios.add(Malena);
        radios.add(arpeggio);
        radios.add(radioConVos);
        radios.add(ESPN);
        radios.add(LaBoca);
        radios.add(Metro);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerRadio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AdaptadorRadio adaptadorRadio = new AdaptadorRadio(radios, getContext());


        adaptadorRadio.setRadioClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    final String url = radios.get(position).getUrl();
                    if (!mediaPlayer.isPlaying() || !url.equals(radioEncendidaUrl)) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                                radioEncendidaUrl = url;
                                Toast.makeText(getActivity(), "Start", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        mediaPlayer.stop();
                        Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        recyclerView.setAdapter(adaptadorRadio);
        return view;
    }

}


