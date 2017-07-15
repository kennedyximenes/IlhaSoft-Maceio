package com.is.kennedy.ilhafilmes.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.is.kennedy.ilhafilmes.presenter.FilmeDao;

/**
 * Created by kennedy ximenes on 13/07/2017.
 */

@Database(entities = {Filme.class}, version = 1)
public abstract class FilmeDB extends RoomDatabase {

    private static FilmeDB INSTANCE;
    public abstract FilmeDao filmeDao();

    public static FilmeDB getFilmeDB(Context context){

        if( INSTANCE == null){

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FilmeDB.class, "filme-database")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
