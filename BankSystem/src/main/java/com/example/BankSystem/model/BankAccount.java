package com.example.BankSystem.model;

import jakarta.persistence.*;

import java.util.Date;

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
    @Column(name="EXPIRE_DATE", nullable=false)
    private Date expireDate;
    @Column(name="AVAILABLE_FUNDS", nullable=false)
    private int availableFunds;

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(int availableFunds) {
        this.availableFunds = availableFunds;
    }
}
