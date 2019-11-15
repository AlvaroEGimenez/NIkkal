package com.digitalhouse.a0818moacn01_02.DAO.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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



