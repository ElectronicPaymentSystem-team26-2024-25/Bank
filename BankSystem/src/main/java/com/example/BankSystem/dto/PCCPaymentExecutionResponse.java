package com.example.BankSystem.dto;

import com.example.BankSystem.model.PaymentStatus;

import java.time.LocalDateTime;

public class PCCPaymentExecutionResponse {
    private int acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private int issuerOrderId;
    private LocalDateTime issuerTimestamp;
    private PaymentStatus paymentStatus;
    private String issuerBank;

    public PCCPaymentExecutionResponse(){}

    public PCCPaymentExecutionResponse(int acquirerOrderId, LocalDateTime acquirerTimestamp, int issuerOrderId, LocalDateTime issuerTimestamp, PaymentStatus paymentStatus, String issuerBank) {
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = issuerTimestamp;
        this.paymentStatus = paymentStatus;
        this.issuerBank = issuerBank;
    }

    public int getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(int acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public LocalDateTime getAcquirerTimestamp() {
        return acquirerTimestamp;
    }

    public void setAcquirerTimestamp(LocalDateTime acquirerTimestamp) {
        this.acquirerTimestamp = acquirerTimestamp;
    }

    public int getIssuerOrderId() {
        return issuerOrderId;
    }

    public void setIssuerOrderId(int issuerOrderId) {
        this.issuerOrderId = issuerOrderId;
    }

    public LocalDateTime getIssuerTimestamp() {
        return issuerTimestamp;
    }

    public void setIssuerTimestamp(LocalDateTime issuerTimestamp) {
        this.issuerTimestamp = issuerTimestamp;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getIssuerBank() {
        return issuerBank;
    }

    public void setIssuerBank(String issuerBank) {
        this.issuerBank = issuerBank;
    }
}
