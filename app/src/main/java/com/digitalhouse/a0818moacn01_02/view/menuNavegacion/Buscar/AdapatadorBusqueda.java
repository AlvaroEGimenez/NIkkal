package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Buscar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Busqueda;

import java.util.ArrayList;

public class AdapatadorBusqueda extends RecyclerView.Adapter{

    ArrayList<Busqueda> listaBusqueda;
    BusquedaInterface busquedaInterface;

    public AdapatadorBusqueda(ArrayList<Busqueda> listaBusqueda, BusquedaInterface busquedaInterface) {
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
        Busqueda busqueda = listaBusqueda.get(posicion);
        viewHolderBusqueda.bind(busqueda);



    }

    @Override
    public int getItemCount() {
        return listaBusqueda.size();
    }

    public interface BusquedaInterface{
        void busquedaClick (Busqueda busqueda);
    }


    public class ViewHolderBusqueda extends RecyclerView.ViewHolder {
        private TextView textViewBusqueda;


        public ViewHolderBusqueda(@NonNull View itemView) {
            super(itemView);
            textViewBusqueda = itemView.findViewById(R.id.tvBusquedaReciente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Busqueda busqueda = listaBusqueda.get(getAdapterPosition());
                    busquedaInterface.busquedaClick(busqueda);
                }
            });

        }

        public void  bind (Busqueda busqueda){
            textViewBusqueda.setText(busqueda.getBusqueda()+" - "+busqueda.getArtista());
        }
    }
}