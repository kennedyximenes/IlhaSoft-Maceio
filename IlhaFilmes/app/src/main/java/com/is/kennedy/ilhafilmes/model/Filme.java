package com.is.kennedy.ilhafilmes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kennedy ximenes on 13/07/2017.
 */

@Entity(tableName = "Filme")
public class Filme {

    @PrimaryKey(autoGenerate = true)
    private int FilmeId;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "Poster")
    private String Poster;

    @ColumnInfo(name = "Year")
    private String Year;

    @ColumnInfo(name = "Genre")
    private String Genre;

    @ColumnInfo(name = "Runtime")
    private String Runtime;

    @ColumnInfo(name = "Director")
    private String Director;

    @ColumnInfo(name = "Actors")
    private String Actors;

    @ColumnInfo(name = "Plot")
    private String Plot;

    public int getFilmeId() {
        return FilmeId;
    }

    public void setFilmeId(int filmeId) {
        FilmeId = filmeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }
}
