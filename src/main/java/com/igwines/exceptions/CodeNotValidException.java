package com.igwines.exceptions;

public class CodeNotValidException extends RuntimeException {

    public CodeNotValidException() {
        super("Code not valid");
    }
}
