package com.example.BankSystem.dto;

public class QRCodeRequest {
    private String paymentId;
    public QRCodeRequest(){}
    public String getText() {
        return paymentId;
    }

    public void setText(String text) {
        this.paymentId = text;
    }
}
