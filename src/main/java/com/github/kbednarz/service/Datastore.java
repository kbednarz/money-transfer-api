package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
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

    public Account getAccount(String accountNumber) {
        return getAccountCollection().get(accountNumber);
    }

    public Account saveAccount(Account account) {
        getAccountCollection().put(account.getNumber(), account);
        return account;
    }

    private HTreeMap<String, Account> getAccountCollection() {
        return db.hashMap(ACCOUNT_COLLECTION, Serializer.STRING, Serializer.JAVA).createOrOpen();
    }
}
