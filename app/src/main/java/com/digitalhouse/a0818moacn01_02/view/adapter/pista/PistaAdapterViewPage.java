package com.digitalhouse.a0818moacn01_02.view.adapter.pista;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.List;

public class PistaAdapterViewPage extends PagerAdapter {
    private List<Track> pistas;
    private Context context;
    private LayoutInflater layoutInflater;
    private PistaViewPageInterface escuchador;
    private Boolean primerInstancia;

    public PistaAdapterViewPage(List<Track> pistas, Context context, PistaViewPageInterface escuchador) {
        this.pistas = pistas;
        this.context = context;
        layoutInflater = ((Activity) context).getLayoutInflater();
        this.escuchador = escuchador;
        primerInstancia = Boolean.TRUE;
    }

    @Override
    public int getCount() {
        return pistas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (View) o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.pista_view_page, container, false);
        TextView textView = view.findViewById(R.id.tvTitulo_PistaViewPage);
        TextView textViewArtista = view.findViewById(R.id.Artista_PistaViewPage);
        textView.setSelected(true);

        final ImageView favoritoPista = view.findViewById(R.id.imgFavoritoViewPage);
        final ImageButton btnPistaAnterior = view.findViewById(R.id.ic_play_antrior_pista);
        final ImageButton btnPistaSiguiente = view.findViewById(R.id.ic_play_siguiente_pista);
        final ImageButton btnPlay = view.findViewById(R.id.ic_play_pista);
        final ImageButton btnPause = view.findViewById(R.id.ic_pause_pista);

        container.addView(view);
        final Track pista = pistas.get(position);
        favoritoPista.setImageResource(pista.getFavorito() ? R.drawable.ic_favorite_seleccionado : R.drawable.ic_favorite_no_seleccion);

        favoritoPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchador.favoritoListenerPista(position, favoritoPista);
            }
        });

        textView.setText(pista.getTitle());
        textViewArtista.setText(pista.getArtist().getName());

        final Integer posicion = position;
        btnPistaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchador.pistaAnterior(posicion - 1);
            }
        });

        btnPistaSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchador.pistaSiguiente(posicion + 1);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(posicion, btnPlay, btnPause);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause(posicion, btnPlay, btnPause);
            }
        });

        if (primerInstancia) {
            play(posicion, btnPlay, btnPause);
            primerInstancia = Boolean.FALSE;
        }

        return view;
    }


    public interface PistaViewPageInterface {
        void pistaAnterior(Integer position);

        void pistaSiguiente(Integer position);

        void pistaPlay(Integer posicion);

        void pistaPause();

        void favoritoListenerPista(Integer position, ImageView favoritoPista);
    }


    private void play(Integer posicion, ImageButton btnPlay, ImageView btnPause) {
        btnPlay.setVisibility(View.INVISIBLE);
        btnPause.setVisibility(View.VISIBLE);
        escuchador.pistaPlay(posicion);
    }

    private void pause(Integer posicion, ImageButton btnPlay, ImageView btnPause) {
        btnPlay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.INVISIBLE);
        escuchador.pistaPause();

    }

}
