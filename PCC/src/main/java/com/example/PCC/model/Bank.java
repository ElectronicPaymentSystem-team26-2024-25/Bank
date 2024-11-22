package com.example.PCC.model;

import jakarta.persistence.*;

@Entity
public class Bank {
    @Id
    @Column(name="BANK_IDENTIFIER")
    int bankId;
    @Column(name = "BANK_NAME", nullable = false)
    String bankName;
    @Column(name = "PORT", nullable = false)
    int port;

    public Bank(){
        bankId = 0;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
