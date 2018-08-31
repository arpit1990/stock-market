package com.mavenhive.bootcamp.projects.stockmarket.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UnauthorizedAccessException extends Exception  implements ExceptionMapper<UnauthorizedAccessException> {
    public UnauthorizedAccessException(String userName) {
        super("User not found: " + userName);
    }

    @Override
    public Response toResponse(UnauthorizedAccessException exception)
    {
        return Response.status(403)
                .entity(exception.getMessage())
                .type("application/json")
                .build();
    }
}