package com.example.BankSystem.service;

import com.example.BankSystem.dto.*;
import com.example.BankSystem.model.*;
import com.example.BankSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardPaymentService {
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
    @Value("${custom.property.bankName}")
    String bankName;
    @Value("${custom.property.bankId}")
    String bankId;
    public CardPaymentRequestResponse getCardPaymentForm(CardPaymentRequest cardPaymentRequest){
        if(isCardPaymentRequestValid(cardPaymentRequest)){
            saveMerchantOrder(cardPaymentRequest);
            String paymentId = getNewCardPaymentId(cardPaymentRequest.getAmount(), cardPaymentRequest.getMerchantOrderId());
            savePaymentUrls(cardPaymentRequest, paymentId);
            return new CardPaymentRequestResponse(paymentId, getPaymentUrl(paymentId));
        }else{
            return new CardPaymentRequestResponse("-1", "");
        }
    }

    private boolean isCardPaymentRequestValid(CardPaymentRequest cardPaymentRequest){
        //TODO: kako proveriti da li je zahtev validan???
        BankAccount account = bankAccountRepository.findByMerchantId(cardPaymentRequest.getMerchantId());
        if(account == null)
            return false;
        else return true;
    }

    private String getNewCardPaymentId(int amount, int merchantOrderId){
        Payment payment = new Payment(0, bankName, 0, "?", amount, PaymentStatus.IN_PROGRESS, merchantOrderId);
        payment = paymentRepository.save(payment);
        return payment.getPaymentId();
    }
    private void savePaymentUrls(CardPaymentRequest cardPaymentRequest, String paymentId){
        PaymentUrls paymentUrls = new PaymentUrls(paymentId, cardPaymentRequest.getSuccessUrl(), cardPaymentRequest.getFailedUrl(), cardPaymentRequest.getErrorUrl());
        paymentUrlsRepository.save(paymentUrls);
    }
    private void saveMerchantOrder(CardPaymentRequest cardPaymentRequest){
        MerchantOrder order = new MerchantOrder(cardPaymentRequest.getMerchantOrderId(), cardPaymentRequest.getMerchantTimestamp(), cardPaymentRequest.getMerchantId(), cardPaymentRequest.getAmount());
        merchantOrderRepository.save(order);
    }

    private String getPaymentUrl(String paymentId){
        return "http://localhost:4200/payment/"+paymentId;
    }

    public boolean isCardDataValid(PaymentExecutionRequest paymentExecutionRequest){
        BankAccount account = bankAccountRepository.findByPAN(paymentExecutionRequest.getPAN());
        if(account == null)
            return false;
        if(account.getSecurityCode() != paymentExecutionRequest.getSecurityCode() ||
            account.getExpirationDate().getYear() != paymentExecutionRequest.getExpirationDate().getYear() ||
            account.getExpirationDate().getMonthValue() != paymentExecutionRequest.getExpirationDate().getMonthValue() ||
            !account.getCardHolderName().equals(paymentExecutionRequest.getCardHolderName())){
            return false;
        }
        return true;
    }

    public boolean hasSufficientFunds(String PAN, String paymentId){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        Payment payment = paymentRepository.getReferenceById(paymentId);
        if(account == null)
            return false;
        return account.getAvailableFunds() >= payment.getAmount();
    }
    public boolean hasSufficientFundsAtIssuer(String PAN, int amount){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        if(account == null)
            return false;
        return account.getAvailableFunds() >= amount;
    }
    public boolean isPaymentExecutable(String paymentId){
        Payment payment = paymentRepository.getReferenceById(paymentId);
        if(payment.getStatus() == PaymentStatus.SUCCESS)
            return false;
        return true;
    }

    public boolean isAccountInCurrentBank(String pan){
        String firstSixDigits = pan.substring(0, 6);
        if(firstSixDigits.equals(bankId))
            return true;
        else return false;
    }

    //TODO: dodati transakcioni rezim rada
    public PaymentExecutionResponse savePayment(PaymentExecutionRequest paymentExecution){
        Payment payment = paymentRepository.getReferenceById(paymentExecution.getPaymentId());
        MerchantOrder merchantOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        AcquirerOrder acquirerOrder = makeAcquirerOrder(payment.getAmount(), merchantOrder.getMerchantId());
        IssuerOrder issuerOrder = makeIssuerOrder(payment.getAmount(), paymentExecution.getPAN());
        payment.setAcquirerOrderId(acquirerOrder.getAcquirerOrderId());
        payment.setIssuerOrderId(issuerOrder.getIssuerOrderId());
        payment.setIssuerBank(bankName);
        executePayment(merchantOrder.getMerchantId(), paymentExecution.getPAN(), payment.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return new PaymentExecutionResponse(0, acquirerOrder.getAcquirerOrderId(), acquirerOrder.getAcquirerTimestamp(),
                paymentExecution.getPaymentId(), PaymentStatus.SUCCESS, "success url sa domenom PSP-a", ".");
    }

    private AcquirerOrder makeAcquirerOrder(int amount, String merchantId){
        BankAccount bankAccount = bankAccountRepository.findByMerchantId(merchantId);
        AcquirerOrder order = new AcquirerOrder(LocalDateTime.now(), bankAccount.getAccountId(), amount);
        return acquirerOrderRepository.save(order);
    }

    private IssuerOrder makeIssuerOrder(int amount, String PAN){
        BankAccount bankAccount = bankAccountRepository.findByPAN(PAN);
        IssuerOrder order = new IssuerOrder(LocalDateTime.now(), bankAccount.getAccountId(), amount);
        return issuerOrderRepository.save(order);
    }

    private void executePayment(String merchantId, String buyerPAN, int amount){
        BankAccount merchantAccount = bankAccountRepository.findByMerchantId(merchantId);
        addFunds(merchantAccount.getPAN(), amount);
        withdrawFunds(buyerPAN, amount);
    }

    private void addFunds(String PAN, int amount){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        account.setAvailableFunds(account.getAvailableFunds() + amount);
        bankAccountRepository.save(account);
    }
    private void withdrawFunds(String PAN, int amount){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        account.setAvailableFunds(account.getAvailableFunds() - amount);
        bankAccountRepository.save(account);
    }

    public PCCPaymentExecutionResponse savePaymentAtIssuer(PCCPaymentExecutionRequest paymentExecution){
        Payment payment = new Payment(paymentExecution.getAcquirerOrderId(), paymentExecution.getAcquirerBank(), 0, bankName, paymentExecution.getAmount(), PaymentStatus.IN_PROGRESS, paymentExecution.getMerchantOrderId());
        paymentRepository.save(payment);
        MerchantOrder merchantOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        IssuerOrder issuerOrder = makeIssuerOrder(payment.getAmount(), paymentExecution.getPAN());
        payment.setAcquirerOrderId(paymentExecution.getAcquirerOrderId());
        payment.setIssuerOrderId(issuerOrder.getIssuerOrderId());
        withdrawFunds(paymentExecution.getPAN(), payment.getAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return new PCCPaymentExecutionResponse(paymentExecution.getAcquirerOrderId(), paymentExecution.getAcquirerOrderTimestamp(),
                issuerOrder.getIssuerOrderId(), issuerOrder.getIssuerTimestamp(), payment.getStatus(), bankName, ".");
    }

    public void savePaymentAtAcquirer(PCCPaymentExecutionResponse pccPaymentExecutionResponse, String paymentId){
        Payment payment = paymentRepository.getReferenceById(paymentId);
        payment.setStatus(pccPaymentExecutionResponse.getPaymentStatus());
        payment.setAcquirerOrderId(pccPaymentExecutionResponse.getAcquirerOrderId());
        payment.setAcquirerBank(bankName);
        payment.setIssuerOrderId(pccPaymentExecutionResponse.getIssuerOrderId());
        payment.setIssuerBank(pccPaymentExecutionResponse.getIssuerBank());
        MerchantOrder merchantOrder = merchantOrderRepository.getReferenceById(payment.getMerchantOrderId());
        BankAccount merchantAccount = bankAccountRepository.findByMerchantId(merchantOrder.getMerchantId());
        addFunds(merchantAccount.getPAN(), payment.getAmount());
        paymentRepository.save(payment);
    }
    public String getPaymentUrl(PaymentStatus status, String paymentId){
        PaymentUrls paymentUrls = paymentUrlsRepository.findByPaymentId(paymentId);
        if(status == PaymentStatus.SUCCESS) return paymentUrls.getSuccessUrl();
        else if (status == PaymentStatus.ERROR) return paymentUrls.getErrorUrl();
        else return paymentUrls.getFailUrl();
    }
}
