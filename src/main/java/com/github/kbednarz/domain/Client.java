package com.github.kbednarz.domain;

public class Client {
    private long idNumber;
    private String name;
    private String address;

    public Client(long idNumber, String name, String address) {
        this.idNumber = idNumber;
        this.name = name;
        this.address = address;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
