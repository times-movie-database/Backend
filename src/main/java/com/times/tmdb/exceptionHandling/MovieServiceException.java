package com.times.tmdb.exceptionHandling;

public class MovieServiceException extends RuntimeException {
    private static final long serialVersionUID = 1348771109171435607L;

    public MovieServiceException(String message) {
        super(message);
    }
}