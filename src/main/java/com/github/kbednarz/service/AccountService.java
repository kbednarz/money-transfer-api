package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final Datastore db;

    @Inject
    public AccountService(Datastore db) {
        this.db = db;
    }

    public Account create(Account account) {
        return db.saveAccount(account);
    }
}
