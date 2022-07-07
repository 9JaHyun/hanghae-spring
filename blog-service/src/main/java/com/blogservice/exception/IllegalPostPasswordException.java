package com.blogservice.exception;

public class IllegalPostPasswordException extends RuntimeException{

    public IllegalPostPasswordException() {
        super();
    }

    public IllegalPostPasswordException(String message) {
        super(message);
    }
}
