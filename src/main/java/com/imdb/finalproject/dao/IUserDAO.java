package com.imdb.finalproject.dao;

import com.imdb.finalproject.model.User;
import com.imdb.finalproject.service.exceptions.EmailAlreadyExistsException;
import com.imdb.finalproject.service.exceptions.UserNotFoundException;
import com.imdb.finalproject.service.exceptions.UsernameAlreadyExistsException;

/**
 * Interface with crud methods for retrieving data for the User from the database.
 * */
public interface IUserDAO {
    User insertUser(User user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException;
    User getUser(String username, String password) throws UserNotFoundException;
}
