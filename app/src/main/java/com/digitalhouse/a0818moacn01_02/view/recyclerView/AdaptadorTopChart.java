package com.digitalhouse.a0818moacn01_02.view.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalhouse.a0818moacn01_02.R;
import com.digitalhouse.a0818moacn01_02.model.TopChart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorTopChart extends BaseAdapter {
    private List<TopChart> topChartList;
    private Context context;

    public AdaptadorTopChart(List<TopChart> topChartList, Context context) {
        this.topChartList = topChartList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null){
            rootView = LayoutInflater.from(context).inflate(R.layout.layout_item,null);

            TextView nombre = rootView.findViewById(R.id.labelTopChart);
            ImageView imagen = rootView.findViewById(R.id.imagenTopChart);

            Picasso.get().load(topChartList.get(position).getUrlImagen())
                    .into(imagen);

            nombre.setText(topChartList.get(position).getNombreTrack());
        }

        return rootView;
    }
}
