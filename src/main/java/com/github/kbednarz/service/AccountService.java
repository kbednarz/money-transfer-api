package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.error.InvalidInputException;
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

    public Account create(Account account) throws InvalidInputException {
        // todo: impl dto
        logger.debug("Creating account: [{}]", account.getNumber());

        if (db.getAccount(account.getNumber()) != null)
            throw new InvalidInputException("Account with number: " + account.getNumber() + " already exists");

        return db.saveAccount(account);
    }

    public Account get(String accountNumber) throws InvalidInputException {
        logger.debug("Getting account with number: [{}]", accountNumber);
        Account account = db.getAccount(accountNumber);
        if (account == null)
            throw new InvalidInputException("Account with number: " + accountNumber + " does not exist");

        return account;
    }
}
