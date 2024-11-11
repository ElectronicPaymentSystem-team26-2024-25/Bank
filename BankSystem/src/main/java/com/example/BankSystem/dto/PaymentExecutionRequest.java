package com.example.BankSystem.dto;

import java.time.LocalDate;

public class PaymentExecutionRequest {
    private int pan;
    private int securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private int paymentId;

    public PaymentExecutionRequest(){}

    public int getPAN() {
        return pan;
    }

    public void setPAN(int PAN) {
        this.pan = PAN;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
