package com.imdb.finalproject.dao;

import com.imdb.finalproject.model.*;
import com.imdb.finalproject.service.exceptions.MovieNotFoundException;

import java.util.List;

/**
 * Interface with CRUD methods for retrieving data for the Movies from the database.
 * */
public interface IMovieDAO {
    List<Movie> getMovie(String search) throws MovieNotFoundException;

    Movie getMovieById(long movieId) throws MovieNotFoundException;

    List<Movie> getTopRatedMovies();

    List<Movie> getLatestReleases();

    List<Movie> getUsersFavorites(long userId);

    Movie addFavorites(long movieId, long userId) throws MovieNotFoundException;

    List<Genre> getGenreByMovieId(long movieId) throws MovieNotFoundException;

    List<Actor> getActors(long movieId);

    List<Director> getDirectors(long movieId);

    FavoriteMovie getFavorite(long userId, long movieId);


}
