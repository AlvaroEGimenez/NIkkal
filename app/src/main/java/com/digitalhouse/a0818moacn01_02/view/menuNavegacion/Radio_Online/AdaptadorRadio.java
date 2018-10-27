package com.digitalhouse.a0818moacn01_02.view.menuNavegacion.Radio_Online;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;

import java.util.ArrayList;

public class AdaptadorRadio extends RecyclerView.Adapter {

    private ArrayList<Radio> listaRadios;
    private RadioInterface radioInterface;

    public AdaptadorRadio(ArrayList<Radio> listaRadios, RadioInterface radioInterface) {
        this.listaRadios = listaRadios;
        this.radioInterface = radioInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.recycle_view_radio,viewGroup,false);
        return new ViewHolderRadio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int posicion) {
        ViewHolderRadio viewHolderRadio = (ViewHolderRadio) viewHolder;
        Radio radio = listaRadios.get(posicion);
        viewHolderRadio.bind(radio);

    }

    @Override
    public int getItemCount() {
        return listaRadios.size();
    }

    public interface RadioInterface {
        void OnRadioClick (Radio radio);
    }


    public class ViewHolderRadio extends RecyclerView.ViewHolder {
        TextView textViewNombreRadio;
        TextView textViewSintonia;

        public ViewHolderRadio(@NonNull View itemView) {
            super(itemView);

            textViewNombreRadio = itemView.findViewById(R.id.tvNombreradio);
            textViewSintonia = itemView.findViewById(R.id.tvSintonia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Radio radio = listaRadios.get(getAdapterPosition());
                    radioInterface.OnRadioClick(radio);
                }
            });

        }

        public void  bind (Radio radio){
            textViewNombreRadio.setText(radio.getNombre());
            textViewSintonia.setText(radio.getSintonia());
        }
    }
}
