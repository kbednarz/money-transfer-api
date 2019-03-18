package com.github.kbednarz.controller;

import com.github.kbednarz.BeanConfiguration;
import com.github.kbednarz.dto.AccountDto;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountControllerIntTest extends JerseyTest {
    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new BeanConfiguration();
    }

    @Test
    public void create() {
        AccountDto accountDto = new AccountDto("1232134123452", 1);

        Response response = target("/account")
                .request()
                .post(Entity.entity(accountDto, MediaType.APPLICATION_JSON));

        assertEquals("should return status CREATED 201", 201, response.getStatus());
        assertNotNull("Should return created account", response.getEntity());
    }
}