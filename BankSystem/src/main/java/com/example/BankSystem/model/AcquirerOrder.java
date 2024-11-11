package com.example.BankSystem.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AcquirerOrder {
    @Id
    @Column(name="ACQUIRER_ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int acquirerOrderId;
    @Column(name = "ACQUIRER_TIMESTAMP", nullable = false)
    LocalDateTime acquirerTimestamp;
    @Column(name = "ACCOUNT_ID", nullable = false)
    String accountId;
    @Column(name = "ACQUIRER_BANK", nullable = false)
    String bank;
    @Column(name = "AMOUNT", nullable = false)
    int amount;

    public AcquirerOrder(){}

    public AcquirerOrder(LocalDateTime acquirerTimestamp, String accountId, String bank, int amount) {
        this.acquirerTimestamp = acquirerTimestamp;
        this.accountId = accountId;
        this.bank = bank;
        this.amount = amount;
    }

    public int getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public LocalDateTime getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(LocalDateTime acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}