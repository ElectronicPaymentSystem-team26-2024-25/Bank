package com.example.PCC.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PCCPaymentExecutionRequest {
    private String pan;
    private int securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private int paymentId;
    private int acquirerOrderId;
    private LocalDateTime acquirerOrderTimestamp;
    private String acquirerBank;
    private int amount;


    public PCCPaymentExecutionRequest(){}

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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getAcquirerOrderId() {
        return acquirerOrderId;
    }

    public void setAcquirerOrderId(int acquirerOrderId) {
        this.acquirerOrderId = acquirerOrderId;
    }

    public LocalDateTime getAcquirerOrderTimestamp() {
        return acquirerOrderTimestamp;
    }

    public void setAcquirerOrderTimestamp(LocalDateTime acquirerOrderTimestamp) {
        this.acquirerOrderTimestamp = acquirerOrderTimestamp;
    }

    public String getAcquirerBank() {
        return acquirerBank;
    }

    public void setAcquirerBank(String acquirerBank) {
        this.acquirerBank = acquirerBank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
