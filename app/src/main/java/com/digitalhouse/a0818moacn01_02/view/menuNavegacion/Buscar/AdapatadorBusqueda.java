package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.List;

public class AdapatadorBusqueda extends RecyclerView.Adapter{

    List<Track> listaBusqueda;
    BusquedaInterface busquedaInterface;

    public AdapatadorBusqueda(List<Track> listaBusqueda, BusquedaInterface busquedaInterface) {
        this.listaBusqueda = listaBusqueda;
        this.busquedaInterface = busquedaInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.modelo_busqueda, viewGroup,false);
        ViewHolderBusqueda viewHolderBusqueda = new ViewHolderBusqueda(view);
        return viewHolderBusqueda;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int posicion) {
        ViewHolderBusqueda viewHolderBusqueda = (ViewHolderBusqueda) viewHolder;
        Track trackBusqueda = listaBusqueda.get(posicion);
        viewHolderBusqueda.bind(trackBusqueda);



    }

    @Override
    public int getItemCount() {
        return listaBusqueda.size();
    }

    public interface BusquedaInterface{
        void busquedaClick (Track trackBusqueda, Integer posicion);
    }


    public class ViewHolderBusqueda extends RecyclerView.ViewHolder {
        private TextView textViewBusqueda;


        public ViewHolderBusqueda(@NonNull View itemView) {
            super(itemView);
            textViewBusqueda = itemView.findViewById(R.id.tvBusquedaReciente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track busqueda = listaBusqueda.get(getAdapterPosition());
                    busquedaInterface.busquedaClick(busqueda,getAdapterPosition());
                }
            });

        }

        public void  bind (Track track){
            textViewBusqueda.setText(track.getTitle()+" - "+track.getArtist().getName());
        }
    }
}