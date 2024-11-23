package com.example.BankSystem.dto;

import java.time.LocalDate;

public class PaymentExecutionRequest {
    private String pan;
    private int securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private String paymentId;

    public PaymentExecutionRequest(){}

    public PaymentExecutionRequest(PCCPaymentExecutionRequest pccRequest){
        this.pan = pccRequest.getPAN();
        this.securityCode = pccRequest.getSecurityCode();
        this.cardHolderName = pccRequest.getCardHolderName();
        this.expirationDate = pccRequest.getExpirationDate();
    }
    public String getPAN() {
        return pan;
    }

    public void setPAN(String PAN) {
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
