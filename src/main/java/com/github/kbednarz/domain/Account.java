package com.github.kbednarz.domain;

import java.io.Serializable;

public class Account implements Serializable {
    private long balance;
    private String number;
    private long clientId;

    public Account(long balance, String number, long clientId) {
        this.balance = balance;
        this.number = number;
        this.clientId = clientId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
