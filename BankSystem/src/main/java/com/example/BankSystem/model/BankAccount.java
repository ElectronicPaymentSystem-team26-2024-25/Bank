package com.example.BankSystem.model;

import com.example.BankSystem.security.CryptoIntegerConverter;
import com.example.BankSystem.security.CryptoStringConverter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BankAccount {
    @Id
    @Column(name="ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    String accountId;
    @Convert(converter = CryptoStringConverter.class)
    @Column(name="PAN", nullable=false)
    private String PAN;

    @Convert(converter = CryptoIntegerConverter.class)
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

    @Convert(converter = CryptoStringConverter.class)
    @Column(name="MERCHANT_PASSWORD", nullable=true)
    private String merchantPassword;

    @Convert(converter = CryptoStringConverter.class)
    @Column(name="ACCOUNT_NUMBER", nullable = false, unique = true)
    private String accountNumber;
    public BankAccount(){}

    public String getAccountId() {
        return accountId;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
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

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
