package com.digitalhouse.a0818moacn01_02.menuNavegacion.Radio_Online;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitalhouse.a0818moacn01_02.R;

import java.util.List;

public class AdaptadorRadio extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Radio> radios;
    private Layout layout;
    private ItemClickListener radioClickListener;

    public AdaptadorRadio(List<Radio> emisoras) {
        this.radios = emisoras;
    }

    public void setRadioClickListener(ItemClickListener radioClickListener) {
        this.radioClickListener = radioClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.from(context).inflate(R.layout.recycle_view_radio, parent, false);
        return new MyViewHolder(v, radioClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textViewNombre.setText(radios.get(position).getNombre());
        holder.textViewEmisora.setText(radios.get(position).getSintonia());

    }


    @Override
    public int getItemCount() {
        return radios.size();
    }

}
