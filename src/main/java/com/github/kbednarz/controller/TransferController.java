package com.github.kbednarz.controller;

import com.github.kbednarz.dto.TransferDto;
import com.github.kbednarz.error.InvalidInputException;
import com.github.kbednarz.error.NotEnoughFundsException;
import com.github.kbednarz.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(TransferDto dto) throws InvalidInputException, NotEnoughFundsException {
        transferService.transfer(dto.from, dto.to, dto.amount);

        return Response
                .status(Response.Status.OK)
                .build();
    }
}
