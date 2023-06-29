package com.imdb.finalproject.model;

public class FavoriteMovie {
    private long movieId;
    private long userId;

    public FavoriteMovie(long movieId, long userId) {
        this.movieId = movieId;
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
