package com.example.BankSystem.controller;

import com.example.BankSystem.dto.CardPaymentRequest;
import com.example.BankSystem.dto.CardPaymentRequestResponse;
import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.service.CardPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cardpayment")
public class CardPaymentController {
    @Autowired
    CardPaymentService cardPaymentService;
    @PostMapping (consumes = "application/json", path = "/cardpaymentform")
    public ResponseEntity<CardPaymentRequestResponse> getCardPaymentRequestResponse(@RequestBody CardPaymentRequest cardPaymentRequest)
    {
        CardPaymentRequestResponse response = cardPaymentService.getCardPaymentForm(cardPaymentRequest);
        if(response.getPaymentId() == -1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: resiti neuspesne slucajeve na drugi nacin (fail, error url...)
    @PutMapping (consumes = "application/json", path = "/executepayment")
    public ResponseEntity<Object> executePayment(@RequestBody PaymentExecutionRequest paymentExecutionRequest)
    {
        //TODO: proveriti pre ovoga da li je struktura PAN-a dobra
        if(cardPaymentService.isAccountInCurrentBank(paymentExecutionRequest.getPAN())){
            if(!cardPaymentService.isPaymentInProgress(paymentExecutionRequest.getPaymentId()))
                return new ResponseEntity<>("Invalid request",HttpStatus.BAD_REQUEST);
            if(!cardPaymentService.isCardDataValid(paymentExecutionRequest))
                return new ResponseEntity<>("Invalid request: given bank card data is not valid.",HttpStatus.BAD_REQUEST);
            if(!cardPaymentService.hasSufficientFunds(paymentExecutionRequest.getPAN(), paymentExecutionRequest.getPaymentId()))
                return new ResponseEntity<>("Invalid request: insufficient funds",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(cardPaymentService.savePayment(paymentExecutionRequest), HttpStatus.OK);
        }
        else{
            //pozovi drugu banku

        }
    }
}