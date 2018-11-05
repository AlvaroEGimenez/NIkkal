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
import com.digitalhouse.a0818moacn01_02.model.RadioDeezer;

import java.util.List;

public class RadioAdapterRecyclerView extends RecyclerView.Adapter {

    private List<RadioDeezer> radioDeezerList;
    private Integer resources;
    private Activity activity;
    private RadioAdapterRecyclerView.AdapterInterface escuchador;

    public RadioAdapterRecyclerView(List<RadioDeezer> radioDeezerList, int resources, Activity activity, RadioAdapterRecyclerView.AdapterInterface escuchador) {
        this.radioDeezerList = radioDeezerList;
        this.resources = resources;
        this.activity = activity;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        RadioDeezer radioDeezer = radioDeezerList.get(posicion);
        RadioViewHolder radioViewHolder = (RadioViewHolder) holder;
        radioViewHolder.cargar(radioDeezer);
    }

    @Override
    public int getItemCount() {
        return radioDeezerList.size();
    }

    public interface AdapterInterface {
        void cambiarDeActividadRadio(RadioDeezer radioDeezer);
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenSugerenciaCardView;
        private TextView tituloCardView;

        public RadioViewHolder(@NonNull final View itemView) {
            super(itemView);

            imagenSugerenciaCardView = itemView.findViewById(R.id.imagenCategoria);
            tituloCardView = itemView.findViewById(R.id.tituloCategoria);

            imagenSugerenciaCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioDeezer radioDeezer = radioDeezerList.get(getAdapterPosition());
                    escuchador.cambiarDeActividadRadio(radioDeezer);
                }
            });
        }

        public void cargar(RadioDeezer radioDeezer) {
            tituloCardView.setText(radioDeezer.getTitle());
            cargarImagen(imagenSugerenciaCardView, radioDeezer.getPictureMedium());
        }
    }

    private void cargarImagen(ImageView imageView, String url) {
        Glide.with(activity).load(url).into(imageView);
    }
}
