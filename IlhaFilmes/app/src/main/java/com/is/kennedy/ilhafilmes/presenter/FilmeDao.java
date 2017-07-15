package com.is.kennedy.ilhafilmes.presenter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.is.kennedy.ilhafilmes.model.Filme;

import java.util.List;

/**
 * Created by kennedy ximenes on 13/07/2017.
 */

@Dao
public interface FilmeDao {

    @Query("SELECT * FROM Filme")
    List<Filme> getAll();

    @Query("SELECT * FROM Filme where Title LIKE  :Title")
    Filme findByName(String Title);

    @Query("SELECT * FROM Filme where FilmeID = :FilmeID")
    Filme findByID(int FilmeID);

    @Query("SELECT COUNT(*) from Filme")
    int countFilmes();

    @Insert
    void insertAll(Filme... filmes);

    @Delete
    void delete(Filme filme);

}
