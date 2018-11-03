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

public class AlbumAdapterRecyclerView extends RecyclerView.Adapter {
    private List<ArtistDeezer> albunes;
    private Integer resources;
    private Activity activity;
    private AlbumAdapterInterface escuchador;

    public AlbumAdapterRecyclerView(List<ArtistDeezer> albunes, int resources, Activity activity, AlbumAdapterInterface escuchador) {
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
        ArtistDeezer albumRock = albunes.get(posicion);
        AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
        albumViewHolder.cargar(albumRock);
    }

    @Override
    public int getItemCount() {
        return albunes.size();
    }

    public interface AlbumAdapterInterface {
        void cambiarDeActividad(ArtistDeezer album);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenAlbumCardView;
        private TextView tituloCardView;
        private TextView textViewFanAlbum;

        public AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenAlbumCardView = itemView.findViewById(R.id.imagenAlbum);
            tituloCardView = itemView.findViewById(R.id.tituloAlbum);
            textViewFanAlbum = itemView.findViewById(R.id.fanAlbum);

            imagenAlbumCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArtistDeezer album = albunes.get(getAdapterPosition());
                    escuchador.cambiarDeActividad(album);
                }
            });
        }

        public void cargar(ArtistDeezer album) {
            tituloCardView.setText(album.getName());
            //textViewFanAlbum.setText(album.getNbFan().toString());
            cargarImagen(imagenAlbumCardView, album.getPictureMedium());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }
}
