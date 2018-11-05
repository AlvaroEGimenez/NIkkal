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
import com.digitalhouse.a0818moacn01_02.model.Genre;

import java.util.List;

public class CategoriaAdapterRecyclerView extends RecyclerView.Adapter {
    private List<Genre> albunes;
    private Integer resources;
    private Activity activity;
    private AdapterInterface escuchador;

    public CategoriaAdapterRecyclerView(List<Genre> albunes, int resources, Activity activity, AdapterInterface escuchador) {
        this.albunes = albunes;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        Genre Genre = albunes.get(posicion);
        GenreViewHolder GenreViewHolder = (GenreViewHolder) holder;
        GenreViewHolder.cargar(Genre);
    }

    @Override
    public int getItemCount() {
        return albunes.size();
    }

    public interface AdapterInterface {
        void cambiarDeActividadGenero(Genre Genre);
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenCategoriaCardView;
        private TextView tituloCardView;

        public GenreViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenCategoriaCardView = itemView.findViewById(R.id.imagenCategoria);
            tituloCardView = itemView.findViewById(R.id.tituloCategoria);

            imagenCategoriaCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Genre Genre = albunes.get(getAdapterPosition());
                    escuchador.cambiarDeActividadGenero(Genre);
                }
            });
        }

        public void cargar(Genre Genre) {
            tituloCardView.setText(Genre.getName());
            cargarImagen(imagenCategoriaCardView, Genre.getPictureMedium());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }

}
