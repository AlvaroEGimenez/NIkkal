package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.RadioPlayer;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.TrackListController;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.ReproductorActivity;
import com.digitalhouse.a0818moacn01_02.view.adapter.PlaylistAdapterRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugerenciasFragment extends Fragment implements PlaylistAdapterRecyclerView.playlistClick {

    public static final String KEY_IMAGEN_SUGERENCIA = "imagen";
    public static final String KEY_ID_PLAYLIST_SUGERENCIA = "urlPlaylist";
    public static final String KEY_NOMBRE_SUGERENCIA = "nombre";

    private RadioPlayer radioPlayer;
    private TrackPlayer trackPlayer;
    private Integer id;
    private List<Track> trackList;


    public SugerenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugerencias, container, false);

        ImageView imageViewSugerencia = view.findViewById(R.id.ivImagenSugerencia);

        String applicationID = "54587f8009fc0747c9f2eacef47d35f4";
        final DeezerConnect deezerConnect = new DeezerConnect(getContext(), applicationID);

        radioPlayer = null;
        try {
            radioPlayer = new RadioPlayer(getActivity().getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
        } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
            tooManyPlayersExceptions.printStackTrace();
        } catch (DeezerError deezerError) {
            deezerError.printStackTrace();
        }

        try {
            trackPlayer = new TrackPlayer(getActivity().getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
        } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
            tooManyPlayersExceptions.printStackTrace();
        } catch (DeezerError deezerError) {
            deezerError.printStackTrace();
        }

        Bundle bundle = getArguments();

        Glide.with(getContext()).load(bundle.getString(KEY_IMAGEN_SUGERENCIA)).into(imageViewSugerencia);
        String playlistId = bundle.getString(KEY_ID_PLAYLIST_SUGERENCIA);
        id = Integer.parseInt(playlistId);

        Toolbar toolbarSugerencias = view.findViewById(R.id.toolbarSugerencias);
        toolbarSugerencias.setTitle(bundle.getString(KEY_NOMBRE_SUGERENCIA));

        RecyclerView recyclerViewTrackList = view.findViewById(R.id.rvTracksSugerencias);
        recyclerViewTrackList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerViewTrackList.setLayoutManager(layoutManager);
        final PlaylistAdapterRecyclerView playlistAdapterRecyclerView = new PlaylistAdapterRecyclerView(new ArrayList<Track>(), this);
        recyclerViewTrackList.setAdapter(playlistAdapterRecyclerView);

        TrackListController trackListController = new TrackListController();
        trackListController.getTraksList(new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                trackList = resultado;
                playlistAdapterRecyclerView.setTrackList(resultado);
            }
        }, getContext(), id);


        return view;
    }

    @Override
    public void OnClickTrack(final Track track, final Integer posicion) {

        final ImageView imageViewPlay = getActivity().findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = getActivity().findViewById(R.id.btnReproductorPause);

        TextView textViewNombreTrack = getActivity().findViewById(R.id.tvNombreReproductor);
        ImageView imageViewExpandir = getActivity().findViewById(R.id.btnActivityReproductor);
        LinearLayout linearLayout = getActivity().findViewById(R.id.layoutPlayer);


        linearLayout.setVisibility(View.VISIBLE);
        textViewNombreTrack.setText(track.getTitle() + " ° " + track.getArtist().getName());


        imageViewPause.setVisibility(View.VISIBLE);
        imageViewPlay.setVisibility(View.INVISIBLE);
        trackPlayer.playTrack(track.getId());
        imageViewPause.setVisibility(View.VISIBLE);
        imageViewPlay.setVisibility(View.INVISIBLE);


        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayer.play();
                imageViewPause.setVisibility(View.VISIBLE);
                imageViewPlay.setVisibility(View.INVISIBLE);

            }
        });

        imageViewPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackPlayer.pause();
                imageViewPause.setVisibility(View.INVISIBLE);
                imageViewPlay.setVisibility(View.VISIBLE);

            }
        });
        imageViewExpandir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReproductorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(ReproductorActivity.KEY_POSICION,posicion);
                bundle.putSerializable(ReproductorActivity.KEY_OBJETO, (Serializable) trackList);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
}
