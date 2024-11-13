package com.example.BankSystem.repository;

import com.example.BankSystem.model.MerchantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantOrderRepository extends JpaRepository<MerchantOrder, Integer> {
}
