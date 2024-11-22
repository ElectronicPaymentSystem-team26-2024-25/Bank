package com.example.BankSystem.model;

import jakarta.persistence.*;

//Da li moram dodati acquirer i issuer timestamp ovde
@Entity
public class Payment {
    @Id
    @Column(name="PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int paymentId;
    @Column(name = "ACQUIRER_ORDER_ID", nullable = false)
    int acquirerOrderId;
    @Column(name = "ACQUIRER_BANK", nullable = false)
    String acquirerBank;
    @Column(name = "ISSUER_ORDER_ID", nullable = false)
    int issuerOrderId;
    @Column(name = "ISSUER_BANK", nullable = false)
    String issuerBank;
    @Column(name = "MERCHANT_ORDER_ID")
    int merchantOrderId;
    @Column(name = "AMOUNT", nullable = false)
    int amount;
    @Column(name = "STATUS", nullable = false)
    PaymentStatus status;
    public Payment (){}

    public Payment(int acquirerOrderId, String acquirerBank, int issuerOrderId, String issuerBank, int amount, PaymentStatus status, int merchantOrderId) {
        this.acquirerOrderId = acquirerOrderId;
        this.issuerOrderId = issuerOrderId;
        this.amount = amount;
        this.status = status;
        this.acquirerBank = acquirerBank;
        this.issuerBank = issuerBank;
        this.merchantOrderId = merchantOrderId;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getAcquirerBank() {
        return acquirerBank;
    }

    public void setAcquirerBank(String acquirerBank) {
        this.acquirerBank = acquirerBank;
    }

    public String getIssuerBank() {
        return issuerBank;
    }

    public void setIssuerBank(String issuerBank) {
        this.issuerBank = issuerBank;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
}
