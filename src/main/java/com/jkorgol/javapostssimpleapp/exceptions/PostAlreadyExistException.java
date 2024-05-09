package com.jkorgol.javapostssimpleapp.exceptions;

public class PostAlreadyExistException extends Exception {
    public PostAlreadyExistException(String message) {
        super(message);
    }
}
