package com.imdb.finalproject.service.exceptions;

public class UsernameAlreadyExistsException extends Exception{

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}