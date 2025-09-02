package com.vtc.exceptions;

public class NonexistentEntityException extends Exception {
    
    public NonexistentEntityException(String message) {
        super(message);
    }

    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
