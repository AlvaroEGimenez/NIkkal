package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;


import android.content.Context;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.Utils.ReproducirMp3;
import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.controller.SearchControlller;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;
import com.digitalhouse.a0818moacn01_02.model.Busqueda;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.MainActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment implements AdapatadorBusqueda.BusquedaInterface {

    private ArrayList<Busqueda> listaBusquedas = new ArrayList<>();
    private ImageButton imageButtonBusqueda;
    private EditText editTextBusqueda;
    private ImageView circleImageView;
    private TextView textViewArtista;
    private RecyclerView recyclerViewTracks;
    private AdapatadorBusqueda adapatadorBusqueda;
    private String busqueda;
    private MediaPlayer mediaPlayer;
    private List<Track> trackListSeach = new ArrayList<>();
    private ArtistDeezer artistDeezer;
    private RelativeLayout relativeLayoutBusqueda;


    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        relativeLayoutBusqueda = view.findViewById(R.id.relativeLayoutBusqueda);

        editTextBusqueda = view.findViewById(R.id.edittextBusqueda);
        editTextBusqueda.requestFocus();
        mostrarTeclado();


        imageButtonBusqueda = view.findViewById(R.id.imagebuttonBusqueda);


        recyclerViewTracks = view.findViewById(R.id.rvBusquedaTracks);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        recyclerViewTracks.setLayoutManager(linearLayoutManager);
        recyclerViewTracks.setHasFixedSize(true);


        MainActivity mainActivity = (MainActivity) getActivity();
        mediaPlayer = mainActivity.getMediaPlayer();

        circleImageView = view.findViewById(R.id.civImagenArtista);
        textViewArtista = view.findViewById(R.id.tvBusquedaArtista);

        imageButtonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBusqueda.onEditorAction(EditorInfo.IME_ACTION_DONE);
                busqueda = editTextBusqueda.getText().toString();
                ocultarTeclado();
                listaBusquedas.clear();

                SearchControlller searchControlller = new SearchControlller();
                searchControlller.getSearch(new ResultListener<List<Track>>() {
                    @Override
                    public void finish(List<Track> resultado) {
                        trackListSeach = resultado;
                        artistDeezer = trackListSeach.get(0).getArtist();
                        adapatadorBusqueda = new AdapatadorBusqueda(trackListSeach, BuscarFragment.this);
                        recyclerViewTracks.setAdapter(adapatadorBusqueda);
                        Glide.with(getContext()).load(artistDeezer.getPictureMedium()).into(circleImageView);
                        textViewArtista.setText(artistDeezer.getName());
                        relativeLayoutBusqueda.setVisibility(View.VISIBLE);
                    }
                }, getContext(), busqueda);
                editTextBusqueda.setText("");
            }
        });

        editTextBusqueda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    busqueda = editTextBusqueda.getText().toString();
                    ocultarTeclado();
                    listaBusquedas.clear();


                    SearchControlller searchControlller = new SearchControlller();
                    searchControlller.getSearch(new ResultListener<List<Track>>() {
                        @Override
                        public void finish(List<Track> resultado) {
                            trackListSeach = resultado;
                            adapatadorBusqueda = new AdapatadorBusqueda(trackListSeach, BuscarFragment.this);
                            recyclerViewTracks.setAdapter(adapatadorBusqueda);
                        }
                    }, getContext(), busqueda);
                    editTextBusqueda.setText("");
                }
                return false;
            }
        });


        return view;
    }


    /*private void analizarJSON() {

        String url = "https://api.deezer.com/search?q=" + busqueda;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String titulo = hit.getString("title_short");
                                String urlMp3 = hit.getString("preview");
                                Object artista = hit.getJSONObject("artist");
                                String nombreArtista = ((JSONObject) artista).getString("name");
                                listaBusquedas.add(new Busqueda(titulo, urlMp3, nombreArtista));
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(request);
    }*/


    @Override
    public void busquedaClick(Track track) {
        LinearLayout linearLayout = getActivity().findViewById(R.id.layoutPlayer);
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);


        linearLayout.setVisibility(View.VISIBLE);
        String url = track.getPreview();

        ReproducirMp3 reproducirMp3 = new ReproducirMp3();
        reproducirMp3.reproducirMp3(url, mediaPlayer, getActivity());

        textViewNombrePista.setText(track.getTitle() + " - " + track.getArtist().getName());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));
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



