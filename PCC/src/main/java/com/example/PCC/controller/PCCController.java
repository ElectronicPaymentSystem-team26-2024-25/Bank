package com.example.PCC.controller;

import com.example.PCC.dto.PCCPaymentExecutionRequest;
import com.example.PCC.dto.PCCPaymentExecutionResponse;
import com.example.PCC.service.PCCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pcc")
public class PCCController {
    @Autowired
    PCCService service;
    @PostMapping(consumes = "application/json", path = "/cardpayment")
    public ResponseEntity<PCCPaymentExecutionResponse> getCardPaymentRequestResponse(@RequestBody PCCPaymentExecutionRequest request)
    {
        return new ResponseEntity<>(service.sendPaymentRequestToBank(request), HttpStatus.OK);
    }
}
