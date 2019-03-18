package com.github.kbednarz.dto;

public class AccountDto {
    public String number;
    public long clientId;

    public AccountDto() {
    }

    public AccountDto(String number, long clientId) {
        this.number = number;
        this.clientId = clientId;
    }
}
