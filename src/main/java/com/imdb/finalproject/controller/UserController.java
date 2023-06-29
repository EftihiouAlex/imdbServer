package com.imdb.finalproject.controller;

import com.imdb.finalproject.model.User;
import com.imdb.finalproject.service.IUserService;
import com.imdb.finalproject.service.exceptions.EmailAlreadyExistsException;
import com.imdb.finalproject.service.exceptions.UsernameAlreadyExistsException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/user")
public class UserController {

    @Inject
    private IUserService userService;

    @Path("/login")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            User user = userService.userToGet(username, password);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User not found.").build();
            }
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error in login").build();
        }
    }

    @Path("/register")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response registerUser(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("email") String email) {
        try {
            User user = new User(username, password, email);
            User insertedUser = userService.userToInsert(user);

            if (insertedUser.getId() > 0) {
                return Response.status(Response.Status.OK).entity(user).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User not inserted").build();
            }
        } catch (EmailAlreadyExistsException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Email already exists").build();
        }catch (UsernameAlreadyExistsException e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Username already exists").build();
        }
    }
}
