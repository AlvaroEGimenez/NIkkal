package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.content.Context;
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

public class MasEscuchadosRecyclerView extends RecyclerView.Adapter {
    private List<AlbumDeezer> artistDeezerList;
    private Context context;
    private OnclickMasEscuchados onclickMasEscuchados;

    public MasEscuchadosRecyclerView(List<AlbumDeezer> artistDeezerList, OnclickMasEscuchados onclickMasEscuchados) {
        this.artistDeezerList = artistDeezerList;
        this.onclickMasEscuchados = onclickMasEscuchados;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_mas_escuchados, parent, false);
        TopChartAlbumsViewHolder topChartAlbumsViewHolder = new TopChartAlbumsViewHolder(view);
        return topChartAlbumsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlbumDeezer albumDeezer = artistDeezerList.get(position);
        TopChartAlbumsViewHolder topChartAlbumsViewHolder = (TopChartAlbumsViewHolder) holder;
        topChartAlbumsViewHolder.bind(albumDeezer);
    }

    @Override
    public int getItemCount() {
        return artistDeezerList.size();
    }


    public class TopChartAlbumsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMasEscuchados;
        ImageView imageViewMasEscuchados;

        public TopChartAlbumsViewHolder(View itemView) {
            super(itemView);
            textViewMasEscuchados = itemView.findViewById(R.id.tituloMasEscuchados);
            imageViewMasEscuchados = itemView.findViewById(R.id.imagenMasEscuchados);

            imageViewMasEscuchados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickMasEscuchados.onClickmasescuchados(artistDeezerList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(AlbumDeezer albumDeezer) {
            textViewMasEscuchados.setText(albumDeezer.getTitle());
            Glide.with(context).load(albumDeezer.getCoverMedium()).into(imageViewMasEscuchados);
        }
    }

    public interface OnclickMasEscuchados {
        void onClickmasescuchados(AlbumDeezer albumDeezer);
    }
}
