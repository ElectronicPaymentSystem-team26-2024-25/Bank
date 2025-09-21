package com.example.BankSystem.dto;

import com.example.BankSystem.model.PaymentStatus;

public class PaymentStatusResponse {
    private PaymentStatus status;
    private String paymentId;
    private int merchantOrderId;

    public PaymentStatusResponse(){}

    public PaymentStatusResponse(PaymentStatus status, String paymentId, int merchantOrderId) {
        this.status = status;
        this.paymentId = paymentId;
        this.merchantOrderId = merchantOrderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(int merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }
}
