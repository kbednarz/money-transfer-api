package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.error.InvalidInputException;
import com.github.kbednarz.error.NotEnoughFundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private static final int MAX_CONNECTIONS = 200;

    private final HashMap<String, ReentrantLock> lockHashMap = new HashMap<>();
    private final Datastore db;
    private final AccountService accountService;

    @Inject
    public TransferService(Datastore db, AccountService accountService) {
        this.db = db;
        this.accountService = accountService;
    }

    public void transfer(String from, String to, long amount) throws InvalidInputException, NotEnoughFundsException {
        logger.debug("Transferring: [{}] from [{}] to [{}]", amount, from, to);

        /* Lock only 'from' account */
        ReentrantLock lock = acquireLock(from);
        try {
            Account accountFrom = accountService.get(from);
            Account accountTo = accountService.get(to);

            if (accountFrom.getBalance() < amount)
                throw new NotEnoughFundsException();

//            accountTo.getBalance()

        } finally {
            lock.unlock();
        }
    }

    private ReentrantLock acquireLock(String accountNumber) {
        int hash = accountNumber.hashCode() % MAX_CONNECTIONS;
        ReentrantLock lock = lockHashMap.getOrDefault(hash, new ReentrantLock());
        lock.lock();

        return lock;
    }

}
