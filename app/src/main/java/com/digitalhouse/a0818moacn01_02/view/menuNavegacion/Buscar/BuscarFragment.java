package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.MediaPlayerNikkal;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.SearchControlller;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment implements AdapatadorBusqueda.BusquedaInterface {


    private EditText editTextBusqueda;
    private RecyclerView recyclerViewTracks;
    private AdapatadorBusqueda adapatadorBusqueda;
    private MediaPlayer mediaPlayer;
    private List<Track> trackListSeach = new ArrayList<>();
    private List<ArtistDeezer> artistDeezerList = new ArrayList<>();
    private RelativeLayout relativeLayoutBusqueda;
    private RecyclerView recyclerViewArtistaBusqueda;
    private View view;

    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        this.view = view;

        relativeLayoutBusqueda = view.findViewById(R.id.relativeLayoutBusqueda);

        editTextBusqueda = view.findViewById(R.id.edittextBusqueda);
        editTextBusqueda.requestFocus();
        mostrarTeclado();


        ImageButton imageButtonBusqueda = view.findViewById(R.id.imagebuttonBusqueda);


        recyclerViewTracks = view.findViewById(R.id.rvBusquedaTracks);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        recyclerViewTracks.setLayoutManager(linearLayoutManager);
        recyclerViewTracks.setHasFixedSize(true);


        MainActivity mainActivity = (MainActivity) getActivity();
        mediaPlayer = mainActivity.getMediaPlayer();

        //circleImageView = view.findViewById(R.id.civImagenArtista);
        //textViewArtista = view.findViewById(R.id.tvBusquedaArtista);

        imageButtonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarBusqueda();

            }
        });

        editTextBusqueda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    realizarBusqueda();
                }
                return false;
            }
        });

        return view;
    }

    private void realizarBusqueda() {
        editTextBusqueda.onEditorAction(EditorInfo.IME_ACTION_DONE);
        String busqueda = editTextBusqueda.getText().toString();

        if (!busqueda.isEmpty()) {
            ocultarTeclado();
            artistDeezerList.clear();
            SearchControlller searchControlller = new SearchControlller();
            searchControlller.getSearch(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    trackListSeach = resultado;

                    adapatadorBusqueda = new AdapatadorBusqueda(trackListSeach, BuscarFragment.this);
                    recyclerViewTracks.setAdapter(adapatadorBusqueda);

                    for (Track track : trackListSeach) {
                        artistDeezerList.add(track.getArtist());
                    }

                    List<ArtistDeezer> artistDeezerListFiltrada = new ArrayList<>();

                    for (ArtistDeezer artistDeezer : artistDeezerList) {
                        if (!artistDeezerListFiltrada.contains(artistDeezerList)) {
                            artistDeezerListFiltrada.add(artistDeezer);

                        }
                    }
                    recyclerViewArtistaBusqueda = view.findViewById(R.id.rvArtistaBusqueda);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    AdaptadorBusquedaArtistas adaptadorBusquedaArtistas = new AdaptadorBusquedaArtistas(artistDeezerListFiltrada);
                    recyclerViewArtistaBusqueda.setHasFixedSize(true);
                    recyclerViewArtistaBusqueda.setLayoutManager(linearLayoutManager);
                    recyclerViewArtistaBusqueda.setAdapter(adaptadorBusquedaArtistas);

                    relativeLayoutBusqueda.setVisibility(View.VISIBLE);
                }
            }, getContext(), busqueda);
            editTextBusqueda.setText("");
        } else {

            Toast.makeText(getActivity(), R.string.busqueda_vacia, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void busquedaClick(Track track, final Integer posicion) {
        LinearLayout linearLayout = getActivity().findViewById(R.id.layoutPlayer);
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);

        linearLayout.setVisibility(View.VISIBLE);
        String url = track.getPreview();
        MainActivity parent = (MainActivity) getActivity();
        textViewNombrePista.setSelected(true);
        textViewNombrePista.setText(track.getArtist().getName() + " - " + track.getTitle());
        parent.visibilidadReproductor(true);
        parent.getBottomNavigation().setVisibility(View.VISIBLE);
        ReproducirMp3 reproducirMp3 = parent.getReproducirMp3();
        MediaPlayerNikkal.getInstance().getMediaPlayer().stop();
        reproducirMp3.reproducirMp3(trackListSeach, posicion, parent);

        textViewNombrePista.setSelected(true);
    }


    private void ocultarTeclado() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void mostrarTeclado() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}



