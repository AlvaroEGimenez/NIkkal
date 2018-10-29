package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;


import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digitalhouse.a0818moacn01_02.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment implements AdapatadorBusqueda.BusquedaInterface {

    private ArrayList<Busqueda> listaBusquedas = new ArrayList<>();
    private ImageButton imageButtonBusqueda;
    private EditText editTextBusqueda;
    private RecyclerView recyclerView;
    private AdapatadorBusqueda adapatadorBusqueda;
    private RequestQueue requestQueue;
    private String busqueda;
    private MediaPlayer mediaPlayer;
    private String applicationID = "302884";


    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);


        editTextBusqueda = view.findViewById(R.id.edittextBusqueda);
        editTextBusqueda.requestFocus();
        mostrarTeclado();


        imageButtonBusqueda = view.findViewById(R.id.imagebuttonBusqueda);
        requestQueue = Volley.newRequestQueue(getActivity());
        recyclerView = view.findViewById(R.id.rvBusqueda);
        adapatadorBusqueda = new AdapatadorBusqueda(listaBusquedas, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        mediaPlayer = new MediaPlayer();


        imageButtonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBusqueda.onEditorAction(EditorInfo.IME_ACTION_DONE);
                busqueda = editTextBusqueda.getText().toString();
                ocultarTeclado();
                listaBusquedas.clear();
                analizarJSON();
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
                    analizarJSON();
                    editTextBusqueda.setText("");
                }
                return false;
            }
        });
        adapatadorBusqueda.notifyDataSetChanged();
        return view;
    }

    private void analizarJSON() {

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

                            adapatadorBusqueda = new AdapatadorBusqueda(listaBusquedas, BuscarFragment.this);
                            recyclerView.setAdapter(adapatadorBusqueda);


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
    }


    @Override
    public void busquedaClick(Busqueda busqueda) {
        LinearLayout linearLayout = getActivity().findViewById(R.id.layoutPlayer);
        TextView textViewNombrePista = getActivity().findViewById(R.id.tvNombreReproductor);
        textViewNombrePista.setSelected(true);


        linearLayout.setVisibility(View.VISIBLE);
        String url = busqueda.getMp3();
        reproducirMp3(url, mediaPlayer);
        textViewNombrePista.setText(busqueda.getBusqueda() + " - " + busqueda.getArtista());
        textViewNombrePista.setTextColor(Color.parseColor("#FD9701"));


    }


    private void reproducirMp3(final String url, final MediaPlayer mediaPlayer) {
        final ImageView imageViewPlay = getActivity().findViewById(R.id.btnRepoductorPlay);
        final ImageView imageViewPause = getActivity().findViewById(R.id.btnReproductorPause);

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


