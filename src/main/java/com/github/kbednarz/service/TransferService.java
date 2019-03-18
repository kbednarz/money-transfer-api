package com.github.kbednarz.service;

import com.github.kbednarz.domain.Account;
import com.github.kbednarz.error.InvalidInputException;
import com.github.kbednarz.error.NotEnoughFundsException;
import com.github.kbednarz.error.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);
    private static final int MAX_CONNECTIONS = 200;
    private static final int LOCK_TIMEOUT_MS = 1000;

    private final HashMap<Integer, ReentrantLock> lockHashMap = new HashMap<>();
    private final Datastore db;
    private final AccountService accountService;

    @Inject
    public TransferService(Datastore db, AccountService accountService) {
        this.db = db;
        this.accountService = accountService;
    }

    public void transfer(String from, String to, long amount) throws InvalidInputException, NotEnoughFundsException, ServerException, InterruptedException {
        logger.debug("Transferring: [{}] from [{}] to [{}]", amount, from, to);

        ReentrantLock lockFrom = null, lockTo = null;
        try {
            lockFrom = acquireLock(from);
            try {
                lockTo = acquireLock(to);

                Account accountFrom = accountService.get(from);
                Account accountTo = accountService.get(to);

                if (accountFrom.getBalance() < amount)
                    throw new NotEnoughFundsException();

                accountFrom.setBalance(accountFrom.getBalance() - amount);
                accountTo.setBalance(accountTo.getBalance() + amount);
                db.saveAccount(accountFrom);
                db.saveAccount(accountTo);

            } finally {
                if (lockTo != null) lockTo.unlock();
            }
        } finally {
            if (lockFrom != null) lockFrom.unlock();

        }
    }

    private ReentrantLock acquireLock(String accountNumber) throws InterruptedException, ServerException {
        int hash = accountNumber.hashCode() % MAX_CONNECTIONS;
        lockHashMap.putIfAbsent(hash, new ReentrantLock());
        ReentrantLock lock = lockHashMap.get(hash);
        if (lock.tryLock(LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
            return lock;
        } else {
            throw new ServerException("Cannot acquire lock");
        }
    }

}
