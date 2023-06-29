package com.imdb.finalproject.dao;

import com.imdb.finalproject.model.*;
import com.imdb.finalproject.service.dbutil.DBUtil;
import com.imdb.finalproject.service.exceptions.MovieNotFoundException;

import javax.ws.rs.ext.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of  IMovieDAO Interface.
 */
@Provider
public class IMovieDAOImpl implements IMovieDAO {
    @Override
    public List<Movie> getMovie(String search) throws MovieNotFoundException {
        String sql = "SELECT * FROM movies WHERE title LIKE ?";
        ResultSet rs;
        List<Movie> movies = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, "%" + search + "%");
            rs = p.executeQuery();

            while (rs.next()) {
                Movie mappedMovie = new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("release_year"),
                        rs.getString("duration"),
                        rs.getString("plot_summary"),
                        rs.getDouble("rating"),
                        rs.getString("image_url"));
                movies.add(mappedMovie);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new MovieNotFoundException(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return movies;
    }

    @Override
    public Movie getMovieById(long movieId) throws MovieNotFoundException {
        String sql = "SELECT * FROM movies WHERE id = ?";
        ResultSet rs;
        Movie movie = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, movieId);
            rs = p.executeQuery();

            if (rs.next()) {
                movie = new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("release_year"),
                        rs.getString("duration"),
                        rs.getString("plot_summary"),
                        rs.getDouble("rating"),
                        rs.getString("image_url"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new MovieNotFoundException(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return movie;
    }

    @Override
    public List<Movie> getTopRatedMovies() {
        String sql = "SELECT * FROM movies WHERE rating > 8.5 ";
        ResultSet rs;
        List<Movie> movies = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            rs = p.executeQuery();

            while (rs.next()) {
                Movie mappedMovie = new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("release_year"),
                        rs.getString("duration"),
                        rs.getString("plot_summary"),
                        rs.getDouble("rating"),
                        rs.getString("image_url"));
                movies.add(mappedMovie);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return movies;
    }

    @Override
    public List<Movie> getLatestReleases() {
        String sql = "SELECT * FROM movies WHERE release_year >= 2010";
        ResultSet rs;
        List<Movie> movies = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            rs = p.executeQuery();

            while (rs.next()) {
                Movie mappedMovie = new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("release_year"),
                        rs.getString("duration"),
                        rs.getString("plot_summary"),
                        rs.getDouble("rating"),
                        rs.getString("image_url"));
                movies.add(mappedMovie);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return movies;
    }

    @Override
    public List<Movie> getUsersFavorites(long userId) {
        String sql = "SELECT * FROM favorites " +
                "INNER JOIN movies ON movies.id=favorites.movie_id " +
                "INNER JOIN users ON users.id=favorites.user_id WHERE user_id=?";
        ResultSet rs;
        List<Movie> movies = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, userId);

            rs = p.executeQuery();

            while (rs.next()) {
                Movie mappedMovie = new Movie(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("release_year"),
                        rs.getString("duration"),
                        rs.getString("plot_summary"),
                        rs.getDouble("rating"),
                        rs.getString("image_url")
                );
                movies.add(mappedMovie);
            }
            return movies;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public Movie addFavorites(long movieId, long userId) throws MovieNotFoundException{
        String sql = "INSERT IGNORE INTO favorites (movie_id, user_id) values(?,?)";
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            p.setLong(1, movieId);
            p.setLong(2, userId);
            p.executeUpdate();

            try (ResultSet generatedKeys = p.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long favoriteId = generatedKeys.getLong(1);
                }
            }

            return getMovieById(movieId);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new MovieNotFoundException(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public List<Genre> getGenreByMovieId(long movieId) throws MovieNotFoundException {
        String sql = "SELECT * FROM genre " +
                "INNER JOIN movie_genre ON genre.id=movie_genre.genre_id " +
                "INNER JOIN movies ON movies.id=movie_genre.movie_id WHERE movie_genre.movie_id=?";
        List<Genre> genres = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, movieId);
            rs = p.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getLong("id"),
                        rs.getString("type"));
                genres.add(genre);
            }
            return genres;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new MovieNotFoundException(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public List<Actor> getActors(long movieId) {
        String sql = "SELECT * FROM actors " +
                "INNER JOIN movie_actors ON actors.id=movie_actors.actor_id " +
                "INNER JOIN movies ON movies.id=movie_actors.movie_id WHERE movie_actors.movie_id=?";
        List<Actor> actors = new ArrayList<>();
        ResultSet rs;
        Connection conn = null;
        try {
             conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, movieId);

            rs = p.executeQuery();

            while (rs.next()) {
                Actor actor = new Actor(
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("biography"));
                actors.add(actor);
            }
            return actors;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public List<Director> getDirectors(long movieId) {
        String sql = "SELECT * FROM directors " +
                "INNER JOIN movie_directors ON directors.id=movie_directors.director_id " +
                "INNER JOIN movies ON movies.id=movie_directors.movie_id WHERE movie_directors.movie_id = ?";
        ResultSet rs;
        List<Director> directors = new ArrayList<>();
        Connection conn = null;
        try {
             conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, movieId);
            rs = p.executeQuery();

            while (rs.next()) {
                directors.add(new Director(
                        rs.getLong("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("biography")));
            }
            return directors;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public FavoriteMovie getFavorite(long userId, long movieId) {
        FavoriteMovie favoriteMovie = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM favorites WHERE user_id=? AND movie_id=?";
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setLong(1, userId);
            p.setLong(2, movieId);

            rs = p.executeQuery();

            if (rs != null && rs.next()) {
                    favoriteMovie = new FavoriteMovie(
                            rs.getLong("user_id"),
                            rs.getLong("movie_id"));
                } else {
                    favoriteMovie = new FavoriteMovie(-1, -1);
                    System.out.println("No favorite movie found for the given user and movie.");
                }



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally {
            DBUtil.closeConnection(conn);
        }
        return favoriteMovie;
    }


}
