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
import com.digitalhouse.a0818moacn01_02.model.Album;

import java.util.ArrayList;
import java.util.List;

public class CategoriaAdapterRecyclerView extends RecyclerView.Adapter {
    private List<Album> albunes;
    private Integer resources;
    private Activity activity;
    private AdapterInterface escuchador;

    public CategoriaAdapterRecyclerView(ArrayList<Album> albunes, int resources, Activity activity, AdapterInterface escuchador) {
        this.albunes = albunes;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        Album album = albunes.get(posicion);
        AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
        albumViewHolder.cargar(album);
    }

    @Override
    public int getItemCount() {
        return albunes.size();
    }

    public interface AdapterInterface {
        void cambiarDeActividad(Album album);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenCategoriaCardView;
        private TextView tituloCardView;

        public AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenCategoriaCardView = itemView.findViewById(R.id.imagenCategoria);
            tituloCardView = itemView.findViewById(R.id.tituloCategoria);

            imagenCategoriaCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Album album = albunes.get(getAdapterPosition());
                    escuchador.cambiarDeActividad(album);
                }
            });
        }

        public void cargar(Album album) {
            tituloCardView.setText(album.getNombre());
            cargarImagen(imagenCategoriaCardView, album.getImagen());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }

}