package com.imdb.finalproject.service;

import com.imdb.finalproject.model.User;
import com.imdb.finalproject.service.exceptions.EmailAlreadyExistsException;
import com.imdb.finalproject.service.exceptions.UserNotFoundException;
import com.imdb.finalproject.service.exceptions.UsernameAlreadyExistsException;

/**
 * The service layer Interface for a User.
 * */
public interface IUserService {
    User userToGet(String username, String password) throws UserNotFoundException;
    User userToInsert(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;
}
