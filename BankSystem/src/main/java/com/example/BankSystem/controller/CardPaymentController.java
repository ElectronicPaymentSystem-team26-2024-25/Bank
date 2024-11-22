package com.example.BankSystem.controller;

import com.example.BankSystem.dto.CardPaymentRequest;
import com.example.BankSystem.dto.CardPaymentRequestResponse;
import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
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
        CardPaymentRequestResponse response = cardPaymentService.getCardPaymentForm(cardPaymentRequest);
        if(response.getPaymentId() == -1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping (consumes = "application/json", path = "/executepayment")
    public ResponseEntity<Object> executePayment(@RequestBody PaymentExecutionRequest paymentExecutionRequest)
    {
        //TODO: proveriti pre ovoga da li je struktura PAN-a dobra
        if(!cardPaymentService.isPaymentExecutable(paymentExecutionRequest.getPaymentId()))
            return new ResponseEntity<>("Invalid request",HttpStatus.BAD_REQUEST);
        if(cardPaymentService.isAccountInCurrentBank(paymentExecutionRequest.getPAN())){
            if(!cardPaymentService.isCardDataValid(paymentExecutionRequest))
                // da li vratiti bad request 400 ili 200
                return new ResponseEntity<>(new PaymentExecutionResponse(-1, -1, null, -1, PaymentStatus.ERROR,
                        cardPaymentService.getPaymentUrl(PaymentStatus.ERROR, paymentExecutionRequest.getPaymentId()), "Card data invalid."),HttpStatus.OK);
            if(!cardPaymentService.hasSufficientFunds(paymentExecutionRequest.getPAN(), paymentExecutionRequest.getPaymentId()))
                return new ResponseEntity<>(new PaymentExecutionResponse(-1, -1, null, -1, PaymentStatus.FAIL,
                        cardPaymentService.getPaymentUrl(PaymentStatus.FAIL, paymentExecutionRequest.getPaymentId()), "Insufficient funds on account."),HttpStatus.OK);
            return new ResponseEntity<>(cardPaymentService.savePayment(paymentExecutionRequest), HttpStatus.OK);
        }
        else{
            PaymentExecutionResponse response = pccCommunicationService.sendRequestToPCC(paymentExecutionRequest);
            if(response==null)
                return new ResponseEntity<>("Invalid request: couldn't reach issuer bank",HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}