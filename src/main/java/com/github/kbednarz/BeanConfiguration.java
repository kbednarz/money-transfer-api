package com.github.kbednarz;

import com.github.kbednarz.service.AccountService;
import com.github.kbednarz.service.Datastore;
import com.github.kbednarz.service.TransferService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;

public class BeanConfiguration extends ResourceConfig {
    public BeanConfiguration() {
        packages("com.github.kbednarz");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(Datastore.class).in(Singleton.class);
                bindAsContract(AccountService.class).in(Singleton.class);
                bindAsContract(TransferService.class).in(Singleton.class);
            }
        });
    }
}
