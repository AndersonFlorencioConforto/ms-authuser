package com.ead.authuser.domain.exceptions;


public class ForbbidenUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbbidenUserException(String msg) {
        super(msg);
    }


}