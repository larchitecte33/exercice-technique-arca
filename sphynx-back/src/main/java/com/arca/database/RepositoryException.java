package com.arca.database;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String s) {
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
