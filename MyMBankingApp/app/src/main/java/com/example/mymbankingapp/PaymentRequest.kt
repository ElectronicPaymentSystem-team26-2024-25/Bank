package com.example.mymbankingapp

data class PaymentRequest(
    var customerAccountNumber: String,
    var merchantAccountNumber: String,
    var paymentId: String,
    var amount: Int,
    var merchantId: String
)
