package com.example.BankSystem.dto;

public class CardPaymentRequestResponse {
    private String paymentId;
    private String paymentUrl;
    public CardPaymentRequestResponse(){}
    public CardPaymentRequestResponse(String paymentId, String paymentUrl){
        this.paymentId = paymentId;
        this.paymentUrl = paymentUrl;
    }
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
