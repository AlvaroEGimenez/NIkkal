package com.digitalhouse.a0818moacn01_02.menuNavegacion.Radio_Online;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewNombre;
    TextView textViewEmisora;


    public MyViewHolder(View itemView, final ItemClickListener itemClickListener) {
        super(itemView);
        textViewNombre = itemView.findViewById(R.id.tvNombreradio);
        textViewEmisora = itemView.findViewById(R.id.tvSintonia);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posicion = getAdapterPosition();

                itemClickListener.onItemClick(posicion);

            }
        });

    }
}
