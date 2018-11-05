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
import com.digitalhouse.a0818moacn01_02.model.AlbumDeezer;

import java.util.List;

public class AlbumAdapterRecyclerView extends RecyclerView.Adapter {
    private List<AlbumDeezer> albunDeezer;
    private Integer resources;
    private Activity activity;
    private AlbumAdapterInterface escuchador;


    public AlbumAdapterRecyclerView(List<AlbumDeezer> albunes, int resources, Activity activity, AlbumAdapterInterface escuchador) {
        this.albunDeezer = albunes;
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
        AlbumDeezer albumDeezer = albunDeezer.get(posicion);
        AlbumViewHolder albumViewHolder = (AlbumViewHolder) holder;
        albumViewHolder.cargar(albumDeezer);
    }

    @Override
    public int getItemCount() {
        return albunDeezer.size();
    }

    public interface AlbumAdapterInterface {
        void cambiarDeActividad(AlbumDeezer album);
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenAlbumCardView;
        private TextView tituloCardView;
        private TextView cantidadTracks;

        public AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenAlbumCardView = itemView.findViewById(R.id.imagenAlbum);
            tituloCardView = itemView.findViewById(R.id.tituloAlbum);
            cantidadTracks = itemView.findViewById(R.id.cantidadTracks);

            imagenAlbumCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlbumDeezer album = albunDeezer.get(getAdapterPosition());
                    escuchador.cambiarDeActividad(album);
                }
            });
        }

        public void cargar(AlbumDeezer album) {
            tituloCardView.setText(album.getTitle());
            cantidadTracks.setText(String.valueOf(album.getRating()));
            cargarImagen(imagenAlbumCardView, album.getCoverMedium());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }
}
