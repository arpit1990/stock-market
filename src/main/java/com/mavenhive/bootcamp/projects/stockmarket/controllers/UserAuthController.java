package com.mavenhive.bootcamp.projects.stockmarket.controllers;


import com.mavenhive.bootcamp.projects.stockmarket.exceptions.IncorrectUserCredentialsException;
import com.mavenhive.bootcamp.projects.stockmarket.model.UserAuth;
import com.mavenhive.bootcamp.projects.stockmarket.services.UserAuthService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
@Component
public class UserAuthController {

    @Inject
    UserAuthService userDetailsService;

    @POST
    @Path("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public Response login(UserAuth userAuth) throws IncorrectUserCredentialsException {

        userAuth = userDetailsService.findByUsername(userAuth);

        return Response.status(200)
                .entity(userAuth)
                .build();
    }
}