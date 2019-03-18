package com.github.kbednarz.dto;

public class TransferDto {
    public String from;
    public String to;
    public long amount;

    public TransferDto() {
    }

    public TransferDto(String from, String to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
