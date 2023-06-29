package com.imdb.finalproject.service;

import com.imdb.finalproject.dao.IUserDAO;
import com.imdb.finalproject.model.User;
import com.imdb.finalproject.service.exceptions.EmailAlreadyExistsException;
import com.imdb.finalproject.service.exceptions.UserNotFoundException;
import com.imdb.finalproject.service.exceptions.UsernameAlreadyExistsException;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

@Provider
public class UserServiceImpl implements IUserService{

    @Inject
    private IUserDAO userDAO;

    @Override
    public User userToGet(String username, String password) throws UserNotFoundException {
        return userDAO.getUser(username, password);
    }

    @Override
    public User userToInsert(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        return userDAO.insertUser(user);
    }
}
