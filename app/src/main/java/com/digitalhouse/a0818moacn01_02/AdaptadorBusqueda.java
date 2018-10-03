package com.digitalhouse.a0818moacn01_02;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.BusquedaViewHolder> {


    private ArrayList<String> busquedas = new ArrayList<>();
    private Integer resources;

    public AdaptadorBusqueda(ArrayList<String> busquedas) {

        this.busquedas = busquedas;
    }

    @NonNull
    @Override
    public BusquedaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resources, viewGroup, false);
        return new BusquedaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusquedaViewHolder busquedaViewHolder, int i) {

    }


    @Override
    public int getItemCount() {
        return busquedas.size();
    }


    public class BusquedaViewHolder extends RecyclerView.ViewHolder {
        EditText editTextBusqueda;
        ImageButton imageButtonBusqueda;

        public BusquedaViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextBusqueda = itemView.findViewById(R.id.edittextBusqueda);
            imageButtonBusqueda = itemView.findViewById(R.id.imagebuttonBusqueda);
        }
    }
}
