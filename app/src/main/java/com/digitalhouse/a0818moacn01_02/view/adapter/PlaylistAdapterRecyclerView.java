package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.List;

public class PlaylistAdapterRecyclerView extends RecyclerView.Adapter {

    private List<Track> trackList;
    private playlistClick playlistClick;

    public PlaylistAdapterRecyclerView(List<Track> trackList, playlistClick playlistClick) {
        this.trackList = trackList;
        this.playlistClick = playlistClick;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.modelo_playlist,parent,false);
        TracklistViewHolder tracklistViewHolder = new TracklistViewHolder(view);
        return tracklistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Track track = trackList.get(position);
        TracklistViewHolder tracklistViewHolder = (TracklistViewHolder) holder;
        tracklistViewHolder.bind(track);

    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class TracklistViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewArtista;
        private TextView textViewAlbum;

        public TracklistViewHolder(View itemView) {
            super(itemView);
            textViewArtista = itemView.findViewById(R.id.tvArtistaPlaylist);
            textViewNombre = itemView.findViewById(R.id.tvTituloPlaylist);
            textViewAlbum = itemView.findViewById(R.id.tvAlbumPlaylist);


        }

        public void bind (final Track track){
            textViewNombre.setText(track.getTitle());
            textViewArtista.setText(track.getArtist().getName()+" ° ");
            textViewAlbum.setText(track.getAlbum().getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playlistClick.OnClickTrack(track, getAdapterPosition());
                }
            });

        }
    }

    public  interface playlistClick{
        void OnClickTrack(Track track, Integer posicion);
    }
}
