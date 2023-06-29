package com.imdb.finalproject.dto;

import com.imdb.finalproject.model.Actor;
import com.imdb.finalproject.model.Director;
import com.imdb.finalproject.model.Genre;
import com.imdb.finalproject.model.Movie;

import java.util.List;

/**
 * A complete movie Java Bean that contains all the necessary fields for a movie layout.
 * */
public class FullMovie {
    private Movie movie;
    private List<Director> director;
    private List<Actor> actors;
    private List<Genre> genres;

    public FullMovie() {
    }

    public FullMovie(Movie movie, List<Director> director, List<Actor> actors, List<Genre> genres) {
        this.movie = movie;
        this.director = director;
        this.actors = actors;
        this.genres = genres;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenre(List<Genre> genres) {
        this.genres = genres;
    }

}
