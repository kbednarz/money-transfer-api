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

    private final HashMap<Integer, ReentrantLock> lockHashMap = new HashMap<>();
    private final Datastore db;
    private final AccountService accountService;

    @Inject
    public TransferService(Datastore db, AccountService accountService) {
        this.db = db;
        this.accountService = accountService;
    }

    public void transfer(String from, String to, long amount) throws InvalidInputException, NotEnoughFundsException {
        logger.debug("Transferring: [{}] from [{}] to [{}]", amount, from, to);

        ReentrantLock firstLock = null, secondLock = null;
        boolean order = from.compareTo(to) < 0;
        try {
            firstLock = order ? acquireLock(from) : acquireLock(to);
            try {
                secondLock = order ? acquireLock(to) : acquireLock(from);

                transferMoney(from, to, amount);
            } finally {
                if (secondLock != null) secondLock.unlock();
            }
        } finally {
            if (firstLock != null) firstLock.unlock();

        }
    }

    private void transferMoney(String from, String to, long amount) throws InvalidInputException, NotEnoughFundsException {
        Account accountFrom = accountService.get(from);
        Account accountTo = accountService.get(to);

        if (accountFrom.getBalance() < amount)
            throw new NotEnoughFundsException();

        accountFrom.setBalance(accountFrom.getBalance() - amount);
        accountTo.setBalance(accountTo.getBalance() + amount);
        db.saveAccount(accountFrom);
        db.saveAccount(accountTo);
    }

    private ReentrantLock acquireLock(String accountNumber) {
        int hash = accountNumber.hashCode() % MAX_CONNECTIONS;
        lockHashMap.putIfAbsent(hash, new ReentrantLock());
        ReentrantLock lock = lockHashMap.get(hash);

        lock.lock();
        return lock;
    }

}
