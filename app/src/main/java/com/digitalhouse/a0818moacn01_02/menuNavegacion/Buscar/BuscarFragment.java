package com.digitalhouse.a0818moacn01_02.menuNavegacion.Buscar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.digitalhouse.a0818moacn01_02.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarFragment extends Fragment {

    ListView listView;
    ArrayList<String> busquedas= new ArrayList<>();
    ImageButton imageButtonBusqueda;
    EditText editTextBusqueda;


    public BuscarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);

        listView = view.findViewById(R.id.listview);
        imageButtonBusqueda = view.findViewById(R.id.imagebuttonBusqueda);
        editTextBusqueda = view.findViewById(R.id.edittextBusqueda);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,busquedas);
        listView.setAdapter(adapter);

        imageButtonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBusqueda.onEditorAction(EditorInfo.IME_ACTION_DONE);
                String busqueda = editTextBusqueda.getText().toString();
                busquedas.add(busqueda);
                editTextBusqueda.setText("");
                }
        });
        adapter.notifyDataSetChanged();
        return view;
    }

}
