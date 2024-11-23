package com.example.BankSystem.repository;

import com.example.BankSystem.model.PaymentUrls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentUrlsRepository extends JpaRepository<PaymentUrls, Integer> {
    public PaymentUrls findByPaymentId(String paymentId);
}
