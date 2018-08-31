package com.mavenhive.bootcamp.projects.stockmarket.exceptions;

import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectUserCredentialsException extends Exception {

    public IncorrectUserCredentialsException() {
        super("Invalid username/password combination.");
    }
}