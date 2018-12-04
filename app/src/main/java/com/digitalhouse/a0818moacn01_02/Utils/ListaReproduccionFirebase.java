package com.digitalhouse.a0818moacn01_02.Utils;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.digitalhouse.a0818moacn01_02.model.Track;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaReproduccionFirebase {
    public static final String PATH_LIST_REPRODUCCION = "lista_reproduccion";
    private List<Track> pistas;
    private String nombre;
    private DatabaseReference mReference;
    private FirebaseUser currentUser;


    public ListaReproduccionFirebase(String nombre) {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        this.nombre = nombre;
    }

    public ListaReproduccionFirebase() {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
    }

    public void agregarPista(Track pista) {
        if (this.pistas == null) {
            this.pistas = new ArrayList<>();
        }
        this.pistas.add(pista);

        if (currentUser != null) {
            DatabaseReference id = mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(nombre).push();
            pista.setUid(id.getKey());
            id.setValue(pista,
                    FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
            );
        }

    }

    public void eliminarPista(Track pista){
        mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(nombre).child(pista.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getLista(final ResultListener<List<Track>> resultListener) {
        if(currentUser == null){
            return;
        }
        final List<Track> artistList = new ArrayList<>();
        mReference.child(currentUser.getUid()).child(PATH_LIST_REPRODUCCION).child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Track pista = childSnapShot.getValue(Track.class);
                    artistList.add(pista);
                }
                resultListener.finish(artistList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public List<Track> getPistas() {
        if (pistas == null) {
            pistas = new ArrayList<>();
        }

        return pistas;
    }

    public void setPistas(List<Track> pistas) {
        this.pistas = pistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
