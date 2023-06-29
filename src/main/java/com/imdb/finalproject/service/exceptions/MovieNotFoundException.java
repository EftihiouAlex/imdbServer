package com.imdb.finalproject.service.exceptions;

public class MovieNotFoundException extends Exception{

    public MovieNotFoundException(String message) {
        super(message);
    }
}
