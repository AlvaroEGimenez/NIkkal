package com.digitalhouse.a0818moacn01_02.view.categorias;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.digitalhouse.a0818moacn01_02.R;

public class PistaViewPageFragment extends Fragment {

    public PistaViewPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pista_view_page, container, false);
        view.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return view;
    }

}
