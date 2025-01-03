package com.example.BankSystem.service;

import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
import com.example.BankSystem.dto.QRPaymentRequest;
import com.example.BankSystem.model.*;
import com.example.BankSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class QRCodePaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    AcquirerOrderRepository acquirerOrderRepository;
    @Autowired
    IssuerOrderRepository issuerOrderRepository;
    @Autowired
    MerchantOrderRepository merchantOrderRepository;
    @Autowired
    PaymentUrlsRepository paymentUrlsRepository;
    @Autowired
    RestTemplate restTemplate;
    @Value("${custom.property.bankName}")
    String bankName;
    @Value("${custom.property.bankId}")
    String bankId;

    public PaymentExecutionResponse savePayment(QRPaymentRequest paymentExecution){
        Payment payment = paymentRepository.getReferenceById(paymentExecution.getPaymentId());
        MerchantOrder merchantOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        AcquirerOrder acquirerOrder = makeAcquirerOrder(payment.getAmount(), merchantOrder.getMerchantId());
        IssuerOrder issuerOrder = makeIssuerOrder(payment.getAmount(), paymentExecution.getCustomerAccountNumber());
        payment.setAcquirerOrderId(acquirerOrder.getAcquirerOrderId());
        payment.setIssuerOrderId(issuerOrder.getIssuerOrderId());
        payment.setIssuerBank(bankName);
        executePayment(paymentExecution.getMerchantAccountNumber(), paymentExecution.getCustomerAccountNumber(), payment.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return new PaymentExecutionResponse(merchantOrder.getMerchantOrderId(), acquirerOrder.getAcquirerOrderId(), acquirerOrder.getAcquirerTimestamp(),
                paymentExecution.getPaymentId(), PaymentStatus.SUCCESS, "success url sa domenom PSP-a", ".");
    }

    private AcquirerOrder makeAcquirerOrder(int amount, String merchantId){
        BankAccount bankAccount = bankAccountRepository.findByMerchantId(merchantId);
        AcquirerOrder order = new AcquirerOrder(LocalDateTime.now(), bankAccount.getAccountId(), amount);
        return acquirerOrderRepository.save(order);
    }

    private IssuerOrder makeIssuerOrder(int amount, String accountNumber){
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        IssuerOrder order = new IssuerOrder(LocalDateTime.now(), bankAccount.getAccountId(), amount);
        return issuerOrderRepository.save(order);
    }

    private void executePayment(String merchantAccountNumber, String customerAccountNumber, int amount){
        addFunds(merchantAccountNumber, amount);
        withdrawFunds(customerAccountNumber, amount);
    }

    private void addFunds(String accountNumber, int amount){
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber);
        account.setAvailableFunds(account.getAvailableFunds() + amount);
        bankAccountRepository.save(account);
    }
    private void withdrawFunds(String accountNumber, int amount){
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber);
        account.setAvailableFunds(account.getAvailableFunds() - amount);
        bankAccountRepository.save(account);
    }

    public boolean hasSufficientFunds(String accountNumber, String paymentId){
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber);
        Payment payment = paymentRepository.getReferenceById(paymentId);
        if(account == null)
            return false;
        return account.getAvailableFunds() >= payment.getAmount();
    }

    public String getQRCodeText(String paymentId){
        Payment payment = paymentRepository.getReferenceById(paymentId);
        MerchantOrder mOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        BankAccount account = bankAccountRepository.findByMerchantId(mOrder.getMerchantId());
        String qrText = "K:PR|V:01|C:"+paymentId+"|R:"+account.getAccountNumber()+"|N:Prodavac usluge|I:EU;"+payment.getAmount()+
                "|P:TELEKOM|SF:123|S:Kupovanje usluge|RO:"+mOrder.getMerchantId();
        return qrText;
    }
}
