package com.imdb.finalproject.service;

import com.imdb.finalproject.dao.IMovieDAO;
import com.imdb.finalproject.dto.FullMovie;
import com.imdb.finalproject.model.*;
import com.imdb.finalproject.service.exceptions.MovieNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class MovieServiceImpl implements IMovieService{

    @Inject
    private IMovieDAO movieDAO;


    @Override
    public List<Movie> movieToGet(String search) throws MovieNotFoundException {
        return movieDAO.getMovie(search);
    }

    @Override
    public FullMovie getOneMovie(long movieId) throws MovieNotFoundException {
        Movie movie = movieDAO.getMovieById(movieId);
        List<Director> directors = movieDAO.getDirectors(movieId);
        List<Actor> actors = movieDAO.getActors(movieId);
        List<Genre> genres = null;
        try {
            genres = movieDAO.getGenreByMovieId(movieId);
        } catch (MovieNotFoundException e) {
            throw new RuntimeException(e);
        }

        FullMovie fullMovie = new FullMovie(movie, directors, actors, genres);

        return fullMovie;
    }

    @Override
    public List<Movie> topRatedMoviesToGet() {
        return movieDAO.getTopRatedMovies();
    }

    @Override
    public List<Movie> latestReleasesToGet() {
        return movieDAO.getLatestReleases();
    }

    @Override
    public List<Movie> usersFavoritesToGet(long userId) {
        return movieDAO.getUsersFavorites(userId);
    }

    @Override
    public Movie favoritesToAdd(long movieId, long userId) throws MovieNotFoundException {
        return movieDAO.addFavorites(movieId, userId);
    }

    @Override
    public List<Genre> genreToGet(long movieId) throws MovieNotFoundException {
        return movieDAO.getGenreByMovieId(movieId);
    }

    @Override
    public List<Actor> actorsToGet(long movieId) {
        return movieDAO.getActors(movieId);
    }

    @Override
    public List<Director> directorToGet(long movieId) {
        return movieDAO.getDirectors(movieId);
    }

    @Override
    public FavoriteMovie favoriteMovieToGet(long userId, long movieId) {return movieDAO.getFavorite(userId, movieId);}


}
