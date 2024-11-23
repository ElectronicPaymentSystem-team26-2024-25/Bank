package com.example.BankSystem.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PaymentUrls {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "PAYMENT_ID", nullable = false)
    String paymentId;
    @Column(name = "SUCCESS_URL", nullable = false)
    String successUrl;
    @Column(name = "FAIL_URL", nullable = false)
    String failUrl;
    @Column(name = "ERROR_URL", nullable = false)
    String errorUrl;
    public PaymentUrls(){}

    public PaymentUrls(String paymentId, String successUrl, String failUrl, String errorUrl) {
        this.paymentId = paymentId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.errorUrl = errorUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
