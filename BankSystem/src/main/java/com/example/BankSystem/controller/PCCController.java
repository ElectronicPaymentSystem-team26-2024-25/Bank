package com.example.BankSystem.controller;

import com.example.BankSystem.dto.PCCPaymentExecutionRequest;
import com.example.BankSystem.dto.PCCPaymentExecutionResponse;
import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
import com.example.BankSystem.model.PaymentStatus;
import com.example.BankSystem.service.CardPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pcc-cardpayment")
public class PCCController {
    @Autowired
    CardPaymentService service;
    @PostMapping(consumes = "application/json", path = "/execute-at-issuer")
    public ResponseEntity<PCCPaymentExecutionResponse> executePaymentAtIssuer(@RequestBody PCCPaymentExecutionRequest request)
    {
        if(!service.isCardDataValid(new PaymentExecutionRequest(request)))
            return new ResponseEntity<>(new PCCPaymentExecutionResponse(-1, null, -1, null, PaymentStatus.ERROR, "error url", "Card data invalid."),HttpStatus.OK);
        if(!service.hasSufficientFundsAtIssuer(request.getPAN(), request.getAmount()))
            return new ResponseEntity<>(new PCCPaymentExecutionResponse(-1, null, -1, null, PaymentStatus.FAIL, "fail url", "Insufficient funds on account."),HttpStatus.OK);
        PCCPaymentExecutionResponse response = service.savePaymentAtIssuer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
