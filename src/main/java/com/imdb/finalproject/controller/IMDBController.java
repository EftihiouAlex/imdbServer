package com.imdb.finalproject.controller;

import com.imdb.finalproject.model.FavoriteMovie;
import com.imdb.finalproject.dto.FullMovie;
import com.imdb.finalproject.model.Movie;
import com.imdb.finalproject.service.IMovieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
public class IMDBController {

    @Inject
    private IMovieService movieService;


    @Path("/search")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMovies(@QueryParam("title") String title) {
        try {
            List<Movie> movies = movieService.movieToGet(title);
            return Response.status(Response.Status.OK).entity(movies).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Path("/")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMovie(@QueryParam("id") long movieId) {
        try {
            FullMovie fullMovie = movieService.getOneMovie(movieId);
            return Response.status(Response.Status.OK).entity(fullMovie).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }


    @Path("/latestreleases")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLatestReleases() {
        try {
            List<Movie> movieList = movieService.latestReleasesToGet();
            return Response.status(Response.Status.OK).entity(movieList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Path("/toprated")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTopRated() {
        try {
            List<Movie> movieList = movieService.topRatedMoviesToGet();
            return Response.status(Response.Status.OK).entity(movieList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Path("/favorites")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsersFavorites(@QueryParam("userId") long userId) {
        try {
            List<Movie> movieList = movieService.usersFavoritesToGet(userId);
            return Response.status(Response.Status.OK).entity(movieList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Path("/addfavorites")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addUsersFavorite(@QueryParam("movieId") long movieId, @QueryParam("userId") long userId) {
        try {
            Movie movie = movieService.favoritesToAdd(movieId, userId);
            return Response.status(Response.Status.OK).entity(movie).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @Path("/getfavorite")
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFavoriteMovie(@QueryParam("userId") long userId, @QueryParam("movieId") long movieId){
        try{
            FavoriteMovie favoriteMovie = movieService.favoriteMovieToGet(userId, movieId);
            return Response.status(Response.Status.OK).entity(favoriteMovie).build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
        }
    }
}
