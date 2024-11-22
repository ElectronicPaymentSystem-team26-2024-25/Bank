package com.example.BankSystem.repository;

import com.example.BankSystem.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    public BankAccount findByPAN(String PAN);
    public BankAccount findByMerchantId(String merchantId);
}
