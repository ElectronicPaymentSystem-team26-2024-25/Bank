package com.example.BankSystem.dto;

public class CardPaymentRequestResponse {
    private int paymentId;
    private String paymentUrl;
    public CardPaymentRequestResponse(){}
    public CardPaymentRequestResponse(int paymentId, String paymentUrl){
        this.paymentId = paymentId;
        this.paymentUrl = paymentUrl;
    }
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
