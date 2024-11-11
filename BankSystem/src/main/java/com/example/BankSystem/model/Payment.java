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
    @Column(name = "AMOUNT", nullable = false)
    int amount;

    public Payment (){}

    public Payment(int acquirerOrderId, int issuerOrderId, int amount) {
        this.acquirerOrderId = acquirerOrderId;
        this.issuerOrderId = issuerOrderId;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
