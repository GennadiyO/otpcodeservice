package com.igwines.exceptions;

public class NotValidUserDetailsException extends RuntimeException {

    public NotValidUserDetailsException() {
        super("Not valid user details");
    }
}
