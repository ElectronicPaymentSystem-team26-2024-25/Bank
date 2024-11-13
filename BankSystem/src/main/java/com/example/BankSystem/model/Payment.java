package com.example.BankSystem.model;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @Column(name="PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int paymentId;
    @Column(name = "ACQUIRER_ORDER_ID", nullable = false)
    int acquirerOrderId;
    @Column(name = "ISSUER_ORDER_ID", nullable = false)
    int issuerOrderId;
    @Column(name = "MERCHANT_ORDER_ID", nullable = false)
    int merchantOrderId;
    @Column(name = "AMOUNT", nullable = false)
    int amount;
    @Column(name = "STATUS", nullable = false)
    PaymentStatus status;
    public Payment (){}

    public Payment(int acquirerOrderId, int issuerOrderId, int merchantOrderId, int amount, PaymentStatus status) {
        this.acquirerOrderId = acquirerOrderId;
        this.issuerOrderId = issuerOrderId;
        this.merchantOrderId = merchantOrderId;
        this.amount = amount;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(int acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public int getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(int issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
