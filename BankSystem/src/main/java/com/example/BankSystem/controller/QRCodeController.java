package com.example.BankSystem.controller;

import com.example.BankSystem.dto.*;
import com.example.BankSystem.model.Payment;
import com.example.BankSystem.model.PaymentStatus;
import com.example.BankSystem.service.CardPaymentService;
import com.example.BankSystem.service.QRCodeGenerator;
import com.example.BankSystem.service.QRCodePaymentService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("api/qrcode")
public class QRCodeController {

    @Autowired
    CardPaymentService cardPaymentService;
    @Autowired
    QRCodePaymentService qrCodePaymentService;
    @GetMapping (path = "/{paymentId}")
    public ResponseEntity<QRCodeResponse> makeQrCode(@PathVariable String paymentId) throws IOException, WriterException {
        byte[] response = QRCodeGenerator.getQRCodeImage(qrCodePaymentService.getQRCodeText(paymentId), 300, 300);
        QRCodeResponse qrCodeResponse = new QRCodeResponse();
        String base64Response = Base64.getEncoder().encodeToString(response);
        qrCodeResponse.setResponse(base64Response);
        return new ResponseEntity<>(qrCodeResponse, HttpStatus.OK);
    }

    @PostMapping (consumes = "application/json", path = "/cardpaymentform")
    public ResponseEntity<CardPaymentRequestResponse> getQRPaymentRequestResponse(@RequestBody CardPaymentRequest cardPaymentRequest)
    {
        CardPaymentRequestResponse response = cardPaymentService.getCardPaymentForm(cardPaymentRequest, true);
        if(response.getPaymentId().equals("-1"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping (path = "/execute-payment")
    public ResponseEntity<Object> executePayment(@RequestBody QRPaymentRequest request)
    {
        if(!cardPaymentService.isPaymentExecutable(request.getPaymentId()))
            return new ResponseEntity<>("Invalid request",HttpStatus.BAD_REQUEST);
        if(!qrCodePaymentService.hasSufficientFunds(request.getCustomerAccountNumber(), request.getPaymentId()))
            return new ResponseEntity<>(new PaymentExecutionResponse(-1, -1, null, "-1", PaymentStatus.FAIL,
                    cardPaymentService.getPaymentUrl(PaymentStatus.FAIL, request.getPaymentId()), "Insufficient funds on account."),HttpStatus.OK);
        PaymentExecutionResponse response = qrCodePaymentService.savePayment(request);
        cardPaymentService.sendResponseToPSP(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping (path = "/payment-status/{paymentId}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable String paymentId) {
        PaymentStatusResponse response = qrCodePaymentService.getPaymentStatusResponse(paymentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}