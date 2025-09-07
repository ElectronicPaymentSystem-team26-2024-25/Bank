package com.example.BankSystem.controller;

import com.example.BankSystem.dto.*;
import com.example.BankSystem.model.PaymentStatus;
import com.example.BankSystem.service.CardPaymentService;
import com.example.BankSystem.service.PccCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cardpayment")
public class CardPaymentController {
    @Autowired
    CardPaymentService cardPaymentService;
    @Autowired
    PccCommunicationService pccCommunicationService;

    @PostMapping (consumes = "application/json", path = "/cardpaymentform")
    public ResponseEntity<CardPaymentRequestResponse> getCardPaymentRequestResponse(@RequestBody CardPaymentRequest cardPaymentRequest)
    {
        CardPaymentRequestResponse response = cardPaymentService.getCardPaymentForm(cardPaymentRequest, false);
        if(response.getPaymentId().equals("-1"))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping (consumes = "application/json", path = "/executepayment")
    public ResponseEntity<Object> executePayment(@RequestBody PaymentExecutionRequest paymentExecutionRequest)
    {
        if(!cardPaymentService.isPaymentExecutable(paymentExecutionRequest.getPaymentId()) || !cardPaymentService.isPANValid(paymentExecutionRequest.getPAN()))
            return new ResponseEntity<>("Invalid request",HttpStatus.BAD_REQUEST);
        if(cardPaymentService.isAccountInCurrentBank(paymentExecutionRequest.getPAN())){
            if(!cardPaymentService.isCardDataValid(paymentExecutionRequest))
                return new ResponseEntity<>(new PaymentExecutionResponse(-1, -1, null, "-1", PaymentStatus.ERROR,
                        cardPaymentService.getPaymentUrl(PaymentStatus.ERROR, paymentExecutionRequest.getPaymentId()), "Card data invalid."),HttpStatus.OK);
            if(!cardPaymentService.hasSufficientFunds(paymentExecutionRequest.getPAN(), paymentExecutionRequest.getPaymentId()))
                return new ResponseEntity<>(new PaymentExecutionResponse(-1, -1, null, "-1", PaymentStatus.FAIL,
                        cardPaymentService.getPaymentUrl(PaymentStatus.FAIL, paymentExecutionRequest.getPaymentId()), "Insufficient funds on account."),HttpStatus.OK);
            PaymentExecutionResponse response = cardPaymentService.savePayment(paymentExecutionRequest);
            cardPaymentService.sendResponseToPSP(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            PaymentExecutionResponse response = pccCommunicationService.sendRequestToPCC(paymentExecutionRequest);
            if(response==null)
                return new ResponseEntity<>("Invalid request: couldn't reach issuer bank",HttpStatus.BAD_REQUEST);
            else{
                cardPaymentService.sendResponseToPSP(response);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
    @GetMapping (path = "/amount/{paymentId}")
    public ResponseEntity<Integer> getAmount(@PathVariable String paymentId)
    {
        return new ResponseEntity<>(cardPaymentService.getPaymentAmount(paymentId), HttpStatus.OK);
    }

}