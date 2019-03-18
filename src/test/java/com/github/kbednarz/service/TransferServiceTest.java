package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.error.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransferServiceTest {
    private TransferService transferService;
    private Datastore db;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        db = new Datastore();
        accountService = new AccountService(db);
        transferService = new TransferService(db, accountService);
    }

    @Test
    public void transferFromOneToAnotherFromDifferentThreads() throws InterruptedException, InvalidInputException {
        // given
        int THREADS = 200;
        String from = "0000071219812874", to = "004561231564165";
        Account accountFrom = new Account(1000, from, 1);
        accountFrom = accountService.create(accountFrom);
        Account accountTo = new Account(0, to, 2);
        accountTo = accountService.create(accountTo);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            Thread thread = new Thread(() -> {
                try {
                    transferService.transfer(from, to, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }

        // when
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        // then
        accountFrom = accountService.get(accountFrom.getNumber());
        accountTo = accountService.get(accountTo.getNumber());
        assertEquals(1000 - THREADS, accountFrom.getBalance());
        assertEquals(THREADS, accountTo.getBalance());
    }
}