package com.github.kbednarz.service;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Datastore {
    private final DB db;

    public Datastore() {
        db = DBMaker
                .memoryDB()
                .closeOnJvmShutdown()
                .make();
    }
}
