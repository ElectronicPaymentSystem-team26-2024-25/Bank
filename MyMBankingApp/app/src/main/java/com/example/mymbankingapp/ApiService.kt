package com.example.mymbankingapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/qrcode/execute-payment")
    fun postData(@Body requestData: PaymentRequest): Call<PaymentResponse>
}