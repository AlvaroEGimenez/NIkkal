package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.Track;

import java.util.List;

public class AdaptadorTopChartDeezer extends BaseAdapter {
    private List<Track> topChartList;
    private Context context;
    private onItemClickTopChartDeezer onItemClickTopChart;

    public AdaptadorTopChartDeezer(List<Track> topChartList, Context context, onItemClickTopChartDeezer onItemClickTopChart) {
        this.topChartList = topChartList;
        this.context = context;
        this.onItemClickTopChart = onItemClickTopChart;
    }

    public void setTopChartList(List<Track> topChartList) {
        this.topChartList = topChartList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return topChartList.size();
    }

    @Override
    public Object getItem(int position) {
        return topChartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null){
            rootView = LayoutInflater.from(context).inflate(R.layout.layout_item,null);

            TextView nombre = rootView.findViewById(R.id.labelTopChart);
            ImageView imagen = rootView.findViewById(R.id.imagenTopChart);

            Glide.with(context).load(topChartList.get(position).getAlbum().getCoverMedium()).into(imagen);

            nombre.setText(topChartList.get(position).getArtist().getName());

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickTopChart.onClickTopChartDeezer(topChartList.get(position));
                }
            });

        }

        return rootView;
    }

    public interface onItemClickTopChartDeezer{
        void onClickTopChartDeezer(Track topChartLocalDeezer);
    }
}