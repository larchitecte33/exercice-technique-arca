package com.arca.importing;

public class ImportException extends RuntimeException {
    public ImportException(String s) {
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
