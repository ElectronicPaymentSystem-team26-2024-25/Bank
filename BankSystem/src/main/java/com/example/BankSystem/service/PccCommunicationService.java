package com.example.BankSystem.service;

import com.example.BankSystem.dto.PCCPaymentExecutionRequest;
import com.example.BankSystem.dto.PCCPaymentExecutionResponse;
import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
import com.example.BankSystem.model.*;
import com.example.BankSystem.repository.AcquirerOrderRepository;
import com.example.BankSystem.repository.BankAccountRepository;
import com.example.BankSystem.repository.MerchantOrderRepository;
import com.example.BankSystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class PccCommunicationService {
    @Autowired
    AcquirerOrderRepository acquirerOrderRepository;
    @Autowired
    BankAccountRepository accountRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    MerchantOrderRepository merchantOrderRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CardPaymentService cardPaymentService;

    public PaymentExecutionResponse sendRequestToPCC(PaymentExecutionRequest paymentExecutionRequest){
        String url = "http://localhost:9090/api/pcc/cardpayment";
        Payment payment = paymentRepository.getReferenceById(paymentExecutionRequest.getPaymentId());
        AcquirerOrder order = generateAcquirerOrder(payment);
        PCCPaymentExecutionRequest request = new PCCPaymentExecutionRequest(paymentExecutionRequest, order.getAcquirerOrderId(), order.getAcquirerTimestamp(), "Banka 1", order.getAmount(), payment.getMerchantOrderId());
        ResponseEntity<PCCPaymentExecutionResponse> response = restTemplate.postForEntity(url, request, PCCPaymentExecutionResponse.class);
        if(response.getStatusCode() == HttpStatusCode.valueOf(400))
            return null;
        PCCPaymentExecutionResponse responseBody = response.getBody();
        if(responseBody.getPaymentStatus() == PaymentStatus.SUCCESS)
            cardPaymentService.savePaymentAtAcquirer(responseBody, payment.getPaymentId());
        return new PaymentExecutionResponse(payment.getMerchantOrderId(), responseBody.getAcquirerOrderId(), responseBody.getAcquirerTimestamp(),
        paymentExecutionRequest.getPaymentId(), responseBody.getPaymentStatus(), cardPaymentService.getPaymentUrl(responseBody.getPaymentStatus(), payment.getPaymentId()), responseBody.getFailReason());
    }

    private AcquirerOrder generateAcquirerOrder(Payment payment){
        MerchantOrder order = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        BankAccount account = accountRepository.findByMerchantId(order.getMerchantId());
        AcquirerOrder acquirerOrder = new AcquirerOrder(LocalDateTime.now(), account.getAccountId(), payment.getAmount());
        return acquirerOrderRepository.save(acquirerOrder);
    }
}
