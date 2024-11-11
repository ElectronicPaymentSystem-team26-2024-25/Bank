package com.example.BankSystem.service;

import com.example.BankSystem.dto.CardPaymentRequest;
import com.example.BankSystem.dto.CardPaymentRequestResponse;
import com.example.BankSystem.dto.PaymentExecutionRequest;
import com.example.BankSystem.dto.PaymentExecutionResponse;
import com.example.BankSystem.model.*;
import com.example.BankSystem.repository.AcquirerOrderRepository;
import com.example.BankSystem.repository.BankAccountRepository;
import com.example.BankSystem.repository.IssuerOrderRepository;
import com.example.BankSystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public CardPaymentRequestResponse getCardPaymentForm(CardPaymentRequest cardPaymentRequest){
        if(isCardPaymentRequestValid(cardPaymentRequest)){
            return new CardPaymentRequestResponse(getNewCardPaymentId(cardPaymentRequest.getAmount()), getPaymentUrl());
        }else{
            return new CardPaymentRequestResponse(-1, "");
        }
    }

    private boolean isCardPaymentRequestValid(CardPaymentRequest cardPaymentRequest){
        //TODO: kako proveriti da li je zahtev validan???
        return true;
    }

    private int getNewCardPaymentId(int amount){
        Payment payment = new Payment(0, 0, amount);
        payment = paymentRepository.save(payment);
        return payment.getPaymentId();
    }

    private String getPaymentUrl(){
        return "";
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

    public boolean hasSufficientFunds(int PAN, int paymentId){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        Payment payment = paymentRepository.getReferenceById(paymentId);
        if(account == null)
            return false;
        if(account.getAvailableFunds() < payment.getAmount())
            return false;
        else
            return true;
    }

    //TODO: dodati transakcioni rezim rada
    public PaymentExecutionResponse executePayment(PaymentExecutionRequest paymentExecution){
        Payment payment = paymentRepository.getReferenceById(paymentExecution.getPaymentId());
        AcquirerOrder acquirerOrder = makeAcquirerOrder(payment.getAmount());
        IssuerOrder issuerOrder = makeIssuerOrder(payment.getAmount());
        payment.setAcquirerOrderId(acquirerOrder.getAcquirerOrderId());
        payment.setIssuerOrderId(issuerOrder.getIssuerOrderId());
        paymentRepository.save(payment);
        withdrawFunds(paymentExecution.getPAN(), payment.getAmount());
        return new PaymentExecutionResponse(0, acquirerOrder.getAcquirerOrderId(), acquirerOrder.getAcquirerTimestamp(),
                paymentExecution.getPaymentId(), PaymentStatus.SUCCESS, "");
    }

    private AcquirerOrder makeAcquirerOrder(int amount){
        //Kako znam koji je njegov account i da li ovo uopste treba...
        AcquirerOrder order = new AcquirerOrder(LocalDateTime.now(), "0", "trenutna banka", amount);
        return acquirerOrderRepository.save(order);
    }

    private IssuerOrder makeIssuerOrder(int amount){
        //ovde znam njegov account id
        IssuerOrder order = new IssuerOrder(LocalDateTime.now(), "0", "trenutna banka", amount);
        return issuerOrderRepository.save(order);
    }

    private void withdrawFunds(int PAN, int amount){
        BankAccount account = bankAccountRepository.findByPAN(PAN);
        account.setAvailableFunds(account.getAvailableFunds() - amount);
        bankAccountRepository.save(account);
    }
}
