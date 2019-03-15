package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.error.InvalidInputException;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class Datastore {
    private final static String ACCOUNT_COLLECTION = "accounts";
    private final DB db;

    public Datastore() {
        db = DBMaker
                .memoryDB()
                .closeOnJvmShutdown()
                .make();
    }

    public Account getAccount(String accountNumber) throws InvalidInputException {
        Account account = getAccountCollection().get(accountNumber);
        if (account == null)
            throw new InvalidInputException("Account with number: " + accountNumber + " does not exist");

        return account;
    }

    public Account saveAccount(Account account) {
        return getAccountCollection().put(account.getNumber(), account);
    }

    private HTreeMap<String, Account> getAccountCollection() {
        return db.hashMap(ACCOUNT_COLLECTION, Serializer.STRING, Serializer.JAVA).createOrOpen();
    }
}
