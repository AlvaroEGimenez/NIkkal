package com.digitalhouse.a0818moacn01_02.DAO.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.digitalhouse.a0818moacn01_02.model.Favorito;

import java.util.List;

@Dao
public interface FavoritoRoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  agregar(Favorito favorito);

    @Delete
    void eliminar(Favorito favorito);

    @Query("Select * from Favoritos where uidUsuario = :uidUsuario and tipo = :tipo ")
    List<Favorito> getLista(String uidUsuario, String tipo);

    @Query("Select * from Favoritos where uidUsuario = :uidUsuario and tipo = :tipo and id = :id ")
    Favorito getFavoritoPorId(String uidUsuario, String tipo, Integer id);
}



