package com.example.BankSystem.dto;

import com.example.BankSystem.model.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentExecutionResponse {
    private int merchantOrderId;
    private int acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private int paymentId;
    private PaymentStatus status;
    private String redirectUrl;
    private String failReason;

    public PaymentExecutionResponse(){}

    public PaymentExecutionResponse(int merchantOrderId, int acquirerOrderId, LocalDateTime acquirerTimestamp, int paymentId, PaymentStatus status, String redirectUrl, String failReason) {
        this.merchantOrderId = merchantOrderId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
        this.paymentId = paymentId;
        this.status = status;
        this.redirectUrl = redirectUrl;
        this.failReason = failReason;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
