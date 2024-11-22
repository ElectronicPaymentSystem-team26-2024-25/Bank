package com.example.PCC.dto;

import com.example.PCC.model.PaymentStatus;

import java.time.LocalDateTime;

public class PCCPaymentExecutionResponse {
    private int acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private int issuerOrderId;
    private LocalDateTime issuerTimestamp;
    private PaymentStatus paymentStatus;
    private String issuerBank;
    private String failReason;
    public PCCPaymentExecutionResponse(){}

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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
