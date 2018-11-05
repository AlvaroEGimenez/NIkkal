package com.digitalhouse.a0818moacn01_02.view.categorias;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitalhouse.a0818moacn01_02.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugerenciasFragment extends Fragment {

    public static final String KEY_IMAGEN_SUGERENCIA = "imagen";
    public static final String KEY_URL_PLAYLIST_SUGERENCIA = "urlPlaylist";
    public static final String KEY_NOMBRE_SUGERENCIA = "nombre";


    public SugerenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugerencias, container, false);

        ImageView imageViewSugerencia = view.findViewById(R.id.ivImagenSugerencia);
        TextView textViewNombre = view.findViewById(R.id.tvtituloSugerencia);


        Bundle bundle = getArguments();

        Glide.with(getContext()).load(bundle.getString(KEY_IMAGEN_SUGERENCIA)).into(imageViewSugerencia);
        textViewNombre.setText(bundle.getString(KEY_NOMBRE_SUGERENCIA));






        return view;
    }

}
