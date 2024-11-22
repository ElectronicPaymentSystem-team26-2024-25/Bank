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
    @Column(name = "AMOUNT", nullable = false)
    int amount;

    public AcquirerOrder(){}

    public AcquirerOrder(LocalDateTime acquirerTimestamp, String accountId, int amount) {
        this.acquirerTimestamp = acquirerTimestamp;
        this.accountId = accountId;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}