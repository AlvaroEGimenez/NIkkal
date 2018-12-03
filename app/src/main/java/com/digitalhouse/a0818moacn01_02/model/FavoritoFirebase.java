package com.digitalhouse.a0818moacn01_02.model;

import android.support.annotation.NonNull;

import com.digitalhouse.a0818moacn01_02.Utils.ResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritoFirebase {
    public static final String PATH_LIST_FAVORITO = "favorito";
    private DatabaseReference mReference;
    private FirebaseUser currentUser;
    private String tipo;
    private List<Favorito> favoritoList = new ArrayList<>();

    public FavoritoFirebase(String tipo) {
        mReference = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance()
                .getCurrentUser();
        this.tipo = tipo;
        getLista(new ResultListener<List<Favorito>>() {
            @Override
            public void finish(List<Favorito> Resultado) {
                favoritoList.addAll(Resultado);
            }
        });
    }

    public void agregar(Integer id) {
        if (currentUser != null) {
            DatabaseReference uid = mReference.child(PATH_LIST_FAVORITO).child(currentUser.getUid()).child(tipo).push();
            Favorito favorito = new Favorito(id, uid.getKey());
            uid.setValue(favorito,
                    FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
            );
        }
    }

    public void eliminar(Integer id) {
        Favorito favorito = getFavoritoPorId(id);
        mReference.child(PATH_LIST_FAVORITO).child(currentUser.getUid()).child(tipo).child(favorito.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getLista(final ResultListener<List<Favorito>> resultListener) {
        if (currentUser == null) {
            return;
        }
        final List<Favorito> favoritosList = new ArrayList<>();
        mReference.child(PATH_LIST_FAVORITO).child(currentUser.getUid()).child(tipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoritosList.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    Favorito favorito = childSnapShot.getValue(Favorito.class);
                    favoritosList.add(favorito);
                }
                resultListener.finish(favoritosList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public Favorito getFavoritoPorId(Integer id) {
        for (Favorito favorito : favoritoList) {
            if (favorito.getId().equals(id)) {
                return favorito;
            }
        }

        return null;
    }
}
