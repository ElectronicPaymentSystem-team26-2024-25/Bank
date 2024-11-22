package com.example.BankSystem.controller;

import com.example.BankSystem.dto.PCCPaymentExecutionRequest;
import com.example.BankSystem.dto.PCCPaymentExecutionResponse;
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
        PCCPaymentExecutionResponse response = service.savePaymentAtIssuer(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
