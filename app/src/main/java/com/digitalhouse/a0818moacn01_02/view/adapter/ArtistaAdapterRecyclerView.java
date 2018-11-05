package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.ArtistDeezer;

import java.util.List;

public class ArtistaAdapterRecyclerView  extends RecyclerView.Adapter {

    private List<ArtistDeezer> artistas;
    private Integer resources;
    private Activity activity;
    private ArtistaAdapterInterface escuchador;

    public ArtistaAdapterRecyclerView( List<ArtistDeezer> artistas, int resources, Activity activity, ArtistaAdapterInterface escuchador) {
        this.artistas = artistas;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posicion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new ArtistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        ArtistDeezer artista = artistas.get(posicion);
        ArtistaViewHolder artistaViewHolder = (ArtistaViewHolder) holder;
        artistaViewHolder.cargar(artista);
    }

    @Override
    public int getItemCount() {
        return artistas.size();
    }

    public interface ArtistaAdapterInterface {
        void cambiarDeActividad(ArtistDeezer artista);
    }


    public class ArtistaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenArtistaCardView;
        private TextView nombreArtistaCardView;

        public ArtistaViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenArtistaCardView = itemView.findViewById(R.id.imagenArtistaCardView);
            nombreArtistaCardView = itemView.findViewById(R.id.nombreArtistaCardView);

            imagenArtistaCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistDeezer artista = artistas.get(getAdapterPosition());
                    escuchador.cambiarDeActividad(artista);
                }
            });
        }

        public void cargar(ArtistDeezer artista) {
            nombreArtistaCardView.setText(artista.getName());
            cargarImagen(imagenArtistaCardView, artista.getPictureMedium());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }
}
