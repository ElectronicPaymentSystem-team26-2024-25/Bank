package com.example.BankSystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IssuerOrder {
    @Id
    @Column(name="ISSUER_ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int issuerOrderId;
    @Column(name = "ISSUER_TIMESTAMP", nullable = false)
    LocalDateTime issuerTimestamp;
    @Column(name = "ACCOUNT_ID", nullable = false)
    String accountId;
    @Column(name = "ISSUER_BANK", nullable = false)
    String bank;
    @Column(name = "AMOUNT", nullable = false)
    int amount;

    public IssuerOrder(){}

    public IssuerOrder(LocalDateTime issuerTimestamp, String accountId, String bank, int amount) {
        this.issuerTimestamp = issuerTimestamp;
        this.accountId = accountId;
        this.bank = bank;
        this.amount = amount;
    }

    public int getIssuerOrderId() {
        return issuerOrderId;
    }

    public LocalDateTime getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(LocalDateTime issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
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