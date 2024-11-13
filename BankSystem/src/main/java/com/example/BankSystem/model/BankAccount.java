package com.example.BankSystem.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BankAccount {
    @Id
    @Column(name="ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    String accountId;
    @Column(name="PAN", nullable=false)
    private int PAN;
    @Column(name="SECURITY_CODE", nullable=false)
    private int securityCode;
    @Column(name="CARD_HOLDER_NAME", nullable=false)
    private String cardHolderName;
    @Column(name="EXPIRATION_DATE", nullable=false)
    private LocalDate expirationDate;
    @Column(name="AVAILABLE_FUNDS", nullable=false)
    private int availableFunds;
    @Column(name="MERCHANT_ID", nullable=true)
    private String merchantId;

    public BankAccount(){}

    public String getAccountId() {
        return accountId;
    }

    public int getPAN() {
        return PAN;
    }

    public void setPAN(int PAN) {
        this.PAN = PAN;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(int availableFunds) {
        this.availableFunds = availableFunds;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
