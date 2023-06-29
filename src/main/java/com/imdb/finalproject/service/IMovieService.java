package com.imdb.finalproject.service;

import com.imdb.finalproject.dto.FullMovie;
import com.imdb.finalproject.model.*;
import com.imdb.finalproject.service.exceptions.MovieNotFoundException;
import java.util.List;

/**
 * The service layer Interface for Movies.
 * */
public interface IMovieService {
    List<Movie> movieToGet(String search) throws MovieNotFoundException;

    FullMovie getOneMovie(long movieId) throws MovieNotFoundException;

    List<Movie> topRatedMoviesToGet();

    List<Movie> latestReleasesToGet();

    List<Movie> usersFavoritesToGet(long userId);

    Movie favoritesToAdd(long movieId, long userId) throws MovieNotFoundException;

    List<Genre> genreToGet(long movieId) throws MovieNotFoundException;

    List<Actor> actorsToGet(long movieId);

    List<Director> directorToGet(long movieId);

    FavoriteMovie favoriteMovieToGet(long userId, long movieId);
}
