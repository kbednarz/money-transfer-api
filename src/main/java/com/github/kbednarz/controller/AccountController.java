package com.github.kbednarz.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return "GET MOCK";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String create() {
        return "POST MOCK";
    }
}
