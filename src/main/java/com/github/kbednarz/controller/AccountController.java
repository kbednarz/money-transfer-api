package com.github.kbednarz.controller;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.dto.AccountDto;
import com.github.kbednarz.error.InvalidInputException;
import com.github.kbednarz.service.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountController {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("accountNumber") String accountNumber) throws InvalidInputException {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new InvalidInputException("Account number [" + accountNumber + "] is invalid");
        }

        Account account = accountService.get(accountNumber);
        return Response
                .status(Response.Status.CREATED)
                .entity(account)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(AccountDto dto) throws InvalidInputException {
        Account account = accountService.create(dto);
        return Response
                .status(Response.Status.CREATED)
                .entity(account)
                .build();
    }
}
