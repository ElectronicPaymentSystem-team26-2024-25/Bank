package com.example.PCC.service;

import com.example.PCC.dto.PCCPaymentExecutionRequest;
import com.example.PCC.dto.PCCPaymentExecutionResponse;
import com.example.PCC.model.Bank;
import com.example.PCC.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class PCCService {
    @Autowired
    BankRepository bankRepository;
    @Autowired
    RestTemplate restTemplate;
    public PCCPaymentExecutionResponse sendPaymentRequestToBank(PCCPaymentExecutionRequest paymentRequest){
        ArrayList<Bank> banks = new ArrayList<>(bankRepository.findAll());
        Bank issuerBank = new Bank();
        String bankId = getBankId(paymentRequest.getPAN());
        for(Bank b: banks){
            if(bankId.equals(String.valueOf(b.getBankId()))){
                issuerBank = b;
                break;
            }
        }
        if(issuerBank.getBankId() == 0)
            return null;
        String url = "http://localhost:"+issuerBank.getPort()+"/api/pcc-cardpayment/execute-at-issuer";
        ResponseEntity<PCCPaymentExecutionResponse> response = restTemplate.postForEntity(url, paymentRequest, PCCPaymentExecutionResponse.class);
        return response.getBody();
    }
    private String getBankId(String pan){
        return pan.substring(0, 6);
    }
}
