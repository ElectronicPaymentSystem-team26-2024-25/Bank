package com.example.BankSystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MerchantOrder {
    @Id
    @Column(name="MERCHANT_ORDER_ID")
    int merchantOrderId;
    @Column(name = "MERCHANT_TIMESTAMP", nullable = false)
    LocalDateTime merchantTimestamp;
    @Column(name = "MERCHANT_ID", nullable = false)
    String merchantId;
    @Column(name = "AMOUNT", nullable = false)
    int amount;

    public MerchantOrder(){}

    public MerchantOrder(int merchantOrderId, LocalDateTime merchantTimestamp, String merchantId, int amount) {
        this.merchantOrderId = merchantOrderId;
        this.merchantTimestamp = merchantTimestamp;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public LocalDateTime getMerchantTimestamp() {
        return merchantTimestamp;
    }

    public void setMerchantTimestamp(LocalDateTime merchantTimestamp) {
        this.merchantTimestamp = merchantTimestamp;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
