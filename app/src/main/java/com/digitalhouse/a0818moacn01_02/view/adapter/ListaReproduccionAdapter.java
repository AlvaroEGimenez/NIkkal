package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.ListaReproduccion;

import java.util.List;

public class ListaReproduccionAdapter extends RecyclerView.Adapter {

    private List<ListaReproduccion> listaReproduccionList;
    private Integer resources;
    private ListaReproduccionAdapterInterface escuchador;

    public ListaReproduccionAdapter(List<ListaReproduccion> listaReproduccionList, int resources, ListaReproduccionAdapterInterface escuchador) {
        this.listaReproduccionList = listaReproduccionList;
        this.resources = resources;
        this.escuchador = escuchador;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posicion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resources, parent, false);
        return new ListaReproduccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int posicion) {
        ListaReproduccion listaReproduccion = listaReproduccionList.get(posicion);
        ListaReproduccionViewHolder listaReproduccionViewHolder = (ListaReproduccionViewHolder) holder;
        listaReproduccionViewHolder.cargar(listaReproduccion);
    }

    @Override
    public int getItemCount() {
        return listaReproduccionList.size();
    }

    public interface ListaReproduccionAdapterInterface {
        void seleccionLista(ListaReproduccion listaReproduccion);
    }

    public class ListaReproduccionViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreListaReproduccion;

        public ListaReproduccionViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombreListaReproduccion = itemView.findViewById(R.id.nombreListaReproduccion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListaReproduccion listaReproduccion = listaReproduccionList.get(getAdapterPosition());
                    escuchador.seleccionLista(listaReproduccion);
                }
            });
        }

        public void cargar(ListaReproduccion listaReproduccion) {
            nombreListaReproduccion.setText(listaReproduccion.getNombre());
        }
    }

    public void setListaReproduccionList(List<ListaReproduccion> listaReproduccionList) {
        this.listaReproduccionList = listaReproduccionList;
        notifyDataSetChanged();
    }
}
