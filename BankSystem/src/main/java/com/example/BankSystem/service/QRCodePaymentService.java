package com.example.BankSystem.service;

import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
import com.example.BankSystem.dto.PaymentStatusResponse;
import com.example.BankSystem.dto.QRPaymentRequest;
import com.example.BankSystem.model.*;
import com.example.BankSystem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

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
    private static final Logger log = LoggerFactory.getLogger(QRCodePaymentService.class);
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
        log.info("Payment approved for account ending in {} for merchant {}",
                paymentExecution.getCustomerAccountNumber().substring(paymentExecution.getCustomerAccountNumber().length() - 4), payment.getMerchantOrderId());
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
        BankAccount bankAccount = getAccountByAccountNumber(accountNumber);
        IssuerOrder order = new IssuerOrder(LocalDateTime.now(), bankAccount.getAccountId(), amount);
        return issuerOrderRepository.save(order);
    }

    private void executePayment(String merchantAccountNumber, String customerAccountNumber, int amount){
        addFunds(merchantAccountNumber, amount);
        withdrawFunds(customerAccountNumber, amount);
    }

    private void addFunds(String accountNumber, int amount){
        BankAccount account = getAccountByAccountNumber(accountNumber);
        account.setAvailableFunds(account.getAvailableFunds() + amount);
        bankAccountRepository.save(account);
    }
    private void withdrawFunds(String accountNumber, int amount){
        BankAccount account = getAccountByAccountNumber(accountNumber);
        account.setAvailableFunds(account.getAvailableFunds() - amount);
        bankAccountRepository.save(account);
    }

    public boolean hasSufficientFunds(String accountNumber, String paymentId){
        BankAccount account = getAccountByAccountNumber(accountNumber);
        Payment payment = paymentRepository.getReferenceById(paymentId);
        if(account == null)
            return false;
        if(account.getAvailableFunds() >= payment.getAmount()){
            return true;
        }
        else{
            log.warn("Payment failed because of insufficient funds for account ending with {}",
                    accountNumber.substring(accountNumber.length() - 4));
            return false;
        }
    }

    public String getQRCodeText(String paymentId){
        Payment payment = paymentRepository.getReferenceById(paymentId);
        MerchantOrder mOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        BankAccount account = bankAccountRepository.findByMerchantId(mOrder.getMerchantId());
        //TODO: probati naci podatke kao sto su naziv prodavca, svrha uplate itd...
        String qrText = "K:PR|V:01|C:"+paymentId+"|R:"+account.getAccountNumber()+"|N:Prodavac usluge|I:EU;"+payment.getAmount()+
                "|P:TELEKOM|SF:123|S:Kupovanje usluge|RO:"+mOrder.getMerchantId();
        return qrText;
    }

    public PaymentStatusResponse getPaymentStatusResponse(String paymentId){
        Payment payment = paymentRepository.getReferenceById(paymentId);
        return new PaymentStatusResponse(payment.getStatus(), payment.getPaymentId(), payment.getMerchantOrderId());
    }

    private BankAccount getAccountByAccountNumber(String accountNumber){
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
}
