package com.example.BankSystem.service;

import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.model.AcquirerOrder;
import com.example.BankSystem.model.BankAccount;
import com.example.BankSystem.repository.AcquirerOrderRepository;
import com.example.BankSystem.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PccCommunicationService {
    @Autowired
    AcquirerOrderRepository acquirerOrderRepository;
    @Autowired
    BankAccountRepository accountRepository;

    public void sendRequestToPCC(PaymentExecutionRequest paymentExecutionRequest){

    }

    private AcquirerOrder generateAcquirerOrder(String merchantId, int amount){
        BankAccount account = accountRepository.findByMerchantId(merchantId);
        AcquirerOrder acquirerOrder = new AcquirerOrder(LocalDateTime.now(), account.getAccountId(), "Trenutna banka", amount);
        return acquirerOrderRepository.save(acquirerOrder);
    }
}
